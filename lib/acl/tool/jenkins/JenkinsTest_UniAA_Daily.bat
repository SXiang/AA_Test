Rem schedulerCMD: USER_NAME PASSWORD ACL Latest TEST_UNICODE TEST_NONUNICODE TEST_TYPE tFolder
SET testbat=%JENKINS_HOME%\jobs\RFTBats\JenkinsTest.bat
SET reportDir=%JENKINS_HOME%\jobs\TestReport


REM default inputs
SET USER_NAME=Steven_Xiang
SET PASSWORD=*
SET DOMAIN_NAME=ACL
SET TEST_BUILD=Build_137
SET TEST_UNICODE=Yes
SET TEST_NONUNICODE=No
SET TEST_CATEGORY=Daily
SET PROJECT_TYPE=LOCALONLY
SET tFolder=D:\ACL\JENKINS_HOME\FS_ROOT\Jenkins_deploy\Unicode\ACLWin.exe



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

Rem disable it for log parser dev ---
Rem ************************************* 
Rem rmdir /S /Q %reportDir%.\FinishedTest

::SCHTASKS /Create /RU SYSTEM /SC ONCE /TN Automation_AA_Test /st 00:00 /sd 2022-01-01 /TR "%testbat% %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%"
::SCHTASKS /Create /SC ONCE /TN Automation_AA_Test /st 00:00 /sd 2012-01-01 /TR "%testbat%"
::SCHTASKS /Run /TN Monako_NonUniDailyLocal
rem  %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%
::START "Run Jenkins Job" /B /WAIT /D%testbat%.\..\ %testbat% %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%
::CALL "%testbat%" %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%
::goto DONE
Rem ************************************ 

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
%testbat%\..\sleep %period% /quiet
SET /a elapsed=elapsed+%period%
IF elapsed GTR wait (
  ECHO. Error: test not finished in %wait% seconds, check the log for details !
  GOTO DONE
) ELSE (
  GOTO WAIT
)
:DONE
ECHO Test Completed!!!

%testbat%\..\sleep 5 /quiet

TYPE %reportDir%\samples\test_details.log
Rem TYPE %reportDir%\test_summary.html
Rem TYPE %reportDir%\test_memusage.csv
Echo. Test Matrix: %reportDir%\test_matrix.xls
Echo. Test Details: %reportDir%\test_details.log
Echo. Test Summary: %reportDir%\test_summary.html
Echo. Mem Usage: %reportDir%\test_memusage.csv

:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%