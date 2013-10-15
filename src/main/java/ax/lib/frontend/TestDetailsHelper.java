package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestDetailsHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>TestSetDetailsHelper</b>
	 * Generated     : <b>Sep 27, 2013</b>
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
	By projectDropDownLocator = By.cssSelector("div.sub-layer2 > div.sub-layer-dropdown > a");
	By projectDropDownMenuItemsLocator = By.cssSelector("div.sub-layer2 > div.dropdown > ul.dropdown-menu > li > a");
	By testSetDropDownLocator = By.cssSelector("div.sub-layer1 > div.sub-layer-dropdown > a");
	By testSetDropDownMenuItemsLocator = By.cssSelector("div.sub-layer1 > div.dropdown > ul.dropdown-menu > li > a");
	By testDropDownLocator = By.cssSelector("div.title-row > div.dropdown > a");
	By testDropDownMenuItemsLocator = By.cssSelector("div.title-row > div.dropdown > ul.dropdown-menu > li > a");
	By analyticsHeaderLocator = By.cssSelector("div.analytic-script-subtitle > span");
	By analyticNameLocator = By.cssSelector("div.script-row > div.row-fluid > div.ng-binding");
	//By analyticNameOfOpenDrawerLocator = By.cssSelector("div.drawer-opened > div > div.ng-binding");
	By analyticRunIconLocator = By.cssSelector("div.script-row > div > a");
	By analyticDescriptionLocator = By.cssSelector("div[style*='height: auto'] > div.drawer[ng-show*='runInfo'] > span");
	By analyticDrawerBtnLocator = By.cssSelector("div.drawer > div > a.action-btn");
	By analyticJobsIconLocator = By.cssSelector("div.action-buttons > a.icon_list");
	By analyticScheduleIconLocator = By.cssSelector("div.action-buttons a.icon_comment");
	By analyticJobDrawerHeaderLocator = By.cssSelector("div[style*='height: auto'] > div.drawer[ng-show*='results'] > div > h3 > span");
	By analyticScheduleDrawerHeaderLocator = By.cssSelector("div[style*='height: auto'] > div.drawer[ng-show*='schedule'] > div > h3 > span");
	By analyticJobsTableColHeaderLocator = By.cssSelector("div[style*='height: auto'] > div.drawer[ng-show*='results'] > div.headlines > div > span");
	By analyticScheduleTableColHeaderLocator = By.cssSelector("div[style*='height: auto'] > div.drawer[ng-show*='schedule'] > div.headlines > div > span");
	By analyticJobRunByLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(1)");
	By analyticJobParameterSetLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(2)");
	By analyticJobStartTimeLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(3) > span");
	By analyticJobEndTimeLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(4) > span");
	By analyticJobStatusLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(5)");
	By analyticJobResultLinkLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div:nth-child(6) > a > span");
	By scheduledByLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div:nth-child(1)");
	By scheduleTypeLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div:nth-child(2)");
	By scheduleRepeatLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div:nth-child(3)");
	By scheduleNextLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div:nth-child(4) > span");
	By scheduleParamSetLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div:nth-child(5)");
	//By analyticJobDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div");
	//By analyticJobDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='job'] > div > div > span");
	//By analyticScheduleDrawerTableTextValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div.ng-binding");
	//By analyticScheduleDrawerTableDTValuesLocator = By.cssSelector("div[style*='height: auto'] > div > div[ng-repeat*='schedule'] > div > div > span");
	//By analyticDrawerViewResultsLinkLocator = By.cssSelector("div.margin_top > div > div > a > span");
	By rightPanelTitleLocator = By.className("right-panel-block-title");
	By rightPanelIconLocator = By.className("right-panel-block-icon");
	By descriptionLocator = By.className("right-panel-block-content");
	By infoContentLabelLocator = By.cssSelector("div.right-panel-block-content > dl > dt > span");
	By infoContentDataLocator = By.cssSelector("div.right-panel-block-content > dl > dd");
	//By usersPopupHeaderLocator = By.className("modal-title");
	//By usersListLocator = By.cssSelector("div.modal-body > ul.user-list > li.user-row");
	//By usersPopupCloseIconLocator = By.cssSelector("div.modal-header > div.icon-remove");
	
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> rightPanelData;
	protected List<WebElement> dropDownMenu;
	protected String dropDownMenuList;
	protected List<WebElement> users;
	protected String usersList;
	protected List<WebElement> tests;
	protected String testsList;
	protected List<WebElement> analytics;
	protected String analyticsList;
	protected List<WebElement> categories;
	protected String categoriesList;
	protected int index;
	protected List<WebElement> runIcons;
	protected List<WebElement> jobsIcons;
	protected List<WebElement> scheduleIcons;
	protected List<WebElement> columnHeaders;
	protected List<WebElement> jobRunBy;
	protected List<WebElement> jobStartTime;
	protected List<WebElement> jobEndTime;
	protected List<WebElement> jobStatus;
	protected List<WebElement> jobResultsLink;
	protected List<WebElement> jobParamSet;
	protected String jobsList;
	protected String[] jobParamSetArr;
	protected List<WebElement> scheduledBy;
	protected List<WebElement> scheduleType;
	protected List<WebElement> scheduleRepeat;
	protected List<WebElement> scheduleNext;
	protected List<WebElement> scheduleParamSet;
	protected String scheduleList;
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
		isElementDisplayed(testNameLocator, "Test name");
		isElementDisplayed(testDropDownLocator, "Test Drop down button");
		isElementDisplayed(analyticsHeaderLocator, "Analytics list header");
		isRightPanelIconDisplayed();
		isElementDisplayed(closeIconLocator, "Close layer icon");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void isRightPanelIconDisplayed(){
		rightPanelData = driver.findElements(rightPanelIconLocator);
        if(rightPanelData.get(0).isDisplayed()){
        	logTAFInfo("Found Description Icon");
        } else{
        	logTAFError("Description Icon missing");
        }
        if(rightPanelData.get(1).isDisplayed()){
        	logTAFInfo("Found Info Icon");
        } else{
        	logTAFError("Info Icon missing");
        }
	}

	public String getDescription(){
		
		return driver.findElements(rightPanelTitleLocator).get(0).getText()+":"+driver.findElement(descriptionLocator).getText();
	}
	
	public String getInfo(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(infoContentLabelLocator));
		for(int i = 0; i < 4; i++) {
        	if(i==0){
        		//infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        		infoPanelContent=driver.findElements(infoContentLabelLocator).get(i).getText();
        	}else{
        		//infoPanelContent=infoPanelContent+"|"+driver.findElements(infoContentLabelLocator).get(i).getText()+":"+driver.findElements(infoContentDataLocator).get(i).getText();
        		infoPanelContent=infoPanelContent+"\r\n"+driver.findElements(infoContentLabelLocator).get(i).getText();
        	}
        }
		return infoPanelContent;
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
	
	public String getTestName(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testNameLocator));
		return driver.findElement(testNameLocator).getText();
	}
	
	public String getTestsListFromDropDown(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownLocator));
		driver.findElement(testDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownMenuItemsLocator));
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
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
	
	public String getAnalyticsHeader(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(analyticsHeaderLocator));
		return driver.findElement(analyticsHeaderLocator).getText();
	}

	public String getAnalyticsList(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Boolean reachedbottom = false;
		do{
			  sleep(timerConf.waitToTakeScreenshot);
			  captureScreen(getScreenshotPathAndName());
			  logTAFInfo("Screenshot taken");
			  js.executeScript("scroll(0,window.innerHeight);");
			  reachedbottom = Boolean.parseBoolean(js.executeScript("return ((window.innerHeight + window.scrollY) >= document.body.offsetHeight);").toString());
			  }while(!reachedbottom);
			js.executeScript("scroll(0,document.body.offsetHeight);");
			sleep(timerConf.waitToTakeScreenshot);
			captureScreen(getScreenshotPathAndName());
			logTAFInfo("Screenshot taken");
			js.executeScript("scroll(250,0);");
		analytics = driver.findElements(analyticNameLocator);
		if(analytics.size() >0){
			for(int i = 0; i < analytics.size(); i++) {
	        	if(i==0){
	        		analyticsList=analytics.get(i).getText();
	        	}else{
	        		analyticsList=analyticsList+"\r\n"+analytics.get(i).getText();
	        	}
	        }
		}else{
			analyticsList = "";
			logTAFError("No Analytics Available.");
		}
		return analyticsList;
	}
	
	public void verifyAnalyticsIcons(){
		analytics = driver.findElements(analyticNameLocator);
		runIcons = driver.findElements(analyticRunIconLocator);
		jobsIcons = driver.findElements(analyticJobsIconLocator);
		scheduleIcons = driver.findElements(analyticScheduleIconLocator);
        for(int i = 0; i < analytics.size(); i++) {
            if(runIcons.get(i).isDisplayed()){
            	logTAFInfo("Found Run Icon for analytic '"+analytics.get(i).getText()+"'");
            } else{
            	logTAFError("Run Icon missing for analytic '"+analytics.get(i).getText()+"'");
            }
            if(jobsIcons.get(i).isDisplayed()){
            	logTAFInfo("Found Jobs Icon for analytic '"+analytics.get(i).getText()+"'");
            } else{
            	logTAFError("Jobs Icon missing for analytic '"+analytics.get(i).getText()+"'");
            }
            if(scheduleIcons.get(i).isDisplayed()){
            	logTAFInfo("Found Schedule Icon for analytic '"+analytics.get(i).getText()+"'");
            } else{
            	logTAFError("Schedule Icon missing for analytic '"+analytics.get(i).getText()+"'");
            }
        }
	}
	
	public String getCategoriesName(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(analyticsHeaderLocator));
		categories = driver.findElements(analyticsHeaderLocator);
        for(int i = 0; i < categories.size(); i++) {
        	if(i==0){
        		categoriesList=categories.get(i).getText();
        	}else{
        		categoriesList=categoriesList+"|"+categories.get(i).getText();
        	}
        }
		return categoriesList;
	}
	
	public String getJobsList(){
		//**** This code doesnt handle if in one drawer there is one job without param set and another without.
		// it can only handle if all jobs have param set or if none have param set
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
		columnHeaders = driver.findElements(analyticJobsTableColHeaderLocator);
		jobRunBy = driver.findElements(analyticJobRunByLocator);
		if(jobRunBy.size()>0){
			jobStartTime = driver.findElements(analyticJobStartTimeLocator);
			jobEndTime = driver.findElements(analyticJobEndTimeLocator);
			jobStatus = driver.findElements(analyticJobStatusLocator);
			jobResultsLink = driver.findElements(analyticJobResultLinkLocator);
			jobParamSet = driver.findElements(analyticJobParameterSetLocator);
		}else{
			logTAFInfo("No analytic jobs found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText()+"|"+columnHeaders.get(2).getText()+"|"+columnHeaders.get(3).getText()+"|"+columnHeaders.get(4).getText();
		}
		if(jobParamSet.size()==0){
			for(int i = 0; i < jobRunBy.size(); i++) {
	        	if(i==0){
	        		jobsList=columnHeaders.get(0).getText()+":"+jobRunBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":No Parameter set used|"+columnHeaders.get(2).getText()+":"+jobStartTime.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+jobEndTime.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+jobStatus.get(i).getText()+"|"+columnHeaders.get(5).getText()+":"+jobResultsLink.get(i).getText();
	        	}else{
	        		jobsList=jobsList+"\r\n"+columnHeaders.get(0).getText()+":"+jobRunBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":No Parameter set used|"+columnHeaders.get(2).getText()+":"+jobStartTime.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+jobEndTime.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+jobStatus.get(i).getText()+"|"+columnHeaders.get(5).getText()+":"+jobResultsLink.get(i).getText();
	        	}
	        }
		}else{
			for(int i = 0; i < jobRunBy.size(); i++) {
	        	if(i==0){
	        		jobsList=columnHeaders.get(0).getText()+":"+jobRunBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+jobParamSet.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+jobStartTime.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+jobEndTime.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+jobStatus.get(i).getText()+"|"+columnHeaders.get(5).getText()+":"+jobResultsLink.get(i).getText();
	        	}else{
	        		jobsList=jobsList+"\r\n"+columnHeaders.get(0).getText()+":"+jobRunBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+jobParamSet.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+jobStartTime.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+jobEndTime.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+jobStatus.get(i).getText()+"|"+columnHeaders.get(5).getText()+":"+jobResultsLink.get(i).getText();
	        	}
	        }
		}
	    return jobsList;
	}
	
	public String getScheduleList(){
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
		columnHeaders = driver.findElements(analyticScheduleTableColHeaderLocator);
		scheduledBy = driver.findElements(scheduledByLocator);
		if(scheduledBy.size()>0){
			scheduleType = driver.findElements(scheduleTypeLocator);
			scheduleRepeat = driver.findElements(scheduleRepeatLocator);
			scheduleNext = driver.findElements(scheduleNextLocator);
			scheduleParamSet = driver.findElements(scheduleParamSetLocator);
		}else{
			logTAFInfo("No analytic schedules found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText()+"|"+columnHeaders.get(2).getText()+"|"+columnHeaders.get(3).getText()+"|"+columnHeaders.get(4).getText();
		}
        for(int i = 0; i < scheduledBy.size(); i++) {
        	if(i==0){
        		scheduleList=columnHeaders.get(0).getText()+":"+scheduledBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+scheduleType.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+scheduleRepeat.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+scheduleNext.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+scheduleParamSet.get(i).getText();
        	}else{
        		scheduleList=scheduleList+"\r\n"+columnHeaders.get(0).getText()+":"+scheduledBy.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+scheduleType.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+scheduleRepeat.get(i).getText()+"|"+columnHeaders.get(3).getText()+":"+scheduleNext.get(i).getText()+"|"+columnHeaders.get(4).getText()+":"+scheduleParamSet.get(i).getText();
        	}
        }
		return scheduleList;
	}
	
	public String getAnalyticDescription(){
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
		return driver.findElement(analyticDescriptionLocator).getText();
	}
	
	public boolean clickRunIcon(String analyticName) {
		analytics = driver.findElements(analyticNameLocator);
		runIcons = driver.findElements(analyticRunIconLocator);
		for(int i = 0; i < analytics.size(); i++) {
        	if(analytics.get(i).getText().equals(analyticName)){
        		logTAFStep("Analytic: "+analyticName+" found and clicked on Run icon.");
        		runIcons.get(i).click();
        		return true;
        	}
        }
		logTAFError("Analytic: "+analyticName+" not found!!");
		return false;
	}	
	
	public void clickRunBtn(){
		
	}
	
	public void viewLatestJobResults(){
		jobResultsLink = driver.findElements(analyticJobResultLinkLocator);
		jobResultsLink.get(0).click();
	}
	
	public boolean clickJobsIcon(String analyticName) {
		analytics = driver.findElements(analyticNameLocator);
		jobsIcons = driver.findElements(analyticJobsIconLocator);
		for(int i = 0; i < analytics.size(); i++) {
        	if(analytics.get(i).getText().equals(analyticName)){
        		logTAFStep("Analytic: "+analyticName+" found and clicked on Jobs icon.");
        		jobsIcons.get(i).click();
        		return true;
        	}
        }
		logTAFError("Analytic: "+analyticName+" not found!!");
		return false;
	}
	
	public boolean clickScheduleIcon(String analyticName) {
		analytics = driver.findElements(analyticNameLocator);
		scheduleIcons = driver.findElements(analyticScheduleIconLocator);
		for(int i = 0; i < analytics.size(); i++) {
        	if(analytics.get(i).getText().equals(analyticName)){
        		logTAFStep("Analytic: "+analyticName+" found and clicked on Schedule icon.");
        		scheduleIcons.get(i).click();
        		return true;
        	}
        }
		logTAFError("Analytic: "+analyticName+" not found!!");
		return false;
	}
	
	public Boolean clickTestNameFromDropDown(String testName){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownLocator));
		driver.findElement(testDropDownLocator).click();
		wait.until(ExpectedConditions.presenceOfElementLocated(testDropDownMenuItemsLocator));
		dropDownMenu = driver.findElements(testDropDownMenuItemsLocator);
        for(int i = 0; i < dropDownMenu.size(); i++) {
        	if(dropDownMenu.get(i).getText().equalsIgnoreCase(testName)){
        		logTAFStep("Test: "+testName+" found and clicked on.");
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
