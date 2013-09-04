package ax.keyword.frontend;

import pageObjects.ProjectsListPage;
import ax.lib.frontend.LoginHelper;

public class ProjectsList extends LoginHelper{
	
	/**
	 * Script Name   : <b>ProjectsList</b>
	 * Generated     : <b>Aug 13, 2013</b>
	 * Description   : ProjectsList keyword
	 * 
	 * @author Ramneet Kaur
	 */
	
	private static ProjectsListPage objPrjctListPage=null;
	
	private static ProjectsListPage prepPage(){
		objPrjctListPage = new ProjectsListPage(driver);
		return objPrjctListPage;
	}
	
	public static void viewList() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.viewList();
	}
		
	public static void viewCards() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.viewCards();
			
	}
	
	public static void clickProjectName() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.clickProjectName();
			
	}
	
	public static void getAllProjects() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.getAllProjects();	
	}
	
	public static void filterTestSetsList(){
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.filterTestSetsList();
	}
	
	public static void getSearchItemsList(){
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.getSearchItemsList();
	}
	
	public static void clearFilter(){
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.clearFilter();
	}
	
	public static void getProjectHeader() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.getProjectHeader();		
	}
	
	public static void verifyOnProjectsListPage() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.getProjectHeader();		
	}
	
	public static void findViewType() {
		if(objPrjctListPage == null){
			prepPage();
		}
		objPrjctListPage.findViewType();
		
	}
	
	public static void allElementsPresent(){
		if(objPrjctListPage == null){
			prepPage();
		}
		if(!objPrjctListPage.isProjectHeaderPresent()){
			System.out.println("Projects List header NOT present!!!");
		}else{
			System.out.println("Projects List header present!!!");
		}
		if(!objPrjctListPage.isCardIconPresent()){
			System.out.println("Card View icon NOT present!!!");
		}else{
			System.out.println("Card View icon present!!!");
		}
		if(!objPrjctListPage.isListIconPresent()){
			System.out.println("List View icon NOT present!!!");
		}else{
			System.out.println("List View icon present!!!");
		}
		if(!objPrjctListPage.isSearchBoxPresent()){
			System.out.println("Search Box NOT present!!!");
		}else{
			System.out.println("Search Box present!!!");
		}
		if(!objPrjctListPage.isSearchBoxIconPresent()){
			System.out.println("Search Box icon NOT present!!!");
		}else{
			System.out.println("Search box icon present!!!");
		}
	}

	
	
}
