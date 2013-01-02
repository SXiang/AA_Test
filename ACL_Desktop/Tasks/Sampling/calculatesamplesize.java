package ACL_Desktop.Tasks.Sampling;

import resources.ACL_Desktop.Tasks.Sampling.calculatesamplesizeHelper;
import ACL_Desktop.AppObject_Karen.DesktopSuperHelper;
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

public class calculatesamplesize extends calculatesamplesizeHelper
{
	/**
	 * Script Name   : <b>calculatesamplesize</b>
	 * Generated     : <b>2012-03-16 11:27:59 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/16
	 * @author Karen_zou
	 */
	
	private Object[] options = {null,null,State.selected(),State.selected(),null,null};

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
    //***  dpActOnItem 
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
	
	private String dpSampleType;        //@arg Sample type
    									//@value = 'Monetary|Record'
	private String dpConfidence;        //@arg the reliability to be specified, which the sample is expected to generate.
	private String dpPopulation;        //@arg the number of records in the file being sampled
	private String dpMateriality;       //@arg the maximum amount of error to be accepted without detection 
    									//and related to the amount of money considered significant.  	
	private String dpExpectedTotalErrs; //@arg the total expected amount of errors measured in dollars. 
	private String dpUpperErrLimit;     //@arg the upper limit (measured as a percentage) in order to meet the confidence entered
	private String dpExpectedErrRate;   //@arg the percentage of error expected in data.
	private String dpCalculateBtn;      //@arg '1|0', 1 means click 'Calculate' button, 0 means not click 'Calculate' button. Default to 0
    // END of datapool variables declaration

    @Override
    public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Sampling";
		command = "CalculateSampleSize";
		winTitle = "Size";
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
    	
	    dpSampleType = getDpString("SampleType");
	    if (dpSampleType.equals("")){
	    	dpSampleType = "Monetary";
	    }
	    dpConfidence = getDpString("Confidence");
	    dpPopulation = getDpString("Population");
	    dpMateriality = getDpString("Materiality");
	    dpExpectedTotalErrs = getDpString("ExpectedTotalErrs"); 
	    dpUpperErrLimit = getDpString("UpperErrLimit"); 
	    dpExpectedErrRate = getDpString("ExpectedErrRate"); 
	    dpCalculateBtn = getDpString("CalculateBtn");

    	return done;
}
	
	public void testMain(Object[] args) 
	{
		super.testMain(args);
		
		//Configure Options
		//dLog.aclOptions("Interface", "OK", options);
		
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
        // Enhancing this later ... 
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
    	
		if (dpSampleType.equalsIgnoreCase("Monetary")){
	    	actionOnCheckbox(findRadiobutton(tabDialog,dpSampleType),dpSampleType,true,"New");
    		if(!dpConfidence.equals("")){
    			actionOnText(findEditbox(tabDialog,true,0),"Confidence",dpConfidence,"New");	
    		}
    		if(!dpPopulation.equals("")){
    			actionOnText(findEditbox(tabDialog,true,1),"Population",dpPopulation,"New");	
    		}
    		if(!dpMateriality.equals("")){
    			actionOnText(findEditbox(tabDialog,true,2),"Materiality",dpMateriality,"New");	
    		}
    		if(!dpExpectedTotalErrs.equals("")){
    			actionOnText(findEditbox(tabDialog,true,4),"Expected Total Errors",dpExpectedTotalErrs,"New");	
    		}   		
		}else if (dpSampleType.equalsIgnoreCase("Record")){
	    		actionOnCheckbox(findRadiobutton(tabDialog,dpSampleType),dpSampleType,true,"New");
	    		if(!dpConfidence.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,0),"Confidence",dpConfidence,"New");	
	    		}
	    		if(!dpPopulation.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,1),"Population",dpPopulation,"New");	
	    		}
	    		if(!dpUpperErrLimit.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,3),"Upper Error Limit(%)",dpUpperErrLimit,"New");	
	    		}	    		
	    		if(!dpExpectedErrRate.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,5),"Expected Error Rate(%)",dpExpectedErrRate,"New");	
	    		}	
		} else {
			logTAFError("The input of Sample type incorrect!");
		}			
		
		if(dpCalculateBtn.equalsIgnoreCase("Yes")){
			if (isEnabled(findPushbutton(tabDialog,"Calculate"))){			
				click(findPushbutton(tabDialog,"Calculate"),"Calculate");
				confirmACLError(false);
			}else {
				logTAFError("Calculate button disabled");		
			}			
		}
	}
	
    public void thisOutputTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisOutputTab(tabDialog);
    }
    
}

