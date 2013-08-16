package ax.keyword.frontend;

import ax.lib.BrowserTaskHelper;
import pageObjects.ProjectDetailsPage;

public class ProjectDetails  extends BrowserTaskHelper{
	
	/**
	 * Script Name   : <b>ProjectsList</b>
	 * Generated     : <b>Aug 16, 2013</b>
	 * Description   : ProjectsList keyword
	 * 
	 * @author Ramneet Kaur
	 */
	
	private static ProjectDetailsPage objPrjctDetailPage=null;
	
	private static ProjectDetailsPage prepPage(){
		objPrjctDetailPage = new ProjectDetailsPage(driver);
		return objPrjctDetailPage;
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
		objPrjctDetailPage.getCreatedBy();
	}
	
	public static void getProjectCreatedDateTimeStamp(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getCreatedAt();
	}
	
	public static void getProjectModifierName(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getModifiedBy();
	}
	
	public static void getProjectModifiedDateTimeStamp(){
		if(objPrjctDetailPage == null){
			prepPage();
		}
		objPrjctDetailPage.getModifiedAt();
	}
	
	public static void getProjectUsersList(){
		/**
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
		*/
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

}
