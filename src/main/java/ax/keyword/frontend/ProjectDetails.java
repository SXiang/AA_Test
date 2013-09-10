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
	protected String dpTestSetName; //@arg ProjectName whose link to be clicked for details
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpTestSetName = getDpString("TestSetName");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		verifyTestSetsList();
		verifyProjectsDropDownList();
		verifyDescriptionPanelContents();
		verifyInfoPanelContents();
		verifyHeaderFooter();		
		if(!dpTestSetName.isEmpty()){
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
		String header = getProjectHeader()+"|"+getProjectName()+"|"+getTestSetHeader();
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
	
	public void openTestSetDetails(){
		clickTestSetName(dpTestSetName);
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
	}
	
	/*
	private static ProjectDetailsPage objPrjctDetailPage=null;
	
	private static ProjectDetailsPage prepPage(){
		objPrjctDetailPage = new ProjectDetailsPage(driver);
		return objPrjctDetailPage;
	}

	public static void getProjectHeader(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getProjectHeader();
	}
	
	public static void getProjectName(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getProjectName();
	}
	
	public static void getProjectDesc(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getProjectDesc();
	}
	
	public static void getProjectDescLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getProjectDescLabel();
	}
	
	public static void getProjectsList(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getProjectsListFromDropDown();
	}
	
	public static void getProjectCreatorName(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getCreatedBy();
	}
	
	public static void getProjectCreatedDateTimeStamp(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getCreatedAt();
	}
	
	public static void getProjectModifierName(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getModifiedBy();
	}
	
	public static void getProjectModifiedDateTimeStamp(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getModifiedAt();
	}
	
	public static void getProjectCreatorNameLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getCreatedByLabel();
	}
	
	public static void getProjectCreatedDateTimeStampLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getCreatedAtLabel();
	}
	
	public static void getProjectModifierNameLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getModifiedByLabel();
	}
	
	public static void getProjectModifiedDateTimeStampLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getModifiedAtLabel();
	}
	
	public static void getInfoLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getInfoLabel();
	}
	
	public static void getUsersLabel(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getUsersLabel();
	}
	
	public static void getProjectUsersList(){

		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getUsersPopup();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getUsersPopupHeader();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		objPrjctDetailPage.getUsersList();
		objPrjctDetailPage.closeUsersPopup();
	
	}
	
	public static void getTestSetsList(){

		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getTestSetsList();
	}
	
	public static void filterTestSetsList(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.filterTestSetsList();
	}
	
	public static void getSearchItemsList(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getSearchItemsList();
	}
	
	public static void clearFilter(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.clearFilter();
	}
	
	public static void clickTestSetName(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.clickTestSetName();
	}
	
	public static void closeProjectDetailsLayer(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.closeProjectDetailsLayer();
	}
	
	public static void allElementsPresent() {
		if(objPrjctDetailPage == null){
			prepPage();
		}
		allElementsOnBasePagePresent();
		objPrjctDetailPage.getUsersPopup();
		allElementsOnUsersPopupPresent();
		objPrjctDetailPage.closeUsersPopup();
	}
	
	public static void allElementsOnBasePagePresent(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		if(!objPrjctDetailPage.isProjectHeaderPresent()){
			System.out.println("Projects List header NOT present!!!");
		}else{
			System.out.println("Projects List header present!!!");
		}
		if(!objPrjctDetailPage.isProjectNamePresent()){
			System.out.println("ProjectName NOT present!!!");
		}else{
			System.out.println("ProjectName present!!!");
		}
		if(!objPrjctDetailPage.isTestSetsHeaderPresent()){
			System.out.println("Test Sets List header NOT present!!!");
		}else{
			System.out.println("Test Sets List header present!!!");
		}
		if(!objPrjctDetailPage.isSearchBoxPresent()){
			System.out.println("Search Box NOT present!!!");
		}else{
			System.out.println("Search Box present!!!");
		}
		if(!objPrjctDetailPage.isSearchBoxIconPresent()){
			System.out.println("Search Box icon NOT present!!!");
		}else{
			System.out.println("Search box icon present!!!");
		}
		if(!objPrjctDetailPage.isDescTitlePresent()){
			System.out.println("Desc Title NOT present!!!");
		}else{
			System.out.println("Desc Title present!!!");
		}
		if(!objPrjctDetailPage.isDescIconPresent()){
			System.out.println("Desc Icon NOT present!!!");
		}else{
			System.out.println("Desc icon present!!!");
		}
		if(!objPrjctDetailPage.isInfoTitlePresent()){
			System.out.println("Info title NOT present!!!");
		}else{
			System.out.println("Info title present!!!");
		}
		if(!objPrjctDetailPage.isInfoIconPresent()){
			System.out.println("Info icon NOT present!!!");
		}else{
			System.out.println("Info icon present!!!");
		}
		if(!objPrjctDetailPage.isCreatedByLabelPresent()){
			System.out.println("CreatedByLabel NOT present!!!");
		}else{
			System.out.println("CreatedByLabel present!!!");
		}
		if(!objPrjctDetailPage.isCreatedByInfoPresent()){
			System.out.println("CreatedByInfo NOT present!!!");
		}else{
			System.out.println("CreatedByInfo present!!!");
		}
		if(!objPrjctDetailPage.isCreatedAtLabelPresent()){
			System.out.println("CreatedAtLabel NOT present!!!");
		}else{
			System.out.println("CreatedAtLabel present!!!");
		}
		if(!objPrjctDetailPage.isCreatedAtInfoPresent()){
			System.out.println("CreatedAtInfo NOT present!!!");
		}else{
			System.out.println("CreatedAtInfo present!!!");
		}
		if(!objPrjctDetailPage.isModifiedByLabelPresent()){
			System.out.println("ModifiedByLabel NOT present!!!");
		}else{
			System.out.println("ModifiedByLabel present!!!");
		}
		if(!objPrjctDetailPage.isModifiedByInfoPresent()){
			System.out.println("ModifiedByInfo present!!!");
		}else{
			System.out.println("ModifiedByInfo present!!!");
		}
		if(!objPrjctDetailPage.isModifiedAtLabelPresent()){
			System.out.println("ModifiedAtLabel NOT present!!!");
		}else{
			System.out.println("ModifiedAtLabel present!!!");
		}
		if(!objPrjctDetailPage.isModifiedAtInfoPresent()){
			System.out.println("ModifiedAtInfo NOT present!!!");
		}else{
			System.out.println("ModifiedAtInfo present!!!");
		}
		if(!objPrjctDetailPage.isUsersIconPresent()){
			System.out.println("UsersIcon NOT present!!!");
		}else{
			System.out.println("UsersIcon present!!!");
		}
		if(!objPrjctDetailPage.isUsersTitlePresent()){
			System.out.println("UsersTitle NOT present!!!");
		}else{
			System.out.println("UsersTitle icon present!!!");
		}
		if(!objPrjctDetailPage.isCloseIconPresent()){
			System.out.println("CloseIcon NOT present!!!");
		}else{
			System.out.println("CloseIcon icon present!!!");
		}
	}
	
	public static void allElementsOnUsersPopupPresent(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		if(!objPrjctDetailPage.isUsersPopupHeaderPresent()){
			System.out.println("UsersPopupHeader NOT present!!!");
		}else{
			System.out.println("UsersPopupHeader present!!!");
		}
		if(!objPrjctDetailPage.isUsersListPresent()){
			System.out.println("UsersList NOT present!!!");
		}else{
			System.out.println("UsersList icon present!!!");
		}
		if(!objPrjctDetailPage.isUsersPopupCloseIconPresent()){
			System.out.println("UsersPopupCloseIcon NOT present!!!");
		}else{
			System.out.println("UsersPopupCloseIcon icon present!!!");
		}
	}
	*/

}
