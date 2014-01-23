package anr.lib.frontend;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ax.lib.restapi.TestSuiteExampleHelper;

import com.acl.qa.taf.helper.KeywordSuperHelper;

public class OBSOLETE_FrontendCommonHelper extends KeywordSuperHelper{
	
	/**
	 * Script Name   : <b>FrontendCommonHelper</b>
	 * Generated     : <b>Sep 5, 2013</b>
	 * Description   : FrontendCommonHelper
	 * 
	 * @since  Oct 7, 2013
	 * @author Karen Zou
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

	// END of datapool variables declaration
	
    // BEGIN of other local variables declaration
	protected WebDriver driver;
	//END
	
	public boolean dataInitialization() {
		getSharedObj();
		
		dpExpectedErr = getDpString("ExpectedErr");
		dpKnownBugs = getDpString("KnownBug");
		dpEndWith = getDpString("EndWith");
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
	
	public void takeScreenshotWithoutScroll(){
		logTAFInfo("takeScreenshot() function disabled for now. need to be enabled again when localization tests have been added.");
		/*
		sleep(timerConf.waitToTakeScreenshot);
		captureScreen(getScreenshotPathAndName());
		logTAFInfo("Screenshot taken");
		*/
	}
	
	public int getNumbers(String strNum) {
		String regEx="[^0-9]";   
		
		Pattern p = Pattern.compile(regEx);   
		Matcher m = p.matcher(strNum);   
		
		return Integer.parseInt(m.replaceAll("").trim());
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
