package an.testdriver.restapi;

import an.lib.restapi.TestDriverExampleHelper;
import an.testdriver.restapi.TestDriverExample;

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

		startFromLine = 2; // 4-9
		//endAtLine =2;     //
			
		String poolFile = "testdata/ax/testdriver/restapi/TestDriverExample.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}
	
}

