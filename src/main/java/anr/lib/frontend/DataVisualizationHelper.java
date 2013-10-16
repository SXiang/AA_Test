package anr.lib.frontend;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.acl.qa.taf.util.FileUtil;

import anr.lib.frontend.FrontendCommonHelper;

public class DataVisualizationHelper extends FrontendCommonHelper{
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
	// END of datapool variables declaration

	// BEGIN locators of the web elements of DataVisualization page
	By tableNameLocator = By.id("table-name");
	By recordCountLocator = By.id("record-count");
	//By tableHeaderLocator = By.cssSelector("div.ngHeaderCell > div.ngSorted > div");
	By tableHeaderLocator = By.cssSelector("div[id^='col']");
	By tableDataLocator = By.cssSelector("div[class^='ngCellText ng-scope']");
	By dataCountLocator = By.xpath("//div[@class^='ngCellText ng-scope'][last()]");
	//END
    
    // BEGIN of other local variables declaration 
	protected List<WebElement> allTableColumns,allTableData;
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

	public  String getTableRecords() {
		String recordcount = "";
		
		recordcount = driver.findElement(recordCountLocator).getText();
		return recordcount;
	}

	public String getTableData() {
		//Enter ACL Project file folder
	    logTAFStep("Get Table Data");
		String alltabledata="";
		String temp;
		
 		allTableColumns = driver.findElements(tableHeaderLocator);
 		allTableData = driver.findElements(tableDataLocator);

 		//First get all table columns
        for(int i = 0; i < allTableColumns.size(); i++) {
        	temp = allTableColumns.get(i).getText();
        	alltabledata +=" " + temp;
       	}
        
        System.out.println("allTableColumns.size():"+allTableColumns.size());

        //Then get all table data
        for(int i = 0; i < allTableData.size(); i++) {
        	temp = allTableData.get(i).getText();
        	int mod = i % (allTableColumns.size()/2);
        	if (mod == 0) {
        		System.out.println("i:"+i);
        		alltabledata +="\r\n" + temp;
        	} else
        		alltabledata +=" " + temp;
       	}

		((JavascriptExecutor) driver).executeScript("scroll(810,3060);");
		
        return alltabledata;

	}

}
