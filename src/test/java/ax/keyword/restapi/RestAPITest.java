package ax.keyword.restapi;
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
        
        rait.testAXRestAPI(driver);
       // driver.quit();
    }


   private void testAXRestAPI(WebDriver driver){
	   //Curl command:   curl -k -o c:\curl\getProjectsList.xml --user ACLQA\g1_admin:Password00 https://WIN2012-3.ACLQA.local:8443/aclax/api/projects?scope=working
	   String response = "No response?";
	   String domain = "win2012-3.aclqa.local";
	          //domain = "192.168.10.68";
	   String[] scope = {
			   "working",
			   "library",
			   "",
			   "working",
			   "library",
	   };
	   String[] url={"https://"+domain+":8443/aclax/api/projects",
			         "https://"+domain+":8443/aclax/api/projects",
			         "https://"+domain+":8443/aclax/api/projects",
					 "https://"+domain+":8443/aclax/api/projects",
					 "https://"+domain+":8443/aclax/api/projects"};


	   int numTest=0;
	   boolean casAuthenticated = false;


	   while(numTest<url.length){
		  if(scope.length>=url.length&&scope[numTest]!="")
			  url[numTest] += "?scope="+scope[numTest];
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
	   try{
		// WebElement form = driver.findElement(By.id("id1"));
	     WebElement username = driver.findElement(By.id("username"));
	     username.sendKeys(user);
	     WebElement password = driver.findElement(By.id("password"));
	     password.sendKeys(pass);




	      WebElement submit = driver.findElement(By.xpath("//input[@name='submit']"));	      


          submit.click();


	   }catch(Exception e){
		  System.out.println("No CAS login page found");
		  e.printStackTrace();
	   }


   }
}

