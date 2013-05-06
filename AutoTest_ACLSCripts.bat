@ECHO OFF

REM ***************** PART 1: Verify these values for your test ************************************************************
SET ACL_PROJECT_PATH=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation\ACL_Desktop\DATA\ACLProject
SET DESROOT=D:\ACL\TFSView\RFT_Automation\Monaco\Desktop
REM ************************************************************************************************************************

REM ***************** PART 2: These values can be also specified in windows scheduled tasks ********************************
:: Example: "AutoTest_ACLSCripts.bat" Steven_Xaing ******** ACL Yes No ACLScript.exe Dev *\*.ACL Script_Name
SET USER_NAME=Your ACL User Name
SET PASSWORD=Your ACL Password
SET DOMAIN_NAME=ACL
SET TEST_UNICODE=Yes
::SET TEST_NONUNICODE=Yes
SET tExecutable=ACLWin.exe
::SET tExecutable=ACLscript.exe
SET tFolder=Dev
SET ACL_PROJECT=Unicode\DesktopScript\OldbatsDesktopEnglish\OldBats_U.acl
::SET ACL_PROJECT=NonUnicode\DesktopScript\OldbatsDesktopEnglish\OldBats_NU.acl
::SET ACL_PROJECT=Unicode\DesktopScript\OldbatsDesktopEnglish\OldBats_Ironhide_U.acl
::SET ACL_PROJECT=NonUnicode\DesktopScript\OldbatsDesktopEnglish\OldBats_Ironhide_NU.acl
SET ACL_SCRIPT=_AllTest


REM ***********************************************************************************************************************
REM *************************************************END OF USER CONCERNS *************************************************
REM ***********************************************************************************************************************
REM ***********************************************************************************************************************



REM **************** PART 3: Default settings AND commands(don't modify them if your are not sure what they are) **********
SET TEST_BUILD=Latest
SET ACL_UPDATE_SCRIPT_LOCATION=\\WINRUNNER\RFT\QA_Automation_2012_V2.0_Monako\ACL_User
SET ACL_UPDATE_SCRIPT=update_DT_IH_Build.bat
SET SEND_EMAIL=sendMail.bat
SET _VersionPrefix=
SET RETEST=Yes

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

SET RUN_PARAMETER=%ACL_PROJECT_PATH%.\%ACL_PROJECT% /b%ACL_SCRIPT%
::SET ACL_UPDATE_SCRIPT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation\lib\acl\tool
::SET ACL_UPDATE_SCRIPT=updateACLDailyBuild.bat
:RUN run tests
ECHO "Update ACL And Run Test..." /D%ACL_UPDATE_SCRIPT_LOCATION% /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
START "Update ACL And Run Test..." /D%ACL_UPDATE_SCRIPT_LOCATION% %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
ECHO Test RUNING in seperate thread!!!

:EMail
SET subject=test
rem mutil address and files can be seperated by semiconlon ';'
SET toAddress=
rem body could path to file (or plain text, but don't use ',' inside your text for now)
SET body=
SET attachFiles=
SET ccAddress=
SET bccAddress=

IF '%ACL_UPDATE_SCRIPT_LOCATION%'=='' SET ACL_UPDATE_SCRIPT_LOCATION=\\WINRUNNER\RFT\QA_Automation_2012_V2.0_Monako\ACL_User
ECHO "Send Test Report..." /D%ACL_UPDATE_SCRIPT_LOCATION% sendMail.bat
START "Send Test Report..." /D%ACL_UPDATE_SCRIPT_LOCATION% sendMail.bat
IF NOT '%SILENT%'=='Silent' PAUSE
:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%
