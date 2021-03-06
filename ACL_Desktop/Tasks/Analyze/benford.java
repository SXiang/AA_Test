package ACL_Desktop.Tasks.Analyze;

import resources.ACL_Desktop.Tasks.Analyze.benfordHelper;
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

public class benford extends benfordHelper
{
	/**
	 * Script Name   : <b>benford</b>
	 * Generated     : <b>Mar 24, 2012 3:43:13 PM</b>
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
	protected String dpLeadingDigits ;      //@arg num leading digits
	protected String dpUpperLower ; //@arg Yes|No
    protected String dpOn; //@arg field name
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "PerformBenfordAnalysis";
		winTitle = "Benford";
		tabMainName = winTitle; //"_Main";
		fileExt = ".FIL";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
        
        dpOn = getACLFields("On");
        dpLeadingDigits = getDpString("LeadingDigits");
        dpUpperLower = getDpString("UpperLower");
        
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
		String keyToOnExp = winTitle+" On...";	

		// Work on tabDialog
		if(!dpOn.equals("")&&!selectedFromFields(tabDialog,keyToOnExp,dpOn)){
			actionOnSelect(findComboBox(tabDialog,true,0),keyToOnExp,dpOn,"New");
		}

				
		if(!dpUpperLower.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Include Upper and Lower Bounds"),
					"Include Upper and Lower Bounds",
					dpUpperLower.equalsIgnoreCase("Yes")?true:false,"New");
		}
		
		if(!dpLeadingDigits.equals("")){
			  actionOnText(findEditbox(tabDialog,true,0),"Number of Leading Digits:",dpLeadingDigits,"New");	
			}
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,1),"If Condition",dpIf,"New");	
			}
       
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
	}
    

 }
