package ax.keyword.frontend;

import pageObjects.LoginPage;
import ax.lib.BrowserTaskHelper;
import ax.lib.ReadProperties;

public class Login extends BrowserTaskHelper{
	/**
	 * Script Name   : <b>Login</b>
	 * Generated     : <b>Aug 13, 2013</b>
	 * Description   : Login keyword
	 * 
	 * @author Ramneet Kaur
	 */

	private static LoginPage objLoginPage = null;
	
	//*********************** Shared Variables to be set in conf *******************
	private static String server = ReadProperties.getServer();
	private static String port = ReadProperties.getPort();
	private static String browserType = ReadProperties.getBrowserType();
	private static String casType = ReadProperties.getCasType();
	//end		

	public static void testLogin(){
		if(objLoginPage == null){
			prepPage();
		}
		if(!"SSO".equalsIgnoreCase(casType)){
			objLoginPage.login();
		}else{
			objLoginPage.loginSSO();
		}
	}
	
	public static void allElementsPresent(){
		if(objLoginPage == null){
			prepPage();
		}
		if(!objLoginPage.isUsernamePresent()){
			System.out.println("Username textBox NOT present!!!");
		}else{
			System.out.println("Username textBox present!!!");
		}
		if(!objLoginPage.isPasswordPresent()){
			System.out.println("Password textBox NOT present!!!");
		}else{
			System.out.println("Password textBox present!!!");
		}
		if(!objLoginPage.isLoginBtnPresent()){
			System.out.println("Login Button NOT present!!!");
		}else{
			System.out.println("Login Button present!!!");
		}
	}
	
	private static LoginPage prepPage(){
		BrowserTaskHelper.launchBrowser(browserType);
		objLoginPage = new LoginPage(driver);
		String url = "https://"+server+":"+port+"/aclax";
		driver.get(url);
		if(driver.getTitle().startsWith("Certificate Error")){
			objLoginPage.passCertWarning();
		}
		return objLoginPage;
	}
}
