package lib.acl.helper;

import java.util.Calendar;

import lib.acl.helper.sup.InitializeTerminateHelper;
import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.TAFLogger;
import lib.acl.util.DatapoolUtil;
import lib.acl.util.FileUtil;
import lib.acl.util.NLSUtil;

import org.eclipse.hyades.edit.datapool.IDatapool;

import ACL_Desktop.conf.beans.ProjectConf;
import conf.beans.FrameworkConf;
import conf.beans.TimerConf;

import com.rational.test.ft.datapool.DatapoolUtilities;
import com.rational.test.ft.object.interfaces.IWindow;
import com.rational.test.ft.script.RationalTestScript;

import conf.beans.FrameworkConf;

public class TestCaseSuperHelper extends InitializeTerminateHelper {

	private String keywordLine1 = "\t\n------     ";
	private String keywordLine2 = "     ------\n";
	private String kPrefix = ">>>";
	private String testResultLine;
	public  String expectedErrMessage ="";
	public String testScenario="";
	public String buildName = "";
	private boolean prtFailScenarioTitle = false;
	public String linkToKeywordDescription = "";
	public String runTest ="";
	public String dpTestCategory ="";
	public boolean unicodeOnly = false,
	               nonunicodeOnly = false,
	               skipTest=false; 
	public String subpathToKeyword="",
	              temp[];
	private Object ksh;
//	public String isUnicode = "";	//##D## new variable for unicode/non-unicode test, default as unicode
	
	public TestCaseSuperHelper(){
		//batchRun = false;
	}
	
	// Overloaded exeTestCase with a set of datapools
	// test is an array of [commands][poolFiles]
	
	public void exeTestCase(Object[] args, String[][] test, String testName){
		for(int i=0;i<test[0].length;i++){
			if(i==0){
				isMainScript = isMainScript();
			}else{
				isMainScript = false;
			}
			menuItem = test[0][i];
			
			//** Assume no project dependence between data pools  **
			projName = "";
			//*******************************************************
			
		    onInitialize(test[1][i], testName);
		    exeTestCase(args);
		    isMainScript = isMainScript();
		    menuItem = "";
		}
	}
	
	// exeTestCase for a single pool
	public void exeTestCase(Object[] args){
        if(args==null){
        	return;
        }
		int numConsecutiveFails = 0;
		if(testResultHeader==""){
			if(batchRun){
				testResultHeader="Test_No.,Test_Name,Keyword_No.,Keyword_Name,Test_Result,Test_Message";
			}else{
				testResultHeader = "Keyword_No.,Keyword_Name,Test_Result,Test_Message";
			}

			FileUtil.writeFileContents(FrameworkConf.tempCsvResult, testResultHeader);
		}
		dpReset();
		//setDataInPool(args);
		if(scriptStartTime == null){
			if(isMainScript)
				scriptStartTime = TAFLogger.timeOfTest;
			else 
				scriptStartTime = Calendar.getInstance().getTime().toString();
		}
        // ** Set for daily test range
		
		if(!isMainScript()){
     		if(!testCategory.equals("")){
     			startFromLine = 2;
				endAtLine = Integer.MAX_VALUE;
     		}
		}
		
		
		
		while (!dpDone() && currentLine<=endAtLine &&
				!stopScript)
		{
			skipTest = false;
			runTest = getDpString("Run_Test");
			logItem = "";
			// To select form 'Defect Daily, Smoke, or Regression
			if(batchRun){
			   dpTestCategory = getDpString("Test_Category");
			   skipTest = isNotInCategory(dpTestCategory);
			}
			
			
			//##D## new isUnicode variable added and assigned value
			//isUnicode = getDpString("Is_Unicode");
			keywordName = getDpString("Keyword").replaceAll("[\\\\/]",".");
			    pathToKeywordScripts = originalPathToKeywordScripts;
				temp = keywordName.split("[\\\\/\\.]");
				for(int i=0;i<temp.length-1;i++){
					pathToKeywordScripts +=temp[i]+".";
				}
				keywordName = temp[temp.length-1];

			buildName = getDpString("Build_Name");	
            knownBugs = getDpString("KnownBugs");
            expectedErr = getDpString("ExpectedErr");
              String ut = getDpString("UnicodeTest");
            if(!isValidBuild(buildName)){
            	skipTest = true;
            }
            if(ut.equalsIgnoreCase("True")){
            	unicodeOnly = true;
            	if(unicodeOnly&&!isUnicode){
            		skipTest = true;
            	}
            }else if(ut.equalsIgnoreCase("False")){
            	nonunicodeOnly = true;
            	if(nonunicodeOnly&&isUnicode){
            		skipTest = true;
            	}
            }
            if(ProjectConf.appLocale.matches("(?i)Ko|Pl")){  // Ko ACLSE is not available at this time - Steven
            	ProjectConf.testType = "LOCALONLY";
            	//sleep(1);
            	//logTAFWarning("\t!!! No korean aclse available, some tests may fail due to the failure of server connection!!!");
            }   

            String temp = getDpString("ProjectName");

            if(ProjectConf.testType.matches("(?i)SERVER")){   

            	if(temp.matches("(?i).+LOCAL(\\.ACL)?")){
            		skipTest = true;
            	}
            }            
            if(localOnlyTest){            	
            	if(getDpString("ProjectName").matches("(?i)(.+SERVER(\\.ACL)?)")){
            		skipTest = true;
            	}
            	if(keywordName.matches("(?i)servermenu"))
        			skipTest= true;
            }
            
            errorHandledInLine = false;
            currentLine++;
            logTAFDebug("Line : "+currentLine+" runTest = '"+runTest+"' keywordName = '"+keywordName+"'");
            

		    // ** For GWP, handle sleep time between keywords
            if ((runTest.matches("T|t")||runTest.equals(""))&&
					lineInRange(currentLine)&&
					"sleep".equalsIgnoreCase(keywordName)){ 
 				String sleepCounter = getDpString("reqArg0");
 				logTAFInfo("\n\tSleep [" + sleepCounter + "] seconds to wait for previous task complete ...\n");

 				sleep(Double.parseDouble(sleepCounter));

 			}
 		      if(currentLine==endAtLine)
 		    	  sleep(0);
   // ** Call keywords one by one
            if ((runTest.matches("T|t")||runTest.equals(""))&&
					!keywordName.startsWith("#") && 
					!"".equalsIgnoreCase(keywordName)&&
					lineInRange(currentLine)&&
					!"sleep".equalsIgnoreCase(keywordName)&&
					!skipTest){ 
				
				try{
					testScenario = getDpString("Test_Scenario").trim();					
				}catch(Exception e){
					testScenario = "";
				}
				if(!testScenario.equals("")){
					logTAFInfo("\n+++++ Test "+testScenario+"\n");
				}
				
				try
				{  
					testResult = "None";
					message = "";

					//Call each keyword in the associated data pool	
					numKWs++;
					if(batchRun)
						testResultLine = testCaseLine+","+numKWs+","+keywordName;
					else
						testResultLine = numKWs+","+keywordName;

					
					logTAFTestResult(keywordLine1 +" "+numKWs+").Keyword: " + keywordName +"(line "+ currentLine +" in data pool) started - "+
							Calendar.getInstance().getTime().toString()+ keywordLine2,true);
                    currentkeyword = caseName+"(line "+ currentLine +")."+keywordName;
				    currentTestLine = currentLine;
				    
					// support call keywords other than in current project also
					if (keywordName.contains(".Tasks.")) { 
						logTAFDebug("Call: "+ keywordName);
						ksh = callScript(pathToKeywordScripts + keywordName, poolArgs);
					} else {
						//logTAFWarning("We don't currently support call keywords other than in current project - ");
						logTAFDebug("Call: "+ keywordName);
						//stopScript = true;
						ksh = callScript(pathToKeywordScripts + keywordName, poolArgs);
						unregisterAllInAUT();
					}
					
					
//					if(!errorHandledInLine){//is not empty, it's setup/changed by the keyword
//						   expectedErr = getDpString("ExpectedErr");
//	                }
					
	                logTAFDebug("ExpectedErr ='"+expectedErr+"'");
					if(!expectedErr.equals("")){						
						expectedErrMessage = "(Negative Test with expected error - "+expectedErr+")";						
					}else{
						expectedErrMessage = "";
					}
					if(errorHandledInLine)
						expectedErr = "";
					LoggerHelper.updateTestInfo("",true);
					
				}
				catch(Exception e) {
					logTAFException(e);
				}
				
//				logTAFDebug("test result before possible reverse, testResult ='"+testResult+"'");
				if(!errorHandledInLine&&!expectedErr.equals("")){ 
					
					if(testResult.equals("Pass")){
						testResult = "Fail";
						message = "Expected error does not apear - "+expectedErr;
						failMessage = message;						
					}else{						
						if(numKWsFail==0){							  
							// ***** For this test case
							testCaseResult = "Pass";
							failMessage = "";
							// ***** For batch run
							if(numTCsFail==0){
								mainMessage = "";
								testMainResult = "Pass";	
							}							
						}
						// ***** For this keyword
						testResult = "Pass"; 
						message = "";		
					}
					
				errorHandledInLine = false;
				expectedErr = "";
				logItem = "";
				
				}
				checkMemory();
														
				setDatapool("Recent_Test", testResult);
				setDatapool("Test_Message", message.replaceAll(",","|").replaceAll("\\n","--"));
				setDatapool("Test_Date",TAFLogger.timeOfTest);
				
				linkToKeywordDescription =projectName.replaceAll("^AX_|^ACL_", "")+"_Keyword_Definition#"+
                keywordName + "|" +keywordName;
				
				if(testResult=="Pass"){
					numConsecutiveFails = 0;
					kPrefix = ">>>";
				}else{
					numConsecutiveFails++;					
						kPrefix = "!!!";                    
					String msgTag="";
					if(knownBugs.equals("")){
						if(isAutomationIssue(message)){
						    msgTag = "\n***\t  [AUTOMATION ISSUE?]";
						}else{
							msgTag = "\n***\t  [NEW ISSUE?]";
						}
					}else{
						msgTag = "\n***\t  [KNOWN ISSUE:    "+knownBugs+"]";
					}
					keywordErrors = keywordErrors + 
					//"\n**\t\t[["+linkToKeywordDescription+" "+numKWs + ")]] " + 
					"\n**\t\t[["+linkToKeywordDescription+" - "+"line "+ currentLine +")]] " + 
					
					((prtFailScenarioTitle)? ("Scenario - '" + curTestScenario+"' ") : "") + message +msgTag;
					//(knownBugs.equals("")?"\n***\t  [NEW ISSUE?]":"\n***\t  [KNOWN ISSUE:    "+knownBugs+"]");
					message = message.replaceAll("\\*+", "");
					unKnownBugs = message;//.replaceAll("\\n.*Snapshot:.*","");

//					keywordErrors = keywordErrors + ((prtFailScenarioTitle)? ("\n\tScenario Failed: " + curTestScenario) : "") + 
//					"\n\t   -- " + keywordName + " " + numKWs + ") " + message;
					prtFailScenarioTitle = false;	// only print once
					
// *********  You can debug here whenever there is a keyword failure
					numKWsFail++; 
// *********  				
					
					if (numConsecutiveFails>=FrameworkConf.stopIfNumConsecutiveFailures){
						stopScript = true; // stop current test case if critical keywords fail
					}

				}
				
				testResultLine = testResultLine+","+testResult+","+message.replaceAll(",", "|").replaceAll("\\n","--");
//                logTAFDebug("Final test result for this keyword, testResult ='"+testResult+"'");

				if(!knownBugs.equals("")){
					if(testResult.equals("Pass")){
						kPrefix = ">>-"; // Pass: Fixed issue
						fixedBugs += "\n***\t[["+linkToKeywordDescription+ "]] "+knownBugs;
						numFBugs++;
					}else {
						kPrefix = ">>!"; // Warning : Known(old) issue
						remainingBugs += "\n***\t[["+linkToKeywordDescription+ "]] "+knownBugs;
						numRBugs++;
					}
				}else if(!testResult.equals("Pass")){
					if(isAutomationIssue(unKnownBugs)){
						kPrefix = ">!!";  // Fail: Automation issue
						automationBugs += "\n***\t[["+linkToKeywordDescription+ "]] "+unKnownBugs;
						numABugs++;
					}else{
						kPrefix = "!!!";  // Fail: New issue
					    newBugs += "\n***\t[["+linkToKeywordDescription+ "]] "+unKnownBugs;
					    numNBugs++;
					}
				}
				
				logTAFTestResult(kPrefix+" Keyword: " + 
						keywordName+ " " +
						(testResult==("Pass")? 
								testResult+"ed" +expectedErrMessage:testResult+"ed: " +
								message)+
								(knownBugs.equals("")?"":"\n\t  [KNOWN BUGS:    "+knownBugs+"]"),
								testResult=="Pass"?true:false);
				
				FileUtil.appendStringToFile(FrameworkConf.tempCsvResult, testResultLine);
				sleep(conf.beans.TimerConf.waitBetweenKeywords);

			}
			
			// get comment for current test scenario
			if (keywordName.startsWith(scenarioDesLinePrefix)) {
				curTestScenario = keywordName.substring(scenarioDesLinePrefix.length()).trim();
				prtFailScenarioTitle = ! "".equals(curTestScenario)? true : false;
			}
			
			try {
				((ACL_Desktop.AppObject.DesktopSuperHelper)ksh).saveProjectToServer();
			}catch(Exception e){
				//
			}
            if(individualTest&&!ProjectConf.singleInstance){
            	//stopApp();
            }
			      dpNext();
			

		} // End of while (all keywords)
		
		if(stopScript){
			logTAFInfo(stopMessage+" - stop remaining execution from this test suite");
		}

		if(testCaseResult == "Fail"){
			numTCsFail++;
		}

		scriptEndTime = Calendar.getInstance().getTime();
		
		String[] cases = caseName.split("\\.");
		String poolName = cases[cases.length-1];
		if(!batchRun){
			bugmessage = 
			(remainingBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorRemainBug+"   Remaining Issues:   "+closeTag+remainingBugs)+
			(fixedBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorFixedBug+"   Fixed Bugs:   "+closeTag+fixedBugs)+
			//(newBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorNewIssue+"   New Issues?:   "+closeTag+unKnownBugs)+
			(newBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorNewIssue+"   New Issues?:   "+closeTag+newBugs)+
			(automationBugs.equals("")?"":"\n* [["+poolName+"]]\n**"+colorAutomationIssue+"   Automation Issues?:   "+closeTag+automationBugs);
			cleanBugHistory();
//			fixedBugs = "";
//			newBugs = "";
//			automationBugs = "";
//			remainingBugs = "";
		}else{
			
		}
		
		
		numTestedKeywordInCase = numKWs;
    	logTAFTestResult(testSummary(testKeyword),true);
		numKWs=0;
		numKWsFail=0;
		scriptStartTime = null;

		//logTAFDebug("<><><><><>\t"+appMemoryUsage());
		// Use these two lines if you want to see test results in data pools		
//		DatapoolUtilities.storeCSV((IDatapool) poolArgs[0], poolCsvFile, ",", true);
//		DatapoolUtil.replaceData(poolCsvFile.getAbsolutePath(), "<NULL>", "",true);

	}
	private boolean isNotInCategory(String cate){
		boolean notInCate = false;
//		String autoRunTestCategory = null;
		String smoke = "Smoke",
		       regression = "Regression",
		       daily = "Daily",
		       defect = "Defect",
		       datatype = "DataType",
		       all = "All";
//		autoRunTestCategory = getSystemProperty(sysPropPrefix+"testCategory");
//		
////		try{
////		    autoRunTestCategory = System.getProperty(sysPropPrefix+"testCategory");
////		}catch(Exception e){
////			
////		}
//		if(autoRunTestCategory!=null&&autoRunTestCategory!=""){
//			testCategory = autoRunTestCategory;
//		}
		
		if(testCategory.equals("")){
			return notInCate;
		}
		
		if(currentLine==1){
			startFromLine = 2;
			endAtLine = Integer.MAX_VALUE;
		}
		
        if(testCategory.equalsIgnoreCase(smoke)){		
        	
        	if(cate.equals("")&&currentLine==1){ // For old data pools which haven't added field 'Test_Category' to
				 endAtLine = 4;
            }
			if(!cate.equalsIgnoreCase(daily)
					&&!cate.equalsIgnoreCase(smoke)
					&&!cate.equalsIgnoreCase(defect)
					&&!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(daily)){		
        	if(cate.equals("")&&currentLine==1){ // For old data pools which haven't added field 'Test_Category' to
				 endAtLine = 2;
           }
			if(!cate.equalsIgnoreCase(daily)
					//&&!cate.equalsIgnoreCase(defect)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(datatype)){		
			if(!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(defect)){		
			if(!cate.equalsIgnoreCase(defect)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(regression)){		
        	
			if(!cate.equalsIgnoreCase(daily)
					&&!cate.equalsIgnoreCase(smoke)
					&&!cate.equalsIgnoreCase(regression)
					&&!cate.equalsIgnoreCase(all)
					&&!cate.equalsIgnoreCase(defect)
					&&!cate.equalsIgnoreCase(datatype)
					){
				notInCate = true;
			}
		}else if(testCategory.equalsIgnoreCase(all)){  // All  --> in case we have other special cases added to data pool
			     //        --> or for debugging

		}else if(!testCategory.equalsIgnoreCase("")){
			notInCate = true;
		}
		return notInCate;
	}
}
	
