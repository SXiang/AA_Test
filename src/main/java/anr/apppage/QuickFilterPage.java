/**
 * 
 */
package anr.apppage;

import java.util.List;

import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;


/**
 * Script Name   : <b>DataVisualizationPage.java</b>
 * Generated     : <b>5:38:19 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 12, 2013
 * @author steven_xiang
 * 
 */
public class QuickFilterPage extends WebPage{

	//*** Final varialbes ***	
	 private  final WebDriver pageDriver;
	 private final int pageLoadTime = 3;
	 
    //*** Common elements ***
//	    @FindBy(css = ".static-tabs.filers-tab")
//		private WebElement filterBtn;
//		@FindBy(css = ".addchart-tab-text")
//		private WebElement addChartBtn;
      
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
		
		//***** Quick Filter *****
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > i.icon-check-empty")
		private List<WebElement> quickFilterUniqueItemsCheckBox;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span[ng-show^='item.value']")
		private List<WebElement> quickFilterUniqueItems;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > div.filter-value > span.value-count")
		private List<WebElement> quickFilterUniqueItemsCount;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > input.search-filter-value")
		private WebElement quickFilterSearchUniqueItemsBox;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > "+
		              "div.row-fluid > div > select.select-operator")
        private WebElement quickFilterCriteriaSelctor;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > "+
	              "div.row-fluid > div > div > input.criteria-value")
        private WebElement quickFilterCriteriaValue;		
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div.criteria-filter-section > "+
	              "div.row-fluid > div > span > input.criteria-value")
        private List<WebElement> quickFilterCriteriaValues;		
		//***** Submit ****
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.apply-quick-filter > span")
		private WebElement quickFilterApplyBtn;
		@FindBy(css = "div[id$='quick-filter-panel']:not([style='display: none;']) > div[id='filter-section'] > div > a.clear-quick-filter > span")
		private WebElement quickFilterClearBtn;

		//*** Table Data ***
		//@FindBy(css = "#table-data div[id^='col']:nth-child(1)")
		@FindBy(css = "#table-data div[class^='ngHeaderText']:nth-child(1)")
		private List<WebElement> tableHeader;
		@FindBy(css = "#table-data div[class^='ngCellText']")
		private List<WebElement> tableData;
		
		@FindBy(css = ".visualizer-page-header-title")
		private WebElement tableName;				
		@FindBy(css = "#record-count")
		private WebElement recordCount;
		@FindBy(css = "div[class*='selected'] > div[class*='col']")
		private List<WebElement> rowSelected;
		@FindBy(css = "tab-heading.chart-tabs > i.icon-table")
		private WebElement tableViewTab;

		

		//***************  Part 3  *******************
		// ******* Methods           ****************
		// *******************************************

		public void activateTable(){
			//click(tableViewTab,"Table View",tableData);
			//click(tableViewTab,"Table View");
		}
		
		public boolean clickColumnHeader(String columnName){
			return clickColumnHeader(columnName,true);
		}
		public boolean clickColumnHeader(String columnName,boolean expand) {            
            try{
            	if(quickFilterSearchUniqueItemsBox.isDisplayed()){
            		return true;
            	}
            }catch(Exception e){
            	//
            }
            
	        for(int i = 0; i < tableHeader.size(); i++) {
	        	if(tableHeader.get(i).getText().equalsIgnoreCase(columnName)){
	        		click(pageDriver,tableHeader.get(i),columnName,quickFilterSearchUniqueItemsBox);
	        		return true;
	        	}
	        }
	        return false;
		}
		
		public void setCriteria(String[] option,int start,String type){
			// Should be from 
			selectItem(new Select(quickFilterCriteriaSelctor),option[start],type);
			if(option[start].equalsIgnoreCase("Between")){
				inputChars(quickFilterCriteriaValues.get(0),option[start+1],type);
				inputChars(quickFilterCriteriaValues.get(1),option[start+2],type);
			}else{
			      inputChars(quickFilterCriteriaValue,option[start+1],type);
			}
		}
		public void searchValue(String value){
			searchValue(value,"input");
		}
		public void searchValue(String value,String type){
			inputChars(quickFilterSearchUniqueItemsBox,value,type);
			sleep(pageLoadTime);
		}
		
        public void selectCheckBox(String[] item){
			selectCheckBox(item,0,"on");
		}
        public void selectCheckBox(String[] item, int start){
			selectCheckBox(item,start, "on");
		}
		public void selectCheckBox(String[] item, int start, String type){
			List<WebElement> labels,counts,boxs;
			WebElement label,count,box;
            boolean on = type.equalsIgnoreCase("off")?false:true;
            
				labels = quickFilterUniqueItems;
				counts = quickFilterUniqueItemsCount;
				boxs = quickFilterUniqueItemsCheckBox;
			
			boolean selectAll = false;
			for(int j=start;j<item.length&&!selectAll;j++){
			   selectAll = item[j].equalsIgnoreCase("All")?true:false;
			   for(int i=0;i<boxs.size();i++){
				label = labels.get(i);
				count = counts.get(i);
				if(selectAll
						||(label.getText()+count.getText()).equals(item[j])){
					box = boxs.get(i);
					if(!selectAll){
						toggleItem(box,on,item[j],type);
						break;
					}else{
						toggleItem(box,on,"item "+(i+1),type);
					}
				}
			  }
			}
		}
		
	   public void endWith(String command){
		   String[] comms = command.split("\\|");
		   for(String comm:comms){
			   if(comm.equalsIgnoreCase("Apply")){
				   click(quickFilterApplyBtn,"Apply",quickFilterApplyBtn,false);
			   }else if(comm.equalsIgnoreCase("Clear")){
				   click(quickFilterClearBtn,"clear");
			   }else if(comm.equalsIgnoreCase("Dismiss")){
				   click(closeQuickFilterMenu,"Dismiss(X)",quickFilterApplyBtn,false);
			   }
		   }
	   }
	   
    // ****** Following may not need anymore ******************
	   
//		public String getTableName() {
//			return tableName.getText();
//		}	
//
		public String getTableRecords() {
			try{
			 return recordCount.getText();
			}catch(Exception e){
				return "";
			}
		}

		public String getTableData() {
			return getTableData(20);
		}
		
		public String getTableData(int numRecords) {
			String t[][] =  getTableArray(numRecords);
			String result = "";
			for(int i=0;i<t.length;i++){
				for(int j=0;j<t[i].length;j++){
					result += t[i][j]+",";
				}
				result += "\r\n";
			}
			return result;
		}
		
		public String[][] getTableArray(int numRecords){
				
		    logTAFStep("Get the Table Data");
		    
	 		//First get all table columns
		    int maxDisplayRecords = 30;
            int numFields = tableHeader.size();
            int numRows = tableData.size()/numFields;
	        int recordCount= Math.min(numRecords, numRows);//   getNumbers(getTableRecords());  
	    	int initialDisplayRowCount = numRows;
	 
            String[][] table = new String[recordCount+1][numFields];
            
	        for(int i=0;i<numFields;i++) {
	        	table[0][i] = tableHeader.get(i).getText();
	       	}

	        //Get the initial displayed table data since all table data cannot be loaded at one time for performance limits
            for(int i=0;i<recordCount;i++){
            	for(int j=0;j<numFields;j++){
            		table[i+1][j] = tableData.get(i*numFields+j).getText();
            	}
            }
           
            if(recordCount<maxDisplayRecords)
            	return table;
            
            //Following needs more work -- Steven
	        //Continue to get the left table data by pressing ARROW_DOWN key one row by one row 
	 
	    	click(tableData.get(tableData.size()-1),"");
	    	Actions actions = new Actions(driver); 
	    	actions.sendKeys(tableData.get(tableData.size()-1), Keys.ARROW_DOWN).perform();

	        logTAFStep("Press ARROW_DOWN key to get the left table data");
	        for (int j = initialDisplayRowCount; j < recordCount; j++) {
	        	for (int k =0; k < rowSelected.size(); k++ ) {
	        		table[j+1][k] += rowSelected.get(k).getText();
	    	    }
	        	actions.sendKeys(rowSelected.get(0), Keys.ARROW_DOWN).perform();
	        }
	        
	        return table;
		}

	  public QuickFilterPage(WebDriver driver){
		  
		    this.pageDriver = driver; 
		    
	  }
}
