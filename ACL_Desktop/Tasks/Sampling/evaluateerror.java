package ACL_Desktop.Tasks.Sampling;

import resources.ACL_Desktop.Tasks.Sampling.evaluateerrorHelper;
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

public class evaluateerror extends evaluateerrorHelper
{
	/**
	 * Script Name   : <b>evaluateerror</b>
	 * Generated     : <b>2012-03-18 1:30:56 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/18
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
	private String dpInterval;          //@arg the size of interval
	private String dpErrors;            //@arg Errors
	                                    //@value format like '100|100 300|100,30'
	private String dpSampleSize;        //@arg the sample size
	private String dpNumberErrs;        //@arg number of errors
    // END of datapool variables declaration

    @Override
    public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Sampling";
		command = "EvaluateError";
		winTitle = "Evaluate";
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
	    dpInterval = getDpString("Interval");
	    dpErrors = getDpString("Errors");
	    dpSampleSize = getDpString("SampleSize"); 
	    dpNumberErrs = getDpString("NumberErrs"); 
    	
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
    		if(!dpInterval.equals("")){
    			actionOnText(findEditbox(tabDialog,true,2),"Interval",dpInterval,"New");	
    		}
    		if(!dpErrors.equals("")){
    			actionOnText(findEditbox(tabDialog,true,4),"Errors",dpErrors,"New");	
    		}
		}else if (dpSampleType.equalsIgnoreCase("Record")){
	    		actionOnCheckbox(findRadiobutton(tabDialog,dpSampleType),dpSampleType,true,"New");
	    		if(!dpConfidence.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,0),"Confidence",dpConfidence,"New");	
	    		}
	    		if(!dpSampleSize.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,1),"Sample Size",dpSampleSize,"New");	
	    		}
	    		if(!dpNumberErrs.equals("")){
	    			actionOnText(findEditbox(tabDialog,true,3),"Number of Errors",dpNumberErrs,"New");	
	    		}	    		
		} else {
			logTAFError("The input of Sample type incorrect!");
		}			
	}
	
    public void thisOutputTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisOutputTab(tabDialog);
    }

}

