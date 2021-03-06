package lib.acl.util;
//
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

//import ACL_Desktop.conf.beans.ProjectConf;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.ObjectHelper;
import lib.acl.helper.sup.TAFLogger;
import lib.acl.helper.sup.RFTGuiFinderHelper;
import conf.beans.LoggerConf;

public class NLSUtil {
    public static Locale appLocale = null;
	// *** Get Property ID ***
	private static String baseName = lib.acl.helper.sup.LoggerHelper.localizationDir+
	                                 lib.acl.helper.sup.LoggerHelper.projectName;
	
	private static ResourceBundle resBundle = null,resBundleEn=null;

    private static Properties enProps = null,curProps=null;
    private static String enFile = null,curFile=null;
    private static Set<String> idSet = null;
    private static String[] splitPattern = {" - ", " - "};
	public static String excludeList="Context"+
                                     "|File name:"+
                                     "|Files of type:"+
                                     "|Look in:"+
                                     "|Open|Save|Cancel"+
                                     "";
	public  static   	String idPattern = "lv_.*";
	public static String noTranslation = "Not_Found_lv";
	// convert "myString" to String set by current locale

	public static String _convert2Locale(String myString){
		return _convert2Locale(myString,"");
	}
    public static String _convert2Locale(String myString,String className) {
    	return _convert2Locale(myString,className,true);
    }
    public static String _convert2Locale(String myString,String className, boolean trucate) {
    	String oriString = myString;
    	String pre = "SingleLocValue_";    // Not values, just value!!!
        boolean singleValue = false;
    	
    	if(myString.startsWith(pre)){
    		myString = myString.replaceFirst(pre, "");
    		
    	}else{
    		pre = "";
    		
    	}
    	
    	if(
    			(!myString.matches(idPattern))
    			&&(appLocale.getLanguage().equalsIgnoreCase("en"))){
    		return myString;
    	}
    	

    	enFile = baseName+"_en.properties";
    	if(resBundle==null){
			resBundle = getUTF8Bundle(baseName,appLocale);
			//resBundle = getUTF8Bundle(enFile,appLocale);
		}
    	
    	//String lstring = _convert2Locale(resBundle,getPropID(enFile,pre+myString,className));
    	String lstring = _convert2Locale(resBundle,getPropID(enFile,pre+myString,className));
        if(!lstring.contains(noTranslation)){
        	return lstring = addExp(lstring,oriString,true);
        }else{
        	lstring = lstring.replaceAll(noTranslation, myString);
        
        }
    	if(
    			(lstring.equals(myString)||
    			     (RFTGuiFinderHelper.isValidPattern(myString)&&lstring.matches(myString)))){
    		
    	    String temp = "",temp1;
    	    
    		for(int i=0;i<splitPattern.length;i=i+2){
    			if(myString.contains(splitPattern[i])||myString.matches(".*"+splitPattern[i]+".*")){
    				
    				String[] myStrings = myString.split(splitPattern[i]);
    			   for(String part:myStrings){
    				   if(part.trim().equals(""))
    					   continue;
    				   temp1 = _convert2Locale(resBundle,getPropID(enFile,pre+part,className));   
    				   if(temp1.contains(noTranslation))
   			        	  temp1=temp1.replaceAll(noTranslation,part);
       			       temp1 = addExp(temp1,part,true);    			       
//  			           if(temp1.equals("")){
//  			    	       temp1 = part;
//  			           }
  			    	   if(""!=temp1){
  			    		   if(temp.equals(""))
  			    			   temp = temp1;
  			    		   else   
  			    			 temp = combineExp(temp,splitPattern[i+1],temp1);
  			    			  // temp += splitPattern[i+1]+temp1;
  			    	   }
                     temp1 = "";
    			   }//End of inner for loop
    			}//End of if
    		}//End of outer for loop
    
    		lstring = temp;
    	}else{
    	   lstring = addExp(lstring,myString,trucate);
    	}
    	
       	if(lstring.equals("")){ // In case
     	   lstring = myString;
     	}
//       	if(RFTGuiFinderHelper.isPattern(myString))
//       		lstring = RFTGuiFinderHelper._correctPattern(lstring);
       return lstring;
       //return _convert2Locale(resBundle,getPropID(enFile,myString),className);
    }
    public static String _convert2English(String myString){
    	return _convert2English(myString,"");
    }
    public static String _convert2English(String myString,String className) {
    	return _convert2English(myString,className,true);
    }
    public static String _convert2English(String myString,String className,boolean truncate) {
    	
    	
    	String pre = "SingleLocValue_";
    	if(myString.startsWith(pre)){
    		myString = myString.replaceFirst(pre, "");
    	}else{
    		pre = "";
    	}
    	String oriString = myString;
    	if((!myString.matches(idPattern))&&(appLocale.getLanguage().equalsIgnoreCase("en"))){
    		return myString;
    	}
    	
    	curFile = baseName+"_"+appLocale.getLanguage()+".properties";    
    	if(resBundleEn==null){
			resBundleEn = getUTF8Bundle(baseName,Locale.ENGLISH);
			//resBundleEn = getUTF8Bundle(curFile,Locale.ENGLISH);
		}
    	LoggerHelper.logTAFDebug("  Translating - '"+myString+"'");
    	String lstring = _convert2Locale(resBundleEn,getPropID(curFile,pre+myString,className,true));
    	if(myString.contains("(.prf)"))
    			LoggerHelper.logTAFDebug("  Translated - '"+myString+"' -> '"+lstring+"'");
        if(!lstring.contains(noTranslation)){
        	return lstring = addExp(lstring,oriString,truncate);
        }else{
        	lstring = lstring.replaceAll(noTranslation, myString);
        
        }
    	if(lstring.equals(myString)||
    			(RFTGuiFinderHelper.isValidPattern(myString)&&lstring.matches(myString))){
    	    String temp = "",temp1;
    		for(int i=0;i<splitPattern.length;i=i+2){
    			if(myString.contains(splitPattern[i])||myString.matches(".*"+splitPattern[i]+".*")){    	
    				String[] myStrings = myString.split(splitPattern[i]);
    			   for(String part:myStrings){
    				   LoggerHelper.logTAFDebug("---------  Translating part- '"+part+"'");
    				   temp1 = _convert2Locale(resBundleEn,getPropID(curFile,pre+part,className,true));   
    				   LoggerHelper.logTAFDebug("---------  Translated part- '"+part+"' -> '"+temp1+"'");
    			        if(temp1.contains(noTranslation))
    			        	temp1=temp1.replaceAll(noTranslation,part);
       			       temp1 = addExp(temp1,part,true);    			       
  			           
  			    	   if(""!=temp1){
  			    		   if(temp.equals(""))
  			    			   temp = temp1;
  			    		   else {   
  			    			   temp = combineExp(temp,splitPattern[i+1],temp1);
  			    			   //temp += splitPattern[i+1]+temp1;
  			    		   }
  			    	   }
                     temp1 = "";
    			   }//End of inner for loop
    			}//End of if
    		}//End of outer for loop
    		lstring = temp;
    	}else{
    	   lstring = addExp(lstring,oriString,truncate);
    	}
       	if(lstring.equals("")){ // In case
      	   lstring = myString;
      	}  
//		 if(RFTGuiFinderHelper.isPattern(myString)){
//			 lstring = RFTGuiFinderHelper._correctPattern(lstring);
//		 }
       return lstring;
    }
    public static String combineExp(String first,String conn,String second){
    	String[] secondArray = second.split("\\|");
    	String exp = "";
    	for(int i=0;i<secondArray.length;i++){
    		if(i==0)
    			exp = first + conn + secondArray[i];
    		else
    		    exp += "|"+ first+ conn + secondArray[i];
    	}
    	return exp;
    }
    public static String addExp(String lstring,String oriString,boolean truncate){
//    	String[] replacement = {
//    			                "%s",".*",
//    			                "\\.{2,}([*+])",".$1"};
    	String[] patt ={".*",".+"};
    	String rep = ".*";
    	boolean done = false;
    	

    	
    	for(int i=0;i<patt.length&&!done;i++){
    	 if(oriString.startsWith(patt[i])){
			String temp[] = lstring.split("\\|");
			lstring = "";
			for(String st:temp){
			   if(truncate&&st.length()> 100){
				    st = st.substring(0,100);
					//st = RFTGuiFinderHelper.correctPattern(st);
			   }
			   
			   st = RFTGuiFinderHelper.correctPattern(st);
			   st = rep+st;
			   if(lstring.equals(""))
			      lstring = st;
			   else
				   lstring += "|"+st;
		    }
			
			done = true;
		
    	 }
    	}
    	done=false;
    	for(int i=0;i<patt.length&&!done;i++){
    		if(oriString.endsWith(patt[i])){
    			String temp[] = lstring.split("\\|");
    			lstring = "";
    			for(String st:temp){
    				if(truncate&&st.length()> 100){
    					  st = st.substring(0,100);
    	                  //st = RFTGuiFinderHelper.correctPattern(st.substring(0,100));
    				   }
    				st = RFTGuiFinderHelper.correctPattern(st);
    			   st += rep;
    			   if(lstring.equals(""))
    			      lstring = st;
    			   else
    				   lstring += "|"+st;
    		    }
    			
    			done = true;
    		}
    	}
    	
//    	for(int i=0;i<replacement.length;i=i+2){
//    		lstring.replaceAll(replacement[i], replacement[i+1]);
//    	}
    	return lstring;
    }
//	public  static String _convert2Locale(ResourceBundle rb,String myString) {
//		return _convert2Locale(rb,myString,"");
//	}
	public  static String _convert2Locale(ResourceBundle rb,String myString){//, String className) {
		String conv = "",temp = "";
    	if(myString.contains("ACL 기본 설정 파일을(.prf)")||
    			myString.contains("lv_ACLTXTG_RH_MISC_008_ID")){
		    myString = myString;
	    }
		if (myString == null)
			return myString;
		String[] ms = myString.split("\\|");
		try {			
			if (myString != null && ! "".equalsIgnoreCase(myString)) {
				
				for(String id:ms){
					if(!id.matches(idPattern)){
						temp = id;
					}else{
					    temp = rb.getString(id);
					}
			        if(temp==null||temp==""||temp.matches("^([\\s]*['\"(\\[]?%[\\d]*[l]?[Icds]['\")\\]]?[\\s]*)*"))
			        	continue;
					if(conv.equals("")){
						conv = temp;
					}else if(!conv.equals(temp)&&
							!conv.startsWith(temp+"|")&&
							!conv.endsWith("|"+temp)&&
							!conv.contains("|"+temp+"|")){
						conv += "|"+temp;
					}
					}
				
			} else {
				LoggerHelper.logTAFDebug("\tWarning: No ResourceBundle key provided/found!");
			}
		} catch (Exception e) {		
			LoggerHelper.logTAFDebug("Exception found "+e.toString());
					
			LoggerHelper.logTAFDebug("\tNo ResourceBundle setting for [" + myString + "]; [" + myString + "] used instead");
		}
        
		
        if(conv==null||conv==""||conv.matches("^([\\s]*['\"(\\[]?%[\\d]*[l]?[Icds]['\")\\]]?[\\s]*)*"))
        	//conv = myString;
            conv = myString;
        
        //   conv = RFTGuiFinderHelper.correctPattern(conv);
        
        return conv;
		//return conv.replaceAll("%s", ".+");
	}

	public static String convert2English(String myString) {
		return convert2English(myString,"");
	}
	public static String convert2Locale(String myString) {
	  return convert2Locale(myString,"");
	}
	public static String convert2English(String myString,String className) {
		String pattern = "(.+)(->)"+                  // For path
        "|(.+)(\\|)"+                // For Regular Exp
        "|(.+)([\\s]+-[\\s])";      // For ACL title separated by '-
		String template =  ".+->.+"+                  // For path
        "|.+\\|.+"+                // For Regular Exp
        "|.+[\\s]+-[\\s]+.+";      // For ACL title separated by '-
		if(myString.matches(template))
		    return i18nReplaceAll(true,myString,className,pattern,1,2);
		else
			return _convert2English(myString,className);
	}
	public static String convert2Locale(String myString, String className) {
		//String pattern = "(.+)(->)|(.+)(\\||(.+)([\\s]+-[\\s]))";
		String template =  ".+->.+"+                  // For path
        "|.+\\|.+"+                // For Regular Exp
        "|.+[\\s]+-[\\s]+.+";      //
		String pattern = "(.+)(->)"+                  // For path
		                 "|(.+)(\\|)"+                // For Regular Exp
		                 "|(.+)([\\s]+-[\\s])";      // For ACL title separated by '-'
		if(myString.matches(template))
		    return i18nReplaceAll(myString,className,pattern,1,2);
		else
			return _convert2Locale(myString,className);
		}

		public static String i18nReplaceAll(String value,String patt){
			  return i18nReplaceAll(false,value,patt);
			}
		
		public static String i18nReplaceAll(boolean reverse,String value,String patt){
			  return i18nReplaceAll(reverse,value,patt,1,-1);
			}
		public static String i18nReplaceAll(String value,String patt,int convert,int keep){
			  return i18nReplaceAll(false,value,patt,convert, keep);
			}
		public static String i18nReplaceAll(String value,String className,String patt,int convert,int keep){
			  return i18nReplaceAll(false,value,className,patt,convert,keep);
			}
		public static String i18nReplaceAll(boolean reverse,String value,String patt,int convert,int keep){
		  return i18nReplaceAll(reverse,value,"",patt,convert,keep);
		}
			public static String i18nReplaceAll(boolean reverse,String value,String className,String patt,int convert,int keep){
				String convertedString = "",convertedStr = "";
				String tmpConvertedStr;
                String pat1="->",
                        //pat2="\\s-\\s",
                       pat="\\|",rep="|";
                String temp = "";
				//logDebug("getLocaleStrings::origPropValueTexts: " + origPropValueTexts);
//            	if(value.contains("ACL Wizard Error")){
//        			value = value;
//        		}
				for (String curStr1 : value.split(pat1)) {
   				     for(String curStr : curStr1.split(pat)){
							if(!reverse){
								temp = _convert2Locale(curStr,className);
							}else{
								temp = _convert2English(curStr,className);
							}		
							if(convertedString.equals("")){
							   convertedString = temp;
							}else if(!temp.equals("")){
							   convertedString += rep+temp;
							}
						}
					if(convertedStr.equals(""))
						   convertedStr = convertedString;
					else
					   convertedStr += pat1+convertedString;
			     }
//				 if(RFTGuiFinderHelper.isPattern(convertedStr)){
//					 convertedStr = RFTGuiFinderHelper.correctPattern(convertedStr);
//				 }
			     LoggerHelper.logTAFDebug("i18nReplaceAll for '"+value+"': "+convertedStr);

           return convertedStr;
			}	


	public static String getPropID(String file,String sValue, String cName){   
	     return getPropID(file,sValue,cName,false);   
	}
    public static String getPropID(String file,String sValue){    	
       return getPropID(file,sValue,false);
    }

	public static String getPropID(String file,String sValue,boolean reverse){
	  return  getPropID(file,sValue,"",reverse);
	}
	public static String getPropID(String file,String sValue,String cName,boolean reverse){
				
		return getPropID(file,sValue,cName,reverse,RFTGuiFinderHelper.isPattern(sValue));
	}
	public static String getPropID(String file,String sValue, boolean reverse,boolean isPattern){
		return getPropID(file,sValue,"",reverse,isPattern);
	}
	public static String getClassKey(String cName){
		String classKey = "";
		String prefix = ".+_";
		String suffix = "_.+";
		String[] ckArray = {
				// Win classes
//				  "[\\.]?Radiobutton|[\\.]?Checkbutton",prefix+"CONTROL"+suffix,
//				  "[\\.]?Pushbutton|[\\.]?Button",prefix+"PUSHBUTTON"+suffix+
//				            "|"+prefix+"DIFPUSHBUTTON"+suffix,
//				  "[\\.]?Statictext",prefix+"[LCR]TEXT"+suffix,				  
//				  "[\\.]?Menuitem",prefix+"MENUITEM"+suffix,
//				  //"[\\.]?Menupopup",prefix+"POPUP"+suffix,
//				  "[\\.]?ComboBox",prefix+"GROUPBOX"+suffix,
//				  "[\\.]?Edit",prefix+"[LCR]TEXT"+suffix,
				
//				  "#32770",prefix+"POPUP"+suffix+
//				       "|"+prefix+"CAPTION"+suffix,
		          };
		for(int i=0;i<ckArray.length-1;i=i+2){
			if(cName.matches(ckArray[i])){
				return classKey=ckArray[i+1];
			}
		}
		return classKey;
	}
	public static String getPropID_working(String file,String sValue, String cName,boolean reverse,boolean isPattern){
		String key = noTranslation;
		String keys="";
		String value = "";
		String temp = null;
		Properties pr = null;
		boolean noTrans = false;
        boolean single = false;
    	String oriValue = sValue;
    	
    	String pre = "SingleLocValue_";
    	if(sValue.startsWith(pre)){
    		sValue = sValue.replaceFirst(pre, "");
    		single = true;
    	}else{
    		pre = "";
    		single = false;
    	}
        
    	if(sValue.matches(idPattern)){
    		return sValue;
    	}
		//boolean validPattern = true;

    	String classKey = getClassKey(cName);
		if(!reverse){
    		if(enProps==null||enFile==null
//    				||!file.equals(enFile)
    				){
		    enProps = loadProperties(file);
		    enFile = file;
		    idSet = enProps.stringPropertyNames();
		     }	
    		pr = enProps;
		}else{
    		if(curProps==null||curFile==null
//    				||!file.equals(curFile)
    				){
    		    curProps = loadProperties(file);
    		    curFile = file;
    		    idSet = curProps.stringPropertyNames();
    		     }	
        		pr = curProps;
		}
		//LoggerHelper.logTAFInfo(+idSet.size()+"props in '"+file+"' loaded");
		
		String sValueTemp = "";
		int numIds = idSet.size(),curNum=0;
		if(!sValue.endsWith("$")){
			sValueTemp = sValue;
		}else{
			//sValueTemp = sValue.replaceAll("([\\s]['\"]?)%[\\d]*[l]?[Icds](['\"]?)", "$1.*$2");
			sValueTemp = RFTGuiFinderHelper.correctPattern(sValue);
			//sValueTemp = sValueTemp.replaceAll("\\n|\\r","/");
		}
		
		for(String id:idSet){
			
			        	
			temp = pr.getProperty(id);

			if(temp==null){
				temp="";
				LoggerHelper.logTAFDebug("'"+id+" == null?'");
				continue;
			}
			temp = temp.trim();
			
//			if(sValue.contains(debugString)&&id.contains("lv_ACLTXT_RH_SX_750_ID")){
//				LoggerHelper.logTAFWarning("'"+sValue+" ?=\n\t '"+temp+"'");
//			}
			if(sValueTemp.equals(sValueTemp.toUpperCase())||
			    temp.equals(temp.toUpperCase()))
				noTrans = true; // No translation for all UPPERCASE English!!!
			if(
					(!noTrans
							&&(temp.equals(sValue)||temp.equals(sValueTemp)) // Value equal
							)
					&&(classKey.equals("")||id.matches(classKey)) // Class equal
					){	
				
				if(keys!="")
					   keys += "|"+id;
					else
						keys = id;
				if(single)
			         return id;
			}else{ // Pattern match
				try{					
					Pattern.compile(RFTGuiFinderHelper.correctPattern(sValue)+"|"+sValueTemp);
					isPattern = true;
				}catch(Exception e){
					//System.err.println(e.toString());
					isPattern = false;
				}
				
				if(
						(temp.equalsIgnoreCase(sValueTemp)
								||temp.equalsIgnoreCase(sValueTemp+":")
								||(temp+":").equalsIgnoreCase(sValue))
						||(isPattern&&temp.replaceAll("\\n|\\r","/").matches(
								RFTGuiFinderHelper.correctPattern(sValue)+"|"+sValueTemp))){
					
            // *** Option 1, get shortest trans
//					if(value.equals("")||temp.length()<value.length()){
//						value = temp;
//						key = id;
//					}
					
			//*** Option 2, get all possible matches
					if(!key.equals(noTranslation)&&key!="")
						   key += "|"+id;
					else
						   key = id;
//					if(single)
//				         return id;
			    }
			}
		}
		if(keys!=""){
			key = keys;
		}
//		if(key==""){
//			key = sValue;
//			if(single){
//				key = pre+key;
//			}
//		}

//		LoggerHelper.logTAFDebug("Property '"+key+"' = '"+sValue+"'");
		return key;
	}
	
	public static String getPropID(String file,String sValue, String cName,boolean reverse,boolean isPattern){
		String key = noTranslation;
		String key_r="";
		String keys="";
		String temp = null;
		Properties pr = null;
		
        boolean single = false;
   	
    	String pre = "SingleLocValue_";
    	if(sValue.startsWith(pre)){
    		sValue = sValue.replaceFirst(pre, "");
    		single = true;
    	}else{
    		pre = "";
    		single = false;
    	}
    	sValue = ObjectHelper.removeLineFeed(sValue);
    	if(sValue.matches(idPattern)){
    		return sValue;
    	}
		//boolean validPattern = true;

    	String classKey = getClassKey(cName);
		if(!reverse){
    		if(enProps==null||enFile==null
//    				||!file.equals(enFile)
    				){
		    enProps = loadProperties(file);
		    enFile = file;
		    idSet = enProps.stringPropertyNames();
		     }	
    		pr = enProps;
		}else{
    		if(curProps==null||curFile==null
//    				||!file.equals(curFile)
    				){
    		    curProps = loadProperties(file);
    		    curFile = file;
    		    idSet = curProps.stringPropertyNames();
    		     }	
        		pr = curProps;
		}
		//LoggerHelper.logTAFInfo(+idSet.size()+"props in '"+file+"' loaded");
		
		String sValuePattern = "";
		int numIds = idSet.size(),curNum=0;
        boolean isValuePattern = false;
		try{
			  Pattern.compile(sValue);
			  isPattern = true;
		}catch(Exception e){
			isPattern = false;
		}
		try{
			  Pattern.compile(sValuePattern=RFTGuiFinderHelper.correctPattern(sValue));
			  isValuePattern = true;
		}catch(Exception e){
			isValuePattern = false;
		}
		
		boolean isTempPattern = false;
		for(String id:idSet){            	
			temp = pr.getProperty(id);
			String debugString = "ACL 기본 설정 파일을(.prf)";
			
			if(sValue.contains(debugString)){	
			    if(temp.contains("ACL 기본 설정 파일을(.prf)")){
				 LoggerHelper.logTAFDebug("'"+sValue+" ?=\n\t '"+temp+"'");
			    }
			}    
            
			if((temp=cleanUpMsg(temp)).equals("IGNOREABLE")){
				continue;
			}
			temp = ObjectHelper.removeLineFeed(temp);
			if(
					((temp.equals(sValue)) // Value equal
							)
					&&(classKey.equals("")||id.matches(classKey)) // Class equal
					){	
				
				if(keys!="")
					   keys += "|"+id;
					else
						keys = id;
				if(single)
			         return id;
			}else{ // Pattern match
				
				String tempPattern = RFTGuiFinderHelper.correctPattern(temp);
				try{
					Pattern.compile(tempPattern);
					
					    isTempPattern = true;
					
				}catch(Exception e){
					//System.err.println(e.toString());
					isTempPattern = false;
				}
				
				if(
						(temp.equalsIgnoreCase(sValuePattern)
								||temp.equalsIgnoreCase(sValue+":")
								||(temp+":").equalsIgnoreCase(sValue))
						||(isPattern)&&temp.matches(sValue)
						||(isValuePattern&&temp.matches(sValuePattern))
				){
					
					if(!key.equals(noTranslation)&&key!="")
						   key += "|"+id;
					else
						   key = id;
			    }
				
				if(	(reverse
						&&isTempPattern&&sValue.matches(tempPattern))){
					if(!key_r.equals(""))
						   key_r += "|"+id;
					else
						   key_r = id;
				}

			}
		}
		if(keys!=""){
			key = keys;
		}
		if(reverse&&key.matches(noTranslation)&&key_r!=""){
			key = key_r;
		}
		return key;
	}

	public static String cleanUpMsg(String message){
		String msg = "IGNOREABLE";
		String str = message;

		String[] control = {				 
	            "[\\uEFEE-\\uFFFF]"
                ,"\\p{Cc}|\\p{Cntrl}"
	};
		String[] pat = {
				     "\\n"
				     ,"\\r"
				     ,"['\"(\\[]?%[\\-+]?[\\d]*[Icds]['\")\\])\\]]?"
		             ,"[\\.+*?'\"\\d\\\\/\\s\\n\\r\\(\\)\\[\\]\\-,;]"
		             ,"\\d"
		             ,"^([\\s]*['\"(\\[]?%[\\d]*[l]?[Icds]['\")\\]]?[\\s]*)*"
		};

		if(message==null||message.equals("")){
           return msg;
		}
		
		for(int i=0;i<control.length;i++){
		   str = message.trim().replaceAll(control[i],"");
		}
		
		//str = message;
		for(int j=0;j<pat.length;j++){
		    str = str.replaceAll(pat[j],"");
		}
		
		if(str.length()<2){
			return msg;
		}
		
		return message;
	}
	public static String _getPropID(String file,String sValue, String cName,boolean reverse,boolean isPattern){
		String key = noTranslation;
		String keys="";
		String temp = null;
		
		Properties pr = null;
		boolean noTrans = false;
        boolean single = false;
        boolean isValuePattern = isPattern;
        boolean isTempPattern = false;
    	//String oriValue = sValue;
    	
    	String pre = "SingleLocValue_";
    	sValue = sValue.trim();
    	if(sValue.startsWith(pre)){
    		sValue = sValue.replaceFirst(pre, "");
    		single = true;
    	}else{
    		pre = "";
    		single = false;
    	}
        
    	if(sValue.equals("")){
    		return key;
    	}
    	if(sValue.matches(idPattern)){
    		return sValue;
    	}
		//boolean validPattern = true;

    	
    	String classKey = getClassKey(cName);
		if(!reverse){
    		if(enProps==null||enFile==null
//    				||!file.equals(enFile)
    				){
		    enProps = loadProperties(file);
		    enFile = file;
		    idSet = enProps.stringPropertyNames();
		     }	
    		pr = enProps;
		}else{
    		if(curProps==null||curFile==null
//    				||!file.equals(curFile)
    				){
    		    curProps = loadProperties(file);
    		    curFile = file;
    		    idSet = curProps.stringPropertyNames();
    		     }	
        		pr = curProps;
		}
		//LoggerHelper.logTAFInfo(+idSet.size()+"props in '"+file+"' loaded");
		
		String sValueLine = sValue.replaceAll("\\n","/").replaceAll("\\r","/");
		String sValuePattern = RFTGuiFinderHelper.correctPattern(sValueLine)+"[ ]?[:]?";
		       sValuePattern = RFTGuiFinderHelper.trimExp(sValuePattern);
		String tempLine;
		String tempPattern;
		//int numIds = idSet.size(),curNum=0;
		try{	
			if(isValuePattern){
			  Pattern.compile(RFTGuiFinderHelper.correctPattern(sValuePattern));
			  isValuePattern = true;
			}
			
		}catch(Exception e){
			//System.err.println(e.toString());
			isValuePattern = false;
		}
		for(String id:idSet){
            	
			temp = pr.getProperty(id);
			if(temp==null){
				temp="";
				LoggerHelper.logTAFDebug("'"+id+" == null?'");
				continue;
			}
			
			
			temp = temp.trim();
			if(temp.equals(""))
				continue;
			tempLine = temp.replaceAll("\\\\n","/").replaceAll("\\\\r","/");
			tempPattern = RFTGuiFinderHelper.correctPattern(tempLine)+"[ ]?[:]?";
			
			if(sValueLine.equals(sValueLine.toUpperCase())||
			    tempLine.equals(tempLine.toUpperCase()))
				noTrans = true; // No translation for all UPPERCASE words?!!!
			if(
					(!noTrans
							&&(tempLine.equalsIgnoreCase(sValueLine)) // Value equal
							)
					&&(classKey.equals("")||id.matches(classKey)) // Class equal
					){	
				
				if(keys!="")
					   keys += "|"+id;
					else
						keys = id;
				if(single)
			         return id;
			}else if(lvMatched(sValueLine,sValuePattern,tempLine,tempPattern,isPattern)){ // Pattern match
					if(!key.equals(noTranslation)&&key!="")
						   key += "|"+id;
					else
						   key = id;
			    
			}
		}
		if(keys!=""){
			key = keys;
		}
		return key;
	}
	public static boolean lvMatched(String searchString, String searchPattern, String valueString, String valuePattern,boolean isSearchPattern){
		boolean matched = false;
		boolean isValuePattern = false;
        if(valueString.matches("[/ :|+*?\\r\\n]*")||searchString.matches("[/ :|+*?\\r\\n]*")){
        	return matched = false;
        }
		if(searchString.equalsIgnoreCase(valuePattern)
				||searchString.equalsIgnoreCase(valueString+":")
				||(searchString+":").equalsIgnoreCase(valueString)
				){
			return matched = true;
		}
		try{					
			Pattern.compile(valuePattern);
			isValuePattern = true;
		}catch(Exception e){
			//System.err.println(e.toString());
			isValuePattern = false;
		}
		try{
			if(!isSearchPattern&&isValuePattern&&searchString.matches(valuePattern)){
				return matched = true;
			}
		}catch(Exception e){
			
		}
		try{
			if(isSearchPattern&&valueString.matches(searchPattern)){
				return matched = true;
			}
		}catch(Exception e){
			
		}
       return matched;
	}
//	public static boolean isOutputFormat(String format, String value){
//		String st = "%s|%d|%i|%f";
//		String[] temp = format.split(st);
//		if(temp.length<2){
//			return false;
//		}
//		for(int i=0;i<temp.length;i++){ // need to enhance it later ... Steven.
//			if(!value.contains(temp[i]))
//				return false;
//		}
//		
//		return true;
//	}
	public static ResourceBundle getUTF8Bundle(String baseName,Locale loc){
		return ResourceBundle.getBundle(baseName,loc,new UTF8Control());
	}
	public static Properties  loadProperties(String file){
		Properties curProps = new Properties();	
		FileInputStream fins;
		LoggerHelper.logTAFDebug("Loading properties '"+file+"'");
        try {
			//curProps.load(fins = new FileInputStream(file));
			curProps = UniPropertiesLoader.loadProperties(fins = new FileInputStream(file));
			fins.close();
		} catch (Exception e) {
			LoggerHelper.logTAFDebug(e.toString());
		} 
		LoggerHelper.logTAFDebug("Loaded properties '"+file+"'");
		return curProps;
		
	}

}