package ax.lib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.acl.qa.taf.helper.KeywordSuperHelper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.thoughtworks.selenium.DefaultSelenium;

public class KeywordHelper extends KeywordSuperHelper {

	// BEGIN of datapool variables declaration
	protected String dpExpectedErr; // @arg error message for negative test
	protected String dpKnownBugs; // @arg infomation for known bugs (won't be
									// fixed in this relase)
	protected String dpWebDriver; // @arg Selenium webdriver type
									// @value = HtmlUnit|Firefox|...

	protected String dpUserName; // @arg username for login
	protected String dpPassword; // @arg password for login
	protected String dpEndWith; // @arg actions after test
								// @value = logout|quit|kill, or empty
	// END of datapool variables declaration

	protected WebDriver driver;
	protected boolean casAuthenticated;

	public boolean dataInitialization() {
		getSharedObj();

		dpExpectedErr = getDpString("ExpectedErr");
		dpKnownBugs = getDpString("KnownBug");
		dpUserName = getDpString("UserName");
		dpPassword = getDpString("Password");
		dpWebDriver = getDpString("WebDriver");
		dpEndWith = getDpString("EndWith");

		return true;
	}

	@Override
	public void testMain(Object[] args) {
		// Do something for all keywords -
		dataInitialization();
		super.testMain(args);

		activateBrowser();

	}

	public void activateBrowser() {
		if (dpWebDriver.equals("")) {

		} else {
			setupNewDriver(dpWebDriver);

		}

	}

	public void setupNewDriver(String Browser) {
        String imageName = "";
		logTAFStep("Start a new browser for testing - " + Browser);
		if (Browser.equalsIgnoreCase("HtmlUnit")) {
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
			imageName = "";
		} else if (Browser.equalsIgnoreCase("FireFox")) {
			driver = new FirefoxDriver();
			imageName = "firefox.exe";
		} else if (Browser.equalsIgnoreCase("Chrome")) {
			driver = new ChromeDriver();
			imageName = "chrome.exe";
		} else if (Browser.equalsIgnoreCase("InternetExplorer")) {
			driver = new InternetExplorerDriver();
			imageName = "iexploere.exe";
		} else {
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
			imageName = "";
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        
		casAuthenticated = false;
        projectConf.imageName = imageName;
		setSharedObj();

	}

	public void getSharedObj() {
		if (suiteObj != null) {
			driver = ((TestSuiteHelper) suiteObj).currentDriver;
			casAuthenticated = ((TestSuiteHelper) suiteObj).casAuthenticated;
		} else if (caseObj != null) {
			driver = ((TestDriverHelper) caseObj).currentDriver;
			casAuthenticated = ((TestDriverHelper) caseObj).casAuthenticated;
		}
	}

	public void setSharedObj() {
		if (suiteObj != null) {
			((TestSuiteHelper) suiteObj).currentDriver = driver;
			((TestSuiteHelper) suiteObj).casAuthenticated = casAuthenticated;
		} else if (caseObj != null) {
			((TestDriverHelper) caseObj).currentDriver = driver;
			((TestDriverHelper) caseObj).casAuthenticated = casAuthenticated;
		}
	}

	public void compareTextResult(String result, String verifyType) {
		if (result == null) {
			return;
		}
		compareTextFile(result,verifyType,true);
	}
	
	public void compareJsonResult(String result) {
		if (result == null) {
			return;
		}
		
        String[] ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")"};
        String[] ignoreName = {"$1u-u-i-d$2"};
        String verifyType = "JSON";
        String delimiterPattern = "\\},\\{";
        
		++fileIndex;
		if(!dpMasterFile.endsWith(fileExt)){
			thisMasterFile = dpMasterFile.trim()+"["+fileIndex+"]"+fileExt;
			superMasterFile = dpSuperMasterFile.trim()+"["+fileIndex+"]"+fileExt;
		}
		
		if(!dpActualFile.endsWith(fileExt)){
           thisActualFile  = dpActualFile.trim()+"["+fileIndex+"]"+fileExt;
		}
		
		compareTextFile(thisMasterFile, thisActualFile, result,
				projectConf.updateMasterFile, verifyType,true,
				ignorePattern,ignoreName,delimiterPattern);
	}

	public void cleanUp(String url) {

		if (dpEndWith.equals("close")) {
			driver.close();
			driver = null;
			casAuthenticated = false;
			logTAFStep("Close test browser");
		} else if (dpEndWith.equals("quit")) {
			driver.quit();
			driver = null;
			casAuthenticated = false;
			logTAFStep("Quit test browser");
		}else if (dpEndWith.equals("kill")) { // if image name is available
			logTAFStep("Kill browser '" + imageName + "'");
			killProcess();
			driver = null;
			casAuthenticated = false;
		} else if (dpEndWith.equals("logout")) {
			logTAFStep("Use logoff");
			url = url.substring(0,url.indexOf("/aclax/")) + "/cas/logout";// "/cas/login"
			driver.get(url);
			casAuthenticated = false;
		} else {
			return;
		}

		setSharedObj();
	}

}
