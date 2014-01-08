
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
		 private final QuickFilterPage qfPage;
		 
		 //*** Search context ***
		 private int filterIndex = 0;
		 private int criteriaIndex = 0;
		 

		 
	    //*** Common elements ***
		    @FindBy(css = ".static-tabs.filers-tab")
			private WebElement filterBtn;
			
		//*** Filter Panel ***
		    //***** Driver context *****
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div > div >div > div.filter-panel-header > span")
			private WebElement filterPanelHeader;			
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row > div > div > div.filter-panel-row-header > div > div.filter-column-name")
			private List<WebElement> filterPanelColNames;
			@FindBy(css = "div.tab-pane.active > div > div > div.filter-panel > div> div.filter-panel-row")
			private List<WebElement> filters;
			
 			//***** Panel filter context - criteria *****
			
//			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters "
//					+ "div.filter-connecor > button.filter-connector-btn[btn-radio='and']")
//			List<WebElement> andBtns;
//			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters "
//					+ "div.filter-connecor > button.filter-connector-btn[btn-radio='or']")
//			List<WebElement> orBtns;
//			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters a.select2-choice > span.select2-chosen")
			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters > div.criteria-filter select.criteria-operator[ui-select2|='criteriaOperatorSelectOptions']")
			List<WebElement>	criteriaSelectors;
//			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters input.criteria-value")
//			List<WebElement>	criteriaValues ;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters > div.criteria-filter")
			List<WebElement>	criterias;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.criteria-filters > div.criteria-filter div.criteria-filter-remove-btn > i.icon-remove")
			private List<WebElement> criteriaRemoveBtn;
			
			//***** Panel filter context - checkbox *****
			@FindBy(css = " div > div > div.filter-panel-row-body > div.search-filter:not([style='display: none;']) > input.search-filter-value")
			private WebElement filterPanelSearchFilter;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value > span.value-count")
			private List<WebElement> filterPanelCheckboxCount;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value")
			private List<WebElement> filterPanelCheckboxText;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.filter-panel-values > div.filter-panel-value > i.icon-check")
			private List<WebElement> filterPanelCheckbox;
			
			

			//***** Panel filter context - end with *****
			@FindBy(css = "div > div > div.filter-panel-row-body > div.filter-panel-button > a.action-btn-filter")
			private WebElement filterPanelApplyBtn;
			@FindBy(css = "div > div > div.filter-panel-row-body > div.filter-panel-button > a.clear-quick-filter")
			private WebElement filterPanelClearBtn;
			
			@FindBy(css = "div > div > div.filter-panel-row-header > div > div > i.icon-remove.pull-right.margin_half")
			private WebElement filterPanelDeleteBtn;
			@FindBy(css = "div > div > div.filter-panel-row-header > div > div > i.icon-minus:not([style='display: none;'])")
			private WebElement filterPanelMinusBtn;
			@FindBy(css = "div > div > div.filter-panel-row-header > div > div > i.icon-plus:not([style='display: none;'])")
			private WebElement filterPanelPlusBtn;
			@FindBy(css = "div > div > div.filter-panel-row-header > div > div > div.filter-toggle.toggle-off") //ng-class="{'toggle-off': !filter.active}"
			private WebElement filterPanelEnableBtn;
			@FindBy(css = "div > div > div.filter-panel-row-header > div > div > div.filter-toggle:not([class$='toggle-off'])")
			private List<WebElement> filterPanelDisableBtn;

			//***************  Part 3  *******************
			// ******* Methods           ****************
			// *******************************************
            
			/*****     shared with quick filter *****/
			public void activateTable(){
				//qfPage.activateTable();
                
			}
			public String getTableData(){
				return getTableData(20);
			}
			
			public String getTableData(int numRecords){
				return qfPage.getTableData(numRecords);
			}
			/****************************************/
			
			/********** Filter Locator (Column) **********/
			public WebElement openFilterPanel(){
				return openFilterPanel("");
			}	
			public WebElement openFilterPanel(boolean expand){
				return openFilterPanel("",expand);
			}	
			public WebElement openFilterPanel(String columnName){
				return openFilterPanel(columnName,true);
			}
	
			public WebElement openFilterPanel(String columnName,boolean expand){
				activateTable();
				toggleElementByClick(filterPanelHeader,filterBtn,"Filters",expand);				
				filterIndex = getElementIndex(filterPanelColNames,columnName);
				WebElement filterElement = filters.get(filterIndex);
				//filterName = filterElement.getText();
				logTAFInfo("Working filter: '"+columnName+"'");
				return filterElement;
			}

			/*************************************************/
			public void scrollToFilter(){
				scrollToElement(filterPanelDeleteBtn);
				toggleElementByClick(filterPanelMinusBtn, filterPanelPlusBtn, "Expand(+)",true);
//				if(filterPanelDisableBtn.size()<1){
//					click(filterPanelEnableBtn,"Toggle on filter",filterPanelEnableBtn,false);
//				}
				scrollToElement(filterPanelApplyBtn);
			}
			//setCriteria(_filterValues,2,_action,_filterValues[0],"",-1,"")
			public void verifyCriteria(String[] _option,int _start,String _connector){

				WebElement    workingConnector = null;
				String cValues = "input.criteria-value";
				String orBtn = "div.filter-connector > button.filter-connector-btn[btn-radio='\\'or\\'']";
				String andBtn = "div.filter-connector > button.filter-connector-btn[btn-radio='\\'and\\'']";
				
			
				boolean found = false;
				String value = "";
				String expectedValue = "";
				for(int i=0;i<criteriaSelectors.size();i++){
					Select criteriaSelect = new Select(criteriaSelectors.get(i));
					//String selection = criteriaSelectors.get(i).getText().trim();
					//String selection = criteriaSelect.getFirstSelectedOption().getAttribute("value").trim();
					String selection = criteriaSelect.getFirstSelectedOption().getText().trim();
					
					List<WebElement>  criteriaBetweenValues;
					if(_connector.equalsIgnoreCase("or")){
						workingConnector = criterias.get(i).findElement(By.cssSelector(orBtn));
					}else if(_connector.equalsIgnoreCase("and")){
						workingConnector = criterias.get(i).findElement(By.cssSelector(andBtn));
					}
					
					if(selection.equalsIgnoreCase("Between")){
						   criteriaBetweenValues = criterias.get(i).findElements(By.cssSelector("span > "+cValues));
						   if(criteriaBetweenValues.size()<2)
							   break;
					       value = criteriaBetweenValues.get(0).getAttribute("value").trim()+
					    		   "-"+criteriaBetweenValues.get(1).getAttribute("value").trim();
					       expectedValue =  _option[_start+1]+"-"+ _option[_start+2];
					}else{
					        value = criterias.get(i).findElement(By.cssSelector("div > "+cValues)).getAttribute("value").trim();
					        //value = criteriaValues.get(i).getText().trim();
					        expectedValue = _option[_start+1];
					       
					}

					boolean activeConnector = true;
					if(workingConnector!=null){
					 activeConnector = workingConnector.getAttribute("class").endsWith("active");
				    }
					if(selection.equalsIgnoreCase(_option[_start])
							&&value.equalsIgnoreCase(expectedValue)
							&&activeConnector){
						criteriaIndex = i;
						found = true;
					}
				}
				if(found){
					logTAFInfo("Criteria found: '"+_option[_start]+" " +expectedValue+"'");
				}else{
					logTAFError("Criteria not found: '"+_option[_start]+" " +expectedValue+"'");
				}
			}	
			
			public void setCriteria(String[] option,int start,String connector){
				
				if(connector.equalsIgnoreCase("off")){
					  click(criteriaRemoveBtn.get(criteriaIndex),"RemoveCriteal(x)");
					  return;
				}
				
				String cValues = "input.criteria-value";
				String orBtn = "div.filter-connector > button.filter-connector-btn[btn-radio='\\'or\\'']";
				String andBtn = "div.filter-connector > button.filter-connector-btn[btn-radio='\\'and\\'']";
				WebElement    workingConnector = null;			
				if(connector.equalsIgnoreCase("or")){
					workingConnector = criterias.get(criteriaIndex).findElement(By.cssSelector(orBtn));
				}else if(connector.equalsIgnoreCase("and")){
					workingConnector = criterias.get(criteriaIndex).findElement(By.cssSelector(andBtn));
				}
				boolean activeConnector = true;
				if(workingConnector!=null){
				 activeConnector = workingConnector.getAttribute("class").endsWith("active");
			    }
				
				Select criteriaSelect = new Select(criteriaSelectors.get(criteriaIndex));
				selectItem(criteriaSelect,option[start]);
				
				if(option[start].equalsIgnoreCase("Between")){
					List<WebElement>    criteriaBetweenValues = criterias.get(criteriaIndex).findElements(By.cssSelector("span > "+cValues));
				   waitUntil(criteriaBetweenValues);
				   inputChars(criteriaBetweenValues.get(0),option[start+1]);
                   //click(filterPanelColNames.get(filterIndex));
				   inputChars(criteriaBetweenValues.get(1),option[start+2]);
				   
				}else{
					WebElement criteriaValue = criterias.get(criteriaIndex).findElement(By.cssSelector("div > "+cValues));
					waitUntil(criteriaValue);

					inputChars(criteriaValue,option[start+1]);
				}
				click(filterPanelColNames.get(filterIndex));
				if(!activeConnector){
					click(workingConnector,connector.toUpperCase());
				}else if(workingConnector!=null){
					logTAFInfo("Connector '"+connector.toUpperCase()+"' was selected ");
				}
			}
			
			public void searchValue(String value){
				searchValue(value,"input");
			}
			public void searchValue(String value,String type){
				
				inputChars(filterPanelSearchFilter,value,type);
				sleep(pageLoadTime);
			}
			
	        public void selectCheckBox(String[] item){
				selectCheckBox(item,0);
			}
	        public void selectCheckBox(String[] item, int start){
				selectCheckBox(item,start, "on");
			}
			public void selectCheckBox(String[] item, int start, String type){
				List<WebElement> labels,counts,boxs;
				WebElement label,count,box;
	            boolean on = type.equalsIgnoreCase("off")?false:true;
	            
					labels = filterPanelCheckboxText;
					counts = filterPanelCheckboxCount;
					boxs = filterPanelCheckbox;
				
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
					   click(filterPanelApplyBtn,"Apply");
				   }else if(comm.equalsIgnoreCase("Clear")){
					   click(filterPanelClearBtn,"Clear");
				   }else if(comm.equalsIgnoreCase("Delete")){
					   click(filterPanelDeleteBtn,"Delete(X)");
				   }else if(comm.equalsIgnoreCase("Minimize")){
					   click(filterPanelMinusBtn,"Minimize(-)",filterPanelApplyBtn,false);
				   }else if(comm.equalsIgnoreCase("Expand")){
					   click(filterPanelPlusBtn,"Expand(+)",filterPanelApplyBtn,true);
				   }else if(comm.equalsIgnoreCase("Disable")){
						if(filterPanelDisableBtn.size()>0){
							click(filterPanelDisableBtn.get(0),"Toggle off filter",filterPanelEnableBtn,false);
						}
				   }else if(comm.equalsIgnoreCase("Enable")){
					   //click(filterPanelEnableBtn,"Enable");
						if(filterPanelDisableBtn.size()<1){
							click(filterPanelEnableBtn,"Toggle on filter",filterPanelEnableBtn,false);
						}else{
							logTAFWarning("Filter was enabled? Toggle on skiped");
						}
				   }
			   }
		   }

		  
		  public FilterPanelPage(WebDriver driver){
			  
			    this.pageDriver = driver; 
			    qfPage = PageFactory.initElements(driver, QuickFilterPage.class);
		  }


}
