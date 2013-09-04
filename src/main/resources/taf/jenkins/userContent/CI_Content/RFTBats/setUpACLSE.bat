@ECHO OFF

Rem for standalong testing, or debugging
IF "%Version%"=="" SET Version=Build_15
IF '%Project%'=='' SET Project=Zaxxon
IF '%LOCLAE%'=='' SET LOCALE=En
IF '%ACLSELocaleUni%'=='' SET ACLSELocaleUni=Ko
IF '%ACLSELocaleNonUni%'=='' SET ACLSELocaleNonUni=Pt
Rem Setup dirs where to get server binary and l10n dlls
 IF '%LOCALE%'=='En' (
  SET tFolder=Dev
 ) ELSE (
  SET tFolder=Dev
 )
IF '%localizedDllDir%'=='' SET localizedDllDir=\\biollante02\DailyBuild\%Project%\LocalizedDLLs
IF '%SRCROOT%'=='' SET SRCROOT=\\biollante02\DailyBuild\%Project%\%tFolder%
IF '%LOCALE%'=='En' SET SRCROOT=\\biollante02\DailyBuild\%Project%

Rem set dir for scripts, such as sleep.exe etc. 
IF "%RFT_PROJECT_LOCATION%"=="" SET RFT_PROJECT_LOCATION=\\Winrunner\RFT\QA_Automation_2012_V2.0_Monako\ACL_User\Development
IF "%WORKSPACE%"=="" SET WORKSPACE=\\192.168.10.129\Automation\%Project%\userContent\CI_Content
SET ScriptDirTest=%RFT_PROJECT_LOCATION%\lib\acl\tool
SET ScriptDir=%WORKSPACE%\RFTBats

Rem Setup ACLSE access account and locations
SET aclseUser=Administrator
SET aclsePassword=Password00
IF "%unicodeACLSE%"=="" SET unicodeACLSE=\\192.168.10.95
IF "%releaseACLSE%"=="" SET releaseACLSE=\\192.168.10.98

Rem Set l10n dll folder name: local -> name, if they are different
IF /I '%ACLSELocaleUni%' == 'Zh' SET ACLSELocaleUni=SimplifiedChinese
IF /I '%ACLSELocaleUni%' == 'Ja' SET ACLSELocaleUni=Japanese
IF /I '%ACLSELocaleUni%' == 'Ko' SET ACLSELocaleUni=Korean
IF /I '%ACLSELocaleUni%' == 'Fr' SET ACLSELocaleUni=French
IF /I '%ACLSELocaleUni%' == 'Pt' SET ACLSELocaleUni=Portuguese
IF /I '%ACLSELocaleUni%' == 'De' SET ACLSELocaleUni=German
IF /I '%ACLSELocaleUni%' == 'Es' SET ACLSELocaleUni=Spanish
IF /I '%ACLSELocaleUni%' == 'Pl' SET ACLSELocaleUni=Polish

IF /I '%ACLSELocaleNonUni%' == 'Zh' SET ACLSELocaleNonUni=SimplifiedChinese
IF /I '%ACLSELocaleNonUni%' == 'Ja' SET ACLSELocaleNonUni=Japanese
IF /I '%ACLSELocaleNonUni%' == 'Ko' SET ACLSELocaleNonUni=Korean
IF /I '%ACLSELocaleNonUni%' == 'Fr' SET ACLSELocaleNonUni=French
IF /I '%ACLSELocaleNonUni%' == 'Pt' SET ACLSELocaleNonUni=Portuguese
IF /I '%ACLSELocaleNonUni%' == 'De' SET ACLSELocaleNonUni=German
IF /I '%ACLSELocaleNonUni%' == 'Es' SET ACLSELocaleNonUni=Spanish
IF /I '%ACLSELocaleNonUni%' == 'Pl' SET ACLSELocaleNonUni=Polish


Rem Check net conncetion to the binary folder, both En binary and l10n dlls
IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes
SET XCSWITCH=/Y /E /R /I
IF /I "%Version%"=="Latest" GOTO GET
IF NOT "%Version%"=="" GOTO SetUnicodeACLSE

:GET
Rem get latest available version number, if not specified
SET NUM_TOKENS=4

SET VerPrefix=Build_
SET VerPattern=%VerPrefix%1

if exist %SRCROOT%\%VerPrefix%1? set VerPattern=%VerPrefix%1?
if exist %SRCROOT%\%VerPrefix%2? set VerPattern=%VerPrefix%2?
if exist %SRCROOT%\%VerPrefix%3? set VerPattern=%VerPrefix%3?
if exist %SRCROOT%\%VerPrefix%4? set VerPattern=%VerPrefix%4?
if exist %SRCROOT%\%VerPrefix%5? set VerPattern=%VerPrefix%5?
if exist %SRCROOT%\%VerPrefix%6? set VerPattern=%VerPrefix%6?
if exist %SRCROOT%\%VerPrefix%7? set VerPattern=%VerPrefix%7?
if exist %SRCROOT%\%VerPrefix%8? set VerPattern=%VerPrefix%8?
if exist %SRCROOT%\%VerPrefix%9? set VerPattern=%VerPrefix%9?
if exist %SRCROOT%\%VerPrefix%1?? set VerPattern=%VerPrefix%1??

IF '%LatestVer%'=='' SET LatestVer=0
FOR /D %%g IN (%SRCROOT%\%VerPattrn%) DO (
    FOR /F "eol=. tokens=%NUM_TOKENS% usebackq delims=\" %%f IN ('%%g') DO (
	   ECHO f = '%%f'
	   IF /I '%LatestVer%' LEQ '%%f' (
	      SET LatestVer=%%f
		  ECHO LatestVer = '%LatestVer%'
	   )
    )   
)
SET Version=%LatestVer%
ECHO Version = '%Version%'

:SetUnicodeACLSE

SET ACLSETYPE=UNICODE
SET DESROOT=%unicodeACLSE%
IF '%WHICH%' == '' SET WHICH=Unicode
Rem build connection to ACLSE unicode server
REM IF EXIST %DESROOT%\analytic_engine NET USE %DESROOT%\analytic_engine /DELETE
IF NOT EXIST %DESROOT%\analytic_engine NET USE %DESROOT%\analytic_engine "%aclsePASSWORD%" /USER:"%aclseUser%" /P:Yes
IF EXIST %DESROOT%\analytic_engine\aclse\VERSION\%Version%\%ACLSELocaleUni% (
  Echo This ACLSE: %Version%-%WHICH%-%ACLSELocaleUni% was set by previous jenkins job, setup skiped!
  GOTO SetReleaseACLSE
  )

SET SERVICE=AXCoreDesktopConnector
Echo.Stop Unicode aclse service :SC %DESROOT% STOP %SERVICE%
SC %DESROOT% STOP %SERVICE%

IF NOT EXIST %SRCROOT%\%Version%\%ACLSETYPE%SERVER\ACLSE.exe %ScriptDir%\sleep 30 /quiet
IF NOT EXIST %SRCROOT%\%Version%\%ACLSETYPE%SERVER\ACLSE.exe (
   Echo. not exist? %SRCROOT%\%Version%\%ACLSETYPE%SERVER\ACLSE.exe
   rem pause
   Goto Error
  )

IF EXIST %DESROOT%\analytic_engine\aclse\VERSION\%Version% GOTO UpdateL10NDllUni
:UpdateServerUni
Echo. COPY BINARY ACLSE BUILD - XCOPY %SRCROOT%\%Version%\%ACLSETYPE%SERVER\
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\CONF %DESROOT%\analytic_engine\aclse\CONF\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\%ACLSETYPE%SERVER %DESROOT%\analytic_engine\aclse\ %XCSWITCH%>NUL

IF NOT EXIST %DESROOT%\analytic_engine\aclse\ACLSE.exe %ScriptDir%\sleep 10 /quiet
IF NOT EXIST %DESROOT%\analytic_engine\aclse\ACLSE.exe GOTO Error

:UpdateL10NDllUni
SET l10ndlls=%localizedDllDir%\%ACLSELocaleUni%\IronhideUnicode
IF /I '%ACLSELocaleUni%' == 'En' GOTO UpdateLabelUni
Echo. COPY L10N ACLSE DLLs - XCOPY %l10ndlls%
START "" /B /WAIT XCOPY %localizedDllDir%\CONF %DESROOT%\analytic_engine\aclse\CONF\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %l10ndlls% %DESROOT%\analytic_engine\aclse\ %XCSWITCH%>NUL

:UpdateLabelUni
IF NOT '%DESROOT%' == '' RMDIR /S /Q %DESROOT%\analytic_engine\aclse\VERSION 2>NUL
IF NOT '%DESROOT%' == '' MKDIR %DESROOT%\analytic_engine\aclse\VERSION\%Version%\%ACLSELocaleUni% 2>NUL
Echo.Start Unicode aclse service :SC %DESROOT% START %SERVICE%
SC %DESROOT% START %SERVICE%

:SetReleaseACLSE

SET ACLSETYPE=RELEASE
SET DESROOT=%releaseACLSE%
IF '%WHICH%' == '' SET WHICH=Release
REM IF EXIST %DESROOT%\analytic_engine NET USE %DESROOT%\analytic_engine /DELETE
IF NOT EXIST %DESROOT%\analytic_engine NET USE %DESROOT%\analytic_engine "%aclsePASSWORD%" /USER:"%aclseUser%" /P:Yes

IF EXIST %DESROOT%\analytic_engine\aclse\VERSION\%Version%\%ACLSELocaleNonUni% (
   Echo This ACLSE %Version%-%WHICH%-%ACLSELocaleNonUni% was set by previous jenkins job, setup skiped!
   GOTO Skip
   )
SET SERVICE=AXCoreDesktopConnector
Echo.Stop Release aclse service :SC %DESROOT% STOP %SERVICE%
SC %DESROOT% STOP %SERVICE%

IF NOT EXIST %SRCROOT%\%Version%\%ACLSETYPE%SERVER\ACLSE.exe %ScriptDir%\sleep 30 /quiet
IF NOT EXIST %SRCROOT%\%Version%\%ACLSETYPE%SERVER\ACLSE.exe (
   Goto Error
  )

IF EXIST %DESROOT%\analytic_engine\aclse\VERSION\%Version% GOTO UpdateL10NDllNonUni

:UpdateServerNonUin
Echo. COPY BINARY ACLSE BUILD - XCOPY %SRCROOT%\%Version%\%ACLSETYPE%SERVER\
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\CONF %DESROOT%\analytic_engine\aclse\CONF\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %SRCROOT%\%Version%\%ACLSETYPE%SERVER %DESROOT%\analytic_engine\aclse\ %XCSWITCH%>NUL

IF NOT EXIST %DESROOT%\analytic_engine\aclse\ACLSE.exe %ScriptDir%\sleep 10 /quiet
IF NOT EXIST %DESROOT%\analytic_engine\aclse\ACLSE.exe GOTO Error


:UpdateL10NDllNonUni
SET l10ndlls=%localizedDllDir%\%ACLSELocaleNonUni%\IronhideRelease
IF /I '%ACLSELocaleUni%' == 'En' GOTO UpdateLabelNonUni
Echo. COPY L10N ACLSE DLLs - XCOPY %l10ndlls%
START "" /B /WAIT XCOPY %localizedDllDir%\CONF %DESROOT%\analytic_engine\aclse\CONF\ %XCSWITCH%>NUL
START "" /B /WAIT XCOPY %l10ndlls% %DESROOT%\analytic_engine\aclse\ %XCSWITCH%>NUL

:UpdateLabelNonUni
IF NOT '%DESROOT%' == '' RMDIR /S /Q %DESROOT%\analytic_engine\aclse\VERSION 2>NUL
IF NOT '%DESROOT%' == '' MKDIR %DESROOT%\analytic_engine\aclse\VERSION\%Version%\%ACLSELocaleNonUni% 2>NUL
Echo.Start Release aclse service :SC %DESROOT% START %SERVICE%
SC %DESROOT% START %SERVICE%
IF NOT '%hisdir%' == '' (
  IF EXIST %hisdir%\ACLSEError rmdir /S /Q %hisdir%\ACLSEError 2>NUL
)
GOTO EOF
:Error
Echo Failed to deploye ACLSE %Version% %ACLSETYPE% to %DESROOT%\analytic_engine\aclse for testing, check it out!
IF NOT '%hisdir%' == '' (
   IF EXIST %hisdir% mkdir %hisdir%\ACLSEError 2>NUL
   )
Echo.ReStart %ACLSETYPE% aclse service :SC %DESROOT% STOP %SERVICE%
SC %DESROOT% START %SERVICE%
GOTO EOF
:Skip
rem Echo This build had been set on this machine: %Version%-%WHICH%-%LOCALE% in previous jenkins job, setup skiped!
GOTO EOF
:EOF
REM Echo. %ScriptDir%\sleep 10 /quiet
IF EXIST %ScriptDir%\sleep.exe (
  Echo. %ScriptDir%\sleep 10 /quiet
  Call %ScriptDir%\sleep.exe 10 /quiet
) ELSE (
  Echo. sleep.exe 10 /quiet
  Call sleep.exe 10 /quiet
  )
rem PAUSE
REM EXIT 0
REM EXIT 0

