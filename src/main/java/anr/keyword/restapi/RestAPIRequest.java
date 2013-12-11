package anr.keyword.restapi;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.UTF8Control;

import anr.lib.restapi.HttpRequestHelper;
import anr.lib.restapi.RestapiHelper;
import anr.lib.restapi.TestDriverExampleHelper;

public class RestAPIRequest extends HttpRequestHelper implements KeywordInterface {
	/**
	 * Script Name   : <b>DoGet</b>
	 * Generated     : <b>Oct. 29, 2013 1:20:22 PM</b>
	 * Description : <b>ACL Test Automation</b>
	 * 
	 * @since  2013/12/01
	 * @author Karen_Zou
	 */
	// AX Server: autoqawin2012.aclqa.local - 10.83
	// BEGIN of datapool variables declaration
	protected String dpProjectName;   	//@arg value for Project Name

	protected String dpApi_Path;   	    //@arg path of the request
	protected String dpTableName;   	//@arg value for Table Name
	protected String dpAnalyticName;   	//@arg value for Analytic Name
	protected String dpParameterSetName; //@arg parameter set name
	protected String dpRequestBody;     //@arg input for json post, could be string or file
	
	protected String dpWrongID = "InvalidID";   	//@arg invalid uuid for negative test
	// END of datapool variables declaration

    private String url = "";
	private String id="";
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		
		//*** read in data from datapool     
		dpApi_Path = getDpString("Api_Path");
		dpTableName = getDpString("TableName");
		dpAnalyticName = getDpString("AnalyticName");
		dpParameterSetName = getDpString("ParameterSetName");
		dpRequestBody = getDpString("RequestBody");
		
		dpWrongID = getDpString("WrongID");
		url = getApiFullPath(dpApi_Path);
		if (!url.contains(OpenProject_API))
			dpRequestBody = loadJsonText(dpRequestBody);
		else {
			dpRequestBody = FileUtil.getAbsDir(dpRequestBody, projectConf.anrProjectPath);
		}
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
			actualResult = UTF8Control.utf8decode(sendApiRequest(driver,url,dpRequestBody));

//1204			if(isJsonText(actualResult)){
				logTAFInfo("JSON data: '\n\t\t"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()+1))+"...");
				// compare Json Result - exact master and actual files are handled by framework.
				logTAFStep("File verification - "+dpMasterFiles[0]);
				
				if (dpApi_Path.contains("analytics/{uuid}/run")){
					scheduleid = getJsonValue(actualResult,"scheduleId");
					((TestDriverExampleHelper) caseObj).scheduleid = scheduleid;
				}
				
				compareJsonResult(actualResult,dpMasterFiles[0]);
/*1204			}else{			
				logTAFError("Not a valid Json object? - Http Status:"+responseCode+" '"+FormatHtmlReport.getHtmlPrintable(actualResult,Math.min(100,actualResult.length()+1))+"..."+"'"	);
			}*/
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
        	//1203 System.out.println("rg:"+textMaster[i]+":end");
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
		
		path = "http://"+(projectConf.anrapiPrefix + path).replaceAll("//", "/");

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