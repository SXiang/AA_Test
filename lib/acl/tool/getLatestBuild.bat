@ECHO OFF
Rem Get Latest build - or from upstream JOB TEST_BUILD = BUILD_${BUILD_NUMBER}
IF NOT '%1'=='' SET tFolder=%1

IF '%tFolder%'=='' SET tFolder=RC
IF '%VerPrefix%'=='' SET VerPrefix=Build_
IF /I '%tFolder%'=='Dev' SET VerPrefix=%VerPrefix%1

IF '%SRCROOT%'=='' SET SRCROOT=\\biollante02\DailyBuild\Monaco
IF /I '%TEST_BUILD%'=='Latest' GOTO GET
IF '%TEST_BUILD%'=='' GOTO GET
IF /I '%tFolder%'=='Dev' GOTO GET
IF /I '%tFolder%'=='RC' GOTO GET
GOTO EOF
:GET
SET SRCROOT=%SRCROOT%\%tFolder%
SET NUM_TOKENS=5
SET DOMAIN_NAME=ACL

IF EXIST %tFolder% SET Version=%TEST_BUILD%
IF EXIST %tFolder% GOTO ENV
IF '%USER_NAME%'=='' SET USER_NAME=QAMail
IF '%PASSWORD%'=='' SET PASSWORD=Password00

if '%LatestVer%'=='' SET LatestVer=0
FOR /D  %%g IN (%SRCROOT%\%VerPrefix%*) DO (
    FOR /F "eol=. tokens=%NUM_TOKENS% usebackq delims=\" %%f IN ('%%g') DO (
	   IF /I '%%f' GTR '%VerPrefix%999' (
	      ECHO 
       ) ELSE IF /I '%%f' EQU '%VerPrefix%8_2012-10-12_14-12-34' (
	      ECHO 
       ) ELSE IF /I '%%f' EQU '%VerPrefix%9_2012-10-12_15-34-22' (
	      ECHO 
       ) ELSE IF /I '%%f' LSS '%VerPrefix%000' (
	      ECHO 
       ) ELSE IF /I '%LatestVer%' LEQ '%%f' (
	      SET LatestVer=%%f
	   )
    )   
)


SET Version=%_Version%
IF '%Version%'=='' SET Version=%LatestVer%
IF /I '%Version%'=='Latest' SET Version=%LatestVer%

:ENV
SET userProp=%JENKINS_HOME%\userContent\user.properties

Echo.TEST_BUILD=%Version%> %userProp%
ECHO.Version=%Version%>> %userProp%
ECHO.MISSINGDLLSSRC=\\winrunner\winrunner\SharedFiles\ACL_missing_Files>> %userProp%
ECHO.SRCROOT=%SRCROOT%>> %userProp%
ECHO.tFolder=%tFolder%>> %userProp%
ECHO.USER_NAME=%USER_NAME%>> %userProp%
ECHO.PASSWORD=%PASSWORD%>> %userProp%

:EOF
EXIT 0