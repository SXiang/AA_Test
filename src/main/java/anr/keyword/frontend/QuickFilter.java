package anr.keyword.frontend;

import anr.lib.frontend.DataVisualizationHelper;


public class QuickFilter  extends DataVisualizationHelper{

	/**
	 * Script Name   : <b>QuickFilter</b>
	 * Generated     : <b>Dec 11, 2013</b>
	 * Description   : QuickFilter keyword
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be clicked on to open Quick filter
	protected String dpFilterValues; //@arg type of filter: whether typing in and then selecting values or selecting directly from checkbox
	                                  // value = check|value1|value2|value3..
	                                  // value = type|Text to type|value1|value2|value3...
	// END of datapool variables declaration
	
	private String actionType;
	private String checkItems;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpFilterValues = getDpString("FilterValues");
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
			openQuickFilterMenu();
		}
		if(!dpFilterValues.isEmpty()){
			verifyUniqueValuesList();
			actionType = dpFilterValues.split("\\|")[0];
			if(actionType.equalsIgnoreCase("check")){
				checkItems = dpFilterValues.split("\\|")[1];
				filterUsingCheckboxes(checkItems);
			}
			else if(actionType.equalsIgnoreCase("type")){
				findFilteredValues(dpFilterValues.split("\\|")[1]);
				checkItems = dpFilterValues.split("\\|")[2];
				filterUsingCheckboxes(checkItems);
			}
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void openQuickFilterMenu(){
		clickColumnHeader(dpColumnName);
	}
	
	public void verifyUniqueValuesList(){
		String allUniqueValues = getUniqueValuesFromQuickFilter();
		if(allUniqueValues.isEmpty()){
			logTAFError("Unable to read Unique Values from QuickFilter");
		}else{
			logTAFStep("Verify Unique Values from QuickFilter - " + dpMasterFiles[0]);
			System.out.println("All Unique Values from QuickFilter: "+allUniqueValues);
			result[0] = allUniqueValues; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void filterUsingCheckboxes(String checkItems){
		
	}
	
	public void findFilteredValues(String searchText){
		
	}
		

	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
