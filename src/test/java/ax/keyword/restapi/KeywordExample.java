/**
 * 
 */
package ax.keyword.restapi;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.UTF8Control;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ax.lib.KeywordHelper;

/**
 * Script Name :KeywordExample.java Generated :3:10:31 PM Description : ACL Test
 * Automation
 * 
 * @since Aug 16, 2013
 * @author steven_xiang
 * 
 */
public class KeywordExample extends KeywordHelper implements KeywordInterface {

	/**
	 * @param args
	 */

	// BEGIN of datapool variables declaration
	protected String dpURL; // @arg the url to be tested
	protected String dpScope; // @arg scope of the project
								// @value = working|libray, or empty

	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
				
		dpURL = getDpString("URL");
		dpScope = getDpString("Scope");		
		return true;
	}

	@Override
	public void testMain(Object[] args){			
		super.testMain(onInitialize(args, getClass().getName()));
		
		//*** Test steps ****
		navigateToURL();
		casLogon();
		doVerification();
		cleanUp(dpURL);
		
		//*** End of test ***
		
		onTerminate();
	}
	
	public void navigateToURL(){
		String url = dpURL;
		if(!dpScope.equals("")){
			url += "?scope="+dpScope;
		}
		logTAFStep("Navigate to url '"+url+"'");
		driver.get(url);
	}
	
	public boolean isUserLoggedon(){
		boolean done = false;
		if(driver!=null){
			try{
				driver.findElement(By.id("username"));
			
			    driver.findElement(By.id("password"));
			    
	            casAuthenticated = false;
	            setSharedObj();
			}catch(Exception e){
				casAuthenticated = true;
	            setSharedObj();
				done = true;
			}
		}
			
		return done;
	}
	public void casLogon(){
		if(casAuthenticated){
			if(!isUserLoggedon()){
			  logTAFError(autoIssue+"User CAS session experied or server problem?");
			}else{
			  logTAFInfo("CAS authenticated session is still alive");
			}
			return;
		}
		try {
			// WebElement form = driver.findElement(By.id("id1"));
			logTAFStep("Input username '"+dpUserName+"'");
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(dpUserName);
			logTAFStep("Input password '"+dpPassword+"'");
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(dpPassword);

			WebElement submit = driver.findElement(By
					.xpath("//input[@name='submit']"));
            logTAFStep("Submit user credential");
			submit.click();
			// How to wait for page load?
			sleep(2);
		} catch (Exception e) {
			logTAFError("CAS login failed "+e.toString());
		}
		
		if(isUserLoggedon()){
			logTAFInfo("User CAS logon successed!");
		}else{
			logTAFError("Failed to logon - "+dpUserName+":"+dpPassword);
		}

	}
	
	
	public void doVerification(){
		setupTestFiles();  

		String actualResult = UTF8Control.utf8decode(driver.getPageSource());
		if(casAuthenticated){
			logTAFInfo("JSON data: '"+actualResult.substring(0,100)+"...");
			// comparTextReult
			// 1. contents - string
			// 2. type - "File","JSON",
			logTAFStep("Demo - first file verification");
			compareJsonResult(actualResult);
			// Do another for demo - multiple comparisons - Steven
			logTAFStep("Demo - second file verification");
			compareJsonResult(actualResult);
		}else{
			actualResult = (actualResult.substring(0,300))+"\n\t...";
			
			logTAFWarning("This should not be what we want '"+actualResult+"'"	);
		}

		
	}

   // ************  Debugging ************************
	public static void main(String args){
		KeywordExample debug = new KeywordExample();
		debug.startBrowser("HtmlUnit");
		debug.testAXRestAPI(debug.driver);
	}
}

