package ax;
import com.acl.qa.taf.helper.Interface.TestSuiteInterface;

import ax.lib.restapi.TestSuiteExampleHelper;

public class TestSuiteRestAPI extends TestSuiteExampleHelper implements TestSuiteInterface {

	/**
	 * Script Name   : <b>TestSuiteFrontEnd</b>
	 * Generated     : <b>Sep 18, 2013</b>
	 * Description   : ACL Test Automation TestSuiteFrontEnd
	 * 
	 * @author karen_zou
	 */
		
    public static void main(String[] args){
    	TestSuiteRestAPI test = new TestSuiteRestAPI();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
	@Override
	public void testMain(Object[] args) 
	{
		//testCategory = "Daily";
		//startFromLine = 37; // 4-9
		//endAtLine =2;     //
		//logTAFInfo(System.getProperty("user.dir"));
    	//String poolFile = "testdata/ax/TestSuiteRestAPI.xls";
		String poolFile = "C:/GitHub/ACLQAAutomation/automation/src/main/resources/testdata/ax/TestSuiteRestAPI.xls";
		exeBatchRun(onInitialize(poolFile, getClass().getName()));
	}
		
	
}
