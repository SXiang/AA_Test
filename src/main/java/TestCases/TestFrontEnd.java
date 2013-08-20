package TestCases;

import ax.keyword.frontend.Login;
import ax.keyword.frontend.ProjectDetails;
import ax.keyword.frontend.ProjectsList;
import ax.keyword.frontend.TestSetDetails;

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
		//ProjectsList.getProjectHeader();
		//ProjectsList.viewList();
		//ProjectsList.viewCards();
		//ProjectsList.getAllProjects();
		//ProjectsList.filterTestSetsList();
		//ProjectsList.getSearchItemsList();
		//ProjectsList.clearFilter();
		//ProjectsList.viewList();
		//ProjectsList.getAllProjects();
		
		ProjectsList.clickProjectName();
		
		//ProjectDetails.allElementsPresent();
		//ProjectDetails.getProjectHeader();
		//ProjectDetails.getProjectName();
		//ProjectDetails.getProjectDescLabel();
		//ProjectDetails.getProjectDesc();
		//ProjectDetails.getInfoLabel();
		//ProjectDetails.getProjectCreatorNameLabel();
		//ProjectDetails.getProjectCreatorName();
		//ProjectDetails.getProjectCreatedDateTimeStampLabel();
		//ProjectDetails.getProjectCreatedDateTimeStamp();
		//ProjectDetails.getProjectModifierNameLabel();
		//ProjectDetails.getProjectModifierName();
		//ProjectDetails.getProjectModifiedDateTimeStampLabel();
		//ProjectDetails.getProjectModifiedDateTimeStamp();
		//ProjectDetails.getProjectsList();
		//ProjectDetails.getTestSetsList();
		//ProjectDetails.getUsersLabel();
		//ProjectDetails.getProjectUsersList();
		//ProjectDetails.closeProjectDetailsLayer();
		//ProjectDetails.filterTestSetsList();
		//ProjectDetails.getSearchItemsList();
		//ProjectDetails.clearFilter();
		
		ProjectDetails.clickTestSetName();
		
		//TestSetDetails.allElementsPresent();
		//TestSetDetails.getProjectHeader();
		//TestSetDetails.getProjectName();
		//TestSetDetails.getTestSetName();
		//TestSetDetails.getDataTablesLabel();
		//TestSetDetails.getRelatedFilesLabel();
		//TestSetDetails.getTestSetDescLabel();
		//TestSetDetails.getTestSetDesc();
		//TestSetDetails.getInfoLabel();
		//TestSetDetails.getProjectCreatorNameLabel();
		//TestSetDetails.getProjectCreatedDateTimeStampLabel();
		//TestSetDetails.getProjectModifierNameLabel();
		//TestSetDetails.getProjectModifiedDateTimeStampLabel();
		//TestSetDetails.getTestSetCreatorName();
		//TestSetDetails.getTestSetCreatedDateTimeStamp();
		//TestSetDetails.getTestSetModifierName();
		//TestSetDetails.getTestSetModifiedDateTimeStamp();
		//TestSetDetails.getProjectsList();
		//TestSetDetails.getTestSetsList();
		//TestSetDetails.getTestsList();
		//TestSetDetails.getUsersLabel();
		//TestSetDetails.getTestSetUsersList();
		//TestSetDetails.closeTestSetDetailsLayer();
		//TestSetDetails.filterTestsList();
		//TestSetDetails.getSearchItemsList();
		//TestSetDetails.clearFilter();
		
	}

}
