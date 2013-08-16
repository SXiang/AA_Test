package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ax.lib.ReadDatapool;

public class LoginPage{
	
	/**
	 * Script Name   : <b>LoginPage</b>
	 * Generated     : <b>Aug 12, 2013</b>
	 * Description   : LoginPage
	 * 
	 * @since  Aug 12, 2013
	 * @author Ramneet Kaur
	 */

	// Local variables
	private final WebDriver driver;
	//end
	
	// locators of the web elements of login page
	static By usernameLocator = By.id("username");
    static By passwordLocator = By.id("password");
    static By loginButtonLocator = By.name("submit");
	//end
	
	// BEGIN of datapool variables declaration   
	private static String dpUsername = ReadDatapool.getDpUsername();	
	private static String dpPassword = ReadDatapool.getDpPassword();	
	 // END of datapool variables declaration	
	
	public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

	public LoginPage enterCredentials(String username, String password) {
        driver.findElement(usernameLocator).sendKeys(username);
        driver.findElement(passwordLocator).click();
        driver.findElement(passwordLocator).sendKeys(password);
        return this;    
    }
	
	public boolean isUsernamePresent() {
        return driver.findElement(usernameLocator).isEnabled(); 
    }
	
	public boolean isPasswordPresent() {
        return driver.findElement(passwordLocator).isEnabled();    
    }
	
	public boolean isLoginBtnPresent() {
        return driver.findElement(loginButtonLocator).isEnabled();   
    }

	public ProjectsListPage submitLogin() {
        driver.findElement(loginButtonLocator).click();
        return new ProjectsListPage(driver);    
    }
	
	public ProjectsListPage login() {
		enterCredentials(dpUsername, dpPassword);
        return submitLogin();
    }

	public ProjectsListPage loginSSO() {
        return new ProjectsListPage(driver);
    }
	
	public LoginPage passCertWarning() {
		driver.get("javascript:document.getElementById('overridelink').click();");
		return this;
	}
	
}