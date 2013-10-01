package ax.lib.frontend;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import ax.lib.restapi.TestSuiteExampleHelper;

import com.acl.qa.taf.helper.KeywordSuperHelper;

public class FrontendCommonHelper extends KeywordSuperHelper{
	
	/**
	 * Script Name   : <b>FrontendCommonHelper</b>
	 * Generated     : <b>Sep 5, 2013</b>
	 * Description   : FrontendCommonHelper
	 * 
	 * @since  Sep 5, 2013
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************

	// BEGIN of datapool variables declaration
	protected String dpExpectedErr; //@arg error message for negative test
	protected String dpKnownBugs; //@arg infomation for known bugs (won't be
									//fixed in this relase)
	protected String dpEndWith; //@arg actions after test
                                //@value = logout|quit|kill, or empty
	protected String dpSearchItems; //@arg Search items for search test
                                    //@value = item1|item2|..
	protected String dpClearFilter; //@arg Search items for search test
                                    //@value = yes|no
	// END of datapool variables declaration
	
	// BEGIN locators of the web elements of ProjectsList page
	By searchBoxLocator = By.cssSelector("div.multi-name-search > input.search-query.search-input");
	By searchBoxIconLocator = By.cssSelector("div.multi-name-search > div.icon-search");
	By searchItemLocator = By.cssSelector("li.search-item-row > button.search-item");
	By searchCancelFilterIconLocator = By.cssSelector("li.search-item-row > i.icon-remove");
	By copyrightFooter = By.className("footer");
	By closeIconLocator = By.cssSelector(".icon_remove.close-layer-icon");
	//END
    
    // BEGIN of other local variables declaration
	protected String[] searchItemsArr;
	protected List<WebElement> searchItems;
	protected String allSearchItems;
	//END
	
    // BEGIN of other local variables declaration
	protected WebDriver driver;
	//END
	
	public boolean dataInitialization() {
		getSharedObj();
		dpExpectedErr = getDpString("ExpectedErr");
		dpKnownBugs = getDpString("KnownBug");
		dpEndWith = getDpString("EndWith");
		dpSearchItems = getDpString("SearchItems");
		dpClearFilter = getDpString("ClearFilter");
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(args);
	}
	
	//***************  Part 1  *******************
	// ******* common functions      ***
	// *******************************************
	
	
	public String getFooter() {
		return driver.findElement(copyrightFooter).getText();
	}	
	
	public void filterList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		if(!dpSearchItems.isEmpty()){
			searchItemsArr = dpSearchItems.split("\\|");
			for(int i=0;i<searchItemsArr.length;i++){
				logTAFInfo("with updated code");
				//((JavascriptExecutor) driver).executeScript("document.getElementByTagName('input').focus()");
				new Actions(driver).moveToElement(driver.findElement(searchBoxIconLocator)).click().perform();
				driver.findElement(searchBoxLocator).sendKeys(Keys.END);
				driver.findElement(searchBoxLocator).sendKeys(searchItemsArr[i]);
				driver.findElement(searchBoxLocator).sendKeys(Keys.ENTER);
				//driver.findElement(searchBoxIconLocator).click();
			}	
		}
	}
	
	public String getSearchItemsList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		searchItems = driver.findElements(searchItemLocator);
		for(int i = 0; i < searchItems.size(); i++) {
        	if(i==0){
        		allSearchItems=searchItems.get(i).getText();
        	}else{
        		allSearchItems=allSearchItems+"|"+searchItems.get(i).getText();
        	}
        }
		return allSearchItems;
	}
	
	public void clearFilter(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		searchItems = driver.findElements(searchCancelFilterIconLocator);
		for(int i=0;i<searchItems.size();i++){
			searchItems.get(i).click();
		}
	}
	
	public void verifyElementsAfterFilter(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		isElementDisplayed(searchItemLocator, "Search Items in search box");
		isElementEnabled(searchCancelFilterIconLocator, "Search items' cancel (x) button");
	}
	
	public void closeLayer() {
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		driver.findElement(closeIconLocator).click();
	}	
	
	
	//*******************************************
	// ******* Methods on compare results **************
	// *******************************************
	
	public boolean compareTxtResult(String result,String master)	{
		
        String[] ignorePattern ={""};
        String[] ignoreName = {""};
        String delimiterPattern = "\\|";
        
        return compareResult(
        	master,result,
   			ignorePattern,ignoreName,  //Replacement
   			delimiterPattern);  // used to split
		
	}
	
	//******************************************
	// ******* Methods on verification ***********
	// *******************************************
	
	public boolean isElementEnabled(By locator, String elementName) {
		boolean done = false;
		try{
			done = driver.findElement(locator).isEnabled();
			logTAFStep("Successfully found '"+elementName+"'");
		}catch(Exception e){
			logTAFError("Failed to find '"+elementName+"' !!!");
		}
        return done;
    }
	
	public boolean isElementDisplayed(By locator, String elementName) {
		boolean done = false;
		try{
			done = driver.findElement(locator).isDisplayed();
			logTAFStep("Successfully found '"+elementName+"'");
		}catch(Exception e){
			logTAFError("Failed to find '"+elementName+"' !!!");
		}
        return done;
    }
	
	//*******************************************
	// ******* Methods on terminate **************
	// *******************************************
	
	public void cleanUp() {

		if (dpEndWith.equals("close")) {
           closeBrowser();
		}else if (dpEndWith.equals("kill")) { // if image name is available
          killBrowser();
		} else if (dpEndWith.equals("logout")) {	
			//casLogout(url);						
		} else {
			return;
		}		
	}

	public void closeBrowser(){
		driver.close();
		killProcess(projectConf.driverName);
		driver = null;
		logTAFStep("Close test browser");
		setSharedObj();
	}
	
	public void killBrowser(){
		logTAFStep("Kill browser '" + imageName + "'");
		killProcess(imageName);
		killProcess(projectConf.driverName);
		driver = null;
		setSharedObj();
	}
	
	public boolean casLogout(String url){
		/**
		String infoText = "You have successfully logged out";
		
		String logoutUrl = url.substring(0,url.indexOf("/aclax/")) + "/cas/logout";// "/cas/login"
		
		logTAFStep("Logout user - '"+logoutUrl+"'");
		driver.get(logoutUrl);
		try{
		   logTAFStep("Logout user sucessfully - '"+logoutUrl+"'");
		}catch(Exception e){
			logTAFError("Failed to logout url - '"+logoutUrl+"'");
			return false;
		}
		setSharedObj();
		**/
		return true;
	}
	
	
	//**********************************************
	// ******* Methods on Objects sharing ********
	// *******************************************
	
	public void getSharedObj() {
		if (suiteObj != null) {
			driver = ((TestSuiteExampleHelper) suiteObj).currentDriver;
		} else if (caseObj != null) {
			driver = ((FrontendTestDriverHelper) caseObj).currentDriver;
		}
	}

	public void setSharedObj() {
		if (suiteObj != null) {
			((TestSuiteExampleHelper) suiteObj).currentDriver = driver;
		} else if (caseObj != null) {
			((FrontendTestDriverHelper) caseObj).currentDriver = driver;
		}
	}	

}
