/**
 * 
 */
package anr.keyword.frontend;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import anr.apppage.AnalyticPage;
import anr.apppage.CommonWebHelper;
import anr.apppage.QuickFilterPage;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

/**
 * Script Name   : <b>RunAnalytic.java</b>
 * Generated     : <b>3:00:24 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Jan 8, 2014
 * @author steven_xiang
 * 
 */
public class RunAnalytic extends CommonWebHelper implements KeywordInterface {

		// *************** Part 1 *******************
		// ******* Declaration of variables **********
		// *******************************************
		// BEGIN of datapool variables declaration
	protected String dpAnalyticName; //@arg Analytic Name whose link to be clicked for details
	protected String dpParameterSet; //@arg Analytic Name whose link to be clicked for details
	                                 // value = New(Exist)|SetName[|value1|value2|value3..]                              
	// END of datapool variables declaration
	
	// private String endWith for filter panel Run|Cancel|Verify;
	private String[] setValues;
	private String[] endValues;

	protected AnalyticPage anPage,an;
	protected SearchContext sc;
	String action = "";
	int startIndex = -1;
	
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
		
		@Override
		public void testMain(Object[] args) {
			super.testMain(onInitialize(args, getClass().getName()));
			anPage = PageFactory.initElements(driver, AnalyticPage.class);
			an = PageFactory.initElements(driver, AnalyticPage.class);
			sc = anPage.locateAnalytic(dpAnalyticName);
			
			PageFactory.initElements(new DefaultElementLocatorFactory(sc), an);			
           
            
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
//			int numRecords = 20;
//			String result = qfPage.getTableData(numRecords);  // to-do
//				logTAFStep("Verify resulted table from QuickFilter(first "+numRecords+" records - " + dpMasterFiles[0]+")");			
//				compareTxtResult(result, dpMasterFiles[0]);

		}

}
