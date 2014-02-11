package ax.keyword.frontend;

import ax.lib.frontend.ProjectDetailsHelper;

public class ProjectDetails  extends ProjectDetailsHelper{
	
	/**
	 * Script Name   : <b>ProjectDetails</b>
	 * Generated     : <b>Sep 7, 2013</b>
	 * Description   : ProjectDetails keyword
	 * 
	 * @author Ramneet Kaur
	 */

	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpProjectName; //@arg ProjectName whose link to be clicked for details
	protected String dpTestSetName; //@arg TestSet Name whose link to be clicked for details
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpTestSetName = getDpString("TestSetName");
		dpProjectName = getDpString("ProjectName");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		if(!dpMasterFiles[0].isEmpty()){
			verifyTestSetsList();
			verifyProjectsDropDownList();
			verifyDescriptionPanelContents();
			verifyInfoPanelContents();
			verifyHeaderFooter();
			verifyUsersList();
		}
		if(!dpProjectName.isEmpty()){
			openProjectFromDropDown();
		}else if(!dpTestSetName.isEmpty()){
			openTestSetDetails();
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyTestSetsList(){
		String allTestSets = getTestSetsList();
		if(allTestSets.isEmpty()){
			logTAFError("No TestSet Found!!");
		}else{
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[0]);
			//System.out.println("All TestSets: "+allTestSets);
			result[0] = allTestSets; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyProjectsDropDownList(){
		String allProjects = getProjectsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No Projects Found!!");
		}else{
			//System.out.println("All Projects: "+allProjects);
			logTAFStep("Verify list of Projects - " + dpMasterFiles[5]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"\r\n"+getProjectName()+"\r\n"+getTestSetHeader();
		String footer = getFooter();
		//System.out.println("header: "+header);
		//System.out.println("footer: "+footer);
		logTAFStep("Verify Project Header - " + dpMasterFiles[1]);
		result[0] = header; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[1]);
		logTAFStep("Verify Page Footer - " + dpMasterFiles[2]);
		result[1] = footer; // You need to get actual result for
											// each comparison
		compareTxtResult(result[1], dpMasterFiles[2]);
	}
	
	public void verifyUsersList(){
		String usersList = getUsersPanelTitle();
		getUsersPopup();
		usersList = usersList+":\r\n"+getUsersPopupHeader()+":\r\n"+getUsersList();
		logTAFStep("Verify Users - " + dpMasterFiles[6]);
		result[0] = usersList; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[6]);
		closeUsersPopup();
	}
	
	public void openTestSetDetails(){
		clickTestSetName(dpTestSetName);
	}
	
	public void openProjectFromDropDown(){
		clickProjectNameFromDropDown(dpProjectName);
	}
	
	public void verifyDescriptionPanelContents(){
		String description = getDescription();
		if(description.isEmpty()){
			logTAFError("Description panel not found!!");
		}else{
			//System.out.println("description: "+description);
			logTAFStep("Verify contents of Description panel - " + dpMasterFiles[3]);
			result[0] = description; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[3]);
		}
	}
	
	public void verifyInfoPanelContents(){
		String info = getInfo();
		if(info.isEmpty()){
			logTAFError("Info panel not found!!");
		}else{
			//System.out.println("info: "+info);
			logTAFStep("Verify contents of Info panel - " + dpMasterFiles[4]);
			result[0] = info; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[4]);
		}
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//ProjectDetails debug = new ProjectDetails();
		//debug.verifyTestSetsList();
		//debug.verifyProjectsDropDownList();
		//debug.verifyDescriptionPanelContents();.
		//debug.verifyInfoPanelContents();
		//debug.verifyHeaderFooter();
		//debug.openTestSetDetails();
		//debug.verifyUsersList();
	}
}
