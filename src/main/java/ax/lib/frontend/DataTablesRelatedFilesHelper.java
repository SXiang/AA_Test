package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DataTablesRelatedFilesHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>TestSetDetailsHelper</b>
	 * Generated     : <b>Oct 1, 2013</b>
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
	By projectDropDownLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-dropdown > a");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer2 > div.dropdown > div > ul.dropdown-menu > li > a");
	By testSetDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a");
	By testSetDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.dropdown > div > ul.dropdown-menu > li > a");
	By listHeaderLocator = By.cssSelector("div.title-row > span");
	By listColHeaderLocator = By.cssSelector("div.header-row > div > span");
	By tableNameLocator = By.cssSelector("div.table-row > div > div:nth-child(1)");
	By tableSizeLocator = By.cssSelector("div.table-row > div > div:nth-child(2)");
	By tableRecordsLocator = By.cssSelector("div.table-row > div > div:nth-child(3)");
	By tableModDateLocator = By.cssSelector("");
	By descIconLocator = By.cssSelector("div.action-buttons > div.dropdown > a");
	By descHeaderLocator = By.cssSelector("div.description-content > h5 > span");
	By descLocator = By.cssSelector("div.description-content > span");
	By fileNameLocator = By.cssSelector("div.file-row > div > div:nth-child(1)");
	By fileSizeLocator = By.cssSelector("div.file-row > div > div:nth-child(2)");
	By fileRecordsLocator = By.cssSelector("div.file-row > div > div:nth-child(3)");
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> dropDownMenu;
	protected String dropDownMenuList;
	protected List<WebElement> columnHeaders;
	protected List<WebElement> tableName;
	protected List<WebElement> tableSize;
	protected List<WebElement> tableRecords;
	protected List<WebElement> descIcon;
	protected String tablesList;
	protected String desc;
	protected List<WebElement> fileName;
	protected List<WebElement> fileSize;
	protected String filesList;
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
		isElementDisplayed(listHeaderLocator, "List header");
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
	
	public String getTablesList(){
		takeScreenshot();
		columnHeaders = driver.findElements(listColHeaderLocator);
		tableName = driver.findElements(tableNameLocator);
		if(tableName.size()>0){
			tableSize = driver.findElements(tableSizeLocator);
			tableRecords = driver.findElements(tableRecordsLocator);
		}else{
			logTAFInfo("No tables found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText()+"|"+columnHeaders.get(2).getText()+"|"+columnHeaders.get(3).getText();
		}
		for(int i = 0; i < tableName.size(); i++) {
	        	if(i==0){
	        		tablesList=columnHeaders.get(0).getText()+":"+tableName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+tableSize.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+tableRecords.get(i).getText();
	        	}else{
	        		tablesList=tablesList+"\r\n"+columnHeaders.get(0).getText()+":"+tableName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+tableSize.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+tableRecords.get(i).getText();
	        	}
	        }
	        return tablesList;
	}
	
	public String getTableDesc( String table){
		tableName = driver.findElements(tableNameLocator);
		if(tableName.size()>0){
			descIcon = driver.findElements(descIconLocator);
		}else{
			logTAFError("No tables found");
			return "";
		}
		for(int i = 0; i < tableName.size(); i++) {
	        	if(tableName.get(i).getText().equalsIgnoreCase(table)){
	        		logTAFStep("Table: "+table+" found and clicked on its Description icon.");
	        		descIcon.get(i).click();
	        		sleep(timerConf.waitToTakeScreenshot);
	        		captureScreen(getScreenshotPathAndName());
	        		logTAFInfo("Screenshot taken");
	        		desc = driver.findElements(descHeaderLocator).get(i).getText()+":"+driver.findElements(descLocator).get(i).getText();
	        		descIcon.get(i).click();
	        		return desc;
	        	}
		}
	    return "";
	}
	
	public String getFileDesc( String file){
		fileName = driver.findElements(fileNameLocator);
		if(fileName.size()>0){
			descIcon = driver.findElements(descIconLocator);
		}else{
			logTAFError("No files found");
			return "";
		}
		for(int i = 0; i < fileName.size(); i++) {
	        	if(fileName.get(i).getText().equalsIgnoreCase(file)){
	        		logTAFStep("File: "+file+" found and clicked on its Description icon.");
	        		descIcon.get(i).click();
	        		sleep(timerConf.waitToTakeScreenshot);
	        		captureScreen(getScreenshotPathAndName());
	        		logTAFInfo("Screenshot taken");
	        		desc = driver.findElements(descHeaderLocator).get(i).getText()+":"+driver.findElements(descLocator).get(i).getText();
	        		descIcon.get(i).click();
	        		return desc;
	        	}
		}
	    return "";
	}

	public String getFilesList(){
		takeScreenshot();
		columnHeaders = driver.findElements(listColHeaderLocator);
		fileName = driver.findElements(fileNameLocator);
		if(fileName.size()>0){
			fileSize = driver.findElements(fileSizeLocator);
		}else{
			logTAFInfo("No files found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText();
		}
		for(int i = 0; i < fileName.size(); i++) {
	        	if(i==0){
	        		filesList=columnHeaders.get(0).getText()+":"+fileName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+fileSize.get(i).getText();
	        	}else{
	        		filesList=filesList+"\r\n"+columnHeaders.get(0).getText()+":"+fileName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+fileSize.get(i).getText();
	        	}
	        }
	        return filesList;
	}
	public String getProjectHeader(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectHeaderLocator));
		return driver.findElement(projectHeaderLocator).getText();
	}
	public String getProjectName(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectNameLocator));
		return driver.findElement(projectNameLocator).getText();
	}
	
	public String getProjectsListFromDropDown(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownLocator));
		driver.findElement(projectDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(projectDropDownMenuItemsLocator));
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
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
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetNameLocator));
		return driver.findElement(testSetNameLocator).getText();
	}
	
	public String getTestSetsListFromDropDown(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownLocator));
		driver.findElement(testSetDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testSetDropDownMenuItemsLocator));
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
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
	
	public String getListHeader(){
		return driver.findElement(listHeaderLocator).getText();
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
	
	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	


}
