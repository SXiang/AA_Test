@ECHO OFF
SETLOCAL enabledelayedexpansion

:Default_Properties
Echo. 1. Set robot test default properties
IF '%USER_NAME%'=='' SET USER_NAME=QAMail
IF '%PASSWORD%'=='' SET PASSWORD=Password00
IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%TEST_BUILD%'=='' SET TEST_BUILD=Latest
IF '%TEST_UNICODE%'=='' SET TEST_UNICODE=No
IF '%TEST_NONUNICODE%'=='' SET TEST_NONUNICODE=No
IF '%TEST_CATEGORY%'=='' SET TEST_CATEGORY=Daily
IF '%PROJECT_TYPE%'=='' SET PROJECT_TYPE=LOCALONLY
IF '%sut%'=='' SET sut=Desktop
IF '%Maven_Project%'=='' SET Maven_Project=\\192.168.10.129\Automation\MavenTest\AN_TestAutomation
IF '%WORKSPACE%'=='' SET WORKSPACE=%Maven_Project%\taf\jenkins\userContent\CI_Content\RobotBats
IF '%ROBOT_SCRIPT_NAME%'=='' SET ROBOT_SCRIPT_NAME=RobotTest.bat
IF '%testDir%'=='' SET testDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
IF '%testSuite%'=='' SET testSuite=\\nas2-dev\QA_A\AN\AutomationScriptRunner\TestSuite
SET OS_NAME=%ThisOS%
SET Encoding=%Which%
IF /I '%Encoding%'=='Release' SET Encoding=NonUnicode

:Robot_Properties
Echo.  2. Setup pybot properties
SET ScriptDir=%WORKSPACE%\RobotBats
SET robotDir=%ScriptDir%\execution\local\scripts
SET CopyReportDir=%WORKSPACE%\TestReport
SET screenshotDir=Screenshot

IF Not '%reportDir%'=='' SET CopyReportDir=%reportDir%
SET reportDir=%testDir%\TestResult\%TestBy%\%OS_NAME%\%Encoding%\RobotReport\%sut%

IF /I '%sut%'=='Ironhide' (
  SET imageName=ACLScript.exe
  SET sutPrefix=%sut%
  ) ELSE (
  SET imageName=ACLWin.exe
  SET sutPrefix=
  )

:L10N_Properties
Echo. 3. Setup L10N properties
Rem En,De,Es,Pt,Fr,Ch,Ko,Jp,Pl
SET HexID=HexID
SET En%HexID%=0x0409
SET Ch%HexID%=0x0804
SET Fr%HexID%=0x040c
SET De%HexID%=0x0407
SET Jp%HexID%=0x0411
SET Ko%HexID%=0x0412
SET Pl%HexID%=0x0415
rem SET Pt%HexID%=0x0416
rem SET Es%HexID%=0x040a
SET Pt%HexID%=0x0816
SET Es%HexID%=0x0c0a


SET En%HexID%=1033
SET Ch%HexID%=2052
SET Fr%HexID%=1036
SET De%HexID%=1031
SET Jp%HexID%=1041
SET Ko%HexID%=1042
SET Pl%HexID%=1045
SET Pt%HexID%=1046
SET Es%HexID%=1034

:UNC_Connection
Echo. 4. Check UNC paths
::BuildServer
IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
%ScriptDir%\sleep 2 /quiet

IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
%ScriptDir%\sleep 2 /quiet

::Automation Project Server
IF NOT EXIST %Maven_Project% NET USE %Maven_Project% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL
%sCRIPTDir%\sleep 2 /quiet
IF NOT EXIST %HistoryDir% NET USE %HistoryDir% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL

IF NOT EXIST %testSuite% NET USE %testSuite% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
%ScriptDir%\sleep 2 /quiet


:ConfLocale
Echo. 5. Setup Locale and Encoding variables
IF '%LOCALE%' == '' SET LOCALE=En
REM ******************* Attention **********************************************************
REM IN case testing l10n including En version, or else we don't need to change this - Steven
REM If the folder is not same as %Branch% (depents on CM), need to use the folder name instead.
REM IF /I '%LOCALE%' == 'En' SET SRCROOT=%SRCROOT%\..\Dev
IF /I '%LOCALE%' == 'En' (
  IF /I Not '%Branch%' == '' SET SRCROOT=%SRCROOT%\..\%Branch%
)
REM ****************** END *****************************************************************
IF '%WHICH%'=='' SET WHICH=Unicode
IF /I '%WHICH%'=='Unicode' SET TEST_UNICODE=Yes
IF /I '%WHICH%'=='Unicode' SET TEST_NONUNICODE=No
IF /I '%WHICH%'=='Release' SET TEST_NONUNICODE=Yes
IF /I '%WHICH%'=='Release' SET TEST_UNICODE=No

REM SET SERVER TEST FOR L10N
IF /I '%PROJECT_TYPE%' == 'ServerAndLocal' GOTO ConfType
GOTO ConfContinue

:ConfType
Echo. 6. Configure test with Jenkins job's variables
SET PROJECT_TYPE=LOCALONLY
IF /I '%WHICH%'=='Unicode' (
  IF /I '%LOCALE%' == '%ACLSELocaleUni%' SET PROJECT_TYPE=SERVER
)

IF /I '%WHICH%'=='Release' (
  IF /I '%LOCALE%' == '%ACLSELocaleNonUni%' SET PROJECT_TYPE=SERVER
)

:ConfContinue
IF '%CopyReportDir%'=='' SET CopyReportDir=%WORKSPACE%\TestReport

IF EXIST %reportDir%.\FinishedTest rmdir /S /Q %reportDir%.\FinishedTest 2>NUL
mkdir %ReportDir% 2>NUL

SET redir=%WORKSPACE%\..\TestHistory\Robot\%Version%.%Version_Suffix%-%LOCALE%-%WHICH%-%TEST_CATEGORY%-%PROJECT_TYPE%-%sut%
SET nodeProp=%HistoryDir%\%COMPUTERNAME%.properties

Echo.%PROJECT_TYPE%> %nodeProp%

SET SubTitlePrefix=
IF EXIST %redir% SET SubTitlePrefix=[Retest]
IF /I "%BUILD_CAUSE%" == "USERIDCAUSE" GOTO goon
IF /I "%TitlePrefix%" == "[Skipped]" GOTO Skip
IF /I "%BUILD_CAUSE%" == "UPSTREAMTRIGGER" GOTO goon
IF /I "%BUILD_CAUSE%" == "" GOTO goon
IF /I Not '%NewBuildOnly%'=='Yes' GOTO goon
IF Not "%SubTitlePrefix%"=="" GOTO Skip

:GoOn

IF /I '%verType%'=='Install' GOTO Install
IF /I '%verType%'=='Copy' GOTO GetBuild
IF /I '%verType%'=='Current' GOTO RUN
IF Exist %tFolder% (
   SET SUTDIR=%tFolder%\..
   GOTO RUN
   )

:GetBuild
Echo. 10.2. Get binary build %sut% %Which% To C:\ACL\CI_Jenkins\Analytics_binary\%sutPrefix%%WHICH%
SET DESROOT=C:\ACL\CI_Jenkins\Analytics_binary\%sutPrefix%%WHICH%
rem %WORKSPACE%\ACLAnalytics10.5_binary\%sutPrefix%%WHICH%
SET SUTDIR=%DESROOT%
SET XCSWITCH=/Y /E /R /I
IF NOT EXIST %SRCROOT%\%Version%\%sutPrefix%%WHICH%\%imageName% %ScriptDir%\sleep 30 /quiet

IF NOT EXIST %SRCROOT%\%Version%\%sutPrefix%%WHICH%\%imageName% (
   Echo.'%SRCROOT%\%Version%\%sutPrefix%%WHICH%\%imageName%' -- source not existed?
   Goto Error
  )
Echo. COPY BINARY BUILD - XCOPY %SRCROOT%\%Version%\%sutPrefix%%WHICH% %DESROOT%\
START "" /B /WAIT XCOPY %MISSINGDLLSSRC% %DESROOT%\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\%sutPrefix%%WHICH% %DESROOT%\ %XCSWITCH%>NUL

::Verify copy result
SET tFolder=%DESROOT%\%imageName%

IF NOT EXIST %tFolder% %ScriptDir%\sleep 10 /quiet
IF NOT EXIST %tFolder% (
   Echo.'%tFolder%' -- SUT not found?
   Goto Error
  )
GOTO Run
:Install
Echo. 10.1. Install %SUT% %which% To %tFolder%\%sutPrefix%%WHICH%
SET XCSWITCH_=/Y /R /I
SET LOGDIR=%tFolder%
SET DESROOT=%tFolder%\%sutPrefix%%WHICH%
SET SUTDIR=%DESROOT%
:ConfigInstaller
SET InstallerFolder=%SRCROOT%\%Version%\%Version_Suffix%
IF '%Version_Suffix%'=='' SET InstallerFolder=%SRCROOT%\%Version%\Installer

IF /I '%LOCALE%'=='EN' SET LOCALE=En
IF /I '%LOCALE%'=='CN' SET LOCALE=Zh
IF /I '%LOCALE%'=='CH' SET LOCALE=Zh
IF /I '%LOCALE%'=='JP' SET LOCALE=Ja
REM IF /I NOT "%LOCALE%" == "En" SET InstallerFolder=%SRCROOT%\%Version%\LocalizedInstallers
rem IF '%LOCALE%'=='Pl' SET InstallerFolder=%SRCROOT%\%Version%\Polish
SET installer_LOCALE=%LOCALE%
IF /I '%LOCALE%'=='ZH' SET installer_LOCALE=Ch
IF /I '%LOCALE%'=='JA' SET installer_LOCALE=Jp

SET InstallerQA_prefix=ACLv10%installer_LOCALE%_%sutPrefix%
SET InstallerRC_prefix=ACLAnalytics10%installer_LOCALE%_Desktop_
SET Installer_suffix=%WHICH%
SET Installer_prefix=%InstallerQA_prefix%
SET langHexID=!%LOCALE%%HexID%!
echo Language Hex ID = '%langHexID%'

REM Enable This 2 for RC installer - Steven
REM SET Installer_prefix=%InstallerRC_prefix%
REM IF /I '%WHICH%'=='Release' SET Installer_suffix=nonUnicode




SET LOCAL_Installer_prefix=%Installer_prefix%

SET LOCAL_INSTALLEXE=%sutPrefix%ACLInstaller.exe

IF Exist %InstallerFolder%\%Installer_prefix%%Installer_suffix%.exe GOTO continueInstall

SET /a elapsed=0
REM SET /a wait=60
 SET /a wait=60*60
SET /a period=1*5
:WaitForInstaller
REM TASKKILL /F /T /IM Sleep.exe 2>NUL
%ScriptDir%\sleep %period% /quiet

IF Exist %InstallerFolder%\%Installer_prefix%%Installer_suffix%.exe GOTO continueInstall
SET /a elapsed=elapsed+period
IF %elapsed% GTR %wait% (
  Echo. %InstallerFolder%\%Installer_prefix%%Installer_suffix%.exe Not Found?
  ECHO. Warning: installer not found in %wait% seconds, check the log for details !
  GOTO continueInstall
) ELSE (
  GOTO WaitForInstaller
)
:continueInstall
IF Not Exist %InstallerFolder%\%Installer_prefix%%Installer_suffix%.exe (
   GOTO Error
   REM SET Installer_prefix=%InstallerQA_prefix%
   REM SET Installer_suffix=%WHICH%
)

SET INSTALLEXE=%Installer_prefix%%Installer_suffix%.exe

SET tFolder=%DESROOT%\%imageName%
SET INSTALL_DIR=%DESROOT%

  IF NOT EXIST %InstallerFolder%\%INSTALLEXE% (
   Echo.Not found: %InstallerFolder%\%INSTALLEXE%
   Goto Error
  )
  IF EXIST %DESROOT%.\%VerType%\%Version%_%LOCALE% (
     IF EXIST %INSTALL_DIR%\%imageName% GOTO Run
   )
   
   TASKKILL /F /T /IM %imageName% 2>NUL
   TASKKILL /F /T /IM %INSTALLEXE% 2>NUL
   

  
  IF NOT EXIST %DESROOT%.\%VerType%\%Version%_%LOCALE% (
      ECHO.%DESROOT%\..\Release\%VerType%\%LOCAL_INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%LOGDIR%\Uninstallation.log\"" 2>NUL
	  %DESROOT%\..\Release\%VerType%\%LOCAL_INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%LOGDIR%\Uninstallation.log\"" 2>NUL
	  TYPE "%LOGDIR%\Uninstallation.log" 2>NUL
	  ECHO.%DESROOT%\..\Unicode\%VerType%\%LOCAL_INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%LOGDIR%\Uninstallation.log\"" 2>NUL
	  %DESROOT%\..\Unicode\%VerType%\%LOCAL_INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%LOGDIR%\Uninstallation.log\"" 2>NUL
	  TYPE "%LOGDIR%\Uninstallation.log" 2>NUL
	  Rem 	  %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_DIR%\%VerType%\Uninstallation.log\"" 2>NUL
	  Rem 
	  RMDIR /S /Q %DESROOT%\..\Release\%VerType% 2>NUL
	  RMDIR /S /Q %DESROOT%\..\Unicode\%VerType% 2>NUL
   ) 

   RMDIR /S /Q %DESROOT%.\%VerType% 2>NUL
   MKDIR %DESROOT%.\%VerType%\%Version%_%LOCALE% 2>NUL
rem   ECHO. XCOPY %InstallerFolder%\%INSTALLEXE% %DESROOT%\%VerType%\%INSTALLEXE% %XCSWITCH_%
   ECHO. F | XCOPY %InstallerFolder%\%INSTALLEXE% %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% %XCSWITCH_%
   ECHO. %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% /s /a /s /L%langHexID% /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%LOGDIR%\Installation.log\""
   %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% /s /a /s /L%langHexID% /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%LOGDIR%\Installation.log\""
   TYPE "%LOGDIR%\Installation.log" 2>NUL
GOTO Run
:Run
IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
 CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\ACLImex.dll /unregister > NUL
 CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\ACLImex.dll /register > NUL
)
rem ECHO.START "Run Robot Test" /B /WAIT /D"%robotDir%" %robotDir%\%ROBOT_SCRIPT_NAME%
Echo. Call %robotDir%\%ROBOT_SCRIPT_NAME%

pushd %robotDir%
Call %robotDir%\%ROBOT_SCRIPT_NAME%
popd

CALL :ExportResult

Rem Debugging -- Steven
goto done
:SETTIME

SET /a elapsed=0
SET /a gracetime=1*10*60
SET /a period=1*60
SET /a wait=MAX_RUNTIME+gracetime
IF %wait% GTR 59 (   
   ECHO. In progress..., max running time is %wait% seconds - including a grace period - %gracetime% seconds
   GOTO WAIT
   )
:: This is for long execution period, won't be used now - Steven
IF /I '%TEST_CATEGORY%'=='Daily' (
   SET /a wait=5*60*60
) ELSE IF /I '%TEST_CATEGORY%'=='Smoke' (
   SET /a wait=10*60*60
) ELSE IF /I '%TEST_CATEGORY%'=='Regression' (
   SET /a wait=22*60*60
) ELSE (
   SET /a wait=5*60*60
)
SET /a wait=wait+gracetime
IF %wait% GTR 7200 (
   ECHO. In progress..., max running time is %wait% seconds - including a grace period - %gracetime% seconds
   GOTO WAIT
   )

:WAIT

IF EXIST %reportDir%.\FinishedTest GOTO DONE
REM TASKKILL /F /T /IM Sleep.exe 2>NUL
%ScriptDir%\sleep %period% /quiet
SET /a elapsed=elapsed+period
IF %elapsed% GTR %wait% (
  TASKKILL /F /T /IM java.exe 2>NUL
  ECHO. Timeout! > %reportDir%.\test_summary.html
  ECHO. Error: test has not been finished in %wait% seconds, check the console output for details ! >> %reportDir%.\test_summary.html
  GOTO DONE
) ELSE (
  GOTO WAIT
)
:DONE

ECHO Test Completed!!!
IF EXIST %reportDir%.\Interrupted Echo Test was interrupted by another user?
IF EXIST %reportDir%.\Interrupted mkdir %HistoryDir%\Interrupted 2>NUL

GOTO EOF
:Error
SET SubTitlePrefix=[Error]
Echo Failed to deploye %Version% to %DESROOT% for testing, check it out! > %ReportDir%\test_summary.html
IF EXIST %HistoryDir% mkdir %HistoryDir%\JenkinsError 2>NUL
CALL :ExportResult
GOTO EOF
:Skip
SET SubTitlePrefix=[Skipped]
Echo.This build had been tested  on this machine: %TEST_CATEGORY% test [%PROJECT_TYPE%] %Version%-%WHICH%, test skipped! > %ReportDir%\test_summary.html
CALL :ExportResult
GOTO EOF

:ExportResult
Echo. 11. Touch HTML report segment
SET testName=%Project%_%Branch%#%REVISION_NUM%.%Version_Suffix%_%LOCALE%-%WHICH%%TYPE_SUFFIX%

SET ROSTYPE=%ThisOs%
SET RLOCALE=%LOCALE%
SET RCHARSET=%WHICH%
IF /I '%RCHARSET%' == 'Release' SET RCHARSET=NonUnicode
SET RTABLETYPE=%PROJECT_TYPE%
IF /I '%RTABLETYPE%' == 'LOCALONLY' SET RTABLETYPE=Local
IF /I '%RTABLETYPE%' == 'LOCAL' SET RTABLETYPE=Local
IF /I '%RTABLETYPE%' == 'SERVER' SET RTABLETYPE=Server
SET RTESTTYPE=%TEST_CATEGORY%
SET RPS=...%testSuite:~-10%
Echo.^<tr style="background-color:#F5F5F5"^>^
          ^<td^> %ROSTYPE% ^</td^>^
		  ^<td^> %RLOCALE% ^</td^>^
		  ^<td^> %RCHARSET% ^</td^>^
		  ^<td^> %SUT% ^</td^>^
		  ^<td^> ^<a href="%BUILD_URL%"^> %testName% ^</a^> ^</td^>^
		  ^<td^> %RTESTTYPE% ^</td^>^
		  ^<td^> ^<a href="file:///%testSuite%"^> %RPS% ^</a^>^</td^> > %HistoryDir%\%COMPUTERNAME% 2>NUL

ECHO.%ReportDir%\output.xml> %HistoryDir%\%COMPUTERNAME%.robot

Rem START "" /B /WAIT XCOPY %outDir% %CopyReportDir%\ /Y /R /I>NUL
START "" /B /WAIT XCOPY %ReportDir% %CopyReportDir%\ /Y /R /E /I>NUL
SET ReportDir=%CopyReportDir%
IF EXIST %HistoryDir% mkdir %HistoryDir%\ReportReady 2>NUL
rem SET nodeProp=%HistoryDir%\%COMPUTERNAME%.properties
rem Echo.%PROJECT_TYPE%> %nodeProp%

%ScriptDir%\sleep 5 /quiet
IF NOT EXIST %HistoryDir%\%COMPUTERNAME% %ScriptDir%\sleep 5 /quiet
GOTO :EOF
:EOF
