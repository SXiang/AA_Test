/**
 * 
 */
package ax.testdriver.restapi.jobs;

/**
 * Script Name   : <b>TestGetTables.java</b>
 * Generated     : <b>1:17:59 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov. 12, 2013
 * @author Karen_Zou
 * 
 */
import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;



public class TestGet_Results  extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGet_Results test = new TestGet_Results();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine =3;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/jobs/TestGet_Results.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}

