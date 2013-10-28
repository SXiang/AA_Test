package ax.lib.restapi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


import org.openqa.selenium.remote.DesiredCapabilities;

import ax.lib.restapi.TestDriverExampleHelper;
import ax.lib.restapi.TestSuiteExampleHelper;

import com.acl.qa.taf.helper.KeywordSuperHelper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.thoughtworks.selenium.DefaultSelenium;

public class KeywordExampleHelper extends KeywordSuperHelper {

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
		String dpNumber = getDpString("Number");
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
		//Browser="Chrome";
		//Browser="InternetExplorer";
		//Browser="fireFox";
        String imageName = "";
		logTAFStep("Start a new browser for testing - " + Browser);
		if (Browser.equalsIgnoreCase("HtmlUnit")) {
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
			imageName = "";
		} else if (Browser.equalsIgnoreCase("FireFox")) {
			driver = new FirefoxDriver();
			imageName = "firefox.exe";
		} else if (Browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
			driver = new ChromeDriver();
			imageName = "chrome.exe";
		} else if (Browser.equalsIgnoreCase("InternetExplorer")) {
			System.setProperty("webdriver.ie.driver", projectConf.toolDir+"IEDriverServer32.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(ieCapabilities);
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
		if(getDpString("L10NDemo").equalsIgnoreCase("Yes")){
            nlsDemo();
		}
            
        try{
			WebElement username = driver.findElement(By.id("username"));
			logTAFStep("Input username '"+dpUserName+"'");
			username.sendKeys(dpUserName);
//		inputChars(username,"用户测试username");
			WebElement password = driver.findElement(By.id("password"));
			logTAFStep("Input password '"+dpPassword+"'");

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
	
	public boolean compareJsonResult(String result) {
		  fileExt += "[JSON]";  
          return compareJsonResult(result,"");
	}
	
	public boolean compareJsonResult(String result,String master)	{
				
        String[] ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")"};
        String[] ignoreName = {"$1u-u-i-d$2"};
        String delimiterPattern = "\\},\\{";
        
        return compareResult(
        	master,result,
   			ignorePattern,ignoreName,  //Replacement
   			delimiterPattern);  // used to split
		
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
			driver = ((TestSuiteExampleHelper) suiteObj).currentDriver;
			casAuthenticated = ((TestSuiteExampleHelper) suiteObj).casAuthenticated;
		} else if (caseObj != null) {
			driver = ((TestDriverExampleHelper) caseObj).currentDriver;
			casAuthenticated = ((TestDriverExampleHelper) caseObj).casAuthenticated;
		}
	}

	public void setSharedObj() {
		if (suiteObj != null) {
			((TestSuiteExampleHelper) suiteObj).currentDriver = driver;
			((TestSuiteExampleHelper) suiteObj).casAuthenticated = casAuthenticated;
		} else if (caseObj != null) {
			((TestDriverExampleHelper) caseObj).currentDriver = driver;
			((TestDriverExampleHelper) caseObj).casAuthenticated = casAuthenticated;
		}
	}

}
