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

public class TestPost_RecordCount extends TestDriverExampleHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestPost_RecordCount test = new TestPost_RecordCount();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine = 18;    

		String poolFile = "src/main/resources/testdata/anr/testdriver/restapi/analytics/tables/TestPost_recordcount.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}