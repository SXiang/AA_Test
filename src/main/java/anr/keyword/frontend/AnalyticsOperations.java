package anr.keyword.frontend;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

//Copy from Steven
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import anr.apppage.AnalyticPage;
import anr.apppage.CommonWebHelper;
import anr.apppage.QuickFilterPage;
import anr.apppage.WebPage;
//Copy End

public class AnalyticsOperations extends CommonWebHelper implements KeywordInterface{
	
	/**
	 * Script Name   : <b>OpenProject</b>
	 * Generated     : <b>Jan 25, 2013</b>
	 * Description   : AnalyticsOperations
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	private String dpAnalyticName;      //@arg the analytic name to run
	private String dpParameterSet;      //@arg the Parameter set for each value of PARAM
                                        // value = New(Exist)|SetName[|value1|value2|value3..]  
	// END of datapool variables declaration

    // BEGIN of other local variables declaration
	private String[] allParameters;
	
	//Copy from Steven
	// private String endWith for filter panel Run|Cancel|Verify;
	private String[] setValues;
	private String[] endValues;

	protected AnalyticPage anPage,an;
	protected SearchContext sc;
	String action = "";
	int startIndex = -1;
	//Copy End
	
	//END of other local variables declaration
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpAnalyticName = getDpString("AnalyticName");
		dpParameterSet = getDpString("ParameterSet");
		
  	    setValues = dpParameterSet.split("\\|");
 	    if(setValues.length>1){
		    action = setValues[0];
		    startIndex = 1;
	   }
	   //END
	   return true;

	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	//Copy from Steven
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		anPage = PageFactory.initElements(driver, AnalyticPage.class);
		an = PageFactory.initElements(driver, AnalyticPage.class);
		sc = anPage.locateAnalytic(dpAnalyticName);
		
		PageFactory.initElements(new DefaultElementLocatorFactory(sc), an);			
       
	/*Karen03/02/2014	if(!dpAnalyticName.isEmpty()){
			//verifyElementsEnabledOrDisplayed();  for RunIcon button and Result button status verification will be put to OpenProject keyword
			
			if(dpAnalyticName.contains("|")){
				analyticOperation = dpAnalyticName.split("\\|")[1];
				if("run".equalsIgnoreCase(analyticOperation)){
					runAnalytic(analyticName);
				}else if("viewResults".equalsIgnoreCase(analyticOperation)){
					viewResults(analyticName);
				}else{
					verifyAnalyticDescription(analyticName);
				}
			}else{
					verifyAnalyticDescription(analyticName);
			}
		}else{
			logTAFInfo("Analytic Name datatpool entry is empty");
		}
		cleanUp();
	
		// *** cleanup by framework ***
		onTerminate();
      */
		
		// To do: Adding a pSet verification here if needed -- Steven
        // verififyParameterSet();
        if(setParameterSet()){
		   an.endWith(dpEndWith);
        }else{
           an.cancelAnalytic();
        }

		if(!dpMasterFiles[0].isEmpty()){
			verifyResultView();
		}	
		
		endValues = dpEndWith.split("\\|");
		cleanUp(endValues[endValues.length-1]);
		
		// *** cleanup by framework ***
		onTerminate();
	}
	
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	
/*Karen 02/04	public void verifyAnalyticDescription(String analyticName){
		clickRunIcon(analyticName);
		String desc = getAnalyticDescription();
		logTAFStep("Verify Description of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = desc; // You need to get actual result for each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
		clickRunIcon(analyticName);
	}
	
	public void runAnalytic(String analyticName){
		clickRunIcon(analyticName);
		if(dpParameterSet.equalsIgnoreCase("")){
			clickRunBtn();
		}else{
			allParameters = dpParameterSet.split("\\|");
			if(allParameters.length > 1){
				runWithNewParameterSet(allParameters);
			}else if(allParameters.length == 1){
				runWithExistingParameterSet(allParameters);
			}
		}
		sleep(timerConf.waitToTakeScreenshot);
		takeScreenshotWithoutScroll();
	}
	
	public void viewResults(String analyticName){
		clickJobsIcon(analyticName);
		viewLatestJobResults();
	}
*/
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {
		//TestSetDetails debug = new TestSetDetails();
		//debug.verifyTestsList();
		//debug.verifyProjectsDropDownList();
		//debug.verifyDescriptionPanelContents();.
		//debug.verifyInfoPanelContents();
		//debug.verifyHeaderFooter();
		//debug.openTestSetDetails();
		//debug.verifyUsersList();
	}
	
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	
	public boolean setParameterSet(){
		boolean done = true;
		an.openAnalytic(dpAnalyticName);
		if(startIndex<0)
			return done;
		if(action.equalsIgnoreCase("New")){
		   if(dpExpectedErr.equals("")){
		      an.deleteParameterSet(dpAnalyticName,setValues[startIndex]);
		   }
		   
           done = an.setParameterSet(setValues,startIndex);
		}else if(action.equalsIgnoreCase("Exist")){
		   done = an.selectParameterSet(setValues,startIndex);
		}
		
		return done;
	}

	public void verifyParameterSet(){		
      // To do...
	}			
	public void verifyResultView(){
//		int numRecords = 20;
//		String result = qfPage.getTableData(numRecords);  // to-do
//			logTAFStep("Verify resulted table from QuickFilter(first "+numRecords+" records - " + dpMasterFiles[0]+")");			
//			compareTxtResult(result, dpMasterFiles[0]);

	}
	//Copy End
}