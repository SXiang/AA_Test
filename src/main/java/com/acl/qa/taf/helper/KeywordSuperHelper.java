package com.acl.qa.taf.helper;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.acl.qa.taf.helper.superhelper.InitializeTerminateHelper;
import com.acl.qa.taf.util.FileUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  January 05, 2012
 */
public abstract class KeywordSuperHelper extends InitializeTerminateHelper 
{

	// BEGIN of datapool variables declaration
   

    protected String dpExpectedErr;
    protected String dpKnownBugs;
//	protected String dpCommand;
//	protected String dpEndWith; //@arg Finish or cancel
                                //@VALUE = 'Finish' or 'Cancel', default to Finish
   // END of datapool variables declaration
    protected String 
            dpMasterFile,dpMasterFiles[],
			dpActualFile,dpActualFiles[],
			result[],
			dpSuperMasterFile,
			fileExt = ".vp",
			outputFolder = "result";
    
    protected int fileIndex = 0;
          // in the case of that the existences of files will prevent App functioning properly
          // enable delFile by setting it as 'true'
    protected boolean delFile = false;
    
	public void testMain(Object[] args) 
	{

		// Data-Driven Stub
		dataInit(args); 
		// Set file name and path for verification
		// they will be extended with [number] suffix at each comparison.
		
		setupVPFolder();  

		//testMain(args); - if there is one in the future;
		//exeCommands();
	}
	private boolean dataInit(Object[] args){
		boolean done= true;
		dpMasterFiles = getDpString("MasterFiles").split("\\|");
		result = new String[dpMasterFiles.length];
		dpActualFiles = new String[dpMasterFiles.length];
		return done;
	}
	
	protected String getNameFromPath(String path){
		String itemName = path;
		if(!path.equals(null)&&!path.equals("")){
			String temp[] = path.split("->");
			itemName = temp[temp.length-1];
		}	
	return itemName;
	}
	
	public boolean compareResult(String masterFile,  String result,
			 boolean exactMatch,
			String ignorePattern[],String[] ignoreName,String delimiterPattern){
		if (result == null) {
			return true;
		}
		
		return compareTextFile(
				setupMasterFile(masterFile), 
				thisActualFile,
				result,
		        projectConf.updateMasterFile, "File",
		        true,
		        ignorePattern,ignoreName,
		        delimiterPattern
		        );
	}
	
	public String setupMasterFile(String masterFile) {

		if (masterFile == null || masterFile.equals("")) {
			return "";
		}
//			++fileIndex;
//			if (!dpMasterFile.endsWith(fileExt)) {
//				thisMasterFile = dpMasterFile.trim() + "[" + fileIndex + "]"
//						+ fileExt;
//				superMasterFile = dpSuperMasterFile.trim() + "[" + fileIndex
//						+ "]" + fileExt;
//			}
//
//			if (!dpActualFile.endsWith(fileExt)) {
//				thisActualFile = dpActualFile.trim() + "[" + fileIndex + "]"
//						+ fileExt;
//			}
		   String name = "";
		   
		   if(!masterFile.contains("/")&&!masterFile.contains("\\")){
			 name = masterFile;  
			 superMasterFile = dpSuperMasterFile.trim() + name;
		   }else{
			 name = FileUtil.getFullName(masterFile);
			 masterFile = FileUtil.getAbsDir(masterFile+"/../").replaceFirst("/$","");  
			 superMasterFile = masterFile + name;
		   }
		   
		   
			thisMasterFile = dpMasterFile.trim() + name;
			thisActualFile = dpActualFile.trim() + name;
            if(delFile){  // in the case the existences of files will prevent App functioning properly
            	FileUtil.delFile(thisActualFile);  
            }

		return thisMasterFile;
	}
	//************ Setup file path for data verification ******************
	public String setupVPFolder(){
		
		return setupTestFiles(scriptName.replaceAll("\\.", "/"),"","No","");
	}
	public String setupTestFiles(String filename,String location){
		return setupTestFiles(filename,location,"No","");
	}
	public String setupTestFiles(String filename,String location, String defaultFileExt){
		return setupTestFiles(filename,location,"No",defaultFileExt);
	}
	public String setupTestFiles(String filename,String location, String append, String defaultFileExt){	
		
        return setupTestFiles(filename,location,append,defaultFileExt,"",-1);
	}
	

	public String setupTestFiles(String fName,String location, String append, 
			String defaultFileExt,String mFile, int numFile){	
		
		String tempMasterFile = "",tempActualFile = "", filename = fName;
		if(defaultFileExt.equals("")){
			defaultFileExt = fileExt;
		}
		fileExt = defaultFileExt;
		
        if(filename==null||filename.equals("")){
        	//logTAFWarning("Empty file name");
        	return "";
        }

		String localName = filename;
		String[] fd = filename.split("/");
		String fdName = fd[0];
		filename = fd[fd.length-1];
		for(int i=1;i<fd.length-1;i++){
		 fdName +="/"+fd[i];
		}
		if(fd.length==1)
			fdName = "";
		
	
		String superMDir = "/master/",expectedDir = "/expecteddata/",actualDir="/actualdata/";
		String subFilename = "";//filename+"_Line_"+(currentTestLine);
		dpSuperMasterFile = projectConf.testDataDir+fdName+superMDir+subFilename;
logTAFDebug("dpSuperMasterFile path '"+superMasterFile+"'");
		//        if(location.equalsIgnoreCase("Server")){
//        	localName = projectConf.tempServerNetDir+fdName+expectedDir+subFilename;
//    		fdName = projectConf.tempServerDir+expectedDir+subFilename;
//        }else{
//    		fdName = loggerConf.logRoot+fdName+subFilename;
//    		localName = fdName;    
//    	    
//    	}
        
        
        //localName = FileUtil.getFullNameWithExt(localName,defaultFileExt);
        tempActualFile = loggerConf.logRoot+fdName+actualDir+subFilename;
    	tempMasterFile = loggerConf.logRoot+fdName+expectedDir+subFilename;

    	logTAFDebug("DelFile is '"+delFile+"'");
    	FileUtil.mkDirs(tempMasterFile);
    	FileUtil.mkDirs(tempActualFile);  
 
		if(numFile>-1){ // for old taf - will be removed soon - Steven
			 dpMasterFiles[numFile]  = tempMasterFile;
			 dpActualFiles[numFile]  = tempActualFile;
		}else{
		     dpMasterFile = tempMasterFile;
		     dpActualFile  = tempActualFile;
		}
		
        thisMasterFiles = dpMasterFiles;
        thisActualFiles = dpActualFiles;
        
        
        thisMasterFile = dpMasterFile;
        thisActualFile = dpActualFile;
        
    	return subFilename;
	}
	
	// **** Required for keyword **************
	public Object[] onInitialize(Object[] args,String testName) {	
		if(args.length<3){
			logTAFError("Failed to load test data?");
			return args;
		}
		dpw = (HSSFRow) args[2];
		dph = (ArrayList<String>) args[1];
		//datapool = (HSSFSheet) args[0];		
		setScriptName(testName);
		return args;
	}
	
	
// **************** Methods On Debugging ****************************************************	
	public WebDriver startBrowser(String Browser) {
        WebDriver driver = null;
		if (Browser.equalsIgnoreCase("HtmlUnit")) {
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
		} else if (Browser.equalsIgnoreCase("FireFox")) {

		} else if (Browser.equalsIgnoreCase("Chrome")) {

		}else{
			driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	return driver;

	}
	public DefaultSelenium getSeleniumClient(String url) {
		DefaultSelenium selenium = getSeleniumClient("dpURL");
		selenium.start();
//
//		selenium.click("link=JVM");
//		selenium.waitForPageToLoad("30000");
		return selenium;
	}
	
	// *************  Temp  *************************************
	protected void testAXRestAPI(WebDriver driver) {
		// Curl command: curl -k -o c:\curl\getProjectsList.xml --user
		// ACLQA\g1_admin:Password00
		// https://WIN2012-3.ACLQA.local:8443/aclax/api/projects?scope=working
		String response = "No response?";
		String domain = "win2012-3.aclqa.local";
		// domain = "192.168.10.68";
		String[] scope = { "working", "library", "", "working", "library", };
		String[] url = { "https://" + domain + ":8443/aclax/api/projects",
				"https://" + domain + ":8443/aclax/api/projects",
				"https://" + domain + ":8443/aclax/api/projects",
				"https://" + domain + ":8443/aclax/api/projects",
				"https://" + domain + ":8443/aclax/api/projects" };

		int numTest = 0;
		boolean casAuthenticated = false;

		while (numTest < url.length) {
			if (scope.length >= url.length && scope[numTest] != "")
				url[numTest] += "?scope=" + scope[numTest];
			getPageWithCAS(driver, url[numTest], casAuthenticated);
			System.out.println("中文显示：Get Rest API: " + url[numTest++]);

			response = getPageSource(driver, "UTF-8");
			System.out.println("\t" + response);
			casAuthenticated = true;
		}
		driver.quit();
	}

	private String getPageSource(WebDriver driver, String encode) {
		String response = driver.getPageSource();
		// WebClient wc = driver.getWeb
		return response;
	}

	private void getPageWithCAS(WebDriver driver, String url,
			boolean casAuthenticated) {
		//
		String user = "ACLQA\\g1_admin";
		String pass = "Password00";
		driver.get(url);
		if (!casAuthenticated) {
			cas_sso(driver, user, pass);
		}
	}

	private void cas_sso(WebDriver driver, String user, String pass) {
		try {
			// WebElement form = driver.findElement(By.id("id1"));
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(user);
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(pass);

			WebElement submit = driver.findElement(By
					.xpath("//input[@name='submit']"));

			submit.click();

		} catch (Exception e) {
			System.out.println("No CAS login page found");
			e.printStackTrace();
		}

	}
	
}