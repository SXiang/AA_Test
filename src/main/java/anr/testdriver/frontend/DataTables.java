package anr.testdriver.frontend;

import anr.lib.frontend.FrontendTestDriverHelper;

import com.acl.qa.taf.helper.Interface.TestDriverInterface;

public class DataTables extends FrontendTestDriverHelper implements TestDriverInterface{

	public static void main(String[] args){
		DataTables test = new DataTables();
    	test.setMainScript(true);
    	test.testMain(args);
    }
    
    
	@Override
	public void testMain(Object[] args) 
	{		

		startFromLine = 2; // 4-9
		endAtLine = 2;     //
			
		String poolFile = "src/main/resources/testdata/anr/testdriver/frontend/DataTables.xls";
		exeTestCase(onInitialize(poolFile,getClass().getName()));
		
	}

}
