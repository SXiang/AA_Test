package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultsHelper extends FrontendCommonHelper{
	
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
	By projectNameLocator = By.cssSelector("div.sub-layer3 > div.sub-layer-title > span");
	By testSetNameLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-title > span");
	By testNameLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-title > span");
	By projectDropDownLocator = By.cssSelector("div.sub-layer3 > div.sub-layer-dropdown > a > i");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer3 > div.dropdown > ul.dropdown-menu > li > a");
	By testSetDropDownLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-dropdown > a > i");
	By testSetDropDownMenuItemsLocator = By.cssSelector("div.sub-layer2 > div.dropdown > ul.dropdown-menu > li > a");
	By testDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a > i");
	By testDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.dropdown > ul.dropdown-menu > li > a");
	By listHeaderLocator = By.cssSelector("div.title-row > h1 > span:nth-child(1)");
	By listColHeaderLocator = By.cssSelector("div.header-row > div > span");
	By resultSelectAllCheckboxLocator = By.cssSelector("div.left-panel > div > div > span > i.icon_check");
	By resultCheckboxLocator = By.cssSelector("div.result-row > div > div > i.icon_check");
	By resultNameLocator = By.cssSelector("div.result-row > div > div > span:nth-child(3)");
	By resultSizeLocator = By.cssSelector("div.result-row > div > div > span:nth-child(1)");
	By resultTypeLocator = By.cssSelector("div.result-row > div > div:nth-child(2)");
	By resultRecordsLocator = By.cssSelector("div.result-row > div > div:nth-child(3)");
	By tableDownloadIconLocator = By.cssSelector("div.result-row > div > div > a > i.icon_download");
	By rightPanelTitleLocator = By.className("right-panel-block-title");
	By rightPanelIconLocator = By.className("right-panel-block-icon");
	By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
	By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
	By paramLabelLocator = By.cssSelector("div.right-panel-block-content > dl[ng-repeat*='parameterSet'] > dt");
	By paramValueLocator = By.cssSelector("div.right-panel-block-content > dl[ng-repeat*='parameterSet'] > dd");
	By paramMsgLocator = By.cssSelector("div.right-panel-block-content[ng-hide*='hasParameterSet'] > span");
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> dropDownMenu;
	protected String dropDownMenuList;
	protected List<WebElement> columnHeaders;
	protected List<WebElement> resultName;
	protected List<WebElement> resultSize;
	protected List<WebElement> resultRecords;
	protected List<WebElement> resultType;
	protected List<WebElement> descIcon;
	protected String resultsList;
	protected String desc;
	protected List<WebElement> rightPanelData;
	protected List<WebElement> allParams;
	protected String infoPanelContent;
	protected String params;
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
		isElementDisplayed(testNameLocator, "Test name");
		isElementDisplayed(testDropDownLocator, "Test Drop down button");
		isElementDisplayed(listHeaderLocator, "List header");
		isRightPanelIconDisplayed();
		isElementDisplayed(closeIconLocator, "Close layer icon");
	}
	
	public void isRightPanelIconDisplayed(){
		rightPanelData = driver.findElements(rightPanelIconLocator);
        if(rightPanelData.get(0).isDisplayed()){
        	logTAFInfo("Found Info Icon");
        } else{
        	logTAFError("Info Icon missing");
        }
        if(rightPanelData.get(1).isDisplayed()){
        	logTAFInfo("Found Parameters Icon");
        } else{
        	logTAFError("Parameters Icon missing");
        }
	}
	
	public String getResultsList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		takeScreenshot();
		columnHeaders = driver.findElements(listColHeaderLocator);
		resultName = driver.findElements(resultNameLocator);
		if(resultName.size()>0){
			resultType = driver.findElements(resultTypeLocator);
			resultSize = driver.findElements(resultSizeLocator);
			resultRecords = driver.findElements(resultRecordsLocator);
		}else{
			logTAFError("No job results available");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText()+"|"+columnHeaders.get(2).getText()+"|"+columnHeaders.get(3).getText();
		}
		for(int i = 0; i < resultName.size(); i++) {
	        	if(i==0){
	        		if(isCheckboxChecked()){
	        			logTAFError("The result "+resultName.get(i).getText()+"has checked checkbox");
	        		}
	        		resultsList=columnHeaders.get(0).getText()+":"+resultName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+resultType.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+resultRecords.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+resultSize.get(i).getText();
	        	}else{
	        		if(isCheckboxChecked()){
	        			logTAFError("The result "+resultName.get(i).getText()+"has checked checkbox");
	        		}
	        		resultsList=resultsList+"\r\n"+columnHeaders.get(0).getText()+":"+resultName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+resultType.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+resultRecords.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+resultSize.get(i).getText();
	        	}
	        }
	        return resultsList;
	}
	
	public boolean isCheckboxChecked(){
		if(driver.findElements(resultCheckboxLocator).get(1).getAttribute("style").contains("display: none")){
			return false;
		}
		return true;
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
	
	public String getTestName(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testNameLocator));
		return driver.findElement(testNameLocator).getText();
	}
	
	public String getTestsListFromDropDown(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownLocator));
		driver.findElement(testDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownMenuItemsLocator));
		takeScreenshotWithoutScroll();
		dropDownMenu = driver.findElements(testDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(i==0){
        		dropDownMenuList=dropDownMenu.get(i).getText();
        	}else{
        		dropDownMenuList=dropDownMenuList+"\r\n"+dropDownMenu.get(i).getText();
        	}
        }
		driver.findElement(testDropDownLocator).click();
		return dropDownMenuList;
	}
	
	public String getListHeader(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(listHeaderLocator));
		return driver.findElement(listHeaderLocator).getText();
	}
	
	public String getInfo(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(infoContentDataLocator));
		for(int i = 0; i < 4; i++) {
        	if(i==0){
        		infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        		//infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText();
        	}else if(i<3){
        		//infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        		infoPanelContent=infoPanelContent+"\r\n"+driver.findElements(infoContentLabelLocator).get(i).getText();
        	}else{
        		infoPanelContent=infoPanelContent+"\r\n"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        	}
        }
		return infoPanelContent;
	}
	
	public String getParams(){
		allParams = driver.findElements(paramLabelLocator);
		if(allParams.size()>0){
			for(int i = 0; i < allParams.size(); i++) {
	        	if(i==0){
	        		params=driver.findElements(paramLabelLocator).get(i).getText()+":"+driver.findElements(paramValueLocator).get(i).getText();
	        		//infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText();
	        	}else{
	        		//infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
	        		params=params+"\r\n"+driver.findElements(paramLabelLocator).get(i).getText()+":"+driver.findElements(paramValueLocator).get(i).getText();
	        	}
	        }
		}else{
			params = driver.findElement(paramMsgLocator).getText();
		}
		return params;
	}
	public Boolean clickTestNameFromDropDown(String testName){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownLocator));
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		driver.findElement(testDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownMenuItemsLocator));
		dropDownMenu = driver.findElements(testDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(testName)){
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(testDropDownLocator).click();
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
        		dropDownMenu.get(i).click();
        		return true;
        	}
        }
		driver.findElement(projectDropDownLocator).click();
		return false;
	}
	
	public void clickTableDownloadIcon( String tableName){
		resultName = driver.findElements(resultNameLocator);
		if(resultName.size()>0){
			resultType = driver.findElements(resultTypeLocator);
		}else{
			logTAFInfo("No results found");
		}
		for(int i = 0; i < resultName.size(); i++) {
	        	if(tableName.equalsIgnoreCase(resultName.get(i).getText())){
	        		driver.findElements(tableDownloadIconLocator).get(i).click();
	        		return;
	        	}
		}
		logTAFError("Table: '"+tableName+"' not found");
	}
	
	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	


}
