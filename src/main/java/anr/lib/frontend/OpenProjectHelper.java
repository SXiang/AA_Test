package anr.lib.frontend;

import java.awt.Toolkit;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.acl.qa.taf.util.FileUtil;

import anr.lib.frontend.FrontendCommonHelper;

public class OpenProjectHelper extends FrontendCommonHelper{
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
	protected String dpAXServerName; //@arg AX Server name or IP address
	protected String dpAXServerPort; //@arg AX Server port
	// END of datapool variables declaration

	// BEGIN locators of the web elements of OpenProject page
	By projectFilePathLocator = By.id("projectFilePath");
	//By projectOpenButtonLocator = By.id("projectOpenButton");
	By projectOpenButtonLocator = By.id("projectOpenButton");
	By projectTitleLocator = By.id("projectTitle");
	By dataTablesButtonLocator = By.id("dataTables");

	By analyzeLocator = By.className("script-group");
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
		dpAXServerName = projectConf.getAxServerName();
		dpAXServerPort = projectConf.getAxServerPort();
		imageName = projectConf.getImageName();

		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
//		isElementEnabled(projectOpenButtonLocator, "Open Local Project");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public  String getAllTables(String projectfolder, String projectfile) {
		String tables = "";
		
		if(((projectfolder != null) && (projectfolder!="")) && ((projectfile != null)||(projectfile!=""))) {
			clickdataTablesButton();
			allTableNames = driver.findElements(tableNameLocator);
			allTableRecords = driver.findElements(tableRecordsLocator);
			allTableSizes = driver.findElements(tableSizeLocator);
			allTableSizeUnits = driver.findElements(tableSizeUnitLocator);
		}

		//Locate the cursor to the first line
		driver.findElement(titleRowLocator).getText(); 
		tables = driver.findElement(titleRowLocator).getText(); 
		tables.replace("\n", " ");
        for(int i = 0; i < allTableNames.size(); i++) {
        	tables=tables+"\r\n"+allTableNames.get(i).getText()+" "+allTableRecords.get(i).getText()+" "+allTableSizes.get(i).getText();
        }
        return tables;
	}

	public  String getAllAnalytics(String projectfolder, String projectfile) {
		String analytics = "";
		
		if(((projectfolder != null) && (projectfolder!="")) && ((projectfile != null)||(projectfile!=""))) {
			allAnalytics = driver.findElements(analyzeLocator);
		}
		
//		analytics += analyticsTag.getText();
        for(int i = 0; i < allAnalytics.size(); i++) {
        	//analytics=analytics+"\r\n"+allAnalytics.get(i).getText();
        	analytics=analytics+allAnalytics.get(i).getText();
        }
        return analytics;
	}

	public String getProjectTitle() {
		return driver.findElement(projectTitleLocator).getText();
	}	
	
	public boolean clickProjectOpenButton(String projectfolder, String projectfile) {
		//Enter ACL Project file folder
	    logTAFStep("Enter the full path of an ACL project file...");
		if(((projectfolder != null) && (projectfolder!="")) && ((projectfolder != null)||(projectfolder!=""))) {
			WebElement element = driver.findElement(projectFilePathLocator);
			element.sendKeys(projectfolder+"\\" + projectfile);
		}
		
		//Click Open Local Project button
	    logTAFStep("Click Open Local Project button");
		driver.findElement(projectOpenButtonLocator).click();
		
	    logTAFStep("Click Open Local Project button successfully");
		return true;
	}
	
	public boolean clickdataTablesButton() {
		//Enter ACL Project file folder
	    logTAFStep("Click Data Tables button");
		driver.findElement(dataTablesButtonLocator).click();
		
		return true;
	}

	public boolean clickTableName(String tablename) {
		List<WebElement> allTables;
		List<WebElement> allDataVisualizeIcons;
		
	    logTAFStep("Find the table name");
		
	    //Find the table list
	    allTables = driver.findElements(tableNameLocator);
	    allDataVisualizeIcons = driver.findElements(dataVisualizeIconLocator);
	    		
		for(int i = 0; i < allTables.size(); i++) {
		    String temp = allTables.get(i).getText();
        	if (temp.equalsIgnoreCase(tablename)) {
        	    logTAFStep("Table name has been found successfully!");
        	    logTAFStep("Click the related data visualize icon");
        	    allDataVisualizeIcons.get(i).click();
        		
        		sleep(1);
        		return true;
        	}
		}

        return false;
	}

	public void launchBrowser() {
		if (dpWebDriver.equals("")) {
			logTAFError("Not able to read from project.properties the correct value of variable 'webDriver'. Please check the file.");
		} else {
			setupNewDriver(dpWebDriver);
		}
	}
	
/*	public void setupNewDriver(String browserType) {
		if (browserType.equalsIgnoreCase("chrome")) {
			logTAFStep("Recognized Chrome browser, about to intiate...");
			InitiateChromeBrowser();
			driver = new ChromeDriver(capability);
		}

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(projectConf.getanrPrefix()+"/index.html");
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screenResolution = new Dimension((int)
	    toolkit.getScreenSize().getWidth(), (int)
	    toolkit.getScreenSize().getHeight());
	    driver.manage().window().setPosition(new Point(0, 0));
		driver.manage().window().setSize(screenResolution);

		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}
*/

	public void setupNewDriver(String browserType) {

		File chromium = new File("C:\\ACL\\ANR\\FrontEnd\\Client\\ANR.exe");
		//File chromium = new File("C:\\ACL\\ANR\\FrontEnd\\Client\\ANR-Shortcut.shortcut");
 		System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--start-maximized");
		options.setBinary(chromium);
		 		
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
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
