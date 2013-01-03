package ACL_Desktop.TestCase.Tools;

import resources.ACL_Desktop.TestCase.Tools.ExportMemUsageTestHelper;
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

public class ExportMemUsageTest extends ExportMemUsageTestHelper
{
	/**
	 * Script Name   : <b>ExportMemUsageTest</b>
	 * Generated     : <b>Jun 28, 2012 10:54:55 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/06/28
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Data/ExportToApp_MemUsage.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 2; //23
		endAtLine = 22;     //43

	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}

