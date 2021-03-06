package ACL_Desktop.Tasks.Data;

import java.io.File;

import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.Data.extractdataHelper;
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

public class extractdata extends extractdataHelper
{
	/**
	 * Script Name   : <b>extractdata</b>
	 * Generated     : <b>Mar 2, 2012 4:51:37 PM</b>
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
	//*********************** Shared Output test Variables  in 
    //***  dpTo;
    //***  dpFileType;
    //***  dpOutputHeader
    //***  dpOutputFooter

	private String dpEOF; 		//@arg End of file processing
    							//@value = 'Yes|No', default to 'No'
	// END of datapool variables declaration


	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Data";
		command = "ExtractData";
		winTitle = "Extract";   
		tabMainName = winTitle; //"_Main";
		
		readSharedTestData();
        readSharedMainTestData();

        dpEOF = getDpString("EOF");
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
		String keyToExp = "Extract Fields...";

		if(dpFields.matches("Record")){
			actionOnCheckbox(findRadiobutton(tabDialog,dpFields),dpFields,true,"New");
		}else{
			actionOnCheckbox(findRadiobutton(tabDialog,"Fields"),"Fields",true,"New");
		}
		
		
		if(!dpFields.equalsIgnoreCase("Record")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
			selectSomeFields(findTable(tabDialog,true,0),dpFields);
		}
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
    	if(!dpEOF.equals(""))
			actionOnCheckbox(findCheckbutton(tabDialog,"EOF.*"),"EOF (End of ifle processing)",
					dpEOF.equalsIgnoreCase("Yes")?true:false,"New");
    	
	}
 }
