/**
 * 
 */
package anr.keyword.frontend;

import org.bouncycastle.util.Arrays;
import org.openqa.selenium.support.PageFactory;

import anr.apppage.CommonWebHelper;
import anr.apppage.FilterPanelPage;
import anr.apppage.QuickFilterPage;
import anr.apppage.DataVisualizationPage;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

/**
 * Script Name   : <b>QuickFilter_Steven.java</b>
 * Generated     : <b>9:25:51 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 20, 2013
 * @author steven_xiang
 * 
 */
public class QuickFilter  extends CommonWebHelper implements KeywordInterface {

	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be clicked on to open Quick filter
	protected String dpFilterValues; //@arg type of filter: whether typing in and then selecting values or selecting directly from checkbox
	                                  // value = on(off)(verify)|check|All|value1|value2|value3..
	                                  // value = on(off)(verify)|type|Text to type|All|value1|value2|value3...
                                        // value = on(off)(verify)|drop|option to select|value
	// END of datapool variables declaration
	
	// private String endWith for this filter: Apply|Clear|Dismiss
	// private String endWith for filter panel Apply|Clear|Dismiss|Delete
	private String actionType;
	private String action;
	//private String checkItems;
	private String[] filterValues;
	private String[] endValues;

	protected QuickFilterPage qfPage;
	
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpFilterValues = getDpString("FilterValues");
		filterValues = dpFilterValues.split("\\|");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		
		qfPage = PageFactory.initElements(driver, QuickFilterPage.class);
		
		qfPage.activateTable();
		
		if(!dpColumnName.isEmpty()){
			openQuickFilterMenu();
		}
		
		if(!dpFilterValues.isEmpty()){
			
			action = filterValues[0];
			actionType = filterValues[1];
			if(actionType.equalsIgnoreCase("check")){
				qfPage.selectCheckBox(filterValues,2,action);
			}else if(actionType.equalsIgnoreCase("type")){
				qfPage.searchValue(filterValues[2],action);
				qfPage.selectCheckBox(filterValues,3,action);
			}else if(actionType.equalsIgnoreCase("drop")){
				if(!action.equalsIgnoreCase("off")){
				   qfPage.setCriteria(filterValues,2,action);
				}else{
					
				}
			}
		}
		qfPage.endWith(dpEndWith);

		if(!dpMasterFiles[0].isEmpty()){
			verifyResultTable();
		}	
		
		endValues = dpEndWith.split("\\|");
		cleanUp(endValues[endValues.length-1]);
		
		// *** cleanup by framework ***
		onTerminate();
	}
	
	// *************** Part 3 *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	
	public void openQuickFilterMenu(){
		qfPage.clickColumnHeader(dpColumnName);
	}
	


		
	public void verifyResultTable(){
		int numRecords = 20;
		String result = qfPage.getTableData(numRecords);  // to-do
			logTAFStep("Verify resulted table from QuickFilter(first "+numRecords+" records - " + dpMasterFiles[0]+")");			
			compareTxtResult(result, dpMasterFiles[0]);

	}
	
	public QuickFilter() {
		// TODO Auto-generated constructor stub
	}

}
