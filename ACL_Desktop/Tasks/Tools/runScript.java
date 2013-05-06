package ACL_Desktop.Tasks.Tools;

import java.io.File;

import lib.acl.helper.sup.ObjectHelper;
import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.Tools.runScriptHelper;
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

import conf.beans.FrameworkConf;

public class runScript extends runScriptHelper
{
	/**
	 * Script Name   : <b>runScript</b>
	 * Generated     : <b>Apr 11, 2012 4:22:25 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/11
	 * @author Steven_Xiang
	 */
    //*********************** Shared Variables in SuperHelper *******************
	//***  dpOpenProject
    //***  dpEndWith
    //***  dpProjectName
    //***  dpUnicodeTest
    //
    
    protected int numFiles = 0;
	// BEGIN of datapool variables declaration   	
	protected String dpIf; //@arg If condition for running script
	protected String dpScriptName;      //@arg name of the script to be executed
	protected String dpDismissPopup;    //@arg [Yes|No], default to 'No'
	protected String dpDeleteFiles; //@arg delete files before running script.
	                                //@value = [Yes|No], default to 'No'
	protected String dpVerifyFiles; //@arg Required if you want to verify the resulted files.
	                                //@value = [LOCAL]pathTofile1|[SERVER]pathTofile2... (prefix is default to LOCAL)
	                                //@value   :If is a Absolute path, usually means this test is just 
	                                //@value    for either 'Unicode' or 'Non Unicode' build, \
	                                //@value    so there must be a True/False value from dpUnicodeTest
	                                //@value   :If is a relative path, 
	                                //@value     1) with prefix [LOCAL], 
	                                //@value        the path is related to the working directory of this ACL Project
          	                        //@value     2) with prefix [SERVER], 
                                    //@value        the path is relative to the mapped drive for this test,
	                                //@value        either Unicode server, or Non Unicode server
	protected String dpUserMasterFiles; //@arg Optional, if specified, these files will be compared with the resulted file from dpVerifyFiles
	                                //@value =  pathTofile1|pathTofile2...
	                                //@vlaue    All the master files must be in your Local drive or mapped Local drive
	                                //@value    :If relative path used, it's rooted in the dir specified in the ProjConf
	                                //@value     Which is 'ACL_Desktop/DATA/ExpectedData/'
	                                //@value    :If empty, will check default master dir for the file to compare, if not found,
	                                //@value     A resulted file will be copied to the dir as a new master file.
	protected String dpVerifyItems; //@arg paths to items in the tree view
	                                //@value = [dir1->dir2->table1|dir1->dir2->table1...]
	protected String dpVerifyLog; //@arg only if there is a log view at the end of execution.
                                  //@value [Yes|No], default to 'No'
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Tools";
		command = "RunScript";
		winTitle = "Do Script";
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		//if(ProjectConf.isUnicodeTest()){
		    lineDelimiter = aclTableLineFeed;//"\\n[\\s]*[\\r]?\\n";       
		//}
		
        dpIf = getDpString("If");
        dpScriptName = getDpString("ScriptName");
       
        dpDismissPopup = getACLFields("DismissPopup");
        if(dpDismissPopup.equalsIgnoreCase("Yes")){
        	dismissPopup = true;
        }else{
        	dismissPopup = false;
        }
        
        dpDeleteFiles = getACLFields("DeleteFiles");
           if(dpDeleteFiles.equalsIgnoreCase("Yes")){
        	   delFile = true;
           }else{
        	   delFile = false;
           }
           
        dpVerifyItems = getDpString("VerifyItems");
        dpVerifyFiles = getDpString("VerifyFiles");
        dpUserMasterFiles = getDpString("UserMasterFiles");
        dpUserMasterFiles = dpUserMasterFiles.replaceAll("\\|", "\\ |");
        dpVerifyLog = getDpString("VerifyLog");
        
        //printObjectTree(acl_StatusBar());       
		return done;
	}

	public void testMain(Object[] args) 
	{
		//printObjectTree(calendar());
		super.testMain(args);
		setupTestFiles();
	      //setupTestFiles(aFileName,prefix,"No",fileExt,mFile[i]);

		if(!dpScriptName.equals("")){
		    openTest();		
		    aclMainDialog();        
	        
		}else{
			aRou.exeACLCommands(dpPreCmd);
			aRou.setACLFilters(dpPreFilter);
		}
		aRou.exeACLCommands(dpPostCmd);	    
	    doVerification(); //"Log"
		//aRou.exeACLCommands(dpPostCmd);	    
	}
	
	public void setupTestFiles(){
		String prefix = "LOCAL",aFileName = "";
		String aFile[] = dpVerifyFiles.split("\\|"),
		       mFile[] = dpUserMasterFiles.split("\\|");
		String masFile = "";
        if(dpDeleteFiles.equalsIgnoreCase("Yes")){
     	   delFile = true;
        }else{
     	   delFile = false;
        }
		if(!dpVerifyFiles.equals("")){
		   	numFiles = aFile.length;
		   	for(int i=0; i<numFiles; i++){
		   		String[] temp = aFile[i].split("->");
		   		aFileName = temp[temp.length-1];
		   		if(temp.length>0){
		   			prefix = temp[0];
		   		}
		   		//String filename,String location, String append, String defaultFileExt,String mFile
		   		//aFileName = FileUtil.getAbsDir(aFileName,kUtil.workingDir);	
		   		if(mFile.length>i&&!dpUserMasterFiles.equals("")){
		   			masFile = mFile[i].trim();
		   		}else{
		   			masFile = keywordUtil.projName+"\\"+
					  keywordUtil.replaceSpecialChars(dpScriptName)+"\\"+aFileName;
		   		}
		   		setupTestFiles(aFileName,prefix,"No",fileExt,masFile,i);		
		   	}
		}

	}	
	public void doVerification(){

		for(int i=0; i<numFiles; i++){
			compareTextFile(dpMasterFiles[i], dpActualFiles[i],
					ProjectConf.updateMasterFile,"File");		
		
		}
		if(dpVerifyLog.equalsIgnoreCase("Yes")){
			   textResult = gObj.getTestDataFromResultPage("Log");	
			   if(textResult==null||textResult.contains("!\n")){
				   //return;
			   }else{
//			      //setupTestFiles(aFileName,prefix,"No",fileExt,mFile[i]);
				  setupTestFiles(
						  keywordUtil.workingProject+"\\"+
						  keywordUtil.replaceSpecialChars(dpScriptName),
						  "Local","No",fileExt);
			      compareTextFile(dpMasterFile, dpActualFile,textResult,
					ProjectConf.updateMasterFile,"Log");	
			   }
			
		}
		if(!dpVerifyItems.equals("")){
		   aRou.searchSubitems(dpVerifyItems);
		}
		saveProjectToServer();
	} 
	public void aclMainDialog(){
		mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
		
		click(findPagetab(mainDialog, mainTab), mainTab);	
    	tabMain = findSubWindow(mainDialog,true,tabMainName);
		thisMainTab(tabMain);		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		//long maxTime = 1800*1000;
		long maxTime = 45*60*1000;
		long startTime = System.currentTimeMillis( );
		
		boolean isScriptRunning = true;
		String script="",ifexp="";
		String names[] = dpScriptName.split("\\|");
		String ifs[]=dpIf.split("\\|");
		for(int i=0;i<names.length;i++){
			if(i>0){
				if(!defaultMenu.equals("")&&!command.equals(""))
			         kUtil.invokeMenuCommand(defaultMenu+"->"+command);
			}
			script = names[i];
			logItem = script+"@"+kUtil.workingProject; //
			if(i>ifexp.length()-1){
				ifexp = "";
			}else{
				ifexp = ifs[i];
			}

			// Work on tabDialog
			if(!script.equals("")){
				actionOnSelect(listBoxlist(),command,script.trim(),"New");
			}
			if(!ifexp.equals("")){
				actionOnText(findEditbox(tabDialog,true,0),"If...",ifexp,"New");	
			}
			sleep(1);
			// click to Run
	    	click(findPushbutton(mainDialog,"OK"),"OK", true);	
	    	sleep(2);
	    	if(script.equalsIgnoreCase("_Cleanup")){
	    	    dismissPopup("Script Cleanup","Make My Day",true);
	    	}
	    	if(script.equalsIgnoreCase("_AllTest")||
	    			script.equalsIgnoreCase("_总测试")){
	    		
	    	    //dismissPopup("User Dialog","OK",true);
	    	    if(dismissPopup("User Dialog","ENTER",true))
	    	          sleep(2);
//	    	    if(kUtil.checkACLCrash()){
//	    	    	logTAFError("ACL Crashed when running oldbats '_Cleanup' then '_AllTest'");
//	    	    	return;
//	    	    }	    	    
	    	    dismissPopup("OK|Yes",true,true,-1);
	    	   
	    	    waitForExistence(emailDialog());
	    	    //actionOnText(emailbox(),"Email result to:","QAMail@ACL.com","New");	
	    	    actionOnText(findEditbox(emailDialog(),true,0),"Email result to:","QAMail@ACL.com","New");	
	    	    if(!dismissPopup("Who\\sshould\\sbe.*","ENTER",true))
	    	        dismissPopup("谁应该被通报.*","ENTER",true);
	    	    //dismissPopup("Who\\sshould\\sbe.*","LET'S ROLL",true);
	    	    //dismissPopup("谁应该被通报.*","开拔",true);
//	    	    while(!dismissPopup("OK")&&wTime++<maxTime){
//	    	    	sleep(2);
//	    	    }
	    	}
	    	

            
          
			while(isScriptRunning&&System.currentTimeMillis( )<maxTime+startTime){
				unregisterAll();
				sleep(2);
				isScriptRunning=isRunningScript(ACL9(),acl_StatusBar(),dismissPopup);
				//isScriptRunning=isRunningScript(ACL9(),null,dismissPopup);
				if(!isScriptRunning){
				   int mCheck = 1,x=0;
//				   if(!kUtil.isActivated()){
//				      mCheck = 5;
//				   }
				   while(!isScriptRunning&&x++<mCheck){
					   sleep(2);
    			       isScriptRunning = isRunningScript(null,acl_StatusBar(),dismissPopup);
    			       logTAFInfo("Check "+x+": Script is Running..."+isScriptRunning);
				   }
    		    }
		
			}
			
			if(isScriptRunning){
				logTAFError(autoIssue+"This script has been running for "+(long)(maxTime/(60*1000))+
						" minutes which is much longer than usual, check the screenshot and console log for details");
			}else if(!kUtil.isActivated()){
				logTAFWarning("AUT is not activated at the end of this test, please check if there were anything wrong!");
			}
			isScriptRunning = true;
	    	
		}
	}
	

 }
