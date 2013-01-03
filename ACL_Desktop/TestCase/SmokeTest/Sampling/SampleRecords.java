package ACL_Desktop.TestCase.SmokeTest.Sampling;

import resources.ACL_Desktop.TestCase.SmokeTest.Sampling.SampleRecordsHelper;
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

public class SampleRecords extends SampleRecordsHelper
{
	/**
	 * Script Name   : <b>SampleRecords</b>
	 * Generated     : <b>2012-03-20 1:51:45 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/20
	 * @author Karen_zou
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Sampling/SampleRecords.xls";
	
	public void testMain(Object[] args) 
	{
		startFromLine = 2; // 
		endAtLine = 8;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}

