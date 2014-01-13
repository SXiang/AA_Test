/**
 * 
 */
package anr.testdriver.frontend;

/**
 * Script Name   : <b>TestTableChartView.java</b>
 * Generated     : <b>3:37:28 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 5, 2013
 * @author steven_xiang
 * 
 */
import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestTableChartView extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestTableChartView test = new TestTableChartView();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine = 11;
			
		String poolFile = "testdata/anr/testdriver/frontend/TestQuickFilter.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));		
	}

}
