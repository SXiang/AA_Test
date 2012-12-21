package conf.beans;


public class TimerConf {
	public  static int 

	//seconds

	waitBetweenKeywords = 5,
	maxPageLoadingTime = 120,
	// m seconds
	onTerminateSleep = 5000, 
	waitTimeBetweenTestCases = 10000;


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


	public static int getMaxPageLoadingTime() {
		return maxPageLoadingTime;
	}

	public static void setMaxPageLoadingTime(int maxPageLoadingTime) {
		TimerConf.maxPageLoadingTime = maxPageLoadingTime;
	}

	public static int getWaitBetweenKeywords() {
		return waitBetweenKeywords;
	}

	public static void setWaitBetweenKeywords(int waitBetweenKeywords) {
		TimerConf.waitBetweenKeywords = waitBetweenKeywords;
	}


	public static int getOnTerminateSleep() {
		return onTerminateSleep;
	}

	public static void setOnTerminateSleep(int onTerminateSleep) {
		TimerConf.onTerminateSleep = onTerminateSleep;
	}

	public static int getWaitTimeBetweenTestCases() {
		return waitTimeBetweenTestCases;
	}

	public static void setWaitTimeBetweenTestCases(int waitTimeBetweenTestCases) {
		TimerConf.waitTimeBetweenTestCases = waitTimeBetweenTestCases;
	}

}
