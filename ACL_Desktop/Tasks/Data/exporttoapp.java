package ACL_Desktop.Tasks.Data;

import resources.ACL_Desktop.Tasks.Data.exporttoappHelper;
import ACL_Desktop.AppObject.DesktopSuperHelper;
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

public class exporttoapp extends exporttoappHelper
{
	/**
	 * Script Name   : <b>exporttoapp</b>
	 * Generated     : <b>Mar 2, 2012 4:51:01 PM</b>
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
	//*********************** Shared Output test Variables  in SuperHelper ***********
	//***  dpTo;
	//***  dpFileType;
	//***  dpOutputHeader
	//***  dpOutputFooter
	//

	private String dpWithFieldNames;  //@arg Yes|No
	private String dpExportAs;		  //@arg export option of application
	                                  //@value = 'Delimited|Text|Access|Clipboard|dBase III Plus|XML|Excel2.1(97-2003,072010)
	private String dpColumnSeparator; //@arg !|.|<space>|<tab>
    private String dpTextQualifier;   //@agr '|"|<space>|<tab>
	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "Data";
		command = "ExportToApp";
		winTitle = "Export";           
		tabMainName = winTitle; 	 //"_Main";
		dpTo = "File";
		//TBD: Add fileExt
				
		readSharedTestData();
        readSharedMainTestData();
 
	    dpWithFieldNames = getDpString("WithFieldNames");	
        dpExportAs = getDpString("ExportAs");
        if(dpExportAs.equalsIgnoreCase("Clipboard")){
        	dpTo = dpExportAs;
        }
        dpColumnSeparator = getDpString("ColumnSeparator");
        dpTextQualifier = getDpString("TextQualifier");        
        dpSaveLocalOrServer = "Local";
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
		
		click(findPagetab(mainDialog, moreTab), moreTab);
    	tabMore = findSubWindow(mainDialog,true,tabMoreName);
		thisMoreTab(tabMore);
		
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		String keyToExp = "Export Fields...";
		   
		if(dpFields.matches("View|Fields")){
			actionOnCheckbox(findRadiobutton(tabDialog,dpFields),dpFields,true,"New");
		}
		
		// Work on tabDialog
		if(!dpFields.equals("")&&!selectedFromFields(tabDialog,keyToExp,dpFields)){
			selectSomeFields(findTable(tabDialog,true,0),dpFields);
		}

		// In case of expression field from data pool
		if(!dpExpression.equals("")){
			click(findPushbutton(tabDialog,keyToExp),keyToExp);	
			selectedFields("",dpFields,"OK");
		}
		fileExt = selectExportAs(tabDialog);
		if(fileExt.matches("\\.mdb|\\.dbf")){
			fileComparable = false;
		}
		//Set Local
    	//dpSaveLocalOrServer = setToLocal(dpSaveLocalOrServer,findCheckbutton(tabDialog,"Local"));	
		itemCreated = setOutputInMain(tabDialog);
		
		if(!dpIf.equals("")){
			  actionOnText(findEditbox(tabDialog,true,0),"If Condition",dpIf,"New");	
		}
				
    	if(!dpFileName.equalsIgnoreCase("")){
    		if(!dpExportAs.equalsIgnoreCase("Clipboard"))
    		   actionOnText(findEditbox(tabDialog,true,1),"To...",actualACLFile, "New");
			fileCreated = true;	
    	}
       
	}

	public String selectExportAs(GuiSubitemTestObject tabDialog){
		String[] fileExts = {".mdb","",".dbf",".del",
				".xlsx",".xls",".xls",
				".txt",".xml"};
		String[] appName = {"Access","Clipboard","dBase III Plus","Delimited",
				"Excel 07-2010","Excel 97-2003","Excel 2.1",
				"Text","XML"};
		int opIndex = 7; // Default to Text
		boolean found = false;
		if(dpExportAs.equals("")){
			dpExportAs = appName[opIndex];
		}
		for(int i=0;i<appName.length&&!found;i++){
			if(appName[i].equalsIgnoreCase(dpExportAs)){
				opIndex = i;
				if(!fileExts[opIndex].equals("")){
					dpExportAs = dpExportAs+" (*"+fileExts[opIndex]+")";					
				}
				found = true;
			}
		}
		if(!found)
			logTAFError("ACL does not support - Export as '"+dpExportAs+"'");

		actionOnSelect(findComboBox(tabDialog,0),"Export As",dpExportAs,"New");
		if(!dpWithFieldNames.equals(""))
			actionOnCheckbox(findCheckbutton(tabDialog,"Export with field names"),"Export with field names",
					dpWithFieldNames.equalsIgnoreCase("Yes")?true:false,"New");
		if(!dpColumnSeparator.equals("")){
			actionOnSelect(findComboBox(tabDialog,1),"Column Separator",dpColumnSeparator,"New");
		}
		if(!dpTextQualifier.equals("")){
			actionOnSelect(findComboBox(tabDialog,2),"Text Qualifier",dpTextQualifier,"New");
		}
		selectUnicode(tabDialog,dpUnicodeTest);
		
		return fileExts[opIndex];
	}
	
	   public void selectUnicode(GuiSubitemTestObject tabDialog,String toUnicode){
		    ToggleGUITestObject uniBtn = null;
	        if(ProjectConf.unicodeTest){
	        	if((uniBtn = findCheckbutton(tabDialog,"Unicode"))!=null){
	        		 actionOnCheckbox(uniBtn,"Unicode",
	      				   toUnicode.equalsIgnoreCase("TRUE")?true:false,"New");
	        	}else{
	        		logTAFError("Unicode ACL should support this Unicode option");
	        	}
	        }else{
	        	if((uniBtn = findCheckbutton(tabDialog,"Unicode"))!=null){
	        		//if(isEnabled(uniBtn,false)&&propertyMatch(uniBtn,".visible","true",false))
	        		//logTAFError("NonUnicode ACL should not support this Unicode option");
	        	}
			
				
			}
		}
	public void thisMoreTab(GuiSubitemTestObject tabDialog){   	
		// Work on tabDialog
		super.thisMoreTab(tabDialog);

	}

}

