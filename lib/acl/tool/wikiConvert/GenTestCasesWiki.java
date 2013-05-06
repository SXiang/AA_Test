
package lib.acl.tool.wikiConvert;

import resources.lib.acl.tool.wikiConvert.GenTestCasesWikiHelper;
import java.io.*;
import java.util.*;
import lib.acl.util.UnicodeUtil;
import conf.beans.FrameworkConf;

public class GenTestCasesWiki extends GenTestCasesWikiHelper
{
	private static int logLvl = 2;	// 1: info; 2: debug


	private static String KEYWORD_PAGE = "";
	private static String HEADER_OF_CSV = "Keyword[][STRING]";
	private static  String TESTCASE_DIR = "/KeywordTable/SmokeTest";

    private static String project="GatewayPro";
    private static String backToMain = "[[QA Test Automation|Back To Main - QA Test Automation]]";
    private static String thisHeader = "";
    private static String wikiDir = "/doc/WikiPages";
    
    private static int border = 1,tbwidth = 1200;
    private static String borderColor="#ffffff",borderStyle="solid",borderWidth="1px",
                           backgroundColor="#ffffff";
    private static String tableStyle="",headerStyle="",doneStyle="",
                          inProgressStyle="",planningStyle="",tbdStyle="",assignedStyle="",defaultStyle="";
    private static String rowStyle=headerStyle;
    private static String tableOt = "<TABLE>\n";
    private static String tableCt = "</TABLE>\n";
    private static String projectFolder="";

    private static boolean rowColor = false;

	public void testMain(Object[] args) 
	{
		String projectPrefix = "AX_";
//		TESTCASE_DIR = "/KeywordTable/RegressionTest";
//		TESTCASE_DIR = "/DATA/KeywordTable/Tools";
		TESTCASE_DIR = "/DATA/KeywordTable/SmokeTest/DDW";
//		project = "GatewayPro";		
//		project = "Exception";
//		project = "GatewayPro";
//    	project = "Soundwave";
		projectPrefix = "ACL_";
		project = "Desktop";
        rowColor = true;
  
        tableStyle = "border-color:"+ borderColor+";"+
                            " border-style:"+borderStyle+";"+
                            "border-width:"+borderWidth+";"+
                            "background-color:"+backgroundColor;
        headerStyle = "style = \"color:#ffffff; background-color:#330033\"";
        doneStyle = "style = \"background-color:#339900\"";
        inProgressStyle = "style = \"background-color:#99cc33\"";//666600\"";
        planningStyle = "style = \"background-color:#00ffcc\"";
        tbdStyle = "style = \"background-color:#909090\"";
        assignedStyle = "style = \"background-color:#669966\"";
        defaultStyle ="style = \"background-color:#ffffff\"";
        
        
//        tableOt = "<TABLE width = "+tbwidth+" border = "+border+" style=\""+tableStyle+"\">\n";
        tableOt = "<TABLE border = "+border+" style=\""+tableStyle+"\">\n";
        
		KEYWORD_PAGE = project+" Keyword Definition";
		//KEYWORD_PAGE = "QA Automation "+project+" Keywords Manual";
		//wikiDir = wikiDir+ "/"+projectPrefix+project+"/TestCase/"+TESTCASE_DIR.replaceAll("/KeywordTable", "");
		
		TESTCASE_DIR = "/"+projectPrefix+project+TESTCASE_DIR;
		wikiDir = TESTCASE_DIR;
		
		
		String testcaseFolderFullPath = System.getProperty("user.dir") + TESTCASE_DIR;
		StringBuffer indexPage = new StringBuffer(2048);
		String curFile;
		
		indexPage.append(backToMain+"\n\n");	
		indexPage.append("== "+project +" automation Test Cases ==\n");
		indexPage.append("<br />\n----\n");
		
		for (String curTestCaseFile : new File(testcaseFolderFullPath).list(new FileFilter("csv"))) {
			curFile = curTestCaseFile.split("\\.")[0];
			thisHeader =  curFile;
			if (! curFile.startsWith("_")) { // ignore internal scripts
				logInfo("Working on testcase file  [" + curFile + ".csv] ...");
				StringBuffer contentsBuffer = new StringBuffer(2048);
				indexPage.append("*[["+curFile+"]]\n");
				contentsBuffer.append(backToMain+"\n\n");	// for wiki
				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append("=== "+project+" - "+curFile+" ===\n\n");	// for wiki
				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append(tableOt);	// for wiki format
				contentsBuffer.append(getTestCaseInfo(testcaseFolderFullPath + "/" + curFile+".csv"));
				contentsBuffer.append(tableCt);	// for wiki format

				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append(backToMain+"\n\n");	// for wiki
				logInfo("\n=== Write test case doc to file [" + curFile + ".txt] ...");
				writeToNewFile(System.getProperty("user.dir")+wikiDir+"/"+curFile + ".txt", contentsBuffer);
			}
			//break;
		}
		//Handle xls 
		for (String curTestCaseFile : new File(testcaseFolderFullPath).list(new FileFilter("xls"))) {
			logInfo("File found: "+testcaseFolderFullPath + "/"+curTestCaseFile);
			curFile = new File(curTestCaseFile).getName().split("\\.")[0];
		
			thisHeader =  curFile;
			if (! curFile.startsWith("_")) { // ignore internal scripts
				logInfo("Working on testcase file  [" + curFile + ".xls] ...");
				StringBuffer contentsBuffer = new StringBuffer(2048);
				indexPage.append("*[["+curFile+"]]\n");
				contentsBuffer.append(backToMain+"\n\n");	// for wiki
				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append("=== "+project+" - "+curFile+" ===\n\n");	// for wiki
				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append(tableOt);	// for wiki format
				contentsBuffer.append(getTestCaseInfo(UnicodeUtil.XlsToCsv(testcaseFolderFullPath + "/"+curTestCaseFile, FrameworkConf.tempCsvFile)));
				contentsBuffer.append(tableCt);	// for wiki format

				contentsBuffer.append("<br />\n");//-----------------------------------------------------\n");
				contentsBuffer.append(backToMain+"\n\n");	// for wiki
		
				logInfo("\n=== Write test case doc to file [" + curFile + ".txt] ...");
				writeToNewFile(System.getProperty("user.dir")+wikiDir+"/"+curFile + ".txt", contentsBuffer);
			}
			//break;
		}
		indexPage.append("<br />\n----\n");
		indexPage.append(backToMain+"\n\n");	
		writeToNewFile(System.getProperty("user.dir")+wikiDir+"/"+project +" automation Test Cases" + ".txt", indexPage);
			
		


	}

	private static String getTestCaseInfo(String fileName) {
		StringBuffer contentsBuffer = new StringBuffer();

		/////////////////////////////////////////////////////////////////////////////////////////////
		
		try {
			//FileReader freader = new FileReader(new File(fileName));
			InputStreamReader freader = new InputStreamReader(new FileInputStream(fileName),"UTF-8");
			LineNumberReader lnreader = new LineNumberReader(freader);
			boolean isHeader = false;
			String[] header = null;
			String[] values;
			String comm = "";
			String value = "";
			int lineCount = 0,columns=0, keyIndex=0;
			String line;
			

			while ((line = lnreader.readLine()) != null) {
				line = line.trim();
				line.replace(",", " , ");
				lineCount++;
				
				rowStyle = getRowStyle(line);
				if(isHeader){
					// for contents
					contentsBuffer.append("\t<TR "+rowStyle+">\n");	
					for(int i=0; i<columns; i++){
						values = line.split(",");
						try{
						     value = values[i].trim();
						}catch(Exception e){
							value ="";
							//logInfo(e.toString());
						}
						comm = "  <!-- "+header[i].trim()+" -->";
						if(value.equals("")){
							comm = "";
						}
						if(i==keyIndex){
							contentsBuffer.append("\t\t<TD>[["+KEYWORD_PAGE+"#"+values[i].trim()+
									"|"+value+									
									"]]</TD>\n");  
						}else{
						  contentsBuffer.append("\t\t<TD>"+value+
								comm+"</TD>\n");  
						}
					}
					contentsBuffer.append("\t</TR>\n");	
				}else if (line.contains(HEADER_OF_CSV)) {
					// for header
					isHeader = true;
					line = line.replaceAll("\\[\\]\\[STRING\\]", "");
					header = line.split(",");
		            contentsBuffer.append("\t<TR "+headerStyle+">\n");
					for(int i = 0; i< header.length
					            &&!header[i].trim().startsWith("Recent_Test")
					            &&!header[i].trim().equals(""); i++){
						
						if(header[i].trim().startsWith("Keyword")){
							keyIndex = i;
						}
						columns++;
                        contentsBuffer.append("\t\t<TD><b>"+header[i].trim()+"</b></TD>\n");  
					}
		            contentsBuffer.append("\t</TR>\n");
				
				}
			}
		}catch(Exception e){
				System.out.println("Error: "+e.toString());
			}
		return contentsBuffer.toString().replaceAll(UnicodeUtil.csvComma,",");
	}



	private static void writeToNewFile(String fileName, StringBuffer newContentsBuffer) {
		try {
//			FileWriter txt;
//			PrintWriter out;
//			txt = new FileWriter(new File(fileName));
//			txt = new FileWriter(fileName);
//			out = new PrintWriter(txt);
            FileOutputStream fis =  new FileOutputStream(fileName);
            OutputStreamWriter fisw = new OutputStreamWriter(fis,"UTF-8");
            PrintWriter out = new PrintWriter(fisw);
            
			out.print(newContentsBuffer);

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void logInfo(String message) {
		if (logLvl >= 1) {
			System.out.println(message);
		}
	}

    private static String getRowStyle(String line){
    	String rowStyle = tbdStyle	;
    	if(!rowColor)
    		return "";
//    	if(line.contains(",Assigned")){
//    		rowStyle = assignedStyle;
//    	}else if (line.contains(",TBD")){
//    		rowStyle = tbdStyle;
//    	}else if(line.contains(",Planning")){
//    		rowStyle = planningStyle;
//    	}else if(line.contains(",Completed")){
//    		rowStyle = doneStyle;
//    	}else if(line.contains(",In Progress")){
//    		rowStyle = inProgressStyle;
//    	}else {
//    		rowStyle = tbdStyle;
//    	}
//    	
    	
    	return rowStyle;
    }
}
