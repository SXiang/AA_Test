package ACL_Desktop.TestCase.SmokeTest.Analyze;

import resources.ACL_Desktop.TestCase.SmokeTest.Analyze.HistogramHelper;
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

public class Histogram extends HistogramHelper
{
	/**
	 * Script Name   : <b>Histogram</b>
	 * Generated     : <b>Mar 24, 2012 3:52:46 PM</b>
	 * Description   : Functional Test Script
	 * 
	 * @since  2012/03/24
	 * @author Steven_Xiang
	 */
	private String poolFile = "ACL_Desktop/DATA/KeywordTable/SmokeTest/Analyze/Histogram.xls";
	public void testMain(Object[] args) 
	{
		startFromLine = 11; // 4-9
		endAtLine = 12;     //
	    exeTestCase(args);
	}
			
	public void onInitialize() {
		onInitialize(poolFile,getClass().getName());
	}
}


