@ECHO OFF
SETLOCAL enabledelayedexpansion
SET TEAM_NAME=AN_TestAutomation
IF '%Project%'=='' SET Project=Zaxxon
IF '%TestBy%'=='' SET TestBy=%computername%

CALL "%WORKSPACE%\RobotBats\sleep" 120 /quiet
IF NOT '%1'=='' SET tFolder=%1

rem IF '%tFolder%'=='' SET tFolder=RC
rem [%LOCALE%]   En,De,Es,Pt,Fr;Zh,Pl,Ko,Ja
IF '%LOCALE%'=='' SET LOCALE=En

IF '%ACLSELocaleUni%' == '' SET ACLSELocaleUni=En
IF '%ACLSELocaleNonUni%' == '' SET ACLSELocaleNonUni=En

IF '%VerPrefix%'=='' SET VerPrefix=Build_
IF '%Email_Report%'=='' SET Email_Report=Yes
rem IF /I '%tFolder%'=='Dev' SET VerPrefix=%VerPrefix%
IF '%VerPattrn%'=='' SET VerPattrn=%VerPrefix%??
IF '%SRCROOT%'=='' SET SRCROOT=\\biollante02\DailyBuild\%Project%

SET NUM_TOKENS=4
SET DOMAIN_NAME=ACL

IF Not '%tFolder%'=='' (
  SET SRCROOT=%SRCROOT%\%tFolder%
  SET /a NUM_TOKENS = num_Tokens+1
  IF EXIST %tFolder% SET Version=%TEST_BUILD%
  IF EXIST %tFolder% GOTO ENV
)

IF /I '%TEST_BUILD%'=='Latest' GOTO GET
IF NOT '%REVISION_NUM%'=='' GOTO ENV
IF '%TEST_BUILD%'=='' GOTO GET
IF /I '%tFolder%'=='DEV' GOTO BRANCH
IF /I '%tFolder%'=='RC' GOTO BRANCH
IF /I '%tFolder%'=='' GOTO BRANCH
GOTO EOF

:BRANCH
IF "%Branch%" == "" SET Branch=%tFolder%

:GET
if '%LatestVer%'=='' SET LatestVer=%VerPrefix%0
SET %SILENT_INSTALL%==FALSE
IF /I '%verType%'=='Install' SET %SILENT_INSTALL%==TRUE
set dir_suffix=Installer
FOR /D  %%g IN (%SRCROOT%\%VerPattern%) DO (
    FOR /F "eol=. tokens=%NUM_TOKENS% usebackq delims=\" %%f IN ('%%g') DO (
	   SET tempLatestVer=%%f
	   SET /a tempLatestVer_num=!tempLatestVer:~6!
	   SET /a LatestVer_num=!LatestVer:~6!
	   IF !LatestVer_num! LEQ !tempLatestVer_num! (
	      IF /I '%SILENT_INSTALL%'=='TRUE' (
		    set dir_suffix=Installer
			   set sub_Type=\!dir_suffix!
		       IF EXIST %SRCROOT%\!tempLatestVer!\!dir_suffix!\%ACLUni%.exe SET LatestVer=!tempLatestVer!
			   IF EXIST %SRCROOT%\!tempLatestVer!\!dir_suffix!\%ACLNonUni%.exe SET LatestVer=!tempLatestVer!
		  ) ELSE (
	        SET LatestVer=!tempLatestVer!
		  )
	   )
    )
)

SET Version=%LatestVer%
SET REVISION_NUM=%LatestVer:~6%

:ENV

IF NOT '%REVISION_NUM%'=='' (
 SET Version=BUILD_%REVISION_NUM%
 SET aclBatchs=\\Biollante02\Batches
 SET Version_Suffix=%DIR_SUFFIX%
 )
REM IF '%REVISION_NUM%'=='' (
REM  SET REVISION_NUM=%Version:~6%
REM )
 
IF /I '%verType%'=='Install' (
 SET tFolder=C:\ACL\CI_Jenkins\Analytics10
)
IF /I '%TEST_CATEGORY%'=='Regression' SET TYPE_SUFFIX=[R
IF /I '%TEST_CATEGORY%'=='Smoke' SET TYPE_SUFFIX=[S
IF /I '%PROJECT_TYPE%'=='' SET PROJECT_TYPE=LOCALONLY
IF /I '%PROJECT_TYPE%'=='ServerAndLocal' SET TYPE_SUFFIX=%TYPE_SUFFIX%*]
IF /I '%PROJECT_TYPE%'=='SERVER' (
   SET TYPE_SUFFIX=%TYPE_SUFFIX%S]
   ) ELSE (
   IF /I Not '%PROJECT_TYPE%'=='ServerAndLocal' SET TYPE_SUFFIX=%TYPE_SUFFIX%L]
   )
   
IF /I '%TEST_CATEGORY%'=='Daily' SET TYPE_SUFFIX=
Rem set to True to test only Smoke/Regression tests
IF /I '%testCaseExclusive%'=='' SET testCaseExclusive=False
SET XCSWITCH=/Y /E /R /I

SET userProp="%JENKINS_HOME%"\userContent\user.properties

SET reportDir="%WORKSPACE%"\TestReport
IF Exist %reportDir% DEL "%reportDir%" /S /Q 2>NUL

SET strHDLocation=%ReportDir%\JenkinsReport.html

SET TestBy=Jenkins
rem SET Maven_Project=\\192.168.10.129\Automation\MavenTest\%team_Name%

SET uContDir=%Maven_Project%\taf\jenkins\userContent
SET guiBuglist=%uContDir%\buglist\guiTestCoveredBugs.html
SET batchBuglist=%uContDir%\buglist\batchTestCoveredBugs.html
SET unicodeACLSE=\\192.168.10.95
SET releaseACLSE=\\192.168.10.98
SET missingdllDir=\\winrunner\winrunner\SharedFiles\ACL_missing_Files
SET hisdir=%uContDir%\TestHistory\%Project%\%Version%.%Version_Suffix%-%LOCALE%-%WHICH%-%TEST_CATEGORY%-%PROJECT_TYPE%-%sut%
SET TEST_CATEGORY_final=Regression
SET hisdir_final=%uContDir%\TestHistory\%Project%\%Version%.%Version_Suffix%-%LOCALE%-%WHICH%-%TEST_CATEGORY_final%-%PROJECT_TYPE%-%sut%
SET testLocker=%uContDir%\TestHistory\%Project%\user.locker
SET TitlePrefix=

Rem If this locker enabled, you may need to manually delete it sometimes - Steven
Rem IF EXIST %testLocker% CALL "%WORKSPACE%\RFTBats\sleep" 300 /quiet
Rem IF EXIST %testLocker% (
Rem   SET TitlePrefix=[Skipped]
Rem   Goto PROPFILE
Rem  )
Rem type NUL > %testLocker% 2>NUL
  
IF EXIST %hisdir% SET TitlePrefix=[Retest]
IF /I "%BUILD_CAUSE%" == "USERIDCAUSE" GOTO goon
IF /I "%BUILD_CAUSE%" == "MANUALTRIGGER" GOTO goon
Rem IF /I "%BUILD_CAUSE%" == "UPSTREAMTRIGGER" GOTO goon
IF /I "%BUILD_CAUSE%" == "" GOTO goon
IF /I Not '%NewBuildOnly%'=='Yes' GOTO goon


IF EXIST %hisdir% (
  SET TitlePrefix=[Skipped]
  Echo.This build had been tested before, Test Skipped! > %hisdir%\reportBody 2>NUL
  Goto PROPFILE
  )
IF EXIST %hisdir_final% (
  SET TitlePrefix=[Skipped]
  Echo.This build had been tested in %TEST_CATEGORY_final%, Test Skipped! > %hisdir%\reportBody 2>NUL
  Goto PROPFILE
  )
:goon
IF EXIST %hisdir% RMDIR /S /Q %hisdir% 2>NUL
rem IF EXIST %hisdir%\JenkinsError RMDIR /S /Q %hisdir%\JenkinsError 2>NUL
rem IF EXIST %hisdir%\Interrupted  RMDIR /S /Q %hisdir%\Interrupted 2>NUL
mkdir %hisdir% 2>NUL
type NUL > %hisdir%\reportBody 2>NUL
type NUL > %hisdir%\reportBody.robot 2>NUL

:PROPFILE

Echo.TEST_BUILD=%Version% > %userProp%
ECHO.Version=%Version% >> %userProp%
ECHO.Version_Suffix=%Version_Suffix% >> %userProp%
ECHO.MISSINGDLLSSRC=%missingdllDir% >> %userProp%
ECHO.SRCROOT=%SRCROOT% >> %userProp%
ECHO.tFolder=%tFolder% >> %userProp%
ECHO.Branch=%Branch% >> %userProp%
ECHO.NewBuildOnly=%NewBuildOnly% >> %userProp%
Echo.AA_BUILD=%REVISION_NUM% >> %userProp%
Echo.BUILD_ID=%BUILD_ID% >> %userProp%
Echo.ToAddress=%ToAddress% >> %userProp%
Echo.CcAddress=%CcAddress% >> %userProp%
Echo.BccAddress=%BccAddress% >> %userProp%
Echo.strHDLocation=%strHDLocation% >> %userProp%
Echo.strFileURL=%BUILD_URL%../ >> %userProp%
Echo.aclBatchs=%aclBatchs% >> %userProp%
Echo.REVISION_NUM=%REVISION_NUM% >> %userProp%
Echo.verType=%verType% >> %userProp%
Echo.TEST_CATEGORY=%TEST_CATEGORY% >> %userProp%
Echo.TYPE_SUFFIX=%TYPE_SUFFIX% >> %userProp%
Echo.PROJECT_TYPE=%PROJECT_TYPE% >> %userProp%
Echo.ROBOT_SCRIPT_NAME=%ROBOT_SCRIPT_NAME% >> %userProp%
Echo.PROJECT=%PROJECT% >> %userProp%
Echo.guiBuglist=%guiBuglist% >> %userProp%
Echo.batchBuglist=%batchBuglist% >> %userProp%
Echo.reportFile=%hisdir%\reportBody>> %userProp%
Echo.UPDATE_MASTER_FILE=%UPDATE_MASTER_FILE% >> %userProp%
Echo.UPDATE_PROJECTS=%UPDATE_PROJECTS% >> %userProp%
Echo.MAX_RUNTIME=%MAX_RUNTIME% >> %userProp%
REM Echo.TitlePrefix=%TitlePrefix% >> %userProp%
Echo.HistoryDir=%hisdir% >> %userProp%
Echo.unicodeACLSE=%unicodeACLSE% >> %userProp%
Echo.releaseACLSE=%releaseACLSE% >> %userProp%
Echo.Email_Report=%Email_Report% >> %userProp%
Echo.TestCase_Prefix=%TestCase_Prefix% >> %userProp%
Echo.LOCALE=%LOCALE% >> %userProp%
Echo.ACLSELocaleUni=%ACLSELocaleUni% >> %userProp%

Echo.ACLSELocaleNonUni=%ACLSELocaleNonUni% >> %userProp%
Echo.testLocker=%testLocker% >> %userProp%
Echo.testCaseExclusive=%testCaseExclusive% >> %userProp%
::Added some for robot test
Echo.TestBy=%TestBy% >> %userProp%
Echo.Maven_Project=%Maven_Project% >> %userProp%
Echo.testDir=%TestDir% >> %userProp%
Echo.testSuite=%testSuite% >> %userProp%
Echo.tagExclude=%tagExclude% >> %userProp%
Echo.nonCritical=%nonCritical% >> %userProp%
Echo.tagstatInclude=%tagstatInclude% >> %userProp%

::Echo.comm=XCOPY %userProp% %WORKSPACE%\ %XCSWITCH% >> %userProp%
REM XCOPY %userProp% "%WORKSPACE%"\ %XCSWITCH% 2>NUL
IF /I '%PROJECT_TYPE%' == 'SERVER' GOTO setupACLSE
IF /I '%PROJECT_TYPE%' == 'ServerAndLocal' GOTO setupACLSE
goto skipACLSE

:setupACLSE
ECHO.CALL "%WORKSPACE%"\RobotBats\setUpACLSE
IF /I Not '%TitlePrefix%' == '[Skipped]' CALL "%WORKSPACE%"\RobotBats\setUpACLSE 2>NUL
IF EXIST %hisdir%\ACLSEError SET TitlePrefix=[Skipped]
IF NOT EXIST %unicodeACLSE%\analytic_engine SET TitlePrefix=[Skipped]
IF NOT EXIST %releaseACLSE%\analytic_engine SET TitlePrefix=[Skipped]

:skipACLSE
Echo.TitlePrefix=%TitlePrefix% >> %userProp%
CALL "%WORKSPACE%\RobotBats\sleep" 2 /quiet
ECHO.XCOPY %userProp% "%WORKSPACE%"\ %XCSWITCH%
XCOPY %userProp% "%WORKSPACE%"\ %XCSWITCH% 2>NUL

:EOF