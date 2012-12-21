package lib.acl.util;

import lib.acl.helper.sup.InitializeTerminateHelper;
import lib.acl.helper.sup.LoggerHelper;
import lib.acl.helper.sup.TAFLogger;

import info.bliki.wiki.model.*;
/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  April 28, 2012
 */
public abstract class FormatHtmlReport
{
	//TODO Insert shared functionality here
	
	public static String getHttpReportFromWiki(String wikiPage){
		String outFile = FileUtil.getAbsDir(TAFLogger.testResultHTML);
		return getHttpReportFromWiki(wikiPage,outFile);
	}
	public static String getHttpReportFromWiki(String wikiPage,String title){
		String outFile = FileUtil.getAbsDir(TAFLogger.testResultHTML);
	              return getHttpReportFromWiki(wikiPage, outFile, title,"");
	}
	public static String getHttpReportFromWiki(String wikiPage,String outFile, String title,String subject){
		    String ACLWiki = "http://godzilla.dev.acl.com/wiki/index.php?title=";
		    wikiPage = sanitize(wikiPage);
		    
		    WikiModel wikiModel = 
            new WikiModel(ACLWiki+"${image}", 
                          ACLWiki+"${title}");
		    
           String htmlStr = wikiModel.render(wikiPage);
           htmlStr = addReportHeader(htmlStr,title,subject);
           
           FileUtil.writeFileContents(outFile, htmlStr);
           //System.out.print(htmlStr);
           
		return outFile;
	}
	public static String addReportHeader(String body,String title,String subject){
		int headerIndex = body.indexOf("<h3>");
		String subjectC = subject;
		if(headerIndex>0)
           body = body.substring(headerIndex); //Replace Contents
		
        if(!subject.equals("")){
        	subjectC = "<tr style = \"color:#0B0B3B\"><td><div id=\"tocsubject\"><h5>\t\t - <u>"+subject+"</u></h5></div></td></tr>";
        }
        
        String header = "<table id=\"toc\" class=\"toc\" summary=\"AutoRunReport\">"+
                         "<tr style = \"color:#0B0B61\"><td><div id=\"toctitle\"><h2>"+title+"</h2></div>"+
                         "</td></tr>" +
                         subjectC+
                         "</table>" +
                         "";
        body = header+body;
        return body;
	}
	public static String sanitize(String input){
		String removePattern ="\\[\\[QA Test Automation\\|Back To Main - QA Test Automation\\]\\]"+
		   "|.*of Wiki Report.*";	
		String replaceWord = "(\\[NEW ISSUE\\?\\]|New\\sIssues\\?)";
		String htmlHints = "<div title=\""+LoggerHelper.getNewIssueTips()+"\">";
		input = input.replaceAll(removePattern,"");
		
		//input = input.replaceAll(replaceWord,htmlHints+"$1"+"</div>");
		return input;
	}
	public static void main(String[] args){
		String wikiPage = "C:/Documents and Settings/steven_xiang/Desktop/wikiexample.txt";
		String htmlPage = "C:/Documents and Settings/steven_xiang/Desktop/wikipageTest/wikiPage.html";
        String outPage = FileUtil.readFile(wikiPage);
		//getHttpReportFromWiki(outPage,InitializeTerminateHelper.processFileLink(wikiPage));
		//getHttpReportFromWiki(outPage,wikiPage);
	}
}
