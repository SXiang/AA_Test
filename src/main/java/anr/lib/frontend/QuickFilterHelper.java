package anr.lib.frontend;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.AWTException;
import java.awt.Robot;
import java.lang.String;

import ax.lib.frontend.FrontendCommonHelper;

public class QuickFilterHelper extends FrontendCommonHelper{
	/**
	 * Script Name   : <b>DataVisualizationHelper</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : DataVisualizationHelper
	 * 
	 * @since  Jan 10, 2014
	 * @author Yousef Aichour
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************

	// BEGIN locators of the web elements of filter page
	By optionsLocator = By.cssSelector("select[ng-model='table.sort.field'] > option");
	By filterButtonlocator = By.cssSelector(".btn.btn-primary.static-tabs.filers-tab.pull-left.ng-scope.active");
	By firstColumnValues = By.cssSelector(".ngCell.col0.colt0");	
	By criteriaFilterSelect = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div > div > select");
	By criteriaFilterSelectOptions = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div > div > select > option");
	By criteriaFilterValueLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div:nth-child(2) > div > div > input");
	By criteriaFilterValueHalfLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > div:nth-child(3) > div > span > input");
	By enableFilter = By.cssSelector(".filter-button");
	By removeFiltersLocator = By.cssSelector("div.filter-panel-row-header > div > div.span5 > i.icon-remove");
	By filterConfPanelColumnLocator = By.cssSelector(".filter-column-name");
	By filterConfPanelChosenOptionLocator = By.cssSelector("span.select2-chosen");
	By filterConfPanelSelectDropDownLocator = By.cssSelector("select.criteria-operator"); //elect.criteria-operator > option
	By filterConfPanelSelectOptionsLocator = By.cssSelector("select.criteria-operator > option");
	By filterConfPanelValue = By.cssSelector("div > input.criteria-value");
	By filterConfPanelValueHalf = By.cssSelector("div [on='table.metaData.fields[filter.name].type'] >span > input.criteria-value-half");
	By criteriaFilterApplyButton = By.cssSelector("span[key = '_Filter.Apply.Label_']");
	By allCellsValuesLocator = By.cssSelector("span.ng-binding[ng-cell-text='']");
	By visualizerPageHeaderTitleLocator = By.cssSelector("div.visualizer-page-header-title");
	By visualizerPageHeaderTitleFilteredRecordsLocator = By.cssSelector("div.visualizer-page-header-title > span:nth-of-type(1)");
	//By criteriaFilters = By.cssSelector("div.criteria-filters");
	By criteriaFilters = By.xpath("//div[@class='criteria-filters']");
	//By criteriaFilterChildrenLocator = By.cssSelector("*");
	By criteriaFilterChildrenLocator = By.xpath(".//*//*//*//*//*");
	By searchBtnLocator = By.cssSelector("i[class='icon-chevron-left icon-1x']");
	By filtersBtnLocator = By.cssSelector("div.static-tabs-text");
	By otherBtnLocator = By.cssSelector("ul[class^='dropdown-menu'] > li > a");

	By tableNameLocator = By.cssSelector("div[class^='visualizer-page-header-title']");
	By recordCountLabelLocator = By.cssSelector("div[class^='visualizer-page-header-title'] > span[key='_Record.Count.Label_']");
	By tableHeaderLocator = By.cssSelector("div[id^='col']:nth-child(1)");
	By tableDataLocator = By.cssSelector("div[class^='ngCellText ng-scope']");
	By rowSelectedLocator = By.cssSelector("div[class*='selected'] > div[class*='col']");

	//By colHeaderLocator = By.cssSelector("div[id^='col']");
	By colHeaderLocator = By.xpath("//div[contains(@class, 'ngHeaderText')]");
	By quickFilterHeaderLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header']");
	By closeQuickFilterMenuLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header'] > i.icon-remove");
	By sortSectionLabelLocator = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div.sort-header");
	//By sortHeaderLocator = By.cssSelector("div[id*='quick-filter-panel']:not([style='display: none;']) > div.sort-section > div.sort-header");
	By ascendingLinkLocator = By.cssSelector("div[id*='quick-filter-panel']:not([style='display: none;']) > div.sort-section > div > div.ascending");
	By descendingLinkLocator = By.cssSelector("div[id*='quick-filter-panel']:not([style='display: none;']) > div.sort-section > div > div.descending");
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
	By quickSortAscButtonLocator = By.cssSelector("div#sort-ascending");
	By quickSortDescButtonLocator = By.cssSelector("div#sort-descending");
	By quickSortPanelContent = By.cssSelector(".collapse.in");
	By recordCountLocator = By.id("record-count");	
	
	
	
    // BEGIN of other local variables declaration
	protected List<WebElement> allTableColumns,allTableData;
	protected int recordCount;
	protected List<WebElement> allColumnHeaders;
	protected String allFilters;
	protected List<WebElement> allUniqueItemsCount;
	protected List<WebElement> allUniqueItems;
	protected String allFilterValues;
	protected String allColumns;
		
	protected List<WebElement> options;
	protected List<WebElement> values;
	protected List<WebElement> columnNames;
	protected List<WebElement> removeFilterIcons;
	protected List<WebElement> firstColumnCells;
	protected List<WebElement> criteriaFilterApplyButtons;
	protected List<WebElement> listOfAllDisplayedData;
	
	protected WebElement columnNameElement;
	protected WebElement dropDownElement;
	protected WebElement fromValueElement;
	protected WebElement fromValue1Element;
	protected WebElement fromValue2Element;
	protected WebElement criteriaFiltApplyButton;
	

	
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
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	
	public int recordCount() {
		firstColumnCells = driver.findElements(firstColumnValues);	
		return firstColumnCells.size();
	}
	
	public String getColumnTitles(int columns) {
		String listOfColumns = "";
		List<WebElement> columnTitles = driver.findElements(colHeaderLocator);
		int i=1;
		for(WebElement columnTitle : columnTitles) {
			if(i <= columns) {
				listOfColumns = listOfColumns + '"' + columnTitle.getText() + '"';
				if (i < columns) {
					listOfColumns = listOfColumns + "\t";
				}
			}
			i++;
		}
		return listOfColumns;
	}
	
	public String getActionType(String columnName, String filterValues, String sortDirection) {
		String actionType = "";		
		if (sortDirection.equalsIgnoreCase("asc") || sortDirection.equalsIgnoreCase("desc")) {
			actionType = "Action Type = Sort " + sortDirection + " on " + columnName;
		}
		else {
			if(!filterValues.isEmpty()) {
				if (filterValues.split("\\|")[1].equalsIgnoreCase("Between")) {
					actionType = "Action Type = Filter --> " + columnName + " between " + filterValues.split("\\|")[2] + " and " + filterValues.split("\\|")[3];
				}
				else {
					actionType = "Action Type = Filter --> " + columnName + " " + filterValues.split("\\|")[1] + " " + filterValues.split("\\|")[2];
				}
			}	
		}
		return actionType + "\r";
	}
	
	public String getDisplayedDataToBeCompared(int columns, int rows, String columnName, String filterValues, String sortDirection) {
		String listOfValues = "";
		List<WebElement> values = driver.findElements(allCellsValuesLocator);
		WebElement visualizerPageHeaderTitleElement;
		WebElement visualizerPageHeaderTitleFilteredRecordsElement;
		
		int i = 1, recCOUNT = recordCount();
		int itemSize = values.size();
		int numberOfColumns;
		int rowNumber = 1;
		
		sleepAndWait(2);	
		
		waitUntilPresenceOfElementLocated(visualizerPageHeaderTitleLocator);
		visualizerPageHeaderTitleElement = driver.findElement(visualizerPageHeaderTitleLocator);
		
		waitUntilPresenceOfElementLocated(visualizerPageHeaderTitleFilteredRecordsLocator);
		visualizerPageHeaderTitleFilteredRecordsElement = driver.findElement(visualizerPageHeaderTitleFilteredRecordsLocator);
	
		String[] parts = visualizerPageHeaderTitleElement.getText().split("-");
		String[] parts2 = parts[1].split("/");
			
		listOfValues = "Table Name = " + parts[0] + "\r";

		listOfValues = listOfValues + getActionType(columnName, filterValues, sortDirection);
		
		if (!parts2[0].isEmpty() && parts2.length == 2) {
			//logTAFStep("Condition = " + visualizerPageHeaderTitleFilteredRecordsElement.getText().replace("/", "").trim());
			listOfValues = listOfValues + "Number of Filtered records = " + Integer.valueOf(parts2[0].trim()) + "\r\r";
		}
		
		listOfValues = listOfValues + getColumnTitles(columns) + "\r";
		
		if (recCOUNT > 0) {
			numberOfColumns = itemSize / recCOUNT;
			for(WebElement value : values) {
				if (rowNumber <= rows + 1) {
					if (i <= columns) {
						listOfValues =  listOfValues + value.getText();
						if (i < columns) {
							listOfValues =  listOfValues + "\t";
						}
					}
					i++;
					if (i == numberOfColumns + 1) {
						i = 1;
						rowNumber++;
						listOfValues = listOfValues + "\r";
					}
				}
				else {
					return listOfValues;
				}
			}
		}	
			return listOfValues;
	}
	
	public String isFilterPanelClosed() {
		List<WebElement> filterPanelHeader = driver.findElements(filterButtonlocator);
		int i = filterPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public void waitUntilPresenceOfElementLocated(By element) {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(element));
	}
	
	
	public void editQuickFilter(String columnName, String criteriaFilterFromOption, String criteriaFilterFromValue, String criteriaFilterToOption, String criteriaFilterToValueHalf1, String criteriaFilterToValueHalf2){
		
		if (selectElementsToBeEdited(columnName, criteriaFilterFromOption, criteriaFilterFromValue)) {
			
			selectOption(criteriaFilterToOption);
			
			fromValueElement.clear();
			
			waitUntilPresenceOfElementLocated(filterConfPanelValueHalf);
			
			values = driver.findElements(filterConfPanelValueHalf);
			
			areAllElementsEnabled(filterConfPanelValueHalf, "Criteria Filter, min and max values");
				
			values.get(0).sendKeys(criteriaFilterToValueHalf1);
			
			values.get(1).sendKeys(criteriaFilterToValueHalf2);
			
			criteriaFiltApplyButton.click();
		}	

	} 
	
	public void editQuickFilter(String columnName, String criteriaFilterFromOption, String criteriaFilterFromValue, String criteriaFilterToOption, String criteriaFilterToValue) {
		
		selectElementsToBeEdited(columnName, criteriaFilterFromOption, criteriaFilterFromValue);		
		selectOption(criteriaFilterToOption);
		fromValueElement.clear();
		fromValueElement.sendKeys(criteriaFilterToValue);
		criteriaFiltApplyButton.click();
	
	}

	//editQuickFilter(criteriaFilterFromOption, criteriaFilterFromValue, criteriaFilterToOption, criteriaFilterToValueHalf1, criteriaFilterToValueHalf2);
	
	
	public boolean selectElementsToBeEdited(String columnName, String criteriaFilterFromOption, String criteriaFilterFromValue) {
		
		waitUntilPresenceOfElementLocated(filterConfPanelColumnLocator);	
		List<WebElement> filteredColumns = driver.findElements(filterConfPanelColumnLocator);
		
		waitUntilPresenceOfElementLocated(filterConfPanelSelectDropDownLocator);	
		List<WebElement> dropDownLists = driver.findElements(filterConfPanelSelectDropDownLocator);	
		
		waitUntilPresenceOfElementLocated(filterConfPanelChosenOptionLocator);	
		List<WebElement> chosenOption = driver.findElements(filterConfPanelChosenOptionLocator);	

		waitUntilPresenceOfElementLocated(filterConfPanelValue);	
		List<WebElement> filterValues = driver.findElements(filterConfPanelValue);
		
		waitUntilPresenceOfElementLocated(criteriaFilterApplyButton);	
		List<WebElement> criteriaFilterApplyButtons = driver.findElements(criteriaFilterApplyButton);
		
		int itemSize = filteredColumns.size();
			
		for(int i = 0; i < itemSize;i++){

				if(filteredColumns.get(i).getText().equals(columnName) && chosenOption.get(i).getText().equals(criteriaFilterFromOption) && filterValues.get(i).getAttribute("value").equals(criteriaFilterFromValue)){
					
					columnNameElement = filteredColumns.get(i);
					dropDownElement = dropDownLists.get(i);	
					
					waitUntilPresenceOfElementLocated(filterConfPanelSelectOptionsLocator);
					options = dropDownLists.get(i).findElements(filterConfPanelSelectOptionsLocator);
					
					logTAFStep("Options final = " + options.get(i).getText());
					
					fromValueElement = filterValues.get(i);
					criteriaFiltApplyButton = criteriaFilterApplyButtons.get(i);
					
					return true;
				}					
		}
		logTAFError("Filter doesn't exist !!!");
		return false;
	}
		
	/*public void applyFilter(WebElement select, String criteriaFilterSelectOptions, criteriaFilter) {
		
	} */
	
	
	public void applyCriteriaFilter(String criteriaFilterOption, String criteriaFilterValue) {
		
		//logTAFStep("All data = " + getAllDisplayedData());
		
		waitUntilPresenceOfElementLocated(criteriaFilterSelect);
		
		WebElement select = driver.findElement(criteriaFilterSelect);
		
		logTAFStep("Options size = " + select.getText());
		
		options = select.findElements(criteriaFilterSelectOptions);
		
		logTAFStep("Options size = " + options.size());
		
		selectOption(criteriaFilterOption);

		driver.findElement(criteriaFilterValueLocator).sendKeys(criteriaFilterValue);

		driver.findElement(quickFilterApplyBtnLocator).click();
	}
	
	public void applyCriteriaFilter(String criteriaFilterOption, String criteriaFilterValueHalf1, String criteriaFilterValueHalf2){
		
		isElementEnabled(criteriaFilterSelect, "Criteria Filter Select DropDown List");
		
		WebElement select = driver.findElement(criteriaFilterSelect);
		
		isElementEnabled(criteriaFilterSelectOptions, "Criteria Filter Select Options");
		
		driver.findElement(ascendingLinkLocator).click();
		
		selectOption(criteriaFilterOption);
		
		values = driver.findElements(criteriaFilterValueHalfLocator);
		
		areAllElementsEnabled(criteriaFilterValueHalfLocator, "Criteria Filter, min and max values");
			
		values.get(0).sendKeys(criteriaFilterValueHalf1);
		
		values.get(1).sendKeys(criteriaFilterValueHalf2);
		
		
		isElementEnabled(quickFilterApplyBtnLocator, "Quick filter apply button");
		
		driver.findElement(quickFilterApplyBtnLocator).click();
	}

	public boolean areAllElementsEnabled(By locator, String elementName) {

		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		try{
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			elements = driver.findElements(locator);
			
			for (WebElement element : elements) {
				if(!element.isEnabled()) {
					logTAFError("Failed to find '"+element.getText()+"' !!!");
					return false;
				}
			}

		}catch(Exception e){
			logTAFError("Failed to find '"+elementName+"' !!!");
			return false;
		}
        return true;
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
	
	public void removeAllFilters() {
		isElementEnabled(filtersBtnLocator, "Filter Button");
		driver.findElement(filtersBtnLocator).click();
		areAllElementsEnabled(removeFiltersLocator, "Remove Filter Icon");
		//removeFilterIcons = driver.findElements(removeFiltersLocator);
		for (WebElement removeFilterIcon : elements) {
			sleepAndWait(3);
			removeFilterIcon.click();
		}
	}
	
	public void clickFilterPanelBtn() {
		if(isFilterPanelClosed().equals("close")) {
		driver.findElement(filtersBtnLocator).click();
		}
	}
	
	public void editFilter() {
		clickFilterPanelBtn();
		
	}

	public void selectSortColumnFromSidePanelDropDown( String columnName){	
		
		waitUntilPresenceOfElementLocated(quickSortDropDown);	
		WebElement select = driver.findElement(quickSortDropDown);

		waitUntilPresenceOfElementLocated(optionsLocator);	
		options = select.findElements(optionsLocator);
		selectOption(columnName);
		if(verifyOptionIsSelected(columnName)) {
		logTAFStep("Selected option was successfully verified");
		}
		else {
		logTAFError("Selected option was unsuccessfully verified");
		}
	}
	
	public String isSortPanelClosed() {
		List<WebElement> sortPanelHeader = driver.findElements(quickSortPanelContent);
		int i = sortPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public void clickSortOnPlusSign() {
		waitUntilPresenceOfElementLocated(quickSortPlusLocator);
		driver.findElement(quickSortPlusLocator).click();
	}
	
	public int numberOfRecords() {
		firstColumnCells = driver.findElements(firstColumnValues);
		return firstColumnCells.size();
	}
	
	
	public void justTest() {
		//*************		
		waitUntilPresenceOfElementLocated(criteriaFilters);	
		List<WebElement> values = driver.findElements(criteriaFilters);
		List<WebElement> children = values.get(1).findElements(criteriaFilterChildrenLocator);
		
		int i=0;
		//************
		
		//children.get(0).click();
		
		//selectOption("Not contains");
		
		//children.get(i).
		
		//children.get(58).sendKeys("Yousef");
			
		for(WebElement child : children) {
			i++;
			if(i == 1 || i == 12 || i == 23 || i == 34 || i == 8 || i == 19 || i == 30 || i == 41) {
			logTAFStep("Element text : Index = " + i  + child.getText() + " " + child.getAttribute("value"));
			}
		}
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
	
//List<WebElement> allDescendantsChilds = rootWebElement.findElements(By.xpath("//tr[@class='parent']//*"));
	
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
	

	public void quickSort(String sortDirection, String sortType) {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		
		if (sortType.equalsIgnoreCase("FilterPanel")) {
			wait.until(ExpectedConditions.presenceOfElementLocated(filterPanelAscSortBtnLocator));
			if (sortDirection.equalsIgnoreCase("asc")){
				driver.findElement(filterPanelAscSortBtnLocator).click();
			}
			else if (sortDirection.equalsIgnoreCase("desc")) {
				driver.findElement(filterPanelDescSortBtnLocator).click();
			}	
		}
		else {
			wait.until(ExpectedConditions.presenceOfElementLocated(ascendingLinkLocator));
			while (driver.findElements(ascendingLinkLocator).size() > 0) {
				if (sortDirection.equalsIgnoreCase("asc")){
					driver.findElement(ascendingLinkLocator).click();
				}
				else if (sortDirection.equalsIgnoreCase("desc")) {
					driver.findElement(descendingLinkLocator).click();
				}		
			}
		}
		if (!sortDirection.equalsIgnoreCase("asc") && !sortDirection.equalsIgnoreCase("desc")) {
			logTAFError("Sort Order option is not valid");
		}
		
		
	}
	
}
