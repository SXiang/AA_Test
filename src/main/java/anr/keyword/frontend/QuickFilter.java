package anr.keyword.frontend;

import anr.lib.frontend.QuickFilterHelper;


public class QuickFilter  extends QuickFilterHelper{

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
                                        // value = drop|option to select|value
	protected String dpClearFilters; //If T, all filters will be removed
	// END of datapool variables declaration
	
	private String actionType;
	private String checkItems;
	
	
	private String criteriaFilterValue;
	private String criteriaFilterOption;
	private String criteriaFilterValueHalf1;
	private String criteriaFilterValueHalf2;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpFilterValues = getDpString("FilterValues");
		dpClearFilters = getDpString("ClearFilters");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		logTAFStep("Number of records - " + recordCount());
		
		if(!dpColumnName.isEmpty()){
			openQuickFilterMenu();
		}

		if(!dpFilterValues.isEmpty()){
			
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
			else {
				criteriaFilterOption = dpFilterValues.split("\\|")[1];
				criteriaFilterValue = dpFilterValues.split("\\|")[2];			
				if(criteriaFilterOption.equals("Between")) {
				criteriaFilterValueHalf1 = criteriaFilterValue;
				criteriaFilterValueHalf2 = dpFilterValues.split("\\|")[3];
				applyCriteriaFilter(criteriaFilterOption, criteriaFilterValueHalf1, criteriaFilterValueHalf2);	
				}
				else {
				applyCriteriaFilter(criteriaFilterOption, criteriaFilterValue);
				}
				logTAFStep("Number of records - " + recordCount());
				
			}
			if (dpClearFilters.equalsIgnoreCase("T")) {
			removeAllFilters();
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
		String allUniqueValues = getUniqueValuesFromQuickFilter();  // to-do
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
