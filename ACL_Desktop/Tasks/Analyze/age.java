package ACL_Desktop.Tasks.Analyze;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import resources.ACL_Desktop.Tasks.Analyze.ageHelper;
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

public class age extends ageHelper
{
	/**
	 * Script Name   : <b>age</b>
	 * Generated     : <b>Mar 24, 2012 3:42:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/24
	 * @author Steven_Xiang
	 */
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


	// BEGIN of datapool variables declaration   	
	protected String dpAgingPeriods; //@arg value for Aging Periods
	                                  //@value = [num1 num2 num3...]
	protected String dpOn;      //@arg field or expression for test
	protected String dpCutoffDate; //@arg cutoff date
	                                //@value dd-MMM-yyyy

	protected String dpSuppressOthers; //@arg 'Yes' or 'No', default to 'No'
	protected String dpBreak; //@arg field name to be broken

	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "Age";
		winTitle = command;
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		
		readSharedTestData();
        readSharedMainTestData();        
        readOutputTestData();
        
        
        dpCutoffDate = getDpString("CutoffDate");
        dpAgingPeriods = getDpString("AgingPeriods");
        dpOn = getACLFields("On");
       
        dpSuppressOthers = getDpString("SuppressOthers");
        dpBreak = getDpString("Break");
        
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}

	public void testMain(Object[] args) 
	{
		//printObjectTree(calendar());
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    aclEndWith("fileAction");  
	    doVerification(dpTo); //"Log"
		aRou.exeACLCommands(dpPostCmd);	
		//getLocValues(dpOn);
	}
	
	public void aclMainDialog(){
		mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
		
		click(findPagetab(mainDialog, mainTab), mainTab);	
    	tabMain = findSubWindow(mainDialog,true,tabMainName);
		thisMainTab(tabMain);

		click(findPagetab(mainDialog, outputTab), outputTab);	
    	tabOutput = findSubWindow(mainDialog,true,tabOutputName);    	
		thisOutputTab(tabOutput);
		
		click(findPagetab(mainDialog, moreTab), moreTab);
    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
		thisMoreTab(tabMore);
		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		String keyToOnExp = winTitle+" On...";	
		String keyToExp = "Subtotal Fields...";
		
		// Work on tabDialog
		if(!dpOn.equals("")&&!selectedFromFields(tabDialog,keyToOnExp,dpOn)){
			actionOnSelect(findComboBox(tabDialog,true,0),keyToOnExp,dpOn,"New");
		}
		
		if(!dpFields.equals("")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
			selectSomeFields(findTable(tabDialog,true,0),dpFields);
		}
		
		// In case of expression field from data pool
		if(!dpExpression.equals("")){
			click(findPushbutton(tabDialog,keyToExp),keyToExp);	
			selectedFields("",dpFields,"OK");
		}
		
		if(!dpCutoffDate.equals("")){
			setDateTime(winTitle,dpCutoffDate,0);
		}
		
		if(!dpAgingPeriods.equals("")){
			actionOnText(findEditbox(tabDialog,true,0),"Aging Periods",dpAgingPeriods.replaceAll("[;\\s]","\r\n"),"New");	

		}
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,1),"If Condition",dpIf,"New");	
			}
       //TBD - add Local
		//TBD - add use output table
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
    	
    	if(!dpSuppressOthers.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Suppress Others"),
					"Suppress Others",
					dpSuppressOthers.equalsIgnoreCase("Yes")?true:false,"New");
			}
    	
    	if(!dpBreak.equals("")){
  		  actionOnText(findEditbox(tabDialog,true,3),"Break",dpBreak,"New");	
  		}
    	
	}
    

 }

