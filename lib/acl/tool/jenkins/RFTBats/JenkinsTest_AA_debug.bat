@ECHO OFF
::debug
SET TEST_BUILD=Build_154
SET Version=Build_154
SET MISSINGDLLSSRC=\\winrunner\winrunner\SharedFiles\ACL_missing_Files
SET SRCROOT=\\biollante02\DailyBuild\Monaco\Dev
SET tFolder=Dev
SET USER_NAME=STEVEN_XINAG
SET PASSWORD=Redblue123e
SET WHICH=Unicode
SET TEST_CATEGORY=Daily
SET PROJECT_TYPE=LOCALONLY
SET RFT_SCRIPT_NAME=ACL_Desktop.TestCase.AutoTest
SET RFT_DATAPOOL_NAME=RFTBats/AutomationTestSuites_AA.xls
SET RFT_PROJECT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation
SET WORKSPACE=%JENKINS_HOME%\jobs\TestJenkinsServer\workspace
Rem schedulerCMD: USER_NAME PASSWORD ACL Latest TEST_UNICODE TEST_NONUNICODE TEST_TYPE tFolder
IF '%WORKSPACE%'=='' SET WORKSPACE=%JENKINS_HOME%\userContent
SET reportDir=%WORKSPACE%\TestReport
IF NOT EXIST %RFT_PROJECT_LOCATION% SET RFT_PROJECT_LOCATION=%WORKSPACE%.\..\%RFT_PROJECT_LOCATION%
IF NOT EXIST %RFT_PROJECT_LOCATION% SET RFT_PROJECT_LOCATION=D:\ACL\TFSView\RFT_Automation\QA_Automation_2012_V2.0_I18N\ACLQA_Automation
SET ScriptDir=%RFT_PROJECT_LOCATION%\lib\acl\tool
SET ScriptDirTest=%WORKSPACE%\RFTBats
PUSHD C:%HOMEPATH%\IBM\rationalsdp
IF NOT '%RFT_DATAPOOL_NAME%'=='' SET RFT_DATAPOOL_NAME=%WORKSPACE%.\%RFT_DATAPOOL_NAME%
IF NOT '%RFT_WORKSPACE%'=='' SET RFT_WORKSPACE="C:%HOMEPATH%\IBM\rationalsdp\%RFT_WORKSPACE%"
IF '%WHICH%'=='' SET WHICH=Unicode
IF /I '%WHICH%'=='Unicode' SET TEST_UNICODE=Yes
IF /I '%WHICH%'=='Unicode' SET TEST_NONUNICODE=No
IF /I '%WHICH%'=='Release' SET TEST_NONUNICODE=Yes
IF /I '%WHICH%'=='Release' SET TEST_UNICODE=No

REM default inputs
IF '%USER_NAME%'=='' SET USER_NAME=Steven_Xiang
IF '%PASSWORD%'=='' SET PASSWORD=Redblue123e
IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%TEST_BUILD%'=='' SET TEST_BUILD=BUILD_149
IF '%TEST_UNICODE%'=='' SET TEST_UNICODE=No
IF '%TEST_NONUNICODE%'=='' SET TEST_NONUNICODE=No
IF '%TEST_CATEGORY%'=='' SET TEST_CATEGORY=Daily
IF '%PROJECT_TYPE%'=='' SET PROJECT_TYPE=LOCALONLY
::IF '%tFolder%'=='' SET tFolder=D:\ACL\JENKINS_HOME\FS_ROOT\Jenkins_deploy\Unicode\ACLWin.exe
IF '%tFolder%'=='' SET tFolder=Dev

REM from calling script
IF NOT '%1'=='' SET USER_NAME=%1
IF NOT '%2'=='' SET PASSWORD=%2
IF NOT '%3'=='' SET DOMAIN_NAME=%3
IF NOT '%4'=='' SET TEST_BUILD=%4
IF NOT '%5'=='' SET TEST_UNICODE=%5
IF NOT '%6'=='' SET TEST_NONUNICODE=%6
IF NOT '%7'=='' SET TEST_CATEGORY=%7
IF NOT '%8'=='' SET PROJECT_TYPE=%8
IF NOT '%9'=='' SET tFolder=%9

IF EXIST %tFolder% GOTO RUN
SET DESROOT=%WORKSPACE%\ACLAnalytics
rem set desroot=D:\ACL\JENKINS_HOME\jobs\DailyTest_AA_Unicode\workspace\ACLAnalytics
IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
REM goto run
:GetBuild

SET XCSWITCH=/Y /E /R /I
START "" /B /WAIT XCOPY %MISSINGDLLSSRC% %DESROOT%\%WHICH%\ %XCSWITCH%
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\%WHICH% %DESROOT%\%WHICH%\ %XCSWITCH%
IF EXIST %DESROOT%\%WHICH%\ACLWin.exe SET tFolder=%DESROOT%\%WHICH%\ACLWin.exe
:Run

IF EXIST %reportDir%.\FinishedTest rmdir /S /Q %reportDir%.\FinishedTest
START "Run Jenkins Job" /B /WAIT /D"%ScriptDirTest%" JenkinsTest.bat %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%

REM goto DONE

:SETTIME
SET /a period=5*60
IF /I '%TEST_CATEGORY%'=='Daily' (
   SET /a wait=3*60*60
) ELSE IF /I '%TEST_CATEGORY%'=='Smoke' (
   SET /a wait=10*60*10
) ELSE IF /I '%TEST_CATEGORY%'=='Regression' (
   SET /a wait=20*60*60
) ELSE (
   SET /a wait=5*60*60
) 
Rem Debug ...
SET /a wait=5*60*60
SET /a period=1*60

:WAIT
IF EXIST %reportDir%.\FinishedTest GOTO DONE
%ScriptDir%\sleep %period% /quiet
SET /a elapsed=elapsed+%period%
IF elapsed GTR wait (
  ECHO. Error: test not finished in %wait% seconds, check the log for details !
  GOTO DONE
) ELSE (
  GOTO WAIT
)
:DONE
ECHO Test Completed!!!

%ScriptDir%\sleep 5 /quiet

REM TYPE %reportDir%\samples\test_details.log
TYPE %reportDir%\test_summary.html
Rem TYPE %reportDir%\test_memusage.csv
Rem Echo. Test Matrix: %reportDir%\test_matrix.xls
Rem Echo. Test Details: %reportDir%\test_details.log
Rem Echo. Test Summary: %reportDir%\test_summary.html
Rem Echo. Mem Usage: %reportDir%\test_memusage.csv

:EOF
EXIT 0
EXIT 0