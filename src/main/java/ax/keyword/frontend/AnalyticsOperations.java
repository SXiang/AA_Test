package ax.keyword.frontend;

import ax.lib.frontend.TestDetailsHelper;

public class AnalyticsOperations  extends TestDetailsHelper{

	/**
	 * Script Name   : <b>AnalyticsOperations</b>
	 * Generated     : <b>Oct 1, 2013</b>
	 * Description   : AnalyticsOperations keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpAnalyticName; //@arg Analytic Name whose link to be clicked for details
	protected String dpParameterSet; //@arg Analytic Name whose link to be clicked for details
	// END of datapool variables declaration
	
	
	private String analyticOperation;
	private String analyticName;
	private String[] allParameters;
	private String setName;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpAnalyticName = getDpString("AnalyticName");
		dpParameterSet = getDpString("ParameterSet");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		if(!dpAnalyticName.isEmpty()){
			analyticName = dpAnalyticName.split("\\|")[0];
			if(dpAnalyticName.contains("|")){
				analyticOperation = dpAnalyticName.split("\\|")[1];
				if("run".equalsIgnoreCase(analyticOperation)){
					runAnalytic(analyticName);
				}else if("jobs".equalsIgnoreCase(analyticOperation)){
					verifyAnalyticJobsList(analyticName);
				}else if("viewResults".equalsIgnoreCase(analyticOperation)){
					viewResults(analyticName);
				}else if("schedules".equalsIgnoreCase(analyticOperation)){
					verifyAnalyticSchedulesList(analyticName);
				}else{
					verifyAnalyticDescription(analyticName);
				}
			}else{
					verifyAnalyticDescription(analyticName);
			}
		}else{
			logTAFInfo("Analytic Name datatpool entry is empty");
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	
	public void verifyAnalyticJobsList(String analyticName){
		clickJobsIcon(analyticName);
		String jobs = getJobsList();
		logTAFStep("Verify Jobs of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = jobs; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
		clickJobsIcon(analyticName);
	}
	
	public void verifyAnalyticSchedulesList(String analyticName){
		clickScheduleIcon(analyticName);
		String schedule = getScheduleList();
		logTAFStep("Verify Schedule of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = schedule; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
		clickScheduleIcon(analyticName);
	}
	
	public void verifyAnalyticDescription(String analyticName){
		clickRunIcon(analyticName);
		String desc = getAnalyticDescription();
		logTAFStep("Verify Description of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = desc; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
		clickRunIcon(analyticName);
	}
	
	public void runAnalytic(String analyticName){
		clickRunIcon(analyticName);
		if(dpParameterSet.equalsIgnoreCase("")){
			clickRunBtn();
		}else{
			allParameters = dpParameterSet.split("\\|");
			if(allParameters.length > 1){
				runWithNewParameterSet(allParameters);
			}else if(allParameters.length == 1){
				runWithExistingParameterSet(allParameters);
			}
			
		}
		sleep(timerConf.waitToTakeScreenshot);
		takeScreenshotWithoutScroll();
	}
	
	public void viewResults(String analyticName){
		clickJobsIcon(analyticName);
		viewLatestJobResults();
	}

	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//TestSetDetails debug = new TestSetDetails();
		//debug.verifyTestsList();
		//debug.verifyProjectsDropDownList();
		//debug.verifyDescriptionPanelContents();.
		//debug.verifyInfoPanelContents();
		//debug.verifyHeaderFooter();
		//debug.openTestSetDetails();
		//debug.verifyUsersList();
	}
	
}
