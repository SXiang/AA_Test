/**
 * 
 */
package taf.tool;

import java.util.Date;

import com.acl.qa.taf.util.FileUtil;

/**
 * Script Name   : <b>GetL10NProperties.java</b>
 * Generated     : <b>3:59:50 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Feb 5, 2014
 * @author steven_xiang
 * 
 */
public class GetL10NProperties {

		private static int logLvl = 1;	// 1: info; 2: debug
		
		private static String[] supportLangArr = {
			"en"
			, "de" 
			,"es"
			, "fr" 
			,"pt"
			,"zh"
			,"pl"
			,"ko"
			,"ja"
			};
		private static String[] inputFiles = {
			"English.RHRC"
			, "German.RHRC"
			, "Spanish.RHRC"
			, "French.RHRC"
			, "Portuguese.RHRC"
			,"SimplifiedChinese.RHRC"
			,"Polish.RHRC"
			,"Korean.RHRC"
			,"Japanese.RHRC"
			};

		//private static String outputDir = "ACL_Desktop\\DATA\\LocalizationProperty\\";
		private static String outputDir =   "ACL_Desktop\\DATA\\LocalizationProperty\\util\\Desktop_Resources\\Work\\";
		private static String inputDir = "ACL_Desktop\\DATA\\LocalizationProperty\\util\\Desktop_Resources\\Work\\UTF8\\";
        private static String outputFilePrefix = "ACL_Desktop_",outputFileExt = ".properties";
        //private static String encoding = "UTF-8";
        
        private static String[] masterfile = null;
        private static String WrongIDs = "\n\n---------------------- Possible Errors -----------------------\n";
        private static String toolsDir = FileUtil.getAbsDir(
        		"ACL_Desktop\\DATA\\LocalizationProperty\\util\\Desktop_Resources\\Tools\\"
        		);
		public static void main(String args[])
		{

			// main functions start here
			logWarning("Started getting properties...");
			// Prepare input Files
			String comm = "START \"\" /D\""+toolsDir+"\" /B /WAIT";
			logInfo("git pull and combine .rh .rc src files...");
			logInfo(FileUtil.exeComm(comm+" combineRHRCFiles.bat"));
			logInfo("combine ACLServer src file ...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logInfo(FileUtil.exeComm(comm+" combineAllFiles.bat"));
			
			//for(int i=2;i<3;i++){			
		    for(int i=0;i<inputFiles.length;i++){
		    	logInfo("processFile "+inputDir+inputFiles[i]);
				processFile(supportLangArr[i],inputDir+inputFiles[i],outputDir+outputFilePrefix+supportLangArr[i]+outputFileExt);
			}
		    
		    System.out.println(WrongIDs);
		    FileUtil.writeFileContents(outputDir+"error.log",WrongIDs,"UTF-8");
		}
		
		
		public static void processFile(String lang,String inputFile,String outputFile){
			String lineDel = "\r\n",linePre = "lv_",del="<AUTONEWLINE>",multLine=".*,$";
			int lineNum = 0;			
			String src = FileUtil.readFile(inputFile,del);
			String[] specialCases = {
//					"lv_*_DLG_REPORT_DIALOG_CONTROL_14_ID",
//					"lv_*_DLG_REPORT_DIALOG_CONTROL_BS_AUTOCHECKBOX_dup3_ID"
			};
			
			if("".equals(src)){
				logWarning("Not found: '"+inputFile+"'");
				return;
			}
			
			String[] srcArray = src.split(del);
			src = null;
			
			String out ="",line="",temp="",id="",iddup="",newline="";
			int dup = 0;
			boolean dupfound;
			String srcfile="#ImportFromFile_[\\d]*=.*";
			logInfo("'"+srcArray.length+"' Records in file");
			
			WrongIDs += "\n====== "+lang+" Properties ======\nDate: "+new Date()+"\n\n";
//			if(lang.matches("ja|pl|de")){
			if(lang.matches("de")){
				//WrongIDs += "\t Ignored\n";
			}
			for(int i=0;i<srcArray.length;i++){
				temp += srcArray[i];
				if(srcArray[i].matches(multLine)){
					continue;				
				} else if(temp.matches(srcfile)){
					out += temp+lineDel;
					temp = "";
				}else {
					
					if(
							(null!=(line=processLine(temp)))
						   &&(!"".equals(line))
						   &&(line.contains("="))
						  // &&(!out.contains(linePre+line+lineDel))
						   ){
					  lineNum++;
					  id = line.substring(0,line.indexOf("="));
					  

//					  for(int j=0;j<specialCases.length-1;j=j+2){
//						  if(id.matches("(?i)"+specialCases[j]))
//								  id = specialCases[j+1];
//					  }
					  if(out.contains(linePre+id+"=")){						  
						  //for(int x=1;id.matches(specialCases);x++){
						  
						  for(int x=1;x<100;x++){
							  iddup = id+"_dup"+x;
							  if(!out.contains(linePre+iddup+"="))
									  break;
						  }
						  
						  if(!out.contains(linePre+iddup+"=")){
							  line = line.replaceFirst(id, iddup);
							  dupfound = false;
						  }else{
						    dup++;
						    dupfound=true;
						  }
						  //logInfo("Duplicate id "+dup+"- on line "+i+": "+id);
					  }else{
						  dupfound=false;
					  }
					  
					  if(!dupfound){
						  newline= linePre+line;
					  }else if(line.startsWith(uniqueName)){
					      //out += linePre+(++lineNum)+"_"+line+lineDel;
					      newline= linePre+(dup)+"_"+line;
					      WrongIDs +="\t***Duplicate id "+dup+"- on line "+"[?]"+": "+id+"\n";
					  }else{
						  newline= linePre+(lineNum)+"_"+line;
						  WrongIDs +="\t***Duplicate id "+lineNum+"- on line "+"[?]"+": "+id+"\n";
					  }
					  
					  						
					   out += newline+lineDel;
					  // out += verify(lang,newline,lineNum)+lineDel;
				 	}
				 	temp = "";
				}
			}
			if(out.equals(""))
				return;
			
			if(masterfile==null){
				masterfile = out.split(lineDel);
			}//else{
				out = verify(lang,out,lineDel,srcfile,linePre);
			//}
			logInfo("Output "+masterfile.length+" Properties to '"+outputFile+"'");			
			FileUtil.writeFileContents(outputFile, out);
		}
		
		public static String verify(String lang,String out,String lineDel,String srcfile,String linePre){
			String orderedFile = "";
			String[] localfile = out.split(lineDel);
			String opValue=".*=Translation"
				           +"|.*=x"
				           +"|.*=[\\d]*"
				           +"|"+srcfile
				           +"";
			int startPoint=0;
			String srcName = "";
			for(int i=0;i<masterfile.length;i++){
				boolean found = false;
				String masterID = masterfile[i].substring(0,masterfile[i].indexOf("="));
				
				if(masterfile[i].matches(srcfile)){
					srcName=masterfile[i].split("=")[1].trim().replaceAll("\\.", "_");
//					if(!lang.equalsIgnoreCase("En")){
					 orderedFile += masterfile[i]+lineDel;
//					}
					continue;
				}
				for(int j=startPoint;
				                    j<localfile.length&&!found;
				                                                               j++){
					if(localfile[j].matches("Mached|"+srcfile))
					{
						continue;
					}
					
					String localID = localfile[j].substring(0,localfile[j].indexOf("="));
					if(localID.equalsIgnoreCase(masterID)){
						try{
						 orderedFile += localfile[j].replaceFirst(linePre, linePre+srcName+"_")+lineDel;
						}catch(Exception e){
							
						}
						if(j==startPoint){
							startPoint++;
						}
						localfile[j] = "Mached";
						found = true;
						break;
					}
				}
				if(!found){
					String temp=masterfile[i];
					try{
						 temp= temp.replaceFirst(linePre, linePre+srcName+"_");
						}catch(Exception e){
							
						}
					if(!temp.matches(opValue)){
						
					  WrongIDs +="\t*** ["+lang+"-"+srcName+"]Key not found: Line "+(i+1)+" - '"+masterfile[i]+"'\n";
					}

					orderedFile += "#"+temp+lineDel;
				}
			}
			
			for(int j=0;j<localfile.length;j++){
				if(!localfile[j].equalsIgnoreCase("Mached")&&!localfile[j].matches(opValue)){
					WrongIDs +="\t*** ["+lang+"] Extra Key?: Line "+(j+1)+" - '"+localfile[j]+"'\n";
				}
			}
			return orderedFile;
		}
		
		public static String verify(String lang,String line,int lineNum){
			if(lang.matches("ja|pl|de")){	
			//if(lang.matches("de")){
				return line;
			}
			String newline = line,masterline="";
			String masterID = "",thisId = line.substring(0,line.indexOf("="));
			  if(masterfile!=null){
				  if(masterfile.length>=lineNum){
					  masterline = masterfile[lineNum-1];
					  masterID = masterline.substring(0,masterline.indexOf("="));
					  
					  if(!masterID.equals(thisId)){
						 //newline = line.replaceFirst(thisId, masterID);
						 WrongIDs += "\tLine "+lineNum+": Master="+masterline+", Actual="+line+"\n";
					  }
					  
				  }else{
					  WrongIDs += "\tExtra line "+lineNum+" "+line+"\n";
					  //System.out.println(" Extra line found in "+lang+" properties");
				  }
			  }
			  
			  return newline;
		}
		
		
		//static String[] winID={"win_name","win_caption"};
		static String winName="";
		static boolean startWin=false;
		static String uniqueName="WinName_";
		public static String processLine(String srcLine){
			String validPattern = "^[\\s]*[0-9a-zA-Z()_]+[^\\s]+[\\s]+\".+";
//			String validPattern = "^[\\s]*[0-9a-zA-Z()_]+[^\\s]+[\\s]+[^\\s].+";
			String winPattern ="^([0-9a-zA-Z_]+)\\s+([a-zA-Z_]+)\\s+(\\d{1,4}),\\s+(\\d{1,4}),\\s+(\\d{1,4}),\\s+(\\d{1,4})\\s*$";
			String menuPattern = "^^([0-9a-zA-Z_]+)\\s+(MENU)\\s*$";
			String menuPattern1 = "^^([0-9a-zA-Z_]+)\\s+(MENU)$";
			String beginPattern ="^BEGIN$",endPattern="^END$";
			String guiPattern = ",(\\d{1,4}),(\\d{1,4}),(\\d{1,4}),(\\d{1,4})[^\\d]";
			String guiPattern1 = ",(\\d{1,4}),(\\d{1,4}),(\\d{1,4}),(\\d{1,4})$";
			
			
			String guiPoint="";
			
			// For missing double quotes in ACLTXT.RH file,etc.
			String specialKeys[]={"^[\\s]*(SX\\([0-9]+\\)[\\s]+)([^\\s])","$1\"$2"
			                     
			};
			
			String[] pat ={
					//REMOVE ( 
					"^(.*,\\s*)[\\(]([0-9a-zA-Z_]+.*)$","$1$2",
					//combine prop to name
					"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\"\\s*\"),\\s*([0-9a-zA-Z_]+)\\s*$", "$1_$3,$2",
					"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\"\\s*\"),\\s*([0-9a-zA-Z_]+)([,\\s]+.+$)", "$1_$3,$2$4",
					"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\".*[^\"]\"),\\s*([0-9a-zA-Z_]+)\\s*$", "$1_$3,$2",
					"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\".*[^\"]\"),\\s*([0-9a-zA-Z_]+)([,\\s]+.+$)", "$1_$3,$2$4",
					//combine coordinates to name
					//"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\".*)"+guiPattern,"$1_$3_$4_$5_$6$2",
					//"^([\\s]*[0-9a-zA-Z_\\(\\)]+)([\\s]+\".*)"+guiPattern1,"$1_$3_$4_$5_$6$2",
					
                    //convert to equation
					//"^[\\s]*([0-9a-zA-Z_\\(\\)]+)[^\\s]+[\\s]+\"", "$1=", 	// get Name
					"^[\\s]*([0-9a-zA-Z_\\(\\)]+)[,]*[\\s]+[\"”]", "$1=", 	// get Name

					//clean up
					"\\((.+=)","_$1",                               // change ( to _
					"\\)(.+=)","_$1",                           // change ) to _
					"\\)=[\\s\\^]*","=",                           // change ) to _
					"([^\"])\"[\\s,].*","$1",                      // remove anything after '" |,'
					"[\\s]*[\\^]?[\\s]*[\"]$","",                            // remove last quote
					"&","",				                        // remove &
					"”$","",
					"=”","",                                    //” is found in DE-DE-ACLTXTG.RH GX(555) which causes problem
					"(\"\")+","\"",                                 // Remove double quotes DE-ACLTXT.RH SX(750)
					";$","",
					"^([^\"]*)[\"]([^\"]*)$","$1$2",              //Single "
					"//.*","",                                    // comments 
					"[^=][\\^]$","",                                    // ^
					//"(=.*)=","$1",
					"[^\\s=][\\^]"," ",
					"[\\s][\\^]","",
					"[\\\\][n]$","",
					"[\\\\][n]$","",
					
					
					
					
			};
									
			String line = srcLine;
			
			if(startWin){
               if(srcLine.matches(endPattern)){
					startWin = false;
					winName = "";
				}
			}
			
			if(srcLine.matches(winPattern)){
				startWin=true;
//				winName += srcLine.replaceAll(winPattern, "$1_$2_$3_$4_$5_$6");
				winName += srcLine.replaceAll(winPattern, "$1_$2");
			}else if(srcLine.matches(menuPattern1)){
				startWin=true;
				winName += srcLine.replaceAll(menuPattern1, "$1_$2");
			}else if(srcLine.matches(menuPattern)){
				startWin=true;
				winName += srcLine.replaceAll(menuPattern, "$1_$2");
			}
			
			if(!line.matches(validPattern)){
//				if(line.startsWith("SX(")){
//					logInfo("Not valid? '"+line+"'");
//				}
				
				for(int i=0;i<specialKeys.length-1;i=i+2){
					 line = line.replaceFirst(specialKeys[i],specialKeys[i+1].trim());
				}
				if(!line.matches(validPattern)){
				    return "";
				}else{
					logWarning("Changed: '"+srcLine+"'\n\t "+"-> '"+line+"'");
				}
			}
			
			
			for(int i=0;i<pat.length-1;i=i+2){
				line = line.replaceAll(pat[i],pat[i+1]).trim();								
				if("".equals(line))
					break;
				//logDebug("'"+srcLine+"' = '"+line+"'");
				//srcLine = line;
			}		
			
			
			line = line.replaceFirst("=","_ID=");
			if(line.matches(//".*_ID=\\s*[\\d\\-+.,]+\\s*$"+"|"+
			".*_ID=[\\s]*['\"(\\[]?%[\\d]*[l]?[Icds]['\")\\]]?[\\s]*"))
				return "";
			
			if(startWin){
				//line = uniqueName+winName+"_"+line;
				line = winName+"_"+line;
				//startWin = false;
			}
			logDebug("'"+srcLine+"' '"+line+"'");
			return line.replaceAll("_DIALOGEX_","_DIALOG_");
		}
		
		
		private static void logError(String message) {
			System.out.println("\t!!!! " + message + "!!!!");
		}

		private static void logWarning(String message) {
			System.out.println("\t\t#### " + message + "####");
		}

		private static void logInfo(String message) {
			if (logLvl >= 1) {
				System.out.println(message);
			}
		}

		private static void logDebug(String message) {
			if (logLvl >= 2) {
				System.out.println(message);
			}
		}
	}

