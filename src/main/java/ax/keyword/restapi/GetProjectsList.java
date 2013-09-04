package ax.keyword.restapi;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

import ax.lib.restapi.RestapiHelper;

public class GetProjectsList extends RestapiHelper implements KeywordInterface {
	/**
	 * Script Name   : <b>GetProjectList</b>
	 * Generated     : <b>Aug. 19, 2013 4:20:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2013/08/19
	 * @author Karen_Zou
	 */

	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
	//@value = HtmlUnit|Firefox|...

	protected String dpUserName;    //@arg username for login
	protected String dpPassword;    //@arg password for login
	protected String dpEndWith;     //@arg actions after test
										//@value = logout|close|quit|kill, or empty

	protected String dpScope;          //@arg value for Scope
                                    	//@value = working/library/""
	protected String dpMasterFile;     //@arg value for master file
	// END of datapool variables declaration
    
    private String url = "";
    
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
     
		//*** read in data from datapool     
		dpScope = getDpString("Scope");
		dpMasterFile = getDpString("MasterFile");

		if ((dpScope != null) && (dpScope != ""))
			url = "https://"+projectConf.serverName+":" + projectConf.port + projectConf.apiPrefix + "projects?scope="+dpScope;
		else url = "https://"+projectConf.serverName+":" + projectConf.port + projectConf.apiPrefix + "projects";

		return true;
	}
	
	//***************  Part 2  *******************
	// *********** Test logic ********************
	// *******************************************
	
	//Comments - 'Steps' in this part are used to generate keyword docs
	@Override
	public void testMain(Object[] args){			
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Steps:
		//@Step Start or activate AUT - Browser based on the value of 'WebDriver'
		//@Step Handled browser security warning if there is one
		//@Step Check user CAS session, report info or error accordingly.
		//@Step If login required, submit username and password to CAS
		//@@Step Fill in 'username' and 'password'
		//@@Step Submit
		//@@Step Verify page status and report accordingly
		//@Step Get page contents for verification
		//@@Step Compare actual contents with expected data - pre saved data or prepared master data
		//@@Step Report errors by indicating the actual line number and the contents
		//@@Step Take a screenshot if anything wrong (it happens whenever there is a warring or error reported)
		//@Step Execute the command specified in 'EndWith' such as 'Kill','Close' or 'Logout' 
		
		casLogin(url);
		doVerification();
		cleanUp(url);
		
	  //*** cleanup by framework ***		
		onTerminate();
	}
	
	//***************  Part 3  *******************
	// *** Implementation of test functions ******
	// *******************************************
		
	public void doVerification(){
		
		String actualResult = UTF8Control.utf8decode(driver.getPageSource());
		
		if(casAuthenticated){
			logTAFInfo("JSON data: '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,100)+"...");
			// compare Json Result - exact master and actual files are handled by framework.
		    logTAFStep("File verification - "+dpMasterFile);
		    compareJsonResult(actualResult,dpMasterFile);
		}else{							
			logTAFWarning("Should this be what we want? '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,100)+"..."+"'"	);
		}
	}

 }