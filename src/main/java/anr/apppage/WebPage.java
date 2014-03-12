/**
 * 
 */
package anr.apppage;

import java.awt.AWTException;
import java.awt.Robot;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.*;

import ax.lib.frontend.FrontendCommonHelper;
import ax.lib.restapi.db.SQLQuery;

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
	 protected  WebDriver pageDriver;
	 protected WebDriverWait pageDriverWait;
     static protected Map<String,String> axItems;
	 
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
		click(pageDriver,node,label,untilBy,displayed);
	}	
	public void click(WebDriver driver,WebElement node, String label, Object untilBy){
		click(driver,node,label,untilBy,true);
	}
	public void click(WebDriver driver,WebElement node, String label, Object untilBy,boolean displayed){
		try{
		  node.click();		
		   if(!label.equals(""))
		      logTAFStep("Click element "+label+"");
		   waitUntil(driver,untilBy,displayed);
		}catch(Exception e){
			logTAFInfo("Warning: "+e.toString());
		}
	}
	
	public void toggleElementByClick(WebElement expectedElement, WebElement node, String label,boolean expectedStatus){
		
		boolean currentStatus = false;
		
		try{
				currentStatus = expectedElement.isDisplayed();
		}catch(Exception e){
			
		}
		
		if(expectedStatus==currentStatus) return;
		
        click(pageDriver,node,label,expectedElement,expectedStatus);
	}
	public void toggleElementByClick(List<WebElement> expectedElements, WebElement node, String label,boolean expectedStatus){
		
		boolean currentStatus = false;	
		if(expectedElements.size()>0){
		    try{
				currentStatus = expectedElements.get(0).isDisplayed();
		    }catch(Exception e){			
		  }
		}
		if(expectedStatus==currentStatus) return;
		
        click(pageDriver,node,label,expectedElements,expectedStatus);
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
		doubleClick(pageDriver,node,label,untilBy,displayed);
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
		toggleItem(pageDriver,box,on,label,type);
	}	
	public void toggleItem(WebDriver driver,WebElement box,boolean on, String label,String type){
		toggleItem(driver,box,on,label,type,null);
	}
	
	public void toggleItem(WebDriver driver,WebElement box,boolean on, String label, String type, Object untilBy){
		if(!type.equalsIgnoreCase("Verify")){
			logTAFStep("Toggle item '"+label+"' to be '"+(on?"Checked":"Unchecked")+"'");
		     box.click();
		     waitUntil(driver,untilBy);
		}
		  
		// Following need to be enhanced in order to check the status ... Steven
//		if(box.isSelected()==on){
//			logTAFInfo("Item '"+label+"' is "+(on?"selected":"unSelected")+" ");
//		}else{
//			if(type.equalsIgnoreCase("Verify")){
//			logTAFError("Item '"+label +"' is not in the correct state as expected - "+(on?"selected":"unSelected")+"? ");
//			}else{
//			  logTAFStep("Click '"+label+"' to "+(on?"select":"unSelecte ")+ " the item");
//			  box.click();
//			  waitUntil(driver,untilBy);
//			}
//		}
	}
	
	// *** Wait until methods ***
	public void waitUntil(Object untilBy){
		waitUntil(untilBy,true);
	}
	public void waitUntil(Object untilBy,boolean displayed){
		waitUntil(pageDriver,untilBy,displayed);
	}
	public void waitUntil(WebDriver driver, Object untilBy){
		waitUntil(driver,untilBy,true);
	}
	@SuppressWarnings("unchecked")
	public void waitUntil(WebDriver driver,final Object untilBy,boolean displayed){
		if (untilBy==null||driver==null) return;
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		waitUntil(wait,untilBy,displayed);
	}
	
	public void waitUntil(WebDriverWait wait,final Object untilBy,boolean displayed){
		try{
			if(untilBy instanceof WebElement){
				if(displayed){
		           //wait.until(ExpectedConditions.elementToBeClickable((WebElement)untilBy));
		           //wait.until(ExpectedConditions.visibilityOf((WebElement)untilBy));
				   wait.until( new ExpectedCondition<Boolean>(){
					   public Boolean apply(WebDriver driver){
						   return ((WebElement)untilBy).isDisplayed();
					   }
				   }
				   );
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
		    	   //wait.until(ExpectedConditions.visibilityOfElementLocated((By)untilBy));
		    	   wait.until(ExpectedConditions.elementToBeClickable((By)untilBy));
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
			//logTAFInfo("Warning: "+e.toString());
		}
	}

	public  void inputChars(WebElement we, String text, String type){
		inputChars(null,we,text,type);
	}
	public  void inputChars(WebDriver driver, WebElement we, String text,String type){
			text = text.trim();
			//scrollToElement(we);
			if(type.equalsIgnoreCase("Verify")){
				String curValue = we.getAttribute("value");
				String curText =  we.getText();
				if(curValue.equalsIgnoreCase(text)){
					logTAFInfo("Text value '"+curValue+"' is correct as expected '"+text+"'");
				}else if(curText.equalsIgnoreCase(text)){
					logTAFInfo("Text value '"+curText+"' is correct as expected '"+text+"'");
				}else{
					logTAFError("Text value '"+curText+"' is incorrect? - expected '"+text+"'");
				}
				
				return;
			}
			logTAFStep("Input value '"+text+"'");
			super.inputChars(driver,we,text);
		}
	
	public void  mouseMove(int x, int y){
		try {
		   new Robot().mouseMove(x, y);
		   logTAFInfo("Move mouse to point("+x+","+y+")");
      	} catch (AWTException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	   }
	}
	// ******* Other methods ****************
	public java.awt.Point scrollToElement(WebElement element){	
		return scrollToElement(element,new java.awt.Point(0,50));
	}
    public java.awt.Point scrollToElement(WebElement element, java.awt.Point pt){
    	Point elementPoint = new Point(0,0);

    	try{
    	    Coordinates coor = ((Locatable)element).getCoordinates();
    	    elementPoint = coor.inViewPort();
    	    
    	    
    	    pt.x = elementPoint.x - pt.x;
    	    pt.y = elementPoint.y - pt.y;
    	    
    	    ((JavascriptExecutor) pageDriver).executeScript(
    	    		"scrollBy("+pt.x+","+pt.y+");");
    	    logTAFInfo("ScrollBy("+pt.x+","+pt.y+");");
//    	    if(pt.y>0&&elementPoint.y>pt.y){
    	    	
//    	       ((JavascriptExecutor) pageDriver).executeScript("scrollBy("+pt.x+","+pt.y+");");
    	       //((JavascriptExecutor) pageDriver).executeScript("scroll(0,250);");
//    	    }
    	}catch(Exception e){
    		logTAFInfo("Warning: faile to scroll to element ?'"+element.getText()+"'");
    	}
    	
    	return pt;
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
    public static String getCurrentUUID(WebDriver driver){
    	return getCurrentUUID(driver,"");
    }
    
    public static String getCurrentUUID(WebDriver driver,String childItemName){
    	//sleep(0);
    	String[] ids = getCurrentUUIDs(driver,childItemName);
//    	if(ids.length>1)
//    	  return ids[ids.length-2];
    	return ids[ids.length-1];
    }

    public static String[] getCurrentUUIDs(WebDriver driver,String childItemName){
    	String pathSep = "/";
    	String url = driver.getCurrentUrl();
//    	String fileName = "";
//		try {
//			fileName = new URL(url).getPath();
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	StringBuffer sb = new StringBuffer();
    	String uuidPattern = "[a-z0-9]{8}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{12}";
    	String[] names = url.split(pathSep);
    	int currentItemIndex = 0;
    	for(int i=0;i<names.length;i++){
    		if(names[i].matches(uuidPattern)){
    			sb.append(names[i]+pathSep);
    			currentItemIndex = i;
    		}
    	}
    	
		if(axItems==null){
			axItems = new HashMap<String,String>();
		}
		if(currentItemIndex>0){
		    axItems.put(names[currentItemIndex-1], names[currentItemIndex]);
			if(!childItemName.equals("")){
				if(names[currentItemIndex-1].equalsIgnoreCase("analysisApps")){//tests->analysisApps
				//if(names[names.length-1].equalsIgnoreCase("results")){//tests->results
                   axItems.put("analytic", childItemName);

				}
			}
		}
		

    	return sb.toString().split(pathSep);
    }
    
    //*** L10N related
    
    public String getLocKey(WebElement we, String value){
    	String l10nKey = "key";
    	String l10nDirective = "ng-bind-html-unsafe";
    	String l10nDirectiveValue = l10nKey+"\\|localize";
    	
    	String l10nKeyValue = we.getAttribute(l10nKey);
    	String l10nDirectiveInput = we.getAttribute(l10nDirective);
    	
    	if(l10nKeyValue==null){
    		//logTAFWarning("Text '"+value+"' has not been localized?");
    		l10nKeyValue = value;
    	}else if(l10nDirectiveInput==null||!l10nDirectiveInput.matches(l10nDirectiveValue)){
    		logTAFWarning (l10nDirectiveInput+"is a valid value of "+l10nDirective);
    		
    	}else{
    		logTAFDebug("The L10N key for '"+value+"' is '"+l10nKeyValue);
    	}
    	
    	return l10nKeyValue;
    }

    //Static workaround - 
	 public static String axNameHandle(String itemName){
		 return axNameHandle(null,itemName);
	 }
	 
    public static String axNameHandle(WebDriver driver,String itemName){
    	String ori = "_";
    	String rep = " ";
    	
    	retriveUUID(driver, itemName);
	    		 
    	String uiName = itemName.replaceAll("(?i)\\.aCL$", "");
    	// AX issue, replaced all '_' with ' '
    	// disable this line when fixed
    	//uiName= uiName.replaceAll(ori, rep);
    	return uiName;
    }
    
    public static void retriveUUID(WebDriver driver, String itemName){
    	if(driver==null)
    		return;
    	 getCurrentUUID(driver,itemName);
    }
    
	public WebPage() {
		// TODO Auto-generated constructor stub

	}

}
