package pageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ax.lib.ReadDatapool;

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
	private List<WebElement> allProjects;
	private List<WebElement> searchItems;
	//end
	
	// BEGIN of datapool variables declaration   
	private static String dpProjectName = ReadDatapool.getDpProjectName();	
	private static String[] dpSearchItems = ReadDatapool.getDpSearchItems();
	 // END of datapool variables declaration	
	
	// locators of the web elements of login page
	static By listIconLocator = By.cssSelector("i.icon-reorder");
	static By cardIconLocator = By.cssSelector("i.icon-th-large");
	static By cardIconSelectedLocator = By.cssSelector("i.icon-th-large.active");
	static By listIconSelectedLocator = By.cssSelector("i.icon-reorder.active");
	static By projectNameInCardTypeLocator = By.cssSelector("div.projectTitle > a > strong.ng-binding");
	static By projectNameInListTypeLocator = By.cssSelector("div.projectTitle > strong.ng-binding");
	static By projectHeaderLocator = By.className("project-header");
	static By searchBoxLocator = By.cssSelector("div.multi-name-search > input.search-query.search-input");
	static By searchBoxIconLocator = By.cssSelector("div.multi-name-search > div.icon-search");
	static By searchItemLocator = By.cssSelector("li.search-item-row > button.search-item");
	static By searchCancelFilterIconLocator = By.cssSelector("li.search-item-row > i.icon-remove");
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
	
	public String findViewType(){
		try {
	        driver.findElement(cardIconSelectedLocator);
	    } catch (NoSuchElementException e) {
	        return "list";
	    }
	    return "card";
	}
	
	public ProjectsListPage getAllProjects() {
		if(findViewType().equalsIgnoreCase("card")){
			allProjects = driver.findElements(projectNameInCardTypeLocator);
		}else{
			allProjects = driver.findElements(projectNameInListTypeLocator);
		}
        System.out.println("All Projects: ");
        for(int i = 0; i < allProjects.size(); i++) {
        	System.out.println(allProjects.get(i).getText());
        }
        return this;
	}

	public ProjectsListPage getProjectHeader() {
		System.out.println("\nProject Header: "+driver.findElement(projectHeaderLocator).getText());
        return this;
		
	}	
	
	public ProjectsListPage filterTestSetsList(){
		for(int i=0;i<dpSearchItems.length;i++){
			driver.findElement(searchBoxLocator).click();
			driver.findElement(searchBoxLocator).sendKeys(dpSearchItems[i]);
			driver.findElement(searchBoxIconLocator).click();
		}	
		return this;
	}
	
	public ProjectsListPage getSearchItemsList(){
		searchItems = driver.findElements(searchItemLocator);
		System.out.println("All Filtered items List: ");
		for(int i=0;i<searchItems.size();i++){
			System.out.println(searchItems.get(i).getText());
		}
		return this;
	}
	
	public ProjectsListPage clearFilter(){
		searchItems = driver.findElements(searchCancelFilterIconLocator);
		for(int i=0;i<searchItems.size();i++){
			searchItems.get(i).click();
		}
		return this;
	}
	
	public ProjectDetailsPage clickProjectName() {
		if(findViewType().equalsIgnoreCase("card")){
			allProjects = driver.findElements(projectNameInCardTypeLocator);
		}else{
			allProjects = driver.findElements(projectNameInListTypeLocator);
		}
		for(int i = 0; i < allProjects.size(); i++) {
        	if(allProjects.get(i).getText().equals(dpProjectName)){
        		allProjects.get(i).click();
                return new ProjectDetailsPage(driver);
        	}
        }
		System.out.println("Project: "+dpProjectName+" not found!!");
		return null;
	}
	
	public boolean isListIconPresent() {
        return driver.findElement(listIconLocator).isDisplayed(); 
    }
	public boolean isCardIconPresent() {
        return driver.findElement(cardIconLocator).isDisplayed(); 
    }
	public boolean isProjectHeaderPresent() {
        return driver.findElement(projectHeaderLocator).isDisplayed(); 
    }
	public boolean isSearchBoxPresent() {
        return driver.findElement(searchBoxLocator).isEnabled(); 
    }
	public boolean isSearchBoxIconPresent() {
        return driver.findElement(searchBoxIconLocator).isDisplayed(); 
    }
		
}
