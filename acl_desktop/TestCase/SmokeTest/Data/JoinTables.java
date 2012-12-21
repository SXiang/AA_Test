package ACL_Desktop.TestCase.SmokeTest.Data;

import resources.ACL_Desktop.TestCase.SmokeTest.Data.JoinTablesHelper;
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

public class JoinTables extends JoinTablesHelper
{
	/**
	 * Script Name   : <b>JoinTables</b>
	 * Generated     : <b>Mar 2, 2012 4:57:39 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/02
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Data/JoinTables.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 6; // 4-9
		endAtLine = 6;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}
