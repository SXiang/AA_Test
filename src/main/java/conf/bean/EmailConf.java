/**
 * 
 */
package conf.bean;

/**
 * Script Name   : <b>EmailConf.java</b>
 * Generated     : <b>2:16:38 AM</b> 
 * Description   : <b>ACL Test Automation</b>
 * 
 * @since  Aug 24, 2013
 * @author steven_xiang
 * 
 */
public class EmailConf {

	/**
	 * 
	 */
	public String subject = "Automation Test Report";
	
	public String smtpServer = "xchg-cas-array.acl.com";
	public String fromAddress = "QAMail@ACL.COM";
	public String fromName = "";
	public String toAddress = "QAMail@ACL.COM";
	public String attachFiles = "";//testResultHTML_Server;
	public String ccAddress = "";
	public String bccAddress = "";
	public String importance = "Normal";
	public String userName = "ACL\\QAMail";
	public String password = "Password00";
	public String ipPort = "25";
	public String ssl = "1";
	public boolean emailReport;
	
	
	
	public void setEmailReport(boolean emailReport) {
		this.emailReport = emailReport;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}


	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}


	public void setFromName(String fromName) {
		this.fromName = fromName;
	}


	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}


	public void setAttachFiles(String attachFiles) {
		this.attachFiles = attachFiles;
	}


	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}


	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}


	public void setImportance(String importance) {
		this.importance = importance;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setIpPort(String ipPort) {
		this.ipPort = ipPort;
	}


	public void setSsl(String ssl) {
		this.ssl = ssl;
	}


	public EmailConf() {
		// TODO Auto-generated constructor stub
	}

}
