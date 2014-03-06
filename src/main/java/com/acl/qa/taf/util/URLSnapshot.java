/**
 * 
 */
package com.acl.qa.taf.util;


import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;

/**
 * Script Name   : <b>URLSnapshot.java</b>
 * Generated     : <b>4:10:32 PM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Mar 3, 2014
 * @author steven_xiang
 * 
 */
public class URLSnapshot extends Thread {
	int intervalSeconds = 1*5;
    public boolean ready = true;
	public HashSet<String> urlSnap;
    public static WebDriver snapDriver;
    private String lastURL = "";
   

	public void stopTracing(){
		ready = false;
	}
	
	
	@Override
	public void run(){
		  startTracer();
	}


	
    public void startTracer(){       
    	long sleepTime=1;
    	String snapPathRoot = ACLQATestScript.projectConf.localizationSnapshots;
    	String version = ACLQATestScript.projectConf.buildInfo;
    	String locale = ACLQATestScript.projectConf.locale;
    	String filePath,filePathArray[],filename;
    	urlSnap = new HashSet<String>();
    	int snaps = 0;
    	while(ready){	    		    		
    		try {
    			if(snapDriver==null)
    				continue;
                filePath = getSanpPath(snapDriver);
                
                String title = snapDriver.getTitle().replaceAll("ACL Analytics Exchange", "")
                		.replaceAll("[\\- ]", "")
                		.replaceAll("ACL","").trim();
                if(!title.equalsIgnoreCase("")
                		){
                	 title = title+"/";
                }else{
                	title = "";
                }
                //System.err.println("URL: '"+filePath+"'");
                
                if(filePath.equals(lastURL))
                	continue;
                snaps = urlSnap.size();
                urlSnap.add(filePath);
                if(urlSnap.size()==snaps)
                	continue;
                filePathArray = filePath.split("\\/");
                filename = filePathArray[2];
                            	
                String snapPath = snapPathRoot + version+"/"+locale+"/"+filePathArray[1]+"/"+title;

                for(int i=3;i<filePathArray.length;i++){
                	String fname = filePathArray[i];
                	if(fname.length()>20){
                		fname = fname.substring(0,20);
                	}
                	filename += "."+fname;
                }
                
    			if(filename!=null){    	  
    				//System.err.println("\tL10N Snapshot: '"+snapPath+filename+".jpeg"+"'");
    			    ACLQATestScript.captureScreen(snapPath+filename+".jpeg");
    			}
    			
            }catch(Exception e){
				//e.printStackTrace();
			}finally{
				ACLQATestScript.sleep(sleepTime);
			}
    	}
    	
    }
    
    public String getSanpPath(WebDriver snapDriver){
    	String path = null;
         String url = null;
         String[] pattern = {"[a-z0-9]{8}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{4}\\-[a-z0-9]{12}"};
         String[] replace = {"ItemID"};
         List<WebElement> hideElements;
         int numHideElements = 0;
         List<WebElement> totalElements;
         int numTotalElements = 0;
         List<WebElement> modalElements;
         int numModalElements = 0;
         if(snapDriver!=null){
        	 try{
        		 url = snapDriver.getCurrentUrl().replaceAll("/#/", "/");
        		 hideElements = snapDriver.findElements(By.cssSelector("div[style='display: none;']"));
        		 numHideElements = hideElements.size();
        		 modalElements = snapDriver.findElements(By.cssSelector("div.modal-title"));
        		 numModalElements = modalElements.size();
        		 totalElements = snapDriver.findElements(By.cssSelector("*"));
        		 numTotalElements = totalElements.size();
        		 path = new URL(url).getPath();
        		 
        		 for(int i=0;i<pattern.length;i++){
        			 path = path.replaceAll(pattern[i],replace[i]);
        		 }
        		 
        		 path = path+"/"+numTotalElements;
        		 if(numHideElements!=0){
        			 path = path+"/"+numHideElements;
        		 }
        		 if(numModalElements!=0){
        			 String modalID = modalElements.get(0).getAttribute("id");
        			 path = path+"/"+modalID;
        			 if(numModalElements>1)
        				 path = path+"/"+numModalElements;
        		 }
        	 }catch(Exception e){
        		// System.err.println("Problem to get URL ? '"+url+"'" +e.toString());
        	 }
         }
        	 
    	return path;
    }
    }
