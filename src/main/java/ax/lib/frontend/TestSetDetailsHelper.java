package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSetDetailsHelper extends FrontendCommonHelper{
	
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
	By projectNameLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-title > span");
	By testSetNameLocator = By.cssSelector("div.title > span");
	By projectDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.dropdown > div > ul.dropdown-menu > li > a");
	By testSetDropDownLocator = By.cssSelector("div.title-row > div.dropdown > a");
	By testSetDropDownMenuItemsLocator = By.cssSelector("div.title-row > div.dropdown > div > ul.dropdown-menu > li > a");
	By testsHeaderLocator = By.cssSelector("div.test-subtitle > span");
	By testsNameLocator = By.cssSelector("div.test-row > div.row-fluid > a");
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
		isElementDisplayed(testSetNameLocator, "TestSet name");
		isElementDisplayed(testSetDropDownLocator, "TestSet Drop down button");
		isElementDisplayed(closeIconLocator, "Close layer icon");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void isRightPanelIconDisplayed(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelIconLocator));
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
	
	public void isTestsHeaderDisplayed(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testsHeaderLocator));
		if(isElementDisplayed(testsHeaderLocator, "Tests list Header")){
        	logTAFInfo("Found Tests list Header");
        } else{
        	logTAFError("Tests list Header missing");
        }
	}

	public String getDescription(){
		return driver.findElements(rightPanelTitleLocator).get(2).getText()+":"+driver.findElement(descriptionLocator).getText();
	}
	
	public String getInfo(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(infoContentLabelLocator));
		for(int i = 0; i < 4; i++) {
        	if(i==0){
        		//infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        		infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText();
        	}else{
        		infoPanelContent=infoPanelContent+"\r\n"+driver.findElements(infoContentLabelLocator).get(i).getText();
        		//infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}
        }
		return infoPanelContent;
	}
	
	public String getDataTablesLabel(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		return driver.findElements(rightPanelTitleLocator).get(0).getText();
	}
	
	public String getRelatedFilesLabel(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		return driver.findElements(rightPanelTitleLocator).get(1).getText();
	}
	
	public void clickDataTablesLink(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		driver.findElements(rightPanelTitleLocator).get(0).click();
	}
	
	public void clickRelatedFilesLink(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		driver.findElements(rightPanelTitleLocator).get(1).click();
	}
	
	
	public String getProjectHeader(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectHeaderLocator));
		return driver.findElement(projectHeaderLocator).getText();
	}
	public String getProjectName(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectNameLocator));
		return driver.findElement(projectNameLocator).getText();
	}
	
	public String getProjectsListFromDropDown(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownLocator));
		driver.findElement(projectDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownMenuItemsLocator));
		takeScreenshotWithoutScroll();
		dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(i==0){
        		dropDownMenuList=dropDownMenu.get(i).getText();
        	}else{
        		dropDownMenuList=dropDownMenuList+"\r\n"+dropDownMenu.get(i).getText();
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return dropDownMenuList;
	}
	
	public String getTestSetName(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetNameLocator));
		return driver.findElement(testSetNameLocator).getText();
	}
	
	public String getTestSetsListFromDropDown(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownLocator));
		driver.findElement(testSetDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownMenuItemsLocator));
		takeScreenshotWithoutScroll();
		dropDownMenu = driver.findElements(testSetDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(i==0){
        		dropDownMenuList=dropDownMenu.get(i).getText();
        	}else{
        		dropDownMenuList=dropDownMenuList+"\r\n"+dropDownMenu.get(i).getText();
        	}
        }
		driver.findElement(testSetDropDownLocator).click();
		return dropDownMenuList;
	}
	
	public String getTestHeader(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testsHeaderLocator));
		return driver.findElement(testsHeaderLocator).getText();
	}
	
	public String getUsersPanelTitle() {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		return driver.findElements(rightPanelTitleLocator).get(4).getText();
    }
	public void getUsersPopup(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		driver.findElements(rightPanelTitleLocator).get(4).click();
	}
	public String getUsersPopupHeader(){
		sleep(timerConf.itemClickTime);
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(usersPopupHeaderLocator));
		return driver.findElement(usersPopupHeaderLocator).getText();
	}
	public String getUsersList(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(usersListLocator));
		takeScreenshotWithoutScroll();
		users = driver.findElements(usersListLocator);
        for(int i = 0; i < users.size(); i++) {
        	if(i==0){
        		usersList=users.get(i).getText();
        	}else if(i>0){
        		usersList=usersList+"\r\n"+users.get(i).getText();
        	}
        }
		return usersList;
	}
	public void closeUsersPopup(){
		driver.findElement(usersPopupCloseIconLocator).click();
	}
	
	public String getTestsList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		takeScreenshot();
		tests = driver.findElements(testsNameLocator);
		if(tests.size()>0){
			for(int i = 0; i < tests.size(); i++) {
	        	if(i==0){
	        		testsList=tests.get(i).getText();
	        	}else{
	        		testsList=testsList+"\r\n"+tests.get(i).getText();
	        	}
	        }
		}else{
			testsList = "";
			logTAFError("No Test Available.");
		}
		return testsList;
	}
	
	public boolean clickTestName(String testName) {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testsNameLocator));
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
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownLocator));
		driver.findElement(testSetDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownMenuItemsLocator));
		dropDownMenu = driver.findElements(testSetDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(testSetName)){
        		logTAFStep("TestSet: "+testSetName+" found and clicked on.");
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(testSetDropDownLocator).click();
		return false;
	}
	
	public Boolean clickProjectNameFromDropDown(String projectName){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownLocator));
		driver.findElement(projectDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownMenuItemsLocator));
		dropDownMenu = driver.findElements(projectDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(projectName)){
        		logTAFStep("Project: "+projectName+" found and clicked on.");
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return false;
	}

}
