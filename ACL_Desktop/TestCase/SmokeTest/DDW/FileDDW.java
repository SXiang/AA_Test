package ACL_Desktop.TestCase.SmokeTest.DDW;

import resources.ACL_Desktop.TestCase.SmokeTest.DDW.FileDDWHelper;
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

public class FileDDW extends FileDDWHelper
{
	/**
	 * Script Name   : <b>FileDDW</b>
	 * Generated     : <b>2012-02-09 4:52:26 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/02/09
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/DDW/FileDDW.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 5; // 4-9
		endAtLine = 5;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}