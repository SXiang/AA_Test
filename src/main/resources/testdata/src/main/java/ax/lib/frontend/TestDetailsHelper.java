package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class TestDetailsHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>TestSetDetailsHelper</b>
	 * Generated     : <b>Sep 11, 2013</b>
	 * Description   : TestSetDetailsHelper
	 * 
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of ProjectsList page
	By projectHeaderLocator = By.cssSelector("div.project-header > a > span");
	By projectNameLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-title > span");
	By testSetNameLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-title > span");
	By testNameLocator = By.cssSelector("div.title > span");
	By projectDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.dropdown > div > ul.dropdown-menu > li > a");
	By testSetDropDownLocator = By.cssSelector("div.title-row > div.dropdown > a");
	By testSetDropDownMenuItemsLocator = By.cssSelector("div.title-row > div.dropdown > div > ul.dropdown-menu > li > a");
	By testDropDownLocator = By.cssSelector("div.title-row > div.dropdown > a");
	By testDropDownMenuItemsLocator = By.cssSelector("div.title-row > div.dropdown > div > ul.dropdown-menu > li > a");
	By analyticsHeaderLocator = By.cssSelector("div.analytic-script-subtitle > span");
	By analyticNameLocator = By.cssSelector("div.script-row > div.row-fluid > div.ng-binding");
	By analyticNameOfOpenDrawerLocator = By.cssSelector("div.drawer-opened > div > div.ng-binding");
	By analyticRunIconLocator = By.cssSelector("div.script-row > div > i");
	By analyticDrawerBtnLocator = By.cssSelector("div.drawer > a.action-btn");
	By analyticJobsIconLocator = By.cssSelector("div.script-row > div > div > i.icon_list");
	By analyticScheduleIconLocator = By.cssSelector("div.script-row > div > div > i.icon_comment");
	By analyticJobDrawerHeaderLocator = By.cssSelector("div.drawer > div > h3 > span");
	By analyticScheduleDrawerHeaderLocator = By.cssSelector("div.drawer > div > div.strong > span");
	By analyticDrawerTableColHeaderLocator = By.cssSelector("div.headlines > div > span");
	By analyticJobDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div");
	By analyticJobDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div > span");
	By analyticScheduleDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div.ng-binding");
	By analyticScheduleDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div > span");
	By analyticDrawerViewResultsLinkLocator = By.cssSelector("div.margin_top > div > div > a > span");
	By rightPanelTitleLocator = By.className("right-panel-block-title");
	By rightPanelIconLocator = By.className("right-panel-block-icon");
	By descriptionLocator = By.className("right-panel-block-content");
	By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
	By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
	By usersPopupHeaderLocator = By.className("modal-title");
	By usersListLocator = By.cssSelector("div.modal-body > ul.user-list > li.user-row");
	By usersPopupCloseIconLocator = By.cssSelector("div.modal-header > div.icon-remove");
	
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> rightPanelData;
	protected List<WebElement> dropDownMenu;
	protected String dropDownMenuList;
	protected List<WebElement> users;
	protected String usersList;
	protected List<WebElement> tests;
	protected String testsList;
	protected int index;
	protected String rightPanelLabels;
	protected String infoPanelContent;
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
		isElementDisplayed(projectHeaderLocator, "Project header");
		isElementEnabled(searchBoxLocator, "Search box");
		isElementDisplayed(searchBoxIconLocator, "Search box lens icon");
		isElementDisplayed(copyrightFooter, "Copyright footer");
		isElementDisplayed(projectNameLocator, "Project name");
		isElementDisplayed(projectDropDownLocator, "Project drop down button");
		isElementDisplayed(testSetDropDownLocator, "TestSet Drop down button");
		isElementDisplayed(testsHeaderLocator, "Test list header");
		isRightPanelIconDisplayed();
		isElementDisplayed(closeIconLocator, "Close layer icon");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void isRightPanelIconDisplayed(){
		rightPanelData = driver.findElements(rightPanelIconLocator);
		if(rightPanelData.get(0).isDisplayed()){
        	logTAFInfo("Found DataTables Icon");
        } else{
        	logTAFError("DataTables Icon missing");
        }
        if(rightPanelData.get(1).isDisplayed()){
        	logTAFInfo("Found RelatedFiles Icon");
        } else{
        	logTAFError("Info RelatedFiles missing");
        }
        if(rightPanelData.get(2).isDisplayed()){
        	logTAFInfo("Found Description Icon");
        } else{
        	logTAFError("Description Icon missing");
        }
        if(rightPanelData.get(3).isDisplayed()){
        	logTAFInfo("Found Info Icon");
        } else{
        	logTAFError("Info Icon missing");
        }
        if(rightPanelData.get(4).isDisplayed()){
        	logTAFInfo("Found Users Icon");
        } else{
        	logTAFError("Users Icon missing");
        }
	}

	public String getDescription(){
		return driver.findElements(rightPanelTitleLocator).get(2).getText()+":"+driver.findElement(descriptionLocator).getText();
	}
	
	public String getInfo(){
		for(int i = 0; i < 4; i++) {
        	if(i==0){
        		infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}else{
        		infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}
        }
		return infoPanelContent;
	}
	
	public String getDataTablesLabel(){
		return driver.findElements(rightPanelTitleLocator).get(0).getText();
	}
	
	public String getRelatedFilesLabel(){
		return driver.findElements(rightPanelTitleLocator).get(1).getText();
	}
	
	public String getProjectHeader(){
		return driver.findElement(projectHeaderLocator).getText();
	}
	public String getProjectName(){
		return driver.findElement(projectNameLocator).getText();
	}
	
	public String getProjectsListFromDropDown(){
		driver.findElement(projectDropDownLocator).click();
		sleep(5);
		dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(i==0){
        		dropDownMenuList=dropDownMenu.get(i).getText();
        	}else{
        		dropDownMenuList=dropDownMenuList+"|"+dropDownMenu.get(i).getText();
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return dropDownMenuList;
	}
	
	public String getTestSetName(){
		return driver.findElement(testSetNameLocator).getText();
	}
	
	public String getTestSetsListFromDropDown(){
		driver.findElement(testSetDropDownLocator).click();
		sleep(5);
		dropDownMenu = driver.findElements(testSetDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(i==0){
        		dropDownMenuList=dropDownMenu.get(i).getText();
        	}else{
        		dropDownMenuList=dropDownMenuList+"|"+dropDownMenu.get(i).getText();
        	}
        }
		driver.findElement(testSetDropDownLocator).click();
		return dropDownMenuList;
	}
	
	public String getTestHeader(){
		return driver.findElement(testsHeaderLocator).getText();
	}
	
	public String getUsersPanelTitle() {
		return driver.findElements(rightPanelTitleLocator).get(4).getText();
    }
	public void getUsersPopup(){
		driver.findElements(rightPanelTitleLocator).get(4).click();
	}
	public String getUsersPopupHeader(){
		return driver.findElement(usersPopupHeaderLocator).getText();
	}
	public String getUsersList(){
		users = driver.findElements(usersListLocator);
		sleep(5);
        for(int i = 1; i < users.size(); i++) {
        	if(i==1){
        		usersList=users.get(i).getText();
        	}else if(i>1){
        		usersList=usersList+"|"+users.get(i).getText();
        	}
        }
		return usersList;
	}
	public void closeUsersPopup(){
		driver.findElement(usersPopupCloseIconLocator).click();
	}
	
	public String getTestsList(){
		tests = driver.findElements(testsNameLocator);
        for(int i = 0; i < tests.size(); i++) {
        	if(i==0){
        		testsList=tests.get(i).getText();
        	}else{
        		testsList=testsList+"|"+tests.get(i).getText();
        	}
        }
		return testsList;
	}
	
	public boolean clickTestName(String testName) {
		tests = driver.findElements(testsNameLocator);
		for(int i = 0; i < tests.size(); i++) {
        	if(tests.get(i).getText().equals(testName)){
        		logTAFStep("Test: "+testName+" found and clicked on!!!");
        		tests.get(i).click();
        		return true;
        	}
        }
		logTAFError("Test: "+testName+" not found!!");
		return false;
	}	
	
	public Boolean clickTestSetNameFromDropDown(String testSetName){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		driver.findElement(testSetDropDownLocator).click();
		sleep(5);
		dropDownMenu = driver.findElements(testSetDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(testSetName)){
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return false;
	}
	
	public Boolean clickProjectNameFromDropDown(String projectName){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		driver.findElement(projectDropDownLocator).click();
		sleep(5);
		dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(projectName)){
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return false;
	}

}