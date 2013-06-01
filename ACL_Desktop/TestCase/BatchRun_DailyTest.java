package ACL_Desktop.TestCase;

import resources.ACL_Desktop.TestCase.BatchRun_DailyTestHelper;
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

public class BatchRun_DailyTest extends BatchRun_DailyTestHelper
{
	/**
	 * Script Name   : <b>BatchRun_DailyTest</b>
	 * Generated     : <b>Apr 10, 2012 2:01:27 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/10
	 * @author Steven_Xiang
	 */
private String batchRunDataFile = "ACL_Desktop/DATA/KeywordTable/batchRunData.xls";
	
	public void testMain(Object[] args) 
	{
		//startFromLine = 2;
		//endAtLine = 2;
		testCategory = "Daily";
		exeBatchRun(args);
	}
		
	public void onInitialize() {
		onInitialize(batchRunDataFile, getClass().getName());
	}
}

