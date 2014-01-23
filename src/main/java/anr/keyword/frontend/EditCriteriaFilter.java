package anr.keyword.frontend;

import anr.lib.frontend.QuickFilterHelper;


public class EditCriteriaFilter  extends QuickFilterHelper{

	/**
	 * Script Name   : <b>EditCriteriaFilter</b>
	 * Generated     : <b>Jan 2, 2014</b>
	 * Description   : EditCriteriaFilter keyword
	 * 
	 * @author Yousef Aichour
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be edited from filter configuration panel
	protected String dpFilterValues; //@arg type of filter: Select option from dropdown list and type the value to filter on
    									// value = drop|already selected|value|option to be selected|value
	protected String dpClearFilters; //If T, all filters will be removed
	// END of datapool variables declaration
	
	
	private String criteriaFilterFromValue;
	private String criteriaFilterFromValueHalf1;
	private String criteriaFilterFromValueHalf2;
	private String criteriaFilterFromOption;

	
	private String criteriaFilterToValue;
	private String criteriaFilterToOption;
	private String criteriaFilterToValueHalf1;
	private String criteriaFilterToValueHalf2;
	
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

		if(!dpColumnName.isEmpty()){
			//openQuickFilterMenu();
			clickFilterPanelBtn();
		}

		if(!dpFilterValues.isEmpty()){	
				criteriaFilterFromOption = dpFilterValues.split("\\|")[1];
				criteriaFilterFromValue = dpFilterValues.split("\\|")[2];
				
				if(criteriaFilterFromOption.equals("Between")) {
					criteriaFilterFromValueHalf1 = criteriaFilterFromValue;
					criteriaFilterFromValueHalf2 = dpFilterValues.split("\\|")[3];
					criteriaFilterToOption = dpFilterValues.split("\\|")[4];
					criteriaFilterToValue = dpFilterValues.split("\\|")[5];
					if(criteriaFilterToOption.equals("Between")) {
						criteriaFilterToValueHalf1 = criteriaFilterToValue;
						criteriaFilterToValueHalf2 = dpFilterValues.split("\\|")[6];
						//editQuickFilter(criteriaFilterFromOption, criteriaFilterFromValueHalf1, criteriaFilterFromValueHalf2, criteriaFilterToOption, criteriaFilterToValueHalf1, criteriaFilterToValueHalf2);	
					}
					else {				
						//editQuickFilter(criteriaFilterFromOption, criteriaFilterFromValueHalf1, criteriaFilterFromValueHalf2, criteriaFilterToOption, criteriaFilterToValue);	
					}
				}
				else {
					criteriaFilterToOption = dpFilterValues.split("\\|")[3];
					criteriaFilterToValue = dpFilterValues.split("\\|")[4];
					
					if(criteriaFilterToOption.equals("Between")) {
						criteriaFilterToValueHalf1 = criteriaFilterToValue;
						criteriaFilterToValueHalf2 = dpFilterValues.split("\\|")[5];
						editQuickFilter(dpColumnName, criteriaFilterFromOption, criteriaFilterFromValue, criteriaFilterToOption, criteriaFilterToValueHalf1, criteriaFilterToValueHalf2);	
					}
					else {				
						editQuickFilter(dpColumnName, criteriaFilterFromOption, criteriaFilterFromValue, criteriaFilterToOption, criteriaFilterToValue);	
					}
				}
				logTAFStep("Number of records - " + recordCount());
				
			}
			if (dpClearFilters.equalsIgnoreCase("T")) {
			removeAllFilters();
			}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
