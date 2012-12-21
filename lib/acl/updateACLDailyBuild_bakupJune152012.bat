::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
::
::  Note: This script can be used to update your local Desktop with CosmosDailyBuild
::
::       Step 1: Specify scrdir and desdir accordingly, make sure you have permission to access them     
::                  ex: scr = \\biollante.acl.com\Cosmos\Desktop
::                      dir = C:\WinRunner\ACL_DATA\Lang\English
::       Step 2: Save and exit
::       
::       To run this bat: 
::            1: double click this icon to run it
::            2: Provide Version number you want to update/set up
::
::  Developed by: Steven Xiang
::  Date: March 14, 2001
::
::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
@ECHO OFF
COLOR 1F
:: Set our SRC and DES root folder
SET SRCROOT=\\biollante02\DailyBuild\Silverstone\Desktop
SET NUM_TOKENS=5
IF '%DESROOT%'=='' (
   SET DESROOT=D:\ACL\TFSView\RFT_Automation\Silverstone\Desktop
   IF NOT EXIST D: SET DESROOT=C:\ACL\Desktop
   )
SET INSTALL_DIR="%ProgramFiles%.\ACL Software\ACL Analytics 10"
SET INSTALL_LOGDIR=%ProgramFiles%\ACL Software\ACL Analytics 10
SET INSTALL_DIR1="C:\Progra~1\ACL Software\ACL Analytics 10"
SET INSTALL_LOGDIR1=C:\Progra~1\ACL Software\ACL Analytics 10
SET INSTALL_DIR2="C:\Progra~2\ACL Software\ACL Analytics 10"
SET INSTALL_LOGDIR2=C:\Progra~2\ACL Software\ACL Analytics 10

SET MISSINGDLLSSRC=\\winrunner\winrunner\SharedFiles\ACL_missing_Files
SET ThirdPartDllSRC=%SRCROOT%\..\ThirdPartyDll
SET MISSINGDLLSDES=ReleaseSingleUser
SET MISSINGDLLSDES_U=UnicodeSingleUser
SET VerPrefix=9.3.0
::SET VerPrefix=9.2.1
SET Executable=ACLWin.exe
SET DOMAIN_NAME=ACL
SET USER_NAME=QAMail
SET PASSWORD=Password00
IF NOT '%1'=='' SET _Version=%1
IF NOT '%2'=='' SET VerPrefix=%2
if NOT '%5'=='' SET PASSWORD=%5
if NOT '%4'=='' SET USER_NAME=%4
if NOT '%3'=='' SET DOMAIN_NAME=%3

SET ACLNonUni=ReleaseSingleUser
SET ACLUni=UnicodeSingleUser
SET VerType=DevBuild
IF EXIST %DESROOT%\Acl.ini (
  SET VerType=DevCopy
  )

IF /I '%SILENT_INSTALL%'=='TRUE' (
  IF EXIST %INSTALL_DIR%\"Windows NT" SET DESROOT=%INSTALL_DIR%
  IF EXIST %INSTALL_DIR1%\"Windows NT" SET DESROOT=%INSTALL_DIR1%
  IF EXIST %INSTALL_DIR2%\"Windows NT" SET DESROOT=%INSTALL_DIR2%
)
IF NOT EXIST %DESROOT% (
  SET SILENT_INSTALL=TRUE
  IF EXIST %INSTALL_DIR%\"Windows NT" SET DESROOT=%INSTALL_DIR%
  IF EXIST %INSTALL_DIR1%\"Windows NT" SET DESROOT=%INSTALL_DIR1%
  IF EXIST %INSTALL_DIR2%\"Windows NT" SET DESROOT=%INSTALL_DIR2%
)  

IF /I '%DESROOT%'=='%INSTALL_DIR%' GOTO INSTALLER
IF /I '%DESROOT%'=='%INSTALL_DIR1%' SET INSTALL_LOGDIR=%INSTALL_DIR1%
IF /I '%DESROOT%'=='%INSTALL_DIR1%' GOTO INSTALLER
IF /I '%DESROOT%'=='%INSTALL_DIR2%' SET INSTALL_LOGDIR=%INSTALL_DIR2%
IF /I '%DESROOT%'=='%INSTALL_DIR2%' GOTO INSTALLER

GOTO CONTINUE
:INSTALLER
  IF /I '%SILENT_INSTALL%'=='FALSE' GOTO CONTINUE
  SET VerType=Installer
  SET SRCROOT=\\nas-build\DevRoot\Data\Acldbld\biollante\Silverstone
  SET ACLNonUni=ACLv93En_Desktop_NonUnicode
  SET ACLUni=ACLv93En_Desktop_Unicode
  SET NUM_TOKENS=7
:CONTINUE

SET Reg=regsvr32 /s ACLServer.dll
::SET UnReg=regsvr32 /u /s
SET Done=
SET XCSWITCH=/Y /E /R
SET XCSWITCH_=/Y /R

IF NOT '%DOMAIN_NAME%'=='' SET UserFullName=%DOMAIN_NAME%\%USER_NAME%


IF NOT EXIST %SRCROOT% NET USE %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
IF NOT EXIST %MISSINGDLLSSRC% NET USE %MISSINGDLLSSRC% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
IF NOT EXIST %FILE_SERVER% NET USE %FILE_SERVER% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes

if '%LatestVer%'=='' SET LatestVer=0
FOR /D  %%g IN (%SRCROOT%\%VerPrefix%.*) DO (
    FOR /F "eol=. tokens=%NUM_TOKENS% usebackq delims=\" %%f IN ('%%g') DO (
       IF '%LatestVer%' LSS '%%f' SET LatestVer=%%f
    )   
)


SET Version=%_Version%
IF '%Version%'=='' SET Version=%LatestVer%
IF /I '%Version%'=='Latest' SET Version=%LatestVer%

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
IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% SET aclNonUniExist=!Not Found, Run 'V' And\or 'G' first
IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% SET aclUniExist=!Not Found, Run 'V' And\or 'G' first

IF NOT '%TEST_BUILD%'=='' (
      SET Done=true
      CALL :RUNTEST %Done%
	  GOTO EOF
   )

CLS
ECHO.
ECHO.
ECHO.
ECHO.******************  Setup Silverstone daily build ***********************
ECHO.*
ECHO.*    You must have accesse to Silverstone daily build dir 
ECHO.*    You may change all the default values by editing this script 
ECHO.*    Developed by: Steven_Xiang@ACL.COM
ECHO.*
ECHO.*    List Available Silverstone Daily builds.........L 
ECHO.*         [%SRCROOT%]        %srcExist%
ECHO.*         [Latest Build -  %LatestVer%]
ECHO.*
ECHO.*    Change DESROOT..................................D
ECHO.*        [%DESROOT%]        %desExist%
ECHO.*
ECHO.*    Change Version folder...........................V 
ECHO.*         [%Version%%isLatest%]   %verExist%
ECHO.*
ECHO.*    Get This  Build.................................G
ECHO.*         [%Version%%isLatest%]   %verExist%
ECHO.*   
ECHO.*    Run  ACLWin - NonUnicode.......................RN
ECHO.*         [%Version%%isLatest%]   %aclNonUniExist%
ECHO.*
ECHO.*    Run  ACLWin - Unicode..........................RU
ECHO.*         [%Version%%isLatest%]   %aclUniExist%  
ECHO.*         
ECHO.*    Quit (no further action)........................Q
ECHO.*
ECHO.*
ECHO.************************************************************************
ECHO.
ECHO.
ECHO.
SET Message=

if NOT '%1'=='' (
  SET Done=true
  SET choice=%1
)ELSE (
  SET /p choice=    Choose one:
)

IF /I '%choice%'=='G' GOTO GETBUILD
IF /I '%choice%'=='d' GOTO DESDIR
IF /I '%choice%'=='Q' GOTO Quit
IF /I '%choice%'=='V' GOTO Version
IF /I '%choice%'=='L' GOTO List
IF /I '%choice%'=='RN' GOTO RunNUni
IF /I '%choice%'=='RU' GOTO RunUni

GOTO DESDIR

:LIST
CLS
ECHO.
ECHO.
ECHO.
ECHO.*********************** Avaliable Cosmos Daily Build ************************
ECHO.*
DIR /D /O-D %SRCROOT%
ECHO.*
ECHO.*****************************************************************************
pAUSE
GOTO MAINMENU



:DESDIR
SET /p choice=  Enter new DESROOT[%DESROOT%]:
IF NOT '%choice%'=='' SET DESROOT=%choice%
ECHO.* Your input: DESROOT = %DESROOT%

:Version
SET /p choice=  Enter new Version[%Verprefix%.]:
IF /I '%choice%'=='Latest' (
    SET choice=%LatestVer%
)
IF NOT '%choice%'=='' SET Version=%choice%
::IF NOT '%choice%'=='' SETX Version %choice%
IF /I %Version% LSS %VerPrefix% SET Version=%VerPrefix%.%Version%
::IF /I %Version% LSS %VerPrefix% SETX Version %VerPrefix%.%Version%
ECHO.* Your input: Version = %Version%
GOTO MAINMENU

:GETBUILD
IF NOT '%1'=='' SET Done=%1
SET TEST_FOLDER=%DESROOT%.\%Version%
ECHO. Get build from %SRCROOT%.\%Version% to - %TEST_FOLDER%
IF /I '%VerType%'=='DevCopy' (
   IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLUni% 
      RMDIR /S /Q %DESROOT%.\%VerType%\%Version%.\%ACLNonUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLUni% %DESROOT%.\ %XCSWITCH%
   ) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLNonUni%
	  RMDIR /S /Q %DESROOT%.\%VerType%\%Version%.\%ACLUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLNonUni% %DESROOT%.\ %XCSWITCH%
   )
) ELSE IF /I '%VerType%'=='Installer' (
    SET INSTALL_EXEDIR=%DESROOT%\%VerType%
    SET INSTALL_LOGDIR=%DESROOT%\%VerType%\%Version%
	MKDIR %DESROOT%.\%VerType%\%Version%
   IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%\%VerType%\%Version%\%ACLUni% 
      RMDIR /S /Q %DESROOT%\%VerType%\%Version%.\%ACLNonUni%
      XCOPY %SRCROOT%\%Version%\%ACLUni%.exe %DESROOT%\%VerType%\ %XCSWITCH_%
	  SET INSTALL_EXE=%DESROOT%\%VerType%\%ACLUni%.exe
   ) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
      MKDIR %DESROOT%\%VerType%\%Version%\%ACLNonUni%
	  RMDIR /S /Q %DESROOT%\%VerType%\%Version%\%ACLUni%
      XCOPY %SRCROOT%\%Version%\%ACLNonUni%.exe %DESROOT%\%VerType%\ %XCSWITCH_%
      SET INSTALL_EXE=%DESROOT%\%VerType%\%ACLnonUni%.exe
   )
   
   IF EXIST %DESROOT%\%Executable% (
      TASKKILL /F /T /IM %Executable%
      %DESROOT%\%VerType%\%ACLUni%.exe /s /a /x /s /v"/qb /passive /quiet"
      %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /x /s /v"/qb /passive /quiet"
      Rem %DESROOT%\%VerType%\%ACLUni%.exe /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_LOGDIR%\%VerType%\%Version%\Uninstallation.log\""
      Rem %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_LOGDIR%\%VerType%\%Version%\Uninstallation.log\""
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
	  IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
   )
   IF NOT EXIST %DESROOT%\%Executable% (
      IF /I '%TEST_UNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\""
      IF /I '%TEST_NONUNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\""
      Rem IF /I '%TEST_UNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" /l* \"%INSTALL_LOGDIR%\%VerType%\%Version%\Installation.log\""
      Rem IF /I '%TEST_NONUNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" /l* \"%INSTALL_LOGDIR%\%VerType%\%Version%\Installation.log\""
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
	  IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% %RFT_PROJECT_LOCATION%\lib\acl\tool\sleep 5 /quiet
      IF NOT EXIST %DESROOT%\%Executable% (
         IF /I '%TEST_UNICODE%'=='Yes' RMDIR /S /Q %DESROOT%\%VerType%\%Version%.\%ACLNonUni%
	     IF /I '%TEST_NONUNICODE%'=='Yes' RMDIR /S /Q %DESROOT%\%VerType%\%Version%\%ACLUni%
      )
    )
  ) ELSE (
   XCOPY %ThirdPartDllSRC%  %MISSINGDLLSSRC%.\ %XCSWITCH%
   XCOPY %SRCROOT%.\%Version% %DESROOT%.\%Version%.\ %XCSWITCH%
   XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\%MISSINGDLLSDES%.\ %XCSWITCH%
   XCOPY %MISSINGDLLSSRC%  %DESROOT%.\%Version%.\%MISSINGDLLSDES_U%.\ %XCSWITCH%
)
::RENAME %DESROOT%.\%Version%.\%MISSINGDLLSDES%.\%Executable% ACLWin_%Version%.exe
::RENAME %DESROOT%.\%Version%.\%MISSINGDLLSDES_U%.\%Executable% ACLWin_%Version%.exe

::XCOPY %DESROOT%.\%Version% %DESROOT%.\Latest\ %XCSWITCH%
::RMDIR /S /Q %DESROOT%.\(%Version%-10).\
IF EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% (
   SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
)
IF EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% (
   SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
)
IF '%Done%'=='true' GOTO :EOF
GOTO JOBDONE

:JOBDONE
ECHO. Xcopy done!
PAUSE
GOTO MAINMENU

:RUNNUni
::%UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
%Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll

ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLNonUni%\%Executable%

Start "Desktop - %DESROOT%\%Version%\%ACLNonUni%\%Executable%" /D%DESROOT%\%Version%\%ACLNonUni%\ /MAX /SEPARATE /B %DESROOT%.\%Version%.\%ACLNonUni%.\%Executable%
GOTO MAINMENU

:RUNUni

::%UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
%Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLUni%\%Executable%
Start "Desktop - %DESROOT%\%Version%\%ACLUni%\%Executable%" /D%DESROOT%\%Version%\%ACLUni%\ /MAX /SEPARATE /B %DESROOT%.\%Version%.\%ACLUni%.\%Executable%
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
SET RUN_FLAG=%DESROOT%\%Version%\AUTOMATION_IS_RUNING
IF NOT '%1'=='' SET Done=%1
::IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% CALL :GETBUILD %Done%
::IF /I '%RETEST%'=='Yes' SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
::IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% CALL :GETBUILD %Done%
::IF /I '%RETEST%'=='Yes' SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
IF NOT '%DESROOT%'=='DevBuild' (
  SET UNI_PATH=%DESROOT%\%Executable%
  SET NONUNI_PATH=%DESROOT%\%Executable%
  ::SET VerType=DevCopy
  ::SET VerType=Installer
  IF /I '%TEST_UNICODE%'=='Yes' (
      IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLUni% CALL :GETBUILD %Done%
	  )
  IF /I '%TEST_NONUNICODE%'=='Yes' (
     IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLNonUni% CALL :GETBUILD %Done%
	 )
) ELSE (
    SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
    SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
    IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% CALL :GETBUILD %Done%
    IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% CALL :GETBUILD %Done%
)


:RUN
ECHO "Run test..." 	/D"%RFT_PROJECT_LOCATION%\lib\acl\tool" /WAIT /B doTest.bat 
START "Run test..." /D%RFT_PROJECT_LOCATION%\lib\acl\tool /WAIT /B doTest.bat
GOTO :EOF

:EOF
ECHO. End of Xcopy
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%