package pageObjects;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ax.lib.frontend.ReadDatapool;


public class ProjectDetailsPage {

	/**
	 * Script Name   : <b>ProjectDetailsPage</b>
	 * Generated     : <b>Aug 16, 2013</b>
	 * Description   : ProjectDetailsPage
	 * 
	 * @author Ramneet Kaur
	 */
	// Local variables
		private final WebDriver driver;
		private List<WebElement> rightPanelData;
		private List<WebElement> dropDownMenu;
		private List<String> dropDownMenuList = new ArrayList<String>();
		private List<WebElement> users;
		private List<String> usersList = new ArrayList<String>();
		private List<WebElement> testSets;
		private List<String> testSetsList = new ArrayList<String>();
		private List<WebElement> searchItems;
		private int index;
		//end
		
		// BEGIN of datapool variables declaration   
		private static String dpTestSetName = ReadDatapool.getDpTestSetName();
		private static String[] dpSearchItems = ReadDatapool.getDpSearchItems();
		 // END of datapool variables declaration	
		
		// locators of the web elements of login page
		static By projectHeaderLocator = By.cssSelector("div.project-header > a > span");
		static By projectNameLocator = By.cssSelector("div.title > span");
		static By projectDropDownLocator = By.cssSelector("div.dropdown > a.dropdown-toggle > i");
		static By projectDropDownMenuItemsLocator = By.cssSelector("div.dropdown > ul.dropdown-menu > li.ng-scope");
		static By testSetsHeaderLocator = By.cssSelector("div.testset-subtitle > span");
		static By testSetsNameLocator = By.cssSelector("div.testset-row > div.row-fluid > div");
		static By searchBoxLocator = By.cssSelector("div.multi-name-search > input.search-query.search-input");
		static By searchBoxIconLocator = By.cssSelector("div.multi-name-search > div.icon-search");
		static By searchItemLocator = By.cssSelector("li.search-item-row > button.search-item");
		static By searchCancelFilterIconLocator = By.cssSelector("li.search-item-row > i.icon-remove");
		static By rightPanelTitleLocator = By.className("right-panel-block-title");
		static By rightPanelIconLocator = By.className("right-panel-block-icon");
		static By descriptionLocator = By.className("right-panel-block-content");
		static By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
		static By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
		static By closeIconLocator = By.cssSelector("div.title-row > div > i.icon_remove");
		static By usersPopupHeaderLocator = By.className("modal-title");
		static By usersListLocator = By.cssSelector("div.modal-body > ul.user-list > li.user-row");
		static By usersPopupCloseIconLocator = By.cssSelector("div.modal-header > div.icon-remove");
		//end
	   
		public ProjectDetailsPage(WebDriver driver) {
			this.driver = driver;
		}
		
		public String getProjectHeader(){
			return driver.findElement(projectHeaderLocator).getText();
		}
		public ProjectDetailsPage getProjectName(){
			System.out.println("ProjectName: "+driver.findElement(projectNameLocator).getText());
			return this;
		}
		public ProjectDetailsPage getProjectDescLabel() {
			System.out.println("ProjectDescLabel: "+driver.findElement(descriptionLocator).getText());
			return this;
		}
		public ProjectDetailsPage getProjectsListFromDropDown(){
			driver.findElement(projectDropDownLocator).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
			for(int i=0;i<dropDownMenu.size();i++){
				dropDownMenuList.add(dropDownMenu.get(i).getText());
			}
			System.out.println("Projects List from DropDown: ");
			for(String projects:dropDownMenuList){
				System.out.println(projects);
			}
			driver.findElement(projectDropDownLocator).click();
			return this;
		}
		public ProjectDetailsPage getProjectDesc(){
			System.out.println("ProjectDesc: "+driver.findElements(descriptionLocator).get(0).getText());
			return this;
		}
		public ProjectDetailsPage getCreatedBy(){
			
			System.out.println("Created By: "+driver.findElements(infoContentDataLocator).get(0).getText());
			return this;
		}
		public ProjectDetailsPage getCreatedAt(){
			System.out.println("Created At: "+driver.findElements(infoContentDataLocator).get(1).getText());
			return this;
		}
		public ProjectDetailsPage getModifiedBy(){
			System.out.println("Modified By: "+driver.findElements(infoContentDataLocator ).get(2).getText());
			return this;
		}
		public ProjectDetailsPage getModifiedAt(){
			System.out.println("Modified At: "+driver.findElements(infoContentDataLocator ).get(3).getText());
			return this;
		}
		public ProjectDetailsPage getCreatedByLabel(){
			
			System.out.println("Created By Label: "+driver.findElements(infoContentLabelLocator).get(0).getText());
			return this;
		}
		public ProjectDetailsPage getCreatedAtLabel(){
			System.out.println("Created At Label: "+driver.findElements(infoContentLabelLocator).get(1).getText());
			return this;
		}
		public ProjectDetailsPage getModifiedByLabel(){
			System.out.println("Modified By Label: "+driver.findElements(infoContentLabelLocator ).get(2).getText());
			return this;
		}
		public ProjectDetailsPage getModifiedAtLabel(){
			System.out.println("Modified At Label: "+driver.findElements(infoContentLabelLocator ).get(3).getText());
			return this;
		}
		public ProjectDetailsPage getInfoLabel() {
			System.out.println("Info Label: "+driver.findElements(rightPanelTitleLocator).get(1).getText());
	        return this;
	    }
		public ProjectDetailsPage getUsersLabel() {
			System.out.println("Users Label: "+driver.findElements(rightPanelTitleLocator).get(2).getText());
	        return this;
	    }
		public ProjectDetailsPage getUsersPopup(){
			driver.findElements(rightPanelTitleLocator).get(2).click();
	        return this;
		}
		public ProjectDetailsPage getUsersPopupHeader(){
			System.out.println("UsersPopup header: "+driver.findElement(usersPopupHeaderLocator).getText());
	        return this;
		}
		public ProjectDetailsPage getUsersList(){
			users = driver.findElements(usersListLocator);
			for(int i=1;i<users.size();i++){
				usersList.add(users.get(i).getText());
			}
			System.out.println("Users List from Popup: ");
			for(String singleUser : usersList){
				System.out.println(singleUser);
			}
			return this;
		}
		public ProjectDetailsPage closeUsersPopup(){
			driver.findElement(usersPopupCloseIconLocator).click();
	        return this;
		}
		
		public ProjectDetailsPage getTestSetsList(){
			testSets = driver.findElements(testSetsNameLocator);
			for(int i=0;i<testSets.size();i++){
				testSetsList.add(testSets.get(i).getText());
			}
			System.out.println("Test Sets List: ");
			for(String singleTestSet : testSetsList){
				System.out.println(singleTestSet);
			}	
			return this;
		}
		
		public ProjectDetailsPage filterTestSetsList(){
			for(int i=0;i<dpSearchItems.length;i++){
				driver.findElement(searchBoxLocator).click();
				driver.findElement(searchBoxLocator).sendKeys(dpSearchItems[i]);
				driver.findElement(searchBoxIconLocator).click();
			}	
			return this;
		}
		
		public ProjectDetailsPage getSearchItemsList(){
			searchItems = driver.findElements(searchItemLocator);
			System.out.println("All Filtered items List: ");
			for(int i=0;i<searchItems.size();i++){
				System.out.println(searchItems.get(i).getText());
			}
			return this;
		}
		
		public ProjectDetailsPage clearFilter(){
			searchItems = driver.findElements(searchCancelFilterIconLocator);
			for(int i=0;i<searchItems.size();i++){
				searchItems.get(i).click();
			}
			return this;
		}

		public TestSetDetailsPage clickTestSetName(){
			testSets = driver.findElements(testSetsNameLocator);
			for(int i=0; i<testSets.size();i++){
				if(testSets.get(i).getText().equals(dpTestSetName)){
					index = testSets.indexOf(testSets.get(i));
				}
			}
			if(index <0){
				System.out.println("Test Set: "+dpTestSetName+" not found!!");
				return null;
			}
			testSets.get(index).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new TestSetDetailsPage(driver);
		}
		
		public ProjectsListPage closeProjectDetailsLayer(){
			driver.findElement(closeIconLocator).click();
			return new ProjectsListPage(driver);
		}
		
		public boolean isProjectHeaderPresent() {
	        return driver.findElement(projectHeaderLocator).isEnabled(); 
	    }
		public boolean isProjectNamePresent() {
	        return driver.findElement(projectNameLocator).isDisplayed(); 
	    }
		public boolean isProjectDropDownPresent() {
	        return driver.findElement(projectDropDownLocator).isEnabled(); 
	    }
		public boolean isSearchBoxPresent() {
	        return driver.findElement(searchBoxLocator).isEnabled(); 
	    }
		public boolean isSearchBoxIconPresent() {
	        return driver.findElement(searchBoxIconLocator).isDisplayed(); 
	    }
		public boolean isTestSetsHeaderPresent() {
	        return driver.findElement(testSetsHeaderLocator).isDisplayed(); 
	    }
		public boolean isDescTitlePresent() {
			rightPanelData = driver.findElements(rightPanelTitleLocator);
	        return rightPanelData.get(0).isDisplayed(); 
	    }
		public boolean isDescIconPresent() {
			rightPanelData = driver.findElements(rightPanelIconLocator);
	        return rightPanelData.get(0).isDisplayed(); 
	    }
		public boolean isDescContentBoxPresent() {
			rightPanelData = driver.findElements(descriptionLocator);
	        return rightPanelData.get(0).isDisplayed();
	    }
		public boolean isInfoTitlePresent() {
			rightPanelData = driver.findElements(rightPanelTitleLocator);
	        return rightPanelData.get(1).isDisplayed();
	    }
		public boolean isInfoIconPresent() {
			rightPanelData = driver.findElements(rightPanelIconLocator);
	        return rightPanelData.get(1).isDisplayed();
	    }
		public boolean isCreatedByLabelPresent() {
			return driver.findElements(infoContentLabelLocator).get(0).isDisplayed();
	    }
		public boolean isCreatedByInfoPresent() {
			return driver.findElements(infoContentDataLocator).get(0).isDisplayed();
	    }
		public boolean isCreatedAtLabelPresent() {
			return driver.findElements(infoContentLabelLocator).get(1).isDisplayed();
	    }
		public boolean isCreatedAtInfoPresent() {
			return driver.findElements(infoContentDataLocator).get(1).isDisplayed();
	    }
		public boolean isModifiedByLabelPresent() {
			return driver.findElements(infoContentLabelLocator).get(2).isDisplayed();
	    }
		public boolean isModifiedByInfoPresent() {
			return driver.findElements(infoContentDataLocator).get(2).isDisplayed();
	    }
		public boolean isModifiedAtLabelPresent() {
			return driver.findElements(infoContentLabelLocator).get(3).isDisplayed();
	    }
		public boolean isModifiedAtInfoPresent() {
			return driver.findElements(infoContentDataLocator).get(3).isDisplayed();
	    }
		public boolean isUsersTitlePresent() {
			rightPanelData = driver.findElements(rightPanelTitleLocator);
	        return rightPanelData.get(2).isDisplayed();
	    }
		public boolean isUsersIconPresent() {
			rightPanelData = driver.findElements(rightPanelIconLocator);
	        return rightPanelData.get(2).isDisplayed();
	    }
		public boolean isCloseIconPresent() {
	        return driver.findElement(closeIconLocator).isEnabled(); 
	    }
		public boolean isUsersPopupHeaderPresent(){
	        return driver.findElement(usersPopupHeaderLocator).isDisplayed();
		}
		public boolean isUsersPopupCloseIconPresent(){
	        return driver.findElement(usersPopupCloseIconLocator).isEnabled();
		}
		public boolean isUsersListPresent(){
			users = driver.findElements(usersListLocator);
			if(users.size()>0){
				return true;
			}
			return false;
		}

}
