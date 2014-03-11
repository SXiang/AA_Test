package anr.testdriver.restapi.analytic.tables;

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

public class TestGet_Classify extends TestDriverExampleHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestGet_Classify test = new TestGet_Classify();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine = 34;    

		String poolFile = "src/main/resources/testdata/anr/testdriver/restapi/analytics/tables/TestGet_Classify.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
