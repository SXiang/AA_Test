package anr.lib.frontend;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


import anr.lib.frontend.FrontendCommonHelper;

public class DataVisualizationHelper extends FrontendCommonHelper{
	/**
	 * Script Name   : <b>OpenProjectsHelper</b>
	 * Generated     : <b>Oct 4, 2013</b>
	 * Description   : OpenProjectsHelper
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
	By tableNameLocator = By.id("table-name");
	By recordCountLocator = By.id("record-count");
	By tableHeaderLocator = By.cssSelector("div[id^='col']:nth-child(1)");
	By tableDataLocator = By.cssSelector("div[class^='ngCellText ng-scope']");
	By rowSelectedLocator = By.cssSelector("div[class*='selected'] > div[class*='col']");
	//END
    
    // BEGIN of other local variables declaration 
	protected List<WebElement> allTableColumns,allTableData;
	protected int recordCount;
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
//		isElementEnabled(projectOpenButtonLocator, "Open Local Project");
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
		  rb.keyPress(KeyCode);   // 按下按键
		  rb.delay(100);     // 保持100毫秒

		  rb.keyRelease(KeyCode);  // 释放按键

		  logTAFStep("Robot敲击键盘 " + KeyCode);
	}

}
