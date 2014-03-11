package anr.keyword.frontend;

import anr.lib.frontend.QuickFilterHelper;

public class CriteriaFilter  extends QuickFilterHelper{

	/**
	 * Script Name   : <b>CriteriaFilter</b>
	 * Generated     : <b>Dec 11, 2013</b>
	 * Description   : CriteriaFilter keyword
	 * 
	 * @author Yousef Aichour
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be clicked on to open Quick filter
	protected String dpCriteriaFilterValues; //@arg type of filter: Select option from dropdown list and type the value to filter on
                                        // value = drop|option to select|value
	protected String dpClearFilters; //If T, all filters will be removed
	// END of datapool variables declaration
	
	private String criteriaFilterValue;
	private String criteriaFilterOption;
	private String criteriaFilterValueHalf1;
	private String criteriaFilterValueHalf2;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpCriteriaFilterValues = getDpString("CriteriaFilterValues");
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
		
		if(!dpColumnName.isEmpty()){
			clickColumnHeader(dpColumnName);
		}

		if(!dpCriteriaFilterValues.isEmpty()){		
			criteriaFilterOption = dpCriteriaFilterValues.split("\\|")[1];
			criteriaFilterValue = dpCriteriaFilterValues.split("\\|")[2];	
			
				//If criteria filter option is Between
				if(criteriaFilterOption.equals("Between")) {
					criteriaFilterValueHalf1 = criteriaFilterValue;
					criteriaFilterValueHalf2 = dpCriteriaFilterValues.split("\\|")[3];
					applyCriteriaFilter(criteriaFilterOption, criteriaFilterValueHalf1, criteriaFilterValueHalf2);	
				}
				else {
					applyCriteriaFilter(criteriaFilterOption, criteriaFilterValue);
				}
				
				//if(!dpMasterFile.isEmpty()) {
				//	result[0] = getDisplayedDataToBeCompared();					
				//	compareTxtResult(result[0], dpMasterFile);
				//}
	
			if (dpClearFilters.equalsIgnoreCase("T")) {
			removeAllFilters();
			}
		}
		//cleanUp();
	
		// *** cleanup by framework ***
		//onTerminate();
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
