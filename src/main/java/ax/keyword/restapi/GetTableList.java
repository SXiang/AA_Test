package ax.keyword.restapi;

import java.lang.Math;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

import conf.bean.DBConf;
import ax.lib.restapi.RestapiHelper;

public class GetTableList extends RestapiHelper implements KeywordInterface {
	/**
	 * Script Name   : <b>GetProjectList</b>
	 * Generated     : <b>Aug. 19, 2013 4:20:42 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2013/11/04
	 * @author Karen_Zou
	 */

	// BEGIN of datapool variables declaration
	protected String dpScope;          	//@arg value for Scope
                                    	//@value = working/library/""
	protected String dpProjectName;   	//@arg value for Project Name
	protected String dpTestSetName;   	//@arg value for Test Set Name
	protected String dpTestSetUuid;   	//@arg value for TestSet uuid
	
	// END of datapool variables declaration
	private String url = "";
	private String uuid="";             //TestSet uuid
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
     
		//*** read in data from datapool     
		dpScope = getDpString("Scope");
		dpProjectName = getDpString("ProjectName");
		dpTestSetName = getDpString("TestSetName");
		dpTestSetUuid = getDpString("TestSetUuid");

		//Rest API - Table List in a folder: /api/folders/{uuid}/tables
		if (!dpTestSetUuid.isEmpty())
			uuid = dpTestSetUuid;
		else uuid = queryTestSetID(dpScope,dpProjectName,dpTestSetName);

		if ((uuid != null) && (uuid != ""))
			url = "https://"+projectConf.axServerName+":" + projectConf.axServerPort + projectConf.apiPrefix+"folders/"+uuid+"/tables";
		else System.out.println("Error:" + "Can not find the uuid for the specific test set");

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
			logTAFInfo("JSON data: '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()))+"...");
			// compare Json Result - exact master and actual files are handled by framework.
		    logTAFStep("File verification - "+dpMasterFiles[0]);
		    System.out.println("actualResult:"+actualResult);
		    compareJsonResult(actualResult,dpMasterFiles[0]);
		}else{							
			logTAFWarning("Should this be what we want? '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()))+"..."+"'"	);
		}
	
	}

}