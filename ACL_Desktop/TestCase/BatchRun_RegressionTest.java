package ACL_Desktop.TestCase;

import resources.ACL_Desktop.TestCase.BatchRun_RegressionTestHelper;
import com.rational.test.ft.*;
import com.rational.test.ft.object.interfaces.*;
import com.rational.test.ft.object.interfaces.SAP.*;
import com.rational.test.ft.object.interfaces.WPF.*;
import com.rational.test.ft.object.interfaces.dojo.*;
import com.rational.test.ft.object.interfaces.siebel.*;
import com.rational.test.ft.object.interfaces.flex.*;
import com.rational.test.ft.object.interfaces.generichtmlsubdomain.*;
import com.rational.test.ft.script.*;
import com.rational.test.ft.value.*;
import com.rational.test.ft.vp.*;
import com.ibm.rational.test.ft.object.interfaces.sapwebportal.*;

public class BatchRun_RegressionTest extends BatchRun_RegressionTestHelper
{
	/**
	 * Script Name   : <b>BatchRun_RegressionTest</b>
	 * Generated     : <b>Apr 10, 2012 2:00:11 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/10
	 * @author Steven_Xiang
	 */
private String batchRunDataFile = "ACL_Desktop/TestCase/batchRunData.xls";
//private String batchRunDataFile = "ACL_Desktop/DATA/TempData/batchRunData.xls";	
	public void testMain(Object[] args) 
	{
		//startFromLine = 10;
		//endAtLine = 3;
		testCategory = "Regression";
		exeBatchRun(args);
	}
		
	public void onInitialize() {
		onInitialize(batchRunDataFile, getClass().getName());
	}
}


