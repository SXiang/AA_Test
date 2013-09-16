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
	protected String dpTestName; //@arg ProjectName whose link to be clicked for details
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpTestName = getDpString("TestName");
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
			verifyTestsList();
			verifyProjectsDropDownList();
			verifyTestsDropDownList();
			verifyDescriptionPanelContents();
			verifyInfoPanelContents();
			verifyHeaderFooter();
			verifyUsersList();
		}
		if(!dpTestName.isEmpty()){
			openTestDetails();
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
			logTAFStep("Verify list of Projects - " + dpMasterFiles[5]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyTestSetsDropDownList(){
		String allProjects = getTestSetsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			//System.out.println("All Projects: "+allProjects);
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[5]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"|"+getProjectName()+"|"+getTestSetHeader()+"|"+getTestSetName()+"|"+getTestHeader();
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
		usersList = usersList+":"+getUsersPopupHeader()+":"+getUsersList();
		logTAFStep("Verify Users - " + dpMasterFiles[6]);
		result[0] = usersList; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[6]);
		closeUsersPopup();
	}
	
	public void openTestetails(){
		clickTestName(dpTestName);
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
		//debug.verifyTestsList();
		//debug.verifyProjectsDropDownList();
		//debug.verifyDescriptionPanelContents();.
		//debug.verifyInfoPanelContents();
		//debug.verifyHeaderFooter();
		//debug.openTestSetDetails();
		//debug.verifyUsersList();
	}
	
	/*
	
	private static TestSetDetailsPage objTestSetDetailPage = null;
	
	private static TestSetDetailsPage prepPage(){
		objTestSetDetailPage = new TestSetDetailsPage(driver);
		return objTestSetDetailPage;
	}

	public static void getProjectName(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getProjectName();
	}
	
	public static void getProjectHeader(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getProjectHeader();
	}
	
	public static void getProjectsList(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getProjectsListFromDropDown();
	}

	public static void getTestSetName(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getTestSetName();
	}
	
	public static void getTestSetDesc(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getTestSetDesc();
	}
	
	public static void getTestSetDescLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getTestSetDescLabel();
	}
	
	public static void getTestsList(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getTestsListFromDropDown();
	}

	public static void getTestSetCreatorName(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getCreatedBy();
	}
	
	public static void getTestSetCreatedDateTimeStamp(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getCreatedAt();
	}
	
	public static void getTestSetModifierName(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getModifiedBy();
	}
	
	public static void getTestSetModifiedDateTimeStamp(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getModifiedAt();
	}
	
	public static void getTestSetCreatorNameLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getCreatedByLabel();
	}
	
	public static void getTestSetCreatedDateTimeStampLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getCreatedAtLabel();
	}
	
	public static void getTestSetModifierNameLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getModifiedByLabel();
	}
	
	public static void getTestSetModifiedDateTimeStampLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getModifiedAtLabel();
	}
	
	public static void getInfoLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getInfoLabel();
	}
	
	public static void getUsersLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getUsersLabel();
	}
	
	public static void getDataTablesLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getDataTablesLabel();
	}
	
	public static void getRelatedFilesLabel(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getRelatedFilesLabel();
	}
	
	public static void getTestsList(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getTestsList();
	}

	public static void getTestSetUsersList(){

		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getUsersPopup();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objTestSetDetailPage.getUsersPopupHeader();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objTestSetDetailPage.getUsersList();
		objTestSetDetailPage.closeUsersPopup();
	
	}
	
	public static void filterTestsList(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.filterTestsList();
	}
	
	public static void getSearchItemsList(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.getSearchItemsList();
	}
	
	public static void clearFilter(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.clearFilter();
	}
	
	
	public static void closeTestSetDetailsLayer(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.closeTestSetDetailsLayer();
	}
	
	public static void clickTestName(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		objTestSetDetailPage.clickTestName();
	}
	
	public static void allElementsPresent() {
		if(objTestSetDetailPage == null){
			prepPage();
		}
		allElementsOnBasePagePresent();
		objTestSetDetailPage.getUsersPopup();
		allElementsOnUsersPopupPresent();
		objTestSetDetailPage.closeUsersPopup();
	}
	
	public static void allElementsOnBasePagePresent(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		if(!objTestSetDetailPage.isProjectHeaderPresent()){
			System.out.println("Projects List header NOT present!!!");
		}else{
			System.out.println("Projects List header present!!!");
		}
		if(!objTestSetDetailPage.isProjectNamePresent()){
			System.out.println("Project Name NOT present!!!");
		}else{
			System.out.println("Project Name present!!!");
		}
		if(!objTestSetDetailPage.isTestSetNamePresent()){
			System.out.println("ProjectName NOT present!!!");
		}else{
			System.out.println("ProjectName present!!!");
		}
		if(!objTestSetDetailPage.isTestsHeaderPresent()){
			System.out.println("Tests List header NOT present!!!");
		}else{
			System.out.println("Tests List header present!!!");
		}
		if(!objTestSetDetailPage.isSearchBoxPresent()){
			System.out.println("Search Box NOT present!!!");
		}else{
			System.out.println("Search Box present!!!");
		}
		if(!objTestSetDetailPage.isSearchBoxIconPresent()){
			System.out.println("Search Box icon NOT present!!!");
		}else{
			System.out.println("Search box icon present!!!");
		}
		if(!objTestSetDetailPage.isDescTitlePresent()){
			System.out.println("Desc Title NOT present!!!");
		}else{
			System.out.println("Desc Title present!!!");
		}
		if(!objTestSetDetailPage.isDescIconPresent()){
			System.out.println("Desc Icon NOT present!!!");
		}else{
			System.out.println("Desc icon present!!!");
		}
		if(!objTestSetDetailPage.isInfoTitlePresent()){
			System.out.println("Info title NOT present!!!");
		}else{
			System.out.println("Info title present!!!");
		}
		if(!objTestSetDetailPage.isInfoIconPresent()){
			System.out.println("Info icon NOT present!!!");
		}else{
			System.out.println("Info icon present!!!");
		}
		if(!objTestSetDetailPage.isCreatedByLabelPresent()){
			System.out.println("CreatedByLabel NOT present!!!");
		}else{
			System.out.println("CreatedByLabel present!!!");
		}
		if(!objTestSetDetailPage.isCreatedByInfoPresent()){
			System.out.println("CreatedByInfo NOT present!!!");
		}else{
			System.out.println("CreatedByInfo present!!!");
		}
		if(!objTestSetDetailPage.isCreatedAtLabelPresent()){
			System.out.println("CreatedAtLabel NOT present!!!");
		}else{
			System.out.println("CreatedAtLabel present!!!");
		}
		if(!objTestSetDetailPage.isCreatedAtInfoPresent()){
			System.out.println("CreatedAtInfo NOT present!!!");
		}else{
			System.out.println("CreatedAtInfo present!!!");
		}
		if(!objTestSetDetailPage.isModifiedByLabelPresent()){
			System.out.println("ModifiedByLabel NOT present!!!");
		}else{
			System.out.println("ModifiedByLabel present!!!");
		}
		if(!objTestSetDetailPage.isModifiedByInfoPresent()){
			System.out.println("ModifiedByInfo present!!!");
		}else{
			System.out.println("ModifiedByInfo present!!!");
		}
		if(!objTestSetDetailPage.isModifiedAtLabelPresent()){
			System.out.println("ModifiedAtLabel NOT present!!!");
		}else{
			System.out.println("ModifiedAtLabel present!!!");
		}
		if(!objTestSetDetailPage.isModifiedAtInfoPresent()){
			System.out.println("ModifiedAtInfo NOT present!!!");
		}else{
			System.out.println("ModifiedAtInfo present!!!");
		}
		if(!objTestSetDetailPage.isUsersIconPresent()){
			System.out.println("UsersIcon NOT present!!!");
		}else{
			System.out.println("UsersIcon present!!!");
		}
		if(!objTestSetDetailPage.isUsersTitlePresent()){
			System.out.println("UsersTitle NOT present!!!");
		}else{
			System.out.println("UsersTitle icon present!!!");
		}
		if(!objTestSetDetailPage.isCloseIconPresent()){
			System.out.println("CloseIcon NOT present!!!");
		}else{
			System.out.println("CloseIcon icon present!!!");
		}
	}
	
	public static void allElementsOnUsersPopupPresent(){
		if(objTestSetDetailPage == null){
			prepPage();
		}
		if(!objTestSetDetailPage.isUsersPopupHeaderPresent()){
			System.out.println("UsersPopupHeader NOT present!!!");
		}else{
			System.out.println("UsersPopupHeader present!!!");
		}
		if(!objTestSetDetailPage.isUsersListPresent()){
			System.out.println("UsersList NOT present!!!");
		}else{
			System.out.println("UsersList icon present!!!");
		}
		if(!objTestSetDetailPage.isUsersPopupCloseIconPresent()){
			System.out.println("UsersPopupCloseIcon NOT present!!!");
		}else{
			System.out.println("UsersPopupCloseIcon icon present!!!");
		}
	}
*/
	
}
