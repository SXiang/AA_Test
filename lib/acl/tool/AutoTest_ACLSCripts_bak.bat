@ECHO OFF
REM ***************** Begin of configuration *****************************

REM Scheduler cmd: *.bat Steven_Xaing ******** ACL Yes No ACLScript.exe Dev *\*.ACL Script_Name
:SCRIPT
SET ACL_PROJECT_PATH=M:\Working\Silverstone\Test Cases\Old Features Regression Test Results\Steven\ImportTest
SET ACL_PROJECT=UnicodeTest\ImportUnicode.ACL
SET ACL_SCRIPT=ImportTestScript
:AUT
SET DESROOT=D:\ACL\TFSView\RFT_Automation\Monaco\Desktop
SET tFolder=Dev
SET tExecutable=ACLWin.exe
::SET tExecutable=ACLscript.exe
SET RUN_PARAMETER=%ACL_PROJECT_PATH%.\%ACL_PROJECT% /b%ACL_SCRIPT%
SET TEST_BUILD=Latest
SET TEST_UNICODE=Yes
::SET TEST_NONUNICODE=Yes

:LOGIN
SET DOMAIN_NAME=ACL
SET USER_NAME=Steven_Xiang
SET PASSWORD=******
REM ***************** End of configuration *****************************
:Default settings
SET ACL_UPDATE_SCRIPT_LOCATION=\\WINRUNNER\RFT\QA_Automation_2012_V2.0_Monako\ACL_User
SET ACL_UPDATE_SCRIPT=update_DT_IH_Build.bat
SET ACL_UPDATE_SCRIPT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation\lib\acl\tool
SET ACL_UPDATE_SCRIPT=updateACLDailyBuild.bat
SET RETEST=Yes

REM ***************** Execution of test ********************************

:INPUT from window's scheduler
IF NOT '%1'=='' SET USER_NAME=%1
IF NOT '%1'=='' SET SILENT=Silent
IF NOT '%2'=='' SET PASSWORD=%2
IF NOT '%3'=='' SET DOMAIN_NAME=%3
IF NOT '%4'=='' SET TEST_UNICODE=%4
IF NOT '%5'=='' SET TEST_NONUNICODE=%5
IF NOT '%6'=='' SET tExecutable=%6
IF NOT '%7'=='' SET tFolder=%7
IF NOT '%8'=='' SET ACL_PROJECT=%8
IF NOT '%9'=='' SET ACL_SCRIPT=%9

:RUN run tests
ECHO "Update ACL And Run Test..." /D%ACL_UPDATE_SCRIPT_LOCATION% /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
START "Update ACL And Run Test..." /D%ACL_UPDATE_SCRIPT_LOCATION% /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%

ECHO Test Completed!!!

IF NOT '%SILENT%'=='Silent' PAUSE
:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%
