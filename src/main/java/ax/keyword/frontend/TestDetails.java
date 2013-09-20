package ax.keyword.frontend;

import ax.lib.frontend.TestDetailsHelper;

public class TestDetails  extends TestDetailsHelper{

	/**
	 * Script Name   : <b>TestDetails</b>
	 * Generated     : <b>Sep 11, 2013</b>
	 * Description   : TestDetails keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpProjectName; //@arg ProjectName whose link to be clicked for details
	protected String dpTestName; //@arg Test Name whose link to be clicked for details
	protected String dpTestSetName; //@arg TestSet Name whose link to be clicked for details
	protected String dpAnalyticName; //@arg Test Name whose link to be clicked for details
	// END of datapool variables declaration
	
	public String analyticName;
	public String actionOnAnalytic;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpProjectName = getDpString("ProjectName");
		dpTestSetName = getDpString("TestSetName");
		dpTestName = getDpString("TestName");
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
		if(!dpMasterFiles[0].isEmpty()){
			verifyAnalyticsList();
			verifyProjectsDropDownList();
			verifyTestSetsDropDownList();
			verifyTestsDropDownList();
			verifyDescriptionPanelContents();
			verifyInfoPanelContents();
			verifyHeaderFooter();
		}
		if(!dpProjectName.isEmpty()){
			openProjectFromDropDown();
		}else if(!dpTestSetName.isEmpty()){
			openTestSetFromDropDown();
		}else if(!dpTestName.isEmpty()){
			openTestFromDropDown();
		}else if(!dpAnalyticName.isEmpty()){
			String[] analyticOperationArr = dpAnalyticName.split("\\|");
			analyticName = analyticOperationArr[0];
			if(analyticOperationArr.length > 1){
				actionOnAnalytic = analyticOperationArr[1];
			}
			if("description".equalsIgnoreCase(actionOnAnalytic)){
				verifyAnalyticDescription(analyticName);
			}else if("run".equalsIgnoreCase(actionOnAnalytic)){
				runAnalytic(analyticName);
			}else if("jobs".equalsIgnoreCase(actionOnAnalytic)){
				verifyAnalyticJobsList(analyticName);
			}else if("schedules".equalsIgnoreCase(actionOnAnalytic)){
				verifyAnalyticSchedulesList(analyticName);
			}
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyAnalyticsList(){
		String allAnalytics = getAnalyticsList();
		if(allAnalytics.isEmpty()){
			logTAFError("No Analytic Found!!");
		}else{
			verifyAnalyticsIcons();
			logTAFStep("Verify list of Analytics - " + dpMasterFiles[0]);
			//System.out.println("All Tests: "+allTests);
			result[0] = allAnalytics; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyProjectsDropDownList(){
		String allProjects = getProjectsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No Projects Found!!");
		}else{
			//System.out.println("All Projects: "+allProjects);
			logTAFStep("Verify list of Projects - " + dpMasterFiles[7]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[7]);
		}
	}
	
	public void verifyTestSetsDropDownList(){
		String allTestSets = getTestSetsListFromDropDown();
		if(allTestSets.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[8]);
			result[0] = allTestSets; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[8]);
		}
	}
	
	public void verifyTestsDropDownList(){
		String allTests = getTestSetsListFromDropDown();
		if(allTests.isEmpty()){
			logTAFError("No Tests Found!!");
		}else{
			logTAFStep("Verify list of Tests - " + dpMasterFiles[?]);
			result[0] = allTests; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[?]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"|"+getProjectName()+"|"+getTestSetName()+"|"+getTestName()+"|"+getCategoriesName();
		String footer = getFooter();
		//System.out.println("header: "+header);
		//System.out.println("footer: "+footer);
		logTAFStep("Verify Headers - " + dpMasterFiles[1]);
		result[0] = header; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[1]);
		logTAFStep("Verify Page Footer - " + dpMasterFiles[2]);
		result[1] = footer; // You need to get actual result for
											// each comparison
		compareTxtResult(result[1], dpMasterFiles[2]);
	}
	
	public void openRunDrawer(String analyticName){
		//clickTestName(dpTestName);
	}
	
	public void verifyAnalyticJobsList(String analyticName){
		//clickTestName(dpTestName);
	}
	
	public void verifyAnalyticSchedulesList(String analyticName){
		//clickTestName(dpTestName);
	}
	
	public void verifyAnalyticDescription(String analyticName){
		openRunDrawer(analyticName);
		//clickTestName(dpTestName);
	}
	
	public void runAnalytic(String analyticName){
		openRunDrawer(analyticName);
		//clickTestName(dpTestName);
	}
	
	public void openTestFromDropDown(){
		clickTestNameFromDropDown(dpTestName);
	}
	
	public void openTestSetFromDropDown(){
		clickTestSetNameFromDropDown(dpTestSetName);
	}
	
	public void openProjectFromDropDown(){
		clickProjectNameFromDropDown(dpProjectName);
	}
	
	public void verifyDescriptionPanelContents(){
		String description = getDescription();
		if(description.isEmpty()){
			logTAFError("Description panel not found!!");
		}else{
			//System.out.println("description: "+description);
			logTAFStep("Verify contents of Description panel - " + dpMasterFiles[5]);
			result[0] = description; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyInfoPanelContents(){
		String info = getInfo();
		if(info.isEmpty()){
			logTAFError("Info panel not found!!");
		}else{
			//System.out.println("info: "+info);
			logTAFStep("Verify contents of Info panel - " + dpMasterFiles[6]);
			result[0] = info; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[6]);
		}
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
