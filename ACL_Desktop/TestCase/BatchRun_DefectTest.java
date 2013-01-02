package ACL_Desktop.TestCase;

import resources.ACL_Desktop.TestCase.BatchRun_DefectTestHelper;
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

public class BatchRun_DefectTest extends BatchRun_DefectTestHelper
{
	/**
	 * Script Name   : <b>BatchRun_DefectTest</b>
	 * Generated     : <b>May 25, 2012 10:31:15 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/05/25
	 * @author Steven_Xiang
	 */
private String batchRunDataFile = "ACL_Desktop/DATA/TempData/batchRunData_Debug.xls";
	
	public void testMain(Object[] args) 
	{
		//startFromLine = 10;
		//endAtLine = 3;
		testCategory = "Defect";
		exeBatchRun(args);
	}
		
	public void onInitialize() {
		onInitialize(batchRunDataFile, getClass().getName());
	}
}

