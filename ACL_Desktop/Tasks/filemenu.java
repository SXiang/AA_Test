package ACL_Desktop.Tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import lib.acl.util.FileUtil;
import resources.ACL_Desktop.Tasks.filemenuHelper;
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

public class filemenu extends filemenuHelper
{
	/**
	 * Script Name   : <b>filemenu</b>
	 * Generated     : <b>2012-01-12 11:08:05 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/01/12
	 * @author Steven_Xiang
	 */
	private String itemName = "";
	// BEGIN of datapool variables declaration

	private String dpOpenProject;   //@arg command for open acl project
	//@value = 'Open|OpenCancel|OpenNew|OpenWorking|OpenLastSaved|OpenRecent|CreateNew', default to OpenNew
	private String dpEndWith; //@arg the end options for current test
	//@value = 'Kill' - For kill app after test
	//@value = 'SaveProject|SaveProjectAs|SaveProjectOverwirte|CloseProject'
	private String dpNewProjectName; //@arg new name for save project Save as
	private String dpItemName;//@arg  Item name to open
	//@value = '[pathToItem1]|[pathToItem1]|[pathToItem1]|...', Rename and Save/SaveAs only handle one item 
	private String dpActionOnItem;//@arg the command for item opened
	//@value = 'Save|SaveAs|Rename|Delete|DeleteWithFile|Verify'
	private String dpNewItemName; //@arg New name for Rename
	private String dpFilterHistory=""; //@arg check filters for table
    //@value = 'filter1|filter2...', filters to be verified at beginning
    //@value   'New', No filter for the table 
    //@value   'ClearHistory', remove filter history
	private String dpItemStatus=""; //@arg list of tables with status, (:0) [CLOSED],(:3)[OPENED],(:1) [PRIMARY],(:2) [SECONDARY]
	//@value = 'table1:0|table2|table3:2|table4:3...'
	// END of datapool variables declaration


	@Override
	public boolean dataInitialization() {
		boolean done= true;

		defaultMenu = "File";
		command = "_TableIcon";
		dpOpenProject = getDpString("OpenProject");
		dpEndWith = getDpString("EndWith");	 
		dpNewProjectName = getDpString("NewProjectName");	
		dpItemName = getDpString("ItemName");
		dpFilterHistory = getDpString("FilterHistory");
		dpActionOnItem = getDpString("ActionOnItem");	 
		dpNewItemName = getDpString("NewItemName");	
		dpItemStatus = getDpString("ItemStatus");
		return done;
	}

	public void testMain(Object[] args) 
	{
		//setWelcomeTab();
		super.testMain(args);
               
		//Steps:
		//@Step Activate ACLWin
		//@Step Click File->'MenuItem' [Such as File->Open Project]
		//@Step If OpenProject,Choose file to open from the ACL file chooser;
		//@@Step Click 'Open' 
		//@@Step Handle ACL info dialog and end it with 'OK'
		//@@Step Choose open options if the dialog appears,default to 'Working'
		//@@Step Apply commands And/Or filters if there are any
		//@@Step Verify filters in the history list.
		//@Step Perform Rename,Delete or SaveAs on ACL items
		//@Step Execute the command specified in 'EndWith' such as 'Kill'
        
		if(!dpItemName.equals("")){
			itemName = getNameFromPath(dpItemName);
			exeActOnItem();
		}		
		exeEndWith(dpEndWith);
		if(!dpItemStatus.equals("")){
            verifyItemStatus(dpItemStatus);
			aTabs.switchTableTabs();
		}
		//setWelcomeTab();
	}

	public void exeActOnItem(){
		int[] itemIndex = null;
		if(!dpActionOnItem.equalsIgnoreCase("")){
			  itemIndex = aRou.searchSubitems(dpItemName);
			}
//		aRou.exeACLCommands(dpPreCmd);
//		aRou.setACLFilters(dpPreFilter);
		if(!dpFilterHistory.equals("")){ // Fill in the list - TBD
			aRou.exeACLCommand("OPEN "+itemName,dpActionOnTab);
			filterList.clear();

			if(dpFilterHistory.matches("ClearHistory")){
				invokeFilterCommand(dpFilterHistory);			
			}else{
				if(!dpFilterHistory.matches("New")){
					getFilterList(filterList,dpFilterHistory);
					Collections.reverse(filterList);
				}
				// Set filters
				
				if(!dpFilterHistory.matches("ClearHistory")){
					getFilterList(filterList,dpPreCmd);
					getFilterList(filterList,dpPreFilter);
				}


				aRou.exeACLCommands(dpPreCmd,dpActionOnTab);
				aRou.setACLFilters(dpPreFilter);
			}
			verifyFilterList(filterList);
		}

		if(dpActionOnItem.equalsIgnoreCase("SaveAs")){
			if(!aRou.exeSelectSubitems(itemIndex,dpItemName))
					return;
			aRou.exeACLCommand("OPEN "+itemName, dpActionOnTab);
			kUtil.invokeMenuCommand(defaultMenu+"->"+dpActionOnItem);
			inputUnicodeChars(textSaveAs(),dpNewItemName); 
			click(okRename(),"OK");	
			sleep(2);
			//dismissPopup("Yes",false,true);
			if(dismissPopup("OK|Yes",false))
				sleep(1);
			if(dismissPopup("Yes",false))
				sleep(1);
			if(dismissPopup("Cancel",true)){
		      logTAFWarning("Save as has been canceled due to errors detected");		    	   
			}else{
				aTabs.remove(itemName);
				aTabs.add(dpNewItemName,dpActionOnTab);
				
			}
		}else if(dpActionOnItem.equalsIgnoreCase("Rename")){    		
			if(!aRou.exeSelectSubitems(itemIndex,dpItemName))
				return;
			aRou.exeACLCommand("CLOSE");
			kUtil.invokeMenuCommand(defaultMenu+"->"+dpActionOnItem);
			inputUnicodeChars(dpNewItemName);
			acl_tree().click(atIndex(itemIndex[0]),iconPoint);
			if(dismissPopup("OK|Yes",false))
				sleep(1);
			//dLog.confirmWarning("OK",false); 
			dismissPopup("Yes|OK",false); 

		}else if(dpActionOnItem.equalsIgnoreCase("Delete")){
			if(!aRou.exeSelectSubitems(itemIndex,dpItemName))
				return;
			Object[] options = {null,null,State.notSelected(),
					null,null,null,null,null,null,null};
			dLog.aclOptions("Table","OK",options );
			
			kUtil.invokeMenuCommand(defaultMenu+"->"+dpActionOnItem);
			click(okDelete(),"OK");    	
			dismissPopup("Yes|OK",false); 
			aTabs.remove(itemName);
			
		}else if(dpActionOnItem.equalsIgnoreCase("DeleteWithFile")){
			Object[] options = {null,null,State.selected(),
					null,null,null,null,null,null,null};
			dLog.aclOptions("Table","OK",options );
			 
			String[] filePath = new String[itemIndex.length];
			String[] oriPath = new String[itemIndex.length];
			String backfile = ".DELETE";
			for(int i=0;i<itemIndex.length;i++){
				String name = aRou.getProperties(itemIndex[i],"Name");
				String location = aRou.getProperties(itemIndex[i],"Location");

				if(location.matches("Unknown|Not Applicable")){
					location = "";
				}
				oriPath[i] = (location+name).replaceAll("\\.\\\\", "\\\\");
				filePath[i] = FileUtil.getAbsDir(oriPath[i],ProjectConf.workbookDir);
				FileUtil.copyFile(filePath[i], filePath[i]+backfile);
				if(new File(filePath[i]).exists()){
					logTAFInfo("Data file '"+filePath[i]+" found before the deletion of table");
				}else{
					logTAFWarning("Data file '"+filePath[i]+" not found before the deletion of table");
				}
			}
			    if(!aRou.exeSelectSubitems(itemIndex,dpItemName))
					return;
				kUtil.invokeMenuCommand(defaultMenu+"->Delete");
				click(okDelete(),"OK");   
			
			int maxWait = 5,wt=0;	// Seems the deletion needs some time to complete.
			for(int i=0;i<itemIndex.length;i++){
				while(new File(filePath[i]).exists()&&wt++<maxWait){
					sleep(2);
				}
				if(new File(filePath[i]).exists()){
					if(new File(oriPath[i]).isAbsolute())
					   logTAFError("Data file '"+filePath[i]+" not deleted after the deletion of table");
					else
						logTAFError("ACL has an issue to deal with relative path '"+oriPath[i]+"' during deletion");
				}else{
					logTAFInfo("Data file '"+filePath[i]+" deleted sucessfully with the deletion of table");
					FileUtil.copyFile(filePath[i]+backfile, filePath[i]);
				}
			}
			dismissPopup("Yes|OK",false); 
			aTabs.remove(dpItemName);
		}else if(dpActionOnItem.equalsIgnoreCase("Save")){
			if(!aRou.exeSelectSubitems(itemIndex,dpItemName))
				return;
			aRou.exeACLCommand("OPEN "+itemName);
			kUtil.invokeMenuCommand(defaultMenu+"->"+dpActionOnItem);
			dismissPopup("OK|Yes",false);  
			
		}else{
			logTAFDebug("No valid action performed on any project items");
		}

	}

	// To be D ************

	public boolean verifyFilterList(ArrayList<String> expected){

		int sizeExp = expected.size();
		boolean pass = true;
		String fh = null,fe=null;
		
		GuiTestObject to = (GuiTestObject) aRou.getFilterBox();
		if(to==null){
			logTAFWarning("ACL filter Edit box not found?");
			return true;
		}
		
		if(sizeExp==0){
			fh = getFilterInHistory(to,0).trim();
			if(fh!=""){
				logTAFError("Expected no filter in the filter history, but actual "+fh+" found ?");
			}else{
				logTAFInfo("No filter expected and found in the filter history.");
			}
			return false;
		}
		
		for(int i=0;i<sizeExp;i++){
			fe = expected.get(i).trim();
			fh = getFilterInHistory(to,sizeExp).trim();
			if(fh.equalsIgnoreCase(fe)){
				logTAFInfo("Expected filter "+(sizeExp-i)+": '"+fe+", Actual '"+fh+"' found");
			}else{
				pass = false;
				if(fh!=""&&expected.contains(fh)){
					logTAFError("Expected filter "+(sizeExp-i)+": '"+fe+"', not found in the history list");
				}else if(fh==""){
					logTAFError("Expected filter "+(sizeExp-i)+": '"+fe+"', not found in the history list");
				}else{
					logTAFError("Expected filter "+(sizeExp-i)+": '"+fe+"', Actual '"+fh+"' found");
				}
			}
		}
		if(!pass)
			return pass;
		String unexpectedFilter = fh = getFilterInHistory(to,sizeExp+1).trim();
		if(unexpectedFilter.equalsIgnoreCase(fh)){
			logTAFInfo("Exact "+(sizeExp)+" filters  are found in the filter history");
		}else{
			pass = false;
			logTAFError("UnExpected filter "+unexpectedFilter+" found in the history list!");
		}
		return pass;
		
	}	
	
	public String getFilterInHistory(GuiTestObject to, int n){
		int hFilter = 20, hEdit = 13;
		int hDev = (hFilter-hEdit)/2;
		int xIndex = 100;
		int yIndex = hDev + hEdit + (n*hEdit - hEdit/2);
		
		String filter = "";
		try{
			click((GuiTestObject) to);
			getScreen().getActiveWindow().inputKeys(("%{DOWN}"));
			click((GuiTestObject)to,atPoint(xIndex,yIndex));
			filter = ((TextGuiTestObject) to).getText();
		}catch(Exception e){
			sleep(0);
		}

		if(filter==null)
			filter = "";
        return filter;
	}

	public void getFilterList(ArrayList<String> list,String comms){
	    String othercomms = "(?i)SET .*|OPEN .*|CLOSE .*|\\s*";
	    String filtercomms = "(?i)SET[\\s]+FILTER[\\s]+TO[\\s]+";
	    int maxSize = 10;
		comms = comms.replaceAll(filtercomms, "");
		String[] filters = comms.split("\\|");

		for(int i=0;i<filters.length;i++){
			String ft = filters[i];
			if(ft.matches(othercomms))
				continue;
			if(list.contains(ft)){
				list.remove(ft);
			}
			if(list.size()==maxSize)
				list.remove(0);
			list.add(ft.trim());
		}
		
		
	}
	
	public void invokeFilterCommand(String item){
		GuiTestObject gto = (GuiTestObject) aRou.getFilterBox();
		if(gto==null){
			logTAFWarning("ACL filter Edit box not found?");
			return;
		}	
		
		click(gto, RIGHT);
		kUtil.invokeMenuCommand(item,false);
		
	}
	// **********
	public void exeEndWith(String endWith){
		dismissPopup("Cancel",false); 
		
//		aTabs.actOnTab(dpActionOnTab);
		aRou.exeACLCommands(dpPostCmd);
		if(endWith.equalsIgnoreCase("Kill")){
			killProcess();
			onRecovery = false;
			tabList.clear(); // No welcome page anymore !
		}else if(endWith.equalsIgnoreCase("SaveProject")){
			kUtil.invokeMenuCommand("File->SaveProject");
		}else if(endWith.equalsIgnoreCase("SaveProjectAs")){
			kUtil.saveProjectAs(dpNewProjectName,false,delFile);    		
		}else if(endWith.equalsIgnoreCase("SaveProjectOverwrite")){
			kUtil.saveProjectAs(dpNewProjectName,false,delFile);    		
		}else if(endWith.equalsIgnoreCase("CloseProject")){
			//kUtil.saveProjectAs(dpNewProjectName,false);  
			kUtil.closeProject(dpProjectName);			
		}

		dismissPopup("OK|Yes",false);    	

	}

	public void verifyItemStatus(String itemStatus){
		  String namePre = "tableIcon_";
		  String[] name = {namePre+"0",namePre+"1",namePre+"2",namePre+"3"};
    	  String location = "Local";
    	  String defaultFileExt = ".jpg";
    	
    	  for(int i=0;i<name.length;i++)
    	       setupTestFiles(name[i],location,"No",defaultFileExt,"",i);
		       aRou.searchSubitems(true,itemStatus,dpMasterFiles, dpActualFiles);


	}

}

