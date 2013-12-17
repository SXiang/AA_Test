/**
 * 
 */
package anr.apppage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import ax.lib.frontend.FrontendCommonHelper;

/**
 * Script Name   : <b>WebPage.java</b>
 * Generated     : <b>12:55:21 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Dec 16, 2013
 * @author steven_xiang
 * 
 */
public class WebPage extends CommonWebHelper{

	
	public void click(WebElement node){
		click(driver,node);
	}
	
	public void click(WebDriver driver,WebElement node){
		click(driver,node,"");
	}
	public void click(WebElement node,String label){
		click(driver,node,label);
	}
	public void click(WebDriver driver,WebElement node,String label){
		click(driver,node,label,null);
	}
	
	public void click(WebElement node, String label, By untilBy){
		click(driver,node,label,null);
	}
	
	public void click(WebDriver driver,WebElement node, String label, By untilBy){
		logTAFStep("Click element "+label+"");
		node.click();
		waitUntil(driver,untilBy);
	}
	
	public void doubleClick(WebElement node){
		doubleClick(driver,node);
	}
	
	public void doubleClick(WebDriver driver,WebElement node){
		doubleClick(driver,node,"");
	}
	
	public void doubleClick(WebElement node,String label){
		doubleClick(driver,node,label);
	}
	public void doubleClick(WebDriver driver,WebElement node,String label){
		doubleClick(driver,node,label,null);
	}
	
	public void doubleClick(WebElement node, String label, By untilBy){
		doubleClick(driver,node,label,untilBy);
	}
	public void doubleClick(WebDriver driver,WebElement node, String label, By untilBy){
		Actions actionDriver = new Actions(driver);
		logTAFStep("Double click element "+label+"");
		actionDriver.doubleClick(node).perform();
		waitUntil(driver,untilBy);
	}	
	public void waitUntil(By untilBy){
		waitUntil(driver,untilBy);
	}
	
	public void waitUntil(WebDriver driver,By untilBy){
		if (untilBy==null) return;
		try{
		     new WebDriverWait(driver,30).until(ExpectedConditions.visibilityOfElementLocated(untilBy));
		}catch(Exception e){
			logTAFInfo("Warning: "+e.toString());
		}
	}
	
	public WebPage() {
		// TODO Auto-generated constructor stub
	}

}
