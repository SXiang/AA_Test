package ACL_Desktop.TestCase;

import resources.ACL_Desktop.TestCase.AutoTestHelper;
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

public class AutoTest extends AutoTestHelper
{
	/**
	 * Script Name   : <b>AutoTest</b>
	 * Generated     : <b>Apr 24, 2012 10:34:01 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/24
	 * @author Steven_Xiang
	 */
	
    private String batchRunDataFile = "ACL_Desktop/DATA/KeywordTable/batchRunData.xls";
	
	public void testMain(Object[] args) 
	{
		//testCategory = "Daily";
		//startFromLine = 37; // 4-9
		//endAtLine =2;     //
		exeBatchRun(args);
	}
		
	public void onInitialize() {
		onInitialize(batchRunDataFile, getClass().getName());
	}
}


