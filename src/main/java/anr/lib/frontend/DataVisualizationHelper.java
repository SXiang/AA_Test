package anr.lib.frontend;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.Select; //added by yousef
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.String;

import ax.lib.frontend.FrontendCommonHelper;

public class DataVisualizationHelper extends FrontendCommonHelper{
	/**
	 * Script Name   : <b>DataVisualizationHelper</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : DataVisualizationHelper
	 * 
	 * @since  Oct 16, 2013
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	// END of datapool variables declaration

	// BEGIN locators of the web elements of DataVisualization page
	By quickSortPlusLocator = By.cssSelector("i.icon-plus");
	By quickSortDropDown = By.cssSelector("select[ng-model='table.sort.field']");
	By optionsLocator = By.cssSelector("select[ng-model='table.sort.field'] > option");
	By quickSortAscLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-ascending']");
	By quickSortDescLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-descending']");
	By sortElementsLocator = By.cssSelector("div#sort-section > div.row-fluid");
	By quickSortPanelContent = By.cssSelector(".collapse.in");
	By filterButtonlocator = By.cssSelector(".btn.btn-primary.static-tabs.filers-tab.pull-left.ng-scope.active");
	By firstColumnValues = By.cssSelector(".ngCell.col0.colt0");	
	By criteriaFilterSelect = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div > div > select");
	By criteriaFilterSelectOptions = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div > div > select > option");
	By criteriaFilterValue = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div:nth-child(2) > div > div > input");
	
		
	By filterBtnLocator = By.cssSelector(".static-tabs.filers-tab");
	By colHeaderLocator = By.cssSelector(".ngHeaderText");
	By quickFilterHeaderLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header']");
	By closeQuickFilterMenuLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header'] > i.icon-remove");
	By sortSectionLabelLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div.sort-header");
	By ascendingLinkLocator = By.cssSelector("button[btn-radio*='asc']");
	By descendingLinkLocator = By.cssSelector("button[btn-radio*='desc']");
	By quickFilterUniqueItemsLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span[ng-show^='item.value']");
	By quickFilterUniqueItemsCountLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span.value-count");
	By quickFilterSearchUniqueItemsBoxLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > input.search-filter-value");
	By quickFilterApplyBtnLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.apply-quick-filter > span");
	By quickFilterClearBtnLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.clear-quick-filter > span");
	By tableNameLocator = By.className("visualizer-page-header-title");
	By recordCountLocator = By.id("record-count");
	By tableHeaderLocator = By.cssSelector("div[id^='col']:nth-child(1)");
	By tableDataLocator = By.cssSelector("div[class^='ngCellText ng-scope']");
	By rowSelectedLocator = By.cssSelector("div[class*='selected'] > div[class*='col']");
	By tableViewTabLocator = By.cssSelector("tab-heading.chart-tabs > i.icon-table");
	By addChartBtnLocator = By.className("addchart-tab-text");
	By filterPanelHeaderLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div > div >div > div.filter-panel-header > span");
	By filterPanelColNamesLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div.filter-column-name");
	By filterPanelSortSectionLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div >  div > span");
	By filterPanelSortDropDownItemsLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div >  div > div > select.select-block > option");
	By filterPanelAscSortBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn[btn-radio*='asc']");
	By filterPanelDescSortBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn[btn-radio*='desc']");
	By filterPanelAscSortBtnActiveLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn.active[btn-radio*='asc']");
	By filterPanelDescSortBtnActiveLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn.active[btn-radio*='desc']");
	By filterPanelFilterToggledOffLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle.toggle-off");
	By filterPanelFilterToggledOnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle:not([class$='toggle-off'])");
	By filterPanelSearchFilterLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.search-filter:not([style='display: none;']) > input.search-filter-value");
	By filterPanelCheckboxCountLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value > span.value-count");
	By filterPanelCheckboxTextLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value");
	By filterPanelCheckedLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check:not([style='display: none;'])");
	By filterPanelUncheckedLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check-empty:not([style='display: none;'])");
	By filterPanelApplyFilterBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.action-btn-filter");
	By filterPanelClearFilterBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.clear-quick-filter");
	//END
    
    // BEGIN of other local variables declaration 
	protected List<WebElement> allTableColumns,allTableData;
	protected int recordCount;
	protected List<WebElement> allColumnHeaders;
	protected String allFilters;
	protected List<WebElement> allUniqueItemsCount;
	protected List<WebElement> allUniqueItems;
	protected String allFilterValues;
	protected String allColumns;
	
	
	//END
	
	protected List<WebElement> options;
	protected List<WebElement> firstColumnCells;
	//protected WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);

	
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
		isElementDisplayed(tableViewTabLocator, "Table View Tab");
		isElementDisplayed(addChartBtnLocator, "Add Chart Button");
		isElementDisplayed(filterBtnLocator, "Filter Button");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public String getTableName() {
		return driver.findElement(tableNameLocator).getText();
	}	

	public String getTableRecords() {
		String recordCount = driver.findElement(recordCountLocator).getText();

		return recordCount;
	}

	public String getTableData() {
		String alltabledata="";
		int initialDisplayRowCount = 0;
		List <WebElement> nextRow;

	    logTAFStep("Get the Table Data");
	    
 		//First get all table columns
 		allTableColumns = driver.findElements(tableHeaderLocator);
        for(int i = 0; i < allTableColumns.size(); i++) {
        	alltabledata +=" " + allTableColumns.get(i).getText();
       	}

        //Get the initial displayed table data since all table data cannot be loaded at one time for performance limits
        allTableData = driver.findElements(tableDataLocator);
        for(int i = 0; i < allTableData.size(); i++) {
        	int mod = i % (allTableColumns.size());
        	if (mod == 0) 
        		alltabledata += "\r\n" + allTableData.get(i).getText();
        	else
        		alltabledata += allTableData.get(i).getText() + " ";
       	}

        //Continue to get the left table data by pressing ARROW_DOWN key one row by one row 
        recordCount=getNumbers(getTableRecords());  
    	initialDisplayRowCount = allTableData.size()/allTableColumns.size();
 
    	allTableData.get(allTableData.size()-1).click();
    	Actions actions = new Actions(driver); 
    	actions.sendKeys(allTableData.get(allTableData.size()-1), Keys.ARROW_DOWN).perform();

        logTAFStep("Press ARROW_DOWN key to get the left table data");
        for (int j = initialDisplayRowCount; j < recordCount; j++) {
        	nextRow = driver.findElements(rowSelectedLocator);
        	
        	alltabledata +="\r\n";
        	for (int k =0; k < nextRow.size(); k++ ) {
        		alltabledata += nextRow.get(k).getText() + " " ;
    	    }
        	actions.sendKeys(nextRow.get(0), Keys.ARROW_DOWN).perform();
        }
        
        return alltabledata;
	}
	
	public void pressKeyboard(int KeyCode) {
		  Robot rb = null;
		  try {
		   rb = new Robot();
		  } catch (AWTException e) {
		   e.printStackTrace();
		  }
		  rb.keyPress(KeyCode);   // Press the button
		  rb.delay(100);     // delay of 100 ms

		  rb.keyRelease(KeyCode);  // Release the button

		  logTAFStep("Robot Keystrokes " + KeyCode);
	}
	
	public boolean clickColumnHeader(String columnName) {
		allColumnHeaders = driver.findElements(colHeaderLocator);
        for(int i = 0; i < allColumnHeaders.size(); i++) {
        	if(allColumnHeaders.get(i).getText().equalsIgnoreCase(columnName)){
        		allColumnHeaders.get(i).click();
        		return true;
        	}
        }
        return false;
	}	

	public void clickDescendingLink() {
		takeScreenshotWithoutScroll();
		driver.findElement(descendingLinkLocator).click();
	}
	
	public void clickAscendingLink() {
		takeScreenshotWithoutScroll();
		driver.findElement(ascendingLinkLocator).click();
	}
	
	public void clickSidePanelDescendingLink() {
		driver.findElement(filterPanelDescSortBtnLocator).click();
	}
	
	public void clickSidePanelAscendingLink() {
		driver.findElement(filterPanelAscSortBtnLocator).click();
	}
	
	public String getFilterPanelContents() {
		int itemSize;
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(filterPanelHeaderLocator));
		takeScreenshotWithoutScroll();
		allFilters = "@" + driver.findElement(filterPanelHeaderLocator).getText() + "@";
		allFilters = allFilters + "\r\n@" + driver.findElement(filterPanelSortSectionLocator).getText() + "@ ";
		/*need to fix
		if(driver.findElement(filterPanelSortDropDownSelectedItemLocator).getText().equals("")){
			allFilters = allFilters + "\r\n" + "Sort not applied";
		}else{
			
			allFilters = allFilters + "'" + driver.findElement(filterPanelSortDropDownSelectedItemLocator).getText();
			if(driver.findElements(filterPanelAscSortBtnActiveLocator).size()>0){
				allFilters = allFilters + "' : in Ascending order";
			}else if(driver.findElements(filterPanelDescSortBtnActiveLocator).size()>0){
				allFilters = allFilters + "' : in Descending order";
			}else{
				logTAFError("Sort order buttons not enabled");
			}
			
		}
	*/
		itemSize = driver.findElements(filterPanelColNamesLocator).size();
		if(itemSize>0){
			for(int i = 0; i < itemSize/2;i++){
				allFilters = allFilters + "\r\n" + driver.findElements(filterPanelColNamesLocator).get(i).getText();					
			}
		}
		return allFilters;
		
	}
	
	public String getUniqueValuesFromQuickFilter(){
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(quickFilterSearchUniqueItemsBoxLocator));
		takeScreenshotWithoutScroll();
		allUniqueItems = driver.findElements(quickFilterUniqueItemsLocator);
		allUniqueItemsCount = driver.findElements(quickFilterUniqueItemsCountLocator);
		for(int i =0;i < allUniqueItems.size();i++){
			if(i==0){
				allFilterValues = allUniqueItems.get(i).getText() + allUniqueItemsCount.get(i).getText();
			}
			allFilterValues = allFilterValues + "\r\n" + allUniqueItems.get(i).getText() + allUniqueItemsCount.get(i).getText();
		}
		return allFilterValues;
	}
	
	public String getAllColumnsFromDropDown(){
		//driver.findElements(filterPanelSortDropDownItemsLocator).
		return allColumns;
	}

}
