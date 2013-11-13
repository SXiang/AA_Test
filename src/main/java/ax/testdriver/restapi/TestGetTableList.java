package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetTableList  extends TestDriverExampleHelper implements TestDriverInterface {
	
    public static void main(String[] args){
    	TestGetTableList test = new TestGetTableList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 16; // 4-9
		//endAtLine = 16;     //
			
		String poolFile = "src/main/resources/testdata/ax/testdriver/restapi/GetTableList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}