@ECHO OFF
SET adminEmail=Iryna_Babak@ACL.com;Yousef_Aichour@ACL.com;Steven_Xiang@ACL.com
SET debugEmail=QAMail@ACL.com
REM thisID is specified in downstream jobs which needs to return/combine test result to a shared location,
REM then the main job can grab it for final report
REM if it's empty, means that job is not a specific test job, a trigger job instead.


IF "%thisID%" == "" GOTO Prepare

:ExportResult
set /p thisProject_Type=<%HistoryDir%\%thisID%.properties 2>NUL
Rem IF NOT EXIST %HistoryDir%\%thisID% GOTO Prepare
IF /I '%BUILD_STATUS%' == 'Fail' (
    SET RSTATUS=Fail
	SET bgcolor="#FF0000"
	)
	IF /I '%BUILD_STATUS%' == 'Pass' (
    SET RSTATUS=Pass
	SET bgcolor="#008000"
	)
	IF /I '%BUILD_STATUS%' == 'Unstable' (
    SET RSTATUS=*Pass
	SET bgcolor="#FFFF00"
	)
 IF NOT EXIST %HistoryDir%\%thisID% (
    SET RSTATUS=Not Completed
 	SET bgcolor="#BDBDBD"
 )

 Type %HistoryDir%\%thisID% >> %reportFile%
 IF /I "%TitlePrefix%" == "[Skipped]" Goto Prepare 
 IF NOT '%thisStrFileURL%' == '' Echo.^<td^> ^<a href="%thisStrFileURL%"^> TestSummary ^</a^> ^</td^>^
		  ^<td^ bgcolor=%bgcolor%^> %RSTATUS% ^</td^>^
	   ^</tr^> >> %reportFile% 2>NUL
IF EXIST %HistoryDir%\%thisID% DEL /f /q %HistoryDir%\%thisID% 2>NUL
:Prepare
IF /I "%thisEmail_Report%" == "No" GOTO END
IF /I "%Email_Report%" == "No" GOTO END
rem IF /I "%TitlePrefix%" == "[Skipped]" Goto Skip

"%JENKINS_HOME%\userContent\sleep" 5 /quiet
IF /I Not "%thisProject_Type%" == "" SET Project_Type=%thisProject_Type%
REM thisStrFileURL is html url from upstream job, usually like to Archived HTML
IF NOT "%thisStrFileURL%" == "" set strFileURL=%thisStrFileURL%
REM strFilURL is the default html url specified in properties, usually link to Jenkins page
IF "%strFileURL%" == "" set strFileURL=%BUILD_URL%../
REM Specify the file name for report downloading from pre URL
SET reportDir=C:\ACLQA\CI_Testing\TestReport
rem SET reportDir=%WORKSPACE%.\TestReport
set strHDLocation=%ReportDir%\JenkinsReport.html
mkdir %ReportDir% 2>NUL
REM setup Email subject
SET TABLE_TYPE=%PROJECT_TYPE%
IF /I "%PROJECT_TYPE%" == "LOCALONLY" SET TABLE_TYPE=LOCAL
SET buildInfo=%PROJECT%_%Branch%#%AA_BUILD%.%Version_Suffix%
IF "%Version_Suffix%" == "" SET buildInfo=%PROJECT%#%AA_BUILD%
REM jobSubject is the partial name specified by upstream job
IF NOT "%jobSubject%" == ""	SET subject=%buildInfo%-%jobSubject%
REM thisSubject is a partial name from the properties 
IF NOT "%thisSubject%" == "" SET subject=%buildInfo%[%TABLE_TYPE%]-%TEST_CATEGORY% Test[%thisSubject%]
REM default subject, if there is no subject info from the ENV.
IF "%subject%" == "" SET subject=%buildInfo%_%LOCALE%[%TABLE_TYPE%]-%TEST_CATEGORY% Test
SET subject=%TitlePrefix%%subject%
REM thisStrHDLocation is the file URL of the report, if it's there, don't need to download / cleanup
IF Not "%thisStrHDLocation%" == "" Goto SendReport

:DownloadHTML
REM For http link
Echo wget.exe --no-proxy --wait=5 --tries=3 -S --timeout=600 -O %strHDLocation% %strFileURL%
Call wget.exe --no-proxy --wait=5 --tries=3 -S --timeout=600 -O %strHDLocation% %strFileURL%
SET thisStrHDLocation=%strHDLocation%
IF NOT "%thisStrFileURL%" == "" GOTO SendReport

:CleanUp
REM For Jenkins page 
Echo Call Cscript "%WORKSPACE%"\rftbats\getQAContent.vbs %strFileURL% %strHDLocation% %JENKINS_URL% %strFileURL% "%subject%"
Call Cscript "%WORKSPACE%"\rftbats\getQAContent.vbs %strFileURL% %strHDLocation% %JENKINS_URL% %strFileURL% "%subject%"
SET thisStrHDLocation=%strHDLocation%email.html

:SendReport

IF /I "%TitlePrefix%" == "[Skipped]" Goto Skip
IF NOT EXIST %HistoryDir%\ReportReady GOTO Admin
IF EXIST %HistoryDir%\JenkinsError (
   SET subject=Check the test enviroment! - %subject%
   Goto Admin
)
IF EXIST %HistoryDir%\Interrupted (
   SET subject=Test was interrupted? - %subject%
   Goto Admin
)

Rem ********************************** Debug ************************************
Rem IF /I '%BUILD_STATUS%' == 'Fail' GOTO Admin
Rem Localization tests -- on debugging: Send Matrix to user, details to admin only
Rem IF /I NOT "%LOCALE%"=="En" (
IF /I NOT "%TEST_CATEGORY%"=="Daily" (
 Rem IF /I '%BUILD_STATUS%' == 'Fail' GOTO Admin
 IF NOT "%thisID%" == "" GOTO Admin
 )
Rem **********************************End of debug **********************************

IF /I "%Email_Report%" == "Admin" GOTO Admin
IF /I "%Email_Report%" == "Debug" GOTO Debug

IF NOT "%toThisAddress%" == "" SET toAddress=%toThisAddress%
IF NOT "%ccThisAddress%" == "" SET ccAddress=%ccThisAddress%
IF NOT "%bccThisAddress%" == "" SET bccAddress=%bccThisAddress%

GOTO Email

:Admin
SET toAddress=%adminEmail%
SET ccAddress=
SET bccAddress=
GOTO Email
:Debug
SET toAddress=%debugEmail%
SET ccAddress=
SET bccAddress=
:: SET ccAddress=Danny_Kusnardi@ACL.com;Rory_Emerson;Shane_Grimm
:: SET bccAddress=#Desktop_QA@ACL.com
Goto Email
:Email
IF NOT '%BUILD_STATUS%' == '' SET subject=%BUILD_STATUS% - %subject%
SET strHDLocation=%thisStrHDLocation%
IF /I '%BUILD_STATUS%' == 'Pass' SET attachFiles=
IF Not Exist %strHDLocation% GOTO Error
SET minfilesize=300
FOR /F "usebackq" %%A IN ('%strHDLocation%') DO SET emailsize=%%~zA
IF %emailsize% LSS %minfilesize% GOTO Error
        SET smtpServer=xchg-cas-array.acl.com
rem		SET smtpServer=192.168.10.240
		SET fromAddress=%USER_NAME%@ACL.COM
		SET fromName=%USER_NAME%
		SET userName=ACL\%USER_NAME%
		SET password=%PASSWORD%
		SET body=%strHDLocation%
rem		SET attachFiles=
		SET importance=Normal
		
		SET ipPort=25
rem		set ipPort=587
		SET ssl=1
		SET d=,
		set s= 
IF Not "%bccAddress%" == "" SET bccAddress=%adminEmail%;%bccAddress%
IF "%bccAddress%" == "" SET bccAddress=%adminEmail%
IF "%toAddress%" == "" (
  SET toAddress=%adminEmail%
  Echo.Caution! have you set the email to address correctly?, this report is going to be sent to Admin only as no other address found.
  )
echo. "%WORKSPACE%"\rftbats\CDOMessage.exe %subject%%d%%smtpServer%%d%%fromName%%d%%fromAddress%%d%%toAddress%%d%%body%%d%%attachFiles%%d%%ccAddress%%d%%bccAddress%%d%%importance%%d%%userName%%d%%password%%d%%ipPort%%d%%ssl%
SET emailCmd=%subject%%d%%smtpServer%%d%%fromName%%d%%fromAddress%%d%%toAddress%%d%%body%%d%%attachFiles%%d%%ccAddress%%d%%bccAddress%%d%%importance%%d%%userName%%d%%password%%d%%ipPort%%d%%ssl%
Call "%WORKSPACE%"\rftbats\CDOMessage.exe %emailCmd%
GOTO END

:Error
Echo.Report not found or is empty!!!
IF EXIST %HistoryDir% mkdir %HistoryDir%\JenkinsError 2>NUL
GOTO END

:Skip
Echo.No test report, it had been skipped!!!

:END
IF "%thisID%" == "" (
 IF EXIST %testLocker% DEL /f /q %testLocker% 2>NUL
)
EXIT 0