package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage{
	
	/**
	 * Script Name   : <b>Login</b>
	 * Generated     : <b>Aug 12, 2013</b>
	 * Description   : Login to ACLAX
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
	private static String dpUsername = "g1_admin";	
	private static String dpPassword = "Password00";	
	 // END of datapool variables declaration	
	
	public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

	public LoginPage enterCredentials(String username, String password) {
        driver.findElement(usernameLocator).sendKeys(username);
        driver.findElement(passwordLocator).sendKeys(password);
        return this;    
    }

	public ProjectsListPage submitLogin() {
        driver.findElement(loginButtonLocator).click();
        return new ProjectsListPage(driver);    
    }
	
	public ProjectsListPage login() {
		enterCredentials(dpUsername, dpPassword);
        return submitLogin();
    }
	
	
}