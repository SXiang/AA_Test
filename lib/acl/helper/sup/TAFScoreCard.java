/*
public abstract class LogExtensionAdapter
extends com.rational.test.ft.services.LogAdapter

This is the base class for all Rational Functional Tester log extensions

Implementors should only write the following methods to get the desired log results they wish to design.
public void initLog()
public void closeLog()
public void writeLog(ILogMessage message)

Sample implementation of the subclass is shown below to get the sample text log output of a sample script
Sample Text Log
July 23, 2007 8:30:12 PM IST :Script Name Script1.java Result :INFO Event SCRIPT START headlind Script start [Script1]
Property Name =line_number Property Value =1
Property Name =script_name Property Value =Script1
Property Name =script_id Property Value =Script1.java


July 23, 2007 8:30:12 PM IST :Script Name Script1.java Result :PASS Event SCRIPT END headlind Script end [Script1]
Property Name =line_number Property Value =-1
Property Name =script_name Property Value =Script1
Property Name =script_id Property Value =Script1.java
*/

package lib.acl.helper.sup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import com.rational.test.ft.services.ILogMessage;
import com.rational.test.ft.services.LogException;
import com.rational.test.ft.services.LogExtensionAdapter;
import com.rational.test.ft.services.LogMessageProperty;


public class TAFScoreCard extends LogExtensionAdapter {

	private String logName=null;
	private String logDirectory=null;
	private PrintWriter out=null;

	public TAFScoreCard(String logName) {
		super(logName);
		this.logName=logName;
		this.logDirectory=null;
	}

	public TAFScoreCard () {
		super();
		this.logName=null;
		this.logDirectory=null;
	}


	public void closeLog() {
		try{
			out.close();
		}catch(Exception e){


		}
	}

	public void initLog() throws LogException {
		try{
			this.logName=getLogName();
			this.logDirectory=getLogDirectory();
			File logFile=new File(logDirectory,logName+".txt");
			FileOutputStream fos=new FileOutputStream(logFile);
			out=new PrintWriter(fos);
		}catch(IOException e)
		{

		}
	}

	public void writeLog(ILogMessage message) {
		Vector properties=message.getProperties();
		String result=getResult(message);
		String event_type=getEventType(message);
		String headline=getHeadline(message);
		String timestamp=getTimestamp();
		String currentScriptName=getScriptName(message);
		out.println(timestamp + " :Script Name " + currentScriptName + " Result :" + result + " Event " + event_type + " headlind " + headline );
		for(int i=0,size=properties.size();i<size;i++)  {
			LogMessageProperty property =
				(LogMessageProperty) properties.elementAt( i );
			out.println("Property Name =" + property.getName().toString() + " Property Value =" +property.getValue().toString() );
		} 


		out.println();
	}

	private String getResult(ILogMessage message) {
		String result=null;
		switch (message.getResult())
		{
		case LOG_FAILURE : result="FAILURE";break;
		case LOG_PASS : result="PASS";break;
		case LOG_WARNING : result="WARNING";break;
		default: result= "INFO";
		}
		return result;
	}

	private String getEventType(ILogMessage message) {
		String eventType=null;
		switch(message.getEvent())
		{
		case EVENT_SCRIPT_START : eventType="SCRIPT START";break;
		case EVENT_SCRIPT_END : eventType="SCRIPT END";break;
		case EVENT_VP : eventType="VERIFCATION POINT";break;
		case EVENT_CALL_SCRIPT : eventType = "CALL_SCRIPT"; break;
		case EVENT_APPLICATION_START : eventType="APPLICATION START";break;
		case EVENT_APPLICATION_END : eventType="APPLICATION END";break;
		case EVENT_TIMER_START : eventType="TIMER START";break;
		case EVENT_TIMER_END : eventType= "TIMER END" ;break;
		case EVENT_CONFIGURATION : eventType="CONFIGURATION"; break;
		default : eventType="GENERAL";
		} 

		return eventType;
	}

	private String getHeadline(ILogMessage message) {
		return message.getHeadline();
	}

	private String getScriptName(ILogMessage message) {
		String scriptName=null;
		Vector properties=message.getProperties();
		for(int i=0,size=properties.size();i<size;i++)  {
			LogMessageProperty property =
				(LogMessageProperty) properties.elementAt( i );
			if(property.getName().equalsIgnoreCase(PROP_SCRIPT_ID))
			{
				scriptName=property.getValue().toString();
			}
		}
		return scriptName;
	}
}



