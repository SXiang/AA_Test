package ACL_Desktop.Tasks.Analyze;

import resources.ACL_Desktop.Tasks.Analyze.countHelper;
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

public class count extends countHelper
{
	/**
	 * Script Name   : <b>count</b>
	 * Generated     : <b>Mar 24, 2012 3:43:57 PM</b>
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
  //***  dpScope; 
  //***  dpWhile;
  //***  dpAppendToFile;
  //***  dpSaveLocalOrServer="TBD"; 
  //

     
	// BEGIN of datapool variables declaration   	
	
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "CountRecords";
		winTitle = "Count";
		tabMainName = winTitle; //"_Main";
		fileExt = ".TXT";
		
		readSharedTestData();
        readSharedMainTestData();
        
        setupTestFiles(dpFileName,dpSaveLocalOrServer,dpAppendToFile,fileExt);

        return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		openTest();
		aclMainDialog();        
	    aclEndWith("fileAction");  
	    doVerification("Log");
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
		if(!dpIf.equals("")){
		  actionOnText(findEditbox(tabDialog,true,0),"If Condition",dpIf,"New");	
		}		
         //actionOnText(findEditbox(tabDialog,true,0),	"To file", actualACLFile,"New");
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){
    	setScope(tabDialog,dpScope,dpWhile);
	}
    
 }

