package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ax.lib.ReadDatapool;

public class TestDetailsPage  {
	
	/**
	 * Script Name   : <b>TestDetailsPage</b>
	 * Generated     : <b>Aug 22, 2013</b>
	 * Description   : TestDetailsPage
	 * 
	 * @author Ramneet Kaur
	 */
	// Local variables
	private final WebDriver driver;
	private List<WebElement> rightPanelData;
	private List<WebElement> dropDownMenu;
	private List<String> dropDownMenuList = new ArrayList<String>();
	private List<String> dropDownMenuListTestSets = new ArrayList<String>();
	private List<String> dropDownMenuListTests = new ArrayList<String>();
	private List<WebElement> analytics;
	private List<String> analyticsList = new ArrayList<String>();
	private List<WebElement> drawerDataHeader;
	private List<WebElement> drawerTextData;
	private List<WebElement> drawerDTData;
	private List<String> drawerDataHeaderList = new ArrayList<String>();
	private List<WebElement> searchItems;
	private int analyticIndex;
	private String openDrawerName = "";
	//end
	private By analyticDrawerDataLocator;
		
	// BEGIN of datapool variables declaration   
	private static String dpAnalyticName = ReadDatapool.getDpAnalyticName();
	private static String[] dpSearchItems = ReadDatapool.getDpSearchItems();
	private static int dpJobNumber = ReadDatapool.getDpJobNumber();
	// END of datapool variables declaration	
		
	// locators of the web elements of login page
	static By projectHeaderLocator = By.cssSelector("div.project-header > a > span");
	static By projectNameLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-title > span");
	static By testSetNameLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-title > span");
	static By testNameLocator = By.cssSelector("div.title > span");
	static By projectDropDownLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-dropdown > a");
	static By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-dropdown > ul.dropdown-menu > li.ng-scope");
	static By testSetDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a");
	static By testSetDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > ul.dropdown-menu > li.ng-scope");
	static By testDropDownLocator = By.cssSelector("div.title-row > div.dropdown > a");
	static By testDropDownMenuItemsLocator = By.cssSelector("div.title-row > div.dropdown > ul.dropdown-menu > li.ng-scope");
	static By analyticsHeaderLocator = By.cssSelector("div.analytic-script-subtitle > span");
	static By analyticNameLocator = By.cssSelector("div.script-row > div.row-fluid > div.ng-binding");
	static By analyticNameOfOpenDrawerLocator = By.cssSelector("div.drawer-opened > div > div.ng-binding");
	static By analyticRunIconLocator = By.cssSelector("div.script-row > div > i");
	static By analyticDrawerBtnLocator = By.cssSelector("div.drawer > a.action-btn");
	static By analyticJobsIconLocator = By.cssSelector("div.script-row > div > div > i.icon_list");
	static By analyticScheduleIconLocator = By.cssSelector("div.script-row > div > div > i.icon_comment");
	static By analyticJobDrawerHeaderLocator = By.cssSelector("div.drawer > div > h3 > span");
	static By analyticScheduleDrawerHeaderLocator = By.cssSelector("div.drawer > div > div.strong > span");
	static By analyticDrawerTableColHeaderLocator = By.cssSelector("div.headlines > div > span");
	static By analyticJobDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div");
	static By analyticJobDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div > span");
	static By analyticScheduleDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div.ng-binding");
	static By analyticScheduleDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div > span");
	static By analyticDrawerViewResultsLinkLocator = By.cssSelector("div.margin_top > div > div > a > span");
	static By searchBoxLocator = By.cssSelector("div.multi-name-search > input.search-query.search-input");
	static By searchBoxIconLocator = By.cssSelector("div.multi-name-search > div.icon-search");
	static By searchItemLocator = By.cssSelector("li.search-item-row > button.search-item");
	static By searchCancelFilterIconLocator = By.cssSelector("li.search-item-row > i.icon-remove");
	static By rightPanelTitleLocator = By.className("right-panel-block-title");
	static By rightPanelIconLocator = By.className("right-panel-block-icon");
	static By descriptionLocator = By.className("right-panel-block-content");
	static By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
	static By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
	static By closeIconLocator = By.cssSelector("div.title-row > i.icon_remove");
	//end
	   
		public TestDetailsPage(WebDriver driver) {
			this.driver = driver;
		}
		
		public String getProjectHeader(){
			return driver.findElement(projectHeaderLocator).getText();
		}
		public TestDetailsPage getProjectName(){
			System.out.println("ProjectName: "+driver.findElement(projectNameLocator).getText());
			return this;
		}
		public TestDetailsPage getProjectsListFromDropDown(){
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
		public TestDetailsPage getTestSetName(){
			System.out.println("TestSetName: "+driver.findElement(testSetNameLocator).getText());
			return this;
		}
		public TestDetailsPage getTestSetsListFromDropDown(){
			driver.findElement(testSetDropDownLocator).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dropDownMenu = driver.findElements(testSetDropDownMenuItemsLocator);
			for(int i=0;i<dropDownMenu.size();i++){
				dropDownMenuListTestSets.add(dropDownMenu.get(i).getText());
			}
			System.out.println("TestSets List from DropDown: ");
			for(String testSets:dropDownMenuListTestSets){
				System.out.println(testSets);
			}
			driver.findElement(testSetDropDownLocator).click();
			return this;
		}
		public TestDetailsPage getTestName(){
			System.out.println("TestName: "+driver.findElement(testNameLocator).getText());
			return this;
		}
		public TestDetailsPage getTestsListFromDropDown(){
			driver.findElement(testDropDownLocator).click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dropDownMenu = driver.findElements(testDropDownMenuItemsLocator);
			for(int i=0;i<dropDownMenu.size();i++){
				dropDownMenuListTests.add(dropDownMenu.get(i).getText());
			}
			System.out.println("Tests List from DropDown: ");
			for(String tests:dropDownMenuListTests){
				System.out.println(tests);
			}
			driver.findElement(testSetDropDownLocator).click();
			return this;
		}
		public TestDetailsPage getTestDesc(){
			System.out.println("TestDesc: "+driver.findElements(descriptionLocator).get(0).getText());
			return this;
		}
		public TestDetailsPage getTestDescLabel() {
			System.out.println("TestDescLabel: "+driver.findElements(rightPanelTitleLocator).get(0).getText());
			return this;
		}
		public TestDetailsPage getCreatedBy(){
			
			System.out.println("Created By: "+driver.findElements(infoContentDataLocator).get(0).getText());
			return this;
		}
		public TestDetailsPage getCreatedAt(){
			System.out.println("Created At: "+driver.findElements(infoContentDataLocator).get(1).getText());
			return this;
		}
		public TestDetailsPage getModifiedBy(){
			System.out.println("Modified By: "+driver.findElements(infoContentDataLocator ).get(2).getText());
			return this;
		}
		public TestDetailsPage getModifiedAt(){
			System.out.println("Modified At: "+driver.findElements(infoContentDataLocator ).get(3).getText());
			return this;
		}
		public TestDetailsPage getCreatedByLabel(){
			System.out.println("Created By Label: "+driver.findElements(infoContentLabelLocator).get(0).getText());
			return this;
		}
		public TestDetailsPage getCreatedAtLabel(){
			System.out.println("Created At Label: "+driver.findElements(infoContentLabelLocator).get(1).getText());
			return this;
		}
		public TestDetailsPage getModifiedByLabel(){
			System.out.println("Modified By Label: "+driver.findElements(infoContentLabelLocator ).get(2).getText());
			return this;
		}
		public TestDetailsPage getModifiedAtLabel(){
			System.out.println("Modified At Label: "+driver.findElements(infoContentLabelLocator ).get(3).getText());
			return this;
		}
		public TestDetailsPage getInfoLabel() {
			System.out.println("Info Label: "+driver.findElements(rightPanelTitleLocator).get(1).getText());
	        return this;
	    }
		
		public TestDetailsPage getAnalyticsList(){
			analytics = driver.findElements(analyticNameLocator);
			for(int i=0;i<analytics.size();i++){
				analyticsList.add(analytics.get(i).getText());
			}
			System.out.println("Analytics List: ");
			for(String singleAnalytic : analyticsList){
				System.out.println(singleAnalytic);
			}	
			return this;
		}
		
		public int getAnalyticIndex(){
			analytics = driver.findElements(analyticNameLocator);
			for(int i=0;i<analytics.size();i++){
				if(analytics.get(i).getText().equalsIgnoreCase(dpAnalyticName)){
					analyticIndex = i+1;
					return analyticIndex;
				}
			}
			System.out.println(dpAnalyticName+" not found!!");	
			return 0;
		}
		
		public String analyticWithOpenRunDrawer(){
			analyticIndex = getAnalyticIndex();
			if(driver.findElements(analyticRunIconLocator).get(analyticIndex-1).getAttribute("class").contains("active")){
				openDrawerName = "run";
			    return driver.findElement(analyticNameOfOpenDrawerLocator).getText();
			}
		    return "";
		}
		
		public String analyticWithOpenJobsDrawer(){
			analyticIndex = getAnalyticIndex();
			if(driver.findElements(analyticJobsIconLocator).get(analyticIndex-1).getAttribute("class").contains("active")){
				openDrawerName = "jobs";
			    return driver.findElement(analyticNameOfOpenDrawerLocator).getText();
			}
		    return "";
		}
		
		public String analyticWithOpenScheduleDrawer(){
			analyticIndex = getAnalyticIndex();
			if(driver.findElements(analyticScheduleIconLocator).get(analyticIndex-1).getAttribute("class").contains("active")){
				openDrawerName = "schedules";
			    return driver.findElement(analyticNameOfOpenDrawerLocator).getText();
			}
		    return "";
		}
		
		public TestDetailsPage openRunDrawer(){
			if(!analyticWithOpenRunDrawer().equalsIgnoreCase("")){
				System.out.println("Run Drawer already open for analytic "+analyticWithOpenRunDrawer());
				return this;
			}else if(getAnalyticIndex()>0){
				driver.findElements(analyticRunIconLocator).get(getAnalyticIndex()-1).click();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return this;
		}
		public TestDetailsPage openJobsDrawer(){
			if(!analyticWithOpenJobsDrawer().equalsIgnoreCase("")){
				System.out.println("Jobs Drawer already open for analytic "+analyticWithOpenRunDrawer());
				return this;
			}else if(getAnalyticIndex()>0){
				driver.findElements(analyticJobsIconLocator).get(getAnalyticIndex()-1).click();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return this;
		}
		public TestDetailsPage openScheduleDrawer(){
			if(!analyticWithOpenScheduleDrawer().equalsIgnoreCase("")){
				System.out.println("Schedule Drawer already open for analytic "+analyticWithOpenRunDrawer());
				return this;
			}else if(getAnalyticIndex()>0){
				driver.findElements(analyticScheduleIconLocator).get(getAnalyticIndex()-1).click();
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return this;
		}
		public TestDetailsPage clickDrawerContinueBtn(){
			driver.findElement(analyticDrawerBtnLocator).click();
			return this;
		}
		public TestDetailsPage readJobDrawerHeader(){
			driver.findElement(analyticJobDrawerHeaderLocator).getText();
			return this;
		}
		public TestDetailsPage readScheduleDrawerHeader(){
			driver.findElement(analyticScheduleDrawerHeaderLocator).getText();
			return this;
		}
		public TestDetailsPage readDrawerData(){
			if(analyticWithOpenJobsDrawer().equalsIgnoreCase(dpAnalyticName)){
				System.out.println("Jobs History of analytic "+dpAnalyticName+":");
			}else if(analyticWithOpenScheduleDrawer().equalsIgnoreCase(dpAnalyticName)){
				System.out.println("Schedules of analytic "+dpAnalyticName+":");
			}else{
				System.out.println("Neither Jobs nor Schedules Drawer open to read data");
				return this;
			}
			analyticIndex = getAnalyticIndex();
			//drawerDataHeader = driver.findElements(analyticDrawerTableColHeaderLocator);
			drawerDataHeader = driver.findElements(analyticJobDrawerTableTextValuesLocator);
			for(int j=0;j<drawerDataHeader.size();j++){
				drawerDataHeaderList.add(drawerDataHeader.get(j).getText());
			}
			/*
			 * for(int j=analyticIndex*5;j<analyticIndex*5+5;j++){
				drawerDataHeaderList.add(drawerDataHeader.get(j).getText());
			}
			
			if(openDrawerName.equalsIgnoreCase("jobs")){
				for(int i=0;i<drawerDataHeader.size()/2;i++){
					for(int j=0;j<drawerDataHeader.size()/2;j++){
						drawerDataHeaderList.add(drawerDataHeader.get(j).getText()+": "+driver.findElements(analyticJobDrawerTableTextValuesLocator).get(i).getText());
					}
					drawerDataHeaderList.add(drawerDataHeader.get(i).getText()+": ");
				}
			}else if(openDrawerName.equalsIgnoreCase("schedules")){
				for(int i=drawerDataHeader.size()/2;i<drawerDataHeader.size();i++){
					drawerDataHeaderList.add(drawerDataHeader.get(i).getText());
				}
			}*/
			System.out.println("drawerDataHeader List: ");
			for(String singleHeader : drawerDataHeaderList){
				System.out.println(singleHeader);
			}	
			
			return this;
		}
		public ResultsPage clickViewResults(){
			driver.findElement(analyticDrawerViewResultsLinkLocator).click();
			return new ResultsPage(driver);
		}
		
		public TestDetailsPage filterAnalyticsList(){
			for(int i=0;i<dpSearchItems.length;i++){
				driver.findElement(searchBoxLocator).click();
				driver.findElement(searchBoxLocator).sendKeys(dpSearchItems[i]);
				driver.findElement(searchBoxIconLocator).click();
			}	
			return this;
		}
		
		public TestDetailsPage getSearchItemsList(){
			searchItems = driver.findElements(searchItemLocator);
			System.out.println("All Filtered items List: ");
			for(int i=0;i<searchItems.size();i++){
				System.out.println(searchItems.get(i).getText());
			}
			return this;
		}
		
		public TestDetailsPage clearFilter(){
			searchItems = driver.findElements(searchCancelFilterIconLocator);
			for(int i=0;i<searchItems.size();i++){
				searchItems.get(i).click();
			}
			return this;
		}
		
		public TestSetDetailsPage closeTestDetailsLayer(){
			driver.findElement(closeIconLocator).click();
			return new TestSetDetailsPage(driver);
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
		public boolean isTestSetNamePresent() {
	        return driver.findElement(testSetNameLocator).isDisplayed(); 
	    }
		public boolean isTestSetDropDownPresent() {
	        return driver.findElement(testSetDropDownLocator).isEnabled(); 
	    }
		public boolean isTestNamePresent() {
	        return driver.findElement(testNameLocator).isDisplayed(); 
	    }
		public boolean isTestDropDownPresent() {
	        return driver.findElement(testDropDownLocator).isEnabled(); 
	    }
		public boolean isSearchBoxPresent() {
	        return driver.findElement(searchBoxLocator).isEnabled(); 
	    }
		public boolean isSearchBoxIconPresent() {
	        return driver.findElement(searchBoxIconLocator).isDisplayed(); 
	    }
		public boolean isAnalayticsHeaderPresent() {
	        return driver.findElement(analyticsHeaderLocator).isDisplayed(); 
	    }
		public boolean isAnalayticNamePresent() {
	        return driver.findElement(analyticNameLocator).isDisplayed(); 
	    }
		public boolean isAnalayticRunIconPresent() {
	        return driver.findElement(analyticRunIconLocator).isEnabled(); 
	    }
		public boolean isAnalayticJobsIconPresent() {
	        return driver.findElement(analyticJobsIconLocator).isEnabled(); 
	    }
		public boolean isAnalayticJobDrawerHeaderPresent() {
	        return driver.findElement(analyticJobDrawerHeaderLocator).isDisplayed(); 
	    }
		public boolean isAnalayticScheduleIconPresent() {
	        return driver.findElement(analyticScheduleIconLocator).isEnabled(); 
	    }
		public boolean isAnalayticScheduleDrawerHeaderPresent() {
	        return driver.findElement(analyticScheduleDrawerHeaderLocator).isDisplayed(); 
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
		public boolean isCloseIconPresent() {
	        return driver.findElement(closeIconLocator).isEnabled(); 
	    }
		

}
