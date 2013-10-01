package ax.keyword.frontend;

import ax.lib.frontend.DataTablesRelatedFilesHelper;

public class DataTables  extends DataTablesRelatedFilesHelper{

	/**
	 * Script Name   : <b>DataTables</b>
	 * Generated     : <b>Sep 11, 2013</b>
	 * Description   : DataTables keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpTableName; //@arg Table name whose link to be clicked for description
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpTableName = getDpString("TableOrFileName");
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
			verifyProjectsDropDownList();
			verifyTestSetsDropDownList();
			verifyHeaderFooter();
		}
		if(!dpTableName.isEmpty()){
			verifyTableDesc(dpTableName);
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyTablesList(){
		String allTables = getTablesList();
		if(allTables.isEmpty()){
			logTAFError("No Table Found!!");
		}else{
			logTAFStep("Verify list of Tables - " + dpMasterFiles[0]);
			//System.out.println("All Tests: "+allTests);
			result[0] = allTables; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyTableDesc(String tableName){
		String desc = getTableDesc(tableName);
		logTAFStep("Verify Description of table '"+tableName+"' - " + dpMasterFiles[5]);
		result[0] = desc; // You need to get actual result for
											// each comparison
		compareTxtResult(result[0], dpMasterFiles[5]);
	}
	
	public void verifyProjectsDropDownList(){
		String allProjects = getProjectsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No Projects Found!!");
		}else{
			//System.out.println("All Projects: "+allProjects);
			logTAFStep("Verify list of Projects - " + dpMasterFiles[3]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[3]);
		}
	}
	
	public void verifyTestSetsDropDownList(){
		String allProjects = getTestSetsListFromDropDown();
		if(allProjects.isEmpty()){
			logTAFError("No TestSets Found!!");
		}else{
			logTAFStep("Verify list of TestSets - " + dpMasterFiles[4]);
			result[0] = allProjects; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[4]);
		}
	}
	
	public void verifyHeaderFooter(){
		String header = getProjectHeader()+"|"+getProjectName()+"|"+getTestSetName()+"|"+getTestHeader();
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
