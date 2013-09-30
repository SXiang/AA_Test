package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class TestGetAnalyticsList extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestGetAnalyticsList test = new TestGetAnalyticsList();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		
		//startFromLine = 7; // 4-9
		//endAtLine = 7;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/GetAnalyticsList.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
	}
	
}
