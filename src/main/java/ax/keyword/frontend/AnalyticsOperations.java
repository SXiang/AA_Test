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
	protected String dpAnalyticOperation; //@arg Operation to be performed on Analytic, if nothing specified, it will open Description of analytic
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpAnalyticName = getDpString("AnalyticName");
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
			if("run".equalsIgnoreCase(dpAnalyticOperation)){
				runAnalytic(dpAnalyticName);
			}else if("jobs".equalsIgnoreCase(dpAnalyticOperation)){
				verifyAnalyticJobsList(dpAnalyticName);
			}else if("viewResults".equalsIgnoreCase(dpAnalyticOperation)){
				viewResults(dpAnalyticName);
			}else if("schedules".equalsIgnoreCase(dpAnalyticOperation)){
				verifyAnalyticSchedulesList(dpAnalyticName);
			}else{
				verifyAnalyticDescription(dpAnalyticName);
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
	
	public void openRunDrawer(String analyticName){
		clickRunIcon(analyticName);
	}
	
	public void verifyAnalyticJobsList(String analyticName){
		clickJobsIcon(analyticName);
		String jobs = getJobsList();
		logTAFStep("Verify Jobs of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = jobs; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
	}
	
	public void verifyAnalyticSchedulesList(String analyticName){
		clickScheduleIcon(analyticName);
		String schedule = getScheduleList();
		logTAFStep("Verify Schedule of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = schedule; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
	}
	
	public void verifyAnalyticDescription(String analyticName){
		openRunDrawer(analyticName);
		String desc = getAnalyticDescription();
		logTAFStep("Verify Description of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = desc; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
	}
	
	public void runAnalytic(String analyticName){
		openRunDrawer(analyticName);
		clickRunBtn();
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
