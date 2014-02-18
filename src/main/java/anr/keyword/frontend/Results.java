package anr.keyword.frontend;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

import anr.apppage.ResultsPage;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import anr.lib.frontend.ANR_FrontendCommonHelper;

public class Results extends ANR_FrontendCommonHelper implements KeywordInterface{
	
	/**CommonHelper
	 * Script Name   : <b>DataTables</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : Results
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpAnalyticName;      //@arg Analytic name to view the results 
	private String dpTableName;         //@arg select a result table to open data visualization
	// END of datapool variables declaration

    // BEGIN of other local variables declaration
	protected ResultsPage rtPage,rt;
	protected SearchContext sc;

	//END
	
	//***************  Part 2  *******************
	// ******* Methods on initialization *********
	// *******************************************
	
	public boolean dataInitialization() {
		getSharedObj();
		super.dataInitialization();
		
		dpAnalyticName = getDpString("AnalyticName");
		dpTableName = getDpString("TableName");
		
		return true;
	}
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Set the current analytics WebElement as the search context root for the page
		rtPage = PageFactory.initElements(driver, ResultsPage.class);
		rt = PageFactory.initElements(driver, ResultsPage.class);
		sc = rtPage.locateAnalytic(dpAnalyticName);

		if (sc!=null) {
			PageFactory.initElements(new DefaultElementLocatorFactory(sc), rt);			
		
			//Verify results list 
			if(!dpMasterFiles[0].isEmpty()){
				verifyAllResultsList();
			}
			
			if (!dpTableName.isEmpty()) {
				OpenTable(dpTableName);
			} else rt.closeResultsView(dpAnalyticName);
		} else 	{
			   logTAFError("Analytic '" + dpAnalyticName + "' cannot be found!");
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	public void verifyAllResultsList(){
		rt.openResultsView(dpAnalyticName,true);
		
		String allResults = rt.getAllResultsList();
		logTAFStep("Verify Results list - " + dpMasterFiles[0]);
		
		if(allResults.isEmpty()){
			logTAFWarning("No Result Found in this project. Please check your data!!");
		}else{

			result[0] = allResults; // You need to get actual result for each comparison
			compareTxtResult(result[0], dpMasterFiles[0]);
		}
	}
	
	public void OpenTable(String tablename){
		rt.clickTableName(tablename);
	}

	public static void main(String args) {

	}
}
