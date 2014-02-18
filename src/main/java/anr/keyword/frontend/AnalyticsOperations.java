package anr.keyword.frontend;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

//Copy from Steven
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import anr.apppage.ANR_AnalyticPage;
import anr.lib.frontend.ANR_FrontendCommonHelper;
import anr.apppage.QuickFilterPage;
//Copy End

public class AnalyticsOperations extends ANR_FrontendCommonHelper implements KeywordInterface{
	
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

	// private String endWith for Run|Cancel|Verify;
	private String[] setValues;
	private String[] endValues;

	protected ANR_AnalyticPage anPage,an;
	protected SearchContext sc;
	String action = "";
	int startIndex = -1;
	
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

		//Set the current analytics WebElement as the search context root for the page
		anPage = PageFactory.initElements(driver, ANR_AnalyticPage.class);
		an = PageFactory.initElements(driver, ANR_AnalyticPage.class);
		sc = anPage.locateAnalytic(dpAnalyticName);

		if (sc!=null) {
			PageFactory.initElements(new DefaultElementLocatorFactory(sc), an);			
       
			//Verify analytic description
			verifyAnalyticDescription(dpAnalyticName);
			
			//Start analytic operations
			if(setParameterSet()){
				an.endWith(dpEndWith);
	        }else{
	        	an.cancelAnalytic();
	        }
		} else {
			   logTAFError("Analytic '" + dpAnalyticName + "' cannot be found!");
		}
		
		endValues = dpEndWith.split("\\|");
		cleanUp(endValues[endValues.length-1]);
		
		// *** cleanup by framework ***
		onTerminate();
	}
	
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	
	public void verifyAnalyticDescription(String analyticName){
		//Expand analytic by clicking analytic Run Icon
		an.openAnalytic(dpAnalyticName);

		String desc = an.getAnalyticDescription();
		logTAFStep("Verify Description of Analytic '" +analyticName+"' - "+ dpMasterFiles[0]);
		result[0] = desc;       // You need to get actual result for each comparison
		compareTxtResult(result[0], dpMasterFiles[0]);
	}
	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	public boolean setParameterSet(){
		boolean done = true;

		//analytic without Parameters
		if(startIndex<0)     
			return done;
		
		//analytic with Parameters
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
}