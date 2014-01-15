/**
 * 
 */
package ax.testdriver.restapi.tables;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestPost_Data.java</b>
 * Generated     : <b>9:58:59 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 1, 2013
 * @author steven_xiang
 * 
 */
public class TestPost_Data extends TestDriverExampleHelper implements TestDriverInterface{

	/**
	 * 
	 */
    public static void main(String[] args){
    	TestPost_Data test = new TestPost_Data();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine =3;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/tables/TestPost_Data.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
