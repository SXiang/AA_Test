package conf.bean;

public class TimerConf {

	public  int 
	
	    //seconds  -
	waitBetweenKeywords = 5,
	maxPageLoadingTime = 120,

	errDialogCloseWaitTime = 2,
	itemPasteTime = 5,
	expandWaitTime = 3,
	itemClickTime = 3,
	pageRefreshTime = 3,
	dialogLoadTime = 3,
	launchWaitTime = 15,
			onTerminateSleep = 5, 
			waitTimeBetweenTestCases = 10,
			
	// - General timer
	maxWaitTime = 30,
	waitBetweenRetry = 5,
	loginWaitTime = 5,
	saveToDiskTime = 5,
	waitToTakeScreenshot = 3,
	waitToFindElement = 30;
	
	public int getWaitToTakeScreenshot() {
		return waitToTakeScreenshot;
	}

	public void setWaitToTakeScreenshot(int waitToTakeScreenshot) {
		this.waitToTakeScreenshot = waitToTakeScreenshot;
	}

	public int getWaitToFindElement() {
		return waitToFindElement;
	}

	public void setWaitToFindElement(int waitToFindElement) {
		this.waitToFindElement = waitToFindElement;
	}

	public void setWaitBetweenKeywords(int waitBetweenKeywords) {
		this.waitBetweenKeywords = waitBetweenKeywords;
	}

	public void setMaxPageLoadingTime(int maxPageLoadingTime) {
		this.maxPageLoadingTime = maxPageLoadingTime;
	}

	public void setOnTerminateSleep(int onTerminateSleep) {
		this.onTerminateSleep = onTerminateSleep;
	}

	public void setWaitTimeBetweenTestCases(int waitTimeBetweenTestCases) {
		this.waitTimeBetweenTestCases = waitTimeBetweenTestCases;
	}

	public void setErrDialogCloseWaitTime(int errDialogCloseWaitTime) {
		this.errDialogCloseWaitTime = errDialogCloseWaitTime;
	}

	public void setItemPasteTime(int itemPasteTime) {
		this.itemPasteTime = itemPasteTime;
	}

	public void setExpandWaitTime(int expandWaitTime) {
		this.expandWaitTime = expandWaitTime;
	}

	public void setItemClickTime(int itemClickTime) {
		this.itemClickTime = itemClickTime;
	}

	public void setPageRefreshTime(int pageRefreshTime) {
		this.pageRefreshTime = pageRefreshTime;
	}

	public void setDialogLoadTime(int dialogLoadTime) {
		this.dialogLoadTime = dialogLoadTime;
	}

	public void setLaunchWaitTime(int launchWaitTime) {
		this.launchWaitTime = launchWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}

	public void setWaitBetweenRetry(int waitBetweenRetry) {
		this.waitBetweenRetry = waitBetweenRetry;
	}

	public void setLoginWaitTime(int loginWaitTime) {
		this.loginWaitTime = loginWaitTime;
	}

	public void setSaveToDiskTime(int saveToDiskTime) {
		this.saveToDiskTime = saveToDiskTime;
	}




}
