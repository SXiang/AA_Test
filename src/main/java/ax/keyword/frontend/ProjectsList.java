package ax.keyword.frontend;

import pageObjects.ProjectsListPage;
import ax.lib.BrowserTaskHelper;

public class ProjectsList extends BrowserTaskHelper{
	

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
