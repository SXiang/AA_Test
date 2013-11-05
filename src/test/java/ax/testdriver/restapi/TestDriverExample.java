package ax.testdriver.restapi;

import ax.lib.restapi.TestDriverExampleHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;



public class TestDriverExample  extends TestDriverExampleHelper implements TestDriverInterface{
	
	
    public static void main(String[] args){
    	TestDriverExample test = new TestDriverExample();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 8; // 4-9
	    endAtLine =8;     // 
			
		String poolFile = "testdata/ax/testdriver/restapi/TestDriverExample.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}
