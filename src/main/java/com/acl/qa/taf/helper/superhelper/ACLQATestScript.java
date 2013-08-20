package com.acl.qa.taf.helper.superhelper;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;











import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.acl.qa.taf.helper.TestDriverSuperHelper;
import com.acl.qa.taf.helper.TestSuiteSuperHelper;
import com.acl.qa.taf.util.FileUtil;

import conf.bean.*;


public class ACLQATestScript {

	protected String scriptName;
	protected boolean mainScript = false;
	protected static int currentLogFilter = 5;
	
	public static TimerConf timerConf = new TimerConf();
	public static ProjectConf projectConf = new ProjectConf();
	public static DBConf dbConf = new DBConf();
	public static LoggerConf loggerConf = new LoggerConf();
	public static TestSuiteSuperHelper suiteObj;
	public static TestDriverSuperHelper caseObj;
	public static Object app;
	
	protected HSSFSheet datapool = null;
	protected Iterator dpi = null;
	protected HSSFRow dpw = null;
	protected ArrayList<String> dph = null;

	protected static int getCurrentLogFilter() {
		return currentLogFilter;
	}
    protected Object getTestApp(){
    	
    	if(suiteObj!=null){
  		  app = suiteObj;
          }else if(caseObj!=null){
            app = caseObj;
          }
    	return app;
    }
    
	protected static void setCurrentLogFilter(int currentLogFilter) {
		ACLQATestScript.currentLogFilter = currentLogFilter;
	}

	protected void setMainScript(boolean mainScript) {
		this.mainScript = mainScript;
	}

	protected String getScriptName() {
		return scriptName;
	}

	protected void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}


	public static void captureScreen(String fileName) {
		 try{
		   Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		   Rectangle screenRectangle = new Rectangle(screenSize);
		   Robot robot = new Robot();
		   BufferedImage image = robot.createScreenCapture(screenRectangle);
		   String absPath = FileUtil.getAbsDir(fileName);
		   FileUtil.mkDirs(absPath);
		   LoggerHelper.logTAFDebug("captured screen '"+absPath);
		   ImageIO.write(image, "jpeg", new File(absPath));
		   //LoggerHelper.logTAFDebug("captured screen '"+absPath);
		 }catch(Exception e){
			 
		 }
		}

	protected Object callScript(String scriptFullName){
		return callScript(scriptFullName,null);
	}
	
	protected Object callScript(String scriptFullName,Object[] args){
		String methodName = "testMain";
		Class[] paramObject = new Class[] {Object[].class};		
		return callScript(scriptFullName, methodName, args, paramObject);
	}
	
	
    protected Object callScript(String scriptFullName,String methodName,Object[] args,Class<?>[] paramString){
    	Class<?> cls =null;
		Object obj  =null;
		Object[] oargs = {args};
		
		try{
			cls = Class.forName(scriptFullName);
			obj = cls.newInstance();			
			Method method = cls.getDeclaredMethod(methodName,paramString);	
			
			   method.invoke(obj, oargs);
			   
		}catch(InvocationTargetException ue){
			UnhandledException aclqaautomation = 
					new UnhandledException("ACLQA - UnhandledException: ",ue);
			((InitializeTerminateHelper)this).onUnhandledException(aclqaautomation);
//			String ueMethod = "onUnhandledException";
//		    method = cls.getDeclaredMethod(ueMethod,ue.getClass());
//		    method.invoke(obj,(Throwable)aclqaautomation);
		}catch(Exception e){			
			LoggerHelper.logTAFWarning("Failed to invok method '"
		         +methodName+"' from instance of '"+scriptFullName+"'");
			e.printStackTrace();
		}
		return obj;
	}
    
    protected String dpString(String fieldName){
    	String value = "";
    	int index = dph.indexOf(fieldName);
    	
    	if(index<0){
    		return value;
    	}
    	try{
    		value = dpw.getCell(index).getStringCellValue();
    	}catch(Exception e){
    		
    	}
    	return value;
    	
    }
    
    protected static void sleep(double seconds){    	
    	long sleepTime = (long) (seconds*1000);
    	try{
    		Thread.sleep(sleepTime);
    	}catch(Exception e){
    		
    	}
    }
    
	
	class UnhandledException extends Exception {
		public UnhandledException(String message) {
	        super(message);
	    }

	    public UnhandledException(String message, Throwable throwable) {
	        super(message, throwable);
	    }
	}
	public boolean isMainScript() {
		return mainScript;
	}
}
