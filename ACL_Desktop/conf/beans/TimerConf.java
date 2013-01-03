package ACL_Desktop.conf.beans;

public class TimerConf {

	public  static int 
	
	    //seconds  -
	errDialogCloseWaitTime = 2,
	itemPasteTime = 5,
	expandWaitTime = 3,
	itemClickTime = 3,
	pageRefreshTime = 3,
	dialogLoadTime = 3,
	launchWaitTime = 15,

		// - General timer
	    maxWaitTime = 30,
	    waitBetweenRetry = 5,
	    loginWaitTime = 5,
	    saveToDiskTime = 5;

	public static void setErrDialogCloseWaitTime(int errDialogCloseWaitTime) {
		TimerConf.errDialogCloseWaitTime = errDialogCloseWaitTime;
	}

	public static void setItemPasteTime(int itemPasteTime) {
		TimerConf.itemPasteTime = itemPasteTime;
	}

	public static void setExpandWaitTime(int expandWaitTime) {
		TimerConf.expandWaitTime = expandWaitTime;
	}

	public static void setItemClickTime(int itemClickTime) {
		TimerConf.itemClickTime = itemClickTime;
	}

	public static void setPageRefreshTime(int pageRefreshTime) {
		TimerConf.pageRefreshTime = pageRefreshTime;
	}

	public static void setDialogLoadTime(int dialogLoadTime) {
		TimerConf.dialogLoadTime = dialogLoadTime;
	}

	public static void setLaunchWaitTime(int launchWaitTime) {
		TimerConf.launchWaitTime = launchWaitTime;
	}

	public static void setMaxWaitTime(int maxWaitTime) {
		TimerConf.maxWaitTime = maxWaitTime;
	}

	public static void setWaitBetweenRetry(int waitBetweenRetry) {
		TimerConf.waitBetweenRetry = waitBetweenRetry;
	}

	public static void setLoginWaitTime(int loginWaitTime) {
		TimerConf.loginWaitTime = loginWaitTime;
	}

	public static void setSaveToDiskTime(int saveToDiskTime) {
		TimerConf.saveToDiskTime = saveToDiskTime;
	}
	    

}
