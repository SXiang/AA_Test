package ACL_Desktop.TestCase.SmokeTest.Data;

import resources.ACL_Desktop.TestCase.SmokeTest.Data.ReportHelper;
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

public class Report extends ReportHelper
{
	/**
	 * Script Name   : <b>Report</b>
	 * Generated     : <b>Mar 13, 2012 11:02:12 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/13
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Data/Report.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 6; // 4-9
		endAtLine = 7;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}


