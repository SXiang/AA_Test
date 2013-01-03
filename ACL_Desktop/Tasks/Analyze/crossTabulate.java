package ACL_Desktop.Tasks.Analyze;

import resources.ACL_Desktop.Tasks.Analyze.crossTabulateHelper;
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

public class crossTabulate extends crossTabulateHelper
{
	/**
	 * Script Name   : <b>crossTabulate</b>
	 * Generated     : <b>Mar 24, 2012 3:44:27 PM</b>
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
	protected String dpColumns ;      //@arg field or expression for test
	protected String dpRows ; //@arg rows
	                                  //@value = field1|field2...
	protected String dpIncludeCount ; //@arg Yes|No
    
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Analyze";
		command = "CrossTabulate";
		winTitle = "Cross-tabulate";
		tabMainName = "Cross\\sTabulate"; //"_Main";
		fileExt = ".FIL";
		
		readSharedTestData();
        readSharedMainTestData();
        readOutputTestData();
        
        
        dpColumns = getACLFields("Columns");
        dpRows = getACLFields("Rows");
        dpIncludeCount = getDpString("IncludeCount");
        
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
    	//tabMain = findSubWindow(mainDialog,true,tabMainName);
    	tabMain = mainTab();
		thisMainTab(tabMain);

		click(findPagetab(mainDialog, outputTab), outputTab);	
    	tabOutput = findSubWindow(mainDialog,true,tabOutputName);    	
		thisOutputTab(tabOutput);
		
		click(findPagetab(mainDialog, moreTab), moreTab);
    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
		thisMoreTab(tabMore);
		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		String keyToOnExp = "Columns...";	
		String keyToExp = "Subtotal Fields...";
		String keyToRowsExp = "Rows...";
		// Work on tabDialog
		if(!dpRows.equals("")&&!selectedFromFields(tabDialog,keyToRowsExp,dpRows)){
			selectSomeFields(findTable(tabDialog,true,0),dpRows);
		}
		
		if(!dpFields.equals("")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
			selectSomeFields(findTable(tabDialog,true,1),dpFields);
		}
		
		if(!dpColumns.equals("")&&!selectedFromFields(tabDialog,keyToOnExp,dpColumns)){
			actionOnSelect(findComboBox(tabDialog,true,0),keyToOnExp,dpColumns,"New");
		}

				
		if(!dpIncludeCount.equals("")){
			actionOnCheckbox(findCheckbutton(tabDialog,"Include Count"),"Include Count",
					dpIncludeCount.equalsIgnoreCase("Yes")?true:false,"New");
		}
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,0),"If Condition",dpIf,"New");	
			}
       
	}
	
    public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
    	// Work on tabDialog
    	super.thisMoreTab(tabDialog);
	}
    

 }

