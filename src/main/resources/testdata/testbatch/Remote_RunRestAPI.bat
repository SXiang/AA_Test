SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\Remote_RunRestAPI.pom
SET outputDir=C:\ACL\%pomDir%
SET sysPropPrefix=AutoQA.

Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
SET mainClass=ax.TestSuiteRestAPI
SET testDataFile=testdata/ax/TestSuiteRestAPI.xls
Rem ****************************************************

Rem ****************************************************
Rem *** ALL varialbes with prefix '_' will be used as TAF's System properties *** 
SET _appLocale=%LOCALE%
SET _unicodeTest=%TEST_UNICODE%
IF NOT %UPDATE_MASTER_FILE%' == '' SET _updateMasterFile=%UPDATE_MASTER_FILE%
SET _buildNumber=%TEST_BUILD%
SET _buildInfo=5.0.***
SET _buildName=AX5Web
SET _testerName=QA - Automation
SET _emailReport=%EMAIL_REPORT%
SET _traceMemusage=%TRACE_MEMUSAGE%
SET _testCategory=%TEST_CATEGORY%
SET _reportDir=%WORKSPACE%\TestReport
SET _axServerName=%AXSERVER_ADDR%
SET _dbtype=%dbType%
SET _serverip=%dbServerIP%
SET _port=%dbPort%
SET _dbname=%dbName%
SET _userid=%dbUserId%
SET _passwd=%dbPassword%
Rem ****************************************************

mkdir %outputDir% 2>nul

REM START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
PUSHD %outputDir%
CALL mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DoutputDir=%outputDir% ^
      -DappLocale=%_appLocale% ^
      -DunicodeTest=%_unicodeTest% ^
      -DupdateMasterFile=%_updateMasterFile% ^
      -DbuildNumber=%_buildNumber% ^
      -DbuildInfo=%_buildInfo% ^
      -DbuildName=%_buildName% ^
      -DtesterName=%_testerName% ^
	  -DaxServerName=%_axServerName% ^
	  -Ddbtype=%_dbtype% ^
	  -Dserverip=%_serverip% ^
	  -Dport=%_port% ^
	  -Ddbname=%_dbname% ^
	  -Duserid=%_userid% ^
	  -Dpasswd=%_passwd% ^
      -DemailCmd=%_emailCmd% ^
      -DemailReport=%_emailReport% ^
      -DtraceMemusage=%_traceMemusage% ^
      -DtestCategory=%_testCategory% ^
      -DjenkinsReportDir=%_reportDir% ^
	  -f %pomServer%\%pomDir%\%pom% ^
      clean