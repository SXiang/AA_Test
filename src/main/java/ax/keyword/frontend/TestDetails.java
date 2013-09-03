package ax.keyword.frontend;

import ax.lib.BrowserTaskHelper;
import pageObjects.TestDetailsPage;

public class TestDetails extends BrowserTaskHelper{
	
	private static TestDetailsPage objTestDetailPage = null;
	
	private static TestDetailsPage prepPage(){
		objTestDetailPage = new TestDetailsPage(driver);
		return objTestDetailPage;
	}

	public static void getProjectName(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getProjectName();
	}
	
	public static void getProjectHeader(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getProjectHeader();
	}
	
	public static void getProjectsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getProjectsListFromDropDown();
	}

	public static void getTestSetName(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestSetName();
	}
	
	public static void getTestSetsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestSetsListFromDropDown();
	}
	
	public static void getTestName(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestName();
	}
	
	public static void getTestsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestsListFromDropDown();
	}

	public static void getTestDesc(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestDesc();
	}
	
	public static void getTestDescLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getTestDescLabel();
	}
	
	public static void getTestCreatorName(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getCreatedBy();
	}
	
	public static void getTestCreatedDateTimeStamp(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getCreatedAt();
	}
	
	public static void getTestModifierName(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getModifiedBy();
	}
	
	public static void getTestModifiedDateTimeStamp(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getModifiedAt();
	}
	
	public static void getTestCreatorNameLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getCreatedByLabel();
	}
	
	public static void getTestCreatedDateTimeStampLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getCreatedAtLabel();
	}
	
	public static void getTestModifierNameLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getModifiedByLabel();
	}
	
	public static void getTestModifiedDateTimeStampLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getModifiedAtLabel();
	}
	
	public static void getInfoLabel(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getInfoLabel();
	}
	
	public static void getAnalyticsHeaderList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		//objTestDetailPage.getAnalyticsHeaderList();
	}
	
	public static void getAnalyticsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getAnalyticsList();
	}
	
	public static void getAnalyticJobsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.openJobsDrawer();
		objTestDetailPage.readDrawerData();
	}
	
	public static void getAnalyticScheduleList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		//objTestDetailPage.getAnalyticScheduleList();
	}
	
	public static void viewAnalyticJobResults(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.openJobsDrawer();
		objTestDetailPage.clickViewResults();
	}
	
	public static void filterAnalyticsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.filterAnalyticsList();
	}
	
	public static void getSearchItemsList(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.getSearchItemsList();
	}
	
	public static void clearFilter(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.clearFilter();
	}

	public static void closeTestDetailsLayer(){
		if(objTestDetailPage == null){
			prepPage();
		}
		objTestDetailPage.closeTestDetailsLayer();
	}
	
	public static void allElementsPresent() {
		if(objTestDetailPage == null){
			prepPage();
		}
		allElementsOnBasePagePresent();
	}
	
	public static void allElementsOnBasePagePresent(){
		if(objTestDetailPage == null){
			prepPage();
		}
		if(!objTestDetailPage.isProjectHeaderPresent()){
			System.out.println("Projects List header NOT present!!!");
		}else{
			System.out.println("Projects List header present!!!");
		}
		if(!objTestDetailPage.isProjectNamePresent()){
			System.out.println("Project Name NOT present!!!");
		}else{
			System.out.println("Project Name present!!!");
		}
		if(!objTestDetailPage.isTestSetNamePresent()){
			System.out.println("ProjectName NOT present!!!");
		}else{
			System.out.println("ProjectName present!!!");
		}
		/*if(!objTestDetailPage.isTestsHeaderPresent()){
			System.out.println("Tests List header NOT present!!!");
		}else{
			System.out.println("Tests List header present!!!");
		}*/
		if(!objTestDetailPage.isSearchBoxPresent()){
			System.out.println("Search Box NOT present!!!");
		}else{
			System.out.println("Search Box present!!!");
		}
		if(!objTestDetailPage.isSearchBoxIconPresent()){
			System.out.println("Search Box icon NOT present!!!");
		}else{
			System.out.println("Search box icon present!!!");
		}
		if(!objTestDetailPage.isDescTitlePresent()){
			System.out.println("Desc Title NOT present!!!");
		}else{
			System.out.println("Desc Title present!!!");
		}
		if(!objTestDetailPage.isDescIconPresent()){
			System.out.println("Desc Icon NOT present!!!");
		}else{
			System.out.println("Desc icon present!!!");
		}
		if(!objTestDetailPage.isInfoTitlePresent()){
			System.out.println("Info title NOT present!!!");
		}else{
			System.out.println("Info title present!!!");
		}
		if(!objTestDetailPage.isInfoIconPresent()){
			System.out.println("Info icon NOT present!!!");
		}else{
			System.out.println("Info icon present!!!");
		}
		if(!objTestDetailPage.isCreatedByLabelPresent()){
			System.out.println("CreatedByLabel NOT present!!!");
		}else{
			System.out.println("CreatedByLabel present!!!");
		}
		if(!objTestDetailPage.isCreatedByInfoPresent()){
			System.out.println("CreatedByInfo NOT present!!!");
		}else{
			System.out.println("CreatedByInfo present!!!");
		}
		if(!objTestDetailPage.isCreatedAtLabelPresent()){
			System.out.println("CreatedAtLabel NOT present!!!");
		}else{
			System.out.println("CreatedAtLabel present!!!");
		}
		if(!objTestDetailPage.isCreatedAtInfoPresent()){
			System.out.println("CreatedAtInfo NOT present!!!");
		}else{
			System.out.println("CreatedAtInfo present!!!");
		}
		if(!objTestDetailPage.isModifiedByLabelPresent()){
			System.out.println("ModifiedByLabel NOT present!!!");
		}else{
			System.out.println("ModifiedByLabel present!!!");
		}
		if(!objTestDetailPage.isModifiedByInfoPresent()){
			System.out.println("ModifiedByInfo present!!!");
		}else{
			System.out.println("ModifiedByInfo present!!!");
		}
		if(!objTestDetailPage.isModifiedAtLabelPresent()){
			System.out.println("ModifiedAtLabel NOT present!!!");
		}else{
			System.out.println("ModifiedAtLabel present!!!");
		}
		if(!objTestDetailPage.isModifiedAtInfoPresent()){
			System.out.println("ModifiedAtInfo NOT present!!!");
		}else{
			System.out.println("ModifiedAtInfo present!!!");
		}
		if(!objTestDetailPage.isCloseIconPresent()){
			System.out.println("CloseIcon NOT present!!!");
		}else{
			System.out.println("CloseIcon icon present!!!");
		}
	}
}
