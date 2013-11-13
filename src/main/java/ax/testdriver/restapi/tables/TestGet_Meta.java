/**
 * 
 */
package ax.testdriver.restapi.tables;

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



public class TestGet_Meta  extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGet_Meta test = new TestGet_Meta();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		//endAtLine =2;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/tables/TestGet_Meta.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}

