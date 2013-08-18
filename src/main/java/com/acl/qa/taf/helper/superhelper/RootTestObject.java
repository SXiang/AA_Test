package com.acl.qa.taf.helper.superhelper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class RootTestObject {
	
	private static WebDriver driver;

	public static WebDriver getDriver() {
		if(driver==null){
			System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
			  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			  driver = new InternetExplorerDriver(ieCapabilities);
			 // driver = new HtmlUnitDriver();
			  //driver = new ChromeDriver();
			//driver = new FirefoxDriver();
			  Dimension windowMinSize = new Dimension(100,100);
			  //driver.manage().window().setSize(windowMinSize);
			  ((JavascriptExecutor) driver).executeScript("if(window.screen){window.moveTo(0, 0);window.resizeTo(window.screen.availWidth, window.screen.availHeight);};");
			  driver.get("http://www.google.com");
		}
		return driver;
	}

	public static void setDriver(WebDriver driver) {
		RootTestObject.driver = driver;
	}
	

}
