package anr.keyword.frontend;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

import anr.lib.frontend.DataVisualizationHelper;

public class DataVisualization extends DataVisualizationHelper implements KeywordInterface{
	
	/**
	 * Script Name   : <b>Search</b>
	 * Generated     : <b>Oct 14, 2013</b>
	 * Description   : OpenProject
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpTableName;
	// END of datapool variables declaration

    // BEGIN of other local variables declaration
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		dpTableName = getDpString("TableName");
		
		return true;
	}
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		if (!dpTableName.isEmpty()){
			
			verifyTableName();
			verifyTableRecords();
			verifyTableData();
		}

		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	public void verifyTableName(){
		String tablename = getTableName();
		if(tablename.isEmpty()){
			logTAFError("No Table name Found!!");
		}else{
			logTAFStep("Verify the Table Name");
			if (tablename.equalsIgnoreCase(dpTableName))
				logTAFInfo("Table name is displayed successfully. Expected TableName:"+dpTableName+" Actual TableName:"+tablename);
			else
				logTAFError("Table name is displayed incorrectly!! Expected TableName:"+dpTableName+" Actual TableName:"+tablename);
		}
	}
	
	public void verifyTableRecords(){
		String tablerecords = getTableRecords();
		
		if(tablerecords.isEmpty()){
			logTAFError("No Table Records Found!!");
		}else{
			logTAFStep("Verify the Table Records - " + dpMasterFiles[0]);
			result[0] = tablerecords; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void verifyTableData(){
		String tabledata = getTableData();
		
		if(tabledata.isEmpty()){
			logTAFError("No Table Data Found!!");
		}else{
			logTAFStep("Verify the Table Data - " + dpMasterFiles[1]);
			result[0] = tabledata; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[1]);
		}
	}

	public static void main(String args) {

	}
}
