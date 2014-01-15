package anr.testdriver.restapi.tables;

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

public class TestPost_FilterAndSorting extends TestDriverExampleHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestPost_FilterAndSorting test = new TestPost_FilterAndSorting();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine = 100;    

		String poolFile = "src/main/resources/testdata/anr/testdriver/restapi/tables/TestPost_filterandsorting.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
