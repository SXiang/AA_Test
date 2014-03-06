package anr.keyword.frontend;
import anr.lib.frontend.QuickFilterHelper;
public class Sort  extends QuickFilterHelper {

	/**
	 * Script Name   : <b>Sort</b>
	 * Generated     : <b>Dec 2, 2013</b>
	 * Description   : Sort keyword
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
	protected String dpMasterFile;
	
	private boolean foundIt = false;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpSortDirection = getDpString("SortDirection");
		dpMasterFile = getDpString("MasterFiles");
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
		if(!dpSortDirection.isEmpty() && foundIt){
			/*if(dpSortDirection.equalsIgnoreCase("desc")){
				sortDescending();
			}
			else if(dpSortDirection.equalsIgnoreCase("asc")){
				sortAscending();
			}*/
			logTAFStep("Column Name = " + dpColumnName);
			quickSort(dpSortDirection, "other");
			
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void openQuickFilterMenu(){
		foundIt = clickColumnHeader(dpColumnName);
		if(foundIt){
			logTAFStep("Column '"+dpColumnName+"' found and clicked on");
		}else{
			logTAFError("Column '"+dpColumnName+"' NOT found");
		}
	}
	
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
