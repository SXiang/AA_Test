package ax.keyword.frontend;

import ax.lib.frontend.ResultsHelper;

public class Results  extends ResultsHelper{

	/**
	 * Script Name   : <b>Results</b>
	 * Generated     : <b>Oct 3, 2013</b>
	 * Description   : Results keyword
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
	protected String dpTableName; //@arg Table Name whose link to be clicked for Visualizer
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpProjectName = getDpString("ProjectName");
		dpTestSetName = getDpString("TestSetName");
		dpTestName = getDpString("TestName");
		dpTableName = getDpString("TableName");
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
			verifyTablesList();
			verifyParams();
			verifyInfoPanelContents();
			verifyProjectsDropDownList();
			verifyTestSetsDropDownList();
			verifyTestsDropDownList();
			verifyHeaderFooter();
		}
		if(!dpProjectName.isEmpty()){
			openProjectFromDropDown();
		}else if(!dpTestSetName.isEmpty()){
			openTestSetFromDropDown();
		}else if(!dpTestName.isEmpty()){
			openTestFromDropDown();
		}else if(!dpTableName.isEmpty()){
			openTableVisualizer();
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyTablesList(){
		String allResults = getResultsList();
		if(allResults.isEmpty()){
			logTAFError("No Result Found!!");
		}else{
			logTAFStep("Verify list of Results - " + dpMasterFiles[0]);
			//System.out.println("All Tests: "+allTests);
			result[0] = allResults; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyInfoPanelContents(){
		String info = getInfo();
		if(info.isEmpty()){
			logTAFError("Info panel not found!!");
		}else{
			//System.out.println("info: "+info);
			logTAFStep("Verify contents of Info panel - " + dpMasterFiles[3]);
			result[0] = info; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[3]);
		}
	}
	
	public void verifyParams(){
		String params = getParams();
		if(params.isEmpty()){
			logTAFError("No parameter set used");
		}else{
			//System.out.println("info: "+info);
			logTAFStep("Verify Parameters - " + dpMasterFiles[4]);
			result[0] = params; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[4]);
		}
	}
		
	public void verifyProjectsDropDownList(){
		String allProjects = getProjectsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No Projects Found!!");
		}else{
			//System.out.println("All Projects: "+allProjects);
			logTAFStep("Verify list of Projects - " + dpMasterFiles[5]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[5]);
		}
	}
	
	public void verifyTestSetsDropDownList(){
		String allTestSets = getTestSetsListFromDropDown();
		if(allTestSets.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[6]);
			result[0] = allTestSets; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[6]);
		}
	}
	
	public void verifyTestsDropDownList(){
		String allTests = getTestsListFromDropDown();
		if(allTests.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			logTAFStep("Verify list of Tests - " + dpMasterFiles[7]);
			result[0] = allTests; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[7]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"\r\n"+getProjectName()+"\r\n"+getTestSetName()+"\r\n"+getListHeader();
		String footer = getFooter();
		//System.out.println("header: "+header);
		//System.out.println("footer: "+footer);
		logTAFStep("Verify Project Header - " + dpMasterFiles[1]);
		result[0] = header; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[1]);
		logTAFStep("Verify Page Footer - " + dpMasterFiles[2]);
		result[1] = footer; // You need to get actual result for
											// each comparison
		compareTxtResult(result[1], dpMasterFiles[2]);
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
	
	public void openTableVisualizer(){
		clickTableDownloadIcon(dpTableName);
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
