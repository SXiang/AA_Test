package ax.keyword.frontend;

import pageObjects.ProjectsListPage;
import ax.lib.BrowserTaskHelper;

public class ProjectsList extends BrowserTaskHelper{
	
	/**
	 * Script Name   : <b>ProjectsList</b>
	 * Generated     : <b>Aug 13, 2013</b>
	 * Description   : ProjectsList keyword
	 * 
	 * @author Ramneet Kaur
	 */
	
	public static void viewList() {
		ProjectsListPage obj = new ProjectsListPage(driver);
		obj.viewList();
	}
		
	public static void viewCards() {
		ProjectsListPage obj = new ProjectsListPage(driver);
		obj.viewCards();
			
	}
	
	public static void clickProjectName() {
		ProjectsListPage obj = new ProjectsListPage(driver);
		obj.clickProjectName();
			
	}
	
	public static void getAllProjects() {
		ProjectsListPage obj = new ProjectsListPage(driver);
		obj.getAllProjects();
			
	}
}
