/**
 * 
 */
package anr.apppage;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
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

  //*** Click element and optionally wait until ***	
	public void click(WebElement node){
		click(node,"");
	}
	
	public void click(WebDriver driver,WebElement node){
		click(driver,node,"");
	}
	public void click(WebElement node,String label){
		click(node,label,null);
	}
	public void click(WebDriver driver,WebElement node,String label){
		click(driver,node,label,null);
	}
	
	public void click(WebElement node, String label, Object untilBy){
		click(node,label,untilBy,true);
	}
	public void click(WebElement node, String label, Object untilBy, boolean displayed ){
		click(driver,node,label,untilBy,displayed);
	}	
	public void click(WebDriver driver,WebElement node, String label, Object untilBy){
		click(driver,node,label,untilBy,true);
	}
	public void click(WebDriver driver,WebElement node, String label, Object untilBy,boolean displayed){
		logTAFStep("Click element "+label+"");
		node.click();
		waitUntil(driver,untilBy,displayed);
	}
	
	public void toggleElementByClick(WebElement expectedElement, WebElement node, String label,boolean expectedStatus){
		
		boolean currentStatus = false;
		
		try{
				currentStatus = expectedElement.isDisplayed();
		}catch(Exception e){
			
		}
		
		if(expectedStatus==currentStatus) return;
		
        click(driver,node,label,expectedElement,expectedStatus);
	}
	
  //*** Double click element and optionally wait until ***
	public void doubleClick(WebElement node){
		doubleClick(node,"");
	}
	
	public void doubleClick(WebDriver driver,WebElement node){
		doubleClick(driver,node,"");
	}
	
	public void doubleClick(WebElement node,String label){
		doubleClick(node,label,null,true);
	}
	public void doubleClick(WebDriver driver,WebElement node,String label){
		doubleClick(driver,node,label,null,true);
	}
	
	public void doubleClick(WebElement node, String label, Object untilBy, boolean displayed ){
		doubleClick(driver,node,label,untilBy,displayed);
	}
	public void doubleClick(WebDriver driver,WebElement node, String label, Object untilBy,boolean displayed){
		Actions actionDriver = new Actions(driver);
		logTAFStep("Double click element "+label+"");
		actionDriver.doubleClick(node).perform();
		waitUntil(driver,untilBy,displayed);
	}	
	
	//*** Select ***
	
	public void selectItem(Select selObj, String item){
		selectItem(selObj,item,"Select");
	}
	
	public void selectItem(Select selObj,String item, String type){
		selectItem(selObj,item,type,null);
	}
	
	public void selectItem(Select selObj, String item, String type, Object untilBy){
		if(type.equalsIgnoreCase("Verify")){
			String  selectedItem = selObj.getFirstSelectedOption().getText();
			if(item.equalsIgnoreCase(selectedItem)){
				logTAFInfo("Item '"+item+"' is selected as expected");
			}else{
				logTAFError("Item '"+item+"' is not selected as expected"+
			             "Current selected item is '"+selectedItem+"'");
			}			
		}else{
			logTAFStep("Select item '"+item+"'");
			selObj.selectByVisibleText(item);
			waitUntil(untilBy);
		}
	}
	//*** Toggle radio/check boxes - select / deselect ***
	public void toggleItem(WebElement box,boolean on, String label){
		toggleItem(box,on,label,"Select");
	}
	public void toggleItem(WebElement box,boolean on, String label,String type){
		toggleItem(driver,box,on,label,type);
	}	
	public void toggleItem(WebDriver driver,WebElement box,boolean on, String label,String type){
		toggleItem(driver,box,on,label,type,null);
	}
	
	public void toggleItem(WebDriver driver,WebElement box,boolean on, String label, String type, Object untilBy){

		if(box.isSelected()==on){
			logTAFInfo("Item '"+label+"' is "+(on?"selected":"unSelected")+" ");
		}else{
			if(type.equalsIgnoreCase("Verify")){
				logTAFError("Item '"+label +"' is not in the correct state as expected - "+(on?"selected":"unSelected")+"? ");
			}else{
			  logTAFStep("Click '"+label+"' to "+(on?"select":"unSelecte ")+ " the item");
			  box.click();
			  waitUntil(driver,untilBy);
			}
		}
	}
	
	// *** Wait until methods ***
	public void waitUntil(Object untilBy){
		waitUntil(untilBy,true);
	}
	public void waitUntil(Object untilBy,boolean displayed){
		waitUntil(driver,untilBy,displayed);
	}
	public void waitUntil(WebDriver driver, Object untilBy){
		waitUntil(driver,untilBy,true);
	}
	@SuppressWarnings("unchecked")
	public void waitUntil(WebDriver driver,final Object untilBy,boolean displayed){
		if (untilBy==null||driver==null) return;
		WebDriverWait wait = new WebDriverWait(driver,30);
		
		try{
			if(untilBy instanceof WebElement){
				if(displayed){
		           wait.until(ExpectedConditions.visibilityOf((WebElement)untilBy));
				}else{
				   wait.until( new ExpectedCondition<Boolean>(){
					   public Boolean apply(WebDriver driver){
						   return !((WebElement)untilBy).isDisplayed();
					   }
				   }
				   );
						   
					//	   ExpectedConditions.not(ExpectedConditions.visibilityOf((WebElement)untilBy)));
				}
			}else if(untilBy instanceof By){
				if(displayed){
		    	   wait.until(ExpectedConditions.visibilityOfElementLocated((By)untilBy));
				}else{
				   wait.until(ExpectedConditions.invisibilityOfElementLocated((By)untilBy));
				}
		    }else if(untilBy instanceof List){
		    	if(((List<?>)untilBy).get(0) instanceof WebElement){
		    		if(displayed){
		    		   wait.until(ExpectedConditions.visibilityOfAllElements((List<WebElement>)untilBy));
		    		}else{
		    			wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfAllElements((List<WebElement>)untilBy)));
		    		}
		    	}
		    }else{
		    	logTAFInfo("Warning: invalid object type as expected condition - '"+untilBy+"'");
		    }
		}catch(Exception e){
			logTAFInfo("Warning: "+e.toString());
		}
	}

	public  void inputChars(WebElement we, String text, String type){
		inputChars(null,we,text,type);
	}
	public  void inputChars(WebDriver driver, WebElement we, String text,String type){
			text = text.trim();
			//scrollToElement(we);
			if(type.equalsIgnoreCase("Verify")){
				String curText =  we.getText();
				if(curText.equalsIgnoreCase(text)){
					logTAFInfo("Text value '"+curText+"' is correct as expected '"+text+"'");
				}else{
					logTAFError("Text value '"+curText+"' is incorrect? - expected '"+text+"'");
				}
				
				return;
			}
			super.inputChars(driver,we,text);
		}
	// ******* Other methods ****************
	
    public Point scrollToElement(WebElement element){
    	Point elementPoint = new Point(0,0);
    	try{
    	    Coordinates coor = ((Locatable)element).getCoordinates();
    	    elementPoint = coor.inViewPort();
    	}catch(Exception e){
    		logTAFInfo("Warning: faile to scroll to element ?'"+element.getText()+"'");
    	}
    	return elementPoint;
    }
    
    public int getElementIndex(List<WebElement> element,String columnName){
    	for(int i=0;i<element.size();i++){
    		if(columnName.equalsIgnoreCase("")||
    				columnName.equalsIgnoreCase(element.get(i).getText())){
    			return i;
    		}
    	}
    	return 0;
    }
	public WebPage() {
		// TODO Auto-generated constructor stub
	}

}
