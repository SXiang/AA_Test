package ax;
import com.acl.qa.taf.helper.Interface.TestSuiteInterface;

import ax.lib.TestSuiteHelper;

public class TestSuiteExample extends TestSuiteHelper implements TestSuiteInterface {

	/**
	 * Script Name   : <b>TestSuiteExample</b>
	 * Generated     : <b>Aug 24, 2013 10:34:01 AM</b>
	 * Description   : ACL Test Automation
	 * 
	 * @since  2013/08/24
	 * @author Steven_Xiang
	 */
	
    
	
    public static void main(String[] args){
    	TestSuiteExample test = new TestSuiteExample();
    	test.setMainScript(true);
    	test.onInitialize(args);
    }
    
	public void testMain(Object[] args) 
	{
		//testCategory = "Daily";
		//startFromLine = 37; // 4-9
		//endAtLine =2;     //
		exeBatchRun(args);
	}
		
	public void onInitialize(Object[] args) {
		String batchRunDataFile = "testdata/ax/AutomationTestSuites_template.xls";
		testMain(onInitialize(batchRunDataFile, getClass().getName()));
	}
	
}
