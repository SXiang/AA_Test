package com.acl.qa.taf.helper.superhelper;

import java.sql.SQLException;
import java.util.Date;

import org.apache.xalan.xsltc.trax.Util;
import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import net.sf.cache4j.Cache;

import com.acl.qa.taf.util.FileUtil;
import com.acl.qa.taf.util.FormatHtmlReport;
import com.acl.qa.taf.util.MemusageTracer;
import com.acl.qa.taf.util.NLSUtil;
import com.acl.qa.taf.util.UTF8Control;


public class LoggerHelper extends ACLQATestScript {
    
	public static String logLF = "<LF>";
	public static String logLFIndent = "\n\t\t";
	public static String htmlLFIndent = "<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	public static String testResult = "None"; // keyword test result
	public static String message = ""; // keyword test message
	public static String testCaseResult = "None"; // test case result
	public static String failMessage = ""; // test case fail
	public static boolean sysExceptionCaught = false;
	public static String testMainResult = "None"; // main Script test result
	public static String mainMessage = ""; // main Script test message
	public static String userName = "Unknown";// "Manager";
	public static String password = "Password00";
	public static String testSuite = "Test Suite";
	public static String testKeyword = "Keywords";
	public static String autoIssue = "Possible automation or environment  issue: ";
	public static String autTitle = "";
	public static String hostName;
	public static String hostIP;

	// ***** Moved from batchrunsuperhelper *******
	public static String bugremaining = "";
	public static String bugfixed = "", bugnew = "", bugauto = "";
	public static int bugNumF = 0, bugNumN = 0, bugNumA = 0, bugNumR = 0,
			numTested = 0;
	public static String sysLineSep = System.getProperty("line.separator");

	public static int numKWs = 0, numTestedKeywordInCase = 0, numKWsFail = 0,
			numTCs = 0, numTCsFail = 0, numSnapshots = 0;
	public static boolean stopScript = false, stopTest = false,
			batchRun = false, isWeb = false,

			localOnlyTest = false;

	public static String stopMessage = "", expectedErr = "", menuItem = "";
	public static String colorFail = "<font color=\"#B40404\">",
			colorNewIssue = "<b><font color=\"#886A08\">",
			colorAutomationIssue = "<b><font color=\"#585858\">",
			colorFixedBug = "<b><font color=\"#3ADF00\">",
			colorRemainBug = "<b><font color=\"#8904B1\">",
			colorPass = "<b><font color=\"#21610B\">",
			colorDiv = "<font color=\"#0B0B61\" face=\"verdana\">";
	public static String _closeTag = "</font>", closeTag = "</font></b>";

	// public static String colorFail ="<font color=\"#B40404\">",
	// colorNewIssue =
	// "<b><div title=\""+getNewIssueTips()+"\">"+"<font color=\"#886A08\">",
	// colorAutomationIssue =
	// "<b><div title=\"Automation Issue\"><font color=\"#585858\">",
	// colorFixedBug = "<b><div title=\"Fixed Bug\"><font color=\"#3ADF00\">",
	// colorRemainBug =
	// "<b><div title=\"Remainning Issue\"><font color=\"#8904B1\">",
	// colorPass = "<b><div title=\"Test Passed\"><font color=\"#21610B\">",
	// colorDiv = "<div title=\"\"><font color=\"#0B0B61\" face=\"verdana\">";
	// public static String _closeTag="</font></div>",closeTag =
	// "</font></div></b>";

	public static String errorDetails = "", knownBugs = "",
			automationBugs = "", unKnownBugs = "", fixedBugs = "",
			remainingBugs = "", newBugs = "", bugmessage = "",
			testDetails = "",

			keywordErrors = "", caseName = "", currentPoolName = "",
			keywordName = "", currentkeyword = "", testCaseLine = "",
			testResultHeader = "", reportSubject = "", scriptStartTime; // As we
																		// want
																		// to
																		// user
																		// the
																		// time
																		// used
																		// in
																		// TAF
																		// logger

	public static String curTestScenario = "";
	public static String scenarioDesLinePrefix = "#@";
	public static String currentImeIndex = "";
	public static String poolLanguageIndex = "1";
	public static String resourcePropPrefix = "";

	public static Date scriptEndTime;

	
	public static String snapshotLink = "";
	public static String testDescription = "\n\tDescription of the tested area goes here...";
	public static String resultAnalysis = "*\t\tAnalysis goes here...\n**\t\t\t...\n***\t\t\t...";
	public static String projName = "";

	public static MemusageTracer mt;
	public static String imageName;

	public static boolean errorHandledInLine = false;
	public static boolean onRecovery = true;
	public static boolean applyWR = false;
	public static boolean testInterrupted = false;
	// Dynamic data
	public static String pathToTestCaseScripts = "";
	public static String originalPathToTestCaseScripts = "";
	public static String pathToKeywordScripts = "";
	public static String originalPathToKeywordScripts = "";
	public static String projectName = "";
	public static String localizationDir = "";
	// public boolean isDailyTest = false;
	public static int currentTestLine = 1;
	public static int dailyTestStart = 2;
	public static int dailyTestEnd = 2;
	public static int numFBugs = 0, numRBugs = 0, numNBugs = 0, numABugs = 0;

	// Test Reports
	// public static boolean saveProject = false;
	public static String testResultTXT_Server = "";
	public static String testResultXLS_Server = "";
	public static String testResultHTML_Server = "";
	public static String memusageCSV_Server = "";
	public static String screenShots_Server = "";
	public static String workingDir_Server = "";
	public static String workingDir = "";
	public static boolean projectArchived = false;
	public static boolean individualTest = false;
	public static String logItem = "";


	public static Cache cache_l10n, cache_en;
	public String thisMasterFile, thisMasterFiles[] = new String[50],
			thisActualFile, thisActualFiles[] = new String[50],
			thisArchiveProject = "";

	public static String formatSnapshot(String message, String link) {
		String msg = "";

		// msg = message
		// + (link.equals("")? "" : "\n\t\t***Snapshot: " + link);
		msg = (link.equals("") ? ""
				: FormatHtmlReport.linkOpenTag +" href=\"file:///"
						+ link + "\">" + "[Snapshot]</a>")
				+ message;
		return msg;

	}

	// Seven logging methods for our own test automation framework only
	public static void logTAFErrorInfo(String note, boolean isInfo) {
		if (isInfo)
			logTAFInfo(note);
		else
			logTAFError(note);
	}

	public static void logTAFError(String note)
	// Logs an error.
	{
		String _note;
		if (logItem.equals("")) {
			_note = note;
		} else {
			_note = "<" + logItem + ">" + note;
		}
		if (!expectedErr.equals("")) {
			// if(!expectedErr.equals("")&&!errorHandledInLine){
			if (note.contains(expectedErr)) {
				logTAFInfo("Expected err Matched - '" + _note + "'");
			} else {
				logTAFWarning("Actual err - '" + _note + "', Expected err - '"
						+ expectedErr + "'");
			}
			errorHandledInLine = true;
		} else if (loggerConf.filterLevel != -1) {
			snapshotLink = updateTestInfo(_note, false);
			TAFLogger.getLogger().logScriptError(
					"\tError: " + formatSnapshot(_note, snapshotLink));
		}

	}

	public static void logTAFException(java.lang.Throwable e) {

		snapshotLink = updateTestInfo(e.getMessage(), false);
		logTAFError(formatSnapshot(e.getMessage(), snapshotLink));
		if (loggerConf.filterLevel >= 1) {
			if (loggerConf.filterLevel > 5){
				e.printStackTrace();
			}else if (loggerConf.filterLevel > 4){
				unwindException(e).printStackTrace();
			}else {
				
			}
		}

	}
    public static void logInfo(String info){
    	TAFLogger.getLogger().logScriptInfo(info);
    }
	public static void logTAFInfo(String note)
	// Logs an informational message.
	{
		if (loggerConf.filterLevel >= 3)
			TAFLogger.getLogger().logScriptInfo("\t   * " + note);
	}

	public static void logTAFStep(String stepInfo)
	// Logs a step message.
	{
		if (loggerConf.filterLevel >= 4)
			TAFLogger.getLogger().logScriptInfo("\tStep: " + stepInfo);
	}

	public static void logTAFDebug(String note)
	// Logs a debug message.
	{
		if (loggerConf.filterLevel >= 6)
			TAFLogger.getLogger().logScriptWarning("\tDebug Info: " + note);
	}

	public static void logTAFTestResult(String headline, boolean passed)
	// Logs a test result.
	{
		if (loggerConf.filterLevel != -1)
			logTAFTestResult(headline, passed, "");
	}

	public static void logTAFTestResult(String headline, boolean passed,
			String additionalInfo)
	// Logs a test result.
	{
		snapshotLink = updateTestInfo(headline, passed);
		if (headline.contains("Test Script :")) {
			snapshotLink = "";
		}
		if (loggerConf.filterLevel != -1) {
			// TAFLogger.getLogger().logScriptTestResult(headline,passed,additionalInfo);
			if (passed) {
				TAFLogger.getLogger().logScriptInfo(
						"\t"
								+ headline
								+ (additionalInfo.equals("") ? ""
										: "\n\t\t--Additional Info: "
												+ additionalInfo));
			} else {
				TAFLogger.getLogger().logScriptError(
						"\t"
								+ headline
								+ (additionalInfo.equals("") ? ""
										: "\n\t\t--Additional Info: "
												+ additionalInfo));
				// +(snapshotLink.equals("")? "" : "\n\t\t--Snapshot: " +
				// snapshotLink));
			}
		}

	}

	public static void logTAFWarning(String note)
	// Logs a warning.
	{
		String snapshotLink = snapshot(); // use local link to avoid any
											// possible side effect - Steven
		if (loggerConf.filterLevel >= 1)
			// TAFLogger.getLogger().logScriptWarning("\tWarning: "+note+
			// (snapshotLink.equals("")? "" : "\n\t\t--Snapshot: " +
			// snapshotLink));
			TAFLogger.getLogger().logScriptWarning(
					"\tWarning: " + formatSnapshot(note, snapshotLink));
	}

	public boolean onUnhandleException(Throwable e) {
		logTAFException(e);
		return false;
	}

	// Update test result- Pass/Fail and error message for scripts
	public static String updateTestInfo(String headline, boolean passed) {

		String firstLine = headline;
		String linkToSnapshot = "";
		int newlineStart = 0;
		if (headline != null) {
			if ((newlineStart = headline.indexOf(System
					.getProperty("line.separator"))) > 0) {
				firstLine = headline.substring(0, newlineStart);
			}

		}

		if (testResult.equalsIgnoreCase("None")) {
			testResult = passed ? "Pass" : "Fail";
			message = passed ? "" : firstLine;
			message += knownBugs.equals("") ? knownBugs : "[KnownBugs: "
					+ knownBugs + "]";
			logTAFDebug("Set testResult = " + testResult + ", message = "
					+ message);
		}

		if (testCaseResult.equalsIgnoreCase("None") && expectedErr.equals("")) {
			testCaseResult = passed ? "Pass" : "Fail";
			failMessage = passed ? "" : firstLine;
			logTAFDebug("Set testCaseResult = " + testCaseResult
					+ ", failMessage = " + failMessage);
		}

		if (testMainResult.equalsIgnoreCase("None") && expectedErr.equals("")) {
			testMainResult = passed ? "Pass" : "Fail";
			mainMessage = passed ? "" : firstLine;
			logTAFDebug("Set testMainResult = " + testMainResult
					+ ", mainMessage = " + mainMessage);
		}

		if (!passed) {
			linkToSnapshot = snapshot();
			if (!testResult.equalsIgnoreCase("Fail")) {
				testResult = "Fail";
				message = formatSnapshot(firstLine, linkToSnapshot);
				logTAFDebug("Set testResult = " + testResult + ", message = "
						+ message);
			}

			if (!testCaseResult.equalsIgnoreCase("Fail")
					&& expectedErr.equals("")) {
				testCaseResult = "Fail";
				failMessage = firstLine;
				logTAFDebug("Set testCaseResult = " + testCaseResult
						+ ", failMessage = " + failMessage);
			}
			if (!testMainResult.equalsIgnoreCase("Fail")
					&& expectedErr.equals("")) {
				testMainResult = "Fail";
				mainMessage = firstLine;
				logTAFDebug("Set testMainResult = " + testMainResult
						+ ", mainMessage = " + mainMessage);
			}
		}
		return linkToSnapshot;
	}

	public static int unregisterAllInAUT() {
		int numObjects =0;
		// TestObject[] tos;
		// String msg = "";
		//
		// tos = getRegisteredTestObjects();
		// numObjects = tos.length;
		// msg = numObjects+" registered objects found "+tos;
		// try{
		// unregister(tos);
		// unregisterAll();
		// logTAFDebug("Unregister '"+numObjects+"' RFT Test Objects successfully");
		// }catch(Exception e){
		// logTAFWarning("Exception thrown when unregister objects - "+e.toString());
		// }
		return numObjects;
	}

	public static String snapshot() {

		String savedSnapshot = TAFLogger.file + "/ScreenShots/errorSnapshot_"
				+ ++numSnapshots + ".jpeg";
//        savedSnapshot = "C:\\Users\\steven_xiang\\Desktop"+"/ScreenShots/errorSnapshot_"
//				+ ++numSnapshots + ".jpeg";
//		WebDriver driver = RootTestObject.getDriver();
//		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		captureScreen(savedSnapshot);
//		// Now you can do whatever you need to do with it, for example copy somewhere
//		try {
//			FileUtils.copyFile(scrFile, new File(savedSnapshot));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		savedSnapshot = InitializeTerminateHelper.processLink(savedSnapshot);
		// projectConf.logDirForPublic+savedSnapshot.replaceAll("Zipped_ACLQA_Automation_logs/",
		// "");
		return savedSnapshot;
	}

	public String getL10NExpression(String exp) {
		// Decimal Place Symbol . -> ,
		// Thousands Separator , -> .
		// List Separator , -> ;
		String enNumber = "(?i)EN|ZH|KO|JA";
		String euroNumber = "(?i)ES|PT|DE|FR|Pl";
		String specialNumber = "(?i)TBD";
		String l10nExp = exp;

		if (projectConf.appLocale.matches(euroNumber)) {

			l10nExp = exp.replaceAll("([0-9])[\\.]([0-9])", "$1::$2");
			l10nExp = l10nExp.replaceAll("([0-9]),([0-9])", "$1.$2");
			// l10nExp = l10nExp.replaceAll("(\\(.*),(.*\\))", "$1;$2");// Use ;
			// for list
			l10nExp = l10nExp.replaceAll(",", ";");// Use
			l10nExp = l10nExp.replaceAll("::", ",");

//		} else if (projectConf.appLocale.matches(specialNumber)) {
//			//
		} else {
			l10nExp = l10nExp.replaceAll(";", ","); // Use , for list
		}
		return l10nExp;
	}

	public String getDpString(String var) {
		String value = "";
		String localValue = "";
		String domain = "";
		String csvComma = "<<;>>";
		String devUser1 = "ACL\\acldbld";
		String devPass1 = "ncc1701a";

		String ACLExpressions = "(?i)PreFilter|Filter|Expression"
				+ "|If|While|On|Fields" + "|Minimum|Maximum" + "|Errors" + "";
		logTAFDebug("Trying to get value of '" + var + "'");

		try {
			value = dpString(var).replaceAll(csvComma, ",");
			if (value.matches("<lv_.*>")) { // Localization variables, need to
											// be converted
				// value = NLSUtil.convert2Locale(value);
				// value = value.replaceAll("<(lv_.+)>",
				// NLSUtil.convert2Locale("$1"));
				value = NLSUtil.i18nReplaceAll(value, "<(lv_.+)>");
			}

			if (var.matches(ACLExpressions)) {
				value = getL10NExpression(value);
			}
		} catch (Exception e) {
			value = "";
			logTAFDebug("No '" + var + "' found in your input data file");
		}
		if (value == null) {
			logTAFDebug(var + "=" + value + "' in your input data file?");
			value = "";
		}
		value = value.replaceAll("\\u0000", "").trim();

		// if work on ACL domain...
		if (var.equals("Username") && value.contains("\\")
				&& !value.toUpperCase().startsWith("ACLQA\\")
				&& !value.toUpperCase().startsWith("ACL\\")
				&& !projectConf.serverName.trim().equals("")) {
			domain = value.split("\\\\")[0];
			if (!projectConf.serverName.trim().equals(domain)) {
				localValue = value.replaceFirst(domain,
						projectConf.serverName.trim());
				logTAFDebug("User '"
						+ value
						+ "' has been converted to user with this server domain : "
						+ localValue);
				value = localValue;
			}
		}

		// UnicodeUtil.printHexString(value);
		logTAFDebug(var + "='" + value + "'");
		return value;
	}



	public static void stopApp() {
		stopApp(true);
	}

	public static void stopApp(boolean killProcess) {
        boolean stoped = false;
		if (app != null ) {
          if(app instanceof WebDriver){
        	  
        	  ((WebDriver) app).quit();
        	  suiteObj = null;
        	  caseObj = null;
        	  stoped = true;
        	  logTAFInfo("WebDriver has been closed");
          }

		}
//		if (!stoped&&killProcess) {
//			killProcess();
//
//		}

	}
	public static void killProcess() {
	   killProcess(imageName);
	}
	public static void killProcess(String imageName) {
		try {
			// In case of the app could not be closed normally, we force to kill
			// the process form OS
			// FileUtil.exeComm("taskkill /F /PID " + app.getProcessId() +
			// " /T");
			logTAFInfo("Kill '" + imageName + " with cmd 'taskkill /F /T /IM ");
			FileUtil.exeComm("taskkill /F /T /IM " + imageName);
			sleep(1);
		} catch (Exception e) {

		}
	}

	public void checkMemory() {
		// logTAFDebug("projectConf.stopIfNumConsecutiveFailures = "+projectConf.stopIfNumConsecutiveFailures);
		// logTAFDebug("projectConf.maxMemUsage = "+projectConf.maxMemUsage);
		if (!projectConf.traceMemusage) {
			// logTAFDebug("\t Tracing memusage is not enabled");
			return;
		}

		if (mt.getCurrentMemusage() > projectConf.maxMemUsage) {
			stopApp();
			logTAFWarning("The memusage is " + mt.getCurrentMemusage()
					+ "K which is higher than " + projectConf.maxMemUsage
					+ "K ,\n\t\t" + " current process should have been killed");
		}
	}

	public boolean isAutomationIssue(String msg) {
		String nonErrors = //"All previously saved.*"
				// ".*Do you want to save the changes.*" +
				//+ "|.*Namespace Tree Control.*"
				//+ "|.*will attempt to harmonize these fields.*"
				//+ "|.*Do you wish to continue.*"
				//+ "|.*Do you want to proceed.*"
				//+ "|.*Do you want to continue.*"
				//+ "|.*Do you still want to use.*"
				//+ "|.*is from a previous version.*"
				//+ "|.*Options file missing.*" + "|.*Are you sure you want to.*"
				//+ "|.*ACL preferences file.*" +
				// "|.*This may take some time.*"+
				// "|.*Delete.*"+
				"";
		String nonErrors_cons = "";//"Automatic Updates" + "|Edit";
		String[] autoPattern = {
				"UnhandledException",
				//"java.",
				"noplausiblecandidate",
				"ObjectNotFoundException",
				"ApplicationNotResponding",
				//"Unable to open",
				//"The above file name is invalid",
				//"You cannot open a",
				//"This project is from.*edition of",
				//"Correct the problem and try again",
				//"Window is disabled",
				//"Failed to connect to server",
				//"server connection",
				//"Server Activity",
				//"Project name", // In case of invalid name used somehow
				//"Invalid field data",
				//"null window",
				//"Not found",
				//"Expected error",
				autoIssue,

				// these are mainly for l10n testing,preventing wrong alert --
				// Steven

				//"All previously saved", "Do you want to save the changes",
				//"Namespace Tree Control",
				//"will attempt to harmonize these fields",
				//"Do you wish to continue", "Do you want to proceed",
				//"Do you want to continue", "Do you still want to use",
				//"is from a previous version", "Options file missing",
				//"Are you sure you want to", "ACL preferences file",
				//"Automatic Updates", "Edit"

		};
		boolean isauto = false;
		for (String key : autoPattern) {
			if (msg.toUpperCase().contains(key.toUpperCase())) {
				isauto = true;
				if (key.equalsIgnoreCase("null window")) {
					testInterrupted = true;
				}
				break;
			}
		}
		// logTAFWarning("^^^^^^^^^^'"+msg+"' contains ^^^^^^^^^^^^^'"+autoPattern+"'  '"+isauto+"'");
		return isauto;
	}

	public void setDefaultUser(String user) {
		setDefaultUser(user, "Password00");
	}

	public void setDefaultUser(String user, String pass) {
		if (userName.equalsIgnoreCase("Unknown")) {
			userName = user;
			password = pass;
		}
	}

	public static String getSystemProperty(String key,String defaultValue) {
		String value = "";
		try {
			value = System.getProperty(key);
		} catch (Exception e) {
			value = "";
		}

		if (value == null) {
			value = "";
		} else {
			value = value.replaceAll("\"", "");
		}
		//System.out.println(key+" - conf:cmd = '"+defaultValue+"':'"+value+"'");
		if(value.trim()==""){
			value = defaultValue;
		}
		
		//System.out.println(key+" = '"+value+"'");
		return value.trim();
	}

	// ********* If we want to test automation in ACL, do something
	// autoamtically
	public String setUserNPassForDevServer(String var, String value) {
		return setUserNPassForDevServer(var, value, "", "");
	}

	public String setUserNPassForDevServer(String var, String value,
			String devUser, String devPass) {
		String userCol = "Username", passCol = "Password";
		String qaUser = "Manager";
		String localPass = "Password00";
		if (devUser.equals(""))
			devUser = projectConf.serverName + "\\" + qaUser;
		if (devPass.equals(""))
			devPass = "Password00";

		if (var.equalsIgnoreCase(userCol)) {
			if (value.contains(qaUser)) {
				value = devUser; // for setup users
			} else if (!value.toUpperCase().matches("ACL\\.*")) {
				value = value.replaceAll("^.*\\?", "");
				value = projectConf.serverName + "\\" + value;
			}
		}

		if (var.equalsIgnoreCase(passCol)) {
			if (dpString(userCol).contains(qaUser)) {
				value = devPass; // for setup users
			} else if (!dpString(userCol).toUpperCase().matches("ACL\\.*")) {
				value = localPass;
			}
		}
		return value;
	}

	public static String getNewIssueTips() {
		String tips = "HINTS For possible causes :"
				+ "\r\n\t1. The ACLSE server not accessible at the time of test? "
				+ "\r\n\t2. Test machine had been locked?"
				+ "\r\n\t3. The master data is correct?  Or it's dynamic data that is supposed to be handled by automation?"
				+ "\r\n\t4. If answers are No to questions above, check the test log for details."
				+ "\r\n\t\t  - Error message listed here is the first error being caught during each single test,"
				+ "\r\n\t\t  - There might be more errors for each failed test in the text log."
				+ "\r\n\t\t 5. If it's a known (old) issue (bug) reported before, we need to label the test in data pool with a KNOWN ISSUE message";

		return tips;
	}

	public static String getNewIssueHints(String newIssue) {
		String hints;
		String bugPrefix = "^\\*\\*\\s\\[\\[.+\\]\\]";
		int issues = newIssue.split(bugPrefix).length;

		String cont = "\n**"
				+ colorNewIssue
				+ "<u>HINTS For possible causes : </u>"
				+ "\n*** 1. The ACLSE server not accessible at the time of test? "
				+ "\n*** 2. Test machine had been locked?"
				+ "\n*** 3. The master data is correct?  Or it's dynamic data that is supposed to be handled by automation?"
				+ "\n*** 4. If answers are No to questions above, check the test log for details."
				+ "\n****  - Error message listed here is the first error being caught during each single test,"
				+ "\n****  - There might be more errors for each failed test in the text log."
				+ "\n*** 5. If it's a known (old) issue (bug) reported before, we need to label the test in data pool with a KNOWN ISSUE message"
				+ closeTag;

		if (issues > 2 && newIssue.contains("[[")) {
			hints = "\n*"
					+ colorNewIssue
					+ " New Issues?: "
					+ getString(bugNumN)
					+ issues
					+ " failures detected during this test"
					+ closeTag
					+ "\n**"
					+ colorNewIssue
					+ " Need further analysis to identify possible bugs,error details are listed in 'Test Details'.  "
					+ closeTag + cont + "";

		} else {
			hints = "\n*" + colorNewIssue + "   New Issues?:  "
					+ getString(bugNumN)
					+ newIssue.replaceAll("\\n.*Snapshot:.*", "") + closeTag +
					// cont+
					"";
		}

		return hints;
	}

	public boolean isValidBuild(String buildInfo) {
		String cch = "", reverse = "!";
		String validLocale = cch + "En" + "|" + cch + "Zh|" + cch + "Ch|" + cch
				+ "Cn" + "|" + cch + "Pl" + "|" + cch + "Ko" + "|" + cch
				+ "Ja|" + cch + "Jp" + "";
		String validEuroLocale = cch + "De" + "|" + cch + "Es" + "|" + cch
				+ "Pt" + "|" + cch + "Fr" + "";

		String validBranch = "-RC" + // may be needed on future releases -
										// Steven
				"|-Dev" + "";
		boolean isValidBuild = false;
		boolean isValidLocale = false;
		boolean isValid = false;
		String curBuild = projectConf.testProject.trim();
		String curLocale = projectConf.appLocale;
		String builds[] = buildInfo.split(",");
		if (buildInfo.trim().equals(""))
			return true;
		if (buildInfo.trim().equalsIgnoreCase("All")) {
			return true;
		}
		for (int i = 0; i < builds.length; i++) {
			String buildArray[] = builds[i].split("_");
			isValidBuild = false;
			if (buildArray[0].equalsIgnoreCase("All")
					|| buildArray[0].equalsIgnoreCase(curBuild)
					|| (buildArray[0] + "-RC").equalsIgnoreCase(curBuild)) {
				isValidBuild = true;
			}
			isValidLocale = false;
			if (buildArray.length == 1) {
				isValidLocale = true;
			} else if (buildArray[1].matches("(?i)" + cch + curLocale)) {
				isValidLocale = true;
			} else if (buildArray[1].startsWith(reverse)) {
				if (!buildArray[1].matches("(?i)" + reverse + cch + curLocale)) {
					isValidLocale = true;
				}
			}
			if (isValid = isValidBuild && isValidLocale) {
				return isValid;
			}
		}
		return isValid;
	}

	public static String getString(int num) {
		String str = "";
		String space = " ";

		if (num == 0)
			return str;
		return space + num + space;
	}

	public void cleanBugHistory() {
		fixedBugs = "";
		remainingBugs = "";
		newBugs = "";
		automationBugs = "";

		numFBugs = 0;
		numRBugs = 0;
		numNBugs = 0;
		numABugs = 0;
	}

	public boolean onUnhandledException(java.lang.Throwable e) {
		errorHandledInLine = false;

		logTAFException(e);
//		if (e instanceof com.rational.test.ft.sys.ApplicationNotRespondingException) {
//			sysExceptionCaught = true;
//			stopApp();
//			return true;
//		} else if (e instanceof com.rational.test.ft.RationalTestError
//				|| 
		if(e instanceof java.lang.NoClassDefFoundError) {
			sysExceptionCaught = true;
			stopApp();
			return false;
		}
		return true;
	}

	public static void putToCache(Cache cache, String id, String value) {

		if (cache == null) {
			logTAFWarning("Cache not configured yet? found when put'" + id
					+ "' = '" + value + "'");
			return;
		}
		try {
			cache.put(id, value);
		} catch (Exception e) {
			logTAFWarning("Put to Cache: '" + cache + " - '" + id + "' = '"
					+ value + "'  " + e.toString() + "'");
		}
		// logTAFInfo("Put to Cache: '"+cache+" - '"+id+"' = '"+value+"'");
	}

	public static String getFromCache(Cache cache, String id) {

		if (cache == null) {
			// logTAFWarning("Cache not configured yet?");
			return null;
		}

		String value = null;
		try {
			value = cache.get(id).toString();
		} catch (Exception e) {
			value = null;
			// e.printStackTrace();
		}
		// logTAFInfo("Get from Cache: '"+cache+" - '"+id+"' = '"+value+"'");
		return value;
	}


		  /**
		   * Looks up and returns the root cause of an exception. If none is found, returns
		   * supplied Throwable object unchanged. If root is found, recursively "unwraps" it,
		   * and returns the result to the user.
		   */
		  public static Throwable unwindException(Throwable th) {
		      if (th instanceof SAXException) {
		          SAXException sax = (SAXException) th;
		          if (sax.getException() != null) {
		              return unwindException(sax.getException());
		          }
		      }
		      else if (th instanceof SQLException) {
		          SQLException sql = (SQLException) th;
		          if (sql.getNextException() != null) {
		              return unwindException(sql.getNextException());
		          }
		      }
		      else if (th.getCause() != null) {
		          return unwindException(th.getCause());
		      }

		      return th;
		  }
		
	public LoggerHelper() {
		if (sysLineSep == null)
			sysLineSep = "\\n\\r";
		if (batchRun) {
			loggerConf.filterLevel = loggerConf.batchRunfilterLevel;
		}
	}
}