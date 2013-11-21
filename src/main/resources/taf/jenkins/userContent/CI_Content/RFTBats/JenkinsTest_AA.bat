@ECHO OFF
rem SET output=NUL
rem SET output=&1
Rem schedulerCMD: USER_NAME PASSWORD ACL Latest TEST_UNICODE TEST_NONUNICODE TEST_TYPE tFolder
SET ScriptDir=%RFT_PROJECT_LOCATION%\lib\acl\tool
SET ScriptDirTest=%WORKSPACE%\RFTBats
%ScriptDir%\sleep 5 /quiet
IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
::Automation Project Server
IF NOT EXIST %HistoryDir% NET USE %HistoryDir% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL

REM IF "%WORKSPACE%"=="" SET WORKSPACE=%JENKINS_HOME%\userContent

:ConfLocale
IF '%LOCALE%' == '' SET LOCALE=En
IF '%WHICH%' == '' SET WHICH=Unicode

REM ******************* Attention **********************************************************
REM IN case testing l10n including En version, orelse we don't need to change this - Steven
REM If the folder is not same as %Branch% (depents on CM), need to use the folder name instead.
REM IF /I '%LOCALE%' == 'En' SET SRCROOT=%SRCROOT%\..\Dev
IF /I '%LOCALE%' == 'En' (
  IF /I Not '%Branch%' == '' SET SRCROOT=%SRCROOT%\..\%Branch%
)
REM ****************** END *****************************************************************
:ConfCont1
::PUSHD C:%HOMEPATH%\IBM\rationalsdp
IF NOT '%RFT_DATAPOOL_NAME%'=='' SET RFT_DATAPOOL_NAME=%WORKSPACE%.\%TestCase_Prefix%%RFT_DATAPOOL_NAME%
IF '%WHICH%'=='' SET WHICH=Unicode
IF /I '%WHICH%'=='Unicode' SET TEST_UNICODE=Yes
IF /I '%WHICH%'=='Unicode' SET TEST_NONUNICODE=No
IF /I '%WHICH%'=='Release' SET TEST_NONUNICODE=Yes
IF /I '%WHICH%'=='Release' SET TEST_UNICODE=No

REM default inputs
IF '%USER_NAME%'=='' SET USER_NAME=QAMail
IF '%PASSWORD%'=='' SET PASSWORD=Password00
IF '%DOMAIN_NAME%'=='' SET DOMAIN_NAME=ACL
IF '%TEST_BUILD%'=='' SET TEST_BUILD=BUILD_149
IF '%TEST_UNICODE%'=='' SET TEST_UNICODE=No
IF '%TEST_NONUNICODE%'=='' SET TEST_NONUNICODE=No
IF '%TEST_CATEGORY%'=='' SET TEST_CATEGORY=Daily
IF '%PROJECT_TYPE%'=='' SET PROJECT_TYPE=LOCALONLY
REM IF '%tFolder%'=='' SET tFolder=Dev

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

REM SET SERVER TEST FOR L10N
IF /I '%PROJECT_TYPE%' == 'ServerAndLocal' GOTO ConfType
GOTO ConfCont2

:ConfType
SET PROJECT_TYPE=LOCALONLY
IF /I '%WHICH%'=='Unicode' (
  IF /I '%LOCALE%' == '%ACLSELocaleUni%' SET PROJECT_TYPE=SERVER
)

IF /I '%WHICH%'=='Release' (
  IF /I '%LOCALE%' == '%ACLSELocaleNonUni%' SET PROJECT_TYPE=SERVER
)

:ConfCont2
SET reportDir=%WORKSPACE%\TestReport
REM SET redir=%reportDir%\%Version%-%WHICH%
IF EXIST %reportDir%.\FinishedTest rmdir /S /Q %reportDir%.\FinishedTest 2>NUL
mkdir %ReportDir% 2>NUL

SET redir=%WORKSPACE%\..\TestHistory\%Version%.%Version_Suffix%-%LOCALE%-%WHICH%-%TEST_CATEGORY%-%PROJECT_TYPE%
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

:goon
Rem Initialize the report
Rem SET initRecord=--
Rem Echo.^<tr style="background-color:#F5F5F5"^>^
Rem          ^<td^> %initRecord% ^</td^>^
Rem		  ^<td^> %initRecord% ^</td^>^
Rem		  ^<td^> %initRecord% ^</td^>^
Rem		  ^<td^> %initRecord% ^</td^>^
Rem		  ^<td^> %initRecord% ^</td^>^
Rem		  ^<td^> %initRecord% ^</td^> > %HistoryDir%\%COMPUTERNAME% 2>NUL
Rem		  
IF /I '%verType%'=='Install' GOTO Install
IF /I '%verType%'=='Copy' GOTO GetBuild
IF /I '%verType%'=='Current' GOTO RUN
IF Exist %tFolder% GOTO RUN
rem set desroot=D:\ACL\JENKINS_HOME\jobs\DailyTest_AA_Unicode\workspace\ACLAnalytics
REM IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
REM IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
REM goto run
:GetBuild

rem SET DESROOT=%WORKSPACE%\ACLAnalytics
SET DESROOT=C:\ACL\CI_Jenkins\Analytics_binary\%sutPrefix%%WHICH%
SET XCSWITCH=/Y /E /R /I
IF NOT EXIST %SRCROOT%\%Version%\%WHICH%\ACLWin.exe %ScriptDir%\sleep 30 /quiet

IF NOT EXIST %SRCROOT%\%Version%\%WHICH%\ACLWin.exe (
   Echo.'%SRCROOT%\%Version%\%WHICH%\ACLWin.exe' -- source not existed?
   Goto Error
  )
Echo. COPY BINARY BUILD - XCOPY %SRCROOT%\%Version%\%WHICH% %DESROOT%\%WHICH%\
START "" /B /WAIT XCOPY %MISSINGDLLSSRC% %DESROOT%\%WHICH%\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\%WHICH% %DESROOT%\%WHICH%\ %XCSWITCH%>NUL
SET tFolder=%DESROOT%\%WHICH%\ACLWin.exe
IF NOT EXIST %tFolder% %ScriptDir%\sleep 10 /quiet
IF NOT EXIST %tFolder% (
   Echo.'%tFolder%' -- AUT not found?
   Goto Error
  )
GOTO Run
:Install
SET XCSWITCH_=/Y /R /I
SET LOGDIR=%tFolder%
SET DESROOT=%tFolder%\%WHICH%

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

SET InstallerQA_prefix=ACLv10%installer_LOCALE%_
SET InstallerRC_prefix=ACLAnalytics10%installer_LOCALE%_Desktop_
SET Installer_suffix=%WHICH%
SET Installer_prefix=%InstallerQA_prefix%

REM Enable This 2 for RC installer - Steven
REM SET Installer_prefix=%InstallerRC_prefix%
REM IF /I '%WHICH%'=='Release' SET Installer_suffix=nonUnicode




SET LOCAL_Installer_prefix=%Installer_prefix%

SET LOCAL_INSTALLEXE=ACLInstaller.exe

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

SET Executable=ACLWin.exe
SET tFolder=%DESROOT%\%Executable%
SET INSTALL_DIR=%DESROOT%

  IF NOT EXIST %InstallerFolder%\%INSTALLEXE% (
   Echo.Not found: %InstallerFolder%\%INSTALLEXE%
   Goto Error
  )
  IF EXIST %DESROOT%.\%VerType%\%Version%_%LOCALE% (
     IF EXIST %INSTALL_DIR%\ACLWin.exe GOTO Run
   )
   
   TASKKILL /F /T /IM %Executable% 2>NUL
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
   ECHO. %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%LOGDIR%\Installation.log\""
   %DESROOT%\%VerType%\%LOCAL_INSTALLEXE% /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%LOGDIR%\Installation.log\""
   TYPE "%LOGDIR%\Installation.log" 2>NUL
GOTO Run
:Run
::GOTO SKIP
rem TASKKILL /F /T /IM java.exe 2>NUL
rem IF EXIST %reportDir%.\FinishedTest rmdir /S /Q %reportDir%.\FinishedTest 2>NUL
rem mkdir %ReportDir% 2>NUL
IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
 CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %tFolder%}\..\ACLImex.dll /unregister > NUL
 CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %tFolder%\..\ACLImex.dll /register > NUL
)
ECHO.START "Run Jenkins Job" /B /WAIT /D"%ScriptDirTest%" JenkinsTest.bat %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%
START "Run Jenkins Job" /B /WAIT /D"%ScriptDirTest%" JenkinsTest.bat %USER_NAME% %PASSWORD% %DOMAIN_NAME% %TEST_BUILD% %TEST_UNICODE% %TEST_NONUNICODE% %TEST_CATEGORY% %PROJECT_TYPE% %tFolder%
REM goto DONE
CALL :ExportResult
:SETTIME

SET /a elapsed=0
SET /a gracetime=1*60*60
SET /a period=1*60
SET /a wait=MAX_RUNTIME+gracetime
IF %wait% GTR 7200 (
   
   ECHO. In progress..., max running time is %wait% seconds - including a grace period - %gracetime% seconds
   GOTO WAIT
   )
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
rem SET RPS=
Echo.^<tr style="background-color:#F5F5F5"^>^
          ^<td^> %ROSTYPE% ^</td^>^
		  ^<td^> %RLOCALE% ^</td^>^
		  ^<td^> %RCHARSET% ^</td^>^
		  ^<td^> %RTABLETYPE% ^</td^>^
		  ^<td^> ^<a href="%BUILD_URL%"^> %testName% ^</a^> ^</td^>^
		  ^<td^> %RTESTTYPE% ^</td^>^
		  ^<td^> %RPS% ^</td^> > %HistoryDir%\%COMPUTERNAME% 2>NUL
IF EXIST %HistoryDir% mkdir %HistoryDir%\ReportReady 2>NUL
rem SET nodeProp=%HistoryDir%\%COMPUTERNAME%.properties
rem Echo.%PROJECT_TYPE%> %nodeProp%

%ScriptDir%\sleep 5 /quiet
IF NOT EXIST %HistoryDir%\%COMPUTERNAME% %ScriptDir%\sleep 5 /quiet
GOTO :EOF
:EOF


EXIT 0
EXIT 0
