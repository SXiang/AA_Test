package anr.keyword.frontend;

import anr.lib.frontend.QuickFilterHelper;

public class SortOnFilterPanel  extends QuickFilterHelper {

	/**
	 * Script Name   : <b>SortOnFilterPanel</b>
	 * Generated     : <b>Dec 5, 2013</b>
	 * Description   : SortOnFilterPanel keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be clicked on to open Quick filter
	protected String dpSortDirection; //@arg whether want to sort Ascending or Descending
	                                  // value = asc | desc
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpSortDirection = getDpString("SortDirection");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		if(!dpColumnName.isEmpty()){
			if(isFilterPanelClosed().equalsIgnoreCase("close")){
				clickFilterPanelBtn();
			}
			//verifyColumnsDropDownList();
		}
		if(!dpSortDirection.isEmpty()){
			//if(dpSortDirection.equalsIgnoreCase("desc")){
				// sortDescending();
			if (!isFilterPanelClosed().equalsIgnoreCase("open")){
				clickFilterPanelBtn();
			}
			
			if(!isSortPanelClosed().equalsIgnoreCase("open")) {
				clickSortOnPlusSign();
			}
			    selectSortColumnFromSidePanelDropDown(dpColumnName);
				quickSort(dpSortDirection);
				logTAFStep("Number of records - " + numberOfRecords());
		/*	}
			else{
				sortAscending();
			}*/
		}
			
		//selectSortColumnFromSidePanelDropDown(dpColumnName);
		
		/* to be done
		result[0] = getAllDisplayedData();
		logTAFStep("Master file - " + dpMasterFile);
		logTAFStep("All filterPanelContents: \r" + result[0]);
		logTAFStep("Number of rows - " + recordCount());
		compareTxtResult(result[0], dpMasterFile);
		*/
		
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	

	public void verifyColumnsDropDownList(){
		String allColumns = getAllColumnsFromDropDown();
		if(allColumns.isEmpty()){
			logTAFError("Sort drop down column list is empty");
		}else{
			logTAFStep("Verify Sort dropdown column list - " + dpMasterFiles[0]);
			//System.out.println("All columns from Sort dropdown: "+allColumns);
			result[0] = allColumns; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}	
	
	/*public void selectColumnFromDropDown( String columnName){
		selectSortColumnFromSidePanelDropDown(columnName);

	}	*/
	

	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
