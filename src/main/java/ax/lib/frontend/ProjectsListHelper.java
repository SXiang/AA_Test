package ax.lib.frontend;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ProjectsListHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>ProjectsListHelper</b>
	 * Generated     : <b>Sep 5, 2013</b>
	 * Description   : ProjectsListHelper
	 * 
	 * @since  Sep 5, 2013
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of ProjectsList page
	By listIconLocator = By.cssSelector("i.icon-reorder");
	By cardIconLocator = By.cssSelector("i.icon-th-large");
	By projectNameInCardTypeLocator = By.cssSelector("div.projectTitle > a > strong.ng-binding");
	By projectNameInListTypeLocator = By.cssSelector("div.projectTitle > strong.ng-binding");
	By projectHeaderLocator = By.className("project-header");
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> allProjects;
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
		isElementEnabled(listIconLocator);
		isElementEnabled(cardIconLocator);
		isElementDisplayed(projectHeaderLocator);
		isElementEnabled(searchBoxLocator);
		isElementDisplayed(searchBoxIconLocator);
		isElementDisplayed(copyrightFooter);
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void viewList() {
		driver.findElement(listIconLocator).click();
	}

	public void viewCards() {
		driver.findElement(cardIconLocator).click();
	}
	
	public String findViewType(){
		if(driver.findElement(cardIconLocator).getAttribute("class").contains("active")){
			return "card";
		}
	    return "list"; 
	}
	
	public  String getAllProjects(String viewType) {
		if(viewType.equalsIgnoreCase("card")){
			allProjects = driver.findElements(projectNameInCardTypeLocator);
		}else{
			allProjects = driver.findElements(projectNameInListTypeLocator);
		}
		String projects = "";
        for(int i = 0; i < allProjects.size(); i++) {
        	if(i==0){
        		projects=allProjects.get(i).getText();
        	}else{
        		projects=projects+"|"+allProjects.get(i).getText();
        	}
        }
        return projects;
	}

	public String getProjectHeader() {
		return driver.findElement(projectHeaderLocator).getText();
	}	
	
	public boolean clickProjectName(String viewType, String projectName) {
		if(viewType.equalsIgnoreCase("card")){
			allProjects = driver.findElements(projectNameInCardTypeLocator);
		}else{
			allProjects = driver.findElements(projectNameInListTypeLocator);
		}
		for(int i = 0; i < allProjects.size(); i++) {
        	if(allProjects.get(i).getText().equals(projectName)){
        		logTAFStep("Project: "+projectName+" found and clicked on!!!");
        		allProjects.get(i).click();
        		return true;
        	}
        }
		logTAFError("Project: "+projectName+" not found!!");
		return false;
	}	

}
