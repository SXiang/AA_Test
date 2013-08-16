package ax.lib;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
	private static String nodeUrl;
	
	//*********************** Shared Variables to be set in conf *******************
	private static String driverPath = ReadProperties.getDriverPath();
	private static String nodeName = ReadProperties.getNodeName();
	private static String nodePort = ReadProperties.getNodePort();
	private static String executionType = ReadProperties.getExecutionType();
	// ********** end
	
	public static void launchBrowser(String browserType) {
		
		if(executionType.equalsIgnoreCase("node")){
			nodeUrl = "http://"+nodeName+":"+nodePort+"/wd/hub";
			try {
				remoteURL = new URL(nodeUrl);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			if(browserType.equalsIgnoreCase("IE")){
				InitiateIEBrowser();
			}else if(browserType.equalsIgnoreCase("Chrome")){
				InitiateChromeBrowser();
			}else{
				//other browser's code
			}
			driver = new RemoteWebDriver(remoteURL, capability);
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}else if(executionType.equalsIgnoreCase("local")){
			if(browserType.equalsIgnoreCase("IE")){
				InitiateIEBrowser();
				driver = new InternetExplorerDriver(capability);
			}else if(browserType.equalsIgnoreCase("Chrome")){
				InitiateChromeBrowser();
				driver = new ChromeDriver(capability);
			}else{
				//other browser's code
			}
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}else{
			System.out.println("Incorrect execution type, neither node nor local");
		}
	}
	
	private static void InitiateIEBrowser(){
		System.setProperty("webdriver.ie.driver", driverPath+"IEDriverServer.exe");
		capability = DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setBrowserName("internetExplorer");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
	}
	private static void InitiateChromeBrowser(){
		System.setProperty("webdriver.chrome.driver", driverPath+"chromedriver.exe");
		capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
	}

}
