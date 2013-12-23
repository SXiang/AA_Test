/**
 * 
 */
package anr.keyword.frontend;

/**
 * Script Name   : <b>TableChart.java</b>
 * Generated     : <b>3:42:35 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 5, 2013
 * @author steven_xiang
 * 
 */


import org.openqa.selenium.support.PageFactory;

import anr.apppage.CommonWebHelper;
import anr.apppage.DataVisualizationPage;
import anr.lib.frontend.*;
import ax.lib.frontend.FrontendCommonHelper;

import com.acl.qa.taf.helper.Interface.KeywordInterface;




public class TableChart  extends CommonWebHelper implements KeywordInterface {

	// AX Server: autoqawin2012.aclqa.local - 10.83
	// BEGIN of datapool variables declaration
	protected String dpCategory;   	//@arg value of Category
	protected String dpSubCategory;   	//@arg value of Sub-Category, for certain charts
	protected String dpValue;   	    //@arg value of chart
	protected String dpSummarizedType; //@arg summarized type 
	                                   //@value = Sum|Average|Min|Max
	protected String dpChartAction;   //@arg action for chart configuration
	                                  //@value = Delete|Add|Modify|Verify
	protected int dpChartIndex;    //@arg tab index
	protected String dpChartType;     //@arg chart type to work on
	                                  //value = PieChart|BarChart|AreaChart
	// END of datapool variables declaration

	protected DataVisualizationPage dvpage;
	
	@Override
	public boolean dataInitialization() {

		super.dataInitialization();
		
		//*** read in data from datapool     

		dpCategory = getDpString("Category");
		dpSubCategory = getDpString("SubCategory");
		dpValue = getDpString("Value");
		dpSummarizedType = getDpString("SummarizedType");
		dpChartAction = getDpString("ChartAction");
		dpChartIndex = getDpInteger("ChartIndex");
        dpChartType = getDpString("ChartType");
        delFile = true;
		return true;
	}
	
	//***************  Part 2  *******************
	// *********** Test logic ********************
	// *******************************************
	
	//Comments - 'Steps' in this part are used to generate keyword docs
	@Override
	public void testMain(Object[] args){			
		super.testMain(onInitialize(args, getClass().getName()));
		
		//Steps:
		//@Step Verify data visualization page
		//@Step Create/Delete charts
		//@Step ...
		
		//sleep(2);
		dvpage = PageFactory.initElements(driver, DataVisualizationPage.class);
		if(dpChartAction.equalsIgnoreCase("Add")){
			logTAFStep("Click adding new chart");
			//dvpage.addNewChart.click();
			dvpage.addNewChart(dpChartType);
			dvpage.expandConfPanel(true);
			configChart();
		}else{
			dvpage.activateChart(dpChartIndex);
			dvpage.expandConfPanel(true);
			if(dpChartAction.equalsIgnoreCase("Delete")){
				dvpage.deleteConf();
		    }else if(dpChartAction.equalsIgnoreCase("Modify")){
		    	configChart();
		    }else if(dpChartAction.equalsIgnoreCase("Verify")){
			    verifyChart();
		    }
		}
		
		doVerification();
	
		cleanUp();		
	  //*** cleanup by framework ***		
		onTerminate();
	}
	
	//***************  Part 3  *******************
	// *** Implementation of test functions ******
	// *******************************************
	
	private void configChart(){
		dvpage.selectChartValue("Category",dpCategory);
		dvpage.selectChartValue("Sub-Category",dpSubCategory);
		dvpage.selectChartValue("Value",dpValue);
		dvpage.click(dvpage.applyChartConf,"Apply");
		//dvpage.applyChartConf.click();
		//dvpage.expandConfPanel(false);
	}
	
	private void verifyChart(){
		dvpage.verifyChartConf("Category",dpCategory);
		dvpage.verifyChartConf("Sub-Category",dpSubCategory);
		dvpage.verifyChartConf("Value",dpValue);
	}
	


	public void doVerification(){
		
		//As browsers render charts in diff size, we use diff master for diff browsers now, disable this line if we expecting exact same charts from diff browsers.
		           dpMasterFiles[0] = dpMasterFiles[0].replaceFirst("\\.jpeg", projectConf.webDriver+".jpeg");
		//********************************************************************************************************************************************************
		           
		String _masterImage = setupMasterFile(dpMasterFiles[0]);
		String _actualImage = thisActualFile;
		String extendedImage = "";
		String masterImage = _masterImage;
		String actualImage = _actualImage;
		
		if(_masterImage.equals(""))
			return;
		//dvpage.expandConfPanel(false);
		logTAFStep("Verify original chart...");
		dvpage.saveChartImage(driver,actualImage);
		verifyImage(masterImage,actualImage);
		
		logTAFStep("Verify chart by click one series");
		extendedImage = "_toggle1.jpeg";
		masterImage = modifyMasterFile(extendedImage);
		actualImage = thisActualFile;
		if(dpChartType.equalsIgnoreCase("AreaChart"))
		    dvpage.selectArea(driver,"Stream");
		dvpage.selectSeries(driver,false);
		dvpage.saveChartImage(driver,actualImage);
		verifyImage(masterImage,actualImage);
		
		logTAFStep("Verify chart by double click one series");
		
		extendedImage = "_toggle2.jpeg";
		masterImage = modifyMasterFile(extendedImage);
		actualImage = thisActualFile;
		if(dpChartType.equalsIgnoreCase("AreaChart"))
		     dvpage.selectArea(driver,"Expanded");
		dvpage.selectSeries(driver,true);
		dvpage.saveChartImage(driver,actualImage);
		verifyImage(masterImage,actualImage);
		
		}


 }