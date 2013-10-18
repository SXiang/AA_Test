package ax.lib.frontend;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	By closeIconLocator = By.cssSelector(".icon_remove");
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
	
	public String getScreenshotPathAndName()
	{
		  try {
			return projectConf.localizationSnapshots + projectConf.appLocale + "\\" + InetAddress.getLocalHost().getHostAddress() + "\\" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime())+ "\\" + this.getClass().getName() + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".jpeg";	 
		  } catch (UnknownHostException e) {
			e.printStackTrace();
		    return projectConf.localizationSnapshots + projectConf.appLocale + "\\" + "192.168.0.0" + "\\" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime())+ "\\" + this.getClass().getName() + new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()) + ".jpeg";
		  }
	}
	
	public String getClipboard() {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
	    try {
	        if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
	            String text = (String)t.getTransferData(DataFlavor.stringFlavor);
	            return text;
	        }
	    } catch (UnsupportedFlavorException e) {
	    } catch (IOException e) {}
	    return null;
	}
	
	public void copyToClipboard(String copiedData) {
		StringSelection selection = new StringSelection(copiedData);
	    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    clipboard.setContents(selection, selection);
	}
	
	public String getFooter() {
		return driver.findElement(copyrightFooter).getText();
	}	
	
	public void filterList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		if(!dpSearchItems.isEmpty()){
			searchItemsArr = dpSearchItems.split("\\|");
			for(int i=0;i<searchItemsArr.length;i++){
				//((JavascriptExecutor) driver).executeScript("document.getElementByTagName('input').focus()");
				copyToClipboard(searchItemsArr[i]);
				//driver.findElement(searchBoxLocator).sendKeys(getClipboard());
				//new Actions( driver ).contextClick( driver.findElement(searchBoxLocator) ).sendKeys( "p" ).perform();
				new Actions(driver).moveToElement(driver.findElement(searchBoxIconLocator)).click().perform();
				driver.findElement(searchBoxLocator).sendKeys(Keys.END);
				System.out.println("ABout to type:"+getClipboard()+"*END");
				driver.findElement(searchBoxLocator).sendKeys(Keys.SHIFT, Keys.INSERT);
				//new Actions( driver ).contextClick( driver.findElement(searchBoxLocator)).sendKeys(Keys.END).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).build().perform();
				driver.findElement(searchBoxLocator).sendKeys(Keys.ENTER);
				//driver.findElement(searchBoxIconLocator).click();
			}	
		}
		takeScreenshot();
	}
	
	public String getSearchItemsList(){
		((JavascriptExecutor) driver).executeScript("scroll(250,0);");
		searchItems = driver.findElements(searchItemLocator);
		System.out.println("number of search items: "+searchItems.size());
		for(int i = 0; i < searchItems.size(); i++) {
        	if(i==0){
        		allSearchItems=searchItems.get(i).getText();
        		System.out.println("search item"+i+": "+searchItems.get(i).getText());
        	}else{
        		allSearchItems=allSearchItems+"|"+searchItems.get(i).getText();
        		System.out.println("search item"+i+": "+searchItems.get(i).getText());
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
	
	public void takeScreenshot(){
		logTAFInfo("takeScreenshot() function disabled for now. need to be enabled again when localization tests have been added.");
		/*
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Boolean reachedBottom = false;
		int screenSize;
		int scrolledSize;
		int totalSize;
		do{
		  sleep(timerConf.waitToTakeScreenshot);
		  captureScreen(getScreenshotPathAndName());
		  logTAFInfo("Screenshot taken");
		  if((projectConf.webDriver).startsWith("IE")||((projectConf.webDriver).startsWith("ie"))){
			  screenSize = Integer.parseInt(js.executeScript("return window.innerHeight;").toString());
			  scrolledSize = Integer.parseInt(js.executeScript("return (document.documentElement && document.documentElement.scrollTop) || document.body.scrollTop;").toString());
			  totalSize = Integer.parseInt(js.executeScript("return document.body.scrollHeight;").toString());
		  }else{
			  screenSize = Integer.parseInt(js.executeScript("return window.innerHeight;").toString());
			  scrolledSize = Integer.parseInt(js.executeScript("return window.scrollY;").toString());
			  totalSize = Integer.parseInt(js.executeScript("return document.body.scrollHeight;").toString());
		  }
		  if(totalSize - screenSize - scrolledSize > 0){
			  if(totalSize - screenSize - scrolledSize > screenSize ){
				  js.executeScript("var jsScreenSize = "+ screenSize + "; scrollBy(0,jsScreenSize);");
			  }else if(totalSize - screenSize - scrolledSize < screenSize ){
				  js.executeScript("var jsTotalSize = "+ totalSize + "; var jsScrolledSize = "+ scrolledSize + "; var jsScreenSize = "+ screenSize + "; scrollBy(0,jsTotalSize-jsScrolledSize-jsScreenSize);");
			  }
		  }else{
			  reachedBottom = true;
		  }
		  }while(!reachedBottom);
		js.executeScript("scroll(250,0);");
		*/
	}
	
	public void takeScreenshotWithoutScroll(){
		logTAFInfo("takeScreenshot() function disabled for now. need to be enabled again when localization tests have been added.");
		/*
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
		*/
	}
	
	//*******************************************
	// ******* Methods on compare results **************
	// *******************************************
	
	public boolean compareTxtResult(String result,String master)	{
		
        String[] ignorePattern ={""};
        String[] ignoreName = {""};
        String delimiterPattern = "\r\n";
        
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
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
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
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
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
