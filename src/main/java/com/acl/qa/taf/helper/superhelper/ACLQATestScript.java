package com.acl.qa.taf.helper.superhelper;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;








import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.openqa.selenium.WebDriver;

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
	
	protected HSSFSheet datapool = null;
	protected Iterator dpi = null;
	protected HSSFRow dpw = null;
	protected ArrayList<String> dph = null;

	protected static int getCurrentLogFilter() {
		return currentLogFilter;
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
		   ImageIO.write(image, "jpeg", new File(FileUtil.getAbsDir(fileName)));
		 }catch(Exception e){
			 
		 }
		}

	protected Object callScript(String scriptFullName){
		return callScript(scriptFullName,null);
	}
	
	protected Object callScript(String scriptFullName,Object[] args){
		String methodName = "onInitialize";
		Class[] paramObject = new Class[] {Object[].class};		
		return callScript(scriptFullName, methodName, args, paramObject);
	}
	
	
    protected Object callScript(String scriptFullName,String methodName,Object[] args,Class[] paramString){
    	Class cls =null;
		Object obj  =null;
		try{
			cls = Class.forName(scriptFullName);
			obj = cls.newInstance();			
			Method method = cls.getDeclaredMethod(methodName,paramString);			
			method.invoke(obj, args);
		}catch(Exception e){
			
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
	public boolean isMainScript() {
		return mainScript;
	}
}
