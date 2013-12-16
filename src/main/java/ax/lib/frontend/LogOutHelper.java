package ax.lib.frontend;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

public class LogOutHelper extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>ProjectsListHelper</b>
	 * Generated     : <b>Dec 12, 2013</b>
	 * Description   : ProjectsListHelper
	 * 
	 * @since  Dec 12, 2013
	 * @author Yousef Aichour
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpAXServerName; //@arg AX Server name or IP address
	protected String dpAXServerPort; //@arg AX Server port
	// BEGIN locators of the web elements of Main page
    By settingsLocator = By.cssSelector("ul.nav > li:nth-of-type(3)");
    By logOutLocator = By.cssSelector("a[href='/aclax/logout']");
    By successfulLogoutLocator = By.cssSelector("div > p");
	//END
    

	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		dpAXServerName = projectConf.axServerName;
		dpAXServerPort = projectConf.axServerPort;
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public void logout() {	
		isElementEnabled(settingsLocator,"Settings Link");	
		driver.findElement(settingsLocator).click();
		isElementEnabled(logOutLocator,"Logout Link");
		driver.findElement(logOutLocator).click();
		isElementEnabled(successfulLogoutLocator, "Successful Logout");
		compareTxtResult(driver.findElement(successfulLogoutLocator).getText(), dpMasterFiles[0]);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://"+dpAXServerName+":"+dpAXServerPort+"/aclax");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	
    }

}
