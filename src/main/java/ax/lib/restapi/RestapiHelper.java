package ax.lib.restapi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import ax.lib.restapi.db.SQLConf;

import com.acl.qa.taf.helper.KeywordSuperHelper;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class RestapiHelper extends KeywordSuperHelper {
	
	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
									//@value = HtmlUnit|Firefox|...

	protected String dpUserName;  //@arg username for login
	protected String dpPassword;  //@arg password for login
	protected String dpEndWith;   //@arg actions after test
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
			//driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
			driver = new HtmlUnitDriver(BrowserVersion.CHROME);
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

	public WebDriver setupHtmlUnitDriver() {
		int versionNumber = 9; 
		return (new HtmlUnitDriver(initIEBrowser(versionNumber)));
	}

	private BrowserVersion initIEBrowser(int versionNumber) {
	      String applicationName = "Microsoft Internet Explorer";
	      String applicationVersion = "5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)";
	      String userAgent = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; InfoPath.3)";

	      return (new BrowserVersion(applicationName,applicationVersion,userAgent,versionNumber));
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
   			true,          //Exact Match
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

    public String queryProjectID(String scope, String projectname){
    	String id = "";
    	String sql = SQLConf.getProjectID(scope, projectname);
    	
    	ResultSet rs = queryDB(sql);
    	try {
    		rs.next();
    		id = rs.getString("id");
			logTAFInfo("Project uuid is retrieved successfully");
    	} catch (SQLException e) {
			logTAFError("Cannot find the project uuid for specified project - Please check your data. "+e.toString());
    	}
    	 
	    return id;
    }

    public String queryTestSetID(String scope, String projectname, String testsetname){
    	String id = "";
    	String sql = SQLConf.geTestSetID(scope, projectname, testsetname);
    	
    	ResultSet rs = queryDB(sql);
    	try {
    		rs.next();
    		id = rs.getString("id");
			logTAFInfo("Testset uuid is retrieved successfully");
    	} catch (SQLException e) {
			logTAFError("Cannot find the testset uuid for specified testset - Please check your data. "+e.toString());
    	}
    	 
	    return id;
   }

    public String queryTestID(String scope, String projectname, String testsetname, String testname){
    	String id = "";
    	String sql = SQLConf.getTestID(scope, projectname, testsetname, testname);
    	
    	ResultSet rs = queryDB(sql);
    	try {
    		rs.next();
    		id = rs.getString("id");
			logTAFInfo("Test uuid is retrieved successfully");
    	} catch (SQLException e) {
			logTAFError("Cannot find the test uuid for specified test - Please check your data. "+e.toString());
    	}
    	 
	    return id;
   }

    public String queryAnalyticID(String scope, String projectname, String testsetname, String testname, String analyticname){
    	String id = "";
    	String sql = SQLConf.getAnalyticID(scope, projectname, testsetname, testname, analyticname);
    	
    	ResultSet rs = queryDB(sql);
    	try {
    		rs.next();
    		id = rs.getString("id");
			logTAFInfo("Analytic uuid is retrieved successfully");
    	} catch (SQLException e) {
			logTAFError("Cannot find the analytic uuid for specified analytic - Please check your data. "+e.toString());
    	}
    	 
	    return id;
   }

}