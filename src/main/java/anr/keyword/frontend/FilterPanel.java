package anr.keyword.frontend;

import anr.lib.frontend.DataVisualizationHelper;


public class FilterPanel  extends DataVisualizationHelper{

	/**
	 * Script Name   : <b>FilterPanel</b>
	 * Generated     : <b>Dec 5, 2013</b>
	 * Description   : FilterPanel keyword to open/close filter panel and/or verify filter panel contents
	 * 
	 * @author Ramneet Kaur
	 */

	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpOpenCloseVerifyFilters; //@arg ProjectName whose link to be clicked for details
											// @value = open |  close | verify
	// END of datapool variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpOpenCloseVerifyFilters = getDpString("OpenCloseVerifyFilters");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		if(dpOpenCloseVerifyFilters.equalsIgnoreCase("open")){
			if(isFilterPanelClosed().equalsIgnoreCase("close")){
				clickFilterPanelBtn();
			}else{
				logTAFError("Filter panel already open");
			}
		}
		if(dpOpenCloseVerifyFilters.equalsIgnoreCase("verify")){
			if(isFilterPanelClosed().equalsIgnoreCase("close")){
				clickFilterPanelBtn();  // to open filter panel
			}
			verifyFilterPanelContents();
			clickFilterPanelBtn(); // to close filter panel
		}
		if(dpOpenCloseVerifyFilters.equalsIgnoreCase("close")){
			if(isFilterPanelClosed().equalsIgnoreCase("open")){
				clickFilterPanelBtn();
			}else{
				logTAFError("Filter panel already close");
			}
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public void verifyFilterPanelContents(){
		String filterPanelContents = getFilterPanelContents();  //to -do
		if(filterPanelContents.isEmpty()){
			logTAFError("Unable to read Filter Panel Contents");
		}else{
			logTAFStep("Verify Filter Panel Contents - " + dpMasterFiles[0]);
			System.out.println("All filterPanelContents: "+filterPanelContents);
			result[0] = filterPanelContents; // You need to get actual result for
											// each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
		

	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
