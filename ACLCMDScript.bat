
REM @ECHO OFF
REM SET SILENT=Silent
:AUT
SET DESROOT=D:\ACL\TFSView\RFT_Automation\Monako\Desktop
SET Version=Build90
SET IHExec=ACLScript.exe
SET DTExec=ACLWin.exe
SET DTUni=Unicode
SET DTNonUni=Release
SET IHUni=IronhideUnicode
SET IHNonUni=IronhideRelease


:INPUT from window's scheduler
IF NOT '%1'=='' SET DESROOT=%1
IF NOT '%2'=='' SET Version=%2

:RUN
REM SET ACL_PROJECT=W:\QA_Automation_2012_V2.0_Monako\ACL_User\Steven\ACLQA_Automation\ACL_Desktop\DATA\ACLProject\Unicode\DesktopScript\OldbatsDesktopEnglish\OldBats_U.acl
REM SET ACL_SCRIPT=_AllTest
REM :DT UNICODE
REM ECHO. CMD: "%DTUni%.\%DTExec%" - %ACL_SCRIPT% ...
REM "%DESROOT%.\%Version%.\%DTUni%.\%DTExec%" %ACL_PROJECT% /b%ACL_SCRIPT%
REM :IH UNICODE
REM ECHO. CMD: "%IHUni%.\%IHExec%" - %ACL_SCRIPT% ...
REM "%DESROOT%.\%Version%.\%IHUni%.\%IHExec%" %ACL_PROJECT% /b%ACL_SCRIPT%
SET ACL_PROJECT=M:\Working\Silverstone\Test Cases\Old Features Regression Test Results\Steven\ImportTest\NonUnicodeTest\ImportNonUnicode.ACL
SET ACL_SCRIPT=ImportTestScript
:DT NONUNICODE
ECHO. CMD: "%DTNonUni%.\%DTExec%" - %ACL_SCRIPT% ...
"%DESROOT%.\%Version%.\%DTNonUni%.\%DTExec%" "%ACL_PROJECT%" /b%ACL_SCRIPT%
:IH NONUNICODE
ECHO. CMD: "%IHNonUni%.\%IHExec%" - %ACL_SCRIPT% ...
"%DESROOT%.\%Version%.\%IHNonUni%.\%IHExec%" %ACL_PROJECT% /b%ACL_SCRIPT%

ECHO. End of Testing!
IF /I '%SILENT%'=='Silent' GOTO EOF
PAUSE
:EOF
EXIT %ERRORLEVEL%