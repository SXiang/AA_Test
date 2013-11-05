package ax.keyword.restapi.request;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

import ax.lib.restapi.HttpRequestHelper;
import ax.lib.restapi.RestapiHelper;
import ax.lib.restapi.db.SQLConf;

public class RestAPIRequest extends HttpRequestHelper implements KeywordInterface {
	/**
	 * Script Name   : <b>DoGet</b>
	 * Generated     : <b>Oct. 29, 2013 1:20:22 PM</b>
	 * Description : <b>ACL Test Automation</b>
	 * 
	 * @since  2013/10/29
	 * @author Steven_Xiang
	 */
	// AX Server: autoqawin2012.aclqa.local - 10.83
	// BEGIN of datapool variables declaration
	protected String dpScope;          //@arg value for Scope
                                    	//@value = working/library/""
	protected String dpProjectName;   	//@arg value for Project Name
	protected String dpTableName;   	//@arg value for Table Name
	protected String dpColumnName;   	//@arg value for Column Name
	protected String dpTestSetName;   	//@arg value for TestSet Name
	protected String dpTestName;   	    //@arg value for Test Name
	protected String dpAnalyticName;   	//@arg value for Analytic Name
	protected String dpApi_Path;   	//@arg path of the request
	protected String dpParameterSetName; //@arg parameter set name
	protected String dpWrongUUID = "InvalidUUID";   	//@arg invalid uuid for negative test
	protected String dpJsonBody;   //@arg input for json post, could be string or file
	// END of datapool variables declaration
    
    private String url = "";
	private String uuid="";
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
     	
		//*** read in data from datapool     
		dpScope = getDpString("Scope");
		dpProjectName = getDpString("ProjectName");
		dpTableName = getDpString("TableName");
		dpColumnName = getDpString("ColumnName");
		dpTestSetName = getDpString("TestSetName");
		dpTestName = getDpString("TestName");
		dpAnalyticName = getDpString("AnalyticName");
		dpParameterSetName = getDpString("ParameterSetName");
		dpApi_Path = getDpString("Api_Path");
		dpJsonBody = getDpString("JsonBody");
		//String gap, String an, String setName
		//Rest API - Prepare path
		//dpWebDriver="Firefox";
		url = getApiFullPath(dpApi_Path);
		dpJsonBody = loadJsonText(dpJsonBody);
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
		
		setConnection(url);
		doVerification();
		cleanUp(url);
		
	  //*** cleanup by framework ***		
		onTerminate();
	}
	
	//***************  Part 3  *******************
	// *** Implementation of test functions ******
	// *******************************************
		
	public void doVerification(){
		
	//	for (int i=0; i<ConcurrentInstances; i++) {
			String actualResult;// = UTF8Control.utf8decode(driver[i].getPageSource());
//			actualResult = UTF8Control.utf8decode(driver.getPageSource());
			actualResult = UTF8Control.utf8decode(sendApiRequest(driver,url,dpJsonBody));

			if(casAuthenticated&&isJsonText(actualResult)){
				logTAFInfo("JSON data: '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()+1))+"...");
				// compare Json Result - exact master and actual files are handled by framework.
				logTAFStep("File verification - "+dpMasterFiles[0]);
				compareJsonResult(actualResult,dpMasterFiles[0]);
			}else{							
				logTAFError("Not a valid Json object? - Http Status:"+responseCode+" '"+FormatHtmlReport.getHtmlPrintable(actualResult,100)+"..."+"'"	);
			}
		}
	//}

	public boolean compareJsonResult(String result,String master)	{
		
        String[] ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")"};
        String[] ignoreName = {"$1u-u-i-d$2"};
        String delimiterPattern = "\\},\\{";
        
        return compareResult(
        	master,result,
   			true,          //Exact Match
   			ignorePattern,ignoreName,  //Replacement
   			delimiterPattern);  // used to split
		
	}
	
	public String getApiFullPath(String path){
        String sqlstmt;
        String scope = dpScope;
        if(!scope.equalsIgnoreCase("LIBRARY")){
        	scope = "WORKING";
        }
		if(path.contains("{tableId}")){
			if(dpTableName.equals("")){
				sqlstmt = SQLConf.getProjectID(scope, dpProjectName);
			}else{
				sqlstmt = SQLConf.getTableID(scope, dpProjectName, dpTestSetName, dpTableName);
			}
			uuid = getAuditItemUUID(sqlstmt,"Table",dpTableName);

			path = path.replaceAll("\\{tableId\\}", uuid);

		}
		
		if(path.contains("parametersets/{uuid}")){
			if(dpParameterSetName.equals("")){
				sqlstmt = SQLConf.getProjectID(scope, dpProjectName);
			}else{
				sqlstmt = SQLConf.getParameterSetID(scope, dpProjectName, dpTestSetName, dpTestName,dpAnalyticName,dpParameterSetName);
			}
			uuid = getField(sqlstmt,"parametersetid","Parameter Set",dpParameterSetName);

			path = path.replaceAll("parametersets/\\{uuid\\}", "parametersets/"+uuid);

		}
		if(path.contains("{columnName}")){
			path = path.replaceAll("\\{columnName\\}", dpColumnName);
		}
        // Adding more replacement based on url ...
		// ..
		// .
		
		path = "https://"+projectConf.axServerName+":" + projectConf.axServerPort + (projectConf.apiPrefix + path).replaceAll("//", "/");
		return path;
	}
	
	public String loadJsonText(String text){
		String jsonPattern = "^\\s*[\\[\\{].*";
		String fileText = text;
		if(text.matches(jsonPattern)){
			return text;
		}
		String file = FileUtil.getAbsDir(text.replaceFirst("^[\\/]", ""),currentTestCaseDir);
		fileText = FileUtil.getFileContents(file).replaceAll("\\r\\n", "");
		return fileText;
	}
 }