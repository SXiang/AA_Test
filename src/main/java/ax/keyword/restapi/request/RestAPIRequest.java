package ax.keyword.restapi.request;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

import ax.lib.restapi.HttpRequestHelper;
import ax.lib.restapi.RestapiHelper;
import ax.lib.restapi.TestDriverExampleHelper;
import ax.lib.restapi.db.SQLConf;
import ax.lib.restapi.db.SQLQuery;

public class RestAPIRequest extends HttpRequestHelper implements KeywordInterface {
	/**
	 * Script Name   : <b>DoGet</b>
	 * Generated     : <b>Oct. 29, 2013 1:20:22 PM</b>
	 * Description : <b>ACL Test Automation</b>
	 * 
	 * @since  2013/10/29
	 * @author Steven_Xiang
	 */
	// *** path: /aclax/apidoc
	// *** version: /auditexchange/version
	
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
		
		//System.out.println("scheduledID:"+((TestDriverExampleHelper) caseObj).scheduleid);
     	
		//*** read in data from datapool     
		dpScope = getDpString("Scope");
		dpProjectName = getDpString("ProjectName");
		dpTestSetName = getDpString("TestSetName");
		dpTestName = getDpString("TestName");
		dpTableName = getDpString("TableName");
		dpColumnName = getDpString("ColumnName");
		dpAnalyticName = getDpString("AnalyticName");
		dpParameterSetName = getDpString("ParameterSetName");
		dpApi_Path = getDpString("Api_Path");
		dpJsonBody = getDpString("JsonBody");
		dpWrongUUID = getDpString("WrongUUID");
		//String gap, String an, String setName
		//Rest API - Prepare path
		//dpWebDriver="Firefox";
		url = getApiFullPath(dpApi_Path);
		dpJsonBody = loadJsonText(dpJsonBody);
		return true;
	}
	
	//********* Analytic for debugging:
	// ANALYTIC TestParamsPARAM_N
	// UUID: 1a1fa309-81dc-417e-893e-ea460740743d
	// This analytics tests various types of parameters 
	//PARAM v_num_s N Single Numeric Parameter
	//result file AllUserInputs*
	//RESULT LOG
	//********* END

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
				
				if (dpApi_Path.contains("analytics/{uuid}/run")){
					scheduleid = getJsonValue(actualResult,"scheduleId");
					
					((TestDriverExampleHelper) caseObj).scheduleid = scheduleid;
				}
				
				compareJsonResult(actualResult,dpMasterFiles[0]);
			}else{							
				logTAFError("Not a valid Json object? - Http Status:"+responseCode+" '"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()+1))+"..."+"'"	);
			}
		}
	//}

	public boolean compareJsonResult(String result,String master)	{
		
        String[] ignorePattern ={"(\"id\":\")[0-9\\-a-z]+(\")","\"startTime\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"endTime\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"createdAt\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","\"modifiedAt\":\"\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}.\\d{1,3}\"","[\\[\\{\\]\\}\\s]"};
        String[] ignoreName = {"$1u-u-i-d$2","\"startTime\"","\"endTime\"","\"createdAt\"","\"modifiedAt\"",""};
        String delimiterPattern = "\\}[\\s]*,[\\s]*\\{|[\\[\\]]";
        
        //Testing regular expression
        String[] textMaster = result.split(delimiterPattern);
        for (int i=0; i <textMaster.length; i++) {
        	textMaster[i] = stringReplaceAll(textMaster[i],ignorePattern,ignoreName);
        	//System.out.println("rg:"+textMaster[i]+":end");
        }
        //Test End
        
        return compareResult(
        	master,result,
   			true,          //Exact Match
   			ignorePattern,ignoreName,  //Replacement
   			delimiterPattern);  // used to split
		
	}
	
	public static String stringReplaceAll(String source, String[] pattern,
			String[] replacement) {
		for (int i = 0; i < pattern.length && i < replacement.length; i++) {
			source = source.replaceAll(pattern[i], replacement[i]);
		}
		return source;
	}
	
	// Get API full path for each API
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
		
		if(path.contains("analytics/{uuid}")){
			if (dpWrongUUID.isEmpty()) {
				if (dpAnalyticName.isEmpty()){
					sqlstmt = SQLConf.getProjectID(scope, dpProjectName);
				}else{
					sqlstmt =  SQLConf.getAnalyticID(scope,dpProjectName,dpTestSetName,dpTestName,dpAnalyticName);
				}
			
				uuid = getField(sqlstmt,"id","Analytics",dpAnalyticName);
			}else {
				uuid = dpWrongUUID;
			}
			
			path = path.replaceAll("analytics/\\{uuid\\}", "analytics/"+uuid);
			
			//Analytic with specified ParameterSet name in ParameterSet variable 
			if (!dpParameterSetName.isEmpty()){
				sqlstmt = SQLConf.getParameterSetID(scope, dpProjectName, dpTestSetName, dpTestName,dpAnalyticName,dpParameterSetName);
				uuid = getField(sqlstmt,"parametersetid","Parameter Set",dpParameterSetName);	
				
				if(dpJsonBody.equals("")){
				   dpJsonBody = createParameterSetJsonBody(dpParameterSetName,uuid);
				}
				
				if(!uuid.equals("") && path.endsWith("/parameters") && dpExpectedErr.equals("")){
					int numUpdated = updateDB(SQLQuery.deleteParameterSet(uuid));
					if(numUpdated<=0){
				    	 //logTAFInfo(" Can't find pSet '"+dpParameterSetName+": "+uuid+"'");
				     }else{
				    	 logTAFInfo("Delete '"+dpParameterSetName+": "+uuid+"' from database successfully");
				     }
					//delete possible existing set
				}
				
			}

			//Clean the shared variable 'scheduleid' from last time run
			if (path.contains("analytics/{uuid}/run")){
				((TestDriverExampleHelper) caseObj).scheduleid = "";
			}
		}
		
		if(path.contains("{analysisAppId}")){		
			//getTestID(String type, String bc, String lc, String gap)
				sqlstmt = SQLConf.getTestID(scope, dpProjectName, dpTestSetName, dpTestName);
				uuid = getAuditItemUUID(sqlstmt,"Test",dpTestName);	
			   path = path.replaceAll("\\{analysisAppId\\}", uuid);
			}	
		
		if(path.contains("{jobId}")){
			if (dpAnalyticName.isEmpty()){
				sqlstmt = SQLConf.getProjectID(scope, dpProjectName);
			}else{
				sqlstmt =  SQLConf.getJobID(scope,dpProjectName,dpTestSetName,dpTestName,dpAnalyticName,((TestDriverExampleHelper) caseObj).scheduleid);
			}
			uuid = getField(sqlstmt,"jobnumber","Jobs",dpAnalyticName);
			
			path = path.replaceAll("\\{jobId\\}", uuid);
		}

		// Adding more replacement based on url ...
		// ..
		// .
		
		path = "https://"+projectConf.axServerName+":" + projectConf.axServerPort + (projectConf.apiPrefix + path).replaceAll("//", "/");
		return path;
	}
	
	public String loadJsonText(String oriText){
		String jsonPattern = "^[\\s]*[\\[\\{].*";
		String text = oriText.replaceAll("[\\r\\n]", "");
		if(text.matches(jsonPattern)){
			return text;
		}
		String file = FileUtil.getAbsDir(text.replaceFirst("^[\\/]", ""),currentTestCaseDir);
		text = FileUtil.getFileContents(file).replaceAll("[\\r\\n]", "");
		return text;
	}
	
	private String createParameterSetJsonBody(String parametersetname, String uuid){
		String temp = "";
		temp = "{\"parameterSet\": {\"name\": \""+parametersetname+"\",\"uuid\":\""+uuid+"\"}}";
		
		return temp;
	}

 }