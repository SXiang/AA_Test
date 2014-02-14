/**
 * 
 */
package ax.testdriver.restapi.analytics;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestPostParameters.java</b>
 * Generated     : <b>2:31:17 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Feb 12, 2014
 * @author steven_xiang
 * 
 */
public class TestPostParameters extends TestDriverExampleHelper implements TestDriverInterface{

    public static void main(String[] args){
    	TestPostParameters test = new TestPostParameters();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 6; // 4-9
		//endAtLine = 33;    

		String poolFile = "src/main/resources/testdata/ax/testdriver/restapi/analytics/TestPost_Parameters.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
}
