package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProjectDetailsHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>ProjectDetailsHelper</b>
	 * Generated     : <b>Sep 6, 2013</b>
	 * Description   : ProjectDetailsHelper
	 * 
	 * @since  Sep 6, 2013
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of ProjectsList page
	By projectHeaderLocator = By.cssSelector("div.project-header > a > span");
	By projectNameLocator = By.cssSelector("div.title > span");
	By projectDropDownLocator = By.cssSelector("div.dropdown > a.dropdown-toggle > i");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.dropdown > ul.dropdown-menu > li.ng-scope > a");
	By testSetsHeaderLocator = By.cssSelector("div.testset-subtitle > span");
	By testSetsNameLocator = By.cssSelector("div.testset-row > div.row-fluid");
	By rightPanelTitleLocator = By.className("right-panel-block-title");
	//By rightPanelIconLocator = By.className("right-panel-block-icon");
	By descriptionLocator = By.className("right-panel-block-content");
	By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
	By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
	By usersPopupHeaderLocator = By.xpath("html/body/div[4]/div[1]/div[1]");
	By usersListLocator = By.cssSelector("div.modal-body > ul.user-list > li.user-row");
	By usersPopupCloseIconLocator = By.cssSelector("div.modal-header > div.icon-remove");
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> rightPanelData;
	protected List<WebElement> dropDownMenu;
	protected String dropDownMenuList;
	protected List<WebElement> users;
	protected String usersList;
	protected List<WebElement> testSets;
	protected String testSetsList;
	protected int index;
	protected String rightPanelLabels;
	protected String infoPanelContent;
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		//getSharedObj();
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
		isElementDisplayed(testSetsHeaderLocator, "TestSets list header");
		//isRightPanelIconDisplayed();
		isElementDisplayed(closeIconLocator, "Close layer icon");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
//	public void isRightPanelIconDisplayed(){
//		rightPanelData = driver.findElements(rightPanelIconLocator);
//        if(rightPanelData.get(0).isDisplayed()){
//        	logTAFInfo("Found Description Icon");
//        } else{
//        	logTAFError("Description Icon missing");
//        }
//        if(rightPanelData.get(1).isDisplayed()){
//        	logTAFInfo("Found Info Icon");
//        } else{
//        	logTAFError("Info Icon missing");
//        }
//        if(rightPanelData.get(2).isDisplayed()){
//        	logTAFInfo("Found Users Icon");
//        } else{
//        	logTAFError("Users Icon missing");
//        }
//	}

	public String getDescription(){
		return "@"+driver.findElements(rightPanelTitleLocator).get(0).getText()+"@"+":"+driver.findElement(descriptionLocator).getText();
	}
	
	public String getInfo(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(infoContentLabelLocator));
		for(int i = 0; i < 4; i++) {
        	if(i==0){
        		infoPanelContent="@"+driver.findElements(infoContentLabelLocator).get(i).getText()+"@"+":"+"#"+driver.findElements(infoContentDataLocator).get(i).getText()+"#";
        		//infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}else{
        		infoPanelContent=infoPanelContent+"\r\n"+"@"+driver.findElements(infoContentLabelLocator).get(i).getText()+"@"+":"+"#"+driver.findElements(infoContentDataLocator).get(i).getText()+"#";
        		//infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}
        }
		return infoPanelContent;
	}
	
	public String getProjectHeader(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectHeaderLocator));
		return "@"+driver.findElement(projectHeaderLocator).getText()+"@";
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
	
	public String getTestSetHeader(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetsHeaderLocator));
		return "@"+driver.findElement(testSetsHeaderLocator).getText()+"@";
	}
	
	public String getUsersPanelTitle() {
		return driver.findElements(rightPanelTitleLocator).get(2).getText();
    }
	public void getUsersPopup(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(rightPanelTitleLocator));
		driver.findElements(rightPanelTitleLocator).get(2).click();
	}
	public String getUsersPopupHeader(){
		sleep(timerConf.itemClickTime);
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(usersPopupHeaderLocator));
		String[] usersHeader  = driver.findElement(usersPopupHeaderLocator).getText().split(" - ");
		return "@"+usersHeader[0]+"@ - "+usersHeader[1];
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
	
	public String getTestSetsList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		takeScreenshot();
		testSets = driver.findElements(testSetsNameLocator);
		if(testSets.size()>0){
			for(int i = 0; i < testSets.size(); i++) {
	        	if(i==0){
	        		testSetsList=testSets.get(i).getText();
	        	}else{
	        		testSetsList=testSetsList+"\r\n"+testSets.get(i).getText();
	        	}
	        }
		}else{
			testSetsList = "";
			logTAFError("No Test Set Available.");
		}
		return testSetsList;
	}
	
	public boolean clickTestSetName(String testSetName) {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetsNameLocator));
		testSets = driver.findElements(testSetsNameLocator);
		for(int i = 0; i < testSets.size(); i++) {
        	if(testSets.get(i).getText().equals(testSetName)){
        		logTAFStep("TestSet: "+testSetName+" found and clicked on!!!");
        		testSets.get(i).click();
        		return true;
        	}
        }
		logTAFError("TestSet: "+testSetName+" not found!!");
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
