package anr.lib.frontend;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import anr.lib.frontend.ANR_FrontendCommonHelper;
import ax.lib.frontend.FrontendCommonHelper;

public class DataVisualizationHelper extends ANR_FrontendCommonHelper{
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
	By searchBtnLocator = By.cssSelector("i[class='icon-chevron-left icon-1x']");
	By filtersBtnLocator = By.cssSelector("div.static-tabs-text");
	By otherBtnLocator = By.cssSelector("ul[class^='dropdown-menu'] > li > a");

	By tableNameLocator = By.cssSelector("div[class^='visualizer-page-header-title']");
	By recordCountLabelLocator = By.cssSelector("div[class^='visualizer-page-header-title'] > span[key='_Record.Count.Label_']");
	By tableHeaderLocator = By.cssSelector("div[id^='col']:nth-child(1)");
	By tableDataLocator = By.cssSelector("div[class^='ngCellText ng-scope']");
	By rowSelectedLocator = By.cssSelector("div[class*='selected'] > div[class*='col']");

	By colHeaderLocator = By.cssSelector("div[id^='col']");
	By quickFilterHeaderLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header']");
	By closeQuickFilterMenuLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header'] > i.icon-remove");
	By sortSectionLabelLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div.sort-header");
	By ascendingLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-ascending']");
	By descendingLinkLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-descending']");
	By quickFilterUniqueItemsLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span[ng-show^='item.value']");
	By quickFilterUniqueItemsCountLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span.value-count");
	By quickFilterSearchUniqueItemsBoxLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > input.search-filter-value");
	By quickFilterApplyBtnLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.apply-quick-filter > span");
	By quickFilterClearBtnLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.clear-quick-filter > span");
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
	//By filterPanelMinimizeIconLocator = By.cssSelector(".icon-minus:not([style='display: none;'])");
	//By filterPanelMaximizeIconLocator = By.cssSelector(".icon-plus:not([style='display: none;'])");
	By filterPanelFilterToggledOffLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle.toggle-off");
	By filterPanelFilterToggledOnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle:not([class$='toggle-off'])");
	By filterPanelSearchFilterLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.search-filter:not([style='display: none;']) > input.search-filter-value");
	By filterPanelCheckboxCountLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value > span.value-count");
	By filterPanelCheckboxTextLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value");
	By filterPanelCheckedLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check:not([style='display: none;'])");
	By filterPanelUncheckedLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check-empty:not([style='display: none;'])");
	By filterPanelApplyFilterBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.action-btn-filter");
	By filterPanelClearFilterBtnLocator = By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.clear-quick-filter");
	
	//0123 Solve merge problem
	//0123 By filterBtnLocator = By.cssSelector(".static-tabs.filers-tab");
	By quickSortPlusLocator = By.cssSelector("i.icon-plus");
	By quickSortDropDown = By.cssSelector("select[ng-model='table.sort.field']");
	By optionsLocator = By.cssSelector("select[ng-model='table.sort.field'] > option");
	By quickSortAscButtonLocator = By.cssSelector("div#sort-ascending");
	By quickSortDescButtonLocator = By.cssSelector("div#sort-descending");
	By quickSortPanelContent = By.cssSelector(".collapse.in");
	By recordCountLocator = By.id("record-count");
	By firstColumnValues = By.cssSelector(".ngCell.col0.colt0");	
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
	
	//0123 solve merge problem
	protected List<WebElement> options;
	protected List<WebElement> firstColumnCells;
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

		isElementDisplayed(tableViewTabLocator, "Table View Tab");
		isElementDisplayed(addChartBtnLocator, "Add Chart Button");
		isElementDisplayed(filtersBtnLocator, "Filter Button");
		isElementDisplayed(searchBtnLocator, "Search Button");
		isElementDisplayed(otherBtnLocator, "... Button");
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public int numberOfRecords() {
		firstColumnCells = driver.findElements(firstColumnValues);
		return firstColumnCells.size();
	}
	
	public String getTableNameRecordsNum() {
		String tablenamerecordsnum = "";
		
		//Get table name
		tablenamerecordsnum = driver.findElement(tableNameLocator).getText();
		
		//Get record count label
		tablenamerecordsnum = tablenamerecordsnum+"@"+driver.findElement(recordCountLabelLocator).getText()+"@";
		
		return tablenamerecordsnum;
	}	

	public String getTableRecords() {
		String recordCount = driver.findElement(tableNameLocator).getText();

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

        //Get the initial displayed table data since all table data cannot be loaded at one time due to performance limit
        allTableData = driver.findElements(tableDataLocator);
        for(int i = 0; i < allTableData.size(); i++) {
        	int mod = i % (allTableColumns.size());
        	if (mod == 0) 
        		alltabledata += "\r\n" + allTableData.get(i).getText();
        	else
        		alltabledata += allTableData.get(i).getText() + " ";
       	}

        //Continue to get the remaining table data by pressing ARROW_DOWN key one row by one row 
        recordCount=getNumbers(getTableRecords());  
    	initialDisplayRowCount = allTableData.size()/allTableColumns.size();
 
    	//Get the focus for the current displayed last record
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
	
	public String isFilterPanelClosed() {
		List<WebElement> filterPanelHeader = driver.findElements(filterPanelHeaderLocator);
		int i = filterPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public String isSortPanelClosed() {
		List<WebElement> sortPanelHeader = driver.findElements(quickSortPanelContent);
		int i = sortPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public void clickFilterPanelBtn() {
		driver.findElement(filtersBtnLocator).click();
		takeScreenshotWithoutScroll();
	}
	
	public void clickSortOnPlusSign() {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(filtersBtnLocator));
		driver.findElement(quickSortPlusLocator).click();
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
	
	public void selectSortColumnFromSidePanelDropDown( String columnName){	
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSortDropDown));
		WebElement select = driver.findElement(quickSortDropDown);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(optionsLocator));
		options = select.findElements(optionsLocator);

		selectOption(columnName);
		if(verifyOptionIsSelected(columnName)) {
		logTAFStep("Selected option was successfully verified");
		}
		else {
		logTAFError("Selected option was unsuccessfully verified");
		}
	}
	
	public void quickSort(String sortDirection) {
		sleepAndWait(2);
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSortAscButtonLocator));
		if (sortDirection.equalsIgnoreCase("asc")){
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(ascendingLinkLocator).click();
		}
		else if (sortDirection.equalsIgnoreCase("desc")) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.findElement(descendingLinkLocator).click();
		}		
		else {
			logTAFError("Sort Order option is not valid");
		}
	}
	
	public boolean verifyOptionIsSelected(String name) {
		for (WebElement option : options) {
			  if(name.equals(option.getText()) && option.isSelected()) {
				  return true;
			  }	    
			}
		return false;
	}
	
	public boolean selectOption(String name) {
		for (WebElement option : options) {
			  if(name.equals(option.getText())) {
				  option.click();
				  return true;
			  }	    
			}
		logTAFError("Option doesn't exist");
		return false;
	}

}
