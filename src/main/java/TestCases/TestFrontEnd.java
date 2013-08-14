package TestCases;

import ax.keyword.frontend.Login;
import ax.keyword.frontend.ProjectsList;

public class TestFrontEnd {
	
	public static void main(String[] args) {
		
		Login objLogin = new Login();
		objLogin.testLogin();
		ProjectsList.viewList();
		ProjectsList.viewCards();
		ProjectsList.clickProjectName();
	}

}
