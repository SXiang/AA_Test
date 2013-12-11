::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
::  Note: This script can be used to update your local Desktop with %Project%DailyBuild
::
::       Step 1: Specify scrdir and desdir accordingly, make sure you have permission to access them
::                  ex: scr = \\biollante.acl.com\Cosmos\Desktop
::                      dir = C:\WinRunner\ACL_DATA\Lang\English
::       Step 2: Save and exit
::       * In silent install mode, you may need to uninstall pre installed
::         version manually  for the first time use of this script.
::       To run this bat:
::            1: double click this script to run it
::            2: Provide Version number you want to update/set up
::            3: TBW...
::
::
::  Developed by: Steven Xiang
::  Date: July 13, 2012
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@ECHO OFF
COLOR 1F
MODE CON: COLS=75 LINES=40
SETLOCAL enabledelayedexpansion
:: Set our SRC and DES root folder
:INPUT
::SET SRCROOT=\\biollante02\DailyBuild\Silverstone\Desktop
SET TEAM_NAME=AN_TestAutomation
IF '%Project%'=='' SET Project=Frogger
::SET Project=Zaxxon
SET SILENT_INSTALL=FALSE
SET STANDALONG=TRUE
IF "%SILENT_INSTALL%"=="" SET SILENT_INSTALL=TRUE
if NOT '%5'=='' SET STANDALONG=FALSE
IF NOT '%tFolder%'=='' SET STANDALONG=FALSE
IF NOT '%1'=='' SET STANDALONG=FALSE
REM IF /I "%STANDALONG%"=="TRUE" SET SILENT_INSTALL=TRUE

REM IF '%tFolder%'=='' SET tFolder=DEV
REM IF '%tFolder%'=='' SET tFolder=
Rem SET tFolder=RC

SET SRCROOT=\\biollante02\DailyBuild\%Project%
SET _SRCROOT=%SRCROOT%
SET DOMAIN_NAME=ACL
SET USER_NAME=Your ACL User Name
SET PASSWORD=Your ACL Password
IF "%PIDKEY%"=="" SET PIDKEY=CAW1234567890
SET COMPANYNAME=ACLQA Automation
IF '%InstallIH%'=='' SET InstallIH=No
IF '%INSTALL_DIR1%'=='' SET INSTALL_DIR1=C:\ACL\Analytics11
IF '%INSTALL_DIR2%'=='' SET INSTALL_DIR2=C:\ACL\CI_Jenkins\Analytics11

IF NOT '%1'=='' SET _Version=%1
::IF NOT '%2'=='' SET VerPrefix=%2
IF /I '%tFolder%'=='Dev' SET VerPrefix=%VerPrefix%
if NOT '%5'=='' SET PASSWORD=%5
if NOT '%4'=='' SET USER_NAME=%4
if NOT '%3'=='' SET DOMAIN_NAME=%3

IF NOT '%DOMAIN_NAME%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%
IF NOT EXIST %SRCROOT% (
   IF /I NOT "%USER_NAME%"=="Your ACL User Name" NET USE %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
   )
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

:Permission
IF EXIST %SRCROOT% GOTO RELOAD
IF /I NOT "%STANDALONG%"=="TRUE" GOTO RELOAD
Echo. Do you have access to this folder
Echo.  '%SRCROOT%' ?
SET /p confirm= --(N to exit, Enter to continue,UNC path to change the source dir to):
IF /I "%confirm%"=="N" GOTO EXIT
IF NOT "%confirm%"=="" SET SRCROOT=%confirm%
SET /p un= -- %USER_NAME%?, enter your user name:
IF /I NOT "%un%"=="" SET USER_NAME=%un%
SET /p pa= -- Enter password:
IF /I NOT "%pa%"=="" SET PASSWORD=%pa%
IF NOT '%DOMAIN_NAME%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%
NET USE %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
GOTO Permission

:RELOAD

SET MISSINGDLLSSRC=\\winrunner\winrunner\SharedFiles\ACL_missing_Files
IF "%FILE_SERVER%" == "" SET FILE_SERVER=\\192.168.10.129\Automation\MavenTest
IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
IF NOT EXIST %FILE_SERVER% NET USE %FILE_SERVER% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes

Rem LOCALE=En,De,Es,Pt,Fr;Zh,Pl,Ko,Ja
IF '%LOCALE%'=='' SET LOCALE=En
SET INSTALLEXE=ACLSilentInstall.exe
SET IHINSTALLEXE=ACLIHSilentInstall.exe
SET NUM_TOKENS=4
SET INSTALL_DIR=%DESROOT%

IF '%COPY_DIR1%'=='' SET COPY_DIR1=:\ACL\Analytics11_Binary\%Project%

SET COPY_DIR=C%COPY_DIR1%
rem SET COPY_DIR=D%COPY_DIR1%
rem IF NOT EXIST %COPY_DIR% SET COPY_DIR=C%COPY_DIR1%
MKDIR %COPY_DIR% 2>NUL
IF '"%DESROOT%"'=='""' (
    IF /I '%SILENT_INSTALL%' == 'TRUE' (
	   SET DESROOT=%INSTALL_DIR1%
	) ELSE (
	       SET DESROOT=%COPY_DIR%
	 )
)

IF /I NOT '%SILENT_INSTALL%'=='TRUE' (
 IF /I NOT '%choice%'=='R' SET DESROOT=%DESROOT%\%tFolder%
 SET INSTALL_MODE=Copy Binary
 ) ELSE (
  SET INSTALL_MODE=Silent Installer
 )

SET INSTALL_DIR=%DESROOT%

SET MISSINGDLLSSRCIH=\\winrunner\winrunner\SharedFiles\Ironhide_missing_Files
SET RFTSharedFiles=\\winrunner\RFT\SharedFiles
SET ThirdPartDllSRC=%SRCROOT%\dlls
SET MISSINGDLLSDES=ReleaseSingleUser
SET MISSINGDLLSDES_U=UnicodeSingleUser
SET ACL_DATA="C:\ACL DATA\Sample Data Files"
SET VerPrefix=Build_
SET VerPattern=%VerPrefix%*
SET VerPrefixOld=9.3.0.
REM ************ SHOULD BE UPDATED ACCORDINGLLY ***********
SET Nameprefix=ACLv10
SET Nameprefix_New=ACLAnalytics105
REM ********************************************************
SET Executable=ACLWin.exe

SET ACLNonUni=Release
SET ACLUni=Unicode
SET ACLNonUni_Old=Release
SET ACLUni_Old=Unicode
SET ACLNonUni_New=Desktop_nonUnicode
SET ACLUni_New=Desktop_Unicode
SET ACLNonUniActivation=%ACLNonUni%Activation
SET ACLUniActivation=%ACLUni%Activation
SET ACLExecutable=%Executable%
SET DTUni=%ACLUni%
SET DTNonUni=%ACLNonUni%
SET IHUni=Ironhide%ACLUni_Old%
SET IHNonUni=Ironhide%ACLNonUni_Old%
SET _IHUni=%IHUni%
SET _IHNonUni=%IHNonUni%
SET IHExecutable=ACLscript.exe
  SET SupportUni=True
  SET SupportNonUni=True
SET VerType=%tFolder%Build
IF EXIST %DESROOT%\Acl.ini (
  SET VerType=%tFolder%Copy
  )

IF NOT EXIST %DESROOT% (
  IF NOT '%SILENT_INSTALL%'=='TRUE' GOTO CONTINUE
  IF EXIST "%INSTALL_DIR1%\Windows NT" SET DESROOT="%INSTALL_DIR1%"
  IF EXIST "%INSTALL_DIR2%\Windows NT" SET DESROOT="%INSTALL_DIR2%"
  IF EXIST "%INSTALL_DIR1%\Windows NT" SET INSTALL_DIR=%INSTALL_DIR1%
  IF EXIST "%INSTALL_DIR2%\Windows NT" SET INSTALL_DIR=%INSTALL_DIR2%
)
  IF EXIST %ACL_DATA% (
      SET startDir=%ACL_DATA%
	) ELSE (
	  SET startDir=%DESROOT%
	)
IF /I '%DESROOT%'=='"%INSTALL_DIR1%"' GOTO INSTALLER
IF /I '%DESROOT%'=='"%INSTALL_DIR2%"' GOTO INSTALLER
IF /I '%SILENT_INSTALL%'=='TRUE' GOTO INSTALLER

GOTO CONTINUE
:INSTALLER
SET IHUni=Ironhide%ACLUni_Old%
SET IHNonUni=Ironhide%ACLNonUni_Old%
SET _IHUni=%IHUni%
SET _IHNonUni=%IHNonUni%
  IF /I '%LOCALE%'=='EN' SET LOCALE=En
  rem IF /I '%LOCALE%'=='EN' SET tFolder=DEV
  IF /I NOT '%LOCALE%'=='EN' SET tFolder=DEV_localized_release
  REM SET SRCROOT=\\biollante02\DailyBuild\%Project%\%tFolder%
  SET SRCROOT=\\biollante02\DailyBuild\%Project%
  SET _SRCROOT_installer=%SRCROOT%
  SET SRCROOT_installer=%_SRCROOT_installer%
  REM IF NOT '%LOCALE%'=='En' SET SRCROOT_installer=%_SRCROOT_installer%\LocalizedInstallers
  rem IF '%LOCALE%'=='Pl' SET SRCROOT_installer=%SRCROOT_installer%\Polish
  IF /I '%SILENT_INSTALL%'=='FALSE' GOTO CONTINUE
  SET VerType=Installer
  SET SRCROOT=%SRCROOT_installer%
IF /I '%LOCALE%'=='En' (
  SET ACLNonUni=%NamePrefix%%LOCALE%_%ACLNonUni_Old%
  SET ACLUni=%NamePrefix%%LOCALE%_%ACLUni_Old%
  ) ELSE (
  SET ACLNonUni=%NamePrefix_New%_%ACLNonUni_New%
  SET ACLUni=%NamePrefix_New%_%ACLUni_New%
  )

  SET IHNonUni=%NamePrefix%%LOCALE%_%IHNonUni%
  SET IHUni=%NamePrefix%%LOCALE%_%IHUni%

Rem En,De,Es,Pt,Fr,Ch,Ko,Jp,Pl
IF /I '%LOCALE%'=='ZH' SET LOCALE=Ch
IF /I '%LOCALE%'=='JA' SET LOCALE=Jp

SET SupportNonUni=True
SET SupportUni=True
IF /I '%LOCALE%'=='Jp' SET SupportNonUni=False
IF /I '%LOCALE%'=='Ch' SET SupportNonUni=False
IF /I '%LOCALE%'=='Ko' SET SupportNonUni=False
IF /I '%LOCALE%'=='Pl' SET SupportUni=False


SET langHexID=!%LOCALE%%HexID%!
echo Language Hex ID = '%langHexID%'

:CONTINUE

SET Reg=regsvr32 /s
::SET UnReg=regsvr32 /u /s
SET Done=
SET XCSWITCH=/Y /E /R /I
SET XCSWITCH_=/Y /R /I

:LatestBuild
REM EQU - equal
REM NEQ - not equal
REM LSS - less than
REM LEQ - less than or equal
REM GTR - greater than
REM GEQ - greater than or equal
if '%LatestVer%'=='' SET LatestVer=%VerPrefix%0

set dir_suffix=Installer
FOR /D  %%g IN (%SRCROOT%\%VerPattern%) DO (
    FOR /F "eol=. tokens=%NUM_TOKENS% usebackq delims=\" %%f IN ('%%g') DO (
	   SET tempLatestVer=%%f
	   SET /a tempLatestVer_num=!tempLatestVer:~6!
	   SET /a LatestVer_num=!LatestVer:~6!

	   SET DUBUGTHIS=LatestVer_num LEQ tempLatestVer_num
	   IF !LatestVer_num! LEQ !tempLatestVer_num! (
	      IF /I '%SILENT_INSTALL%'=='TRUE' (
		    set dir_suffix=Installer
rem 		    IF /I Not '%LOCALE%'=='En' (
rem 			   set sub_Type=
rem 			   set sub_dir=%%f
rem 			   IF NOT EXIST %SRCROOT%\%%f\%dir_suffix% Call :getSubDir
rem 			) ELSE (
			   set sub_Type=\!dir_suffix!
		       IF EXIST %SRCROOT%\!tempLatestVer!\!dir_suffix!\%ACLUni%.exe SET LatestVer=!tempLatestVer!
			   IF EXIST %SRCROOT%\!tempLatestVer!\!dir_suffix!\%ACLNonUni%.exe SET LatestVer=!tempLatestVer!
			REM )
		  ) ELSE (
	        SET LatestVer=!tempLatestVer!
		  )
	   )
    )
)

SET Version=%_Version%
IF '%Version%'=='' SET Version=%LatestVer%
echo %LatestVer%, %Version%

IF /I '%Version%'=='Latest' SET Version=%LatestVer%
rem SET Version=%Version:~0,9%

GOTO MAINMENU

:getSubDir
set sub_VerPattern=*

set /a sub_Tokens = num_Tokens+1
set dir_suffix=0
echo (%SRCROOT%\%sub_dir%\%sub_VerPattern%)
FOR /D  %%i IN (%SRCROOT%\%sub_dir%\%sub_VerPattern%) DO (
    FOR /F "eol=. tokens=%sub_TOKENS% usebackq delims=\" %%h IN ('%%i') DO (
	   SET /a tempLatestVer=%%h
	   SET /a LatestVer_num=%dir_suffix%
	   rem IF /I '%dir_suffix%' LEQ '%%h' (
	   IF LatestVer_num LEQ tempLatestVer_num (
		    IF EXIST %SRCROOT%\%sub_dir%\%%h\%ACLUni%.exe SET LatestVer=%sub_dir%\%%h
			IF EXIST %SRCROOT%\%sub_dir%\%%h\%ACLNonUni%.exe SET LatestVer=%sub_dir%\%%h
		)
	)
)

Goto :EOF
:MAINMENU choose option from menu
SET choice=
SET aclNonUniExist=
SET aclUniExist=
SET desExist=
SET srcExist=
SET verExist=


IF /I '%_Version%'=='' (
  IF /I '%Version%' LSS '%VerPrefix%' SET Version=%VerPrefix%.%Version%
)
::IF /I '%Version%' LSS '%VerPrefix%' SETX Version %VerPrefix%.%Version%
IF /I '%Version%'=='%LatestVer%' (
      SET isLatest= - Latest build
)ELSE (
      SET isLatest= - Old build
)

IF NOT EXIST %DESROOT% SET desExist=!Not Found
IF NOT EXIST %SRCROOT% SET srcExist=!Not Found
IF NOT EXIST %SRCROOT%\%Version% SET verExist=!Not Found
IF /I '%VerType%'=='Installer' (
 IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLNonUni%_%LOCALE% (
   SET aclNonUniExist=!Not Installed
   IF EXIST %SRCROOT%\%Version%%sub_Type%\%ACLNonUni%.exe SET aclNonUniExist=Ready
   IF NOT EXIST %SRCROOT%\%Version%%sub_Type%\%ACLNonUni%.exe SET aclNonUniExist=!Installer Not Ready Yet
   )
 IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLUni%_%LOCALE% (
   SET aclUniExist=!Not Installed
   IF EXIST %SRCROOT%\%Version%%sub_Type%\%ACLUni%.exe SET aclUniExist=Ready
   IF NOT EXIST %SRCROOT%\%Version%%sub_Type%\%ACLUni%.exe SET aclUniExist=!Installer Not Ready Yet
   )
) ELSE (

 IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% (
   SET aclNonUniExist=!Not Installed
   IF EXIST %SRCROOT%.\%Version%.\%ACLNonUni%\%Executable% SET aclNonUniExist=Ready
   IF NOT EXIST %SRCROOT%\%Version%\%ACLNonUni%\%Executable% SET aclNonUniExist=!Binary Not Ready Yet
 
   )
 IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% (
   SET aclUniExist=!Not Installed
   IF EXIST %SRCROOT%.\%Version%.\%ACLUni%\%Executable% SET aclUniExist=Ready
   IF NOT EXIST %SRCROOT%\%Version%\%ACLUni%\%Executable% SET aclUniExist=!Binary Not Ready Yet
   )
)

IF NOT '%TEST_BUILD%'=='' (
      SET Done=true
      CALL :RUNTEST %Done%
	  GOTO EOF
   )

CLS
ECHO.
ECHO.
ECHO.
ECHO.**********************  Setup %Project% daily build ***********************
ECHO.**With Silent Installer mode,You may need to uninstall pre installed   *
ECHO.* version manually  for the first time use of this script              *
rem ECHO.**To install PL product, you may need to change the system locale      *
ECHO.**You may change all the default values by editing this script         *
ECHO.**Developed by: Steven_Xiang@ACL.COM                                   *
ECHO.* * * * * * * * * * * * * * *  * * * * * * * * * * * * * * * * * * * * *
ECHO.*
ECHO.*    List Available %Project% Daily builds..............L              
ECHO.*         [%SRCROOT%]        %srcExist%
ECHO.*         [Latest Build -  %LatestVer%]
ECHO.*
ECHO.*    Change Setup Mode...............................M              
ECHO.*         [Current Mode: %INSTALL_MODE%]
ECHO.*
ECHO.*    Change DESROOT..................................D              
ECHO.*         [%DESROOT%]        %desExist%
ECHO.*
ECHO.*    Change Build....................................V              
ECHO.*         [%Version%%isLatest%]   %verExist%
ECHO.*
ECHO.*    Change Locale .................................CL
ECHO.*         [%LOCALE%]   En,De,Es,Pt,Fr,Ch,Ko,Jp,Pl
ECHO.*
Rem IF /I '%VerType%'=='DevBuild' ECHO.*    Get This  Build.................................G
REM IF Not %VerType%'=='DevBuild' ECHO.*    Install Unicode Build...............................GU
REM IF Not %VerType%'=='DevBuild' ECHO.*    Install NonUnicode Build............................GN
REM IF /I '%VerType%'=='DevBuild' ECHO.*         [%Version%%isLatest%]   %verExist%
ECHO.*

IF /I '%SupportNonUni%' == 'True' (
   IF "%aclNonUniExist%"=="" ECHO.*    Run  %Version% - NonUnicode....................RN
   IF /I "%aclNonUniExist%"=="Ready" (
       ECHO.*    Install  %Version% - NonUni....................GN
	   IF /I "%LOCALE%"=="En" ECHO.*    Install  %Version% - NonUni[with Ironhide]....GNI
   ) Else (
      IF NOT "%aclNonUniExist%"=="" ECHO.*    %Version% - NonUnicode[%aclNonUniExist%]
   )
   rem		 ECHO.*    Open(CMD)  ACLScript - NonUnicode..............ON
   rem         ECHO.*         [%Version%%isLatest%]   %aclNonUniExist%
   ECHO.*
) ELSE (
   rem ECHO.*    NonUnicode[%Locale%] - There is no such build or not supported........
)
IF /I '%SupportUni%' == 'True' (
   IF "%aclUniExist%"=="" ECHO.*    Run  %Version% - Unicode.......................RU
   IF /I "%aclUniExist%"=="Ready" (
      ECHO.*    Install  %Version% - Uni.......................GU
	  IF /I "%LOCALE%"=="En" ECHO.*    Install  %Version% - Uni[with Ironhide].......GUI
   ) Else (
      IF NOT "%aclUniExist%"=="" ECHO.*    %Version% - Unicode [%aclUniExist%]
   )
   rem ECHO.*    Open(CMD)  ACLScript - NonUnicode..............OU
   rem ECHO.*         [%Version%%isLatest%]   %aclUniExist%
   ECHO.*
) ELSE (
   rem ECHO.*    Unicode[%Locale%] - There is no such build %SupportUni%   .........
)
ECHO.*    Refresh ........................................R
ECHO.*
ECHO.*    Quit (no further action)........................Q
ECHO.*
ECHO.*
ECHO.************************************************************************
ECHO.
SET Message=

if NOT '%1'=='' (
  SET Done=true
  SET choice=%1
)ELSE (
  SET /p choice=    Type your option, then press Enter:
)
IF /I '%choice%'=='GU' (
  SET TEST_UNICODE=Yes
  SET TEST_NONUNICODE=No
  SET ENCODE=Unicode
  SET InstallIH=No
)
IF /I '%choice%'=='GN' (
  SET TEST_NONUNICODE=Yes
  SET TEST_UNICODE=No
  SET ENCODE=NonUnicode
  SET InstallIH=No
  )
IF /I '%choice%'=='GUI' (
  SET TEST_UNICODE=Yes
  SET TEST_NONUNICODE=No
  SET ENCODE=Unicode
  IF /I "%LOCALE%"=="En" SET InstallIH=Yes
)
IF /I '%choice%'=='GNI' (
  SET TEST_NONUNICODE=Yes
  SET TEST_UNICODE=No
  SET ENCODE=NonUnicode
  IF /I "%LOCALE%"=="En" SET InstallIH=Yes
  )
IF /I '%choice%'=='CL' GOTO ChangeLocale
IF /I '%choice%'=='G' GOTO GETBUILD
IF /I '%choice%'=='GU' GOTO GETBUILD
IF /I '%choice%'=='GN' GOTO GETBUILD
IF /I '%choice%'=='GUI' GOTO GETBUILD
IF /I '%choice%'=='GNI' GOTO GETBUILD
IF /I '%choice%'=='d' GOTO DESDIR
IF /I '%choice%'=='Q' GOTO Quit
IF /I '%choice%'=='V' GOTO Version
IF /I '%choice%'=='L' GOTO List
IF /I '%choice%'=='RN' GOTO RunNUni
IF /I '%choice%'=='RU' GOTO RunUni
IF /I '%choice%'=='ON' GOTO OpenNUniCMD
IF /I '%choice%'=='OU' GOTO OpenUniCMD
IF /I '%choice%'=='R' GOTO Permission
IF /I '%choice%'=='M' GOTO Mode
GOTO MAINMENU

:LIST
CLS
ECHO.
ECHO.
ECHO.
ECHO.*********************** Avaliable Daily Build ************************
ECHO.*
DIR /D /O-D %SRCROOT%
ECHO.*
ECHO.*****************************************************************************
pause
GOTO MAINMENU



:DESDIR
SET /p choice=  Enter new DESROOT[%DESROOT%]:
IF NOT '%choice%'=='' SET DESROOT=%choice%
ECHO.* Your input: DESROOT = %DESROOT%
:Mode
SET /p choice=  Change Mode?[%INSTALL_MODE%,'Y' to change]:
IF /I '%choice%'=='Y' (
  IF /I "%SILENT_INSTALL%"=="TRUE" SET SILENT_INSTALL=FALSE
  IF /I NOT "%SILENT_INSTALL%"=="TRUE" SET SILENT_INSTALL=TRUE
  SET DESROOT=
  )
Goto RELOAD
:Version
SET /p choice=  Enter new Version[%Verprefix%]:
IF /I '%choice%'=='Latest' (
    SET choice=%LatestVer%
)
IF NOT '%choice%'=='' SET Version=%choice%
::IF NOT '%choice%'=='' SETX Version %choice%
IF /I %Version% LSS %VerPrefix% SET Version=%VerPrefix%%Version%
::IF /I %Version% LSS %VerPrefix% SETX Version %VerPrefix%.%Version%
ECHO.* Your input: Version = %Version%
GOTO MAINMENU

:ChangeLocale
SET /p choice=  Enter language option:
IF NOT '%choice%'=='' SET LOCALE=%choice%
rem GOTO MAINMENU
GOTO INSTALLER


:GETBUILD
   SET ACLscriptdir=%FILE_SERVER%\%Team_Name%\taf\tool
   SET ACLshortcutName="ACL Analytics 11"
   SET ACLdescription=%ACLExecutable%-%Version%[ACLQA_Automation]


   SET IHscriptdir=%FILE_SERVER%\%Team_Name%\taf\tool
   SET IHshortcutName=ACL Ironhide 11
   SET IHtargetPath=C:\WINDOWS\system32\cmd.exe
   IF NOT EXIST %IHtargetPath% SET IHtargetPath=C:\WINDOWS\syswow64\cmd.exe
   SET IHDESROOT=%DESROOT%\Ironhide
   SET IHiconLocation=%IHDESROOT%\%IHExecutable%
   SET IHdescription=%IHExecutable%-%Version%[ACLQA_Automation]
   SET cmdOP=/Q /K color 17
   SET IHcmdOP=ACLSE -V
   SET IHhotKey=
   IF /I '%TEST_UNICODE%'=='Yes' SET cmdOP=/U %cmdOP%
   IF /I '%TEST_NONUNICODE%'=='Yes' SET cmdOP=/A %cmdOP%

IF /I NOT '%RETEST%'=='Yes' (
	IF EXIST %TEST_FLAG% GOTO GETBUILDDONE
	)
IF NOT '%1'=='' SET Done=%1
IF NOT EXIST %SRCROOT%.\%Version% ECHO. CAN'T FIND '%SRCROOT%.\%Version%'
IF NOT EXIST %SRCROOT%.\%Version%  GOTO GETBUILDDONE
IF /I '%VerType%'=='DevCopy' (
   RMDIR /S /Q %DESROOT%.\%VerType%
   IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLUni% %DESROOT%.\ %XCSWITCH%
   ) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLNonUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLNonUni% %DESROOT%.\ %XCSWITCH%
   )
) ELSE IF /I '%VerType%'=='Installer' (

REM   IF NOT EXIST %DESROOT%\%VerType%\%INSTALLEXE% (
rem     XCOPY %RFTSharedFiles%\%INSTALLEXE% %DESROOT%\%VerType%\ %XCSWITCH_%
REM   )


   IF EXIST %DESROOT%\%Executable% (
      TASKKILL /F /T /IM %Executable% 2>NUL
	  TASKKILL /F /T /IM %INSTALLEXE% 2>NUL

	  %DESROOT%\%VerType%\%INSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_DIR%\%VerType%\Uninstallation.log\"" 2>NUL
	  rem TYPE %INSTALL_DIR%\%VerType%\Uninstallation.log
	  rem echo. %RFT_PROJECT_LOCATION%
   )
   IF EXIST %IHDESROOT%\%IHExecutable% (
      TASKKILL /F /T /IM %IHExecutable% 2>NUL
	  TASKKILL /F /T /IM %IHINSTALLEXE% 2>NUL

	rem  IF /I '%LOCALE%' == 'En' (
	   %DESROOT%\%VerType%\%IHINSTALLEXE% /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_DIR%\%VerType%\IH_Uninstallation.log\"" 2>NUL
	rem   )
   )
    RMDIR /S /Q %DESROOT%.\%VerType% 2>NUL
	MKDIR %DESROOT%.\%VerType%\%Version% 2>NUL
    DEL "%userprofile%\Desktop\%IHshortcutName% Current.lnk" 2>NUL
   
   IF /I '%TEST_UNICODE%'=='Yes' (
      ECHO. F | XCOPY %SRCROOT%\%Version%%sub_Type%\%ACLUni%.exe %DESROOT%\%VerType%\%INSTALLEXE% %XCSWITCH_%
	  IF /I '%LOCALE%' == 'En' (
	    ECHO. F | XCOPY %SRCROOT%\%Version%%sub_Type%\%IHUni%.exe %DESROOT%\%VerType%\%IHINSTALLEXE% %XCSWITCH_%
		)
      MKDIR %DESROOT%\%VerType%\%Version%\%ACLUni%_%LOCALE% 2>NUL
	  MKDIR %DESROOT%\%VerType%_History\%Version%\%ACLUni%_%LOCALE% 2>NUL
      REM RMDIR /S /Q %DESROOT%\%VerType%\%Version%.\%ACLNonUni%
	) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
	  ECHO. F | XCOPY %SRCROOT%\%Version%%sub_Type%\%ACLNonUni%.exe %DESROOT%\%VerType%\%INSTALLEXE% %XCSWITCH_%
	  IF /I '%LOCALE%' == 'En' (
	    ECHO. F | XCOPY %SRCROOT%\%Version%%sub_Type%\%IHNonUni%.exe %DESROOT%\%VerType%\%IHINSTALLEXE% %XCSWITCH_%
		)

      MKDIR %DESROOT%\%VerType%\%Version%\%ACLNonUni%_%LOCALE% 2>NUL
	  MKDIR %DESROOT%\%VerType%_History\%Version%\%ACLNonUni%_%LOCALE% 2>NUL
	  REM RMDIR /S /Q %DESROOT%\%VerType%\%Version%\%ACLUni%
   )

   IF NOT EXIST %DESROOT%\%Executable% (
	  TASKKILL /F /T /IM %INSTALLEXE% 2>NUL

      %DESROOT%\%VerType%\%INSTALLEXE% /s /a /s /L%langHexID% /v"/qb /passive /quiet PIDKEY=%PIDKEY% COMPANYNAME=\"%COMPANYNAME%\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%INSTALL_DIR%\%VerType%\Installation.log\"" 2>NUL
	  rem TYPE %INSTALL_DIR%\%VerType%\Installation.log
      IF NOT EXIST %DESROOT%\%Executable% (
         IF /I '%TEST_UNICODE%'=='Yes' RMDIR /S /Q %DESROOT%\%VerType%\%Version%.\%ACLNonUni%_%LOCALE% 2>NUL
	     IF /I '%TEST_NONUNICODE%'=='Yes' RMDIR /S /Q %DESROOT%\%VerType%\%Version%\%ACLUni%_%LOCALE% 2>NUL
      )
    )

   IF /I '%InstallIH%' == 'No' GOTO GETBUILDCONTINUE
   IF Not EXIST %IHDESROOT%\%IHExecutable% (
	  TASKKILL /F /T /IM %IHINSTALLEXE% 2>NUL
      IF /I '%LOCALE%' == 'En' (
	    %DESROOT%\%VerType%\%IHINSTALLEXE% /s /a /s /v"/qb /passive /quiet PIDKEY=NoKey COMPANYNAME=\"%COMPANYNAME%\" INSTALLDIR=\"%INSTALL_DIR%\Ironhide\" /l* \"%INSTALL_DIR%\%VerType%\IHInstallation.log\"" 2>NUL
	    Call Cscript %ihscriptdir%\createShortcut.vbs "%IHshortcutName% Current" %IHtargetPath% "%cmdOP% && %IHcmdOP%" "%IHhotKey%" %IHiconLocation% %IHDESROOT% %IHdescription%
		Rem IF /I '%TEST_NOTUNICODE%'=='Yes' Call Cscript %ihscriptdir%\createShortcut.vbs "%IHshortcutName% Release" %IHtargetPath% "%cmdOP% && %IHcmdOP%" "%IHhotKey%" %IHiconLocation% %IHDESROOT% %IHdescription%
	   )
	  rem Call Cscript %ihscriptdir%\createShortcut.vbs %IHshortcutName% "%IHtargetPath% %IHcmdOP% && ACLSE -V" %IHiconLocation% %IHDESROOT% %IHdescription%
    )
  ) ELSE (
   Rem XCOPY %ThirdPartDllSRC%  %MISSINGDLLSSRC%.\ %XCSWITCH%
   Rem XCOPY %SRCROOT%.\%Version% %DESROOT%.\%Version%.\ %XCSWITCH%
   Rem XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\Release.\ %XCSWITCH%
   Rem XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\Unicode.\ %XCSWITCH%

   
   IF /I '%TEST_UNICODE%'=='Yes' (      
      IF NOT EXIST %DESROOT%.\%Version%.\%ACLUni%.\%ACLExecutable% (
	     XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\%ACLUni%.\ %XCSWITCH%
         XCOPY %SRCROOT%.\%Version%.\%ACLUni% %DESROOT%.\%Version%.\%ACLUni%\ %XCSWITCH%
		 Call Cscript %ihscriptdir%\createShortcut.vbs %ACLshortcutName%-Unicode %DESROOT%.\%Version%.\%ACLUni%.\%ACLExecutable% "" "%ACLhotKey%" %DESROOT%.\%Version%.\%ACLUni%.\%ACLExecutable% %DESROOT%.\%Version%.\%ACLUni% %ACLdescription%-Unicode
	     )
      IF /I '%InstallIH%'=='No' GOTO GETBUILDDONE
	  IF NOT EXIST %DESROOT%.\%Version%.\%IHUni%.\%IHExecutable% (
	     XCOPY %MISSINGDLLSSRCIH%  %DESROOT%.\%Version%.\%IHUni%.\ %XCSWITCH%
	     XCOPY %SRCROOT%.\%Version%.\%_IHUni% %DESROOT%.\%Version%.\%IHUni%\ %XCSWITCH%
		 Call Cscript %ihscriptdir%\createShortcut.vbs "%IHshortcutName%-Unicode" %IHtargetPath% "%cmdOP%" "%IHhotKey%" %DESROOT%.\%Version%.\%IHUni%.\%IHExecutable% %DESROOT%.\%Version%.\%IHUni% %IHdescription%-Unicode
		 )
   )

   IF /I '%TEST_NONUNICODE%'=='Yes' (
      IF NOT EXIST %DESROOT%.\%Version%.\%ACLNonUni%.\%ACLExecutable% (
          XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\%ACLNonUni%.\ %XCSWITCH%
          XCOPY %SRCROOT%.\%Version%.\%ACLNonUni% %DESROOT%.\%Version%.\%ACLNonUni%\ %XCSWITCH%
		  Call Cscript %ihscriptdir%\createShortcut.vbs %ACLshortcutName%-Release %DESROOT%.\%Version%.\%ACLNonUni%.\%ACLExecutable% "" "%ACLhotKey%" %DESROOT%.\%Version%.\%ACLNonUni%.\%ACLExecutable% %DESROOT%.\%Version%.\%ACLNonUni% %ACLdescription%-Release
	      )
      IF /I '%InstallIH%'=='No' GOTO GETBUILDCONTINUE
	  IF NOT EXIST %DESROOT%.\%Version%.\%IHNonUni%.\%IHExecutable% (
	      XCOPY %MISSINGDLLSSRCIH%  %DESROOT%.\%Version%.\%IHNonUni%.\ %XCSWITCH%
	      XCOPY %SRCROOT%.\%Version%.\%_IHNonUni% %DESROOT%.\%Version%.\%IHNonUni%\ %XCSWITCH%
		  Call Cscript %ihscriptdir%\createShortcut.vbs "%IHshortcutName%-Release" %IHtargetPath% "%cmdOP%" "%IHhotKey%" %DESROOT%.\%Version%.\%IHNonUni%.\%IHExecutable% %DESROOT%.\%Version%.\%IHNonUni% %IHdescription%-Release
		  )
   )

)
::RENAME %DESROOT%.\%Version%.\%MISSINGDLLSDES%.\%Executable% ACLWin_%Version%.exe
::RENAME %DESROOT%.\%Version%.\%MISSINGDLLSDES_U%.\%Executable% ACLWin_%Version%.exe

::XCOPY %DESROOT%.\%Version% %DESROOT%.\Latest\ %XCSWITCH%
::RMDIR /S /Q %DESROOT%.\(%Version%-10).\
:GETBUILDCONTINUE
IF EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% (
   SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
   %Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%ACLUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%ACLUni%\ACLImex.dll /register 2>NUL
   )
)
IF EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% (
   SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
   %Reg% %DESROOT%\%Version%\%ACLNonUni%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%ACLNonUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%ACLNonUni%\ACLImex.dll /register 2>NUL
   )
)
ECHO.%Version%> %DESROOT%\..\Latest_Installed_Build.TXT
:GETBUILDDONE
IF '%Done%'=='true' GOTO :EOF
GOTO JOBDONE

:JOBDONE
ECHO. Xcopy done!
rem PAUSE
GOTO MAINMENU

:RUNNUni
IF NOT '%VerType%'=='Installer' (
  REM %UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  %Reg% %DESROOT%\%Version%\%ACLNonUni%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%ACLNonUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%ACLNonUni%\ACLImex.dll /register 2>NUL
   )

  ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLNonUni%\%Executable%
  Start "Desktop - %Version%\%ACLNonUni%\%Executable%" /D%startDir%\ /MAX /SEPARATE /B "%INSTALL_DIR%.\%Version%.\%ACLNonUni%.\%Executable%"
  pause
) ELSE (
    REM %UnReg% %DESROOT%\ACLServer.dll
    %Reg% %DESROOT%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%ACLNonUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%ACLNonUni%\ACLImex.dll /register 2>NUL
   )
    ECHO. Start ACLWin at: %DESROOT%\%Executable%
	echo. Start "Desktop - %Version%\%ACLNonUni%\%Executable%" /D%startDir%\ /MAX /SEPARATE /B %DESROOT%.\%Executable%
    IF '"%aclNonUniExist%"'=='""' Start "Desktop - %Version%\%ACLNonUni%\%Executable%" /D%startDir%\ /MAX /SEPARATE /B "%INSTALL_DIR%\%Executable%"
	pause
  )
GOTO MAINMENU

:RUNUni
IF NOT '%VerType%'=='Installer' (
  ::%UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  %Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%ACLUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%ACLUni%\ACLImex.dll /register 2>NUL
   )
  ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLUni%\%Executable%
  Start "Desktop - %Version%\%ACLUni%\%Executable%" /D%startDir%\ /MAX /SEPARATE /B "%INSTALL_DIR%.\%Version%.\%ACLUni%.\%Executable%"
  ) ELSE (
    ::%UnReg% %DESROOT%\ACLServer.dll
  %Reg% %DESROOT%\ACLServer.dll  2>NUL
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%ACLUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%ACLUni%\ACLImex.dll /register 2>NUL
   )
  ECHO. Start ACLWin at: %DESROOT%\%Executable%
  IF '"%aclUniExist%"'=='""' Start "Desktop - %Version%\%ACLUni%\%Executable%" /D%startDir%\ /MAX /SEPARATE /B "%INSTALL_DIR%\%Executable%"

  )
GOTO MAINMENU
:OpenNUniCMD
IF NOT '%VerType%'=='Installer' (
  IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%IHNonUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%IHNonUni%\ACLImex.dll /register 2>NUL
   )
  Start "Ironhide dir" /D%DESROOT%\%Version%\%IHNonUni% /MAX /SEPARATE "CMD %cmdOP% && %IHcmdOP%"
) ELSE (
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %IHDESROOT%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %IHDESROOT%\ACLImex.dll /register 2>NUL
   )
    ECHO. Open command line at: %DESROOT%\Irnohide
    IF '"%aclNonUniExist%"'=='""' Start "Ironhide dir" /D%IHDESROOT%\ /MAX /SEPARATE "CMD %cmdOP% && %IHcmdOP%"
  )
GOTO MAINMENU

:OpenUniCMD
IF NOT '%VerType%'=='Installer' (
  IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %DESROOT%\%Version%\%IHUni%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %DESROOT%\%Version%\%IHUni%\ACLImex.dll /register 2>NUL
   )
  Start "Ironhide dir" /D%DESROOT%\%Version%\%IHUni% /MAX /SEPARATE "CMD %cmdOP% && %IHcmdOP%"
) ELSE (
   IF EXIST "C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe" (
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe %IHDESROOT%\ACLImex.dll /unregister 2>NUL
      CALL C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe  %IHDESROOT%\ACLImex.dll /register 2>NUL
   )
    ECHO. Open command line at: %DESROOT%\Irnohide
    IF '"%aclUniExist%"'=='""' Start "Ironhide dir" /D%IHDESROOT%\ /MAX /SEPARATE "CMD %cmdOP% && %IHcmdOP%"
  )
GOTO MAINMENU

:QUIT no further action
GOTO CLEANUP

:E_FOLDER folder not found
ECHO. Finished with some errors %errorlevel%
GOTO CLEANUP

:CLEANUP
SET SRCROOT=
SET DESROOT=
SET MISSINGDLLSSRC=
SET MISSINGDLLSDES=
SET MISSINGDLLSDES_U=
::SET ThirdPartDllSRC=
SET Version=
SET Verprefix=
SET LatestVer=
SET isLatest=
SET XCSWITCH=
SET desExist=
SET verExist=
SET srcExist=
SET choice=
SET ACLNonUni=
SET ACLUni=
SET aclUniExist=
SET aclNonUniExist=
SET Reg=
SET UnReg=
SET Done=
GOTO EOF

:RUNTEST
IF '%RUN_PARAMETER%'=='' GOTO EOF
SET RUN_FLAG=%DESROOT%\%Version%\AUTOMATION_IS_RUNING
IF NOT '%1'=='' SET Done=%1
::ECHO TEST_CATEGORY=%TEST_CATEGORY%  _testCategory=%_testCategory%
::SET TEST_FLAG=%_AUT%.\..\..\Auto_%_testCategory%Test_Log[%ENCODE%].TXT


IF /I '%TEST_UNICODE%'=='Yes' SET ENCODE=Unicode
IF /I '%TEST_NONUNICODE%'=='Yes' SET ENCODE=NonUnicode
Rem ******** for cmd exec ******
IF /I '%tExecutable%'=='%IHExecutable%' (
      SET ACLUni=%IHUni%
	  SET ACLNonUni=%IHNonUni%
	  SET Executable=%IHExecutable%
)

Rem ************************
IF NOT '%VerType%'=='DevBuild' (
  SET UNI_PATH=%DESROOT%\%Executable%
  SET NONUNI_PATH=%DESROOT%\%Executable%
  SET TEST_FLAG=%DESROOT%\%VerType%_History\%Version%\Auto_%TEST_CATEGORY%Test[%PROJECT_TYPE%]_Log[%ENCODE%].TXT

  ::SET VerType=DevCopy
  ::SET VerType=Installer
  IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%_History\%Version%\%ACLUni% 2>NUL
      IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLUni% CALL :GETBUILD %Done%
	  )
  IF /I '%TEST_NONUNICODE%'=='Yes' (
     MKDIR %DESROOT%.\%VerType%_History\%Version%\%ACLNonUni% 2>NUL
     IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLNonUni% CALL :GETBUILD %Done%
	 )
) ELSE (
    SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
    SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
	SET TEST_FLAG=%DESROOT%\%Version%\Auto_%TEST_CATEGORY%%tExecutable%Test[%PROJECT_TYPE%%ACL_PROJECT%]_Log[%ENCODE%%ACL_SCRIPT%].TXT
    IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% CALL :GETBUILD %Done%
    IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% CALL :GETBUILD %Done%
)


:RUN
IF /I NOT '%RETEST%'=='Yes' (
	IF EXIST %TEST_FLAG% GOTO :EOF
	)

IF /I '%TEST_UNICODE%'=='Yes' (
    %Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll  2>NUL
    SET RUN_COMMAND="%UNI_PATH%" %RUN_PARAMETER%
) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
    %Reg% %DESROOT%\%Version%\%ACLNonUni%\ACLServer.dll  2>NUL
    SET RUN_COMMAND="%NONUNI_PATH%" %RUN_PARAMETER%
) ELSE (
  ECHO. No test performed on %Version%
  GOTO EXIT
)
ECHO.%Version%> %DESROOT%\..\Latest_Tested_Build.TXT
IF /I '%tExecutable%'=='' (
   ECHO "Run test..." 	/D"%RFT_PROJECT_LOCATION%\lib\acl\tool" /WAIT /B doTest.bat
   START "Run test..." /D%RFT_PROJECT_LOCATION%\lib\acl\tool /WAIT /B doTest.bat
   GOTO :EOF
) ELSE (
   TASKKILL /F /T /IM %tExecutable% 2>NUL  2>NUL
   ECHO. "Excute command..." /WAIT /B %RUN_COMMAND%
   START "Excute command..." %RUN_COMMAND%

   GOTO EXIT
)
GOTO EOF

:EOF
ECHO. End of Xcopy
EXIT %ERRORLEVEL%
:EXIT
EXIT %ERRORLEVEL%