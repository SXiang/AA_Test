package ACL_Desktop.TestCase.Tools;

import resources.ACL_Desktop.TestCase.Tools.RunScriptHelper;
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

public class RunScript extends RunScriptHelper
{
	/**
	 * Script Name   : <b>RunScript</b>
	 * Generated     : <b>Apr 18, 2012 11:53:17 AM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/04/18
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/Tools/RunScript.xls";

	public void testMain(Object[] args) 
	{
		startFromLine = 9; // 4-9
		endAtLine =9;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}


