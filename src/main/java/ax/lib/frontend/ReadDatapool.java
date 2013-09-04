package ax.lib.frontend;

public class ReadDatapool {

	private static String dpUsername = "g1_admin";	
	private static String dpPassword = "Password00";
	private static String dpProjectName = "123456";
	private static String dpTestSetName = "æÆôöòûùÿÖÜáíóúñÑº¿°";
	private static String dpTestName = "Analytic_1234NU v9.ACL";
	private static String[] dpSearchItems = {"123","with"};
	private static String dpAnalyticName = "An11_2";
	private static int dpJobNumber = 2;
	
	public static String getDpUsername() {
		return dpUsername;
	}
	public static void setDpUsername(String dpUsername) {
		ReadDatapool.dpUsername = dpUsername;
	}
	public static String getDpPassword() {
		return dpPassword;
	}
	public static void setDpPassword(String dpPassword) {
		ReadDatapool.dpPassword = dpPassword;
	}
	public static String getDpProjectName() {
		return dpProjectName;
	}
	public static void setDpProjectName(String dpProjectName) {
		ReadDatapool.dpProjectName = dpProjectName;
	}
	public static String getDpTestSetName() {
		return dpTestSetName;
	}
	public static String getDpTestName() {
		return dpTestName;
	}
	public static String[] getDpSearchItems() {
		return dpSearchItems;
	}
	public static String getDpAnalyticName() {
		return dpAnalyticName;
	}
	public static int getDpJobNumber() {
		return dpJobNumber;
	}
	
}
