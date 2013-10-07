package ax.keyword.restapi;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BrowserTaskHelper {
	
	public static Capabilities browserCapabilities; 
	public static URL remoteURL;
	public static WebDriver driver;
	
	public static void main(String args[]){

			System.out.println("inside if section");
			System.setProperty("webdriver.ie.driver", "C:\\Selenium\\IEDriverServer.exe");
			DesiredCapabilities browserCapabilities = DesiredCapabilities.internetExplorer();
			browserCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		try {
			String nodeUrl = "http://"+"ramneet-win7-32.acl.com"+":"+"5555"+"/wd/hub";
			remoteURL = new URL(nodeUrl);
			System.out.println("created url driver");
			} catch (java.lang.Exception e) {
				System.out.println("received exception");
				e.printStackTrace();
			}
			System.out.println("about to create driver");
			driver = new RemoteWebDriver(remoteURL, browserCapabilities);
			System.out.println("received driver");
			//return driver;
		
	} 


}
