/**
 * 
 */
package ax.testdriver.restapi.analytics;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestPost_Data.java</b>
 * Generated     : <b>9:58:59 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 12, 2013
 * @author Karen_Zou
 * 
 */
public class TestPost_Run extends TestDriverExampleHelper implements TestDriverInterface{

	/**
	 * 
	 */
    public static void main(String[] args){
    	TestPost_Run test = new TestPost_Run();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 11; // 4-9
		//endAtLine = 11;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/analytics/TestPost_Run.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
