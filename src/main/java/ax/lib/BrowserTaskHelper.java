package ax.lib;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserTaskHelper {
	
	/**
	 * Script Name   : <b>BrowserTaskHelper</b>
	 * Generated     : <b>Aug 12, 2013</b>
	 * Description   : BrowserTaskHelper
	 * 
	 * @since  Aug 12, 2013
	 * @author Ramneet Kaur
	 */

	private static URL remoteURL;
	protected static WebDriver driver;
	private static DesiredCapabilities capability;
	
	//*********************** Shared Variables to be set in conf *******************
	private static String driverPath = "C:\\Selenium\\";
	private static String nodeName = "ramneet-win7-32.acl.com";
	private static String nodePort = "5555";
	// ********** end
	
	public static void launchBrowser(String browserType) {
		try {
			String nodeUrl = "http://"+nodeName+":"+nodePort+"/wd/hub";
			remoteURL = new URL(nodeUrl);
			} catch (java.lang.Exception e) {
				e.printStackTrace();
			}
		if(browserType.equalsIgnoreCase("IE")){
			InitiateIEBrowser();
			driver = new RemoteWebDriver(remoteURL, capability);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}else{
			//other browser's code
		}
	}
	
	private static void InitiateIEBrowser(){
		System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
		capability = DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setBrowserName("internetExplorer");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
	} 

}
