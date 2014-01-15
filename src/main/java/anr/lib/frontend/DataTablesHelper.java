package anr.lib.frontend;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import anr.lib.frontend.ANR_FrontendCommonHelper;

public class DataTablesHelper extends ANR_FrontendCommonHelper{
	/**
	 * Script Name   : <b>OpenProjectsHelper</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProjectsHelper
	 * 
	 * @since  Oct 4, 2013
	 * @author Karen Zou
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	protected String dpWebDriver; //@arg Selenium webdriver type
    							  //@value = HtmlUnit|Firefox|IE|Chrome
	// END of datapool variables declaration

	// BEGIN locators of the web elements of OpenProject page
	By dataTablesHeaderLocator = By.cssSelector("div[class='modal-header'] > div > span");
	By dataTablesTitleRowLocator = By.cssSelector("div[class='row-fluid anr-table-title-row']");	
	By dataTablesRowLocator = By.cssSelector("div[id='tables'] > div[class='anr-table-row datatables-table-row ng-scope']");
    By noDataTablesLocator = By.id("noDatatables");	
    By noDataTablesDescLocator = By.cssSelector("div[id='noDatatables'] > span");
	
	By tableNameLocator = By.cssSelector("div[class~='datatables-table-row'] > div.row-fluid:nth-child(2) > div[class^='span7']");
	By tableRecordsLocator = By.cssSelector("div[class~='datatables-table-row'] > div.row-fluid:nth-child(2) > div[class^='span2'] > span[class^='ng-binding']");
	By tableSizeLocator = By.cssSelector("div[class~='datatables-table-row'] > div.row-fluid:nth-child(2) > div[class='span2 ng-binding']");
	By tableSizeUnitLocator = By.cssSelector("div[class~='datatables-table-row'] > div.row-fluid:nth-child(2) > div[class='span2 ng-binding'] > span[class^='dataTable-unit']");
	By dataVisualizeIconLocator = By.cssSelector("div[class~='datatables-table-row'] > div.row-fluid:nth-child(2) > div.span1 > a > i");
	
	By tableWithoutDataFileLocator = By.cssSelector("div[class~='datatables-table-row'] > div[ng-hide]");
	By tableNamefortableWithoutDataFileLocator = By.cssSelector("div[class~='datatables-table-row'] > div[ng-hide] > div[class='span7 ng-binding']");
	By noDataFilefortableWithoutDataFileLocator = By.cssSelector("div[class~='datatables-table-row'] > div[ng-hide] > div[class='span5 table-special-message']");
//END
     
    // BEGIN of other local variables declaration 
	public String imageName;

	protected List<WebElement> allTables,allAnalytics,allTableNames, allTableRecords, allTableSizes, allTableSizeUnits,
							   allTablesWithoutDataFile,allTableNamesforTablesWithoutDataFile,allNoDataFileforTablesWithoutDataFile;
	protected WebElement noDataTable,noDataTableDesc;
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
	}

	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	public String getDataTablesHeader() {
		WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
		wait.until(ExpectedConditions.presenceOfElementLocated(dataTablesHeaderLocator));
		return "@"+driver.findElement(dataTablesHeaderLocator).getText()+"@";
	}	

	public  String getAllTables() {
		String tables = "";
		
		//Get all elements for tables with data files
		allTableNames = driver.findElements(tableNameLocator);
		allTableRecords = driver.findElements(tableRecordsLocator);
		allTableSizes = driver.findElements(tableSizeLocator);
    	allTableSizeUnits = driver.findElements(tableSizeUnitLocator);
    	
		//Get all elements for tables without data files
    	allTablesWithoutDataFile = driver.findElements(tableWithoutDataFileLocator);
    	allTableNamesforTablesWithoutDataFile = driver.findElements(tableNamefortableWithoutDataFileLocator);
    	allNoDataFileforTablesWithoutDataFile = driver.findElements(noDataFilefortableWithoutDataFileLocator);
    	
    	//Get elements for no data tables
    	noDataTable = driver.findElement(noDataTablesLocator);
    	noDataTableDesc = driver.findElement(noDataTablesDescLocator);
		
		//Locate the cursor to the first line
		driver.findElement(dataTablesTitleRowLocator).getText(); 
		tables = driver.findElement(dataTablesTitleRowLocator).getText(); 
		tables.replace("\n", " ");
		
		if (!(noDataTable.getAttribute("style").isEmpty())) {
			for(int i = 0; i < allTableNames.size(); i++) {
				if (!allTablesWithoutDataFile.get(i).getAttribute("style").isEmpty()) {
					tables=tables+"\r\n"+allTableNames.get(i).getText()+" "+allTableRecords.get(i).getText()+" "+allTableSizes.get(i).getText();
				} else {
					tables=tables+"\r\n"+allTableNamesforTablesWithoutDataFile.get(i).getText()+" "+allNoDataFileforTablesWithoutDataFile.get(i).getText();
				}
			}
		}
		else {
				tables = noDataTableDesc.getText();
		}
		
        return tables;
	}


	public boolean clickTableName(String tablename) {
		List<WebElement> allTables;
		List<WebElement> allDataVisualizeIcons;
		
	    logTAFStep("Find the table name");
		
	    //Find the table list
	    allTables = driver.findElements(tableNameLocator);
	    allDataVisualizeIcons = driver.findElements(dataVisualizeIconLocator);
	    		
		for(int i = 0; i < allTables.size(); i++) {
		    String temp = allTables.get(i).getText();
        	if (temp.equalsIgnoreCase(tablename)) {
        	    logTAFStep("Table name has been found successfully!");
        	    logTAFStep("Click the related data visualize icon");
        	    allDataVisualizeIcons.get(i).click();
        		
        		sleep(1);
        		return true;
        	}
		}

        return false;
	}

}
