
	package anr.apppage;

	import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.util.List;

	import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

	/**
	 * Script Name   : <b>DataVisualizationPage.java</b>
	 * Generated     : <b>5:38:19 PM</b> 
	 * Description   : <b>ACL Test Automation</b>
	 * 
	 * @since  Dec 12, 2013
	 * @author steven_xiang
	 * 
	 */
	public class FilterPanelPage extends WebPage{

		//*** Final varialbes ***	
		 private  final WebDriver pageDriver;
		 private final int pageLoadTime = 3;
		 
	    //*** Common elements ***
		    @FindBy(css = ".static-tabs.filers-tab")
			private WebElement filterBtn;

	      
	    //*** Quick filter ***
			@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header']")
			private WebElement quickFilterHeader;
			@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-header'] > i.icon-remove")
			private WebElement closeQuickFilterMenu;
			
			//***** Sort *****
			@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div.sort-header")
			private WebElement sortSectionLabel;
			@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-ascending']")
			private WebElement ascendingLink;
			@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='sort-section'] > div > div[id='sort-descending']")
			private WebElement descendingLink;
			
					
			//*** Table Data ***
			//@FindBy(css = "#table-data div[id^='col']:nth-child(1)")
			@FindBy(css = "#table-data div[class^='ngHeaderText']:nth-child(1)")
			private List<WebElement> tableHeader;
			@FindBy(css = "#table-data div[class^='ngCellText']")
			private List<WebElement> tableData;
			
			@FindBy(css = "tab-heading.chart-tabs > i.icon-table")
			private WebElement tableViewTab;

		
			@FindBy(css = ".addchart-tab-text")
			private WebElement addChartBtn;
			
			//*** Filter Panel ***
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div > div >div > div.filter-panel-header > span")
			private List<WebElement> filterPanelHeader;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div.filter-column-name")
			private List<WebElement> filterPanelColNames;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div >  div > span")
			private WebElement filterPanelSortSection;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div >  div > div > select.select-block > option")
			private WebElement filterPanelSortDropDownItems;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn[btn-radio*='asc']")
			private WebElement filterPanelAscSortBtn;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn[btn-radio*='desc']")
			private WebElement filterPanelDescSortBtn;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn.active[btn-radio*='asc']")
			private WebElement filterPanelAscSortBtnActive;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel >div > div.filter-panel.sort-section > div > div > div > div > div > button.sort-order-btn.active[btn-radio*='desc']")
			private WebElement filterPanelDescSortBtnActive;
			//By filterPanelMinimizeIconLocator = By.cssSelector(".icon-minus:not([style='display: none;'])");
			//By filterPanelMaximizeIconLocator = By.cssSelector(".icon-plus:not([style='display: none;'])");
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle.toggle-off")
			private WebElement filterPanelFilterToggledOff;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div > div.filter-toggle:not([class$='toggle-off'])")
			private WebElement filterPanelFilterToggledOn;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.search-filter:not([style='display: none;']) > input.search-filter-value")
			private WebElement filterPanelSearchFilter;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value > span.value-count")
			private WebElement filterPanelCheckboxCount;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value")
			private WebElement filterPanelCheckboxText;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check:not([style='display: none;'])")
			private WebElement filterPanelChecked;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-values > div > i.icon-check-empty:not([style='display: none;'])")
			private WebElement filterPanelUnchecked;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.action-btn-filter")
			private WebElement filterPanelApplyFilterBtn;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-body > div.filter-panel-button > a.clear-quick-filter")
			private WebElement filterPanelClearFilterBtn;
		  
			//***************  Part 3  *******************
			// ******* Methods           ****************
			// *******************************************

			public void activateTable(){
				By untilTableHeaderDisplayed = By.cssSelector("tab-heading.chart-tabs > i.icon-table");
				click(tableViewTab,"Table View",untilTableHeaderDisplayed);
			}
//			public boolean clickColumnHeader(String columnName) {
//				By untilSearchBoxDisplayed = By.cssSelector("div[id$='quick-filter-panel']:not([style='display: none;'])"+
//			                                     " > div[id='filter-section'] > div > input.search-filter-value");
//		        for(int i = 0; i < colHeader.size(); i++) {
//		        	if(colHeader.get(i).getText().equalsIgnoreCase(columnName)){
//		        		click(pageDriver,colHeader.get(i),columnName,untilSearchBoxDisplayed);
//		        		return true;
//		        	}
//		        }
//		        return false;
//			}
//			
//	        public void selectCheckBox(String[] item){
//				selectCheckBox(item,true);
//			}
//			public void selectCheckBox(String[] item, boolean on){
//				List<WebElement> labels,counts,boxs;
//				WebElement label,count,box;
//
//					labels = quickFilterUniqueItems;
//					counts = quickFilterUniqueItemsCount;
//					boxs = quickFilterUniqueItemsCheckBox;
//		
//				
//				for(int j=0;j<item.length;j++){
//				   for(int i=0;i<boxs.size();i++){
//					label = labels.get(i);
//					count = counts.get(i);
//					if((label.getText()+count.getText()).equals(item[j])){
//						box = boxs.get(i);
//						//toggleItem(box,on,item[j]);
//					}
//				  }
//			}
//			
//			}	
//			public String getTableName() {
//				return tableName.getText();
//			}	
//
//			public String getTableRecords() {
//				return recordCount.getText();
//			}
//
//			public String getTableData() {
//				String alltabledata="";
//				int initialDisplayRowCount = 0;
//				List <WebElement> nextRow;
//
//			    logTAFStep("Get the Table Data");
//			    
//		 		//First get all table columns
//
//		        for(int i = 0; i < tableHeader.size(); i++) {
//		        	alltabledata +=" " + tableHeader.get(i).getText();
//		       	}
//
//		        //Get the initial displayed table data since all table data cannot be loaded at one time for performance limits
//
//		        for(int i = 0; i < tableData.size(); i++) {
//		        	int mod = i % (tableHeader.size());
//		        	if (mod == 0) 
//		        		alltabledata += "\r\n" + tableData.get(i).getText();
//		        	else
//		        		alltabledata += tableData.get(i).getText() + " ";
//		       	}
//
//		        //Continue to get the left table data by pressing ARROW_DOWN key one row by one row 
//		        int recordCount=getNumbers(getTableRecords());  
//		    	initialDisplayRowCount = tableData.size()/tableHeader.size();
//		 
//		    	click(tableData.get(tableData.size()-1),"");
//		    	Actions actions = new Actions(driver); 
//		    	actions.sendKeys(tableData.get(tableData.size()-1), Keys.ARROW_DOWN).perform();
//
//		        logTAFStep("Press ARROW_DOWN key to get the left table data");
//		        for (int j = initialDisplayRowCount; j < recordCount; j++) {
//		        	
//		        	alltabledata +="\r\n";
//		        	for (int k =0; k < rowSelected.size(); k++ ) {
//		        		alltabledata += rowSelected.get(k).getText() + " " ;
//		    	    }
//		        	actions.sendKeys(rowSelected.get(0), Keys.ARROW_DOWN).perform();
//		        }
//		        
//		        return alltabledata;
//			}
//			
//			public void pressKeyboard(int KeyCode) {
//				  Robot rb = null;
//				  try {
//				   rb = new Robot();
//				  } catch (AWTException e) {
//				   e.printStackTrace();
//				  }
//				  rb.keyPress(KeyCode);   // Press the button
//				  rb.delay(100);     // delay of 100 ms
//
//				  rb.keyRelease(KeyCode);  // Release the button
//
//				  logTAFStep("Robot Keystrokes " + KeyCode);
//			}
//			
//
//
//			public void clickDescendingLink() {
//				takeScreenshotWithoutScroll();
//				click(descendingLink,"");
//			}
//			
//			public void clickAscendingLink() {
//				takeScreenshotWithoutScroll();
//				click(ascendingLink,"");
//			}
//			
//			public void clickSidePanelDescendingLink() {
//				click(filterPanelDescSortBtn,"");
//			}
//			
//			public void clickSidePanelAscendingLink() {
//				click(filterPanelAscSortBtn,"");
//			}
//			
//			public Boolean isFilterPanelClosed() {			
//				 return filterPanelHeader.size()>0;
//			}
//			
//			public void clickFilterPanelBtn() {
//				click(filterBtn,"");
//				takeScreenshotWithoutScroll();
//			}
//			
//			public String getFilterPanelContents() {
//				int itemSize;
//				String allFilters;
//				WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
//				wait.until(ExpectedConditions.presenceOfElementLocated(
//						By.cssSelector("div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div.filter-column-name")));
//				takeScreenshotWithoutScroll();
//				allFilters = "@" + filterPanelHeader.get(1).getText() + "@";
//				allFilters = allFilters + "\r\n@" + filterPanelSortSection.getText() + "@ ";
//				/*need to fix
//				if(driver.findElement(filterPanelSortDropDownSelectedItemLocator).getText().equals("")){
//					allFilters = allFilters + "\r\n" + "Sort not applied";
//				}else{
//					
//					allFilters = allFilters + "'" + driver.findElement(filterPanelSortDropDownSelectedItemLocator).getText();
//					if(driver.findElements(filterPanelAscSortBtnActiveLocator).size()>0){
//						allFilters = allFilters + "' : in Ascending order";
//					}else if(driver.findElements(filterPanelDescSortBtnActiveLocator).size()>0){
//						allFilters = allFilters + "' : in Descending order";
//					}else{
//						logTAFError("Sort order buttons not enabled");
//					}
//					
//				}
//			*/
//				itemSize = filterPanelColNames.size();
//				if(itemSize>0){
//					for(int i = 0; i < itemSize/2;i++){
//						allFilters = allFilters + "\r\n" + filterPanelColNames.get(i).getText();					
//					}
//				}
//				return allFilters;
//				
//			}
//			
//			public String getUniqueValuesFromQuickFilter(){
//				String allFilterValues="";
//				
//				WebDriverWait wait = new WebDriverWait(driver, timerConf.waitToFindElement);
//				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
//						"div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.apply-quick-filter > span")));
//				takeScreenshotWithoutScroll();
//
//				for(int i =0;i < quickFilterUniqueItems.size();i++){
//					if(i==0){
//						allFilterValues = quickFilterUniqueItems.get(i).getText() + quickFilterUniqueItemsCount.get(i).getText();
//					}
//					allFilterValues = allFilterValues + "\r\n" + quickFilterUniqueItems.get(i).getText() + quickFilterUniqueItemsCount.get(i).getText();
//				}
//				return allFilterValues;
//			}
			
			public String getAllColumnsFromDropDown(){
				//driver.findElements(filterPanelSortDropDownItemsLocator).
				return "";
			}
			
			public void selectSortColumnFromSidePanelDropDown( String columnName){
				
			}

		  
		  public FilterPanelPage(WebDriver driver){
			  
			    this.pageDriver = driver; 
			    
		  }


}
