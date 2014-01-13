package anr.lib.frontend;

import java.awt.Toolkit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;



import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.Selenium;

import anr.lib.frontend.ANR_FrontendCommonHelper;

public class OpenProjectHelper extends ANR_FrontendCommonHelper{
	/**
	 * Script Name   : <b>OpenProjectsHelper</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProjectsHelper
	 * 
	 * @since  Oct 4, 2013
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
    							  //@value = HtmlUnit|Firefox|IE|Chrome
	//1225 protected String dpAXServerName; //@arg AX Server name or IP address
	//1225 protected String dpAXServerPort; //@arg AX Server port
	// END of datapool variables declaration

	// BEGIN locators of the web elements of OpenProject page
	By projectFilePathLocator = By.id("projectFilePath");
	By projectOpenButtonLocator = By.id("projectOpenButton");

	By projectHeaderLocator = By.cssSelector("span[key^='_Anr.Product.Navbar_'");  //Header:AN Revolution
    By chevronIconLocator = By.className("icon-chevron-left");
	By searchIconLocator = By.className("icon-search");
	
	By projectTitleLocator = By.id("projectTitle");
	By descriptionButtonLocator = By.id("description");
	By dataTablesButtonLocator = By.id("dataTables");
	By relatedFilesButtonLocator = By.id("relatedFiles");
	
	By importLocator = By.cssSelector("span[key^='_Anr.Project.Import_'");
	By prepareLocator = By.cssSelector("span[key^='_Anr.Project.Prepare_'");
	By analyzeLocator = By.cssSelector("span[key^='_Anr.Project.Analyze_'");

	By descriptionHeaderLocator = By.cssSelector("div[id='descriptionModal'] > div > div > div[class='modal-header'] > div > span");
	By descriptionBodyLocator = By.cssSelector("div[id='descriptionModal'] > div > div > div[class='modal-body']");
	By scriptGroupLocator = By.cssSelector("div[class^='script-group'] > div[class='ng-scope'] > div > div > div > div");
	By closeButtonLocator = By.cssSelector("button[class='close']");
	
	By titleRowLocator = By.cssSelector("div[class='row-fluid datatables-title-row']");
	
	By tableNameLocator = By.cssSelector("div[class^='datatables-row'] > div.row-fluid:nth-child(2) > div[class^='span7']");
	By tableRecordsLocator = By.cssSelector("div[class^='datatables-row'] > div.row-fluid:nth-child(2) > div[class^='span2'] > span[class^='ng-binding']");
	By tableSizeLocator = By.cssSelector("div[class^='datatables-row'] > div.row-fluid:nth-child(2) > div[class='span2 ng-binding']");
	By tableSizeUnitLocator = By.cssSelector("div[class^='datatables-row'] > div.row-fluid:nth-child(2) > div[class='span2 ng-binding'] > span[class^='dataTable-unit']");
	By dataVisualizeIconLocator = By.cssSelector("div[class^='datatables-row'] > div.row-fluid:nth-child(2) > div.span1 > a > i");

//END
     
    // BEGIN of other local variables declaration 
	private DesiredCapabilities capability;
	public String imageName;

	protected List<WebElement> allTables,allAnalytics,allTableNames, allTableRecords, allTableSizes, allTableSizeUnits;
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		dpWebDriver = projectConf.getWebDriver();
//1225		imageName = projectConf.getImageName();

		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	public boolean clickProjectOpenButton(String projectfolder, String projectfile) {
		//Enter ACL Project file folder
	    logTAFStep("Enter the full path of an ACL project file...");
		if(!projectfolder.isEmpty() && !projectfolder.isEmpty()) {
			WebElement element = driver.findElement(projectFilePathLocator);
			element.sendKeys(projectfolder+"\\" + projectfile);
		} else {
			logTAFWarning("");
		}
		
		//Click Open Local Project button
	    logTAFStep("Click Open Local Project button");
		driver.findElement(projectOpenButtonLocator).click();
		
	    logTAFStep("Click Open Local Project button successfully");
		return true;
	}
	
	public void verifyElementsEnabledOrDisplayed(){
		isElementEnabled(relatedFilesButtonLocator, "Related Files Button");
		isElementEnabled(dataTablesButtonLocator, "Data Tables Button");
		isElementEnabled(descriptionButtonLocator, "Project Description Button");
		isElementDisplayed(importLocator, "Import");
		isElementDisplayed(prepareLocator, "Prepare");
		isElementDisplayed(chevronIconLocator, "Go Back");
		isElementDisplayed(searchIconLocator, "Search");
	}

	public String getProjectTitle() {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectTitleLocator));
		return driver.findElement(projectTitleLocator).getText();
	}	

	public String getDescription() {
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(descriptionButtonLocator));

		//Click Description
		logTAFStep("Click Description button");
		
		driver.findElement(descriptionButtonLocator).click();

		String header=driver.findElement(descriptionHeaderLocator).getText();
		String body=driver.findElement(descriptionBodyLocator).getText();
		
		//Return Description Header&Bodyof project
		return header+"\r\n"+body;
	
	}
	
	public  String getAllAnalytics(String projectfolder, String projectfile) {
		String analytics = "";

		allAnalytics = driver.findElements(scriptGroupLocator);
			
        for(int i = 0; i < allAnalytics.size(); i++) {
        	if (i==0)
        		analytics=allAnalytics.get(i).getText();
        	else
        		analytics=analytics+"\r\n"+allAnalytics.get(i).getText();
        }
		
        return analytics;
	}

	public String getProjectHeader() {
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(projectHeaderLocator));

		return "@"+driver.findElement(projectHeaderLocator).getText()+"@";
	}

	public void clickDataTablesButton() {
		//Click Data Tables Icon Button
	    logTAFStep("Click Data Tables Icon Button");
		driver.findElement(dataTablesButtonLocator).click();
	}
	
	public void clickRelatedFilesButton(){
		//Click Related Files Link
	    logTAFStep("Click Related Files Button");
		driver.findElement(relatedFilesButtonLocator).click();
	}

	public void closeLayer() {
		new Actions(driver).moveToElement(driver.findElement(closeButtonLocator)).click().perform();
	}	

	public void launchBrowser() {
		if (dpWebDriver.equals("")) {
			logTAFError("Not able to read from project.properties the correct value of variable 'webDriver'. Please check the file.");
		} else {
			setupNewDriver(dpWebDriver);
		}
	}
	
	public void setupNewDriver(String browserType) {
		if (browserType.equalsIgnoreCase("chrome")) {
			logTAFStep("Recognized Chrome browser, about to intiate...");
			InitiateChromeBrowser();
			driver = new ChromeDriver(capability);
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		String url=projectConf.getanrPrefix()+"/index.html";
		driver.get(url);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screenResolution = new Dimension((int)
	    toolkit.getScreenSize().getWidth(), (int)
	    toolkit.getScreenSize().getHeight());
	    driver.manage().window().setPosition(new Point(0, 0));
		driver.manage().window().setSize(screenResolution);

		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}


	public void setupNewDriverCEF(String browserType) {

		File chromium = new File("C:\\ACL\\ANR\\ANR.exe");
		//File chromium = new File("C:\\ACL\\ANR\\FrontEnd\\Client\\ANR-Shortcut.shortcut");
 		System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		
//		options.addArguments("--start-maximized");
//		options.setBinary(chromium);
		options.setBinary("C:\\ACL\\ANR\\ANR.exe");
		//options.addArguments("/staticport","C:\\ANR\\DATA\\NonUnicode\\AllTypesACLProjects\\Analytics.ACL");
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(130, TimeUnit.SECONDS);
		
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//	    Dimension screenResolution = new Dimension((int)
//	    toolkit.getScreenSize().getWidth(), (int)
//	    toolkit.getScreenSize().getHeight());
//	    driver.manage().window().maximize();
//	    driver.manage().window().setPosition(new Point(0, 0));
		//driver.manage().window().setSize(screenResolution);
		//((JavascriptExecutor) driver).executeScript("window.resizeTo(1024, 768);");
		
		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}

	public void setupNewDriverJohn(String browserType) {

		File chromium = new File("C:\\ACL\\ANR\\ANR.exe");
		//File chromium = new File("C:\\ACL\\ANR\\FrontEnd\\Client\\ANR-Shortcut.shortcut");
 		System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		
//		options.addArguments("--start-maximized");
		options.setBinary(chromium);
		options.addArguments("--start-maximized");
		options.addArguments("/staticport","C:\\ANR\\DATA\\NonUnicode\\AllTypesACLProjects\\Analytics.ACL");
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(130, TimeUnit.SECONDS);
		
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//	    Dimension screenResolution = new Dimension((int)
//	    toolkit.getScreenSize().getWidth(), (int)
//	    toolkit.getScreenSize().getHeight());
//	    driver.manage().window().maximize();
//	    driver.manage().window().setPosition(new Point(0, 0));
		//driver.manage().window().setSize(screenResolution);
		//((JavascriptExecutor) driver).executeScript("window.resizeTo(1024, 768);");
		
		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}

	public void InitiateChromeBrowser(){
		// Commented code is for running using Selenium grid
		System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
		capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		capability.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
		capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
	}

	public void startApp() {
		String comm = projectConf.getanrBatch();
		System.out.println("comm:"+comm);
		
		logTAFStep("Run ANR batch to start HttpService");
		try {
			Runtime.getRuntime().exec(comm);
			sleep(3);
			logTAFStep("HttpService has started successfully");
		} catch (Exception e) {
			logTAFError("Not able to run ANR batch file. Please check the full path");			
		}
	}

}
