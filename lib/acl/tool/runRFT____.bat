@ECHO OFF
PUSHD %RFT_PROJECT_LOCATION%
: Usage of the batch file 


IF '%RFT_USER_DIR%' == '' SET RFT_USER_DIR=C:\Documents and Settings\All Users\Application Data\IBM\RFT\customization
REM *** Script varialbes ***
IF '%RFT_PROJECT_LOCATION%' == '' SET RFT_PROJECT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0\ACLQA_Automation
IF '%RFT_SCRIPT_NAME%' == '' SET RFT_SCRIPT_NAME=ACL_Desktop.TestCase.AutoTest
::IF '%RFT_SCRIPT_NAME%' == '' SET RFT_SCRIPT_NAME=AutoTest
IF '%RUN_SILENT%' == '' SET RUN_SILENT=Silent
IF '%RETEST%' == '' SET RETEST=Yes
IF '%TEST_CATEGORY%' == '' SET TEST_CATEGORY=Daily
::IF /I '%TEST_CATEGORY%' == '' SET TEST_CATEGORY=Smoke
::IF /I '%TEST_CATEGORY%' == '' SET TEST_CATEGORY=Regression
IF /I '%PROJECT_TYPE%' == '' SET PROJECT_TYPE=LOCAL
::IF /I '%PROJECT_TYPE%' == '' SET PROJECT_TYPE=SERVER
IF /I '%FILE_SERVER%' == '' SET FILE_SERVER=\\192.168.10.129\aclqa
REM *** ALL varialbes with prefix '_' will be used as RFT's System properties *** 
SET _AUT=D:/ACL/TFSView/RFT_Automation/Silverstone/Desktop/9.3.0.469/UnicodeSingleUser/
SET _unicodeTest=true
SET _testType=LOCAL
IF NOT %UPDATE_MASTER_FILE%' == '' SET _updateMasterFile=%UPDATE_MASTER_FILE%
SET _buildInfo=9.3.0.***
SET _buildName=Silverstone
SET _testerName=QA - Automation
SET _traceMemusage=%TRACE_MEMUSAGE%
SET _testCategory=%TEST_CATEGORY%
SET _testDataFile=%RFT_DATAPOOL_NAME%

REM *** Get Inputs from user ****
:INPUT
IF NOT '%1'=='' set RFT_PROJECT_LOCATION=%1
IF NOT '%2'=='' set RFT_SCRIPT_NAME=%2
IF NOT '%3'=='' set _AUT=%3
IF NOT '%4'=='' SET _unicodeTest=%4
IF NOT '%5'=='' SET _emailReport=%5
IF NOT '%6'=='' SET _emailCmd=%6
IF NOT '%7'=='' SET _testCategory=%7
IF NOT '%8'=='' SET RUN_SILENT=%8

SET RFT_LOGFILE_NAME=%RFT_SCRIPT_NAME%
IF /I '%TEST_NONUNICODE%'=='Yes' SET _UNICODE_TEST=false
IF NOT '%PROJECT_TYPE%'=='' SET _testType=%PROJECT_TYPE%
IF NOT '%_ProjectName%'=='' SET _buildName=%_ProjectName%
IF NOT '%Version%'=='' SET _buildInfo=%Version%
IF NOT '%USER_NAME%'=='' SET _testerName=%USER_NAME%@%DOMAIN_NAME%


GOTO check_args
:check_args
if "%RFT_PROJECT_LOCATION%" == "" goto missing_args
if "%RFT_SCRIPT_NAME%" == "" goto missing_args
if "%RFT_LOGFILE_NAME%" == "" goto missing_args
goto args_ok

:args_ok
if /I "RUN_SILENT" == "silent" goto playback
echo.
echo RFT_PROJECT_LOCATION=%RFT_PROJECT_LOCATION%
echo RFT_SCRIPT_NAME=%RFT_SCRIPT_NAME%
echo RFT_LOGFILE_NAME=%RFT_LOGFILE_NAME%
echo IBM_RATIONAL_RFT_ECLIPSE_DIR=%IBM_RATIONAL_RFT_ECLIPSE_DIR%
echo IBM_RATIONAL_RFT_INSTALL_DIR=%IBM_RATIONAL_RFT_INSTALL_DIR%
echo.
echo Initializing RFT Playback...

:playback
IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
IF NOT EXIST %FILE_SERVER% NET USE %FILE_SERVER% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
SET sysPropPrefix=AutoQA.
::GOTO EOF
ECHO TEST_CATEGORY=%TEST_CATEGORY%  _testCategory=%_testCategory%
SET TEST_FLAG=%_AUT%.\..\..\Auto_%_testCategory%Test_Log[%ENCODE%].TXT
::DEL "%_AUT%.\..\..\Auto_%_testCategory%Test_Log[%ENCODE%].TXT" /S /Q
IF /I NOT '%RETEST%'=='Yes' (
	IF EXIST %TEST_FLAG% GOTO end
	)
IF EXIST %TEST_FLAG% DEL "%TEST_FLAG%" /S /Q
TASKKILL /F /T /IM %Executable%
START "Run RFT Script..." /D%RFT_PROJECT_LOCATION% /WAIT /B "%IBM_RATIONAL_RFT_ECLIPSE_DIR%\jdk\jre\bin\java" ^
-Xms256m -Xmx256m ^
-XX:MaxNewSize=256m ^
-XX:MaxPermSize=256m ^
-Duser.language=en ^
-Duser.country=CA ^
-Dfile.encoding=utf-8 ^
-D%sysPropPrefix%AUT=%_AUT% ^
-D%sysPropPrefix%unicodeTest=%_unicodeTest% ^
-D%sysPropPrefix%testType=%_testType% ^
-D%sysPropPrefix%updateMasterFile=%_updateMasterFile% ^
-D%sysPropPrefix%buildInfo=%_buildInfo% ^
-D%sysPropPrefix%buildName=%_buildName% ^
-D%sysPropPrefix%testerName=%_testerName% ^
-D%sysPropPrefix%emailCmd=%_emailCmd% ^
-D%sysPropPrefix%emailReport=%_emailReport% ^
-D%sysPropPrefix%traceMemusage=%_traceMemusage% ^
-D%sysPropPrefix%testCategory=%_testCategory% ^
-D%sysPropPrefix%testDataFile=%_testDataFile% ^
-classpath "%RFT_PROJECT_LOCATION%/lib/ojdbc6.jar";"%RFT_PROJECT_LOCATION%/lib/postgresql-8.4-701.jdbc4.jar";"%RFT_PROJECT_LOCATION%/lib/ibm_package.jar";"%RFT_PROJECT_LOCATION%/lib/sqljdbc4.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/poi-3.7-20101029.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/poi-ooxml-3.7-20101029.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/poi-ooxml-schemas-3.7-20101029.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/poi-scratchpad-3.7-20101029.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/lib/commons-logging-1.1.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/lib/junit-3.8.1.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/lib/log4j-1.2.13.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/ooxml-lib/dom4j-1.6.1.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/ooxml-lib/geronimo-stax-api_1.0_spec-1.0.jar";"%RFT_PROJECT_LOCATION%/lib/poi-bin-3.7-20101029/poi-3.7/ooxml-lib/xmlbeans-2.3.0.jar";"%RFT_PROJECT_LOCATION%/lib/jawin2alpha/lib/jawin-stubs.jar";"%RFT_PROJECT_LOCATION%/lib/jawin2alpha/lib/jawin.jar";"%RFT_PROJECT_LOCATION%/lib/commons-io-2.2-bin.zip";"%RFT_PROJECT_LOCATION%/lib/commons-io-2.2-bin/commons-io-2.2/commons-io-2.2.jar";"%IBM_RATIONAL_RFT_INSTALL_DIR%\rational_ft.jar";"%RFT_PROJECT_LOCATION%/lib/info.bliki.wiki/bliki-core-3.0.17.jar" ^
com.rational.test.ft.rational_ft ^
-datastore "%RFT_PROJECT_LOCATION%" ^
-playback "%RFT_SCRIPT_NAME%" ^
-log "%RFT_LOGFILE_NAME%" >%TEST_FLAG% 2>&1

if /I "%RUN_SILENT%" == "silent" goto end
echo RFT playback complete.
goto end

:missing_args
echo.
echo ERROR: Invalid syntax!
goto end

:end
EXIT %ERRORLEVEL%
:EOF
EXIT %ERRORLEVEL%