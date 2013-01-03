package ACL_Desktop.Tasks.Analyze;

import resources.ACL_Desktop.Tasks.Analyze.fuzzyDuplicatesHelper;
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

public class fuzzyDuplicates extends fuzzyDuplicatesHelper
{
	/**
	 * Script Name   : <b>fuzzyDuplicates</b>
	 * Generated     : <b>Mar 29, 2012 7:27:53 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/29
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
	protected String dpIntervalsOrFree ; //@arg value for Intervals or nvalues for Free
	                               //@value = [Intervals|num] or [Free|num1\nnum2\nnum3...]
	protected String dpOn ;      //@arg field or expression for test
	protected String dpMinimum ; //@arg minimum value on field
	protected String dpMaximum ; //@arg maximum value on field
    
	protected String dpSuppressOthers; //@arg 'Yes' or 'No', default to 'No'
	protected String dpBreak; //@arg field name to be broken
	
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "Stratify";
		winTitle = "Stratify";
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
        
        
        dpIntervalsOrFree = getDpString("IntervalsOrFree");
        dpOn = getACLFields("On");
        dpMinimum = getDpString("Minimum");
        dpMaximum = getDpString("Maximum");
        
        dpSuppressOthers = getDpString("SuppressOthers");
        dpBreak = getDpString("Break");
        
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}

	public void testMain(Object[] args) 
	{
		//@Step Start/Activate ACL
		//@Step Open/Identify project
		super.testMain(args);
		
		//@Step  issue pre commands, open table, supply filters
		//@Step click menu item to be tested
		openTest();
		
		//@Step Work on main tab
	    //@Step Work on more tab		
		//aclmainDialog();
        
		//@Step Submit work
		//@Step run commands after main test if there are any 
	    aclEndWith("fileAction");
	    //@Step verify results based on the nature of tests
	    //@@Step Compare ACL resulted file with master file 
	    //@@Step Compare ACL resulted log view with master file 
	    //@@Step Compare ACL resulted Screen view with master file 
	    //@@Step Select item from ACL tree view if a new item created during test.	    
	    doVerification(dpTo);

	}
	
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
		
		aRou.exeACLCommands(dpPostCmd);	    
		return fileCreated;
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
		String keyToOnExp = "Stratify On...";	
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
		
				
		if(!dpMinimum.equals("")){
		  actionOnText(findEditbox(tabDialog,true,1),"Minimum",dpMinimum,"New");	
		}
		if(!dpMaximum.equals("")){
			  actionOnText(findEditbox(tabDialog,true,2),"Maximum",dpMaximum,"New");	
			}
		
		if(!dpIntervalsOrFree.equals("")){
			String[] pair = dpIntervalsOrFree.trim().split("\\|");
			actionOnCheckbox(findRadiobutton(tabDialog,pair[0]),pair[0],true,"New");
			if(pair.length>1){
				if(pair[0].equalsIgnoreCase("Free")){
					actionOnText(findEditbox(tabDialog,true,4),pair[0],pair[1].replaceAll(";","\r\n"),"New");	
				}else{
					actionOnText(findEditbox(tabDialog,true,3),pair[0],pair[1],"New");	
				}
			}
		}
       
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	setScope(tabDialog,dpScope,dpWhile);
    	
    	if(!dpSuppressOthers.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Suppress Others"),
					"Suppress Others",
					dpSuppressOthers.equalsIgnoreCase("Yes")?true:false,"New");
			}
    	
    	if(!dpBreak.equals("")){
  		  actionOnText(findEditbox(tabDialog,true,3),"Break",dpBreak,"New");	
  		}
    	
    	//Verify enabled or disabled
    	ToggleGUITestObject append = findCheckbutton(tabDialog,"Append To Existing File");
    	ToggleGUITestObject usetable = findCheckbutton(tabDialog,"Append To Existing File");
    	if(dpFileType.equalsIgnoreCase("ACL Table")){
    		if(isEnabled(append,false)){
    			logTAFError("Append To Existing File should be disabled for output of 'ACL Table'");
    		}
    		itemCreated = true;
    	}else if(!dpFileType.equals("")){
    		if(isEnabled(usetable,false)){
    			logTAFError("Use Output Table should be disabled for output of '*** Text File'");
    		}
    	}
    	if(!dpAppendToFile.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Append To Existing File"),
					"Append To Existing File",
					dpAppendToFile.equalsIgnoreCase("Yes")?true:false,"New");
			}
	}
    
    public void thisOutputTab(GuiSubitemTestObject tabDialog){    	
    	// Work on tabDialog
    	if(!dpTo.equals("")){
    	   actionOnCheckbox(findRadiobutton(tabDialog,dpTo),
				dpTo,
				true,"New");
    	}
    	
    	dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
	     
     	if(dpTo.equalsIgnoreCase("File")){
     		setFileType(tabDialog,dpFileType);
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
 }



