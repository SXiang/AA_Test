@ECHO OFF
:Email
%JENKINS_HOME%\userContent\sleep 5 /quiet
Rem ECHO. BUILD_URL='%BUILD_URL%' JENKINS_URL='%JENKINS_URL%'
set strFileURL=%BUILD_URL%../
SET reportDir=%WORKSPACE%\TestReport
set strHDLocation=%ReportDir%\JenkinsReport.html
cd C:\WINDOWS\GnuWin32\bin
rem IF '%BUILD_STATUS%'=='' SET BUILD_STATUS=Success -- IT'S a tag name from email-ext, doesn't work here
SET subject=%BUILD_STATUS% Jenkins Test (Beta) - ACL Analytics [%Version%]
Call C:\WINDOWS\GnuWin32\bin\wget.exe --wait=5 --tries=3 -S --timeout=600 -O %strHDLocation% %strFileURL%
Call Cscript %WORKSPACE%\rftbats\getHttpContent.vbs %strFileURL% %strHDLocation% %JENKINS_URL% %strFileURL% "%subject%"

SET strHDLocation=%strHDLocation%email.html
IF Not Exist %strHDLocation% GOTO End
        
		
		SET smtpServer=xchg-cas-array.acl.com
		SET fromAddress=%USER_NAME%@ACL.COM
		SET fromName=%USER_NAME%
		SET userName=ACL\%USER_NAME%
		SET password=%PASSWORD%
REM     SET userName=ACL\QAMAIL
REM		SET password=Password00
rem		SET fromAddress=QAMAIL@ACL.COM
rem		SET fromName=QAMAIL
Rem		SET toAddress=%USER_NAME%@ACL.com
		SET body=%strHDLocation%
		SET attachFiles=
Rem		SET ccAddress=
Rem		SET bccAddress=
		SET importance=Normal
		
		SET ipPort=25
		SET ssl=1
		SET d=,
		set s= 
echo. %WORKSPACE%\rftbats\CDOMessage.exe %subject%%d%%smtpServer%%d%%fromName%%d%%fromAddress%%d%%toAddress%%d%%body%%d%%attachFiles%%d%%ccAddress%%d%%bccAddress%%d%%importance%%d%%userName%%d%%password%%d%%ipPort%%d%%ssl%
SET emailCmd=%subject%%d%%smtpServer%%d%%fromName%%d%%fromAddress%%d%%toAddress%%d%%body%%d%%attachFiles%%d%%ccAddress%%d%%bccAddress%%d%%importance%%d%%userName%%d%%password%%d%%ipPort%%d%%ssl%
Call %WORKSPACE%\rftbats\CDOMessage.exe %emailCmd%

:END
EXIT 0