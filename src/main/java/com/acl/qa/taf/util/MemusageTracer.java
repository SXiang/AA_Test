package com.acl.qa.taf.util;

import java.util.*;
import java.io.*;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;
public class MemusageTracer extends Thread {
	int intervalSeconds = 1*5;
	int numCheck = 0;
	int curMemusage = 0;
	int numKeyword =0;
	
	String csvPath = "C:\\Documents and Settings\\steven_xiang\\Desktop\\memusage_report.csv";
	//String csvPath = "";
	String imageName = "wmiprvse.exe";
	String pid = "";
	String command ="cmd.exe,/c,tasklist,/fo,table,/nh,/fi";
	String reportHeader = "Num Check,No.,Keyword,Running Time(S),Image Name,PID,Session Name, Session#,Mem Usage(K),NumTestObjects,Check Time";
	String currentRecord = "";
	String checkTime="";
	String keyword = "";
	String curKeyword = "";
	
	String pidFilter = "";
	String nameFilter = "";
	FileWriter out;
    public boolean ready = false;
	
	public MemusageTracer(){
       // setCommand();
	}
	
	public void setup(String keyword,String imageName, String pid, String csvPath, int intervalSeconds){
		
		this.imageName = imageName.equals("")?this.imageName:imageName;
		
		this.pid = pid;
		if(keyword.equals(curKeyword)){
			this.keyword = "";
		}else{
			this.keyword = keyword;
			curKeyword = keyword;
			numKeyword++;
		}
		if(!ready){
		  this.csvPath = csvPath;
		  this.intervalSeconds = intervalSeconds;
		}
		this.command ="cmd.exe,/c,tasklist,/fo,csv,/nh,/fi";
		setCommand();
		ready = true;
		
	}
	
	public void stopTracing(){
		ready = false;
	}
	
	public void setCommand(){
	
		String filter ="";
		if(pid!=null&&pid.matches("^\\d+$")){
			pidFilter = "pid eq "+pid;
			filter +=  pidFilter;
		}else if(imageName!=null&&!imageName.equals("")){
			nameFilter = " imageName eq "+imageName;
			filter +=  nameFilter;
		}
		
		command +=  ",\""+filter.trim()+"\"";
		//System.out.println("Memusage in file : '"+csvPath+"'");
		if(!ready){
			try {
				out = new FileWriter(FileUtil.getAbsDir(csvPath,""), true);
			} catch (IOException e) {
			e.printStackTrace();
			} 
		}
	}
	
	public void run(){
		  startTracer();
	}


    public void startTracer(){       
    	long curSecond = 0,sleepTime=0;
    	while(!ready){
    		try {
				sleep(5*1000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
    	}
    	curSecond = Calendar.getInstance().getTimeInMillis()/1000;
    	while(ready){
    		sleepTime=0;
    		numCheck++;
    		checkTime = Calendar.getInstance().getTime().toString();    		
    		try {
    			if(numCheck==1){
    				FileUtil.appendStringToFile(out,reportHeader );
    			}
    			setCurrentRecord();
    			if(!getCurrentRecord().equals(""))
    			    FileUtil.appendStringToFile(out,currentRecord );
        		curSecond += intervalSeconds;
        		while(Calendar.getInstance().getTimeInMillis()/1000<curSecond&&
        				sleepTime<intervalSeconds){
        			sleepTime +=1;
				    Thread.sleep(1000);
        		}
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}catch(IOException e){
				//e.printStackTrace();
			}catch(Exception e){
				//e.printStackTrace();
			}
    	}
    	
    	try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void setCurrentRecord()throws IOException{
    	ProcessBuilder processBuilder =new ProcessBuilder(command.split(","));
//    	System.err.println("\t\t Debug Info: Used command to get memusage : '"+command+"'");
       	Process process = processBuilder.start();
       	
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = "";
        String temp ="";
		int numLine =2;

		//System.out.printf("Output of running %s is:", processBuilder.command());
        for(int i=0;i<numLine;i++){
        	     
                if((line = br.readLine())==null){
                   line = "";
                   break;
        	    }else if(line.contains("\""+pid+"\"")){
        	    	//System.out.println(line);
        	    	break;
        	    }
        }
       

    	if(!line.equals("")){
            temp = line.replaceAll("^.+,\"([1-9]?[0-9,]+) K\".*$", "$1").replaceAll(",","");
            try{
        		curMemusage = Integer.parseInt(temp);
        	}catch(Exception e){
        	//	e.printStackTrace();
        	}
             this.currentRecord = numCheck+","+numKeyword+","+curKeyword+","+(numCheck-1)*intervalSeconds+","+
                             line.replaceAll("\"[1-9]?[0-9,]+ K\"",temp)+
                             ","+//ACLQATestScript.getRegisteredTestObjects().length+
                             ","+checkTime;
    	}else{
    		//this.currentRecord = "";
    		this.currentRecord = numCheck+","+numKeyword+","+curKeyword+","+(numCheck-1)*intervalSeconds+","+
            ",,,,"+//getCurrentMemusage()+
            ","+//ACLQATestScript.getRegisteredTestObjects().length+
            ","+checkTime;
    	}
        if(ACLQATestScript.loggerConf.filterLevel>5)
		    // System.err.println("\t\t Debug Info: Current Task Mem usage: "+getCurrentMemusage()+" K");
             System.err.println("\t\t Debug Info: Current Memusage Record: '"+getCurrentRecord()+"'");
    	
    }
    public String getCurrentRecord(){
    	return currentRecord;
    }
    
    public int getCurrentMemusage(){
    	
    	return curMemusage;
    }
    


	public static  void main(String args[]) throws IOException {
		MemusageTracer mt = new MemusageTracer();	
		mt.setCommand();
		mt.start();		
		try {
			mt.ready = true;
			mt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
 	}

 }