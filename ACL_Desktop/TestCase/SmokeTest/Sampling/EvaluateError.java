package ACL_Desktop.TestCase.SmokeTest.Sampling;

import resources.ACL_Desktop.TestCase.SmokeTest.Sampling.EvaluateErrorHelper;
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

public class EvaluateError extends EvaluateErrorHelper
{
	/**
	 * Script Name   : <b>EvaluateError</b>
	 * Generated     : <b>2012-03-18 1:24:23 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/18
	 * @author Karen_zou
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Sampling/EvaluateError.xls";
	
	public void testMain(Object[] args) 
	{
//		startFromLine = 2; // 
//		endAtLine = 2;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}

