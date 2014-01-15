package anr.testdriver.restapi.aclproject;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;
import anr.lib.restapi.TestDriverExampleHelper;

/**
 * Script Name   : <b>TestPost_OpenProject.java</b>
 * Generated     : <b>4:54:25 PM</b> 
 * Description   : <b>ACL ANR Test Automation</b>
 * 
 * @since  Dec 10, 2013
 * @author karen_zou
 * 
 */

public class TestGet_Datatables extends TestDriverExampleHelper implements TestDriverInterface{
	
    public static void main(String[] args){
    	TestGet_Datatables test = new TestGet_Datatables();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		//startFromLine = 18; // 4-9
		//endAtLine = 18;    

		String poolFile = "src/main/resources/testdata/anr/testdriver/restapi/aclproject/TestGet_Datatables.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
