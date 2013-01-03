package ACL_Desktop.Tasks.Data;

import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.Data.crystalreportsHelper;
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

public class crystalreports extends crystalreportsHelper
{
	/**
	 * Script Name   : <b>crystalreports</b>
	 * Generated     : <b>Mar 2, 2012 4:49:15 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */

	private String command = "CrystalReports";
	private boolean updatable = true;
	// BEGIN of datapool variables declaration

	private String dpTemplateFileName; //@arg paht/name of the template 'Name' crteated
	private String dpLaunchCrystalReports; //@arg Yes|No , currently only 'No' can be used.
	// END of datapool variables declaration


	@Override
	public boolean dataInitialization() {
		boolean done= true;


		readSharedTestData();
		readSharedMainTestData();
		readOutputTestData();
		
		dpTo = getDpString("To");
		
		defaultMenu = "Data";
        dpLaunchCrystalReports = getDpString("LaunchCrystalReports");
        dpTemplateFileName = getDpString("TemplateFileName");
        
             fileExt = ".rpt";
             dpSaveLocalOrServer = "Local";

             
		return done;
	}

	public void testMain(Object[] args) 
	{
		super.testMain(args);
		   
		//itemIndex = aRou.searchSubitems(dpActOnItem);		
		aRou.exeACLCommands(dpPreCmd);
		aRou.setACLFilters(dpPreFilter);
		
		if(!dpFields.equals(""))
			createTemplate();
		
		if(!dpTo.equals("")&&!dpTemplateFileName.equals(""))
			updateTemplate();

	    aRou.exeACLCommands(dpPostCmd);
	}
	
	public void createTemplate(){
		String thiscommand = "CreateTemplate";

		kUtil.invokeMenuCommand(
				defaultMenu+"->"+command+"->"+thiscommand);
        setupTestFiles(dpTemplateFileName,
 				dpSaveLocalOrServer,
 				fileExt);
         
		fileCreated = crystalReport_Create();
		
		if(fileCreated){	
			//doVerification("File"); // not safe to verify contents
		}else{
			updatable = false;
		}
	}
	
	public void updateTemplate(){
		String thiscommand = "UpdateTemplate";
		winTitle = "Update Crystal Report";
		tabMainName = "Crystal Report"; //"_Main";
		fileExt = ".rpt";
		
		if(!updatable){
			return;
		}
		
		kUtil.invokeMenuCommand(defaultMenu+"->"+command+"->"+thiscommand);
		
		mainDialog = mainDialog();//new TopLevelSubitemTestObject(findTopLevelWindow(winTitle)) ;
		
		click(findPagetab(mainDialog, mainTab), mainTab);	
    	tabMain = findSubWindow(mainDialog,true,tabMainName);
    	thisMainTab(tabMain);
		
		click(findPagetab(mainDialog, outputTab), outputTab);	
    	tabOutput = findSubWindow(mainDialog,true,tabOutputName);  
    	
		thisOutputTab(tabOutput);
		
		fileCreated = aclEndWith("fileAction"); 
		if(dpTo.equals(""))
           dpTo = "File";
		//doVerification(dpTo); //not safe to verify
	}
	
	public void thisMainTab(GuiSubitemTestObject tabDialog){
		
		String fromfile = dpActualFile;

		if(dpFields.equals("")&&!dpTemplateFileName.equals("")){
			//updatable = true;
			fromfile = FileUtil.getAbsDir(dpTemplateFileName,ProjectConf.localInputDataDir);
			fromfile = FileUtil.getFullNameWithExt(fromfile,fileExt);
		}else{
			fromfile = dpActualFile;
		}
		actionOnText(findEditbox(tabDialog,true,0),		
				"From Report Template", 
				fromfile,"New");

	}
	
	public boolean  crystalReport_Create(){

		boolean filecreated = false;
		String fieldsArr[] = dpFields.split("\\|");
		dpTemplateFileName = setupTestFiles(dpTemplateFileName,
				dpSaveLocalOrServer,
				fileExt);

		if(!dpTemplateFileName.equals("")){
			actionOnText(findEditbox(mainDialog_CR(),true,0),		
					"Template file", 
					dpTemplateFileName,
			"New");
		}

		if(dpFields.matches("Add All|Clear All")){
			click(findPushbutton(mainDialog_CR(),dpFields),dpFields, true);
		}else{
			SelectGuiSubitemTestObject availableFields = findTable(mainDialog_CR(), true, "ListBox", 0);
			GuiTestObject selectBtn = findPushbutton(mainDialog_CR(),"-->");
			for(int i=0;i<fieldsArr.length;i++){
				actionOnSelect(false,availableFields,"Available fields",fieldsArr[i],"New");
				click(selectBtn,"-->");
			}
		}
		//Currently 'No' for this op only 
		dpLaunchCrystalReports = "No";
		actionOnCheckbox(findCheckbutton(mainDialog_CR(),"Launch Crystal Reports application"),"Launch Crystal Reports application", 
				dpLaunchCrystalReports.equalsIgnoreCase("Yes")?true:false,"New");

		if(dpEndWith.matches("[Oo][Kk]|[Ff][Ii][Nn][Ii][Ss][Hh]")){
			click(findPushbutton(mainDialog_CR(),"OK"),"OK", true);
			filecreated = true;
			GuiTestObject cancelBtn ;
			if(dismissPopup("Yes",false)
					&&(cancelBtn = findPushbutton(mainDialog_CR(),"Cancel"))!=null){
				click(cancelBtn,"Cancel");
				filecreated = false;
			}else{
				filecreated = true;
				dismissPopup("Yes",false);
				sleep(1);
				dismissPopup("OK",true);
			}
		}else{
			click(findPushbutton(mainDialog_CR(),"Cancel"),"Cancel", true);
		}

		return filecreated;
	}
}
