package ACL_Desktop.Tasks.Analyze;

import resources.ACL_Desktop.Tasks.Analyze.classifyHelper;
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

public class classify extends classifyHelper
{
	/**
	 * Script Name   : <b>classify</b>
	 * Generated     : <b>Mar 24, 2012 3:43:37 PM</b>
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
	protected String dpOn ;      //@arg field or expression for test
	protected String dpBreak; //@arg field name to be broken
	
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "Classify";
		winTitle = command;
		tabMainName = winTitle; //"_Main";
		fileExt = ".FIL";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
        
        
        dpOn = getACLFields("On");
        dpBreak = getDpString("Break");
        
        //setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    aclEndWith("fileAction");  
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
		
		click(findPagetab(mainDialog, moreTab), moreTab);
    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
		thisMoreTab(tabMore);
		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		String keyToOnExp = "Classify On...";	
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
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,0),"If Condition",dpIf,"New");	
			}		
       
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
    	
    	if(!dpBreak.equals("")){
  		  actionOnText(findEditbox(tabDialog,true,3),"Break",dpBreak,"New");	
  		}
	}
 
 }

