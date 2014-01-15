/**
 * 
 */
package ax.testdriver.restapi.parametersets;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

import ax.lib.restapi.TestDriverExampleHelper;

/**
 * Script Name   : <b>TestGet_Detail.java</b>
 * Generated     : <b>4:54:25 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Nov 4, 2013
 * @author steven_xiang
 * 
 */

	public class TestGet_Detail  extends TestDriverExampleHelper implements TestDriverInterface{
		
		
	    public static void main(String[] args){
	    	TestGet_Detail test = new TestGet_Detail();
	    	test.setMainScript(true);
	    	test.testMain(args);
	    }
	    
	    
		@Override
		public void testMain(Object[] args) 
		{		

			//startFromLine = 8; // 4-9
			//endAtLine =2;     //

			String poolFile = "src/main/resources/testdata/ax/testdriver/restapi/parametersets/TestGet_Detail.xls";
			exeTestCase(onInitialize(poolFile,getClass().getName()));
			
		}
		
	}