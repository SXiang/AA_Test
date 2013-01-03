package ACL_Desktop.Tasks.Data;

import java.io.File;

import resources.ACL_Desktop.Tasks.Data.jointablesHelper;
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

public class jointables extends jointablesHelper
{
	/**
	 * Script Name   : <b>jointables</b>
	 * Generated     : <b>Mar 2, 2012 4:52:00 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */
	private String secondaryTable = "";
	
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

	//Primary Table
	private String dpPrimaryKeys;   //@arg name of primarykeys
	                          		//@value = 'key1|key2..." or 'Add All'
	private String dpPrimaryFields; //@arg name of primary fields
	                          		//@value = 'field1|field2..." or 'Add All'
	private String dpPresortPrimary;//@arg 'Yes|No' default to 'Yes'

	//Secondary Table
	private String dpSecondaryTable; //@arg name of the secondary table
                                     //@value = '[path->to->the->table]'
	private String dpSecondaryKeys;  //@arg name of primarykeys
									 //@value = 'key1|key2..." or 'Add All'
    private String dpSecondaryFields;//@arg name of primary fields
                                  	 //@value = 'field1|field2..." or 'Add All'
    private String dpPresortSecondary;//@arg 'Yes|No' default to 'Yes'
    
    private String dpJoinCategories; //@arg Join Categories
	                                 //@value 'Matched|Unmatched|Many-to-Many'
	private String dpIncludeAllPrimaryRecords;  //@arg Yes|No	
	private String dpIncludeAllSecondaryRecords;  //@arg Yes|No	 

	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Data";
		command = "JoinTables";
		winTitle = "Join";
		tabMainName = winTitle; //"_Main";
		
		readSharedTestData();
        readSharedMainTestData();
  
        // Section 2: for this keyword

        
    	dpPresortPrimary = getDpString("PresortPrimary");
    	

    	
        dpPrimaryKeys = getACLFields("PrimaryKeys");        
        dpPrimaryFields = getACLFields("PrimaryFields");
    	dpSecondaryTable = getDpString("SecondaryTable");
	          secondaryTable = getNameFromPath(dpSecondaryTable);
	    dpSecondaryKeys = getACLFields("SecondaryKeys",secondaryTable);
	    dpSecondaryFields = getACLFields("SecondaryFields",secondaryTable);
    	dpPresortSecondary = getDpString("PresortSecondary");
        
    	dpJoinCategories = getDpString("JoinCategories");
    	dpIncludeAllPrimaryRecords = getDpString("IncludeAllPrimaryRecords");
    	dpIncludeAllSecondaryRecords = getDpString("IncludeAllSecondaryRecords");       
        
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}	

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();

		aclMainDialog();        
	    aclEndWith("fileAction");  
	    doVerification("File");
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
		//TBD: Define variables
		String pKeys = "Primary Keys...";	
		String pFields = "Primary Fields...";
		String sKeys = "Secondary Keys...";
		String sFields = "Secondary Fields...";
		if(!dpPrimaryKeys.equals("")&&!selectedFromFields(tabDialog,pKeys,dpPrimaryKeys)){
			selectSomeFields(findTable(tabDialog,true,0),dpPrimaryKeys);
		}
		//TBD: Add Primary Keys - dpPrimaryKeys
		if(!dpPrimaryFields.equals("")&&!selectedFromFields(tabDialog,pFields,dpPrimaryFields)){
			selectSomeFields(findTable(tabDialog,true,1),dpPrimaryFields);
		}
		//TBD: Add Primary Fields  - dpPrimaryFields
		if(!dpSecondaryTable.equals("")){
			actionOnSelect(findComboBox(tabDialog,true,0),"Secondary Table",
					secondaryTable,"New");
		}
		//TBD: Add Secondary Table - dpSecondaryTable
		if(!dpSecondaryKeys.equals("")&&!selectedFromFields(tabDialog,sKeys,dpSecondaryKeys)){
			selectSomeFields(findTable(tabDialog,true,2),dpSecondaryKeys);
		}
		//TBD: Add Secondary Keys - dpSecondaryKeys
		if(!dpSecondaryFields.equals("")&&!selectedFromFields(tabDialog,sFields,dpSecondaryFields)){
			selectSomeFields(findTable(tabDialog,true,3),dpSecondaryFields);
		}
		//TBD: Add Secondary Fields - dpSecondaryFields
		if(!dpPresortPrimary.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Presort Primary Table"),
					"Presort Primary Table",
					dpPresortPrimary.equalsIgnoreCase("Yes")?true:false,"New");
		}
		//TBD: Add Presort Primary Table - dpPresortPrimary
		if(!dpPresortSecondary.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Presort Secondary Table"),
					"Presort Primary Table",
					dpPresortSecondary.equalsIgnoreCase("Yes")?true:false,"New");
		}
		//TBD: Add Presort Secondary Table - dpPresortSecondary
		
		//Set Local
    	dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
    	
		itemCreated = setOutputInMain(tabDialog);
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,0),"If Condition",dpIf,"New");	
		}
				
    	if(!dpFileName.equalsIgnoreCase("")){
    		actionOnText(findEditbox(tabDialog,true,1),"To...",actualACLFile, "New");
			//fileCreated = true;	
    	}
       
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
    	
		
		if(dpJoinCategories.equalsIgnoreCase("Matched")){
			actionOnCheckbox(findRadiobutton(tabDialog,"Matched Primary.*"),"Matched Primary Records",
					true,"New");
		}else if(dpJoinCategories.equalsIgnoreCase("Unmatched")){
			actionOnCheckbox(findRadiobutton(tabDialog,"Unmatched Primary.*"),"Unmatched Primary Records",
					true,"New");
		}else if(dpJoinCategories.equalsIgnoreCase("Many-to-Many")){
			actionOnCheckbox(findRadiobutton(tabDialog,"Many-to-Many.*"),"Many-to-Many Matched Records",
					true,"New");
		}
		if(!dpIncludeAllPrimaryRecords.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,
					"Include All Primary.*"),"Include All Primary Records",
					dpIncludeAllPrimaryRecords.equalsIgnoreCase("Yes")?true:false,"New");
		}
		if(!dpIncludeAllSecondaryRecords.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Include All Secondary.*"),
					"Include All Secondary Records",
					dpIncludeAllSecondaryRecords.equalsIgnoreCase("Yes")?true:false,"New");
		}
	}
	
 }
		
