package lib.acl.tool.LocalizationSetup.MyFiles;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
/**
 * Description   : GenPropsFile
 * 					Converted from Merge_TransFiles.pl; And add the functionalities to 
 * 					include shortcuts in the properties files.
 * @since  2011/5/26
 * @author David Carlson
 * Modified by David Carlson to allow for multiple directories
 */
public class Steven_GenPropsFile
{
	private static int logLvl = 1;	// 1: info; 2: debug
	
	public static void main(String args[])
	{
		String[] supportLangArr = {"en", "de", "es", "fr", "pt"};
		//String[] supportLangArr = {"en"};
		String workingDir = "lib\\acl\\resource\\LocalizationSetup\\MyFiles\\";//"C:/temp/RFT_Localization/";
		//String workingDir = "lib\\acl\\resource\\LocalizationSetup\\MyFiles\\input\\messages\\gatewayPro_properties";

		String masterFile = workingDir + "input\\messages\\MASTER.PROPERTIES";
		String homeDir = workingDir + "input\\messages\\";
		String GWPROmessagesDir = workingDir + "input\\messages\\GWP_properties\\";
		String GWmessagesDir = workingDir + "input\\messages\\gateway_properties\\";
		String EMmessagesDir = workingDir + "input\\messages\\em-mgmt_properties\\";
		
		//String GWPROmessagesDir = workingDir + "input\\messages\\gateway_properties\\";
		String targetDir = workingDir + "output\\properties\\";
		
		String outFileNamePrefix = "AXEM_Automation_";
		String combinedPropsFilePrefix = "AXEM_Automation_";
		String workingPropertiesDir ="resources\\";
		


		logDebug("Generate Properties files based on:" 
				+ "\n\t masterFile: " + masterFile 
				+ "\n\t GWPROmessagesDir: " + GWPROmessagesDir
				+ "\n\t targetDir: " + targetDir);

		// main functions start here
		Map<String,String> localeHm = new HashMap<String, String>();
		Map<String,String> masterHm = new TreeMap<String, String>();

		FileReader freader;
		LineNumberReader lnreader;
		FileWriter txt, txtCombined;
		PrintWriter out, outCombined;
		String outFileName;
		String outCombinedPropsFileName;

		String line = "";
		String key = "";
		String value = "";

		String paddingStr = " ==== ";
		String[] tmpArr;
		
		
		
		try {

			////////////////////
			// Build master map
			freader = new FileReader(new File(masterFile));
			lnreader = new LineNumberReader(freader);
			while ((line = lnreader.readLine()) != null){
				//skip comments/empty line
				if(line.startsWith("#") || "".equalsIgnoreCase(line.trim()) || line == null || line.indexOf("=") == -1) {
					continue;
				}

				tmpArr = line.split("=");
				key = tmpArr[0];
				value = (tmpArr.length > 1)? tmpArr[1] : "NULL"; // keep default value

				logDebug(key + "=" + value);
				masterHm.put(key, value);
			}
			
			freader.close();
			lnreader.close();

			String curLang;
			String curPropFile;
			String curPropFileFullPath = null;
			String workingFileName;
			
			//The following arrays are used to represent the different directories
			String[] MYsubdir = new File(homeDir).list();//Project Directory
			String[] subdir = null; //
			String[] subsubdir = null;
			String[] tempArray = null;
			int arrCount=0;
			//Resize the project directory to remove anything that contains MASTER within the file name file.
			//This way there are only folders in the array
			int temp = MYsubdir.length;
			String[] newS= new String[temp-1];		
			for (int count=0;count<temp;count++){
				if(!MYsubdir[count].contains("MASTER")){
					newS[count]=MYsubdir[count];
				}
				else{
					arrCount++;
				}
			}
			tempArray = new String[temp-arrCount];
			for(int count=0;count<tempArray.length;count++){
				tempArray[count]=newS[count];
			}
			
			MYsubdir = tempArray;
			
			//////////////////////////////
			// loop through all languages
			for (int i=0; i<supportLangArr.length; i++) {
				curLang = supportLangArr[i];
				
				// 1. build locale hashmap
				logInfo("\n=================================================================");
				logInfo("\tSearch through properties file for language " + curLang + " ... ");
				logInfo("=================================================================");

				////////////////////
				// 1.1 build combined properties file of all messages file provided by developer
				outCombinedPropsFileName = targetDir + combinedPropsFilePrefix + curLang;
				workingFileName = workingPropertiesDir + combinedPropsFilePrefix + curLang + ".properties";
				logInfo("\n" + paddingStr + "Generating combined properties file: " + outCombinedPropsFileName + paddingStr);
				txtCombined = new FileWriter(new File(outCombinedPropsFileName+ ".properties"));
				outCombined = new PrintWriter(txtCombined);

				/////////////////////
 			    // 1.2 Specify the name of the property files (excluding the language extension)
                String[] curPropFileArray = null;
                String gwCurPropFilePrefix ="ApplicationMessages,ErrorMessageConstants,ErrorHandlingConstants,RequestFactoryConstants";
                String emCurPropFilePrefix ="messages,displaytag,jdbc,emQuartz,systemusers,role";
                String curPropFilePrefix ="messages,plugin,EditorMessages,ConstructedEditorMessages,quartz,build,encoding";
                String projPrefix = "";
                String curpropDir=null;
                
                /////////////////////
                //2.0 loop the project directory to create a list of all of the different projects
                //but only expand folders and not the MASTER.PROPERTIES file
                //counter is incremented by each of the new projects
               // for (int v=0;v<MYsubdir.length;v++){
                  	
                	//Secondary check to ensure that the MASTER.PROPERTIES file isn't included in the directory array 
                	//if (!MYsubdir[v].toString().contains("MASTER")){
                		
                		//initialize this array with the # of items in the destination (may or may not have files/folders)
                	//	subdir= new File(homeDir+MYsubdir[v].toString()).list();
                		                		
                		try{
                			 /////////////////////
                			//2.1 loop through the project directory.  This loop is required so that another counter can be used
	                		//must initialize a new variable subsubdir to account for the items being another directory
	                		//the GWP item contains another subdirectory to traverse
    	                	for (int j=0;j<MYsubdir.length;j++) {
    	                		
    	                		//initialize this array with the destinations items (used for sub sub directories)
    	                		subsubdir= new File(homeDir+MYsubdir[j].toString()).list();
    	         
    	                		    /////////////////////
    	                			//2.2  Determine the number of different types of property files located in each sub directory
    	                		    //     the GWP folder has another sub directory which requires a separate loop to traverse this directory
    	    						curpropDir = MYsubdir[j];
    	    						if(curpropDir.matches(".*gateway.*")){
    	    							projPrefix = "GW";
    	    							curPropFileArray=gwCurPropFilePrefix.split(","); 
    	    						}else if(curpropDir.matches(".*em-admin.*")){
    	    							projPrefix = "EMADMIN_";
    	    							curPropFileArray=curPropFilePrefix.split(",");
    	    						}else if(curpropDir.matches(".*em-mgmt.*")){
    	    							projPrefix = "EM_";
    	    							curPropFileArray=emCurPropFilePrefix.split(",");
    	    						}else{
    	    							//this option represents Gateway Pro projects
    	    							projPrefix = "";
    	    							curPropFileArray=curPropFilePrefix.split(",");
    	    						}
    	    						
    	    						//if the project is the GWP must loop through and expand all of the directories to get to the prop files
    	    						if(projPrefix == "" || projPrefix == "GW"){
    	    							/////////////////////
    	    							/*2.3 Loop through the sub directory of the GWP project.
    	    							 *    1. Update the subsubdir variable to represent the current number of folders in the sub directory
    	    							 *    2. Loop through and assign the number of property files. 
    	    							 *    4. assign the number of different types of property files and determine their expected location
    	    							 *    5. Read in from the file and write out to file
    	    							*/
    	    							if (projPrefix == ""){
	    	    							for(int count=0;count<subsubdir.length;count++){
	    	    								subsubdir = new File(homeDir+MYsubdir[j].toString()).list();
	    	    								for(int x=0;x<curPropFileArray.length;x++){
	    	    									
	    	    									curPropFile = curLang.equals("en")? curPropFileArray[x]+".properties" : 
	    	    									(curPropFileArray[x]+"_" + (curLang.equals("pt")? "pt_BR":curLang) + ".properties");
	    	    										try{
	    	    											curPropFileFullPath = GWPROmessagesDir + subsubdir[count].toString() + "\\" + curPropFile;
	    	    										}
	    	    										catch(Exception e){
	    	    											e.printStackTrace();
	    	    										}
	    	    				
	    	    									logInfo(paddingStr+" Proptery file Num: "+count+" - START of " + 
	    	    											curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ paddingStr+"\n");
	    	    									
	    	    									try{ 
	    	    										try{
	    	    											freader = new FileReader(new File(curPropFileFullPath));
	    	    										}catch (Exception e){
	    	    											logInfo(" #####################"+"\n"+ "No Proptery for file Num: "+count+" - START of " + 
	    	    													curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
	    	    											//e.printStackTrace();
	    	    										}
	    	    										
	    	    										lnreader = new LineNumberReader(freader);
	    	    										
	    	    										outCombined.print("##########################################################################\n");
	    	    										outCombined.print("## START of " + curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
	    	    										while (((line = lnreader.readLine()) != null)){
	    	    											//log(debugLog, "Line:  " + lnreader.getLineNumber() + ": " + line);
	    	    											if("".equalsIgnoreCase(line.trim()) || line.startsWith("#") || line.indexOf("=") == -1) {
	    	    												//continue;
	    	    											}else{
	    	    					
	    	    												key = projPrefix+line.split("=")[0];
	    	    												if (line.split("=").length>1){
	    	    													value = line.split("=")[1];
	    	    												}
	    	    												else{
	    	    													value = line;
	    	    												}
	    	    												value = value.replaceAll("&", "");
	    	    					
	    	    												logDebug("localeHm:[" + key + "=" + value + "]");
	    	    												localeHm.put(key, value);
	    	    					
	    	    												// also write to combined properties file
	    	    												outCombined.print(key + "=" + value + "\n");
	    	    												System.out.println("outCombined: "+key+"="+value);
	    	    											}
	    	    										}//end while
	    	    										logInfo(paddingStr+" Finished Searching Property file: "+ curPropFileFullPath + " "+
	    	    										lnreader.getLineNumber()+" lines "+paddingStr+"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+"\n");
	    	    										freader.close();
	    	    										lnreader.close();
	    	    											
	    	    										    //logInfo(paddingStr + "Searching Property file: " + curPropFileFullPath + paddingStr);
	    	    										}catch(IOException e){
	    	    											logInfo("FileReader threw IOException\n #####################\n");
	    	    										}catch(Exception e){
	    	    											try {
	    	    												Thread.sleep(1000);
	    	    											} catch (InterruptedException e1) {
	    	    												e1.printStackTrace();
	    	    											} 
	    	    											
	    	    											logInfo("Caught Expcetion"+e.toString());
	    	    											e.printStackTrace();
	    	    										}//end TRY/CATCH PROPERTY FILE INPUT
	    	    										
	    	    									}//end for proparray
	    	    								}//end for count<subsubdir
    	    							}// END condition if projPrefix == ""
    	    							else{
    	    								for(int count=0;count<subsubdir.length;count++){
	    	    								subsubdir = new File(homeDir+MYsubdir[j].toString()).list();
	    	    								for(int x=0;x<curPropFileArray.length;x++){
	    	    									
	    	    									curPropFile = curLang.equals("en")? curPropFileArray[x]+".properties" : 
	    	    									(curPropFileArray[x]+"_" + (curLang.equals("pt")? "pt_BR":curLang) + ".properties");
	    	    										try{
	    	    											curPropFileFullPath = GWmessagesDir + subsubdir[count].toString() + "\\" + curPropFile;
	    	    										}
	    	    										catch(Exception e){
	    	    											e.printStackTrace();
	    	    										}
	    	    				
	    	    									logInfo(paddingStr+" Proptery file Num: "+count+" - START of " + 
	    	    											curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ paddingStr+"\n");
	    	    									
	    	    									try{ 
	    	    										try{
	    	    											freader = new FileReader(new File(curPropFileFullPath));
	    	    										}catch (Exception e){
	    	    											logInfo(" #####################"+"\n"+ "No Proptery for file Num: "+count+" - START of " + 
	    	    													curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
	    	    											//e.printStackTrace();
	    	    										}
	    	    										
	    	    										lnreader = new LineNumberReader(freader);
	    	    										
	    	    										outCombined.print("##########################################################################\n");
	    	    										outCombined.print("## START of " + curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
	    	    										while (((line = lnreader.readLine()) != null)){
	    	    											//log(debugLog, "Line:  " + lnreader.getLineNumber() + ": " + line);
	    	    											if("".equalsIgnoreCase(line.trim()) || line.startsWith("#") || line.indexOf("=") == -1) {
	    	    												//continue;
	    	    											}else{
	    	    					
	    	    												key = projPrefix+line.split("=")[0];
	    	    												if (line.split("=").length>1){
	    	    													value = line.split("=")[1];
	    	    												}
	    	    												else{
	    	    													value = line;
	    	    												}
	    	    												value = value.replaceAll("&", "");
	    	    					
	    	    												logDebug("localeHm:[" + key + "=" + value + "]");
	    	    												localeHm.put(key, value);
	    	    					
	    	    												// also write to combined properties file
	    	    												outCombined.print(key + "=" + value + "\n");
	    	    												System.out.println("outCombined: "+key+"="+value);
	    	    											}
	    	    										}//end while
	    	    										logInfo(paddingStr+" Finished Searching Property file: "+ curPropFileFullPath + " "+
	    	    										lnreader.getLineNumber()+" lines "+paddingStr+"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+"\n");
	    	    										freader.close();
	    	    										lnreader.close();
	    	    											
	    	    										    //logInfo(paddingStr + "Searching Property file: " + curPropFileFullPath + paddingStr);
	    	    										}catch(IOException e){
	    	    											logInfo("FileReader threw IOException\n #####################\n");
	    	    										}catch(Exception e){
	    	    											try {
	    	    												Thread.sleep(1000);
	    	    											} catch (InterruptedException e1) {
	    	    												e1.printStackTrace();
	    	    											} 
	    	    											
	    	    											logInfo("Caught Expcetion"+e.toString());
	    	    											e.printStackTrace();
	    	    										}//end TRY/CATCH PROPERTY FILE INPUT
	    	    										
	    	    									}//end for proparray
	    	    								}//end for count<subsubdir
    	    							}
    	    						}else if(projPrefix == "EM_"){
    	    							
						    	    	/////////////////////
						    	    	/*2.4 The project is !GWP and therefore the project sub directory !contain another subdirectory 
						    	    	 *    and only contains property files
						    	    	 *      1. Loop through and assign the number of different types of property files and determine 
    	    							 *         their expected location
    	    							 *      2. Read in from the file and write out to file
						    	    	 */
    	    							 for(int x=0;x<curPropFileArray.length;x++){
    	    								
    	    									curPropFile = curLang.equals("en")? curPropFileArray[x]+".properties" : 
    	    									(curPropFileArray[x]+"_" + (curLang.equals("pt")? "pt_BR":curLang) + ".properties");
    	    								
    	    									
    	    									curPropFileFullPath = homeDir + curpropDir + "\\" + curPropFile;
    	    									
   			
    	    									logInfo(paddingStr+" curPropFileArray Num: "+x+" - START of " + 
    	    											curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ paddingStr);
	
    	    									try{   
    	    										freader = new FileReader(new File(curPropFileFullPath));
    	    										try{
    	    											freader = new FileReader(new File(curPropFileFullPath));
    	    										}catch (Exception e){
    	    											logInfo(" #####################" +"\n"+"No Proptery for file Num: "+x+" - START of " + 
    	    													curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
    	    											//e.printStackTrace();
    	    										}
    	    										lnreader = new LineNumberReader(freader);
    	    										
    	    										outCombined.print("##########################################################################\n");
    	    										outCombined.print("## START of " + curPropFileFullPath.substring(curPropFileFullPath.indexOf("messages"))+ " ##\n");
    	    										while ((line = lnreader.readLine()) != null){
    	    											//log(debugLog, "Line:  " + lnreader.getLineNumber() + ": " + line);
    	    											if("".equalsIgnoreCase(line.trim()) || line.startsWith("#") || line.indexOf("=") == -1) {
    	    												//continue;
    	    											}else{
    	    					
    	    												key = projPrefix+line.split("=")[0];
    	    												value = line.split("=")[1];
    	    												value = value.replaceAll("&", "");
    	    					
    	    												logDebug("localeHm:[" + key + "=" + value + "]");
    	    												localeHm.put(key, value);
    	    					
    	    												// also write to combined properties file
    	    												outCombined.print(key + "=" + value + "\n");
    	    												System.out.println("outCombined: "+key+"="+value);
    	    											}
    	    										}//end while
    	    										logInfo(paddingStr+" Finished Searching Property file: "+ curPropFileFullPath + 
    	    												" "+lnreader.getLineNumber()+" lines "+paddingStr+"\n\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\"+"\n");
    	    										freader.close();
    	    										lnreader.close();
    	    											
    	    										    //logInfo(paddingStr + "Searching Property file: " + curPropFileFullPath + paddingStr);
    	    										}catch(IOException e){
    	    											logInfo("FileReader threw IOException\n#####################\n");
    	    										}catch(Exception e){
    	    											try {
    	    												Thread.sleep(1000);
    	    											} catch (InterruptedException e1) {
    	    												e1.printStackTrace();
    	    											} 
    	    											
    	    											logInfo("Caught Expcetion"+e.toString());
    	    									}//end TRY/CATCH PROPERTY FILE INPUT
    	    								}//end FOR LOOP Property File Array
    	    							}//end ELSE for project folder
    	                			}//end MYsubdir FOR LOOP with "j" counter
    						
    	                	// some properties missed, especially for english
    	                	// addMissedProperties(outCombined, curLang);
    		
    						// 3. Write generated properties file based on MASTER.PROPERTIES
    	                	//    (make sure you append to file and !overwrite the file)
    						outFileName = targetDir + outFileNamePrefix + curLang + ".properties";
    						logInfo("\n" + paddingStr + "Generating properties file: " + outFileName + paddingStr);
    						txt = new FileWriter(new File(outFileName),true);
    						out = new PrintWriter(txt,true);
    		
    						// 3.1 Write properties required by master property file 
    		 				for (Entry<String, String> entry : masterHm.entrySet()) {
    							key = entry.getKey();
    							value = localeHm.get(key);
    		
    							if (value != null && ! "".equalsIgnoreCase(value)) {
    								value = value.replaceAll("&", "");
    							} else {
    								// use masterHm default value
    								value = entry.getValue();
    								//logWarning("No property found for [" + key 
    								//+ "] ==> [" + value + "] (set to default by masterFile)");
    							}
    							logDebug("write2File:[" + key + "=" + value + "]");
    							out.print(key + "=" + value + "\n");
    						}
    		
    						// 3.2 append more key/value for keyboard shortcuts
    						appendShortcuts(outCombined, curLang);
    						addMissedProperties(outCombined, curLang);
    						appendTranslations(outCombined, curLang);
    						out.close();
    						logInfo("\nWell done!");

                		} catch (IOException e) {
                			logError("\n!!!!!! Too bad, something wrong while generating property file! !!!!!!\n");
                			e.printStackTrace();			
                		}//end TRY/CATCH of outer most loop MYsubdir
                	//}//end IF !contains MASTER for outermost loop MYsubdir 
                //	else{
               // 		logInfo("\nYour MYsubdir[v] contains 'MASTER'!");
                //		break;
               // 	}
               // }//end MYsubdir FOR loop, base loop of the main directory
                
                ////////////////////
                //4.0 Now Close all writing streams
                txtCombined.close();
				outCombined.close();
				String files[] = {workingFileName,outCombinedPropsFileName};
                
			}//end FOR LOOP of supported languages
		}catch (IOException e) {
				logError("\n!!!!!! Too bad, something wrong while generating property file! !!!!!!\n");
				e.printStackTrace();			
			}//END outer most TRY/CATCH 
	
	}//end MAIN


	
	
	/// *****************other files *******************
	private static void addMissedProperties(PrintWriter outCombined, String curLang) {
		logWarning("Hard code missing properties, need ask developer to add it or ignore if not used by localization");
		logWarning("Check '## START of MISSING Part ##' for list ...");
		
		outCombined.print("###########################\n");
		outCombined.print("## START of MISSING Part ##\n");

		if ("en".equalsIgnoreCase(curLang)) {
			outCombined.print("item=Item\n");
			//outCombined.print("item_bigContainer=Engagement\n");
			//outCombined.print("item_littleContainer=Activity\n");
			outCombined.print("titleSearchResourceDialog=Find\n");
			outCombined.print("value=Value\n");
		}
		
		outCombined.print("repositoryMenu=Repository\n");
		outCombined.print("operation_Cancel=Cancel\n");
		outCombined.print("Context=Context\n");
		outCombined.print("Lookin=Look in:\n");
		outCombined.print("labelFileName=File Name\n");
		outCombined.print("OK=OK\n");
		outCombined.print("YesToAll=Yes To All\n");
		outCombined.print("UnlockAll=Unlock All\n");
		outCombined.print("Folder=Folder:\n");
		//outCombined.print("RemoveUser=Remove User \n");
		outCombined.print("Application=Application\n");
		outCombined.print("Admin=Admin \n");
		//outCombined.print("Next=Next >\n");
		outCombined.print("titleDeleteUsersAndGroupsDialog=Manager Users\n");
		outCombined.print("RunCtrlR=Run	Ctrl+R\n");
		outCombined.print("ScheduleCtrlShiftR=Run Ctrl+Shift+R\n");
		outCombined.print("Choosetable=Choose table: \n");
		outCombined.print("More=More\n");
		outCombined.print("analyticsHostName=Server\n");
	}

	private static final HashMap<String,String> enShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%ec"); 				//Edit - Copy
			put("scCut","%et"); 				//Edit - Cut
			put("scPaste","%ep"); 				//Edit - Paste
			put("scNewEng","%f%w%e");			//File - New - Engagement
			put("scNewAct","%f%w%a");			//File - New - Activity
			put("scNewResult","%f%w%r"); 		//File - New - Result
			put("scRename","%fm");				//File - Rename
			put("scDiscon", "%f%s");			//Repository - Disconnect
			put("scDel", "%f%d{ENTER}");		//File - Delete
			put("scImport", "%fi");				//File - Import		
			put("scExport", "%fe");				//File - Export
			put("scAnaRun", "^r");				//Anakytics - Run
			put("scAnaSch", "^+r");				//Anakytics - Schedule
			put("scAnaNew","%f%w%s");			//@Script editor: File - new - script

		}
	};

	private static final HashMap<String,String> enTranslations = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("SecurityAlertWindow_TITLE","Security Alert");
			put("FileMenuEditScripts", "Edit Scripts");
			put("windowScriptEditor", "Script Editor - .*");
			put("Next", "Next >");
			put("CopyOfLabel", "Copy of");
			
		}
	};
	
	
	private static final HashMap<String,String> frShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%cc"); 		
			put("scCut","%ec");		
			put("scPaste","%eo");
			put("scNewEng","%f%n%e");	
			put("scNewAct","%f%n%a");	
			put("scNewResult","%f%n%r");
			put("scRename","%fm");		
			put("scDiscon", "%f%d");	
			put("scDel", "%f%s");	
			put("scImport", "%fi");		
			put("scExport", "%fe");
			put("scAnaRun", "^r");				
			put("scAnaSch", "^+r");		
			put("scAnaNew","TBD");			//@Script editor: File - new - script
		}
	};
	
	private static final HashMap<String,String> frTranslations = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("SecurityAlertWindow_TITLE","Alerte de s\\u00E9curit\\u00E9");
			put("FileMenuEditScripts", "Modifier des scripts");
			put("windowScriptEditor", "\\u00C9diteur de script - .*");
			put("Next", "Suivant >");
			put("CopyOfLabel", "Copie de");
			
		}
	};
	

	private static final HashMap<String,String> esShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%ec"); 		
			put("scCut","%et");		
			put("scPaste","%ep");
			put("scNewEng","%a%n%m");	
			put("scNewAct","%a%n%a");	
			put("scNewResult","%a%n%r");
			put("scRename","%amm{ENTER}");		
			put("scDiscon", "%a%d");	
			put("scDel", "%a%m{ENTER}");	
			put("scImport", "%ai");		
			put("scExport", "%ae");
			put("scAnaRun", "^r");				//
			put("scAnaSch", "^+r");		
			put("scAnaNew","TBD");			//@Script editor: File - new - script
		}
	};

	private static final HashMap<String,String> esTranslations = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("SecurityAlertWindow_TITLE","Alerta de seguridad");
			put("FileMenuEditScripts", "Editar scripts");
			put("windowScriptEditor", "Editor de scripts - .*");
			put("Next", "Siguiente >");
			put("CopyOfLabel", "Copia de");
			
		}
	};
		
	
	private static final HashMap<String,String> deShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%bk"); 		
			put("scCut","%b{ENTER}");	//Edit - Cut (walk around for bug)
			//put("scCut","%ba"); //Edit - Cut (should be this one)
			//for cut should use the et to ba conversion below, but current theres's a bug
			//in the app that the mnemonic is wrong, so select the first item from edit menu instead
			//myStringToConvert = myStringToConvert.replace("scCut","%ba");
			put("scPaste","%be");
			put("scNewEng","%d%n%e");	
			put("scNewAct","%d%n%a");	
			put("scNewResult","%d%n%e");
			put("scRename","%du");		
			put("scDiscon", "%d%v");	
			put("scDel", "%d%l");	
			put("scImport", "%di");		
			put("scExport", "%de");
			put("scAnaRun", "^r");				//
			put("scAnaSch", "^+r");		
			put("scAnaNew","TBD");			//@Script editor: File - new - script
		}
	};
	
	private static final HashMap<String,String> deTranslations = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("SecurityAlertWindow_TITLE","Sicherheitshinweis");
			put("FileMenuEditScripts", "Skripts bearbeiten");
			put("windowScriptEditor", "Skript-Editor - .*");
			put("Next", "Weiter >");
			put("CopyOfLabel", "Kopie von");
			
		}
	};
	
	private static final HashMap<String,String> ptShortcuts = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("scCopy","%ec"); 		
			put("scCut","%et");		
			put("scPaste","%ec");
			put("scNewEng","%a{DOWN}va");	
			put("scNewAct","%a{DOWN}va");	
			put("scNewResult","%a{DOWN}vr");
			put("scRename","%a{DOWN}m");		
			put("scDiscon", "%a%d");	
			put("scDel", "%a{DOWN}e");	
			put("scImport", "%a{DOWN}i");		
			put("scExport", "%a{DOWN}e");
			put("scAnaRun", "^r");				//
			put("scAnaSch", "^+r");	
			put("scAnaNew","TBD");			//@Script editor: File - new - script
		}
	};
	
	private static final HashMap<String,String> ptTranslations = new HashMap<String,String>()
	{
		private static final long serialVersionUID = 1L;
		{
			put("SecurityAlertWindow_TITLE","Alerta de seguran\\u00E7a");
			put("FileMenuEditScripts", "Editar scripts");
			put("windowScriptEditor", "Selectionar o destino da importa\\u00E7\\u00E3o- .*");
			put("Next", "Avan\\u00E7ar >");
			put("CopyOfLabel", "C\\u00F3pia de");
			
		}
	};
	
	private static void appendShortcuts(PrintWriter out, String langStr) {
		out.print("############################\n");
		out.print("## START of SHORTCUT Part ##\n");
		
		HashMap<String,String> shortcutsMap=null;

		if ("en".equalsIgnoreCase(langStr)) {
			shortcutsMap = enShortcuts;
		} else if ("fr".equalsIgnoreCase(langStr)) {
			shortcutsMap = frShortcuts;
		} else if ("es".equalsIgnoreCase(langStr)) {
			shortcutsMap = esShortcuts;
		} else if ("de".equalsIgnoreCase(langStr)) {
			shortcutsMap = deShortcuts;
		} else if ("pt".equalsIgnoreCase(langStr)) {
			shortcutsMap = ptShortcuts;
		} else if ("bg".equalsIgnoreCase(langStr)) {
			logWarning("No keyboard shortcuts mapping for bg till now!");
			return;
		}

		// write to target property file
		for (Entry<String, String> entry : shortcutsMap.entrySet()) {
			out.print(entry.getKey() + "=" + entry.getValue() + "\n");
		}
	}//end append shortcuts
	
	private static void appendTranslations(PrintWriter out, String langStr) {
		out.print("############################\n");
		out.print("## START of MISSING TRANSLATION Part - Author: " +
				   "David Carlson, started March.16,2011  ##\n");
		
		HashMap<String,String> propertyMAP=null;
		
		if ("en".equalsIgnoreCase(langStr)) {
			propertyMAP = enTranslations;
		} else if ("fr".equalsIgnoreCase(langStr)) {
			propertyMAP = frTranslations;
		} else if ("es".equalsIgnoreCase(langStr)) {
			propertyMAP = esTranslations;
		//	propertyMAP = esTranslations;
		} else if ("de".equalsIgnoreCase(langStr)) {
			propertyMAP = deTranslations;
		} else if ("pt".equalsIgnoreCase(langStr)) {
			propertyMAP = ptTranslations;
		} else if ("bg".equalsIgnoreCase(langStr)) {
			//propertyMAP = bgShortcuts;
			logWarning("Translations not added yet!");
			return;
		}
		
		// write to target property file
		for (Entry<String, String> MYentry : propertyMAP.entrySet()) {
			out.print(MYentry.getKey() + "=" + MYentry.getValue() + "\n");
		}
	}//end append shortcuts
	
	
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