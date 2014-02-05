SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\Remote_RunFromCommand.pom
SET outputDir=C:\ACL\%pomDir%
SET sysPropPrefix=AutoQA.

Rem ****************************************************
Rem Set default values for properties
Rem ****************************************************
Rem 1.Set default values for project properties
SET testerName=QA-Automation
SET buildInfo=5.0.***
SET testProject=AX5
SET unicodeTest=false

SET webDriver=chrome
SET imageName=chrome.exe
SET driverName=chromedriver.exe
SET casType=nonSSO
SET axServerName=QA-DEV.ACLQA.local
SET axServerPort=8443
SET apiPrefix=/aclax/api/

SET appLocale=En
SET updateMasterFile=false

SET serverNetDir=Automation
SET serverNetUser=Administrator
SET serverNetPassword=Password99

SET TestDataDir=TestData/
SET localizationDir=l10n/LocalizationProperties/
SET l10nPropertiesPrefix=AX5
SET tempLocalDir=%user.dir%/Output/Temp/

SET wikiLink=https://aclgrc.atlassian.net/wiki/display/ACD/

SET traceMemusage=true
SET traceImageName=services.exe
SET timeIntervalForMemusage=10
   
SET inBriefModel=true
SET jenkinsReport=true
SET jenkinsReportDir=
SET toolDir=taf/tool/
SET localInputDataDir=%user.dir%/../SharedAutomationTestData/ACLProject/DataSource/

Rem 2. Set default values for dbConf properties   DONE
SET dbtype=PostgreSQL
SET serverip=192.168.10.78
SET port=5432
SET dbname=AclAuditExchangeDB
SET userid=AclAuditExchangeRole
SET passwd=Password00

Rem 3. Set default values for logger properties    DONE
SET logRoot=%user.dir%/Output/Result/
SET logDirForPublic=//192.168.10.129/Automation/AX_Automation/AX5/
SET recentBugList=//192.168.10.129/Automation/AX_Automation/AX5/RecentBugs/recentBugList.html
SET openHtmlReport=true
SET openLogFile=true
SET openByApp=chrome
SET filterLevel=5
Rem ****************************************************

Rem ****************************************************
Rem *** Get values for common variables of both RESTAPI and FrontEnd from outside the batch
REM ALL varialbes with prefix '_' will be used as TAF's System properties *** 
SET appLocale=%LOCALE%
SET unicodeTest=%TEST_UNICODE%
IF NOT %UPDATE_MASTER_FILE%' == '' SET updateMasterFile=%UPDATE_MASTER_FILE%
SET _buildNumber=%TEST_BUILD%
SET _buildName=AX5Web
SET _testCategory=%TEST_CATEGORY%
SET emailReport=%EMAIL_REPORT%
SET traceMemusage=%TRACE_MEMUSAGE%
SET axServerName=%AXSERVER_ADDR%

SET jenkinsReportDir=%WORKSPACE%\TestReport
IF NOT EXIST %jenkinsReportDir% mkdir %jenkinsReportDir%

Rem *** Set values of variables only for RESTAPI or FrontEnd specially
 	SET mainClass=ax.TestSuiteExample
 	SET testDataFile=testdata/ax/TestSuiteExample.xls
 	SET webDriver=%webDriver%
 	SET imageName=%imageName%
	SET driverName=%driverName%
	SET dbtype=%dbType%
	SET serverip=%dbServerIP%
	SET port=%dbPort%
	SET dbname=%dbName%
	SET userid=%dbUserId%
	SET passwd=%dbPassword%
Rem ****************************************************

mkdir %outputDir% 2>nul

REM START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
PUSHD %outputDir%
CALL mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DoutputDir=%outputDir% ^
      -DtestCategory=%_testCategory% ^
      -DbuildNumber=%_buildNumber% ^
      -DbuildName=%_buildName% ^
	  -DtesterName=%testerName% ^
      -DbuildInfo=%buildInfo% ^
      -DtestProject=%testProject% ^
      -DunicodeTest=%unicodeTest% ^
      -DwebDriver=%webDriver% ^
      -DimageName=%imageName% ^
      -DdriverName=%driverName% ^
      -DcasType=%casType% ^
      -DaxServerName=%axServerName% ^
      -DaxServerPort=%axServerPort% ^
      -DapiPrefix=%apiPrefix% ^
      -DappLocale=%appLocale% ^
      -DupdateMasterFile=%updateMasterFile% ^
      -DserverNetDir=%serverNetDir% ^
      -DserverNetUser=%serverNetUser% ^
      -DserverNetPassword=%serverNetPassword% ^
      -DTestDataDir=%TestDataDir% ^
      -DlocalizationDir=%localizationDir% ^
      -Dl10nPropertiesPrefix=%l10nPropertiesPrefix% ^
      -DtempLocalDir=%tempLocalDir% ^
      -DwikiLink=%wikiLink% ^
      -DtraceMemusage=%traceMemusage% ^
      -DtraceImageName=%traceImageName% ^
      -DtimeIntervalForMemusage=%timeIntervalForMemusage% ^
      -DinBriefModel=%inBriefModel% ^
      -DjenkinsReport=%jenkinsReport% ^
      -DjenkinsReportDir=%jenkinsReportDir% ^
      -DtoolDir=%toolDir% ^
      -DlocalInputDataDir=%localInputDataDir% ^
      -Ddbtype=%dbtype% ^
      -Dserverip=%serverip% ^
      -Dport=%port% ^
      -Ddbname=%dbname% ^
      -Duserid=%userid% ^
      -Dpasswd=%passwd% ^
      -DlogRoot=%logRoot% ^
      -DlogDirForPublic=%logDirForPublic% ^
      -DrecentBugList=%recentBugList% ^
      -DopenHtmlReport=%openHtmlReport% ^
      -DopenLogFile=%openLogFile% ^
      -DopenByApp=%openByApp% ^
      -DfilterLevel=%filterLevel% ^
	  -f %pomServer%\%pomDir%\%pom% ^
      clean	 