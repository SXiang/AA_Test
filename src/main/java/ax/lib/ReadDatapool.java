package ax.lib;

public class ReadDatapool {

	private static String dpUsername = "g1_admin";	
	private static String dpPassword = "Password00";
	private static String dpProjectName = "123456";
	
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
	
}
