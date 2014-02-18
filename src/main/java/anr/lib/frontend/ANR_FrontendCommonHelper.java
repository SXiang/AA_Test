package anr.lib.frontend;

import ax.lib.frontend.FrontendCommonHelper;
import ax.lib.frontend.FrontendTestDriverHelper;
import ax.lib.restapi.TestSuiteExampleHelper;

public class ANR_FrontendCommonHelper extends FrontendCommonHelper{
	
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
	protected String dpKnownBugs;   //@arg infomation for known bugs (won't be
									//fixed in this relase)
	protected String dpEndWith;     //@arg actions after test
                                    //@value = logout|quit|kill, or empty

	// END of datapool variables declaration
	
    // BEGIN of other local variables declaration
	//Karen public WebDriver driver;
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
	

	//*******************************************
	// ******* Methods on terminate **************
	// *******************************************
	public void cleanUp() {
		cleanUp(dpEndWith);
	}

	public void cleanUp(String comm){
		if (comm.equalsIgnoreCase("close")) {
           closeBrowser();
		}else if (comm.equalsIgnoreCase("kill")) { // if image name is available
          killBrowser();
		} else if (comm.equalsIgnoreCase("quit")) {	
			closeBrowser()	;			
		}else {
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

	//**********************************************
	// ******* Methods on Objects sharing ********
	// *******************************************
	
}
