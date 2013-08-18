package com.acl.qa.taf.helper;

import java.awt.Point;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.helper.superhelper.ObjectHelper;
import com.acl.qa.taf.util.FileUtil;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  January 05, 2012
 */
public abstract class KeywordSuperHelper extends ObjectHelper
{

	// BEGIN of datapool variables declaration
   

    protected String dpExpectedErr;
    protected String dpKnownBugs;
//	protected String dpCommand;
//	protected String dpEndWith; //@arg Finish or cancel
                                //@VALUE = 'Finish' or 'Cancel', default to Finish
   // END of datapool variables declaration
    protected String 
            dpMasterFile,dpMasterFiles[] = new String[50],
			dpActualFile,dpActualFiles[] = new String[50],
			fileExt = ".vp",
			outputFolder = "result";
    
    protected boolean delFile = false;
    
	public void testMain(Object[] args) 
	{

		// Data-Driven Stub
		dataInit(args); 
		//exeCommands();
	}
	private boolean dataInit(Object[] args){
		boolean done= true;
		//
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
	
	
	//************ Setup file path for data verification ******************
	public String setupTestFiles(){
		return setupTestFiles(scriptName.replaceAll(".", "/"),"","No",fileExt);
	}
	public String setupTestFiles(String filename,String location){
		return setupTestFiles(filename,location,"No",".txt");
	}
	public String setupTestFiles(String filename,String location, String defaultFileExt){
		return setupTestFiles(filename,location,"No",defaultFileExt);
	}
	public String setupTestFiles(String filename,String location, String append, String defaultFileExt){	
		
        return setupTestFiles(filename,location,append,defaultFileExt,"",-1);
	}
	

	public String setupTestFiles(String filename,String location, String append, 
			String defaultFileExt,String mFile, int numFile){	
		
		String tempMasterFile = "",tempActualFile = "", fName = "";
		
        if(filename==null||filename.equals("")){
        	//logTAFWarning("Empty file name");
        	return "";
        }

		String localName = filename;
		superMasterFile = FileUtil.getAbsDir(filename,projectConf.testDataDir);
		superMasterFile = FileUtil.getFullNameWithExt(superMasterFile+"/master/TestCaseLine_"+(currentTestLine-1),defaultFileExt);
        if(location.equalsIgnoreCase("Server")){
        	localName = FileUtil.getAbsDir(filename,projectConf.tempServerNetDir);
    		filename = FileUtil.getAbsDir(filename,projectConf.tempServerDir);
        }else{
    		filename = FileUtil.getAbsDir(filename,loggerConf.logRoot);	
    		localName = filename;    
    	    
    	}
        
        
        //localName = FileUtil.getFullNameWithExt(localName,defaultFileExt);
        tempActualFile = FileUtil.getFullNameWithExt(localName+"/actualdata/TestCaseLine_"+(currentTestLine-1),defaultFileExt);
    	tempMasterFile = FileUtil.getFullNameWithExt(tempMasterFile+"/expecteddata/TestCaseLine_"+(currentTestLine-1),defaultFileExt);
    	   

    	logTAFDebug("DelFile is '"+delFile+"'");
    	FileUtil.mkDirs(tempMasterFile);
    	FileUtil.mkDirs(tempActualFile,delFile);  
 
		if(numFile>-1){
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
        
    	return filename;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// *************  Temp  *************************************
	private void testAXRestAPI(WebDriver driver) {
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
