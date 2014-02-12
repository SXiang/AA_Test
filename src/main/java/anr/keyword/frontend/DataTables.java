package anr.keyword.frontend;

import com.acl.qa.taf.helper.Interface.KeywordInterface;
import anr.lib.frontend.DataTablesHelper;

public class DataTables extends DataTablesHelper implements KeywordInterface{
	
	/**
	 * Script Name   : <b>DataTables</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProject
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpProjectFile;       //@arg the project file name to open 
	private String dpTableName;         //@arg select a table to open data visualization
	// END of datapool variables declaration

    // BEGIN of other local variables declaration
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		dpProjectFile = getDpString("ProjectFile");
		dpTableName = getDpString("TableName");
		
		return true;
	}
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Enter Project File with the full path
		if(!(dpProjectFile.isEmpty())) {
			verifyDataTablesHeader(dpProjectFile);
		}

		if(!dpMasterFiles[0].isEmpty()){
			verifyAllTablesList();
		}
			
		if (!dpTableName.isEmpty()) {
			OpenTable(dpTableName);
		}
			
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	public void verifyDataTablesHeader(String projectfile){
		String datatablesheader= getDataTablesHeader();
		
		logTAFStep("Verify Data Tables Header");
		if(datatablesheader.isEmpty()){
			logTAFError("Data Tables Header is empty!!!");
		}else{
			result[0] = datatablesheader;
			compareTxtResult(result[0],dpMasterFiles[0]);
		}
	}

	public void verifyAllTablesList(){
		String allTables = getAllTables();
		logTAFStep("Verify Tables list - " + dpMasterFiles[1]);
		
		if(allTables.isEmpty()){
			logTAFWarning("No Table Found in this project. Please check your data!!");
		}else{

			result[0] = allTables; // You need to get actual result for each comparison
			compareTxtResult(result[0], dpMasterFiles[1]);
		}
	}
	
	public void OpenTable(String tablename){
		clickTableName(tablename);
	}

	public static void main(String args) {

	}
}
