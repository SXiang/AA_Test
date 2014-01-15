package anr.keyword.frontend;

import anr.lib.frontend.QuickFilterHelper;


public class DataDisplayComparison  extends QuickFilterHelper{

	/**
	 * Script Name   : <b>DataDisplayComparison</b>
	 * Generated     : <b>Jan 3, 2014</b>
	 * Description   : Data Display Comparison keyword
	 * 
	 * @author Yousef Aichour
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpMasterFile;
	protected String dpColumnName;
	protected String dpFilterValues;
	protected String dpSortDirection;
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpFilterValues = getDpString("FilterValues");
		dpMasterFile = getDpString("MasterFiles");
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
			logTAFStep("Master File = " + dpMasterFile);	
			if(!dpMasterFile.isEmpty()) {
				result[0] = getDisplayedDataToBeCompared(9, 20, dpColumnName, dpFilterValues, dpSortDirection);					
				compareTxtResult(result[0], dpMasterFile);
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
