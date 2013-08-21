package ax.lib;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.acl.qa.taf.helper.KeywordSuperHelper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.thoughtworks.selenium.DefaultSelenium;

public class KeywordHelper extends KeywordSuperHelper {

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpExpectedErr; //@arg error message for negative test
	protected String dpKnownBugs; //@arg infomation for known bugs (won't be
									//fixed in this relase)
	protected String dpWebDriver; //@arg Selenium webdriver type
									//@value = HtmlUnit|Firefox|...

	protected String dpUserName; //@arg username for login
	protected String dpPassword; //@arg password for login
	protected String dpEndWith; //@arg actions after test
								//@value = logout|quit|kill, or empty
	// END of datapool variables declaration

	protected WebDriver driver;
	protected boolean casAuthenticated;

	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
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



	//***************  Part 3  *******************
	// ******* Methods on login   ****************
	// *******************************************
	
	public boolean casLogin(String url){
		boolean done =false;
		String loginUrl = url.substring(0,url.indexOf("/aclax/")) + "/cas/login";// "/cas/login"
		
		logTAFStep("User login - '"+loginUrl+"'");
		
		driver.get(loginUrl);
				
		if(isUserLoggedon()){
			if(!casAuthenticated){
			  logTAFError(autoIssue+"User CAS session is still alive?");			  
			}else{
				logTAFInfo("CAS authenticated session is still alive as expected");
			}			
		  done = true;			
		}else{			
			   if(!casAuthenticated){
				   logTAFInfo("CAS Login required as expected");		  
				}else{
					logTAFError(autoIssue+"User CAS session experied or server problem?");	
					
				}		
		   submitCredential();		
		   if(isUserLoggedon()){
			logTAFInfo("User CAS logon successed!");
			done = true;
		   }else{
			logTAFError("Failed to logon - "+dpUserName+":"+dpPassword);
			done = false;
		   }
		}
		
		setSharedObj();
		driver.get(url);
		return done;
	}
	
	public void submitCredential(){
		try {
			// WebElement form = driver.findElement(By.id("id1"));

			WebElement username = driver.findElement(By.id("username"));
			logTAFStep("Input username '"+dpUserName+"'");
			username.sendKeys(dpUserName);
			
			WebElement password = driver.findElement(By.id("password"));
			logTAFStep("Input password '"+dpPassword+"'");
			password.sendKeys(dpPassword);

			WebElement submit = driver.findElement(By
					.xpath("//input[@name='submit']"));
            logTAFStep("Submit user credential");
			submit.click();
			// How to wait for page load?
			sleep(2);
		} catch (Exception e) {
			logTAFError("CAS login form not found? "+e.toString());
		}
		

	}
	
	//***************  Part 4  *******************
	// ******* Methods on verification ***********
	// *******************************************
	
	public boolean isUserLoggedon(){
		String infoText = "You have successfully logged in";
		
		boolean done = false;
		if(driver!=null){
			try{
				   WebElement msg = driver.findElement(By.className("success"));
				   logTAFStep("User login sucess!");
				   casAuthenticated = true;
				   done = true;
				}catch(Exception e){
					logTAFDebug("Faile to login?");
					done =  false;
				}
			
			}
//			try{
//				driver.findElement(By.id("username"));			
//			    driver.findElement(By.id("password"));			    
//	            casAuthenticated = false;
//	            setSharedObj();
//			}catch(Exception e){
//				casAuthenticated = true;
//	            setSharedObj();
//				done = true;
//			}
			
		return done;	
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
	
	//***************  Part 5  *******************
	// ******* Methods on terminate **************
	// *******************************************
	
	public void cleanUp(String url) {

		if (dpEndWith.equals("close")) {
           closeBrowser();
		}else if (dpEndWith.equals("kill")) { // if image name is available
          killBrowser(projectConf.imageName);
		} else if (dpEndWith.equals("logout")) {	
			casLogout(url);						
		} else {
			return;
		}		
	}

	public void closeBrowser(){
		driver.close();
		driver = null;
		casAuthenticated = false;
		logTAFStep("Close test browser");
		setSharedObj();
	}
	
	public void killBrowser(){
		killBrowser(imageName);
	}
	
	public void killBrowser(String imageName){
		logTAFStep("Kill browser '" + imageName + "'");
		killProcess(imageName);
		driver = null;
		casAuthenticated = false;
		setSharedObj();
	}
	
	public boolean casLogout(String url){
		String infoText = "You have successfully logged out";
		
		String logoutUrl = url.substring(0,url.indexOf("/aclax/")) + "/cas/logout";// "/cas/login"
		
		logTAFStep("Logout user - '"+logoutUrl+"'");
		driver.get(logoutUrl);
		try{
		   //WebElement msg = driver.findElement(By.cssSelector("p:contains("+infoText+")"));
		   //WebElement msg = driver.findElement(By.xpath("//p[.,'"+infoText+"']"));
		   WebElement msg = driver.findElement(By.className("success"));
		   logTAFStep("Logout user sucessfully - '"+logoutUrl+"'");
		   casAuthenticated = false;
		}catch(Exception e){
			logTAFError("Faile to logout url? - '"+logoutUrl+"'");
			return false;
		}
		setSharedObj();
		return true;
	}

	//***************  Part 6  *******************
	// ******* Methods on Objects sharing ********
	// *******************************************
	
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

}
