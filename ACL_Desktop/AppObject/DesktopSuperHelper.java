package ACL_Desktop.AppObject;

import java.awt.Point;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import ACL_Desktop.conf.beans.ProjectConf;

//import com.ibm.security.util.calendar.BaseCalendar.Date;
import com.rational.test.ft.object.interfaces.FrameSubitemTestObject;
import com.rational.test.ft.object.interfaces.GuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.GuiTestObject;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.object.interfaces.SelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.TestObject;
import com.rational.test.ft.object.interfaces.TextGuiTestObject;
import com.rational.test.ft.object.interfaces.TextSelectGuiSubitemTestObject;
import com.rational.test.ft.object.interfaces.ToggleGUITestObject;
import com.rational.test.ft.object.interfaces.TopLevelSubitemTestObject;
import com.rational.test.ft.object.interfaces.TopLevelTestObject;
import com.rational.test.ft.script.Action;
import com.rational.test.ft.script.ITestObjectMethodState;
import com.rational.test.ft.script.RationalTestScript;
import com.rational.test.ft.script.ScriptCommandFlags;
import com.rational.test.ft.script.Subitem;

import conf.beans.FrameworkConf;
import conf.beans.TimerConf;
import lib.acl.helper.*;
import lib.acl.helper.sup.InitializeTerminateHelper;
import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.ObjectHelper;
import lib.acl.helper.sup.TAFLogger;
import lib.acl.tool.htmlRFTHandler;
import lib.acl.util.FileUtil;
//import org.apache.commons.io.FilenameUtils;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  January 12, 2012
 */


public abstract class DesktopSuperHelper extends lib.acl.helper.KeywordSuperHelper
{
	
	protected getObjects gObj;
	protected keywordUtil kUtil;
	protected aclDataDialogs dataDlog;
	protected dialogUtil dLog;
	protected aclRoutines aRou;
	protected String defaultMenu="";
	protected String itemName = "",
	                serverItemPattern = ".*_DB2|.*_SQLServer|.*_Oracle",
					dpMasterFile,dpMasterFiles[] = new String[50],
					dpActualFile,dpActualFiles[] = new String[50],
					actualACLFile,
					fileExt = ".fil",
					actualName = "",
					actualItem = "",
					command = "",
					winTitle = "",
					tabMainName = "",
					tabMoreName = "More...",
					tabOutputName = "Output...",
					mainTab = "Main",moreTab = "More", outputTab = "Output",
					textResult = "",
					datePattern = "dd-MMM-yyyy";
    protected boolean dismissPopup = false;  
    protected boolean fileComparable = true;
	protected String aclTableLineFeed="\\n[\\s]*[\\r]?\\n"; 
	protected TopLevelSubitemTestObject mainDialog;
	protected GuiSubitemTestObject tabMain,tabMore,tabOutput;
	protected ArrayList<String> filterList = new ArrayList<String>();
	
	protected int[] itemIndex;
	protected boolean itemCreated = false,
     				fileCreated = false;
	protected boolean sharedDataDone = false,
		              sharedMainDataDone = false,
		              ignoreOverflow = true;
	// BEGIN of datapool variables declaration
	protected String dpOpenProject=""; //@arg command for open acl project
                                    //@value = 'Open|OpenCancel|OpenNew|OpenWorking|OpenLastSaved|OpenRecent|CreateNew', default to OpenNew
	protected String dpProjectName=""; //@arg Project Name
                                      //@value = 'Name|Name.ACL|Absolute path to project|Relative path to project'
                                      //@value   If ends with '_', the project with suffix - TestType defined in Project.properties will be used 	

	protected String dpUnicodeTest=""; //@arg If this test is for unicode test only or both
                                  //@value = 'True|False', If true, it will only be run with an Unicode AUT 
    protected String dpMenuItem=""; //@arg path to or name for the menu item to be tested
                                 //@value = '[menu->menuitem]|[menuitem], a default path may be provided by each keyword
	protected String dpPreCmd=""; //@arg commands to be executed in the beginning of the test
                                 //value = 'command1|command2|...'
	protected String dpPostCmd=""; //@arg commands to be executed before the end of the test/verification
                              //@value = 'command1|command2|...'
	protected String dpPreFilter=""; //@arg filters for current table
                                //@value = 'filter1|filter2...'

	protected String dpEndWith=""; //@arg the end options for current test
                                //@value = 'Finish|Cancel' - Default to Finish
                                //@value   'SaveProject|SaveProjectAs|SaveProjectOverwirte|CloseProject'
	protected String dpActOnItem="";//@arg Name of the item to be tested.
                               //@value = '[path->to->the->item]'
	
         /* Shared varialbel in main - more - output */
	protected String dpFields="";  //@arg filed name to be extracted, default to 'Record'
                               //@value = 'field1|field2|...' or 'Record' or 'Add All' 
	protected String dpExpression=""; //@arg expression to be used for this test. 
                                 //@value = expression (',' should be replaced by ';' as RFT data pool use ',' as the field separator 
	protected String dpIf="";      //@arg if condition for the test
	protected String dpFileName=""; //@arg paht/name of the resulted file used in 'To' or 'Name' text field
	protected String dpUseOutputTable=""; //@arg Yes|No
	protected String dpScope=""; //@arg All/First/Next
                             //@value = '[All]|[First|Num]|[Next|Num]
	protected String dpWhile=""; //@arg while condition to be used for this test.
	protected String dpAppendToFile=""; //@arg 'Yes|No', default to 'No'
    protected String dpSaveLocalOrServer="TBD"; //arg 'Local|Server'
    protected String dpTo="";   //@arg 'File|Screen|[Graph]' 
    protected String dpFileType="";   //@arg 'Unicode Text File|Ascii Text File|ACL Table' 
    protected String dpOutputHeader=""; //@arg header in Output tab
    protected String dpOutputFooter=""; //@arg footer in Output tab
    protected String dpArchiveProject="";  //@arg Save resulted project/folder to file server
                                           //@value '0|1', '0' for copying files only, '1' for copying the folder,default to '0'.
    protected String dpTestProgressBar=""; //@arg Test import progress bar if 'Yes' , otherwise wait till finish.
	// End of datapool variables declaration
	
	public void testMain(Object[] args) 
	{ 
		super.testMain(args);
		gObj = new getObjects();
		kUtil = new keywordUtil();
		dLog = new dialogUtil();
		aRou = new aclRoutines();
		dataDlog = new aclDataDialogs();
        
		readSharedTestData();		
        kUtil.activateAUT(false);		
		dpProjectName = kUtil.openProject(dpProjectName,dpOpenProject,command.matches("RunScript"));
		readPreCommand();
		//openTest();
	}
	public void readPreCommand(){
	    
	    if(delFile&&dLog.safety){
	    	if(dpPreCmd.equals(""))
	        	  dpPreCmd = "SET Safety OFF";
	          else if(!dpPreCmd.matches("(?i).*SET SAFETY OFF.*"))
	    	     dpPreCmd ="SET Safety OFF|"+dpPreCmd;
	    	//dLog.safety = false;
        }else if(!delFile&&!dLog.safety){
	    	if(dpPreCmd.equals(""))
	        	  dpPreCmd = "SET Safety ON";
	          else if(!dpPreCmd.matches("(?i).*SET SAFETY ON.*"))
	    	   dpPreCmd = "SET Safety ON|"+dpPreCmd;
	    	//dLog.safety = true;
        }
	   
	    if(ignoreOverflow&&dLog.overflow){
	    	if(dpPreCmd.equals(""))
	        	  dpPreCmd = "SET Overflow OFF";
	          else  if(!dpPreCmd.matches("(?i).*SET SAFETY OFF.*"))
	    	   dpPreCmd ="SET Overflow OFF|"+dpPreCmd;
	    	//dLog.safety = false;
        }else if(!ignoreOverflow&&!dLog.overflow){
	    	if(dpPreCmd.equals(""))
	        	  dpPreCmd = "SET Overflow ON";
	          else  if(!dpPreCmd.matches("(?i).*SET SAFETY ON.*"))
	    	   dpPreCmd = "SET Overflow ON|"+dpPreCmd;
	    	//dLog.safety = true;
        }
	   //logTAFInfo("dpPreCmd"+dpPreCmd);
        
	}
	public void readSharedTestData(){
		
		if(sharedDataDone)
			return;
		
		dpUnicodeTest = getDpString("UnicodeTest");
		//dpPreCmd = getDpString("PreCmd");	         
        //dpPostCmd = getDpString("PostCmd");	 
        dpPreFilter = getDpString("PreFilter");
           dpPreFilter = dpPreFilter.replaceAll(";", ",");
        dpTestProgressBar = getDpString("TestProgressBar");
		dpMenuItem = getDpString("MenuItem");
		  if(!dpMenuItem.equals("")){			  
			  String[] temp = dpMenuItem.split("->");
			  menuItem = temp[temp.length-1];
              if(temp.length==1&&!dpMenuItem.equals(defaultMenu)){
            	  dpMenuItem = defaultMenu+"->"+dpMenuItem;
              }			  
		  }
		  
		dpProjectName = getDpString("ProjectName");
		   if(dpProjectName.endsWith("_")){
			   dpProjectName = dpProjectName+ProjectConf.testType;
		   }else if(dpProjectName.endsWith("_"+ProjectConf.testType)){
		    // Nothing, will run always
		   }
        dpOpenProject = getDpString("OpenProject");
        dpArchiveProject = getDpString("ArchiveProject");
        dpEndWith = getDpString("EndWith");	 
        dpPreCmd = getDpString("PreCmd");	
		dpActOnItem = getDpString("ActOnItem");	 
		//if(!defaultMenu.equalsIgnoreCase("File")){
	        itemName = getNameFromPath(dpActOnItem);
	        //logTAFInfo("itemName "+itemName);
	        if(!itemName.equals("")){
	            if(dpPreCmd.equals(""))
	          	  dpPreCmd = "OPEN "+itemName;
	            else
	      	      dpPreCmd = dpPreCmd+"|OPEN "+itemName;
	           }
		//}
         dpPostCmd = getDpString("PostCmd");	
        // dpFilterHistory = getDpString("FilterHistory");

         sharedDataDone= true;
	}
	
	public void readSharedMainTestData(){
        if(sharedMainDataDone)
        	return;
        dpExpression = getDpString("Expression");
        dpIf = getDpString("If");

        dpSaveLocalOrServer = getDpString("SaveLocalOrServer");
	    if(dpSaveLocalOrServer.equals("")){
	    	dpSaveLocalOrServer = "TBD";
	    }
	    
        dpFields = getACLFields("Fields");
        
        
        dpUseOutputTable = getDpString("UseOutputTable");
        dpScope = getDpString("Scope");
        dpWhile = getDpString("While");
        dpAppendToFile = getDpString("AppendToFile");
        
        dpFileName = getDpString("FileName");
            if(dpFileName.equals("")){
            	// We will always use a default name for file output, so please avoid use test case with empty file name
            	dpFileName = defaultMenu+"\\"+command.replaceAll("->", "\\")+"\\"+winTitle+"_"+(currentTestLine-1);
            }
            
    	    actualItem = new File(dpFileName).getName();
    	    logTAFDebug("Name: '"+actualItem+"'");
    	    //actualItem = FilenameUtils.removeExtension(actualItem);
    	    actualItem = FileUtil.removeFileExtension(actualItem);
    	    logTAFDebug("Name without extension : '"+actualItem+"'");
    	    actualName = keywordUtil.replaceSpecialChars(actualItem);
    	    actualName = dpActOnItem.replace(itemName,actualItem);
        sharedMainDataDone = true;
	}
	
	public void readOutputTestData(){
		dpTo = getDpString("To");
		  if(dpTo.equals("")){
			  dpTo = "File";
		  }
	    dpFileType = getDpString("FileType");
	    dpOutputHeader = getDpString("OutputHeader");
	    dpOutputFooter = getDpString("OutputFooter");
	}
	
	public void openTest(){
		aRou.exeACLCommands(dpPreCmd);
		aRou.setACLFilters(dpPreFilter);
		if(!defaultMenu.equals("")&&!command.equals(""))
		         kUtil.invokeMenuCommand(defaultMenu+"->"+command);
	}
	
	public String getACLFields(String item,String basedOn){
		// Because in server table, we were using upper case for fields in excels,
		// it won't work in field selection if in wrong case.
		// 
		String thisValue;
		String excepts = "Record|View";//|Fields|";
		thisValue = getDpString(item);
		if(basedOn.matches(serverItemPattern)){
			if(!thisValue.matches(excepts)&&!isExpression(thisValue))
			   thisValue = thisValue.toUpperCase();
		}
        return thisValue;
	}
	public String getACLFields(String item){
           return getACLFields(item,dpActOnItem);
	}


	public DesktopSuperHelper(){

	}
	
	// ********** Begin of main functions ************

	public void aclMainFunctions(){  // Shared steps
		//@Step  issue pre commands, open table, supply filters
		//@Step Click menu item to be tested
		//openTest();
		
		//@Step Work on main tab
		//@Step Work on output tab
	    //@Step Work on more tab		
		//aclMainDialog();
        
		//@Step Submit work
		//@Step run commands after main test if there are any 
	    //aclEndWith("fileAction");
	    //@Step verify results based on the nature of tests
	    //@@Step Compare ACL resulted file with master file 
	    //@@Step Compare ACL resulted log view with master file 
	    //@@Step Compare ACL resulted Screen view with master file 
	    //@@Step Select item from ACL tree view if a new item created during test.	    
	    //doVerification(dpTo);
	}
	
	
	public boolean setOutputInMain(GuiSubitemTestObject tabDialog){
    	if(propertyMatch(tabDialog, ".visible", "false",false)){
    		logTAFWarning("The Main Tab is not visible?");
    	}
    	boolean useOutputTableEnabled = false;
    	boolean itemCreated = false;
    	//Verify enabled or disabled on 'Use Output Table'

    	if(!dpUseOutputTable.equals("")){
        	ToggleGUITestObject usetable = findCheckbutton(tabDialog,"Use Output Table");   	
        	useOutputTableEnabled = isEnabled(usetable,false);
    		if(!useOutputTableEnabled){
    			logTAFError("Option Use output table is disabled");
    			//return ;
    		}else{
			  actionOnCheckbox(usetable,
					"Use Output Table",
					dpUseOutputTable.equalsIgnoreCase("Yes")?true:false,"New");
			
			  if(!fileExt.equalsIgnoreCase(".INX"))
			     itemCreated = true;
    		}
		}
    	
   
	    
    	//Set To...
		setupTestFiles(dpFileName,
				dpSaveLocalOrServer,
				dpAppendToFile,
				fileExt);  
		
		return itemCreated;
	}
	
	public void setOutputInMore(GuiSubitemTestObject tabDialog){
    	boolean useOutputTableEnabled = false;
    	//Verify enabled or disabled
 
    	ToggleGUITestObject usetable = findCheckbutton(tabDialog,"Use Output Table");
    	useOutputTableEnabled = isEnabled(usetable,false);
    	
	   	boolean appendEnabled = false;
       	ToggleGUITestObject append = findCheckbutton(tabDialog,"Append To Existing File");
    	appendEnabled = isEnabled(append,false);
    	
    	if(dpFileType.equalsIgnoreCase("ACL Table")){
//    		if(!dpAppendToFile.equals("")&&appendEnabled){
//    			logTAFError("Append To Existing File should be disabled for output of 'ACL Table'");
//    		}
    		itemCreated = true;
    	}else if(!dpFileType.equals("")){
    		if(!dpUseOutputTable.equals("")&&useOutputTableEnabled){
    			logTAFError("Use Output Table should be disabled for output of '*** Text File' - ["+dpFileType+"]");
    		}
    	}
    	
 
    	if(!dpUseOutputTable.equals("")){
    		if(!useOutputTableEnabled){
    			logTAFError("Option Use output table is disabled");
    			return;
    		}
			actionOnCheckbox(usetable,
					"Use Output Table",
					dpUseOutputTable.equalsIgnoreCase("Yes")?true:false,"New");
		}
	}
	

    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	if(propertyMatch(tabDialog, ".visible", "false",false)){
    		logTAFWarning("The More Tab is not visible?");
    	}
    	// Work on tabDialog
    	setScope(tabDialog,dpScope,dpWhile);    
    	
	   
       
       	if(!dpAppendToFile.equals("")){    
       		boolean appendEnabled = false;
       		ToggleGUITestObject append = findCheckbutton(tabDialog,"Append To Existing File");
        	appendEnabled = isEnabled(append,false);       
    		if(!appendEnabled){
    			logTAFError("Option Append to file is disabled");
    			return;
    		}
			actionOnCheckbox(append,
					"Append To Existing File",
					dpAppendToFile.equalsIgnoreCase("Yes")?true:false,"New");
		}
       	
    	if(defaultMenu.matches("Analyze")){
    		setOutputInMore(tabDialog);
    	}
	}
    
	// ****** Output Tab *******************
    public void thisOutputTab(GuiSubitemTestObject tabDialog){    	
    	// Work on tabDialog
    	
    	if(propertyMatch(tabDialog, ".visible", "false",false)){
    		logTAFWarning("The Output Tab is not visible?");
    	}
    	if(!dpTo.equals("")){
    	   actionOnCheckbox(findRadiobutton(tabDialog,dpTo),
				dpTo,
				true,"New");
    	}
    	
    	dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
	     
     	if(dpTo.equalsIgnoreCase("File")){
     		dpFileType = setFileType(tabDialog,dpFileType);
     	}
		setupTestFiles(dpFileName,
				dpSaveLocalOrServer,
				dpAppendToFile,
				fileExt);   
		
    	if(dpTo.equalsIgnoreCase("File")){
    		actionOnText(findEditbox(tabDialog,true,0),"Name...",actualACLFile, "New");
				
    	}
     	
     	setOptionalHeaderAndFooter(tabDialog,dpOutputHeader,dpOutputFooter,1);
	}
	
//    public void setToInMain(GuiSubitemTestObject tabDialog){
//    	setToInMain(tabDialog,1);
//    }
//    
//	public void setToInMain(GuiSubitemTestObject tabDialog,int index){
//    	dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
//	     
//		setupTestFiles(dpFileName,
//				dpSaveLocalOrServer,
//				dpAppendToFile,
//				fileExt);   
//
//    	actionOnText(findEditbox(tabDialog,true,index),"To...",actualACLFile, "New");
//
//	}
	public void aclEndWith(){
		aclEndWith("");
	}
	public boolean aclEndWith(String task){		
		return aclEndWith(task,null);
	}

	public boolean aclEndWith(String task,GuiTestObject status){		

	    if(task.equalsIgnoreCase("fileAction")){	    	
	    		fileCreated = endWithAction(mainDialog,dpEndWith,false);      // change to false if there are possible error here - Steven.
	    		//fileCreated = endWithAction(mainDialog,dpEndWith,true); //ignore any popup message
	    }else if(task.equalsIgnoreCase("OtherAction")){
	    	//TBD
	    }

		if(!kUtil.isActivated()){
			logTAFWarning("AUT is not activated at the end of this test, please check if there were anything wrong!");
		}
		return fileCreated;
	}
	public boolean isRunningScript(TestObject status){
		return isRunningScript(null,status,false);
	}
	public boolean isRunningScript(TestObject anchor,TestObject status,boolean dismissPopup){

		boolean running = false;
		String classTag = ".class";
		String classValue = ".Statictext";
		String nameTag = ".name";
		int maxWait = 30, wait=0;
		TestObject runObj;
		//TestObject,nameObj,recordObj;
		String run = "Script\\sRunning.*";
		//String name = "";
		//String record = "[0-9]\\sRecords";
		
		if(dismissPopup){
			dismissPopup(-1,".*Test Passed.*|.*Are you sure.*|.*Save in.*"+
//					"|.*The failures include: vSeq2, vSeq3, vSeq4, vSeq5, vSeq6."+   // For Chinese batch
					"|.*The failures include:.*LOCFun.*"+                                    // For other localized languages
//					"|.*is undefined.*"+
					"|.*Namespace Tree Control.*"+                //Temp workaround for problem on  win7 
			"|Test Failed! %CleanFail%.");
		}

		sleep(2);
		try{
			while((status==null||!status.exists())
					&&wait++<maxWait){			
				if(anchor!=null&&anchor.exists()){
					status = findTestObject(anchor,true,classTag,"ACL_StatusBar");
				}
				sleep(2);
			}
			if(status==null||!status.exists()){
				if(anchor!=null&&anchor.exists()){
					logTAFWarning(autoIssue+"Automation failed to check the ACL status, wait for 10 minutes to check the result istead");
					sleep(10*60);
				}
				return false;
			}
			runObj = findTestObject(status, classTag,classValue,nameTag,run );
			if(runObj==null){
				//printObjectTree(status);
				running = false;
				logTAFDebug("is running script returns '"+runObj+"'");
			}else{
				running = true;
				logTAFDebug(runObj.getProperty(".name").toString()+"...");
			}
		}catch(Exception e){
			if(anchor!=null&&anchor.exists()){
				logTAFError(autoIssue+"Automation failed to check the ACL status '"+e.toString()+"', wait for 10 minutes to check the result istead");
				sleep(10*60);
			}
			return false;
		}
		return running;
	}
	//************************** End of main functions *********************************************

	public String setupTestFiles(String filename,String location, String defaultFileExt){
		return setupTestFiles(filename,location,"No",defaultFileExt);
	}
	public String setupTestFiles(String filename,String location, String append, String defaultFileExt){	
		
        return setupTestFiles(filename,location,append,defaultFileExt,"",-1);
	}
	

	public String setupTestFiles(String filename,String location, String append, 
			String defaultFileExt,String mFile, int numFile){	
		String tempMasterFile = "",tempActualFile = "", fName = "";
		
        if(filename==null||filename.equals("")){
        	//logTAFWarning("Empty file name");
        	return "";
        }
        if(location.equals("")||location.equals("TBD")){
        	logTAFWarning("Set test files to local by default?");
        	location = "Local";
        }
		String localName = filename;
		
		// ********* Processing for run script **************
		File aFile = new File(filename);
		File masFile = new File(mFile);
		if(command.matches("RunScript")){
			if(!aFile.isAbsolute()){
				if(location.equalsIgnoreCase("Server")){
					filename = ProjectConf.curLabel+":\\"+filename;
					aFile = new File(filename);
				}else{
				    filename = FileUtil.getAbsDir(filename,kUtil.workingDir);	
				}
			}						
			if(mFile.equals("")){
				fName = aFile.getName();
				tempMasterFile = FileUtil.getAbsDir(fName,
						ProjectConf.expectedDataDir+defaultMenu+"/"+command+"/"+localName+"/");				
			}else{
				if(!masFile.isAbsolute()){
					tempMasterFile = FileUtil.getAbsDir(mFile,
							ProjectConf.expectedDataDir);	
				}else{
					fName = masFile.getName();
					tempMasterFile = FileUtil.getAbsDir(fName,
							ProjectConf.expectedDataDir+defaultMenu+"/"+command+"/"+localName+"/");		
					if(!tempMasterFile.equalsIgnoreCase(mFile)){
						FileUtil.copyFile(mFile,tempMasterFile);
					}
				}
			}
		}else{
			tempMasterFile = FileUtil.getAbsDir(filename,ProjectConf.expectedDataDir);
		}
        // **************************************************************************
   	    
        if(location.equalsIgnoreCase("Server")){
        	localName = FileUtil.getAbsDir(filename,ProjectConf.tempServerNetDir);
    		filename = FileUtil.getAbsDir(filename,ProjectConf.tempServerDir);
        }else{
    		filename = FileUtil.getAbsDir(filename,ProjectConf.tempLocalDir);	
    		localName = filename;    		              		
    	}
        
        localName = FileUtil.getFullNameWithExt(localName,defaultFileExt);
        actualACLFile = filename;
        tempActualFile = FileUtil.getFullNameWithExt(localName,defaultFileExt);
    	tempMasterFile = FileUtil.getFullNameWithExt(tempMasterFile,defaultFileExt);
    	
        if(append.equalsIgnoreCase("Yes")){       	
//        	if(dpOpenProject.matches("OpenNew")||numKWs==1){
//        		delFile = true;
//        	}else{
//        	    delFile = false;
//        	}    
        	String appendText = "Test\\n\\Append\\nTo\nFile\\n.......";
        	tempMasterFile += "[Append"+(currentTestLine-1)+"]";
            delFile = true;
            try{
        	   FileUtil.delFile(localName);       
        	   sleep(2);
        	   FileUtil.writeFileContents(localName, appendText);
        	   delFile = false;
            }catch(Exception e){
            	//Failed to prepare file, del ori file instead.
            }
        	
        }
        
        

    	logTAFDebug("DelFile is '"+delFile+"'");
    	FileUtil.mkDirs(tempMasterFile);
    	FileUtil.mkDirs(tempActualFile,delFile);  
 
		if(numFile>-1){
			 dpMasterFiles[numFile]  = tempMasterFile;
			 dpActualFiles[numFile]  = tempActualFile;
		}else{
		     dpMasterFile = tempMasterFile;
		     dpActualFile  = tempActualFile;
		}
		

    	return filename;
	}
	//TODO Insert shared functionality here
	public boolean confirmACLError(boolean isWarning){		
		String winTitle = LoggerHelper.autTitle;
		String className = "#32770";
		
		return dLog.dismissPopup(isWarning)||//dLog.confirmAction("No|Cancel")||
	       dismissWinDialog(winTitle,className,isWarning);
	}
	public void onObjectNotFound( 
			ITestObjectMethodState testObjectMethodState) { 
	} 

	  public void onAmbiguousRecognition(ITestObjectMethodState testObjectMethodState,
	          TestObject[] choices,
	          int[] scores){
              super.onAmbiguousRecognition(testObjectMethodState, choices, scores);
	  }
	  
	  //*******************************************************************************************
	  //**************** Shared methods for ACL Desktop - Main,More and Output ********************//
	  //********************************************************************************************
	  
		protected Point[] tabPoint = {atPoint(1,1),atPoint(20,-10),atPoint(60,-10),atPoint(100,-10)};
		protected Point[] tabMainPoint = {atPoint(1,1),atPoint(33,48),atPoint(73,48),atPoint(113,48)};

		public void setScope(TestObject anchor,String scope,String wCondition ){
			int firstIndex = 0, nextIndex = 1, whileIndex = 2;
			String scopeArr[] = scope.split("\\|");
			if(scopeArr[0].equals("All")){
				actionOnCheckbox(findRadiobutton(anchor,"All"),"All",true,"New");
			}else if(scopeArr[0].equals("First")){
				actionOnCheckbox(findRadiobutton(anchor,"First"),"First",true,"New");
				if(scopeArr.length>1){
					actionOnText(findEditbox(anchor,true,firstIndex),"First",scopeArr[1],"New");
				}
			}else if(scopeArr[0].equals("Next")){
				actionOnCheckbox(findRadiobutton(anchor,"Next"),"Next",true,"New");
				if(scopeArr.length>1){
					actionOnText(findEditbox(anchor,true,nextIndex),"Next",scopeArr[1],"New");
				}
			}		
			if(!wCondition.equals("")){
			    actionOnText(findEditbox(anchor,true,whileIndex),"While",wCondition,"New");
                //dismissPopup();
			}
		}
		
		public String setFileType(TestObject anchor, String fileType){
			
			if(fileType.matches("Unicode Text File|ASCII Text File")){
				fileExt = ".TXT";
			}else if(fileType.equals("HTML Text File")){
				fileExt = ".HTM";
			}else if(fileType.equals("ACL Table")){
				fileExt = ".FIL";
			}
			if(!fileType.equals("")){
				actionOnSelect(findComboBox(anchor,true,0),"File Type",
						fileType,"New");
			}else{
				try{
				   fileType = getEngValue(findComboBox(anchor,true,0).getSelectedText());
				}catch(Exception e){
					logTAFDebug("There is no File Type seclection for this test?");
				}
			}
			return fileType;
		}
		public void setOptionalHeaderAndFooter(TestObject anchor,String header,String footer,int guiIndex){
			TextGuiTestObject headerGui,footerGui;
			if(!header.equals("")){
	        	headerGui = findEditbox(anchor,true,guiIndex);
	        	if(headerGui!=null&&isEnabled(headerGui,false))
			       actionOnText(headerGui,"Header",header,"New");	
	        	else{
	        		if(!dpTo.equals("File|Screen")||dpFileType.equals("ACL Table")){
	        		    logTAFWarning("Header option is not enabled For output to '"+dpTo+"'"+
	        		    		(dpFileType.equals("ACL Table")?" - "+dpFileType:""));
	        		}else{
	        		   logTAFError("Header option is not enabled for output to to '"+dpTo+"'?");
	        		}
	        	}
	       }
	       if(!footer.equals("")){
	    	   footerGui = findEditbox(anchor,true,guiIndex+1);
	    	   if(footerGui!=null&&isEnabled(footerGui,false))
	    		   actionOnText(footerGui,"Footer",footer,"New");	
	        	else{
	        		if(!dpTo.matches("File|Screen")||dpFileType.equals("ACL Table")){
	        			 logTAFWarning("Footer option is not enabled For output to '"+dpTo+"'"+
		        		    		(dpFileType.equals("ACL Table")?" - "+dpFileType:""));
	        		}else{
	        		   logTAFError("Footer option is not enabled for output to file?");
	        		}
	        	}
			     
	      }
		}
		
		public String setToLocal(String localOrServer, ToggleGUITestObject tgo){
			boolean enabled = false,selected=false;

			if((tgo==null||dpTo.matches("Screen|Graph|Print"))//&&!dpFileType.equalsIgnoreCase("ACL Table"))
					||!tgo.exists()||!propertyMatch(tgo,".visible","true",false)){
				return "Local";  
			}
			

			   selected = isChecked(tgo);  // if not exists, default to local -> selected
			   enabled = isEnabled(tgo,false); // if not exists, default to !enabled
	
			if(localOrServer.equals("")||localOrServer.equals("TBD")){
				if(selected){
					localOrServer = "Local";
				}else{
					if(!enabled&&defaultMenu.matches("Analyze")){
						//logTAFWarning("For Analyze, an unchecked 'Local' box doesn't mean it's for Server if it's disabled");
						localOrServer = "Local";
					}else{
					    localOrServer = "Server";
					}
				}
			}else if(enabled){
				actionOnCheckbox(tgo,"Local", 
						localOrServer.equalsIgnoreCase("Local")?true:false,"New");
			}else{
				logTAFWarning("Local option for this test is diabled?");
			}
			
			return localOrServer;

		}
		
        public boolean selectedFromFields(GuiSubitemTestObject winDialog,
        		String keyOn, String fields){
        	boolean done = true;
        	if(fields.equals(""))
        		return false;

    		if(fields.equalsIgnoreCase("Add All")){			
    			click(findPushbutton(winDialog,keyOn),keyOn);	
    			selectedFields("Add All",fields,"OK");
    		}else if(isExpression(fields)){
    			click(findPushbutton(winDialog,keyOn),keyOn);		
    			selectedFields("Expr...",fields,"OK");			
    		}else  if(isFromRelation(fields)){
    			click(findPushbutton(winDialog,keyOn),keyOn);	
    			selectedFields("-->",fields,"OK");			
    		}else{
    		   done = false;
    		}
    		
    		return done;
        }
		public void selectedFields(String command,String fields,String endWith){
			String winTitle = "Selected Fields";
			
			TopLevelTestObject winDialog = new TopLevelTestObject(findTopLevelWindow(winTitle)) ;

			
			if(command.equalsIgnoreCase("Add All")){
				click(findPushbutton(winDialog,command),command);
			}else if(command.equalsIgnoreCase("Clear All")){
				click(findPushbutton(winDialog,command),command);
			}else if(command.equalsIgnoreCase("Expr...")){
				click(findPushbutton(winDialog,"Expr..."),"Expr...");
				sleep(1);
				//String winTitleExp = "Expression Builder - Selected Fields: Add expression";
				endWith = buildExpression(fields,"","OK");
//				String winTitleExp = "Expression Builder.*";// - Selected Fields: Add expression";
//				TopLevelTestObject winDialogExp = new TopLevelTestObject(findTopLevelWindow(winTitleExp)) ;
//				actionOnText(findEditbox(winDialogExp,true,0),"Expression",fields,"New");	
//				if(endWith.equalsIgnoreCase("OK")){
//					click(findPushbutton(winDialogExp,"OK"),"OK");
//					if(dismissPopup()){
//						click(findPushbutton(winDialogExp,"Cancel"),"Cancel");
//						endWith = "Cancel";
//					}
//				}else{
//					click(findPushbutton(winDialogExp,"Cancel"),"Cancel");
//				}
			}else if(command.equalsIgnoreCase("-->")){
				String fieldsArr[] = fields.split("[\\|,]");
				String fromTable, fieldName;
				TextSelectGuiSubitemTestObject  fromT= findComboBox(winDialog,true,0);
				GuiTestObject addButton = findPushbutton(winDialog,command);
				SelectGuiSubitemTestObject availableFields;// = findTable(winDialog,true,0);

				for(int i=0;i<fieldsArr.length;i++){
					String[] itemArray = fieldsArr[i].split("\\.");
					if(itemArray.length<2){
						fromTable = itemName;
						fieldName = itemArray[0];
					}else{
						fromTable = itemArray[0];
						if(fromTable.equalsIgnoreCase(itemName)){
							fieldName = itemArray[1];
						}else{
							fieldName = fieldsArr[i];
						}
					}
					
					actionOnSelect(false,fromT,"From Table",fromTable,"New");
					sleep(1);
					
					availableFields = findTable(winDialog,true,1); 
					if(availableFields==null){
						availableFields = findTable(winDialog,true,0); 
					}
					if(availableFields!=null){
					 selectSomeFields(availableFields,fieldName);
					 click(addButton,command);
					}else{
						logTAFWarning("Failed to find Availabe fields to select?");
						endWith = "Cancel";
					}
				}

			}else{
				
			}
			
			if(endWith.equalsIgnoreCase("OK")){
				click(findPushbutton(winDialog,"OK"),"OK");
				if(dismissPopup()){
					click(findPushbutton(winDialog,"Cancel"),"Cancel");
					endWith = "Cancel";
				}
			}else{
				click(findPushbutton(winDialog,"Cancel"),"Cancel");
			}
		}
		
		public String buildExpression(String... options){
			String endWith = "OK"; //Default action  lv_WinName_DLG_EXPR_DIALOG_24_27_5_6_DEFPUSHBUTTON_1 - OK
			                       // lv_WinName_DLG_EXPR_DIALOG_24_27_5_6_PUSHBUTTON_8 - Verify
			if(options==null||options.length==0){
				return "Cancel";
			}
			
			String winTitleExp = "Expression Builder.*";// - Selected Fields: Add expression";
			
			TopLevelTestObject winDialogExp = new TopLevelTestObject(findTopLevelWindow(winTitleExp)) ;

			if(options.length>0&&!options[0].equals("")){
				actionOnText(findEditbox(winDialogExp,true,0),"Expression",options[0],"New");	
			}
			if(options.length>1&&!options[1].equals("")){
				actionOnText(findEditbox(winDialogExp,true,1),"Save As",options[1],"New");	
			}
			
			if(options.length>2&&!options[2].equals("")){
				endWith = options[2];
			}
			
				click(findPushbutton(winDialogExp,"lv_WinName_DLG_EXPR_DIALOG_24_27_5_6_DEFPUSHBUTTON_1"),endWith);
				if(dismissPopup("Any",false,true)){//userAction,boolean isInfo, boolean loop
					click(findPushbutton(winDialogExp,"Cancel"),"Cancel");
					endWith = "Cancel";
				}
			
			return endWith;
		}
		public static int[] selectSomeFields(SelectGuiSubitemTestObject sgto, String... fs){
            
			Point firstRow = atPoint(50,30),
			      upbarbutton = getGuiRelativePoint(sgto, "topright", atPoint(-12,12)),
			      downbarbutton = getGuiRelativePoint(sgto, "bottomright", atPoint(-12,-30));
			String fields = fs[0];
			String oriName = "lv_332";//"Name";
		    if(fs.length>1){
		    	oriName = fs[1];
		    }
			String headerName = getLocValue(oriName);
			String fieldsArr[] = fields.split("[\\|,]");
			boolean resetRoot = false;
		    int[] rowIndex = new int[fieldsArr.length];
		    
		    
		    boolean swaped = false;
		    //printObjectTree(sgto);
	
			for(int i=0;i<fieldsArr.length;i++){
				rowIndex[i] = searchTableRowByText(sgto,
						headerName,fieldsArr[i]);
				
				if(rowIndex[i]<0){
					
					rowIndex[i] = searchTableRowByText(sgto,
							oriName,fieldsArr[i]);
					if(rowIndex[i]<0){
						logTAFError("Field not found - '"+fieldsArr[i]+"'");
						continue;
					}else{
						logTAFWarning("Table header '"+oriName+"' should be localized as '"+headerName+"'");
						headerName = oriName;
						if(fs.length>1){
					    	fs[1]=oriName;
					    }
					}
				}
				
	            if(i!=0&&(rowIndex[i]==0)){ // Force the first row to be selected as the first 
	            	rowIndex[i] = rowIndex[0];
	            	String temp = fieldsArr[i];
	            	fieldsArr[i] = fieldsArr[0];
	            	fieldsArr[0] = temp;
	            	rowIndex[0] = 0;
	            	swaped = true;
	            }
			}
			//printObjectTree(main_fieldstable());
			for(int i=0,curIndex=0;i<rowIndex.length;i++){
				if(rowIndex[i]<0){
					continue;
				}
				logTAFInfo("Select field "+(i+1)+": '"+fieldsArr[i]+"' at Row: '"+(rowIndex[i]+1)+"'");		
				Subitem cell = atCell(atRow(atIndex(rowIndex[i])),atColumn(headerName));
				try{
				     locateTreeRoot(sgto,false);
				}catch(Exception e){
					resetRoot = true;
					logTAFDebug("Exception caught when locating tree root '"+e.toString()+"'");
				}
				if(i==0){
	                if(rowIndex[i]==0){
	                	//It's a workaround for the selection of the first row 
	                	//To ensure works, the firstRow must to be selected as the first
	                	if(swaped){
	                		logTAFDebug("This is a workaround for the issue on select first row");
	                	}
	                	//sgto.doubleClick(firstRow);
	                	logTAFDebug("Click first row at '"+firstRow+"'");
	                	sgto.click(firstRow);
	                	sleep(0);
	                }else{
	                	sgto.setState(Action.select(),cell);
	                	curIndex = rowIndex[i];
	                }
				}else{		
					if(resetRoot){
   //					for(int m=-3;m<rowIndex[i]-curIndex;m++){
   //						sgto.click(downbarbutton);
   //					}
					    for(int j=-3;j<curIndex-rowIndex[i];j++){
						  sgto.click(upbarbutton);
					    }
					    resetRoot = false;
					}
					//locateTreeRoot(sgto);
					sgto.setState(Action.extendSelect(),cell);
					curIndex = rowIndex[i];
				}			
			}
			//printObjectTree(main_fieldstable());
			//sleep(0);
			return rowIndex;
		}
        public boolean setDateTime(String winTitle,String thisDate,int calIndex){
        	Calendar calendar = null;
        	SimpleDateFormat df = null;
        	java.util.Date dt = null;
        	try {
        		df = new SimpleDateFormat(datePattern);
        		dt = df.parse(thisDate);
				calendar = DateFormat.getInstance().getCalendar();
                calendar.setTime(dt)	;			
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				logTAFError("Run date format '"+thisDate+"', it must be '"+datePattern+"'");
				return false;
				//e.printStackTrace();
			}
			return pickDateTime(winTitle,calIndex,calendar);
        }

     public boolean isExpression(String input){
    	 String pattern = ".*[\\(\\*/].*";
    	 if(input.matches(pattern)){
    		 return true;
    	 }else{
    		 return false;
    	 }
     }
     
     public boolean isFromRelation(String input){
    	 String pattern = ".*[\\.].*";
    	 if(input.matches(pattern)){
    		 return true;
    	 }else{
    		 return false;
    	 }
     } 
     
     // **** Handle task progress bar (possible multi threading...)
     
     public boolean handleProgressBar(String command ){
    	 return handleProgressBar(null,command,dpEndWith,false);
     }
     public boolean handleProgressBar(TestObject progressInfo,String command, boolean isMultiTask){
    	 return handleProgressBar(progressInfo,command,dpEndWith,isMultiTask);
     }
     public boolean handleProgressBar(TestObject progressInfo,String command,String action, boolean isMultiTask){
    	// This multitask won't be implemented in Monaco now, so test popup window and progression only  
    	              if(!isMultiTask)
    	            	  return true;
    	              
    	TestObject progressBar = null;                  	              
 		String winTitle = "Status of Task"+
                              "|.*Progress\\.\\.\\."+
                              "|Import Status";
        String winClass = "#32770";
        
        if(progressInfo==null
        		||!progressInfo.exists()        		
 //       		||!isBoundsVisiable(progressInfo)
 //       		||!propertyMatch(progressBar,".name",winTitle,false)
        		){
           progressBar=findTopLevelWindow(winTitle,winClass);
        }else{
        	progressBar = progressInfo.getParent();
        }
        if(progressBar==null
        		||!progressBar.exists()
//        		||!isBoundsVisiable(progressBar)
//       		||!propertyMatch(progressBar,".name",winTitle,false)
        		){
           return true;
        }
        
        TopLevelTestObject popup;
        String actualTitle = "",locTitle;
        
		            //printObjectTree(progressBar);
		            
        try{
	        popup = new TopLevelTestObject(progressBar);
        }catch(Exception e){
        	logTAFDebug("Window not found "+e.toString());
        	return true; // It's not a toplevelwindow;
        }
      // 1. *** popup found, get the actual Title then convert to english
		IWindow iw = null;
		
			
		try{
		    actualTitle = popup.getProperty(".name").toString();
		}catch(Exception e){
			actualTitle ="";
		}
		if(actualTitle.equals("")){
			try{
				actualTitle = getActiveWinTitle();
			}catch(Exception e){
				actualTitle="";
			}	
		}
		//actualTitle = getActiveWinTitle();
		//locTitle = getLocValues(actualTitle);
      
		if(isMultiTask){
			//Do other job while 'in progress...'
			
			checkProgressInfo(progressBar=progressInfo.getParent(),false);
			doSomething(command);
			checkProgressInfo(progressBar=progressInfo.getParent());
		}
		
		if(!action.equalsIgnoreCase("Cancel")){
			// Wait until finish
			int maxProcessTime=60,atime=0;
			while((progressBar=progressInfo.getParent())!=null
					&&progressBar.exists()
					//&&isBoundsVisiable(progressBar)
					&&atime++<maxProcessTime){
			  logTAFInfo("Status of task: In progress..., wait for 10 seconds");
			  checkProgressInfo(progressBar=progressInfo.getParent());
			  sleep(10);
			}
		}
       return cancelRunningTask(progressBar=progressInfo.getParent(),action);
     }   

     public void doSomething(String command){
    	 //TBD
    	 String script = "LOCALCompare";
    	 String tb = "InventoryW";
    	 boolean devCompleted = false;
    	 
    	 if(!devCompleted){
    		 sleep(15);
    		 return;
    	 }
    	 logTAFInfo("Act while import is in progress: ");
    	 
    	 try{    
    		 aRou.activateACL();
    		 aRou.exeACLCommand("DO "+script);
    		 //dismissPopup("ACL.*","",true);
    		 aRou.searchSubitems(tb,true);    	
    	 }catch(Exception e){
    		 logTAFError("Failed to do something while importing...\n\t\t"+e.toString());
    	 }
     }
     
     static String[] pinfo ={"ACL is defining a file. Please wait...","Read:","0","Written:","0",
         "Elapsed:","0","Reamaining:","0","%Complete","0"};
     
     public void checkProgressInfo(TestObject pb){
    	 checkProgressInfo(pb,true);
     }
     public void checkProgressInfo(TestObject pb,boolean compare){
    	 //TBD
    	 logTAFInfo("Check information in progress bar:");
    	 if(pb==null||!pb.exists())
    		 return;
    	 
    	 if(!compare){    		 
    		 try{
    			 FrameSubitemTestObject pbf = (FrameSubitemTestObject) pb;
    		    pbf.minimize();
    		    logTAFStep("Minimize the progress popup window");
    		    sleep(2);
    		    pbf.restore();
    		    logTAFStep("Restore the progress popup window");
//    		    sleep(2);
//    		    pbf.maximize();
//    		    logTAFStep("Maximize the progress popup window");
    		 }catch(Exception e){
    			 logTAFError("Failed to minimize/maximize/restore the progress popup window "+e.toString());
    		 }
    	 }
    	 
    	 TestObject[] progressInfo = pb.find(atChild(".class","Static"));
    	 
    	 if(progressInfo==null||progressInfo.length<1)
    		 return;
    	 
    	 for(int i=0;i<progressInfo.length;i++){
    		 String name = "";
    		 int index = -1;
    		 try{
    			 //name = progressInfo[i].getDescriptiveName();
    		     name = progressInfo[i].getProperty(".name").toString().trim(); 
    		     index =  Integer.parseInt(progressInfo[i].getProperty(".classIndex").toString()); 
    		 }catch(Exception e){
    			 name = "";
    			 index = -1;
    		 }
    		 if(index<0||index>pinfo.length-1){
    			 continue;
    		 }
    		 
    		 if(index>0&&index%2==0){
    			 if(compare){
    				 int num1 = getInteger(pinfo[index]);
    				 int num2 = getInteger(pinfo[index]);
    				 if(index!=8){
    					 if(num2<num1){
    						 logTAFError("Current "+pinfo[index-1]+" '"+name+"' smaller than pre num captured '"+pinfo[index]);
    					 }else{
    						 logInfo("Current "+pinfo[index-1]+" '"+name+"' - pre num captured '"+pinfo[index]);
    					 }
    				 }else{ //Remaining time -- depends on the impl... Steven
    					 if(num2>num1){
    						 logTAFWarning("Current "+pinfo[index-1]+" '"+name+"' grater than pre num captured '"+pinfo[index]);
    					 }else{
    						 logInfo("Current "+pinfo[index-1]+" '"+name+"' - pre num captured '"+pinfo[index]);
    					 }
    				 }
    			 }else{
    				 logInfo("Initial check - "+pinfo[index-1]+" '"+name+"'");
    			 }
    		 }
    		pinfo[index]=name; 
    	 }
     
     }
     public int getInteger(String str){
    	 str = str.replaceAll(",", "");
    	 String[] tStr = str.split(":");
    	 int value = -1;
    	 try{
    		 if(tStr.length>1){
    			 value = Integer.parseInt(tStr[0])*60+
    			 Integer.parseInt(tStr[1]);
    		 }else{
    			 value = Integer.parseInt(tStr[0]);
    		 }
    	 }catch(Exception e){
    		 value = -1;
    	 }
    	 return value;
     }
     public boolean cancelRunningTask(TestObject pb, String action){
    	 //TBD 
    	 GuiTestObject cancelButton = findPushbutton(pb,"Cancel");
    	 if(cancelButton!=null&&cancelButton.exists()//&&isBoundsVisiable(cancelButton)
    			 ){
    		 click(cancelButton,"Cancel");
    		 sleep(1);
    		 if(cancelButton.exists()){
    			 click(cancelButton); 
    			 sleep(1);
    		 }
    		 if(!action.equalsIgnoreCase("Cancel")){
    			 logTAFWarning("Task canceled !!!");
    		 }else{
    			 logTAFStep("Cancel Import...");
    		 }
    		 dismissPopup();
    		 return false;
    		 
    	 }else {
    		 if(pb.exists()//&&isBoundsVisiable(pb)
    				 ){
    		   logTAFError("Cancel button not found!");
    		 }
    	 }
    	 
    	 return true;
     }
     
    //************ 
     public void doVerification(String verifyType ){
    	 doVerification(verifyType,false);       
     }
 	public void doVerification(String verifyType,boolean otionalItem){
		String logView = "Log"; // When file and not use output table
				
		// Verify ACL resulted file
		if(fileCreated&&verifyType.equals("File")&&fileComparable){
		    compareTextFile(dpMasterFile, dpActualFile,
				ProjectConf.updateMasterFile,verifyType);		  
		}
		if(verifyType.equalsIgnoreCase("Clipboard")){
			textResult = getSystemClipboard().getText().trim();	
			compareTextResult(textResult,verifyType);  
		}
		// Verify Log View, Screen (and possible graph/print)
		if(fileCreated&&!dpUseOutputTable.equalsIgnoreCase("Yes")){		
			
		   if(verifyType.equalsIgnoreCase("File")||
				   verifyType.equalsIgnoreCase("Clipboard")){
			         verifyType = logView;
		   }
		   textResult = gObj.getTestDataFromResultPage(verifyType);	
		   compareTextResult(textResult,verifyType);  
		}
		
		// Verify ACL Table from the tree view
		if(fileCreated&&itemCreated){
			 aRou.searchSubitems(actualName,"Navigator",otionalItem);
			}
		
		saveProjectToServer();
 	}
 	
 	public void compareTextResult(String result,String verifyType){
 		if(textResult==null||textResult.contains("!\n")){
			   return;
		   }
		  compareTextFile(dpMasterFile, dpActualFile, textResult,
						ProjectConf.updateMasterFile,verifyType);
 	}
 	public void saveProjectToServer(){

 		if(!testResult.equals("Fail")){ 
 			//Log file could be very big, to save disk space on server,
            //we avoid unnecessary achieve 
 			return;
 		}
 		String aclFolder;
 		String[] files = {".ACL",".LOG",".AC",".LIX"};
 		
 		boolean filesonly = true;
 		if(dpArchiveProject.equalsIgnoreCase("1")){
 			filesonly = false;
 		}
 		
		if(!dpArchiveProject.equalsIgnoreCase("")){
						
			aclFolder = workingDir_Server+"\\[Datapool]"+currentCSVName+
			   "\\Line_"+currentTestLine+"\\"+keywordUtil.workingProject+"\\";
		    
			try{
			   FileUtil.mkDirs(aclFolder+files[0]);
			   if(!filesonly){
			       FileUtil.copyFiles(workingDir+"*", aclFolder); //No subfolder
			       //FileUtil.copyDir(workingDir, aclFolder); //With subfolder
			   }else{				   
				   for(String file:files){
					   FileUtil.copyFile(workingDir+keywordUtil.workingProject+file,aclFolder);
				   }
			   }
			   projectArchived = true;
			   message =(message.equals("")? "" : message+"\n***\t"+"<a style=\"background-color:#886A08\" href=\"file:///"
                   + aclFolder +"\">"+"[Project Archive]</a>");
			}catch(Exception e){
				logTAFWarning("Problem to access '"+aclFolder+"'");
			}
		}
 	}
}
