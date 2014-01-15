package anr.testdriver.restapi.analytic;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;
import anr.lib.restapi.TestDriverExampleHelper;

/**
 * Script Name   : <b>TestPost_OpenProject.java</b>
 * Generated     : <b>4:54:25 PM</b> 
 * Description   : <b>ACL ANR Test Automation</b>
 * 
 * @since  Dec 2, 2013
 * @author karen_zou
 * 
 */

public class TestPost_Run_WithNoParameters extends TestDriverExampleHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestPost_Run_WithNoParameters test = new TestPost_Run_WithNoParameters();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 32; // 4-9
		endAtLine = 33;    

		String poolFile = "src/main/resources/testdata/anr/testdriver/restapi/analytic/TestPost_Run_WithNoParameters.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
