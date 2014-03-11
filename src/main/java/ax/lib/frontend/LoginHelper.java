package ax.lib.frontend;

/*Commented code is for running using Selenium grid
import java.net.URL;
import org.openqa.selenium.remote.RemoteWebDriver;
*/

import java.awt.Toolkit;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.acl.qa.taf.util.URLSnapshot;
import com.acl.qa.taf.util.UTF8Control;
import com.gargoylesoftware.htmlunit.BrowserVersion;

//import ax.lib.util.FileUtil;

public class LoginHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>LoginHelper</b>
	 * Generated     : <b>Sep 4, 2013</b>
	 * Description   : LoginHelper
	 * 
	 * @since  Sep 4, 2013
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
	                              //@value = HtmlUnit|Firefox|IE|Chrome
	protected String dpAXServerName; //@arg AX Server name or IP address
	protected String dpAXServerPort; //@arg AX Server port
	
	/*Commented code is for running using Selenium grid
	protected String dpDriverPath; //@arg Absolute path to Selenium IE/Chrome driver executable
                                   //@value = C:\Selenium\
	protected String dpNodeName; //@arg machine or IP address of the machine where want to run the test
	protected String dpNodePort; //@arg port of the machine on which Selenium node is running
	protected String dpExecutionType; //@arg whether want to run in hub-node mode or local
                                //@value = node|local
    */
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of login page
	By usernameLocator = By.id("username");
    By passwordLocator = By.id("password");
    By loginButtonLocator = By.name("submit");
	//END
    
    // BEGIN of other local variables declaration
	private DesiredCapabilities capability;
	private FirefoxProfile profile;	
	 
	public String imageName;
	//END
	/*Commented code is for running using Selenium grid
	private String nodeUrl;
	private URL remoteURL;
	*/
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		//getSharedObj();
		super.dataInitialization();
		/* Commented code is for running using Selenium grid
		dpDriverPath = projectConf.getDriverPath();
		dpNodeName = projectConf.getNodeName();
		dpNodePort = projectConf.getNodePort();
		dpExecutionType = projectConf.getExecutionType();
		*/
		dpWebDriver = projectConf.webDriver;
		dpAXServerName = projectConf.axServerName;
		dpAXServerPort = projectConf.axServerPort;
		imageName = projectConf.imageName;
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
		
		//String comm = "java -jar selenium-server-standalone-2.34.0.jar "+
		 //        "-role hub -Duser.language=en -Duser.region=US ";
        
		//FileUtil.exeComm(comm);
		
		launchBrowser();
	}
	
	public void launchBrowser() {
		if (dpWebDriver.equals("")) {
			logTAFError("Not able to read from project.properties the correct value of variable 'webDriver'. Please check the file.");
		} else {
			setupNewDriver(dpWebDriver);
		}
	}
	
	public void setupNewDriver(String browserType) {
		// Commented code is for running using Selenium grid
		/*
		if(dpExecutionType.equalsIgnoreCase("node")){
			nodeUrl = "http://"+dpNodeName+":"+dpNodePort+"/wd/hub";
			try {
				remoteURL = new URL(nodeUrl);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			if(browserType.equalsIgnoreCase("IE")){
				logTAFStep("Recognized IE browser, about to intiate...");
				//String comm = "java -jar selenium-server-standalone-2.34.0.jar "+
				//         "-role node -hub http://localhost:4444/grid/register "+
				//		"-Dwebdriver.ie.driver="+projectConf.toolDir+
				//		"IEDriverServer.exe";
				//FileUtil.exeComm(comm);
				InitiateIEBrowser();
			}else if(browserType.equalsIgnoreCase("Chrome")){
				logTAFStep("Recognized Chrome browser, about to intiate...");
				InitiateChromeBrowser();
			}else{
				logTAFStep("Recognized other browser, about to intiate...");
				//other browser's code
			}
			driver = new RemoteWebDriver(remoteURL, capability);
		}else{
			if(browserType.equalsIgnoreCase("IE")){
				logTAFStep("Recognized IE browser, about to intiate...");
				InitiateIEBrowser();
				driver = new InternetExplorerDriver(capability);
			}else if(browserType.equalsIgnoreCase("Chrome")){
				logTAFStep("Recognized Chrome browser, about to intiate...");
				InitiateChromeBrowser();
				driver = new ChromeDriver(capability);
				//driver = new ChromeDriver();
			}else{
					//other browser's code
			}
		}
		*/
		// Commented code is for running using Selenium standalone
		
		
		if((browserType.startsWith("IE")) || (browserType.startsWith("ie"))){
			logTAFStep("Recognized IE browser, about to intiate...");
			InitiateIEBrowser();
			/*String comm = "java -jar selenium-server-standalone-2.34.0.jar "+
				         "-role node -hub http://localhost:4444/grid/register "+
						"-Dwebdriver.ie.driver="+projectConf.toolDir+
						"IEDriverServer.exe";
				FileUtil.exeComm(comm);
			*/
			driver = new InternetExplorerDriver(capability);
		}else if((browserType.startsWith("Chrome"))||(browserType.startsWith("chrome"))){
			logTAFStep("Recognized Chrome browser, about to intiate...");
			InitiateChromeBrowser();

			    driver = new ChromeDriver(capability);


			//driver = new ChromeDriver();
		}else if(browserType.equalsIgnoreCase("FireFox")) {
			InitiateFirefoxBrowser();
			driver = new FirefoxDriver(profile);
			
		}else {
				//other browser's code  -- Steven debugging ...
			if (browserType.equalsIgnoreCase("HtmlUnit")) {
				driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
				imageName = "";
			}else {
				System.setProperty("webdriver.ie.driver", projectConf.toolDir+"IEDriverServer32.exe");
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				driver = new InternetExplorerDriver(ieCapabilities);
				imageName = "iexploere.exe";
			}
			
		}
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//projectConf.buildInfo = getAXBuildNumber(driver);
		URLSnapshot.snapDriver = driver;
		
		driver.get("https://"+dpAXServerName+":"+dpAXServerPort+"/aclax");
//		Toolkit toolkit = Toolkit.getDefaultToolkit();
//	    Dimension screenResolution = new Dimension((int)
//	    toolkit.getScreenSize().getWidth(), (int)
//	    toolkit.getScreenSize().getHeight());
//	    driver.manage().window().setPosition(new Point(0, 0));
//		driver.manage().window().setSize(screenResolution);
		driver.manage().window().maximize();
		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}
	
	public String getAXBuildNumber(WebDriver axDriver){
		String buildName = projectConf.buildInfo;
		String numFormat = ".*build\\.number=([0-9])+.*";
		if(numFormat.contains("<"))
			return buildName;
		try{
		    axDriver.get("https://"+dpAXServerName+":"+dpAXServerPort+"/auditexchange/version");
		    String text = UTF8Control.utf8decode(driver.getPageSource());
		    buildName = text.replaceFirst(numFormat, "$1");
		}catch(Exception e){
			
		}
		if(buildName.equals("")){
			buildName = projectConf.buildInfo;
		}
		return buildName;
		
	}
	public void InitiateIEBrowser(){
		// Commented code is for running using Selenium grid
		System.setProperty("webdriver.ie.driver", projectConf.toolDir+"IEDriverServer.exe");
		capability = DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setBrowserName("internetExplorer");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
	}
	
	public void InitiateFirefoxBrowser(){
		// Commented code is for running using Selenium grid	
		profile = new FirefoxProfile();
		profile.setPreference("intl.accept_languages", projectConf.appLocale.toLowerCase());

	}
	public void InitiateChromeBrowser(){
		// Commented code is for running using Selenium grid
		System.setProperty("webdriver.chrome.driver", projectConf.toolDir+"chromedriver.exe");
		capability = DesiredCapabilities.chrome();
		
		File defaultExt = new File(projectConf.toolDir+"0.7_0.crx");
		if(defaultExt.exists()){
		  ChromeOptions options = new ChromeOptions();
		  options.addExtensions(defaultExt);
		  options.addArguments("--lang="+projectConf.appLocale.toLowerCase());
		  capability.setCapability(ChromeOptions.CAPABILITY, options);
		}
		
		
		capability.setBrowserName("chrome");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		capability.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
		capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
		//capability.setCapability("chrome.switches", Arrays.asList("--lang"+projectConf.appLocale));
		//capability.setCapability("chrome.switches", Arrays.asList("--no-sandbox"));
	}

	
	//***************  Part 3  *******************
	// ******* Methods on login   ****************
	// *******************************************
	
	public void passCertWarning() {
		driver.get("javascript:document.getElementById('overridelink').click();");
		if(driver.getTitle().startsWith("Certificate Error")){
			passCertWarning();
		}
	}
	public void login(String username, String password, String casType) {
		if(driver.getTitle().startsWith("Certificate Error")){
			passCertWarning();
		}
		isElementEnabled(usernameLocator,"Username field");
		isElementEnabled(passwordLocator,"Password field");
		isElementEnabled(loginButtonLocator,"Login Button");
		takeScreenshotWithoutScroll();
		if(casType.equalsIgnoreCase("nonSSO")){
			sleep(1);
			driver.findElement(usernameLocator).click();
			driver.findElement(usernameLocator).sendKeys(username);
	        driver.findElement(passwordLocator).click();
	        driver.findElement(passwordLocator).sendKeys(password);
	        //driver.findElement(loginButtonLocator).click();
	        driver.findElement(usernameLocator).submit();
	        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		logTAFStep(driver.getTitle());
		
		isUserLoggedIn();
    }
	
	//***************  Part 4  *******************
	// ******* Methods on verification ***********
	// *******************************************
	
	public boolean isUserLoggedIn() {
		sleep(3);
		boolean done = false;
		if(!driver.getTitle().contentEquals("ACL Analytics Exchange")){
			logTAFError(dpExpectedErr);
		}
        return done;   
    }

}
