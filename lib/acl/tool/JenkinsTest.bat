@ECHO OFF
Rem schedulerCMD: USER_NAME PASSWORD ACL Latest TEST_UNICODE TEST_NONUNICODE TEST_TYPE tFolder
Rem schedulerCMD: Steven_Xiang ********* ACL Latest Yes No Daily SERVER Dev
Rem schedulerCMD: Steven_Xiang ********* ACL Latest No Yes Daily LOCALONLY Dev
REM ***************** Begin of configuration *****************************

:SCRIPT
IF '%RFT_PROJECT_LOCATION%'=='' SET RFT_PROJECT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation
IF '%RFT_DATAPOOL_NAME%'=='' SET RFT_DATAPOOL_NAME=C:\Documents and Settings\steven_xiang\Desktop\batchRunData.xls
IF '%RFT_WORKSPACE%'=='' SET RFT_WORKSPACE="C:%HOMEPATH%\IBM\rationalsdp\workspace"

IF '%RFT_SCRIPT_NAME%'=='' SET RFT_SCRIPT_NAME=ACL_Desktop.TestCase.AutoTest

REM SET redir= >CON 2>&1
:AUT

IF '%DESROOT%'=='' SET DESROOT=D:\ACL\TFSView\RFT_Automation\Monaco\Desktop
IF NOT EXIST %DESROOT% (
   SET DESROOT=C:\ACL\Monaco\Desktop
   )
IF '%tFolder%'=='' SET tFolder=D:\ACL\JENKINS_HOME\FS_ROOT\Jenkins_deploy\Release\ACLWin.exe
::SET DESROOT=C:\Progra~1\ACL Software\ACL Analytics 10
::SET DESROOT=C:\Progra~2\ACL Software\ACL Analytics 10
SET _ProjectName=Monaco
SET _VersionPrefix=Build_
IF '%TEST_BUILD%'=='' SET TEST_BUILD=Latest
::SET TEST_BUILD=9.3.0.437
IF '%TEST_UNICODE%'=='' SET TEST_UNICODE=Yes
IF Not '%TEST_UNICODE%'=='Yes' SET TEST_NONUNICODE=Yes
Rem ### Locale for application ###
Rem LOCALE=En,De,Es,Pt,Fr;Zh,Pl,Ko,Ja
SET LOCALE=En
:TEST
SET RETEST=Yes
::SET TEST_CATEGORY=Daily
IF '%TEST_CATEGORY%'=='' SET TEST_CATEGORY=Smoke
::SET TEST_CATEGORY=Regression
::SET TEST_CATEGORY=Defect
::SET TEST_CATEGORY=DataType
IF '%PROJECT_TYPE%'=='' SET PROJECT_TYPE=LOCAL
::SET PROJECT_TYPE=SERVER
SET SILENT_INSTALL=False
SET EMAIL_REPORT=true
SET TRACE_MEMUSAGE=true
SET UPDATE_MASTER_FILE=false
SET SINGLE_INSTANCE_AUT=false

:LOGIN
IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%USER_NAME%'=='' SET USER_NAME=QAMail
IF '%PASSWORD%'=='' SET PASSWORD=Password00
IF '%UserFullName%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%
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
REM Elimilate '*'
IF NOT '%1'=='*' SET in1=%1
IF NOT '%2'=='*' SET in2=%2
IF NOT '%3'=='*' SET in3=%3
IF NOT '%4'=='*' SET in4=%4
IF NOT '%5'=='*' SET in5=%5
IF NOT '%6'=='*' SET in6=%6
IF NOT '%7'=='*' SET in7=%7
IF NOT '%8'=='*' SET in8=%8
IF NOT '%9'=='*' SET in9=%9
REM Recormended inputs
IF NOT '%in1%'=='' SET USER_NAME=%in1%
IF NOT '%in2%'=='' SET PASSWORD=%in2%
IF NOT '%in2%'=='' SET SILENT=Silent
IF NOT '%in3%'=='' SET DOMAIN_NAME=%in3%
REM Optional inputs
IF NOT '%in4%'=='' SET TEST_BUILD=%in4%
IF NOT '%in5%'=='' SET TEST_UNICODE=%in5%
IF NOT '%in6%'=='' SET TEST_NONUNICODE=%in6%
IF NOT '%in7%'=='' SET TEST_CATEGORY=%in7%
IF NOT '%in8%'=='' SET PROJECT_TYPE=%in8%
IF NOT '%in9%'=='' SET tFolder=%in9%

GOTO EMAIL
:RUN run tests
::SET ScriptDir=%RFT_PROJECT_LOCATION%\lib\acl\tool\jenkins
rem debug tFolder
:: set tFolder=D:\ACL\JENKINS_HOME\FS_ROOT\Jenkins_deploy\Unicode\ACLWin.exe
IF '%ScriptDirTest%'=='' SET ScriptDirTest=%WORKSPACE%\RFTBats
IF '%ScriptDir%'=='' SET ScriptDir=%RFT_PROJECT_LOCATION%\lib\acl\tool

rem echo %tFolder%
SET ACL_UPDATE_SCRIPT=updateACLDailyBuild.bat

IF Not Exist %tFolder% (
   ECHO "Update ACL And Run Test..." /D"%ScriptDir%" /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
   START "Update ACL And Run Test..." /D"%ScriptDirTest%" /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
   GOTO DONE
) 
SET DESROOT=%tFolder%.\..\
SET BUIDE=%tFolder%
SET tFolder=DeployedBuild
ECHO "Run test on deployed build..." /D"%ScriptDir%" /B /WAIT Jenkins_doTest.bat
REM DON'T KNOW WHY START WON'T WORK ???? 
::START "Run test on deployed build..." /D"%ScriptDirTest%" /B /WAIT Jenkins_doTest.bat
CALL %ScriptDir%\Jenkins_doTest.bat
:DONE
%ScriptDir%\sleep 5 /quiet

IF NOT '%SILENT%'=='Silent' PAUSE
:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%
