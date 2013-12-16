package anr.keyword.frontend;

import anr.lib.frontend.DataVisualizationHelper;


public class SortOnFilterPanel  extends DataVisualizationHelper{

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
			
		}
		if(!dpSortDirection.isEmpty()){
			if(dpSortDirection.equalsIgnoreCase("desc")){
				sortDescending();
			}
			else{
				sortAscending();
			}
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	

	
	public void sortDescending(){
		clickDescendingLink();
	}
	
	public void sortAscending(){
		clickAscendingLink();
	}
		

	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
