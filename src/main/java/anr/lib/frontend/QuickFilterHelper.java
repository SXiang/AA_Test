package anr.lib.frontend;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.String;

import anr.lib.frontend.DataVisualizationHelper;

public class QuickFilterHelper extends DataVisualizationHelper{
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
		isElementDisplayed(filterBtnLocator, "Filter Button");	
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
			
		listOfValues = "Table Name = " + parts[0] + "\r";

		listOfValues = listOfValues + getActionType(columnName, filterValues, sortDirection);
		
		if (!visualizerPageHeaderTitleFilteredRecordsElement.getText().replace("/", "").trim().isEmpty()) {
			listOfValues = listOfValues + "Number of Filtered records = " + Integer.valueOf(visualizerPageHeaderTitleFilteredRecordsElement.getText().replace("/", "").trim()) + "\r\r";
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
		
		options = select.findElements(criteriaFilterSelectOptions);
		
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
		isElementEnabled(filterBtnLocator, "Filter Button");
		driver.findElement(filterBtnLocator).click();
		areAllElementsEnabled(removeFiltersLocator, "Remove Filter Icon");
		//removeFilterIcons = driver.findElements(removeFiltersLocator);
		for (WebElement removeFilterIcon : elements) {
			sleepAndWait(3);
			removeFilterIcon.click();
		}
	}
	
	public void clickFilterPanelBtn() {
		if(isFilterPanelClosed().equals("close")) {
		driver.findElement(filterBtnLocator).click();
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
	
	public void quickSort(String sortDirection) {
		sleepAndWait(2);
		if (sortDirection.equalsIgnoreCase("asc")){
			waitUntilPresenceOfElementLocated(quickSortAscLinkLocator);
			driver.findElement(quickSortAscLinkLocator).click();
		}
		else if (sortDirection.equalsIgnoreCase("desc")) {
			waitUntilPresenceOfElementLocated(quickSortDescLinkLocator);
			driver.findElement(quickSortDescLinkLocator).click();
		}		
		else {
			logTAFError("Sort Order option is not valid");
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
	
//List<WebElement> allDescendantsChilds = rootWebElement.findElements(By.xpath("//tr[@class='parent']//*"));

}
