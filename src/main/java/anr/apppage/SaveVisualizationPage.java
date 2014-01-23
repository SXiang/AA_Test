/**
 * 
 */
package anr.apppage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ax.lib.restapi.db.SQLQuery;

/**
 * Script Name   : <b>SaveVisualizationPage.java</b>
 * Generated     : <b>11:06:19 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Jan 21, 2014
 * @author steven_xiang
 * 
 */
public class SaveVisualizationPage extends WebPage {
 
    // From page header
	@FindBy(css="div.page-header > div.page-dropdown-options > div.dropdown > a.dropdown-toggle")
    private WebElement dropdownSaveIcon;
			
    @FindBy(css="div.page-header > div.page-dropdown-options > div.dropdown > ul.dropdown-menu > li > a > span[key='_MainMenu.SaveViz.Label_']")
    private WebElement saveBtn;
    
    @FindBy(css="div.alert > button.close")
    private WebElement closeAlertIcon;
    
    @FindBy(css="div.alert > span")
    private WebElement alertMsg;
    
    //Save Viz
    @FindBy(css="div.modal.fade.in > div.ng-scope > div.modal.saveViz")
    private List<WebElement> saveViz;
    
    @FindBy(css="div.modal-header > div.modal-title > span[key='_AveViz.Modal.Title_']")
    private WebElement saveVizTitle;
    
    @FindBy(css="div.modal-header > div.icon-remove")
    private WebElement saveVizRemoveIcon;
    
    @FindBy(css="div.modal-body > form > input[ng-model='savedViz.title']")
    private WebElement saveVizInputBox;
    
    @FindBy(css="div.modal-body > form > button.action-btn-saveViz")
    private WebElement saveVizSaveBtn;
    
    @FindBy(css="div.modal-body > form > div.acl-checkbox.link-latest-table > i.icon-check-empty")
    private WebElement saveVizLinkCheckbox;
    
    
			//***************  Part 3  *******************
			// ******* Methods           ****************
			// *******************************************

    public boolean saveVisualization(String saveTo){
    	String[] sto = saveTo.split("\\|");
    	String vTitle = sto[0];
    	boolean link = false;
    	if(sto.length>1){
    		if(sto[1].equalsIgnoreCase("true")||
    				sto[1].equalsIgnoreCase("yes")){
    			link = true;
    		}
    	}
    	return saveVisualization(vTitle,link);
    	
    }
    public boolean saveVisualization(String vTitle, boolean link){
    	return saveVisualization(vTitle,link, true);
    	
    }
    public boolean saveVisualization(String vTitle, boolean link, boolean save){
    	if(vTitle==null||vTitle.equalsIgnoreCase("")){
    		return true;
    	}
    	
    	boolean saved = false;
    	
		if(expectedErr.equals("")){
			      deleteViz(vTitle);
		}
		String alertMessage = vTitle+" did not save successfully.";
    	String expectedMsg = vTitle+"\\s+saved.\\s*";
    	
    	toggleElementByClick(saveViz, dropdownSaveIcon, "Dropdown Menu", true);
    	toggleElementByClick(saveBtn,saveBtn,"Save..",false);
    	inputChars(saveVizInputBox, vTitle);
    	if(link)
    	   toggleItem(saveVizLinkCheckbox, link, "Link to most recent table");
    	if(save){
    		click(saveVizSaveBtn,"Save", alertMsg);
    		String msg = alertMsg.getText();
    		if(msg.matches(expectedMsg)){
    			logTAFInfo(msg);
    			saved = true;
    		}else{
    			logTAFError(msg);
    		}
    		click(closeAlertIcon,"Close(x)");
    	}else{
    		click(saveVizRemoveIcon,"Remove(x)");
    	}
    	return saved;
    }
    public boolean loadVisualization(String vTitle){
    	boolean found = false;
    	//TBD
    	return found;
    }
    
    public boolean deleteViz(String vTitle){
	     //String tableUUID = getCurrentUUID(pageDriver);
	 
	     //if (tableUUID.equals(""))
	     //	 return false;
	     
	     String id = getVizID(vTitle);
	     
	     if(id.equals("")){
	    	 return false;
	     }
	     int numUpdated = updateDB(SQLQuery.deleteAuditItem(id));
	     
	     if(numUpdated<=0){
	    	 //logTAFInfo(" Can't find saved Viz '"+vTitle+": "+id+"'");
	    	 return false;
	     }else{
	    	 logTAFInfo("Delete '"+vTitle+": "+id+"' from database successfully");
	     }
	     
	     return true;
  }
  
  public String getVizID(String vTitle){
	String id = "";
	String testsId, anName;
	
	try{
	  testsId = axItems.get("tests");
	  anName = axItems.get("analytic");
	}catch(Exception e){
		return "";
	}
	
	String sql = SQLQuery.getVizID(testsId,anName, vTitle);
	
	ResultSet rs = queryDB(sql);
	try {
		rs.next();
		id = rs.getString("id");
		logTAFInfo("Table data uuid is retrieved successfully '"+id+"'");
	} catch (SQLException e) {
		logTAFInfo("Warning Cannot find the table data uuid - Please check your data. '"+sql+"'");
		
	}
	 
  return id;
  }
	public SaveVisualizationPage(WebDriver driver) {
	    pageDriver = driver; 
	    pageDriverWait = new WebDriverWait(driver,30);
	}

}
