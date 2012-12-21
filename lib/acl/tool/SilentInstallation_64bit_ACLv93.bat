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
SET SRCROOT_installer=\\nas-build\DevRoot\Data\Acldbld\biollante\Silverstone

SET NUM_TOKENS=5
SET INSTALL_DIR=%DESROOT%
SET INSTALL_DIR1=C:\Progra~1\ACL Software\ACL Analytics 10
SET INSTALL_DIR2=C:\Progra~2\ACL Software\ACL Analytics 10
IF '"%DESROOT%"'=='""' (
   SET STANDALONG=TRUE
   SET DESROOT=%INSTALL_DIR2%
   SET SILENT_INSTALL=TRUE
   REM SET DESROOT=D:\ACL\TFSView\RFT_Automation\Silverstone\Desktop
   REM IF NOT EXIST D: SET DESROOT=C:\ACL\Desktop
   )
SET INSTALL_DIR=%DESROOT%
SET DESROOT="%DESROOT%"
SET MISSINGDLLSSRC=\\winrunner\winrunner\SharedFiles\ACL_missing_Files
SET ThirdPartDllSRC=%SRCROOT%\..\ThirdPartyDll
SET MISSINGDLLSDES=ReleaseSingleUser
SET MISSINGDLLSDES_U=UnicodeSingleUser
SET ACL_DATA="C:\ACL DATA\Sample Data Files"
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

IF NOT EXIST %DESROOT% (
  IF NOT '%SILENT_INSTALL%'=='TRUE' GOTO CONTINUE
  IF EXIST "%INSTALL_DIR1%\Windows NT" SET DESROOT="%INSTALL_DIR1%"
  IF EXIST "%INSTALL_DIR2%\Windows NT" SET DESROOT="%INSTALL_DIR2%"
  IF EXIST "%INSTALL_DIR1%\Windows NT" SET INSTALL_DIR=%INSTALL_DIR1%
  IF EXIST "%INSTALL_DIR2%\Windows NT" SET INSTALL_DIR=%INSTALL_DIR2%
) 

IF /I '%DESROOT%'=='"%INSTALL_DIR1%"' GOTO INSTALLER
IF /I '%DESROOT%'=='"%INSTALL_DIR2%"' GOTO INSTALLER
IF /I '%SILENT_INSTALL%'=='TRUE' GOTO INSTALLER

GOTO CONTINUE
:INSTALLER
  IF /I '%SILENT_INSTALL%'=='FALSE' GOTO CONTINUE
  SET VerType=Installer
  SET SRCROOT=%SRCROOT_installer%
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
IF /I '%VerType%'=='Installer' (
 IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLNonUni% SET aclNonUniExist=!Not Found, 'GN' to install
 IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLUni% SET aclUniExist=!Not Found, 'GU' to install
) ELSE (
 IF NOT EXIST %DESROOT%\%Version%\%ACLNonUni%\%Executable% SET aclNonUniExist=!Not Found, 'G' to get the build
 IF NOT EXIST %DESROOT%\%Version%\%ACLUni%\%Executable% SET aclUniExist=!Not Found, 'G' to get the build
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
ECHO.******************  Setup Silverstone daily build ***********************
ECHO.*
Rem ECHO.*    You must have accesse to Silverstone daily build dir 
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
IF /I %VerType%'=='DevBuild' ECHO.*    Get This  Build.................................G
REM IF Not %VerType%'=='DevBuild' ECHO.*    Install Unicode Build...............................GU
REM IF Not %VerType%'=='DevBuild' ECHO.*    Install NonUnicode Build............................GN
IF /I %VerType%'=='DevBuild' ECHO.*         [%Version%%isLatest%]   %verExist%
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
IF /I '%choice%'=='GU' (
  SET TEST_UNICODE=Yes
  SET TEST_NONUNICODE=No
  SET ENCODE=Unicode
)
IF /I '%choice%'=='GN' (
  SET TEST_NONUNICODE=Yes
  SET TEST_UNICODE=No
  SET ENCODE=NonUnicode
  )
IF /I '%choice%'=='G' GOTO GETBUILD
IF /I '%choice%'=='GU' GOTO GETBUILD
IF /I '%choice%'=='GN' GOTO GETBUILD
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
IF NOT EXIST %SRCROOT%.\%Version% ECHO. CAN'T FIND '%SRCROOT%.\%Version%'
IF NOT EXIST %SRCROOT%.\%Version%  GOTO GETBUILDDONE
IF /I '%VerType%'=='DevCopy' (
   RMDIR /S /Q %DESROOT%.\%VerType%
   IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLUni%
	  REM MKDIR %DESROOT%.\%VerType%_History\%Version%
      REM RMDIR /S /Q %DESROOT%.\%VerType%\%Version%.\%ACLNonUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLUni% %DESROOT%.\ %XCSWITCH%
   ) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%\%Version%.\%ACLNonUni%
	  REM RMDIR /S /Q %DESROOT%.\%VerType%\%Version%.\%ACLUni%
      XCOPY %SRCROOT%.\%Version%.\%ACLNonUni% %DESROOT%.\ %XCSWITCH%
   )
) ELSE IF /I '%VerType%'=='Installer' (
    RMDIR /S /Q %DESROOT%.\%VerType%
	MKDIR %DESROOT%.\%VerType%\%Version%
	
	XCOPY %SRCROOT%\%Version%\%ACLUni%.exe %DESROOT%\%VerType%\ %XCSWITCH_%
	XCOPY %SRCROOT%\%Version%\%ACLNonUni%.exe %DESROOT%\%VerType%\ %XCSWITCH_%
   IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%\%VerType%\%Version%\%ACLUni% 
      REM RMDIR /S /Q %DESROOT%\%VerType%\%Version%.\%ACLNonUni%
	) ELSE IF /I '%TEST_NONUNICODE%'=='Yes' (
      MKDIR %DESROOT%\%VerType%\%Version%\%ACLNonUni%
	  MKDIR %DESROOT%\%VerType%_History\%Version%\%ACLNonUni%
	  REM RMDIR /S /Q %DESROOT%\%VerType%\%Version%\%ACLUni%
   )
   IF EXIST %DESROOT%\%Executable% (
      
      TASKKILL /F /T /IM %Executable%
	  TASKKILL /F /T /IM %ACLUni%.exe
	  TASKKILL /F /T /IM %ACLNonUni%.exe
	  
      Rem %DESROOT%\%VerType%\%ACLUni%.exe /s /a /x /s /v"/qb /passive /quiet"
      Rem %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /x /s /v"/qb /passive /quiet"
      %DESROOT%\%VerType%\%ACLUni%.exe /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_DIR%\%VerType%\Uninstallation.log\""
      %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /x /s /v"/qb /passive /quiet /l* \"%INSTALL_DIR%\%VerType%\Uninstallation.log\""
	  echo. %RFT_PROJECT_LOCATION%
	  
      
	  TASKKILL /F /T /IM %ACLUni%.exe
	  TASKKILL /F /T /IM %ACLNonUni%.exe
   )
   IF NOT EXIST %DESROOT%\%Executable% (
      TASKKILL /F /T /IM %ACLUni%.exe
	  TASKKILL /F /T /IM %ACLNonUni%.exe
	  
      Rem IF /I '%TEST_UNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\""
      Rem IF /I '%TEST_NONUNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\""
      IF /I '%TEST_UNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%INSTALL_DIR%\%VerType%\Installation.log\""
      IF /I '%TEST_NONUNICODE%'=='Yes' %DESROOT%\%VerType%\%ACLNonUni%.exe /s /a /s /v"/qb /passive /quiet PIDKEY=CAW1234567890 COMPANYNAME=\"ACLQA Automation\" INSTALLDIR=\"%INSTALL_DIR%\" /l* \"%INSTALL_DIR%\%VerType%\Installation.log\""
      
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
:GETBUILDDONE
IF '%Done%'=='true' GOTO :EOF
GOTO JOBDONE

:JOBDONE
ECHO. Xcopy done!
PAUSE
GOTO MAINMENU

:RUNNUni
IF NOT '%VerType%'=='Installer' (
  REM %UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  %Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLNonUni%\%Executable%
  Start "Desktop - %Version%\%ACLUni%\%Executable%" /D%ACL_DATA%\ /MAX /SEPARATE /B "%INSTALL_DIR%.\%Version%.\%ACLNonUni%.\%Executable%"
) ELSE (
    REM %UnReg% %DESROOT%\ACLServer.dll
    %Reg% %DESROOT%\ACLServer.dll
    ECHO. Start ACLWin at: %DESROOT%\%Executable%
	echo. Start "Desktop - %Version%\%ACLNonUni%\%Executable%" /D%ACL_DATA%\ /MAX /SEPARATE /B %DESROOT%.\%Executable%
    IF '"%aclNonUniExist%"'=='""' Start "Desktop - %Version%\%ACLNonUni%\%Executable%" /D%ACL_DATA%\ /MAX /SEPARATE /B "%INSTALL_DIR%\%Executable%"
  )  
GOTO MAINMENU

:RUNUni
IF NOT '%VerType%'=='Installer' (
  ::%UnReg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  %Reg% %DESROOT%\%Version%\%ACLUni%\ACLServer.dll
  ECHO. Start ACLWin at: %DESROOT%\%Version%\%ACLUni%\%Executable%
  Start "Desktop - %Version%\%ACLUni%\%Executable%" /D%ACL_DATA%\ /MAX /SEPARATE /B "%INSTALL_DIR%.\%Version%.\%ACLUni%.\%Executable%"
  ) ELSE (
    ::%UnReg% %DESROOT%\ACLServer.dll
  %Reg% %DESROOT%\ACLServer.dll
  ECHO. Start ACLWin at: %DESROOT%\%Executable%
  IF '"%aclUniExist%"'=='""' Start "Desktop - %Version%\%ACLUni%\%Executable%" /D%ACL_DATA%\ /MAX /SEPARATE /B "%INSTALL_DIR%\%Executable%"
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
SET RUN_FLAG=%DESROOT%\%Version%\AUTOMATION_IS_RUNING
IF NOT '%1'=='' SET Done=%1
::ECHO TEST_CATEGORY=%TEST_CATEGORY%  _testCategory=%_testCategory%
::SET TEST_FLAG=%_AUT%.\..\..\Auto_%_testCategory%Test_Log[%ENCODE%].TXT

IF /I NOT '%RETEST%'=='Yes' (
	IF EXIST %TEST_FLAG% GOTO EOF
	)
IF /I '%TEST_UNICODE%'=='Yes' SET ENCODE=Unicode
IF /I '%TEST_NONUNICODE%'=='Yes' SET ENCODE=NonUnicode
IF NOT '%VerType%'=='DevBuild' (
  SET UNI_PATH=%DESROOT%\%Executable%
  SET NONUNI_PATH=%DESROOT%\%Executable%
  SET TEST_FLAG=%DESROOT%\%VerType%_History\%Version%\Auto_%TEST_CATEGORY%Test[%PROJECT_TYPE%]_Log[%ENCODE%].TXT
  
  ::SET VerType=DevCopy
  ::SET VerType=Installer
  IF /I '%TEST_UNICODE%'=='Yes' (
      MKDIR %DESROOT%.\%VerType%_History\%Version%\%ACLUni%
      IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLUni% CALL :GETBUILD %Done%
	  )
  IF /I '%TEST_NONUNICODE%'=='Yes' (
     MKDIR %DESROOT%.\%VerType%_History\%Version%\%ACLNonUni%
     IF NOT EXIST %DESROOT%\%VerType%\%Version%\%ACLNonUni% CALL :GETBUILD %Done%
	 )
) ELSE (
    SET UNI_PATH=%DESROOT%\%Version%\%ACLUni%\%Executable%
    SET NONUNI_PATH=%DESROOT%\%Version%\%ACLNonUni%\%Executable%
	SET TEST_FLAG=%DESROOT%\%Version%\Auto_%TEST_CATEGORY%Test[%PROJECT_TYPE%]_Log[%ENCODE%].TXT
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