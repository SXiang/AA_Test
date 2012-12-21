package ACL_Desktop.Tasks.Data;

import java.io.File;

import resources.ACL_Desktop.Tasks.Data.mergetablesHelper;
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

public class mergetables extends mergetablesHelper
{
	/**
	 * Script Name   : <b>mergetable</b>
	 * Generated     : <b>Mar 2, 2012 4:52:34 PM</b>
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
	
	private String dpPrimaryKeys;    //@arg name of primarykeys
	                          		 //@value = 'key1|key2..." or 'Add All'

	//Secondary Table
	private String dpSecondaryTable; //@arg name of the secondary table
                                     //@value = '[path->to->the->table]'
	private String dpSecondaryKeys;  //@arg name of primarykeys
                                  	 //@value = 'key1|key2..." or 'Add All'
  
	private String dpPresort;		 //@arg 'Yes|No' default to 'Yes'   
	
	// END of datapool variables declaration

	
	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Data";
		command = "MergeTables";
		winTitle = "Merge";
		tabMainName = winTitle; //"_Main";
		
		readSharedTestData();
        readSharedMainTestData();
        
        // Section 2: for this keyword
        dpPrimaryKeys = getDpString("PrimaryKeys");        
    	dpPresort = getDpString("Presort");
    	
    	dpSecondaryTable = getDpString("SecondaryTable");
    	    secondaryTable = getNameFromPath(dpSecondaryTable);
    	dpSecondaryKeys = getACLFields("SecondaryKeys",secondaryTable);       
		
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    fileCreated = aclEndWith("fileAction");  
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
		String sKeys = "Secondary Keys...";
		
		if(!dpPrimaryKeys.equals("")&&!selectedFromFields(tabDialog,pKeys,dpPrimaryKeys)){
			selectSomeFields(findTable(tabDialog,true,0),dpPrimaryKeys);
		}
		//TBD: Add Primary Keys - dpPrimaryKeys

		
		if(!dpSecondaryTable.equals("")){
			actionOnSelect(findComboBox(tabDialog,true,0),"Secondary Table",
					secondaryTable,"New");
		}
		//TBD: Add Secondary Table - dpSecondaryTable
		if(!dpSecondaryKeys.equals("")&&!selectedFromFields(tabDialog,sKeys,dpSecondaryKeys)){
			selectSomeFields(findTable(tabDialog,true,1),dpSecondaryKeys);
		}
		//TBD: Add Secondary Keys - dpSecondaryKeys

		if(!dpPresort.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Presort"),
					"Presort",
					dpPresort.equalsIgnoreCase("Yes")?true:false,"New");
		}
		//TBD: Add Presort 
		
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
    	
    }
	
 }
		
