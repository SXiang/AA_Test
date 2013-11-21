@Echo OFF
SETLOCAL enabledelayedexpansion
cls

:Robot

IF '%Project%'=='' SET Project=Zaxxon
IF '%Revision_Num%'=='' SET Revision_Num=Latest?
IF '%TestBy%'=='' SET TestBy=Demo
IF '%testDir%'=='' SET testDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
rem IF '%testSuite%'=='' SET TestSuite=TestSuite
IF '%testSuite%'=='' SET testSuite=%testDir%\TestSuite
IF '%TEST_CATEGORY%'=='' SET %TEST_CATEGORY%=Daily
IF '%pathToRun%'=='' SET pathToRun=\\nas2-dev\QA_A\AN\AutomationScriptRunner\bin\execution\local\scripts\
SET UPDATE_PROJECTS=%DeleteWorkspace%

:Server

IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%USER_NAME%'=='' SET USER_NAME=QAMAIL
IF '%PASSWORD%'=='' SET PASSWORD=Password00

IF NOT '%DOMAIN_NAME%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%
IF NOT EXIST %testDir% (
   IF /I NOT "%USER_NAME%"=="Your ACL User Name" NET USE *: %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
   )

:Options
::Rebot options
SET outputxml=
SET tagsCombine=
SET reportDir=
SET combineReportDir=%testDir%\TestResult\%TestBy%\RobotReport
IF Not '%combineReportDir%'=='' (
  IF exist %combineReportDir% rmdir /S /Q %combineReportDir% 2>NUL
  )
SET screenshotDir=Screenshot
:: pybot options
SET nonCritical=--noncritical Debug ^
                --noncritical Known*
SET tagstatInclude=--tagstatinclude @* ^
	       --tagstatinclude Known* ^
	       --tagstatinclude Debug
IF '%OS_NAME%'=='' SET OS_NAME=%computername%


SET projectDir=%testDir%\TestResult\%TestBy%\%OS_NAME%
SET projectBackupDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner\Project

:: Update ACL Projects
Call %pathToRun%UpdateACLProject.bat

SET ProjectUpdated=True
:Desktop
SET Encoding=Unicode
SET sut=Desktop
SET imageName=ACLWin.exe

:: SET sutDir=C:\ACL\Analytics10.5_Binary\Zaxxon\Build_126\%Encoding%
SET sutDir=
IF Not '!%sut%Path!'=='' SET sutDir=!%sut%Path!
IF Not '!%sut%Type!'=='' SET Encoding=!%sut%Type!

IF '%sutDir%'=='' Goto Ironhide
IF Not Exist %sutDir%\%imageName% Goto Ironhide

rem SET OS_NAME=
SET reportDir=
Call %pathToRun%RobotTest.bat

Echo. '%tagsCombine%'
Echo. '%outputxml%'
Call :CopyArtifacts

:Ironhide
SET Encoding=Unicode
SET sut=Ironhide
SET imageName=ACLScript.exe
::SET sutDir=C:\ACL\Analytics10.5_Binary\Zaxxon\Build_126\%sut%%Encoding%
SET sutDir=
IF Not '!%sut%Path!'=='' SET sutDir=!%sut%Path!
IF Not '!%sut%Type!'=='' SET Encoding=!%sut%Type!
IF '%sutDir%'=='' Goto Report
IF Not Exist %sutDir%\%imageName% Goto Report

rem SET OS_NAME=
SET reportDir=
Call %pathToRun%RobotTest.bat

Echo. '%tagsCombine%'
Echo. '%outputxml%'
Call :CopyArtifacts

:Report

IF '%_Test_Doc%'=='' SET _Test_Doc=Demo_the_usage_of_Robot_Framework_for_ACL_Script_testing
IF '%_Report_Title%'=='' SET _Report_Title=ACL_Script_Runner[Demo]
IF '%_Test_Name%'=='' SET _Test_Name=Robot
Call %pathToRun%CombineReport.bat
Call Start "Report" /D%combineReportDir% chrome.exe %combineReportDir%\report.html     

:End

:EOF
:CopyArtifacts
Echo.Call XCOPY %reportDir%\%screenshotDir% %combineReportDir%\%screenshotDir%\ /Y /R
Call XCOPY %reportDir%\%screenshotDir% %combineReportDir%\%screenshotDir%\ /Y /R

Goto :EOF