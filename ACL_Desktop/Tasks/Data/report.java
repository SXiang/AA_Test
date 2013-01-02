package ACL_Desktop.Tasks.Data;

import java.awt.Point;

import resources.ACL_Desktop.Tasks.Data.reportHelper;
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

public class report extends reportHelper
{
	/**
	 * Script Name   : <b>report</b>
	 * Generated     : <b>Mar 2, 2012 4:53:28 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */
	// BEGIN of datapool variables declaration
    //*********************** Shared Variables in SuperHelper *******************
	//***  dpOpenProject
    //***  dpEndWith
    //***  dpProjectName
    //***  dpUnicodeTest
    //***  dpPreCmd	         
    //***  dpPostCmd	 
    //***  dpPreFilter
    //***  dpMenuItem 
    //***  dpSaveLocalOrServer
    //
    
	//*********************** Shared Main test Variables  in SuperHelper ***********
	//***  dpFields;  
	//***  dpExpression; 
	//***  dpIf;     
	//***  dpFileName; 
	//***  dpUseOutputTable; 
	//***  dpAppendToFile;
	//***  dpSaveLocalOrServer="TBD"; 
	
	//***  dpScope; 
	//***  dpWhile;
	
	//***  dpTo;
	//***  dpFileType;
	//***  dpOutputHeader
	//***  dpOutputFooter
	//
		
	private String dpHeader; //@arg Header in Main
	private String dpFooter; //@arg Header in Main  

	private String dpSummarize; //@arg Yes|No
	private String dpSuppressBlank;  //@arg Yes|No	                           
	private String dpSpacing; 	//@arg option for spacing
	                        	//@value = 'Triple Spaced|Single Spaced|Double Spaced'
	private String dpPresort; 	//@arg  Yes|No
	private String dpFitToPage; //@arg 'Yes|No'
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Data";
		command = "Report";
		winTitle = "Report";
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
        
        dpHeader = getDpString("Header");
    	dpFooter = getDpString("Footer");
    	dpSummarize = getDpString("Summarize");
    	dpSuppressBlank = getDpString("SuppressBlank");                         
    	dpSpacing = getDpString("Spacing");
    	dpPresort = getDpString("Presort");
    	dpFitToPage = getDpString("FitToPage");        
       
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}
	
	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    fileCreated = aclEndWith("fileAction");  
	    doVerification(dpTo);
		aRou.exeACLCommands(dpPostCmd);	    
	}
	
	public void aclMainDialog(){
		mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
		
		click(findPagetab(mainDialog, mainTab), mainTab);	
    	tabMain = findSubWindow(mainDialog,true,tabMainName);
		thisMainTab(tabMain);

		click(findPagetab(mainDialog, outputTab), outputTab);	
    	tabOutput = findSubWindow(mainDialog,true,tabOutputName);    	
		thisOutputTab(tabOutput);
		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
        // Enhancing this later ... Steven
//        dataDlog.mainTab_Report(command,
//       		 dpHeader,dpFooter,dpIf,
//       		 dpPresort,dpSummarize,
//       		 dpSuppressBlank,dpSpacing,dpFitToPage);
//
    		sleep(1);
    		if(!tabDialog.exists()){
    			logTAFError("'Main' window not found");
    			return;
    		}
    		if(!dpHeader.equals("")){
    			actionOnText(findEditbox(tabDialog,true,0),"Header",dpHeader,"New");	
    		}
    		if(!dpFooter.equals("")){
    			actionOnText(findEditbox(tabDialog,true,1),"Footer",dpFooter,"New");	
    		}
    		if(!dpIf.equals("")){
    			actionOnText(findEditbox(tabDialog,true,2),"If Condition",dpIf,"New");		
    		}

    		if(!dpPresort.equals("")){
    			actionOnCheckbox(findCheckbutton(tabDialog,"Presort"),"Presort", 
    					dpPresort.equalsIgnoreCase("Yes")?true:false,"New");
    		}	

    		if(!dpSummarize.equals("")){
    			actionOnCheckbox(findCheckbutton(tabDialog,"Summarize"),"Summarize", 
    					dpSummarize.equalsIgnoreCase("Yes")?true:false,"New");
    		}

    		if(!dpSuppressBlank.equals("")){
    			actionOnCheckbox(findCheckbutton(tabDialog,"Suppress.*"),"Suppress blank detail lines", 
    					dpSuppressBlank.equalsIgnoreCase("Yes")?true:false,"New");
    		}

    		if(!dpFitToPage.equals("")){
    			actionOnCheckbox(findCheckbutton(tabDialog,"Fit to page"),"Fit to page", 
    					dpFitToPage.equalsIgnoreCase("Yes")?true:false,"New");
    		}

    		if(!dpSpacing.equals("")){
    			actionOnSelect(findComboBox(tabDialog,0),"Spacing",dpSpacing,"New");
    		}

 
       		        
	}
	
    public void thisOutputTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisOutputTab(tabDialog);

    }
	
    
    
 }

