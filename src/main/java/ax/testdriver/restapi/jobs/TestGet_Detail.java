/**
 * 
 */
package ax.testdriver.restapi.jobs;

/**
 * Script Name   : <b>TestGetTables.java</b>
 * Generated     : <b>1:17:59 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Oct 30, 2013
 * @author steven_xiang
 * 
 */
import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;



public class TestGet_Detail  extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGet_Detail test = new TestGet_Detail();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine =2;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/jobs/TestGet_Detail.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}
