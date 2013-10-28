package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetTestSetsList extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetTestSetsList test = new TestGetTestSetsList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 14; // 4-9
		//endAtLine = 15;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetTestSetsList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
}
