package ACL_Desktop.Tasks.Tools;

import java.awt.Point;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import lib.acl.helper.sup.LoggerHelper;
import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.Tools.keywordDifinitionHelper;
import ACL_Desktop.AppObject.aclDataDialogs;
import ACL_Desktop.AppObject.aclRoutines;
import ACL_Desktop.AppObject.dialogUtil;
import ACL_Desktop.AppObject.getObjects;
import ACL_Desktop.AppObject.keywordUtil;
import ACL_Desktop.conf.beans.ProjectConf;

import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class keywordDifinition extends keywordDifinitionHelper
{
	/**
	 * Script Name   : <b>keywordDifinition</b>
	 * Generated     : <b>Apr 11, 2012 4:23:48 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/11
	 * @author Steven_Xiang From DesktopHelper ....
	 */
	
	protected getObjects gObj;
	protected keywordUtil kUtil;
	protected aclDataDialogs dataDlog;
	protected dialogUtil dLog;
	protected aclRoutines aRou;
	protected String defaultMenu;
	protected String itemName = "",
	                serverItemPattern = ".*_DB2|.*_SQLServer|.*_Oracle",
					dpMasterFile,
					dpActualFile,
					actualACLFile,
					fileExt = ".fil",
					actualName = "",
					actualItem = "",
					command = "",
					winTitle = "",
					tabMainName = "",
					tabMoreName = "More...",
					tabOutputName = "Output...",
					textResult = "",
					datePattern = "dd-MMM-yyyy";
	
	protected TopLevelSubitemTestObject mainDialog;
	protected GuiSubitemTestObject tabMain,tabMore,tabOutput;
	
	protected int[] itemIndex;
	protected boolean itemCreated = false,
     				fileCreated = false;
	protected boolean sharedDataDone = false,
		              sharedMainDataDone = false;
	 protected String dpMenuItem=""; //@arg path to or name for the menu item to be tested
     //@value = '[menu->menuitem]|[menuitem], a default path may be provided by each keyword
	 
	// BEGIN of datapool variables declaration
	protected String dpOpenProject=""; //@arg command for open acl project
                                    //@value = 'Open|OpenCancel|OpenNew|OpenWorking|OpenLastSaved|OpenRecent|CreateNew', default to OpenNew
	protected String dpProjectName=""; //@arg Project Name
                                      //@value = 'Name|Name.ACL|Absolute path to project|Relative path to project'
                                      //@value   If ends with '_', the project with suffix - TestType defined in Project.properties will be used 	
    protected String dpUnicodeTest=""; //@arg If this test is for unicode test only or both
                                  //@value = 'True|False', If true, it will only be run with an Unicode AUT 
   
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
	// End of datapool variables declaration
	
	public void testMain(Object[] args) 
	{ 
		
		//@Step Execute pre commands, open table, set up filters
		//@Step Click menu item to be tested

		
		//@Step Work on main tab
		//@Step Work on output tab
	    //@Step Work on more tab		

        
		//@Step Submit work
		//@Step run commands after main test if there are any 

	    //@Step verify results based on the nature of tests
	    //@@Step Compare ACL resulted file with master file 
	    //@@Step Compare ACL resulted log view with master file 
	    //@@Step Compare ACL resulted Screen view with master file 
	    //@@Step Select item from ACL tree view if a new item created during test.	    

		
		super.testMain(args);
		gObj = new getObjects();
		kUtil = new keywordUtil();
		dLog = new dialogUtil();
		aRou = new aclRoutines();
		dataDlog = new aclDataDialogs();
		readSharedTestData();
		kUtil.activateAUT();		
		dpProjectName = kUtil.openProject(dpProjectName,dpOpenProject);

		//openTest();
	}
	public void readSharedTestData(){
		if(sharedDataDone)
			return;
		dpUnicodeTest = getDpString("UnicodeTest");
		//dpPreCmd = getDpString("PreCmd");	         
        //dpPostCmd = getDpString("PostCmd");	 
        dpPreFilter = getDpString("PreFilter");
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
        
        dpEndWith = getDpString("EndWith");	 
		dpActOnItem = getDpString("ActOnItem");	 
	        itemName = getNameFromPath(dpActOnItem);
	    dpPreCmd = getDpString("PreCmd");	
	    
        if(!itemName.equals("")){
          if(dpPreCmd.equals(""))
        	  dpPreCmd = "OPEN "+itemName;
          else
    	   dpPreCmd = dpPreCmd+"|OPEN "+itemName;
         }
         dpPostCmd = getDpString("PostCmd");	
         
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
		if(basedOn.matches(serverItemPattern)
				&&(!isExpression(thisValue)||thisValue.matches(excepts))
				){
			thisValue = thisValue.toUpperCase();
		}
        return thisValue;
	}
	public String getACLFields(String item){
           return getACLFields(item,dpActOnItem);
	}


	// ********** Begin of main functions ************

	public void aclMainFunctions(){  // Shared steps

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
    		if(!dpAppendToFile.equals("")&&appendEnabled){
    			logTAFError("Append To Existing File should be disabled for output of 'ACL Table'");
    		}
    		itemCreated = true;
    	}else if(!dpFileType.equals("")){
    		if(!dpUseOutputTable.equals("")&&useOutputTableEnabled){
    			logTAFError("Use Output Table should be disabled for output of '*** Text File'");
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
		if(task.equalsIgnoreCase("fileAction")){
			fileCreated = endWithAction(mainDialog,dpEndWith);      // Catch message as error
			//fileCreated = endWithAction(mainDialog,dpEndWith,true); //ignore any popup message
		}else if(task.equalsIgnoreCase("OtherAction")){
			//TBD
		}
		if(!kUtil.isActivated()){
			logTAFWarning("AUT is not activated at the end of this test, please check if there were anything wrong!");
		}
		return fileCreated;
	}

	//************************** End of main functions *********************************************
	public String setupTestFiles(String filename,String location, String defaultFileExt){
		return setupTestFiles(filename,location,"No",defaultFileExt);
	}
	public String setupTestFiles(String filename,String location, String append, String defaultFileExt){	
        if(filename==null||filename.equals("")){
        	//logTAFWarning("Empty file name");
        	return "";
        }
        if(location.equals("")||location.equals("TBD")){
        	logTAFWarning("Set test files to local by default?");
        	location = "Local";
        }
		String localName = filename;
    	dpMasterFile = FileUtil.getAbsDir(filename,ProjectConf.expectedDataDir);
    	

        if(location.equalsIgnoreCase("Server")){
        	localName = FileUtil.getAbsDir(filename,ProjectConf.tempServerNetDir);
    		filename = FileUtil.getAbsDir(filename,ProjectConf.tempServerDir);
        }else{
    		filename = FileUtil.getAbsDir(filename,ProjectConf.tempLocalDir);	
    		localName = filename;    		              		
    	}
        
        localName = FileUtil.getFullNameWithExt(localName,defaultFileExt);
        actualACLFile = filename;
        
        if(append.equalsIgnoreCase("Yes")){
        	delFile = false;
        	dpMasterFile += "[Append"+(currentTestLine-1)+"]";
        }
        
    	dpActualFile = FileUtil.getFullNameWithExt(localName,defaultFileExt);
    	dpMasterFile = FileUtil.getFullNameWithExt(dpMasterFile,defaultFileExt);
    	FileUtil.mkDirs(dpMasterFile);
    	FileUtil.mkDirs(localName,delFile);  
    	
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
				   fileType = findComboBox(anchor,true,0).getSelectedText();
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
	        		if(!dpTo.equals("File")||dpFileType.equals("ACL Table")){
	        		    logTAFWarning("Header option is not enabled For output to '"+dpTo+"'"+
	        		    		(dpFileType.equals("ACL Table")?" - "+dpFileType:""));
	        		}else{
	        		   logTAFError("Header option is not enabled for output to file?");
	        		}
	        	}
	       }
	       if(!footer.equals("")){
	    	   footerGui = findEditbox(anchor,true,guiIndex+1);
	    	   if(footerGui!=null&&isEnabled(footerGui,false))
	    		   actionOnText(footerGui,"Footer",footer,"New");	
	        	else{
	        		if(!dpTo.equals("File")||dpFileType.equals("ACL Table")){
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
			

			   selected = isChecked(tgo);
			   enabled = isEnabled(tgo);
	
			if(localOrServer.equals("")||localOrServer.equals("TBD")){
				if(selected){
					localOrServer = "Local";
				}else{
//					if(defaultMenu.matches("Analyze")){
//						logTAFWarning("For Analyze, an unchecked 'Local' box doesn't mean it's for Server if it's disabled");
//						localOrServer = "Local";
//					}else{
					    localOrServer = "Server";
//					}
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
    		}else if(isExpression(dpFields)){
    			click(findPushbutton(winDialog,keyOn),keyOn);		
    			selectedFields("Expr...",fields,"OK");			
    		}else  if(isFromRelation(dpFields)){
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
				String fieldsArr[] = fields.split("[\\|,;]");
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
					
					actionOnSelect(fromT,"From Table",fromTable,"New");
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
			String endWith = "OK"; //Default action
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
			
				click(findPushbutton(winDialogExp,endWith),endWith);
				if(dismissPopup("Any",false,true)){//userAction,boolean isInfo, boolean loop
					click(findPushbutton(winDialogExp,"Cancel"),"Cancel");
					endWith = "Cancel";
				}
			
			return endWith;
		}
		public static int[] selectSomeFields(SelectGuiSubitemTestObject sgto, String fields){
            
			Point firstRow = atPoint(50,30),
			      upbarbutton = getGuiRelativePoint(sgto, "topright", atPoint(-12,12));
			String fieldsArr[] = fields.split("[\\|,;]");
		    int[] rowIndex = new int[fieldsArr.length];
		    
		    String headerName = "Name";
		    
		    boolean swaped = false;
		    //printObjectTree(main_fieldstable());
	
			for(int i=0;i<fieldsArr.length;i++){
				rowIndex[i] = searchTableRowByText(sgto,
						headerName,fieldsArr[i]);
				
				if(rowIndex[i]<0){
					logTAFError("Field not found - '"+fieldsArr[i]+"'");
					continue;
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
	                }
				}else{		
					for(int j=0;j<curIndex-rowIndex[i];j++){
						sgto.click(upbarbutton);
					}
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
     
 	public void doVerification(String verifyType){
		String logView = "Log"; // When file and not use output table
				
		// Verify ACL resulted file
		if(fileCreated&&verifyType.equals("File")){
		    compareTextFile(dpMasterFile, dpActualFile,
				ProjectConf.updateMasterFile,verifyType);		  
		}
		// Verify Log View, Screen (and possible graph/print)
		if(fileCreated&&!dpUseOutputTable.equalsIgnoreCase("Yes")){		
			
		   if(verifyType.equalsIgnoreCase("File")){
			         verifyType = logView;
		   }
		   textResult = gObj.getTestDataFromResultPage(verifyType);	
		   if(textResult==null||textResult.contains("!\n")){
			   return;
		   }
		  compareTextFile(dpMasterFile, dpActualFile, textResult,
						ProjectConf.updateMasterFile,verifyType);

		}
		
		// Verify ACL Table from the tree view
		if(fileCreated&&itemCreated){
			 aRou.searchSubitems(actualName);
			}
 	}



	@Override
	public boolean dataInitialization() {
		// TODO Auto-generated method stub
		return false;
	}
}

