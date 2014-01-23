/**
 * 
 */
package anr.keyword.frontend;

import org.openqa.selenium.SearchContext;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import anr.apppage.CommonWebHelper;
import anr.apppage.DataVisualizationPage;
import anr.apppage.FilterPanelPage;
import anr.apppage.QuickFilterPage;
import anr.apppage.SaveVisualizationPage;

import com.acl.qa.taf.helper.Interface.KeywordInterface;

/**
 * Script Name   : <b>FilterPanel_Steven.java</b>
 * Generated     : <b>3:14:58 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 31, 2013
 * @author steven_xiang
 * 
 */
public class FilterPanel extends CommonWebHelper implements KeywordInterface{

	/**
	 * 
	 */
	
	// *************** Part 1 *******************
	// ******* Declaration of variables **********
	// *******************************************
	// BEGIN of datapool variables declaration
	protected String dpColumnName; //@arg Name of the column that should be clicked on to open Quick filter
	protected String dpFilterValues; //@arg type of filter: whether typing in and then selecting values or selecting directly from checkbox
	                                  // value = on(off)(verify)|check|All|value1|value2|value3..
	                                  // value = on(off)(verify)|type|Text to type|All|value1|value2|value3...
                                        // value = on(off)(verify)(and/or)|drop|option to select|value
	protected String dpCurrentFilterValues; //@arg type of filter: whether typing in and then selecting values or selecting directly from checkbox
                                           // value = check|All|value1|value2|value3..
                                           // value = type|Text to type|All|value1|value2|value3...
                                           // value = and(or)|drop|option to select|value
	protected String dpLoadFrom;  //@arg load saved visulization - title
	protected String dpSaveTo;    //@arg save to title
	                              //value = title|link<true,false>

	// END of datapool variables declaration

	protected SaveVisualizationPage svPage;
	
	// private String endWith for filter panel Apply|Clear|Dismiss|Delete|Minimize|Disable|Enable|Delete
	private String actionType;
	private String action;
	private String _actionType;
	private String _action;
	//private String checkItems;
	private String[] filterValues;
	private String[] _filterValues;
	private String[] endValues;
	//protected String dpOpenCloseVerifyFilters; //@arg ProjectName whose link to be clicked for details
											// @value = open |  close | verify
	// END of datapool variables declaration
	
	protected FilterPanelPage fpPage;
	protected FilterPanelPage fp;
	protected SearchContext sc;
	@Override
	public boolean dataInitialization() {
		super.dataInitialization();
		// BEGIN read datapool
		dpColumnName = getDpString("ColumnName");
		dpFilterValues = getDpString("FilterValues");
		dpCurrentFilterValues = getDpString("CurrentFilterValues");
		filterValues = dpFilterValues.split("\\|");
		_filterValues = dpCurrentFilterValues.split("\\|");
        dpLoadFrom = getDpString("LoadFrom");
        dpSaveTo = getDpString("SaveTo");
		//dpOpenCloseVerifyFilters = getDpString("OpenCloseVerifyFilters");
		//END
		return true;
	}	
	
	// *************** Part 2 *******************
	// *********** Test logic ********************
	// *******************************************
	
	@Override
	public void testMain(Object[] args) {
		super.testMain(onInitialize(args, getClass().getName()));
		fpPage = PageFactory.initElements(driver, FilterPanelPage.class);
		fp = PageFactory.initElements(driver, FilterPanelPage.class);
		sc = fpPage.openFilterPanel(dpColumnName);
		PageFactory.initElements(new DefaultElementLocatorFactory(sc), fp);
		
		fp.scrollToFilter();
		if(!dpCurrentFilterValues.isEmpty()){		
			_action = "Verify";
			_actionType = _filterValues[1];
			if(_actionType.equalsIgnoreCase("check")){
				fp.selectCheckBox(_filterValues,2,_action);
			}else if(_actionType.equalsIgnoreCase("type")){
				fp.searchValue(_filterValues[2],_action);
				fp.selectCheckBox(_filterValues,3,_action);
			}else if(_actionType.equalsIgnoreCase("drop")){
				fp.verifyCriteria(_filterValues,2,_action);
				
			}
		}
		
		if(!dpFilterValues.isEmpty()){		
			action = filterValues[0];
			actionType = filterValues[1];
			if(actionType.equalsIgnoreCase("check")){
				fp.selectCheckBox(filterValues,2,action);
			}else if(actionType.equalsIgnoreCase("type")){
				fp.searchValue(filterValues[2],action);
				fp.selectCheckBox(filterValues,3,action);
			}else if(actionType.equalsIgnoreCase("drop")){
				fp.setCriteria(filterValues,2,action);
				
			}
		}

		
		//*** Ending ... ***
		fpPage.endWith(dpEndWith);

		svPage.saveVisualization(dpSaveTo);
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
	
	public void verifyResultTable(){
		String result = fpPage.getTableData(20);  // to-do
			logTAFStep("Verify resulted table with filter panel (first 20 records - " + dpMasterFiles[0]+")");			
			compareTxtResult(result, dpMasterFiles[0]);

	}
		

	
	// *************** Optional ******************
	// ******* main method for quick debugging ***
	// *******************************************
	
	public static void main(String args) {

	}
	
}
