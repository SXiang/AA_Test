package ax.lib;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import ax.TestSuiteExample;

import com.acl.qa.taf.helper.KeywordSuperHelper;
import com.gargoylesoftware.htmlunit.BrowserVersion;

public class KeywordHelper extends KeywordSuperHelper{
	

	// BEGIN of datapool variables declaration
    protected String dpExpectedErr; //@arg error message for negative test
    protected String dpKnownBugs;   //@arg infomation for known bugs (won't be fixed in this relase)
    protected String dpWebDriver;   //@arg Selenium webdriver type
                                    //@value = HtmlUnit|Firefox|...

    
	protected String dpUserName;  //@arg username for login
	protected String dpPassword;  //@arg password for login
	protected String dpEndWith; //@arg actions after test
                                //@value = logout|quit|kill, or empty
   // END of datapool variables declaration
	
    protected WebDriver driver;
    protected boolean casAuthenticated;
	public boolean dataInitialization() {	
		getSharedObj();

		dpExpectedErr = getDpString("ExpectedErr");
		dpKnownBugs = getDpString("KnownBugs");
		dpUserName = getDpString("UserName");
		dpPassword = getDpString("Password");
		dpWebDriver = getDpString("WebDriver");
		dpEndWith = getDpString("EndWith");
		

		return true;
	}
	
	public void testMain(Object[] args){
		// Do something for all keywords -
		super.testMain(args);
		activateBrowser();
		
	}
	
    public void activateBrowser(){
    	if(dpWebDriver.equals("")){
    		
    	}else{
    		setupNewDriver(dpWebDriver);
    		
    	}
    	
    }
    
    public void setupNewDriver(String Browser){
    	if(Browser.equalsIgnoreCase("HtmlUnit")){
    		driver = new HtmlUnitDriver(BrowserVersion.INTERNET_EXPLORER_8); 
    	}else if(Browser.equalsIgnoreCase("FireFox")){
    		
    	}else if(Browser.equalsIgnoreCase("Chrome")){
    		
    	}
    	casAuthenticated = false;
    	
    	setSharedObj();
    	
    }
    
    public void getSharedObj(){
        if(suiteObj!=null){
		  driver = ((TestSuiteHelper)suiteObj).currentDriver;
		  casAuthenticated = ((TestSuiteHelper)suiteObj).casAuthenticated;
        }else if(caseObj!=null){
          driver = ((TestDriverHelper)caseObj).currentDriver;
  		  casAuthenticated = ((TestDriverHelper)caseObj).casAuthenticated;
        }
    }
    
    public void setSharedObj(){
    	if(suiteObj!=null){
    		  ((TestSuiteHelper)suiteObj).currentDriver = driver;
    		  ((TestSuiteHelper)suiteObj).casAuthenticated = casAuthenticated;
            }else if(caseObj!=null){
              ((TestDriverHelper)caseObj).currentDriver = driver;
      		((TestDriverHelper)caseObj).casAuthenticated = casAuthenticated;
            }
    }
    
	
	public void compareTextResult(String result,String verifyType){
		  if(result==null){
			   return;
		   }
		  compareTextFile(dpMasterFile, dpActualFile, result,
						projectConf.updateMasterFile,verifyType);
	}
	
	public void cleanUp(String url){
		
		if(dpEndWith.equals("quit")){
			driver.quit();
			driver = null;
			casAuthenticated = false;
		}else if(dpEndWith.equals("kill")){
			stopApp();
			driver=null;
			casAuthenticated = false;
		}else if(dpEndWith.equals("logoff")){
			url = url+"/cas/logoff";
			driver.get(url);
			casAuthenticated = false;
		}else{
			return;
		}
		
		setSharedObj();
	}
}
