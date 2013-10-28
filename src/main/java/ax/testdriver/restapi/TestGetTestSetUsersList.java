package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;
import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetTestSetUsersList extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetTestSetUsersList test = new TestGetTestSetUsersList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 15; // 4-9
		//endAtLine = 16;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetTestSetUsersList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
}
