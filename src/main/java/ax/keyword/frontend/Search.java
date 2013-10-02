package ax.keyword.frontend;

import ax.lib.frontend.FrontendCommonHelper;

public class Search  extends FrontendCommonHelper{
	
	/**
	 * Script Name   : <b>Search</b>
	 * Generated     : <b>Sep 6, 2013</b>
	 * Description   : Search
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of ProjectsList page
	
	//END
    
    // BEGIN of other local variables declaration
	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		return true;
	}
	
	@Override
	public void testMain(Object[] args) {
		dataInitialization();
		super.testMain(onInitialize(args, getClass().getName()));
		if(!dpSearchItems.isEmpty()){
			filterList();
			verifyElementsAfterFilter();
			verifySearchItemsList();
		}
		clearFilter();
		cleanUp();
		
		// *** cleanup by framework ***
		onTerminate();
	}

	//***************  Part 3  *******************
	// ******* Methods on verification ***********
	// *******************************************
	
	public void verifySearchItemsList(){
		String searchItems = getSearchItemsList();
		System.out.println("actual search items:"+searchItems+"*END");
		System.out.println("Expected search items:"+dpSearchItems+"*END");
		logTAFStep("Verify Search items list in Search box - ");
		String[] expSearchItemsArr = dpSearchItems.split("\\|");
		String[] actualSearchItemsArr = searchItems.split("\\|");
		Boolean identical = true;
		for(int i=0;i<expSearchItemsArr.length;i++){
			System.out.println("actual search item"+i+"'"+actualSearchItemsArr[i]+"'");
			System.out.println("Expected search item"+i+"'"+expSearchItemsArr[i]+"'");
			if(!expSearchItemsArr[i].equals(actualSearchItemsArr[i])){
				System.out.println("Following items didnt match -- actual search item"+i+"'"+actualSearchItemsArr[i]+"' and Expected search item"+i+"'"+expSearchItemsArr[i]+"'");
				identical = false;
			}
		}
		if(identical){
			logTAFInfo("Search box items match successfully with the searched items");
		}else{
			logTAFError("Search box items do not match with searched items!!!");
		}
	}
	
	public void clearFilter(){
		if(dpClearFilter.equalsIgnoreCase("yes")){
			logTAFStep("Clear Filter");
			super.clearFilter();
		}
	}
	
}
