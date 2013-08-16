package TestCases;

import ax.keyword.frontend.Login;
import ax.keyword.frontend.ProjectDetails;
import ax.keyword.frontend.ProjectsList;

public class TestFrontEnd {
	
	/**
	 * Script Name   : <b>TestFrontEnd</b>
	 * Generated     : <b>Aug 13, 2013</b>
	 * Description   : TestFrontEnd main file to run test keywords
	 * 
	 * @author Ramneet Kaur
	 */
	
	public static void main(String[] args) {
		//Login.allElementsPresent();
		Login.testLogin();
		//ProjectsList.allElementsPresent();
		//ProjectsList.viewList();
		//ProjectsList.viewCards();
		//ProjectsList.getAllProjects();
		ProjectsList.clickProjectName();
		//ProjectDetails.allElementsPresent();
		//ProjectDetails.getProjectName();
		//ProjectDetails.getProjectDesc();
		//ProjectDetails.getProjectCreatorName();
		//ProjectDetails.getProjectCreatedDateTimeStamp();
		//ProjectDetails.getProjectModifierName();
		//ProjectDetails.getProjectModifiedDateTimeStamp();
		//ProjectDetails.getProjectUsersList();
		
	}

}
