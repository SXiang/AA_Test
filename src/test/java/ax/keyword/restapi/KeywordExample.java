/**
 * 
 */
package ax.keyword.restapi;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.UTF8Control;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.*;

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

	public boolean dataInitialization() {
		delFile = false;     // if we need to delete existing file before test
		
		super.dataInitialization();
				
		dpURL = getDpString("URL");
		dpScope = getDpString("Scope");
		
		return true;
	}

	public void testMain(Object[] args){
		super.testMain(args);
		navigateToURL();
		casLogon();
		doVerification();
		cleanUp(dpURL);
	}
	
	public void navigateToURL(){
		String url = dpURL;
		if(!dpScope.equals("")){
			url += "?scope="+dpScope;
		}
		driver.get(url);
	}
	
	public void casLogon(){
		if(casAuthenticated)
			return;
		
		try {
			// WebElement form = driver.findElement(By.id("id1"));
			WebElement username = driver.findElement(By.id("username"));
			username.sendKeys(dpUserName);
			WebElement password = driver.findElement(By.id("password"));
			password.sendKeys(dpPassword);

			WebElement submit = driver.findElement(By
					.xpath("//input[@name='submit']"));

			submit.click();
            casAuthenticated = true;
            setSharedObj();
		} catch (Exception e) {
			logTAFError("No CAS login page found "+e.toString());
		}
	}
	
	
	public void doVerification(){
		setupTestFiles();  
		String actualResult = UTF8Control.utf8decode(driver.getPageSource());		
		compareTextResult(actualResult, "File");
		
	}
    
	
	// **** Required for keyword **************
	public void onInitialize(Object[] args) {	
		if(args.length<3){
			logTAFWarning("Failed to load test data?");
			return;
		}
		dpw = (HSSFRow) args[2];
		dph = (ArrayList<String>) args[1];
		//datapool = (HSSFSheet) args[0];		
		dataInitialization();
		setScriptName(getClass().getName());
		testMain(args);
	}

	public static void main(String[] args) {

	}

}
