package com.acl.qa.ax.tasks.restapi;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.gargoylesoftware.htmlunit.*;


public class RestAPITest  {
    public static void main(String[] args) {
        RestAPITest rait = new RestAPITest();
    	
        WebDriver driver;
         
        driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8);
//        driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8) {
//            protected WebClient modifyWebClient(WebClient client) {
//                client.setWebConnection(new
//                ModifyHostConnection(client.getWebConnection()));
//                return client;
//            }
//        };
       // driver = new FirefoxDriver();
        //driver = rait.setCredential("ACLQA\\g1_admin","Password00");
       // driver = new ChromeDriver();
        //DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
        //ieCapabilities.setCapability(
        //		InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        //driver = new InternetExplorerDriver(ieCapabilities);
        
        rait.testAXRestAPI(driver);
       // driver.quit();
    }

   private void testAXRestAPI(WebDriver driver){
	   //Curl command:   curl -k -o c:\curl\getProjectsList.xml --user ACLQA\g1_admin:Password00 https://WIN2012-3.ACLQA.local:8443/aclax/api/projects?scope=working
	   String response = "No response?";
	   String domain = "win2012-3.aclqa.local";
	          //domain = "192.168.10.68";
	   String[] url={"https://"+domain+":8443/aclax/api/projects",
			         "https://"+domain+":8443/aclax/api/projects",
			         "https://"+domain+":8443/aclax/api/projects",
					 "https://"+domain+":8443/aclax/api/projects",
					 "https://"+domain+":8443/aclax/api/projects"};
	   
	   int numTest=0;
	   boolean casAuthenticated = false;
	   while(numTest<url.length){
		  
	      getPageWithCAS(driver,url[numTest],casAuthenticated);	
	      System.out.println("中文显示：Get Rest API: "+url[numTest++]);
	      
          response = getPageSource( driver,"UTF-8");
	      System.out.println("\t"+response);
	      casAuthenticated = true;
	   }
	  driver.quit();
   }
   private String getPageSource(WebDriver driver,String encode){
	   String response = driver.getPageSource();
	   //WebClient wc = driver.getWeb
	   return response;
   }
   private void getPageWithCAS(WebDriver driver,String url,boolean casAuthenticated){
	   //
	   String user="ACLQA\\g1_admin";
	   String pass="Password00";
	   driver.get(url);
	   if(!casAuthenticated){
		   cas_sso(driver,user,pass);
	   }
   }
 
   
   
   
   private void cas_sso(WebDriver driver,String user,String pass){
//	   try{
//		 // driver.findElement(By.xpath("//a[@id='overridelink]")).click();
//		 // driver.findElement(By.id("overridelink")).click();
//		 // WebElement cont = driver.findElement(By.id("continueToSite"));
//		//  cont = cont.findElement(By.id("overridelink"));
//	     WebElement cont = driver.findElement(By.id("overridelink"));
//	     // WebElement cont = driver.findElement(By.partialLinkText("Continue to this website"));
//	     cont.click();
//	     // driver.navigate().to("javascript:document.getElementById('overridelink').click()");  
//
//
//	   }catch(Exception e){
//		  // System.out.println("No overridelink found");
//		  // e.printStackTrace();
//		  // System.out.println(driver.getPageSource());
//	   }
	   try{
		// WebElement form = driver.findElement(By.id("id1"));
	     WebElement username = driver.findElement(By.id("username"));
	     username.sendKeys(user);
	     WebElement password = driver.findElement(By.id("password"));
	     password.sendKeys(pass);
	     
	      //WebElement submit = driver.findElement(By.name("submit"));
	      WebElement submit = driver.findElement(By.xpath("//input[@name='submit']"));	      
	      //submit.sendKeys("");
          //submit.sendKeys(Keys.ENTER);
          //submit.sendKeys(Keys.ENTER);
          submit.click();
          //submit = driver.findElement(By.xpath("//input[@name='submit']"));
	      
          //submit.sendKeys(Keys.ENTER);
	      //driver.get(url);
	      //driver.findElement(By.name("submit")).click();
          
	   }catch(Exception e){
		  System.out.println("No CAS login page found");
		  e.printStackTrace();
	   }
	   
   }
   private WebDriver setCredential(final String user, final String pass){
	   return new HtmlUnitDriver() {
           protected WebClient modifyWebClient(WebClient client) {
               // This class ships with HtmlUnit itself
               DefaultCredentialsProvider creds = new DefaultCredentialsProvider();

               // Set some credentials
               creds.addCredentials(user, pass);

               // And now add the provider to the webClient instance
               client.setCredentialsProvider(creds);
               
               client.setRedirectEnabled(true);
               try {
					client.setUseInsecureSSL(true);
				} catch (GeneralSecurityException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				} 

               return client;
           }
       };
   }
}

//
//    public static void main(String[] args) {
//        // Create a new instance of the Firefox driver
//        // Notice that the remainder of the code relies on the interface, 
//        // not the implementation.
//    	System.out.println("Started .....");
//        WebDriver driver;// = new FirefoxDriver();
//        //driver = new FirefoxDriver();
//        driver = new HtmlUnitDriver(true);
//
//        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
//        //driver = new InternetExplorerDriver(ieCapabilities);
//        // And now use this to visit Google
//        driver.get("http://www.google.com");
//        // Alternatively the same thing can be done like this
//        // driver.navigate().to("http://www.google.com");
//        
//        // Find the text input element by its name
//        WebElement element = driver.findElement(By.name("q"));
//
//        // Enter something to search for
//        element.sendKeys("Cheese!");
//
//        // Now submit the form. WebDriver will find the form for us from the element
//        element.submit();
//
//        // Check the title of the page
//        System.out.println("Page title is: " + driver.getTitle());
//        
//        // Google's search is rendered dynamically with JavaScript.
//        // Wait for the page to load, timeout after 10 seconds
//        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
//            public Boolean apply(WebDriver d) {
//                return d.getTitle().toLowerCase().startsWith("cheese!");
//            }
//        });
//
//        // Should see: "cheese! - Google Search"
//        System.out.println("Page title is: " + driver.getTitle());
//        
//        //Close the browser
//       // driver.quit();
//    }
//}
//
