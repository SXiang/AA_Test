package ACL_Desktop.TestCase.SmokeTest.File;

import resources.ACL_Desktop.TestCase.SmokeTest.File.FilterHistoryHelper;
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

public class FilterHistory extends FilterHistoryHelper
{
	/**
	 * Script Name   : <b>FilterHistory</b>
	 * Generated     : <b>2012-10-19 3:44:27 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/10/19
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/File/FilterHistory.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 10; // 4-9
		//endAtLine =13;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}
