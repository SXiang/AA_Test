package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProjectsListPage {
	
	/**
	 * Script Name   : <b>ProjectsListPage</b>
	 * Generated     : <b>Aug 14, 2013</b>
	 * Description   : ProjectsListPage
	 * 
	 * @author Ramneet Kaur
	 */

	// Local variables
	private final WebDriver driver;
	//end
	
	// BEGIN of datapool variables declaration   
	private static String dpProjectName = "123456";	
	 // END of datapool variables declaration	
	
	// locators of the web elements of login page
	static By listIconLocator = By.cssSelector("i.icon-reorder.view-toggle ");
	static By cardIconLocator = By.xpath("//div/i");
	static By projectNameLocator = By.linkText(dpProjectName);
	static By allProjectsListLocator = By.className("projectTitle");
	//end
   
	
	public ProjectsListPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public ProjectsListPage viewList() {
		return clickViewTypeIcons(listIconLocator);
	}

	public ProjectsListPage viewCards() {
		return clickViewTypeIcons(cardIconLocator);
		
	}
	
	public ProjectsListPage clickViewTypeIcons(By selectedIcon) {
        driver.findElement(selectedIcon).click();
        return new ProjectsListPage(driver);
	}
	
	public ProjectDetailsPage clickProjectName() {
        driver.findElement(projectNameLocator).click();
        return new ProjectDetailsPage(driver);
	}
	
	public ProjectDetailsPage getAllProjects() {
		
        List<WebElement> allProjects = driver.findElements(allProjectsListLocator);
        System.out.println("\nProjectsize: "+allProjects.size()/2);
        for(int i = 0; i < allProjects.size(); i++) {
        	if(!"".equals(allProjects.get(i).getText())){
        		System.out.println("\nProject: "+allProjects.get(i).getText());
        	}
        }
        return new ProjectDetailsPage(driver);
	}
}
