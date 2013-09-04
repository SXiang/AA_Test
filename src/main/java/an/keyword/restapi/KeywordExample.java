/**
 * 
 */
package an.keyword.restapi;

import an.lib.restapi.KeywordExampleHelper;
import an.keyword.restapi.KeywordExample;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

//Script Name is required for generating keyword doc
/**
 * Script Name : <b>KeywordExample</b> Generated : <b>2013-08-16 12:07:24 PM</b>
 * Description : <b>ACL Test Automation</b>
 * 
 * @since 2013/08/16
 * @author Steven_Xiang
 * 
 */
public class KeywordExample extends KeywordExampleHelper implements
		KeywordInterface {

	/**
	 * @param args
	 */

	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************

	// Comments in section 'BEGIN' TO 'END' are used for generating keyword docs
	// BEGIN of datapool variables declaration
	
	protected String dpURL; // @arg the url to be tested
	protected String dpScope; // @arg scope of the project
								// @value = working|library, or empty

	// END of datapool variables declaration

	@Override
	public boolean dataInitialization() {
		super.dataInitialization();

		dpURL = getDpString("URL");
		dpScope = getDpString("Scope");
		if (!dpScope.equals("")) {
			dpURL += "?scope=" + dpScope;
		}
		return true;
	}

	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************

	// Comments - 'Steps' in this part are used to generate keyword docs
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));

		// Steps:
		// @Step Start or activate AUT - Browser based on the value of
		// 'WebDriver'
		// @Step Handled browser security warning if there is one
		// @Step Check user CAS session, report info or error accordingly.
		// @Step If login required, submit username and password to CAS
		// @@Step Fill in 'username' and 'password'
		// @@Step Submit
		// @@Step Verify page status and report accordingly
		// @Step Get page contents for verification
		// @@Step Compare actual contents with expected data - pre saved data or
		// prepared master data
		// @@Step Report errors by indicating the actual line number and the
		// contents
		// @@Step Take a screenshot if anything wrong (it happens whenever there
		// is a warring or error reported)
		// @Step Execute the command specified in 'EndWith' such as
		// 'Kill','Close' or 'Logout'

		casLogin(dpURL);
		doVerification();
		cleanUp(dpURL);
        
		// *** cleanup by framework ***
		onTerminate();
	}

	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************

	public void doVerification() {
		if (getDpString("DAODemo").equalsIgnoreCase("Yes")) {
			databaseAccessDemo();
		}
		String actualResult = UTF8Control.utf8decode(driver.getPageSource());

		if (casAuthenticated) {
			logTAFInfo("JSON data: '\n\t\t"
					+ FormatHtmlReport.getHtmlPrintable(actualResult, 100)
					+ "...");
			// compare Json Result - exact master and actual files are handled
			// by framework.
			for (int i = 0; i < dpMasterFiles.length; i++) {
				logTAFStep("Demo - file verification - " + dpMasterFiles[i]);
				result[i] = actualResult; // You need to get actual result for
											// each comparison
				compareJsonResult(result[i], dpMasterFiles[i]);
			}
		} else {
			logTAFWarning("Should this be what we want? '\n\t\t"
					+ FormatHtmlReport.getHtmlPrintable(actualResult, 100)
					+ "..." + "'");
		}

	}

	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************

	public static void main(String args) {
		KeywordExample debug = new KeywordExample();
		// debug.startBrowser("HtmlUnit");
		// debug.testAXRestAPI(debug.driver);
	}
}
