/**
 * 
 */
package anr.testdriver.frontend;

import ax.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestQuickFilter.java</b>
 * Generated     : <b>9:32:16 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 20, 2013
 * @author steven_xiang
 * 
 */
public class TestFilter extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		TestFilter test = new TestFilter();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9,12(1000 records
		//endAtLine = 2;     //
			
		String poolFile = "testdata/anr/testdriver/frontend/TestFilter.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
}
