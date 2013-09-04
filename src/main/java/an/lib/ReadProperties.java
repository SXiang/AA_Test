package an.lib;

public class ReadProperties {

	private static String server = "win2012-3.aclqa.local";
	private static String port = "8443";
	private static String browserType = "ie";
	private static String casType = "nonSSO";
	private static String driverPath = "C:\\Selenium\\";
	private static String nodeName = "ramneet-win7-32.acl.com";
	private static String nodePort = "5555";
	private static String executionType = "node";
	
	public static String getServer() {
		return server;
	}
	public static void setServer(String server) {
		ReadProperties.server = server;
	}
	public static String getPort() {
		return port;
	}
	public static void setPort(String port) {
		ReadProperties.port = port;
	}
	public static String getBrowserType() {
		return browserType;
	}
	public static void setBrowserType(String browserType) {
		ReadProperties.browserType = browserType;
	}
	public static String getCasType() {
		return casType;
	}
	public static void setCasType(String casType) {
		ReadProperties.casType = casType;
	}
	public static String getDriverPath() {
		return driverPath;
	}
	public static void setDriverPath(String driverPath) {
		ReadProperties.driverPath = driverPath;
	}
	public static String getNodeName() {
		return nodeName;
	}
	public static void setNodeName(String nodeName) {
		ReadProperties.nodeName = nodeName;
	}
	public static String getNodePort() {
		return nodePort;
	}
	public static void setNodePort(String nodePort) {
		ReadProperties.nodePort = nodePort;
	}
	public static String getExecutionType() {
		return executionType;
	}
	public static void setExecutionType(String executionType) {
		ReadProperties.executionType = executionType;
	}
	
	
}
