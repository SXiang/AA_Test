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



public class TestGet_Classify  extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGet_Classify test = new TestGet_Classify();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine =3;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/tables/TestGet_Classify.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}

