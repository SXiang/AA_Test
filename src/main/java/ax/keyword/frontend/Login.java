package ax.keyword.frontend;

import pageObjects.LoginPage;
import ax.lib.BrowserTaskHelper;

public class Login extends BrowserTaskHelper{
	/**
	 * Script Name   : <b>Login</b>
	 * Generated     : <b>Aug 13, 2013</b>
	 * Description   : Login keyword
	 * 
	 * @author Ramneet Kaur
	 */

	//*********************** Shared Variables to be set in conf *******************
	private String server = "win2012-3.aclqa.local";
	private String port = "8443";
	private String browserType = "IE";
	//end		

	public void testLogin(){
		BrowserTaskHelper.launchBrowser(browserType);
	
		String url = "https://"+server+":"+port+"/aclax";
		
		LoginPage objLoginPage = new LoginPage(driver);
		driver.get(url);
		
		assert driver.getTitle().contentEquals("ACL Analytics Exchange Login");
		
		objLoginPage.login();
		
		assert driver.getTitle().contentEquals("ACL Analytics Exchange");
	}
	
	
	
}
