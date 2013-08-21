package com.acl.qa.taf.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import info.bliki.wiki.model.WikiModel;

import com.acl.qa.taf.helper.superhelper.ACLQATestScript;
import com.acl.qa.taf.helper.superhelper.LoggerHelper;
import com.acl.qa.taf.helper.superhelper.TAFLogger;

/**
 * Description   : Super class for script helper
 * 
 * @author Steven_Xiang
 * @since  April 28, 2012
 */
public abstract class FormatHtmlReport
{
	//TODO Insert shared functionality here
	public static String txtHtmlHeader  = "<!DOCTYPE html>"+
    "<html><body><pre>";
	public static String txtHemlFooter ="</pre></body></html>";
	public static String linkOpenTag ="<a target=\"_blank\""+
			"";
	public static String getHttpReportFromWiki(String wikiPage){
		String outFile = FileUtil.getAbsDir(TAFLogger.testResultHTML);
		return getHttpReportFromWiki(wikiPage,outFile);
	}
	public static String getHttpReportFromWiki(String wikiPage,String title){
		String outFile = FileUtil.getAbsDir(TAFLogger.testResultHTML);
	              return getHttpReportFromWiki(wikiPage, outFile, title,"");
	}
	
public static String getHtmlPrintable(String htmlStr){
	    if(htmlStr==null)
	    	return htmlStr;
		return getHtmlPrintable(htmlStr,htmlStr.length());
	}
public static String getHtmlPrintable(String htmlStr,int length){
	return getHtmlPrintable(htmlStr,0,length);
}
	public static String getHtmlPrintable(String htmlStr,int from,int length){
		String[] pattern ={"<",">"};
		String[] replacement ={"&lt","&gt"};
		try{
		  htmlStr = htmlStr.substring(from,from+length-1);
		  htmlStr = ACLQATestScript.stringReplaceAll(htmlStr,pattern,replacement);
		}catch(Exception e){
			LoggerHelper.logTAFException(e);
		}
		return htmlStr;
	}
	public static String getHttpReportFromWiki(String wikiPage,String outFile, String title,String subject){
		    String ACLWiki = "";//"http://godzilla.dev.acl.com/wiki/index.php?title=";
		    
		    ACLWiki = ACLQATestScript.projectConf.wikiLink;
		    wikiPage = sanitize(wikiPage);		    
		    WikiModel wikiModel = 
            new WikiModel(ACLWiki+"${image}", 
                          ACLWiki+"${title}");
		    
           String htmlStr = wikiModel.render(wikiPage);
           htmlStr = addReportHeader(htmlStr,title,subject);
           htmlStr = addReportFooter(htmlStr,"Test Report");
           FileUtil.writeFileContents(outFile, htmlStr);
           //System.out.print(htmlStr);
           
		return outFile;
	}
	public static String addReportFooter(String footer){
		return addReportFooter("</pre>",footer);
	}
	public static String addReportFooter(String htmlStr,String footer){
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd_HH.mm").format(Calendar.getInstance().getTime());
		htmlStr += "</td></tr>"+
                "<tr bgcolor=\"#CCCCFF\"><td align=\"center\">"+
				"<b><font color=\"#0000A0\">ACLQA Automation "+footer+" - "+
				timeStamp+"</font></b></td></tr>"+
                "</hr>"+
                "</table>"+
                "</body></html>";
		return htmlStr;
	}
	
	public static String addReportHeader(String title){
		return addReportHeader("<pre>","ACLQA Automation "+title,"");
	}
	public static String addReportHeader(String body,String title,String subject){
		int headerIndex = body.indexOf("<h3>");
		String subjectC = subject;
		if(headerIndex>0)
           body = body.substring(headerIndex); //Replace Contents
		
        if(!subject.equals("")){
        	subjectC = "<tr style = \"color:#0B0B3B\"><td><div id=\"tocsubject\"><h5>\t\t - <u>"+subject+"</u></h5></div></td></tr>";
        }
        
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        		+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd>"+
                          "<html xmlns=\"http‎://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">"+
        		          "<head>"+
                          "<meta http-equiv=\"Content-type\" content=\"text/html;charset=UTF-8\" />" +
//        		          "<style>"+
//                             "a:link {color:#FF0000;}    /* unvisited link */"+
//                             "a:visited {color:#00FF00;} /* visited link */"+
//                             "a:hover {color:#FF00FF;}   /* mouse over link */"+
//                             "a:active {color:#0000FF;}  /* selected link */" +
//                          "</style>"+
                          "<body bgcolor=\"#FAFAFB\">"
                          +
        		 
        		         "<table id=\"toc\" class=\"toc\" summary=\"AutoRunReport\">"+
                         "<tr style = \"color:#0B0B61\"><td><div id=\"toctitle\"><h2>"+title+"</h2></div>"+
                         "</td></tr>" +
                         subjectC+
                        
                         "<tr><td>" +
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
