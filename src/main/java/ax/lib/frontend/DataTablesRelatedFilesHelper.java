package ax.lib.frontend;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class DataTablesRelatedFilesHelper extends TestSetDetailsHelper{
	
	/**
	 * Script Name   : <b>TestSetDetailsHelper</b>
	 * Generated     : <b>Sep 11, 2013</b>
	 * Description   : TestSetDetailsHelper
	 * 
	 * @author Ramneet Kaur
	 */

	//***************  Part 1  *******************
	// ******* Declaration of shared variables ***
	// *******************************************
	
	// BEGIN of datapool variables declaration
	
	// END of datapool variables declaration

	// BEGIN locators of the web elements of ProjectsList page
	By listHeaderLocator = By.cssSelector("div.title-row > span");
	By listColHeaderLocator = By.cssSelector("div.header-row > div > span");
	By tableNameLocator = By.cssSelector("div.table-row > div > div:nth-child(1)");
	By tableSizeLocator = By.cssSelector("div.table-row > div > div:nth-child(2)");
	By tableRecordsLocator = By.cssSelector("div.table-row > div > div:nth-child(3)");
	By tableModDateLocator = By.cssSelector("");
	By descIconLocator = By.cssSelector("div.actions-buttons > div.dropdown > i");
	By descHeaderLocator = By.cssSelector("div.description-content > h5 > span");
	By descLocator = By.cssSelector("div.description-content > span");
	By fileNameLocator = By.cssSelector("div.file-row > div > div:nth-child(1)");
	By fileSizeLocator = By.cssSelector("div.file-row > div > div:nth-child(2)");
	By fileRecordsLocator = By.cssSelector("div.file-row > div > div:nth-child(3)");
	//END
    
    // BEGIN of other local variables declaration
	protected List<WebElement> columnHeaders;
	protected List<WebElement> tableName;
	protected List<WebElement> tableSize;
	protected List<WebElement> tableRecords;
	protected List<WebElement> descIcon;
	protected String tablesList;
	protected String desc;
	protected List<WebElement> fileName;
	protected List<WebElement> fileSize;
	protected String filesList;
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
		isElementDisplayed(listHeaderLocator, "List header");

	}
	
	public String getTablesList(){
		columnHeaders = driver.findElements(listColHeaderLocator);
		tableName = driver.findElements(tableNameLocator);
		if(tableName.size()>0){
			tableSize = driver.findElements(tableSizeLocator);
			tableRecords = driver.findElements(tableRecordsLocator);
		}else{
			logTAFInfo("No tables found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText()+"|"+columnHeaders.get(2).getText()+"|"+columnHeaders.get(3).getText();
		}
		for(int i = 0; i < tableName.size(); i++) {
	        	if(i==0){
	        		tablesList=columnHeaders.get(0).getText()+":"+tableName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+tableSize.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+tableRecords.get(i).getText();
	        	}else{
	        		tablesList=tablesList+"\\n"+columnHeaders.get(0).getText()+":"+tableName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+tableSize.get(i).getText()+"|"+columnHeaders.get(2).getText()+":"+tableRecords.get(i).getText();
	        	}
	        }
	        return tablesList;
	}
	
	public String getTableDesc( String table){
		tableName = driver.findElements(tableNameLocator);
		if(tableName.size()>0){
			descIcon = driver.findElements(descIconLocator);
		}else{
			logTAFError("No tables found");
			return "";
		}
		for(int i = 0; i < tableName.size(); i++) {
	        	if(tableName.get(i).getText().equalsIgnoreCase(table)){
	        		descIcon.get(i).click();
	        		desc = driver.findElement(descHeaderLocator)+":"+driver.findElements(descLocator);
	        		descIcon.get(i).click();
	        		return desc;
	        	}
		}
	    return "";
	}
	
	public String getFileDesc( String file){
		fileName = driver.findElements(fileNameLocator);
		if(fileName.size()>0){
			descIcon = driver.findElements(descIconLocator);
		}else{
			logTAFError("No files found");
			return "";
		}
		for(int i = 0; i < fileName.size(); i++) {
	        	if(fileName.get(i).getText().equalsIgnoreCase(file)){
	        		descIcon.get(i).click();
	        		desc = driver.findElement(descHeaderLocator)+":"+driver.findElements(descLocator);
	        		descIcon.get(i).click();
	        		return desc;
	        	}
		}
	    return "";
	}

	public String getFilesList(){
		columnHeaders = driver.findElements(listColHeaderLocator);
		fileName = driver.findElements(fileNameLocator);
		if(fileName.size()>0){
			fileSize = driver.findElements(fileSizeLocator);
		}else{
			logTAFInfo("No files found");
			return columnHeaders.get(0).getText()+"|"+columnHeaders.get(1).getText();
		}
		for(int i = 0; i < fileName.size(); i++) {
	        	if(i==0){
	        		filesList=columnHeaders.get(0).getText()+":"+fileName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+fileSize.get(i).getText();
	        	}else{
	        		filesList=filesList+"\\n"+columnHeaders.get(0).getText()+":"+fileName.get(i).getText()+"|"+columnHeaders.get(1).getText()+":"+fileSize.get(i).getText();
	        	}
	        }
	        return filesList;
	}

	
	//***************  Part 3  *******************
	// ******* Methods           ****************
	// *******************************************
	


}
