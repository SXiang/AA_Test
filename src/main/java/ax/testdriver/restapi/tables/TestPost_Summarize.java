/**
 * 
 */
package ax.testdriver.restapi.tables;

/**
 * Script Name   : <b>TestPost_Summarize.java</b>
 * Generated     : <b>9:58:59 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 1, 2013
 * @author steven_xiang
 * 
 */
/**
 * 
 */

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

/**
 * Script Name   : <b>TestPost_Summarize.java</b>
 * Generated     : <b>9:58:59 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 1, 2013
 * @author steven_xiang
 * 
 */
public class TestPost_Summarize extends TestDriverExampleHelper implements TestDriverInterface{

	/**
	 * 
	 */
    public static void main(String[] args){
    	TestPost_Summarize test = new TestPost_Summarize();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 2; // 4-9
		//endAtLine =3;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/tables/TestPost_Summarize.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}