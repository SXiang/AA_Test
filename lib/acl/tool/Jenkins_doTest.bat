@ECHO OFF
:EMAIL configurations 
IF /I '%RETEST%'=='Yes' (
   SET RunType=Retest
   ) ELSE (
   SET RunType=New Test
   )
   
::SET _Subject=ACL Desktop[%LOCALE%] %TEST_CATEGORY% Test[%PROJECT_TYPE%] -  Build[%VerType%%Version%]
SET _Subject=ACL Analytics[%LOCALE%] %TEST_CATEGORY% Test[%PROJECT_TYPE%] -  %Version%
SET SmtpServer=xchg-cas-array.acl.com
SET FromName=%USER_NAME%
SET FromAddress=%USER_NAME%@ACL.com
SET _Username=%UserFullName%
SET _Password=%PASSWORD%
::SET Body=file://D:\ACL\Automation\Calm_Blue_Template\Calm_Blue_Template\index.html
SET AttachFiles=
SET IPPort=25
SET ssl=1
REM Importance High, Normal, Low                       
SET Importance=Normal
::SET Output_Report=file://%RFT_PROJECT_LOCATION%.\ACL_Desktop\DATA\TempData\HtmlTestReport.html
::SET Output_Report=%RFT_PROJECT_LOCATION%.\ACL_Desktop\DATA\TempData\TestReport.txt
::SET Body=file://%Output_Report% 
SET Body=Output_Report

:SET_TEST
IF '%Reg%'=='' SET Reg=regsvr32 /s
IF '%ScriptDir%'=='' SET ScriptDir=%RFT_PROJECT_LOCATION%\lib\acl\tool
SET RUN_PROJECT_LOCATION=%ScriptDir%
SET EmailParameters=%SmtpServer%,%FromName%,%FromAddress%,%ToAddress%,%Body%,%AttachFiles%,%CcAddress%,%BccAddress%,%Importance%,%_Username%,%_Password%,%IPPort%,%ssl%

REM *** Unicode Test **** 
IF '%BUIDE%'=='' SET BUIDE=%UNI_PATH%
SET ENCODE=Unicode
SET Subject=%_Subject% - %ENCODE%
IF /I '%TEST_UNICODE%'=='Yes' (
    SET isUnicode=true
	%Reg% %BUIDE%\..\ACLServer.dll
    CALL :RUN_TEST
	GOTO EOF
	)

REM *** Non Unicode Test **** 
SET ENCODE=NonUnicode
SET Subject=%_Subject% - %ENCODE%
IF '%BUIDE%'=='' SET BUIDE=%NONUNI_PATH%
IF /I '%TEST_NONUNICODE%'=='Yes' (
    SET isUnicode=false
	%Reg% %BUIDE%\..\ACLServer.dll
    CALL :RUN_TEST
	GOTO EOF
	)

:RUN_TEST
echo BUIDE: '%BUIDE%'

IF NOT '%BUIDE%'=='' (
		   ECHO Run auto test... " /D'%RUN_PROJECT_LOCATION%' /WAIT /B Jenkins_runRFT.bat '%RFT_PROJECT_LOCATION%' '%RFT_SCRIPT_NAME%' '%BUIDE%' '%isUnicode%' '%EMAIL_REPORT%' "OOOOO%RUN_PROJECT_LOCATION% CDOMessage.exe %Subject%,%EmailParameters%OOOOO" '%TEST_CATEGORY%' '%SILENT%'
	       CALL %RUN_PROJECT_LOCATION%\Jenkins_runRFT.bat %RFT_PROJECT_LOCATION% %RFT_SCRIPT_NAME% %BUIDE% %isUnicode% %EMAIL_REPORT% "OOOOO%RUN_PROJECT_LOCATION% CDOMessage.exe %Subject%,%EmailParameters%OOOOO" %TEST_CATEGORY% %SILENT%
		   CALL :SEND_MAIL
		   )
GOTO :EOF

:SEND_MAIL
IF /I '%EMAIL_REPORT%'=='true' (
		      ::ECHO "Send Email report..." 	/D%RUN_PROJECT_LOCATION% CDOMessage.exe %Subject%,%EmailParameters%
			  ::START "Send Email report..." 	/D%RUN_PROJECT_LOCATION% CDOMessage.exe %Subject%,%EmailParameters%  
             )  
GOTO :EOF

:EOF
EXIT %ERRORLEVEL%
EXIT %ERRORLEVEL%
