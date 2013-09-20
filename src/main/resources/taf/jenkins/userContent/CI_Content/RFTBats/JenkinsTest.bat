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


ECHO.Create test flag as '%redir%'
mkdir %redir% 2>NUL
SET redir= >%redir%\RFTConsole.txt 2>$1

:AUT

IF '%DESROOT%'=='' SET DESROOT=D:\ACL\TFSView\RFT_Automation\%Project%\Desktop
IF NOT EXIST %DESROOT% (
   SET DESROOT=C:\ACL\%Project%\Desktop
   )
IF '%tFolder%'=='' SET tFolder=D:\ACL\JENKINS_HOME\FS_ROOT\Jenkins_deploy\Release\ACLWin.exe
::SET DESROOT=C:\Progra~1\ACL Software\ACL Analytics 10
::SET DESROOT=C:\Progra~2\ACL Software\ACL Analytics 10
SET _ProjectName=%Project%
SET _VersionPrefix=Build_
IF '%TEST_BUILD%'=='' SET TEST_BUILD=Latest
::SET TEST_BUILD=9.3.0.437
IF '%TEST_UNICODE%'=='' SET TEST_UNICODE=Yes
IF Not '%TEST_UNICODE%'=='Yes' SET TEST_NONUNICODE=Yes
Rem ### Locale for application ###
Rem LOCALE=En,De,Es,Pt,Fr;Zh,Pl,Ko,Ja
IF '%LOCALE%' == '' SET LOCALE=En
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
rem SET UPDATE_MASTER_FILE=true
SET SINGLE_INSTANCE_AUT=false

:LOGIN
IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%USER_NAME%'=='' SET USER_NAME=QAMail
IF '%PASSWORD%'=='' SET PASSWORD=Password00
IF '%UserFullName%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%
SET FILE_SERVER=\\192.168.10.129\aclqa
GOTO INPUT
:EMAIL
Rem Some cmd errors here ? when , or ; in the value -- Steven
Rem IF '%CcAddress%'=='' SET CcAddress=
Rem IF '%BccAddress%'=='' SET BccAddress=%USER_NAME%@ACL.com
Rem IF '%ToAddress%'=='' SET ToAddress=QAMail@ACL.com

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

echo %tFolder%
SET ACL_UPDATE_SCRIPT=updateACLDailyBuild.bat

IF Not Exist %tFolder% (
REM   ECHO "Update ACL And Run Test..." /D"%ScriptDir%" /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
REM   START "Update ACL And Run Test..." /D"%ScriptDir%" /WAIT /B %ACL_UPDATE_SCRIPT% %TEST_BUILD% %_VersionPrefix% %DOMAIN_NAME% %USER_NAME% %PASSWORD%
   GOTO ERROR
) 
SET DESROOT=%tFolder%.\..\
SET BUIDE=%tFolder%
SET tFolder=DeployedBuild
ECHO "Run test on deployed build..." /D"%ScriptDirTest%" /B /WAIT %ScriptDirTest%\Jenkins_doTest.bat
:RUN JENKINS SLAVE 
REM DON'T KNOW WHY START WON'T WORK ???? 
REM START "Run test on deployed build..." /D"%ScriptDirTest%" /B /WAIT %ScriptDir%\Jenkins_doTest.bat
SET JENKINS_TASK=RFTTest
REM CALL SCHTASKS /delete /tn %JENKINS_TASK% /f 2>NUL
REM ECHO CALL SCHTASKS /create /sc ONCE /tn RFTTest /tr %ScriptDirTest%\Jenkins_doTest.bat /u Jenkins /p Password00
REM CALL SCHTASKS /create /ru Jenkins /rp Password00 /sc MONTHLY /MO 11 /tn RFTTest /tr %ScriptDirTest%\Jenkins_doTest.bat
REM CALL SCHTASKS /RUN /TN %JENKINS_TASK%
CALL %ScriptDirTest%\Jenkins_doTest.bat
GOTO DONE
:ERROR
ECHO. Installation failed?
GOTO EOF
:DONE
%ScriptDir%\sleep 5 /quiet

rem IF NOT '%SILENT%'=='Silent' PAUSE
:EOF
EXIT 0
EXIT 0