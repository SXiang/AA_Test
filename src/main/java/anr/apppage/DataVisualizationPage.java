/**
 * 
 */
package anr.apppage;

import java.awt.Rectangle;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver.Window;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ax.lib.frontend.FrontendCommonHelper;

import com.acl.qa.taf.helper.KeywordSuperHelper;

/**
 * Script Name   : <b>DataVisualizationPage.java</b>
 * Generated     : <b>5:38:19 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 12, 2013
 * @author steven_xiang
 * 
 */
public class DataVisualizationPage extends FrontendCommonHelper{

	//*** Final varialbes	
//	 private  final WebDriver pageDriver=driver;
//	 private  final Actions actionDriver=new Actions(driver);
	 private final int chartLoadTime = 3;
    //*** Common elements
	  @FindBy(xpath = "//div[@class='tabbable']/ul[@class='nav nav-tabs']/li[@active='t.active']")
	  private List<WebElement> navTabs;
	 @FindBy(xpath = "//div[@ng-click='openChartSelectorModal()' and @tooltip='Add a new chart']/i[@type='button']")
	  //@FindBy(xpath = "//div[@tooltip='Add a new chart']/i[@type='button']")
	  private WebElement addNewChart;  
	  
    //*** Add chart
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Pie Chart']")
	  public WebElement pieChart;
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Stacked Area Chart']")
	  public WebElement areaChart;
	  @FindBy(xpath = "//div[@ng-click='pickChart(chartType)' and @tooltip='Bar Chart']")
	  public WebElement barChart;
	  
	//*** Chart configuration
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//i[@ng-click='toggleChartConfigPanel()']") 
	  ///html/body/div/div/div/div/div[2]/div/section/div[2]/div/div[3]/div/div[2]/div/div[1]/i
	  public WebElement configurePanel;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//i[@ng-click='toggleChartConfigPanel()']/div[@class='chartconfig-btn ng-binding' and text()='Configure']") 
	  public WebElement configureButton;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Category']") 
	  public WebElement categorySelect;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Sub-Category']")
	  public WebElement subCategorySelect;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/select[@tooltip='Value']")
	  public WebElement valueSelect;
	  
	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='average']")
	  public WebElement averageChart;	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='sum']")
	  public WebElement sumChart;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='min']")
	  public WebElement minChart;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider']/div/button[@btn-radio='max']")
	  public WebElement maxChart;
	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider-last']/input[@id='submit' and @value='Apply']")
	  public WebElement applyChartConf;
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//div[@class='chart-panels-divider-last']/input[@value='Delete chart']")
	  public WebElement deleteChartConf;
	  
	  
     //*** Chart Details
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']//g[@class='nv-series' or @class='nv-series disabled']")
	  ///text[@text-anchor='start' and @class='nv-legend-text']")
	  public List<WebElement> nvSeries;
	  
	  @FindBy(xpath = "//div[@class='tab-pane ng-scope active']/svg//g[@class='nv-pieWarp']"
	  		//+ " or @class='nv-barsWarp' or @class='nv-areaWarp']"
			  )
	  //@FindBy(xpath = "//svg[@id='chartcontainer2']/g/g/g[@class='nv-pieWarp' or @class='nv-barsWarp' or @class='nv-areaWarp']")

	  public WebElement chartWarp;
	  
	  public WebElement activateChart(int tabIndex){
		  WebElement currentTab = navTabs.get(tabIndex);
		  logTAFStep("Activate chart - tab "+tabIndex);
		  currentTab.click();
		  sleep(chartLoadTime);
		  return currentTab;
	  }
	  
	  public void expandConfPanel(){	
		  expandConfPanel(true);
	  }
	  
	  public void expandConfPanel(boolean expand){
		  try{
		  if(!configurePanel.getText().contains("before")){
			  if(expand){
			    logTAFStep("Expand configuration panel by clicking 'Configure' button");
			    configureButton.click();
			  }
		  }else{
			  if(!expand){
				    logTAFStep("Hide configuration panel by clicking 'Configure' button");
				    configureButton.click();
				  }
		  }
		  }catch(Exception e){
			  logTAFWarning(e.toString());
		  }
	  }
	  
	  public void selectChartValue(String type, String option){
		  if(option==null||option.trim().equals(""))
			  return;
		  Select select = getSelect(type);
          logTAFStep("Select '"+type+"' - '"+option+"'");
		  select.selectByVisibleText(option);
		  //select.selectByValue(option);
	  }
	  
	  public void verifyChartConf(String type, String option){
		  if(option==null||option.trim().equals(""))
			  return;
		  Select select = getSelect(type);		
		  String selectedOption = select.getFirstSelectedOption().getText().trim();
		  if( selectedOption.equals(option)){
			  logTAFInfo(type+" value '"+option+"' is selected correctly");
		  }else{
			  logTAFError(type+" value '"+option +"' is not selected, current selected value is '"+selectedOption+"'");
		  }
		  
	  }
	  public void deleteConf(){
		  logTAFStep("Delete chart configuration");
		  deleteChartConf.click();
	  }
	  
	  public void saveChartImage(WebDriver pageDriver, String fileName){
		  sleep(chartLoadTime);
		  
		  Window win = pageDriver.manage().window();
		  
		  Point winpt = win.getPosition();
		  Dimension windi = win.getSize();
		  Point pt = new Point(winpt.x+200,winpt.y+200);
		  Dimension di = new Dimension(windi.width-400,windi.height-150);
		  try{//debugging... 
		   // pt = chartWarp.getLocation();
		   // di = chartWarp.getSize();
		  }catch(Exception e){
			  logTAFWarning("ChartWarp not found?"+e.toString().substring(0,10)+"...");
		  }
		  
		  int width = di.width+pt.x > windi.width + winpt.x ? (windi.width - pt.x -20):di.width;
		  int height = di.height+pt.y > windi.height + winpt.y ? (windi.height - pt.y -20):di.height;
		  
		  Rectangle rec = new Rectangle(pt.x, pt.y, width, height);
		  logTAFStep("Take screenshot on current chart rectangle '"+rec+"'");
		  captureScreen(fileName, rec);
		  logTAFInfo("Chart image is saved to  '"+fileName+"'");
		  
	  }
	  
	  public void selectSeries(WebDriver driver, boolean exclusive){
		  if(nvSeries.size()==0){
			  logTAFWarning("nv-series not found?");
			  return;
		  }
		  WebElement nvs = nvSeries.get(nvSeries.size()/2);
		  
		  if(exclusive){
			  nvs = nvSeries.get(nvSeries.size()/2);
			  logTAFStep("Double click series '"+nvs.getText());
			  new Actions(driver).doubleClick(nvs).perform();
		  }else{
			  nvs = nvSeries.get(0);
		      logTAFStep("Click series '"+nvs.getText());
			  nvs.click();
		  }
		  
	  }
	  //**** Private methods ****
	  private Select getSelect(String type){
		  Select select;
		  if(type.equalsIgnoreCase("Category")){
			  select = new Select(categorySelect);
		  }else if(type.equalsIgnoreCase("Sub-Category")){
			  select = new Select(subCategorySelect);
		  }else if(type.equalsIgnoreCase("Value")){
			  select = new Select(valueSelect);
		  }else{
			  logTAFWarning("Select type '"+type+"' is not valid? ");
			  select = new Select(categorySelect);
		  }
		  
		  return select;
	  }
	  
	  
	  public void addNewChart(String type){
		  addNewChart.click();
		  sleep(chartLoadTime);
		  if(type.equals("BarChart")){
			  barChart.click();
		  }else if(type.equals("PieChart")){
			  pieChart.click();
		  }else if(type.equals("AreaChart")){
			  areaChart.click();
		  }else{
			  pieChart.click();
		  }
		  sleep(chartLoadTime);
	  }
	  
	 // public DataVisualizationPage(WebDriver driver){
		  //this.pageDriver = driver; 
		  //this.actionDriver = new Actions(driver);
	 // }
}
