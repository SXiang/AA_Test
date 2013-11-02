/**
 * 
 */
package com.acl.qa.taf.tool;

import java.awt.AWTException;
/**
 * Script Name   : <b>TAFRobot.java</b>
 * Generated     : <b>3:31:56 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Oct 18, 2013
 * @author steven_xiang
 * 
 */
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.WebElement;
public class TAFRobot extends Robot{

	/**
	 * 
	 */
	public TAFRobot() throws AWTException {
		// TODO Auto-generated constructor stub
	}
	public boolean  inputUnicodeFromKeyboard(String hexValue){	
		return inputUnicodeFromKeyboard(null,hexValue);
	}
	public boolean  inputUnicodeFromKeyboard(WebElement we, String hexValue){
		boolean done = true;
		if(we!=null){
			we.click();
		}
		keyPress(KeyEvent.VK_ALT);
        keyPress(KeyEvent.VK_ADD);		
		keyRelease(KeyEvent.VK_ADD);
//		keyPress(KeyEvent.VK_7);
//        keyRelease(KeyEvent.VK_7);	
//        keyPress(KeyEvent.VK_5);
//        keyRelease(KeyEvent.VK_5);		
//        keyPress(KeyEvent.VK_2);
//        keyRelease(KeyEvent.VK_2);		
//        keyPress(KeyEvent.VK_8);
//        keyRelease(KeyEvent.VK_8);		
		for(int i=0;i<hexValue.length();i++){
			keyPress(Character.toUpperCase(hexValue.charAt(i)));			
			keyRelease(Character.toUpperCase(hexValue.charAt(i)));
		}
		keyRelease(KeyEvent.VK_ALT);
		delay(0);
		return done;
	}
//    action.keyDown(Keys.ALT);
//    action.sendKeys(Keys.ADD,inputChar);
//    action.keyUp(Keys.ALT);
//    action.perform();
}
