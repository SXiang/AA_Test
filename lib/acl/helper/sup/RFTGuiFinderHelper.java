package lib.acl.helper.sup;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import lib.acl.util.FileUtil;

import com.rational.test.ft.object.interfaces.DomainTestObject;
import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.RootTestObject;
import com.rational.test.ft.object.interfaces.SelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.TextGuiTestObject;
import com.rational.test.ft.object.interfaces.TextSelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.object.interfaces.TopLevelSubitemTestObject;
import com.rational.test.ft.object.interfaces.TopLevelTestObject;
import com.rational.test.ft.script.List;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.script.Subitem;
import com.rational.test.ft.value.RegularExpression;
import com.rational.test.util.regex.Regex;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  March 19, 2012
 */



//while((iw=getDialog(winTitle,className))!=null
//&&numCheck++<maxCheck){
//actualTitle = iw.getText();
public abstract class RFTGuiFinderHelper extends WinTreeHelper
{
	public static int defaultMatchFlag //= Regex.MATCH_CASEINDEPENDENT; 
	                                //= Regex.MATCH_MULTILINE;
                                    = Regex.MATCH_NORMAL;
	public static boolean caseSensitive = false;
	public static String defaultTestDomain = "Win";
	public static String defaultWinClass = "#32770";
	public static String classKey = ".class";
	public static String nameKey = ".name";
	public static String textKey = ".text";
	public static String indexKey = ".classIndex";
	
	 // ***************  TopLevelTestObject *****************************
	public static TestObject findTopLevelWindow(String winTitle) 
    { 
		return findTopLevelWindow(winTitle,defaultWinClass);
    }

	public static TestObject findTopLevelWindow(String winTitle, String winClass) 
    { 
        return findTopLevelWindow(defaultTestDomain,winTitle,winClass);
    }
	
    public static TestObject findTopLevelWindow(String domain,String winTitle, String winClass) 
    { 
        // domain not used currently .. steven
    	if(winTitle.startsWith("Status of Task"))
			sleep(0+0);
    	boolean isPattern = false;
    	boolean tryAgain = false;
    	TestObject top = null;
    	RegularExpression winCaption=null;
    	String vsError = "Visual Studio .* Debugger.?"+
        ""; 
    	
    	IWindow iw = ObjectHelper.getDialog(winTitle,winClass,true);
    	if(iw==null){
    		sleep(2);
    		iw = ObjectHelper.getDialog(winTitle,winClass,true);
    	}
    	Integer pid = null;
    	Long hWnd = null;
    	
    	if(iw==null){
    		tryAgain = false;
    		logTAFDebug("Top window '"+winTitle+"' not found");
    	    return null;
    	}else{
        	try{
        		pid = new Integer(iw.getPid());
        		hWnd = new Long(iw.getHandle());
        		winTitle = iw.getText();
        		logTAFDebug("Window: '"+winTitle+"' hWnd = '"+hWnd+"' pid = '"+pid+"'");
            	if(winTitle.matches(vsError)){
            		LoggerHelper.logTAFError(winTitle+" - An unhandled exception occured in ACLWin.exe");
            		while(iw.isShowing()){
            		   iw.close();
            		   sleep(2);
            		}
            		return null;
        		}
            	
        	}catch(Exception e){
				if(!winTitle.startsWith("Localized:"))
				    winTitle = localizeProps(winTitle);
				else
					winTitle = winTitle.replaceFirst("Localized", "");
        		//winTitle = getLocValues(winTitle);
        	}
        	
    		tryAgain = true;
    	}
        
        if(domain==null||domain.equals("")){
        	domain = defaultTestDomain;
        }
        //winTitle = winTitle.replaceAll("-","\\-");
        if(isPattern=isPattern(winTitle)){
    	   winCaption = new RegularExpression((winTitle),caseSensitive);   
        }
        
    	//Regex winCaption = new Regex(winTitle,defaultMatchFlag);	
    	RootTestObject root = getRootTestObject();

    	TestObject[] to = null,topObjects;
    	
    	Subitem sub = atChild(classKey, winClass,nameKey,isPattern?winCaption:winTitle);
    	List li = null;
    	// *** use domain to get find 
    	Object currentDomain = null;
    	DomainTestObject allDomains[] = getDomains();
       	Subitem sub1 = null,sub2=null,sub3=null;
    	       
    	if(hWnd != null){
    		sub1 = atProperty(".hwnd",hWnd);   		
    		//sub2 = atProperty(".domain",domain); // There is no .domain Property for any windowss
    	}else if(pid != null){
    		sub3 = atProperty(".processId",pid);
    	}
    		
    	boolean found = false;
        li = formRFTList(sub,sub1,sub2,sub3);
    	//Check
    	for(int j=0; j<allDomains.length&&!found; j++){   		
    		currentDomain = allDomains[j].getName();
    		if(currentDomain.equals(domain)){ 
    			// Only check in 'Win' domain by default or 'All' domain
    			topObjects = allDomains[j].getTopObjects();
    			for(int i=0; topObjects!=null&&i<topObjects.length; i++){
    				to = topObjects[i].find(li);
    				if(to!=null&&to.length>0){
    					found = true;
    					break;
    				}
    			}
    		}
    		
    	}
    	
    	for(int j=0; j<allDomains.length&&!found&&tryAgain; j++){   		
    		currentDomain = allDomains[j].getName();
    		if(!currentDomain.equals(domain)){ 
    			// Check other domains excepts 'domain'
    			topObjects = allDomains[j].getTopObjects();
    			for(int i=0; topObjects!=null&&i<topObjects.length; i++){
    				to = topObjects[i].find(li);
    				if(to!=null&&to.length>0){
    					found = true;
    					break;
    				}
    			}
    		}
    		
    	}
    	
    	if(!found&&tryAgain){
           to = root.find(atList(li));
        }
        //
    	int thisOne = 0;
    	boolean done = false, doneHwnd = false, donePid = false;
    	//String messages = "OK|Ok|Yes|No";
        if(to==null||to.length<1){
        	logTAFDebug("No top window found with title '"+winTitle+"' in class '"+winClass);
        	return null;
        }else if(to.length>1){
        	iw = getScreen().getActiveWindow();
        	try{
        		pid = new Integer(iw.getPid());
        		hWnd = new Long(iw.getHandle());
        		logTAFDebug("hWnd = '"+hWnd+"' pid = '"+pid+"'");
        	}catch(Exception e){
        		
        	}
        	for(int i=0;i<to.length&&!done;i++){     
        		try{
        			if(hWnd!=null){
        				Object curHwnd = to[i].getProperty(".hwnd");
        				if(curHwnd!=null){

        					Long hwndvalue= Long.parseLong(curHwnd.toString());
        					if( hWnd.equals(hwndvalue)){//&&propertyMatch(to[i],".visiable","true")){
        						doneHwnd = true;
        					}else{
        						doneHwnd = false;
        					}

        				}
        			}
        		}catch(Exception e){
        			doneHwnd = false;
        		}
        		try{
        			if(pid!=null){
        				Object curPid = to[i].getProperty(".processId");
        				String curDomain = to[i].getDomain().getName().toString();
        				if(curPid!=null){
        					Integer pidvalue= Integer.parseInt (curPid.toString());
        					if( pid.equals(pidvalue)){//&& curDomain.equals(domain)){
        						donePid = true;
        					}else{
        						donePid = false;
        					}
        				}
        			}
        		}catch(Exception e){
        			donePid = false;
        		}
        		if(donePid&&doneHwnd){
        			thisOne = i;
        			done = true;
        		}
        	}

        }
    	top = to[thisOne];
    	logTAFDebug(to.length+" top window(s) found with title '"+winTitle+"' in class '"+winClass);
    	//printHashTable(top.getProperties());
    	logTAFDebug("Top Win returned to["+thisOne+"]' = "+top+"'" );
        return top;
    }
    public static boolean pickDateTime(String winTitle,int calIndex,Calendar calendar){
    	int uYear, uMonth, uDay;
    	String controlID = "";
    	String className = "SysDateTimePick32";
    	String util = "lib/acl/tool/DatePicker";
    	
    	uYear = calendar.get(Calendar.YEAR);
    	uMonth = calendar.get(Calendar.MONTH)+1;
    	uDay = calendar.get(Calendar.DAY_OF_MONTH);
    		//DatePicker.exe Age,,[CLASS:SysDateTimePick32;INSTANCE:1],1905,04,15
    	String args = winTitle+",,[CLASS:"+className+";INSTANCE:"+(calIndex+1)+"],"
    	           +uYear+","+uMonth+","+uDay;
    	
    	FileUtil.runExecutable(util,args);
    	return true;
    }
    
    public static String removePattern(String pattern){
    	String[] ori = {
    			"\\s",
    			"\\\\[.]"
    	};
    	String[] rep = {
    			" ",
    			"."
    	};
    	for(int i=0;i<ori.length;i++){
    		pattern = pattern.replace(ori[i], rep[i]);
    	}
    	pattern = trimString(pattern,"\\.\\*")	;
    	pattern = trimString(pattern,"\\.\\+")	;
    	return pattern;
    }
    public static String correctPattern(String pattern){
    	if(pattern.contains("Arbeitskopie ist kleiner oder älter als das Projekt")){
    		sleep(0);
    	}
    	String skipS=".*[\\.][\\+\\*]$"+
        "|^[\\.][\\+\\*].+"+
        "|.*[\\.][\\+?\\*][\\|].*"+
        "|.*[\\|][\\.][\\+?\\*].*";
    	if(pattern.matches(skipS)){
    		return pattern;
    	}
//    	if(!isPattern(pattern))
//    		return pattern;
//    	String[] ori = {
////    			"\\\\n|\\\\r"
//    			"\\n|\\r"
//    			,"\\s"            //Spaces
//
//    			,"\"\""
//    			//,"\\-"
//    			,"([^\\\\]?)[\\(]"       // ( and )
//    			, "([^\\\\]?)[\\)]"
//    			, "([^\\\\])([\\.\\+\\*])"
//    			,"(['\"]?)%[\\-+]?[I]?[\\d]*[l]?[Icdsi](['\"]?)"  
//    			,"(['\"]?)%[\\-+]?[I]?[\\d]*xh(['\"]?)"
//    			,"(['\"]?)%\\(0x%X\\)(['\"]?)"
//    			,"\\?"
//    			,"[\\\\]+"// In case of duplicates
//    			};                  
//    	String[] rep = {
//    //   		"/"
//    			"/"
//    			,"\\\\s"
//
//    			,"\""
//    			//,"\\\\-"
//    			,"$1\\\\("
//    			,"$1\\\\)"
//    			,"$1\\\\$2"
//    			,"$1.+$2"
//    			,"$1.+$2"
//    			,"$1.+$2"
//    			,"\\\\?"
//    			,"\\\\"
//    			};
    	String[] ori = {
    			"\\n|\\r"
    			,"\\s"            //Spaces
    			,"\"\""
    			,"([^\\\\]?)[\\(]"       // ( and )
    			, "([^\\\\]?)[\\)]"
    			, "([^\\\\])([\\.])([^\\+\\*\\?])" // escape .
    			, "([^\\.%])([\\+\\*\\?])" 	  // escape +*?  
    			,"(['\"]?)%[\\-+]?[I]?[\\d]*[l]?[Icdsi](['\"]?)"  
    			,"(['\"]?)%[\\-+]?[I]?[\\d]*xh(['\"]?)"
    			,"(['\"]?)%\\(0x%X\\)(['\"]?)"
    			,"\\?"
    			,"[\\\\]+"// In case of duplicates
    			};                  
    	String[] rep = {
    			"/"
    			,"\\\\s"
    			,"\""
    			,"$1\\\\("
    			,"$1\\\\)"
    			,"$1\\\\$2$3"
    			,"$1\\\\$2"	 
    			,"$1.+$2"
    			,"$1.+$2"
    			,"$1.+$2"
    			,"\\\\?"
    			,"\\\\"
    			};    	
    	for(int i=0;i<ori.length;i++){
    	    pattern = pattern.replaceAll("(?i)"+ori[i], rep[i]);
    	}
    	
    	//pattern = pattern.replaceAll("\\s", "\\\\s");
    	//logTAFDebug("Pattern: "+pattern);
    	
    	return trimExp(pattern);
    }
    public static String _correctPattern(String pattern){
    	String skipS=".*\\.[+*]$"+
    	             "|^\\.[+*?].+"+
    	             "|.*\\.[+?*]\\|.*"+
    	             "|.*\\|[.][+?*].*";
    	if(pattern.matches(skipS)){
    		return pattern;
    	}
//    	if(!isPattern(pattern))
//    		return pattern;
    	String[] ori = {
    			"\\n|\\r"
    			, "([^\\\\])([.+*()])"
    			,"(['\"]?)%[\\d]*[Icds](['\"]?)"  
    			,"[?]"
    			};                  
    	String[] rep = {
    			"/"
    			,"$1\\\\$2"
    			,"$1.+2"
    			,"\\\\?"
    			};
    	
    	for(int i=0;i<ori.length;i++){
    	    pattern = pattern.replaceAll(ori[i], rep[i]);
    	}
    	
    	//pattern = pattern.replaceAll("\\s", "\\\\s");
    	//logTAFDebug("Pattern: "+pattern);
    	
    	return trimExp(pattern);
    }    
    public static boolean isPattern(String str){
    	if(str.matches(".*[\\[\\]\\|\\*+?].*"
    			+"|.*\\(\\?i\\).*"
    			+"|i{0}"
    			)){
    		//LoggerHelper.logTAFWarning(str+ " is a reg pattern");
    		return true;
    	}else{
    		//LoggerHelper.logTAFWarning(str+ "is not a reg pattern");
    		return false;
    	}
    }
    
    public static List formRFTList(Subitem... subs){
    	List li = new List();
    	for(Subitem sub:subs){
    		if(sub!=null){
    			li.append(sub);
    		}
    	}
    	return li;
    }
    public static List formRFTFindList(String... pairs){
    	return formRFTFindList(false,pairs);
    }    
    
    public static String localizeProps(String value){
    	String temp = getLocValues(value);
    	String lvalue1 = "",lvalue2="";
    	
    	//if(value.contains("ACL Wizard Error"));
    	
    	if(value.equalsIgnoreCase(temp)){
    		return value;
    	}else if(isValidPattern(lvalue1 = correctPattern(temp))){    		
//    		if(isValidPattern(lvalue2 = correctPattern(value)+"|"+lvalue1)){
//    			value = lvalue2;
//    		}else{
    			value = lvalue1;
//    		}
    	}else{
    		value = temp;
    	}

        
      return value;
    }
    public static void localizeProps(String... pairs){
    	String localProp = ".name|name|.text|text";  // For localization project
    	String className = getClassName(pairs);
    	boolean isdbtn = true;//isDButton(className);
       	String temp="";
 
       	
    	for(int i=0;i<pairs.length-1;i=i+2){
            if(pairs[i].matches(localProp)){ // Localized value
            	temp = getLocValues(pairs[i+1],className);
            	if(pairs[i+1].equals("ACL_CmdLine_RunBtn")){
            		className = className;
            	}
            	
            	logTAFDebug("\t\t\t Convert "+pairs[i]+"-"+pairs[i+1]+" -> '"+temp+"'");
            	if(isValidPattern(pairs[i+1] +"|"+temp)&&!pairs[i+1].matches("(.*\\|)?"+temp+"(\\|.*)?")){
            		if(!temp.equals(""))
            	      pairs[i+1] += "|"+temp;
            	}else{
            		pairs[i+1] = temp;
            	}
            	//.correctPattern("");
            	pairs[i+1] = trimExp(pairs[i+1]);
            }
    	}
    }
    public static String trimExp(String from){
//    	return from;
    	from = from.replaceAll("\\|\\|", "|");
    	from = from.replaceAll("\\|\\s[.][\\*\\+\\?]\\s\\|", "|");
    	return trimString(from,"\\|");
    }
    public static String trimString(String from,String st){
    	
    	from = from.replaceAll("^"+st, "");
    	from = from.replaceAll(st+"$", "");
    	return from;
    }
	public static boolean isDButton(String className){
		 String[] dButton = {"BUTTON","MENUITEM"};
		 String[] dLang = {"zh","ja","ko"};
		 boolean isdbtn = false;
		 
		 for(int i=0;i<dLang.length;i++){
			 if(FileUtil.locale.getLanguage().equalsIgnoreCase(dLang[i])){
					 isdbtn = true;
			         break;
			 }
		 }
		 if(!isdbtn)
			 return isdbtn;
		 for(int i=0;i<dButton.length;i++){
			 if(className.toUpperCase().contains(dButton[i]))
					 return true;
		 }
		 
		 return false;
	}
    public static String getClassName(String...pairs){
    	String className = "";
    	for(int i=0;i<pairs.length-1;i=i+2){
    		if(pairs[i].matches(classKey)){
    			className = pairs[i+1];
    			break;
    		}
    	}
    	return className;
    }
    public static boolean isValidPattern(String sValue){
    	boolean isPattern = true;
		try{
			Pattern.compile(sValue);
		}catch(Exception e){
			isPattern = false;
		}
		return isPattern;
    }
    public static List formRFTFindList(boolean isChild, String... pairs){
    	
    	List li = new List();
 
        if(pairs==null||pairs==null)
        	return li;
        
        localizeProps(pairs); // Localize name and text
        
    	for(int i=0;i<pairs.length-1;i=i+2){
    		boolean isValuePattern ;
        	RegularExpression valueReg=null;
        	   
        	
            if(isValuePattern=isPattern(pairs[i+1])){            	
            	valueReg = new RegularExpression(correctPattern(pairs[i+1]),caseSensitive);  
            }
           
            if(i==0){
            	if(isChild){
            		if(pairs.length<4){
            			li.append(atChild(pairs[i],isValuePattern?valueReg:pairs[i+1]));
            		}else{
            		   li.append(atChild(pairs[i],isValuePattern?valueReg:pairs[i+1],
            				             pairs[i+2],isPattern(pairs[i+3])?
                       (new RegularExpression(correctPattern(pairs[i+3]),caseSensitive)):pairs[i+3]));
            		   i=i+2;
            		}
                }else{
                   	if(pairs.length<4){
            			li.append(atDescendant(pairs[i],isValuePattern?valueReg:pairs[i+1]));
            		}else{
            		   li.append(atDescendant(pairs[i],isValuePattern?valueReg:pairs[i+1],
            				             pairs[i+2],isPattern(pairs[i+3])?
                       (new RegularExpression(correctPattern(pairs[i+3]),caseSensitive)):pairs[i+3]));
            		    i=i+2;
            		}
            	   // li.append(atDescendant(pairs[i],isValuePattern?valueReg:pairs[i+1]));
            	}
            }else{

            	li.append(atProperty(pairs[i],isValuePattern?valueReg:pairs[i+1]));
            }
    	}
    	
    	return li;
    }
    //************** General GuiTestObject ***************************
    public static TestObject findTestObject(List li ) {
    	return findTestObject(getRootTestObject(),li);
    }
    public static TestObject findTestObject(String... pairs) {
    	return findTestObject(getRootTestObject(),pairs);
    }
    public static TestObject findTestObject(TestObject anchor, String... pairs ) { 	
        return findTestObject(anchor,false,pairs);
    }
    
    public static TestObject findTestObject(TestObject anchor, boolean isChild, String... pairs ) { 
    	List li;
    	try{
    	    li = formRFTFindList(isChild,pairs);    	
    	}catch(Exception e){
    		logTAFWarning("Exception caught when 'form the find list' "+e.toString());
    		return null;
    	}
//    	if(pairs[3].matches(".*Working|.*Last-saved|.*Cancel")){   
//    		sleep(0);
//    	}
        return findTestObject(anchor,li);
    }
    public static TestObject findTestObject(TestObject anchor, List li) { 
    	return findTestObject(anchor,li,true);
    }
    public static TestObject findTestObject(TestObject anchor, List li ,boolean mappable) { 
    	if(anchor==null||!anchor.exists()||li==null){
    		logTAFWarning("Invalid search criteria!");
    		return null;
    	}
    	TestObject[] to;
    	
    	try{
    	    to = anchor.find(li,mappable);
    	    if(to==null||to.length<1){
    	    	to = anchor.find(li,!mappable);
    	    }
    	}catch(Exception e){
    		logTAFWarning("Exception when find '"+
    				anchor.getDescriptiveName()+
    				".find("+li+","+mappable+")");
    		to = null;
    	}
    	if(to==null||to.length<1)
    		return null;
    	else
    		return to[0];
    }
    

    //*************** Specific TestObject ****************************

    //***** Sub Window **********************
    public static GuiSubitemTestObject findSubWindow(TestObject anchor,String winName){    	
    	return findSubWindow(anchor,false,"",winName);
    }
    public static GuiSubitemTestObject findSubWindow(TestObject anchor){    	
    	return findSubWindow(anchor,"");
    }
    public static GuiSubitemTestObject findSubWindow(TestObject anchor,boolean isChild,String winName){
      return findSubWindow(anchor,isChild,"",winName);
    }
    public static GuiSubitemTestObject findSubWindow(TestObject anchor,boolean isChild,String winClass,String winName){
    	GuiSubitemTestObject to = null;
    	String className = "#32770";
    	if(winClass.equals("")){
    		winClass = className	;
    	}
    		try{
    			to = new GuiSubitemTestObject(findTestObject(anchor,isChild,classKey,winClass,nameKey,""+winName));   
    		}catch(Exception e){
    			to = null;
    			logTAFDebug("Exception when searching for sub windows'"+winName+"' "+e.toString());
    		}
    	return to;
    }
    
    public static GuiSubitemTestObject findGuiSubitemTestObject(TestObject anchor,String[] className,int classIndex){
    	GuiSubitemTestObject to = null;
    	boolean found = false;

    	for(int i=0;i<className.length&&!found;i++){
    		//List li = atList(atDescendant(classID,className[i]));
    		try{
    			to = new GuiSubitemTestObject(findTestObject(anchor,classKey,className[i],indexKey,""+classIndex));   
    			found = true;
    		}catch(Exception e){
    			to = null;
    			logTAFDebug("Exception when searching for '"+className[i]+"' "+e.toString());
    		}
    	}
    	return to;
    }
    //******* Calendar ***************************
    public static GuiSubitemTestObject findTimePick(TestObject anchor,int classIndex){   	
    	String[] className = {"SysDateTimePick32"};
    	return findGuiSubitemTestObject(anchor,className,classIndex);
    }
    public static GuiSubitemTestObject findTimePick(TestObject anchor){   	
        int classIndex = 0;
    	return findTimePick(anchor,classIndex);
    }
    //******* Messages ***************************
    public static GuiSubitemTestObject findStatictext(TestObject anchor){   	
    	String[] className = {".Statictext","Edit"};
    	return findGuiSubitemTestObject(anchor,className,0);
    }

    //******** Checkbutton ***********************************
    public static ToggleGUITestObject findCheckbutton(TestObject anchor,String label){
    	ToggleGUITestObject to;
    	String className = ".Checkbutton";
    	boolean isPattern = false;
    	try{
    	    to = new ToggleGUITestObject(findTestObject(anchor,classKey,className,nameKey,label));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+label+"': "+e.toString());
    	}
    	return to;
    }
    //******** Radiobutton ***********************************
    public static ToggleGUITestObject findRadiobutton(TestObject anchor,String label){
    	ToggleGUITestObject to;
    	String className = ".Radiobutton";
    	boolean isPattern = false;
    	try{
    	    to = new ToggleGUITestObject(findTestObject(anchor,classKey,className,nameKey,label));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+label+"': "+e.toString());
    	}
    	return to;
    }
    //******** Pagetab *********************************** 
    public static GuiTestObject findPagetab(TestObject anchor,String label){
    	GuiTestObject to;
    	String className = ".Pagetab";
    	boolean isPattern = false;
    	try{
    	    to = new GuiTestObject(findTestObject(anchor,classKey,className,nameKey,label));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+label+"': "+e.toString());
    	}
    	return to;
    }
    //******** Pushbutton ***********************************
    public static GuiTestObject findPushbutton(TestObject anchor,String label){
    	GuiTestObject to;
    	String className = ".Pushbutton";
    	boolean isPattern = false;
    	

    	try{
    	    to = new GuiTestObject(findTestObject(anchor,classKey,className,nameKey,label));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+label+"': "+e.toString());
    	}

    	return to;
    }
 
    
    // ***************** Find Table  **************************************************

    public static SelectGuiSubitemTestObject findTable(TestObject anchor,String className,int classIndex){
        return findTable(anchor,false,className,0);
      }
    public static SelectGuiSubitemTestObject findTable(TestObject anchor,boolean isChild,int classIndex){
        return findTable(anchor,isChild,"SysListView32",classIndex);
      }
    
    public static SelectGuiSubitemTestObject findTable(TestObject anchor,boolean isChild,String className, int classIndex){
    	SelectGuiSubitemTestObject to;
    	try{
    	    to = new SelectGuiSubitemTestObject(findTestObject(anchor,isChild,classKey,className,indexKey,""+classIndex));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+classIndex+"': "+e.toString());
    	}
    	return to;
    } 
    
    // ***************** Find EditBox  **************************************************

    public static TextGuiTestObject findEditbox(TestObject anchor,String className,int classIndex){
        return findEditbox(anchor,false,className,classIndex);
      }
    public static TextGuiTestObject findEditbox(TestObject anchor,boolean isChild,int classIndex){
        return findEditbox(anchor,isChild,"Edit",classIndex);
      }
    public static TextGuiTestObject findEditbox(TestObject anchor,boolean isChild,String className, int classIndex){
    	TextGuiTestObject to;
    	try{
    	    to = new TextGuiTestObject(findTestObject(anchor,isChild,classKey,className,indexKey,""+classIndex));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+classIndex+"': "+e.toString());
    	}
    	return to;
    } 
    // ***************** Find ComboBox  **************************************************
    public static TextSelectGuiSubitemTestObject findComboBox(TestObject anchor,int classIndex){
        return findComboBox(anchor,"ComboBox",classIndex);
      }
    public static TextSelectGuiSubitemTestObject findComboBox(TestObject anchor,String className,int classIndex){
        return findComboBox(anchor,false,className,classIndex);
      }

    public static TextSelectGuiSubitemTestObject findComboBox(TestObject anchor,boolean isChild,int classIndex){
        return findComboBox(anchor,isChild,"ComboBox",classIndex);
      }
    public static TextSelectGuiSubitemTestObject findComboBox(TestObject anchor,boolean isChild,String className, int classIndex){
    	TextSelectGuiSubitemTestObject to;
    	try{
    	    to = new TextSelectGuiSubitemTestObject(findTestObject(anchor,isChild,classKey,className,indexKey,""+classIndex));   
    	}catch(Exception e){
    		to = null;
    		logTAFDebug("Exception when searching for '"+className+"' - '"+classIndex+"': "+e.toString());
    	}
    	return to;
    } 
    //***********************************************************************************
    //***********************************************************************************
}
