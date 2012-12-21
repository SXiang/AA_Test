@ECHO OFF
Rem schedulerCMD: USER_NAME PASSWORD ACL Latest TEST_UNICODE TEST_NONUNICODE TEST_TYPE tFolder
Rem schedulerCMD: Steven_Xiang ********* ACL Latest Yes No Daily SERVER Dev
Rem schedulerCMD: Steven_Xiang ********* ACL Latest No Yes Daily LOCALONLY Dev
REM ***************** Begin of configuration *****************************

:SCRIPT
SET RFT_PROJECT_LOCATION=W:\QA_Automation_2012_V2.0_Monako\ACL_User\Steven\ACLQA_Automation
SET RFT_DATAPOOL_NAME=C:\Documents and Settings\steven_xiang\Desktop\batchRunData.xls
SET RFT_WORKSPACE="C:%HOMEPATH%\IBM\rationalsdp\workspace"
SET RFT_SCRIPT_NAME=ACL_Desktop.TestCase.AutoTest
:AUT
SET DESROOT=C:\ACL\Monaco\Desktop
SET tFolder=Dev
::SET DESROOT=C:\Progra~1\ACL Software\ACL Analytics 10
::SET DESROOT=C:\Progra~2\ACL Software\ACL Analytics 10
SET _ProjectName=Monaco
SET _VersionPrefix=Build_
SET TEST_BUILD=Latest
::SET TEST_BUILD=9.3.0.437
SET TEST_UNICODE=Yes
::SET TEST_NONUNICODE=Yes
Rem ### Locale for application ###
Rem LOCALE=En,De,Es,Pt,Fr;Zh,Pl,Ko,Ja
SET LOCALE=En
:TEST
SET RETEST=Yes
::SET TEST_CATEGORY=Daily
SET TEST_CATEGORY=Smoke
::SET TEST_CATEGORY=Regression
::SET TEST_CATEGORY=Defect
::SET TEST_CATEGORY=DataType
SET PROJECT_TYPE=LOCAL
::SET PROJECT_TYPE=SERVER
SET SILENT_INSTALL=False
SET EMAIL_REPORT=true
SET TRACE_MEMUSAGE=true
SET UPDATE_MASTER_FILE=false
SET SINGLE_INSTANCE_AUT=false

:LOGIN
SET DOMAIN_NAME=ACL
SET USER_NAME=Steven_Xiang
SET PASSWORD=
SET FILE_SERVER=\\192.168.10.129\aclqa
GOTO INPUT
:EMAIL
SET ToAddress=%USER_NAME%@ACL.com
::SET ToAddress=Danny_Kusnardi@ACL.com
::SET CcAddress=Iryna_Babak@ACL.com;Jun_Wang@ACL.com;Maya_Gunawan@ACL.com;Nahid_Amini@ACL.com;Yousef_Aichour@ACL.com;Kwok_Young@ACL.com
::SET BccAddress=%USER_NAME%@ACL.com
GOTO RUN
REM ***************** End of configuration *****************************

REM ********************************************************************

REM ***************** Execution of test ********************************

:INPUT from window's scheduler
SET SILENT=ECHOON
REM Recormended inputs
IF NOT '%1'=='' SET USER_NAME=%1
IF NOT '%2'=='' SET PASSWORD=%2
IF NOT '%2'=='' SET SILENT=Silent
IF NOT '%3'=='' SET DOMAIN_NAME=%3
REM Optional inputs
IF NOT '%4'=='' SET TEST_BUILD=%4
IF NOT '%5'=='' SET TEST_UNICODE=%5
IF NOT '%6'=='' SET TEST_NONUNICODE=%6
IF NOT '%7'=='' SET TEST_CATEGORY=%7
IF NOT '%8'=='' SET PROJECT_TYPE=%8
IF NOT '%9'=='' SET tFolder=%9
GOTO EMAIL

:RUN run tests

SET ACL_UPDATE_SCRIPT=updateACLDailyBuild.bat
ECHO "Update ACL And Run Test..." /D%RFT_PROJECT_LOCATION%\lib\acl\tool /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
START "Update ACL And Run Test..." /D%RFT_PROJECT_LOCATION%\lib\acl\tool /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%

ECHO Test Completed!!!
%RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet

IF NOT '%SILENT%'=='Silent' PAUSE
:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%
