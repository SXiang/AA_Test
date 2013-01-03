package ACL_Desktop.TestCase;

import resources.ACL_Desktop.TestCase.BatchRun_AllTestHelper;
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

public class BatchRun_AllTest extends BatchRun_AllTestHelper
{
	/**
	 * Script Name   : <b>BatchRun_AllTest</b>
	 * Generated     : <b>May 25, 2012 10:33:18 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/05/25
	 * @author Steven_Xiang
	 */
private String batchRunDataFile = "ACL_Desktop/DATA/KeywordTable/batchRunData.xls";
	
	public void testMain(Object[] args) 
	{
		//startFromLine = 10;
		//endAtLine = 3;
		testCategory = "All";
		exeBatchRun(args);
	}
		
	public void onInitialize() {
		onInitialize(batchRunDataFile, getClass().getName());
	}
}


