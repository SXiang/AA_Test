package ax.lib.frontend;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

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
	protected String dpDriverPath; //@arg Absolute path to Selenium IE/Chrome driver executable
                                   //@value = C:\Selenium\
	protected String dpNodeName; //@arg machine or IP address of the machine where want to run the test
	protected String dpNodePort; //@arg port of the machine on which Selenium node is running
	protected String dpExecutionType; //@arg whether want to run in hub-node mode or local
                                //@value = node|local
	protected String dpAXServerName; //@arg AX Server name or IP address
	protected String dpAXServerPort; //@arg AX Server port
	// END of datapool variables declaration

	// BEGIN locators of the web elements of login page
	By usernameLocator = By.id("username");
    By passwordLocator = By.id("password");
    By loginButtonLocator = By.name("submit");
	//END
    
    // BEGIN of other local variables declaration
	private URL remoteURL;
	private DesiredCapabilities capability;
	private String nodeUrl;
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		dpWebDriver = getDpString("WebDriver");
		dpDriverPath = getDpString("DriverPath");
		dpNodeName = getDpString("NodeName");
		dpNodePort = getDpString("NodePort");
		dpExecutionType = getDpString("ExecutionType");
		dpAXServerName = getDpString("AXServerName");
		dpAXServerPort = getDpString("AXServerPort");
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
		launchBrowser();
	}
	
	public void launchBrowser() {
		if (dpWebDriver.equals("")) {
			logTAFError("Web Driver datapool entry missing");
		} else {
			setupNewDriver(dpWebDriver);
		}
	}
	
	public void setupNewDriver(String browserType) {
		
		if(dpExecutionType.equalsIgnoreCase("node")){
			nodeUrl = "http://"+dpNodeName+":"+dpNodePort+"/wd/hub";
			try {
				remoteURL = new URL(nodeUrl);
				} catch (java.lang.Exception e) {
					e.printStackTrace();
				}
			if(browserType.equalsIgnoreCase("IE")){
				logTAFStep("Recognized IE browser, about to intiate...");
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
			}else{
					//other browser's code
			}
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://"+dpAXServerName+":"+dpAXServerPort+"/aclax");
		setSharedObj();
		logTAFStep("Browser initiated successfully");
	}
	
	public void InitiateIEBrowser(){
		System.setProperty("webdriver.ie.driver", dpDriverPath+"IEDriverServer.exe");
		capability = DesiredCapabilities.internetExplorer();
		capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		capability.setBrowserName("internetExplorer");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		imageName = "iexploere.exe";
	}
	public void InitiateChromeBrowser(){
		System.setProperty("webdriver.chrome.driver", dpDriverPath+"chromedriver.exe");
		capability = DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(org.openqa.selenium.Platform.ANY);
		capability.setCapability("chrome.switches", Arrays.asList("--ignore-certificate-errors"));
		imageName = "chrome.exe";
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
		isElementEnabled(usernameLocator);
		isElementEnabled(passwordLocator);
		isElementEnabled(loginButtonLocator);
		if(casType.equalsIgnoreCase("nonSSO")){
			driver.findElement(usernameLocator).sendKeys(username);
	        driver.findElement(passwordLocator).click();
	        driver.findElement(passwordLocator).sendKeys(password);
	        driver.findElement(loginButtonLocator).click();
		}
		isUserLoggedIn();
    }
	
	//***************  Part 4  *******************
	// ******* Methods on verification ***********
	// *******************************************
	
	public boolean isElementEnabled(By locator) {
		boolean done = false;
		try{
			done = driver.findElement(locator).isEnabled();
			logTAFStep("Successfully found '"+locator+"'");
		}catch(Exception e){
			logTAFError("Failed to find '"+locator+"' !!!");
		}
        return done;
    }
	
	public boolean isUserLoggedIn() {
		sleep(3);
		boolean done = false;
		if(!driver.getTitle().contentEquals("ACL Analytics Exchange")){
			logTAFError(dpExpectedErr);
		}
        return done;   
    }

}
