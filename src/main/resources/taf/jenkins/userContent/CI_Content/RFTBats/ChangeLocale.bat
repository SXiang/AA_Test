@ECHO OFF
Rem temp solution for the issue of concurrent builds in queue, need to move setUserProperties to the next step inorder to use this.
rem SET testLocker=\\192.168.10.129\Automation\%TEAM_NAME%\userContent\%Project%\TestHistory\user.locker
rem IF EXIST %testLocker% DEL /f /q %testLocker% 2>NUL
rem SET USE=%LOCALE%
SET curLocDir=C:\ACL\CurrentLocale
IF /I '%USE%' == 'En' GOTO Change
:ConfLocale

IF '%USE%' == '' GOTO RESTART
Rem Euro langs
IF /I '%USE%' == 'Es' SET USE=en
IF /I '%USE%' == 'De' SET USE=en
IF /I '%USE%' == 'Fr' SET USE=en
IF /I '%USE%' == 'Pt' SET USE=en
Rem Pl must use Pl
IF /I '%USE%' == 'Pl' SET USE=Pl
Rem East Asian langs
IF /I '%USE%' == 'Zh' SET USE=en
IF /I '%USE%' == 'Ja' SET USE=en
IF /I '%USE%' == 'Ko' SET USE=en

Rem comment out the next goto to enable locale change
REM GOTO EOF

:Change
IF EXIST %curLocDir%\%USE%\ GOTO FORCERESTART
IF Not EXIST %curLocDir% (
  IF /I "%USE%" == "en" GOTO FORCERESTART
)
IF /I "%isXP%" == "True" (
  REM GOTO EOF
  ECHO.%COMPUTERNAME% - rundll32 shell32, Control_RunDLL intl.cpl,,/f:"%WORKSPACE%\RFTBats\LOCALE\locale_%USE%.txt" 2>NUL
  CALL Rundll32 shell32, Control_RunDLL intl.cpl,,/f:"%WORKSPACE%\RFTBats\LOCALE\locale_%USE%.txt" 2>NUL
 ) ELSE (
   ECHO.%COMPUTERNAME% - CALL control.exe intl.cpl,,/f:"%WORKSPACE%\RFTBats\LOCALE\locale_%USE%.xml" 2>NUL
   CALL control.exe intl.cpl,,/f:"%WORKSPACE%\RFTBats\LOCALE\locale_%USE%.xml" 2>NUL
 )
 IF Not '%CurLocdir%'=='' (
  IF EXIST %curLocDir% rmdir /S /Q %curLocDir%\ 2>NUL
  mkdir %curLocDir%\%USE% 2>NUL
  )
 GOTO RESTART 
:FORCERESTART
IF /I NOT '%ForceRestart%'=='True' GOTO EOF

:RESTART
  ECHO.%COMPUTERNAME% - CALL SHUTDOWN -r -t 05 -c "Change Locale and/or Restart for the next Automation test, don't interrupt me!" 2>NUL
  CALL SHUTDOWN -r -t 05 -c "Change Locale and/or Restart for the next Automation test, don't interrupt me!" 2>NUL

:EOF
EXIT 0