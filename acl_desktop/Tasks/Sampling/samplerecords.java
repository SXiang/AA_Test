package ACL_Desktop.Tasks.Sampling;

import java.io.File;

import resources.ACL_Desktop.Tasks.Sampling.samplerecordsHelper;
import ACL_Desktop.AppObject.keywordUtil;
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

public class samplerecords extends samplerecordsHelper
{
	/**
	 * Script Name   : <b>samplerecords</b>
	 * Generated     : <b>2012-03-20 1:50:59 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/20
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
	//*********************** Shared Output test Variables  in 
    //***  dpTo;
    //***  dpFileType;
    //***  dpOutputHeader
    //***  dpOutputFooter

	private String dpSampleType;        //@arg sample type
    									//@value = 'MUS|Record'
	private String dpSampleOn;          //@arg the field sampling based on
	private String dpSampleParameters;  //@arg sample parameters for MUS or Record sample type
										//@value = 'Fixed Interval|Cell|Random'
	private String dpInterval;          //@arg the size of interval
	private String dpStart;             //@arg start	
	private String dpCutoff;            //@arg cutoff		
	private String dpSeed;              //@arg seed
	private String dpSize;              //@arg sample size	
	private String dpPopulation;        //@arg the number of records in the file being sampled
	private String dpSizeBtn;           //@arg the flag clicking 'Size...' button
										//@value "Yes|''", default is empty
	private String dpConfidence;        //@arg the reliability to be specified, which the sample is expected to generate.
	private String dpMateriality;       //@arg the maximum amount of error to be accepted without detection 
    									//and related to the amount of money considered significant.  	
	private String dpExpectedTotalErrs; //@arg the total expected amount of errors measured in dollars.
	private String dpUpperErrLimit;     //@arg the upper limit (measured as a percentage) in order to meet the confidence entered
	private String dpExpectedErrRate;   //@arg the percentage of error expected in data.
	private String dpCalculateBtn;      //@arg "Yes|''", 'Yes' means clicking 'Calculate' button, Empty means not clicking 'Calculate' button. Default to empty
	private String dpFields;  			//@arg filed name to be extracted, default to 'Record'
										//@value = 'field1|field2|...' or 'Record' or 'Add All' 
	private String dpSubsample; 		//@arg 'Yes|No', default to 'No' 
	private String dpNoRepeats; 		//@arg 'Yes|No', default to 'No' 
    // END of datapool variables declaration

    @Override
    public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Sampling";
		command = "SampleRecords";
		winTitle = "Sample";   
		tabMainName = winTitle; //"_Main";
		
		readSharedTestData();
        readSharedMainTestData();

	    dpSampleType = getDpString("SampleType");    
	    if (dpSampleType.equals("")){
	    	dpSampleType = "MUS";
	    }
	    dpSampleOn  = getDpString("SampleOn");          
	    dpSampleParameters  = getDpString("SampleParameters");  
	    dpInterval = getDpString("Interval");          
	    dpStart = getDpString("Start");             	
	    dpCutoff = getDpString("Cutoff");            		
	    dpSeed = getDpString("Seed");              
	    dpSize = getDpString("Size");              	
	    dpPopulation = getDpString("Population");        
	    dpSizeBtn = getDpString("SizeBtn");           
	    dpConfidence = getDpString("Confidence");        
	    dpMateriality = getDpString("Materiality");        	
	    dpExpectedTotalErrs = getDpString("ExpectedTotalErrs"); 
	    dpUpperErrLimit = getDpString("UpperErrLimit");     
	    dpExpectedErrRate = getDpString("ExpectedErrRate");   
	    dpCalculateBtn = getDpString("CalculateBtn");      

	    dpFields = getDpString("Fields");  			
	    dpSubsample = getDpString("Subsample"); 		
	    dpNoRepeats = getDpString("NoRepeats"); 		 
    
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

		click(findPagetab(mainDialog, moreTab), moreTab);
    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
		thisMoreTab(tabMore);
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
    	sleep(1);
    	if(!tabDialog.exists()){
    		logTAFError("'Main' window not found");
    		return;
    	}
    	
		if (dpSampleType.equalsIgnoreCase("MUS")){
	    	actionOnCheckbox(findRadiobutton(tabDialog,"MUS"),"MUS",true,"New");
			if (dpSampleParameters.equalsIgnoreCase("Fixed Interval")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Fixed Interval"),"Fixed Interval",true,"New");
				if (!dpInterval.equals("")){
					actionOnText(findEditbox(tabDialog,true,0),"Interval",dpInterval,"New");	
				}
				if (!dpStart.equals("")){
					actionOnText(findEditbox(tabDialog,true,1),"Start",dpStart,"New");	
				}
				if (!dpCutoff.equals("")){
					actionOnText(findEditbox(tabDialog,true,4),"Cutoff",dpCutoff,"New");	
				}
			}else if (dpSampleParameters.equalsIgnoreCase("Cell")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Cell"),"Cell",true,"New");
				if (!dpInterval.equals("")){
					actionOnText(findEditbox(tabDialog,true,0),"Interval",dpInterval,"New");	
				}
				if (!dpSeed.equals("")){
					actionOnText(findEditbox(tabDialog,true,3),"Seed",dpSeed,"New");	
				}
				if (!dpCutoff.equals("")){
					actionOnText(findEditbox(tabDialog,true,4),"Cutoff",dpCutoff,"New");	
				}
			}else if (dpSampleParameters.equalsIgnoreCase("Random")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Random"),"Random",true,"New");
				if (!dpSize.equals("")){
					actionOnText(findEditbox(tabDialog,true,2),"Size",dpSize,"New");	
				}
				if (!dpSeed.equals("")){
					actionOnText(findEditbox(tabDialog,true,3),"Seed",dpSeed,"New");	
				}
				if (!dpPopulation.equals("")){
					actionOnText(findEditbox(tabDialog,true,5),"Population",dpPopulation,"New");	
				}
			}else {
				logTAFError("Sample Parameters are incorrect");	
			}
		}else if (dpSampleType.equalsIgnoreCase("Record")){
	    	actionOnCheckbox(findRadiobutton(tabDialog,"Record"),"Record",true,"New");
			if (dpSampleParameters.equalsIgnoreCase("Fixed Interval")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Fixed Interval"),"Fixed Interval",true,"New");
				if (!dpInterval.equals("")){
					actionOnText(findEditbox(tabDialog,true,0),"Interval",dpInterval,"New");	
				}
				if (!dpStart.equals("")){
					actionOnText(findEditbox(tabDialog,true,1),"Start",dpStart,"New");	
				}
			}else if (dpSampleParameters.equalsIgnoreCase("Cell")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Cell"),"Cell",true,"New");
				if (!dpInterval.equals("")){
					actionOnText(findEditbox(tabDialog,true,0),"Interval",dpInterval,"New");	
				}
				if (!dpSeed.equals("")){
					actionOnText(findEditbox(tabDialog,true,3),"Seed",dpSeed,"New");	
				}
			}else if (dpSampleParameters.equalsIgnoreCase("Random")){
				actionOnCheckbox(findRadiobutton(tabDialog,"Random"),"Random",true,"New");
				if (!dpSize.equals("")){
					actionOnText(findEditbox(tabDialog,true,2),"Size",dpSize,"New");	
				}
				if (!dpSeed.equals("")){
					actionOnText(findEditbox(tabDialog,true,3),"Seed",dpSeed,"New");	
				}
				if (!dpPopulation.equals("")){
					actionOnText(findEditbox(tabDialog,true,5),"Population",dpPopulation,"New");	
				}
			}else {
				logTAFError("Sample Parameters is incorrect");	
			}
		}else {
			logTAFError("Sample Type is incorrect");	
		}

		//Select SampleOn
		if (dpSampleType.equalsIgnoreCase("MUS")){
			actionOnSelect(findComboBox(tabDialog,true,0),"Sample On...",dpSampleOn,"New");
		}

		//Check Size button enability
		if ((dpSampleType.equalsIgnoreCase("MUS")&&dpSampleParameters.equalsIgnoreCase("Fixed Interval"))||
			(dpSampleType.equalsIgnoreCase("Record"))){
			if (!isEnabled(findPushbutton(tabDialog,"Size..."))){
				logTAFError("Size button is disabled");					
			}else if (dpSizeBtn.equalsIgnoreCase("Yes")){        //Click Size button();
					click(findPushbutton(tabDialog,"Size..."),"Size...");

					GuiSubitemTestObject tabSize = findSubWindow(sizeDialog(),true,"Size");
					thisSizeTab(tabSize);
			}	
		}	

		//Set Local
    	dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
    	
		itemCreated = setOutputInMain(tabDialog);
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,6),"If Condition",dpIf,"New");	
		}
				
    	if(!dpFileName.equalsIgnoreCase("")){
    		actionOnText(findEditbox(tabDialog,true,7),"To...",actualACLFile, "New");
			//fileCreated = true;	
    	}
	}

	public void thisSizeTab(GuiSubitemTestObject tabDialog){
    	sleep(1);
    	if(!tabDialog.exists()){
    		logTAFError("'Main' window not found");
    		return;
    	}
    	
		if (dpSampleType.equalsIgnoreCase("MUS")){
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
	    			actionOnText(findEditbox(tabDialog,true,4),"Expected Error Rate(%)",dpExpectedErrRate,"New");	
	    		}	
		} else {
			logTAFError("The input of Sample type incorrect!");
		}			
		
		if(dpCalculateBtn.equalsIgnoreCase("Yes")){
			if (isEnabled(findPushbutton(tabDialog,"Calculate"))){			
				click(findPushbutton(tabDialog,"Calculate"),"Calculate");
			}else {
				logTAFError("Calculate button disabled");		
			}	
			if (isEnabled(findPushbutton(tabDialog,"Calculate"))){			
				click(findPushbutton(tabDialog,"Calculate"),"Calculate");
				confirmACLError(false);
			}else {
				logTAFError("Calculate button disabled");		
			}			
		}
	
		//click(findPushbutton(tabDialog,"OK"),"OK");
		click(sample_size_ok(),"OK");
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
    	
		String keyToExp = "Extract Fields...";

		if(dpFields.matches("Record")){
			actionOnCheckbox(findRadiobutton(tabDialog,dpFields),dpFields,true,"New");
		}else{
			actionOnCheckbox(findRadiobutton(tabDialog,"Fields"),"Fields",true,"New");
		}
		
		if(!dpFields.equalsIgnoreCase("Record")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
			selectSomeFields(findTable(tabDialog,true,0),dpFields);
		}
		
		if (!dpFields.equalsIgnoreCase("Record")){
			if (!dpSubsample.equalsIgnoreCase("")){
				actionOnCheckbox(findCheckbutton(tabDialog,"Subsample"),"Subsample",dpSubsample.equalsIgnoreCase("Yes")?true:false,"New");
			}
		}
		
		if(!dpNoRepeats.equalsIgnoreCase("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"No Repeats"),"No Repeats",dpNoRepeats.equalsIgnoreCase("Yes")?true:false,"New");
		} 	
	}

}

