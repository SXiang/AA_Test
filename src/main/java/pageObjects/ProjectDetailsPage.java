package pageObjects;


import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


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
		private String[] dropDownMenuList;
		private List<WebElement> users;
		private String[] usersList;
		private String infoList;
		//end
		
		// BEGIN of datapool variables declaration   
		
		 // END of datapool variables declaration	
		
		// locators of the web elements of login page
		static By projectHeaderLocator = By.className("project-header");
		static By projectNameLocator = By.className("title");
		static By projectDropDownLocator = By.className("dropdown");
		static By projectDropDownMenuItemsLocator = By.className("dropdown-menu");
		static By testSetsHeaderLocator = By.className("testset-subtitle");
		static By testSetsNameLocator = By.className("testset-row");
		static By searchBoxLocator = By.className("search-query");
		static By searchBoxIconLocator = By.className("icon-search");
		static By rightPanelTitleLocator = By.className("right-panel-block-title");
		static By rightPanelIconLocator = By.className("right-panel-block-icon");
		static By rightPanelContentLocator = By.className("right-panel-block-content");
		static By closeIconLocator = By.className("icon_remove");
		static By usersPopupHeaderLocator = By.className("modal-title");
		static By usersListLocator = By.xpath("html/body/div[4]/div[2]/ul/li");
		static By usersPopupCloseIconLocator = By.className("icon-remove");
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
		public ProjectDetailsPage getProjectsListFromDropDown(){
			driver.findElement(projectDropDownLocator).click();
			dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
			dropDownMenu.toArray(dropDownMenuList);
			System.out.println("Projects List from DropDown: ");
			for(String projects:dropDownMenuList){
				System.out.println(projects);
			}
			return this;
		}
		public ProjectDetailsPage getProjectDesc(){
			System.out.println("ProjectDesc: "+driver.findElements(rightPanelContentLocator).get(0).getText());
			return this;
		}
		public ProjectDetailsPage getCreatedBy(){
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			System.out.println("Created By: "+infoList.split("[\r\n]+")[1]);
			return this;
		}
		public ProjectDetailsPage getCreatedAt(){
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			System.out.println("Created At: "+infoList.split("[\r\n]+")[3]);
			return this;
		}
		public ProjectDetailsPage getModifiedBy(){
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			System.out.println("Modified By: "+infoList.split("[\r\n]+")[5]);
			return this;
		}
		public ProjectDetailsPage getModifiedAt(){
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			System.out.println("Modified At: "+infoList.split("[\r\n]+")[7]);
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
			/**
			users = driver.findElements(usersListLocator);
			System.out.println("Size: "+users.size());
			for(int i=0,j=1;i < users.size(); i++,j++){
				System.out.println("#: "+users.get(i+1).getText());
				usersList[i] = users.get(j).getText();
			}
			System.out.println("Users List from Popup: ");
			for(int i=0;i <= usersList.length; i++){
				System.out.println(usersList[i]);
			}
			*/
			return this;
		}
		public ProjectDetailsPage closeUsersPopup(){
			driver.findElement(usersPopupCloseIconLocator).click();
	        return this;
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
			rightPanelData = driver.findElements(rightPanelContentLocator);
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
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[0].isEmpty();
	    }
		public boolean isCreatedByInfoPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[1].isEmpty();
	    }
		public boolean isCreatedAtLabelPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[2].isEmpty();
	    }
		public boolean isCreatedAtInfoPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[3].isEmpty();
	    }
		public boolean isModifiedByLabelPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[4].isEmpty();
	    }
		public boolean isModifiedByInfoPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[5].isEmpty();
	    }
		public boolean isModifiedAtLabelPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[6].isEmpty();
	    }
		public boolean isModifiedAtInfoPresent() {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			infoList = driver.findElements(rightPanelContentLocator).get(1).getText();
			return !infoList.split("[\r\n]+")[7].isEmpty();
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
