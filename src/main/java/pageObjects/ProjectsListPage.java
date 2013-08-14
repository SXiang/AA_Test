package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProjectsListPage {

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
		
        List<WebElement> allProjects = driver.findElements(By.className("projectTitle"));
        for(int i = 0; i < allProjects.size(); i++) {
            System.out.println("\nProject: "+allProjects.get(i));
        }
        return new ProjectDetailsPage(driver);
	}
}
