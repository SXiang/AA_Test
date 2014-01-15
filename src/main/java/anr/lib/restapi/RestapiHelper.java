package anr.lib.restapi;

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
import com.acl.qa.taf.util.FileUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class RestapiHelper extends KeywordSuperHelper {
	/**
	 * Script Name   : <b>GetProjectList</b>
	 * Generated     : <b>Aug. 19, 2013 4:20:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2013/09/01
	 * @author Karen_Zou
	 */
	
	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
									//@value = HtmlUnit|Firefox|...
	protected String dpEndWith;   //@arg actions after test
								  //@value = logout|quit|kill, or empty
	// END of datapool variables declaration

	protected WebDriver driver;
	protected String scheduleid;
	

	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();

		dpExpectedErr = getDpString("ExpectedErr");
		dpKnownBugs = getDpString("KnownBug");
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
		if (dpWebDriver.equals("")&&driver!=null) {

		} else {
//			startApp();
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
        
        projectConf.imageName = imageName;
		setSharedObj();

	}

	public void startApp() {
		try {
			logTAFInfo("Run AclRevolution batch '");
			FileUtil.exeComm("start \" "+ "AclRevolution.bat" +"\" /D\"" + "C:\\ACL\\ANR\\"+ "\" /MAX /SEPARATE /B \""+"AclRevolution.bat"+"\"") ;
//			ProjectConf.startComm = "Start \""+imageName+"\" /D\""+FileUtil.getAbsDir(workbookDir)+"\" /MAX /SEPARATE /B \""+AUT+imageName+"\"";
			sleep(10);
		} catch (Exception e) {

		}
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
	
	
	//***************  Part 4  *******************
	// ******* Methods on verification ***********
	// *******************************************
	
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
				
        String[] ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")","\"startTime\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"endTime\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"createdAt\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"modifiedAt\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","[\\[\\{\\]\\}\\s]"};
        String[] ignoreName = {"$1u-u-i-d$2","\"startTime\"","\"endTime\"","\"createdAt\"","\"modifiedAt\"",""};
        String delimiterPattern = "\\}[\\s]*,[\\s]*\\{|[\\[\\]]";
        
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
		try{
		driver.close();
		driver = null;
		logTAFStep("Close test browser");
		setSharedObj();
		}catch(Exception e){
			logTAFWarning("Exception during browser cleanup? b"+e.toString());
		}
	}
	
	public void killBrowser(){
		killBrowser(imageName);
	}
	
	public void killBrowser(String imageName){
		logTAFStep("Kill browser '" + imageName + "'");
		killProcess(imageName);
		driver = null;
		scheduleid = null;
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
		} else if (caseObj != null) {
			driver = ((TestDriverExampleHelper) caseObj).currentDriver;

			scheduleid = ((TestDriverExampleHelper) caseObj).scheduleid;
		}
	}

	public void setSharedObj() {

		if (suiteObj != null) {
			((TestSuiteExampleHelper) suiteObj).currentDriver = driver;
		} else if (caseObj != null) {
			((TestDriverExampleHelper) caseObj).currentDriver = driver;

			((TestDriverExampleHelper) caseObj).scheduleid = scheduleid;
		}

	}

	//***************  Part 7  *******************
	// *******   Get info from Database   ********
	// *******************************************	
	public String getAuditItemUUID(String sql, String itemType,String itemName){
		String uuid = getField(sql,"id",itemType,itemName);
		return uuid;
	}
	public String getField(String sql, String fieldName,String itemType,String itemName){
		if(itemName==""){
			itemName = "AuditItem";
		}
		String field = itemName;
		
    	ResultSet rs = queryDB(sql);
    	
    	try {
    		rs.next();
    		field = rs.getString(fieldName);
			logTAFInfo(itemType+" '"+itemName+"' - '"+ fieldName+"' is retrieved successfully '"+field+"'");
    	} catch (SQLException e) {
			logTAFInfo("Warning - Cannot find the "+itemType+" '"+itemName+"' - '"+ fieldName+"' by '"+sql+"', using '"+field+"' in query then");
    	}
    	 
	    return field;
    }

	
	// **********
	// **************************** can these methods be changed to use getAuditItemUUID ? Steven     ********************************
	// **********
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