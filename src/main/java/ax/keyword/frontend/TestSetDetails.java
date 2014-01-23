package ax.keyword.frontend;

import ax.lib.frontend.TestSetDetailsHelper;

public class TestSetDetails  extends TestSetDetailsHelper{

	/**
	 * Script Name   : <b>TestSetDetails</b>
	 * Generated     : <b>Sep 11, 2013</b>
	 * Description   : TestSetDetails keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpProjectName; //@arg ProjectName whose link to be clicked for details
	protected String dpTestName; //@arg Test Name whose link to be clicked for details
	protected String dpTestSetName; //@arg TestSet Name whose link to be clicked for details
	protected String dpOpenTablesOrFiles; //@arg to select whether open Data Tables or Related Files
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpProjectName = getDpString("ProjectName");
		dpTestSetName = getDpString("TestSetName");
		dpTestName = axNameHandle(getDpString("TestName"));
		dpOpenTablesOrFiles = getDpString("OpenTablesOrFiles");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		isTestsHeaderDisplayed();
		isRightPanelIconDisplayed();
		if(!dpMasterFiles[0].isEmpty()){
			verifyTestsList();
			verifyProjectsDropDownList();
			verifyTestSetsDropDownList();
			verifyDescriptionPanelContents();
			verifyInfoPanelContents();
			verifyHeaderFooter();
			verifyDataTablesLink();
			verifyRelatedFilesLink();
			verifyUsersList();
		}
		if(!dpProjectName.isEmpty()){
			openProjectFromDropDown();
		}else if(!dpTestSetName.isEmpty()){
			openTestSetFromDropDown();
		}else if(!dpTestName.isEmpty()){
			openTestDetails();
		}else if(!dpOpenTablesOrFiles.isEmpty()){
			if(dpOpenTablesOrFiles.contains("table")){
				openDataTables();
			}else if(dpOpenTablesOrFiles.contains("file")){
				openRelatedFiles();
			}
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyTestsList(){
		String allTests = getTestsList();
		if(allTests.isEmpty()){
			logTAFError("No Test Found!!");
		}else{
			logTAFStep("Verify list of Tests - " + dpMasterFiles[0]);
			//System.out.println("All Tests: "+allTests);
			result[0] = allTests; // You need to get actual result for
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
			logTAFStep("Verify list of Projects - " + dpMasterFiles[7]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[7]);
		}
	}
	
	public void verifyTestSetsDropDownList(){
		String allProjects = getTestSetsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[8]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[8]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"\r\n"+getProjectName()+"\r\n"+getTestSetName()+"\r\n"+getTestHeader();
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
		logTAFStep("Verify Users - " + dpMasterFiles[9]);
		result[0] = usersList; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[9]);
		closeUsersPopup();
	}
	
	public void openTestDetails(){
		clickTestName(dpTestName);
	}
	
	public void openTestSetFromDropDown(){
		clickTestSetNameFromDropDown(dpTestSetName);
	}
	
	public void openProjectFromDropDown(){
		clickProjectNameFromDropDown(dpProjectName);
	}
	
	public void verifyDataTablesLink(){
		String dataTablesLink = getDataTablesLabel();
		if(dataTablesLink.isEmpty()){
			logTAFError("dataTablesLink panel not found!!");
		}else{
			//System.out.println("description: "+description);
			logTAFStep("Verify Label of dataTablesLink - " + dpMasterFiles[3]);
			result[0] = dataTablesLink; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[3]);
		}
	}
	
	public void verifyRelatedFilesLink(){
		String relatedFilesLink = getRelatedFilesLabel();
		if(relatedFilesLink.isEmpty()){
			logTAFError("relatedFilesLink panel not found!!");
		}else{
			//System.out.println("description: "+description);
			logTAFStep("Verify Label of relatedFilesLink - " + dpMasterFiles[4]);
			result[0] = relatedFilesLink; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[4]);
		}
	}
	
	public void openDataTables(){
		clickDataTablesLink();
	}
	
	public void openRelatedFiles(){
		clickRelatedFilesLink();
	}
	
	public void verifyDescriptionPanelContents(){
		String description = getDescription();
		if(description.isEmpty()){
			logTAFError("Description panel not found!!");
		}else{
			//System.out.println("description: "+description);
			logTAFStep("Verify contents of Description panel - " + dpMasterFiles[5]);
			result[0] = description; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyInfoPanelContents(){
		String info = getInfo();
		if(info.isEmpty()){
			logTAFError("Info panel not found!!");
		}else{
			//System.out.println("info: "+info);
			logTAFStep("Verify contents of Info panel - " + dpMasterFiles[6]);
			result[0] = info; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[6]);
		}
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//TestSetDetails debug = new TestSetDetails();
		//debug.verifyTestsList();
		//debug.verifyProjectsDropDownList();
		//debug.verifyDescriptionPanelContents();.
		//debug.verifyInfoPanelContents();
		//debug.verifyHeaderFooter();
		//debug.openTestSetDetails();
		//debug.verifyUsersList();
	}
	
}
