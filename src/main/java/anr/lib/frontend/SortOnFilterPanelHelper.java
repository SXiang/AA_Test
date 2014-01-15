package anr.lib.frontend;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;

import org.openqa.selenium.support.ui.Select; //added by yousef

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.String;

import ax.lib.frontend.FrontendCommonHelper;

public class SortOnFilterPanelHelper extends DataVisualizationHelper {
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
	
	//END
	
	protected List<WebElement> options;
	
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
	
	public String isFilterPanelClosed() {
		List<WebElement> filterPanelHeader = driver.findElements(filterBtnLocator);
		int i = filterPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public String isSortPanelClosed() {
		List<WebElement> sortPanelHeader = driver.findElements(quickSortPlusLocator);
		int i = sortPanelHeader.size();
		if(i>0){
			return "open";
		}
		return "close";
	}
	
	public void clickFilterPanelBtn() {
		//driver.findElement(filterBtnLocator).click();
		//takeScreenshotWithoutScroll();
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(filterBtnLocator));
		driver.findElement(filterBtnLocator).click();
		
	}
	
	public void clickSortOnPlusSign() {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(filterBtnLocator));
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
		//select.selectByIndex(1);
		
		//WebElement select = driver.findElement(By.id("selection"));
		
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSortDropDown));
		options = select.findElements(optionsLocator);
		
		//select.sendKeys("Location");
		
		//verifySelectedOption("Location");
		
		selectOption(columnName);

	/*	for (WebElement option : options) {
		  if("Location".equals(option.getText())) // && !select.getText().isEmpty())
		    option.click();
		}*/

//		WebElement option = select.getFirstSelectedOption();
//		option.getText();
		if(verifyOptionIsSelected(columnName)) {
		logTAFStep("Selected option was successfully verified");
		}
	}
		/*
		driver.findElement(quickSortAscButtonLocator).click();	
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSortDescButtonLocator));
		driver.findElement(quickSortDescButtonLocator).click();
		*/
		
		//wait.until(ExpectedConditions.presenceOfElementLocated(quickSortDropDown));
		
		//select.
		//driver.findElement(quickSortDropDown).click();

		
/*
WebElement select = driver.findElement(By.id("selection"));
List<WebElement> options = select.findElements(By.tagName("option"));
for (WebElement option : options) {
  if("Germany".equals(option.getText()))
    option.click();
}*/
		
	public void quickSort(String sortDirection) {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(quickSortAscButtonLocator));
		if (sortDirection.equalsIgnoreCase("asc")){
			driver.findElement(quickSortAscButtonLocator).click();
		}
		else if (sortDirection.equalsIgnoreCase("desc")) {
			driver.findElement(quickSortDescButtonLocator).click();
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
	
}
