@ECHO OFF
COLOR 1F
MODE CON: COLS=75 LINES=40
SETLOCAL enabledelayedexpansion
SET WORK_DIR=\\192.168.10.129\Automation\Shared\
SET CURLBIN=%WORK_DIR%curl-7.34.0-win32\bin\
IF NOT EXIST %CURLBIN% GOTO Error

:Request
FOR /F "skip=1 tokens=1-6" %%A IN ('WMIC Path Win32_LocalTime Get Day^,Hour^,Minute^,Month^,Second^,Year /Format:table') DO (
    IF NOT '%%D'=='' SET /A TODAY=%%F*10000+%%D*100+%%A
	)
	
	echo %today%
IF /I '%KeepHistory%'=='TRUE' Set Export_Dir=%Export_Dir%%today%\
MKDIR "%Export_DIR%"
SET index=1
FOR %%G IN (%Filters%) DO (
   SET FilterID=%%G
   SET JiraURL=%URL%/sr/jira.issueviews:searchrequest-%Export_Type%/!FilterID!/SearchRequest-!FilterID!.xls?tempMax=%tempMax%
   
   %CURLBIN%curl -o "%Export_DIR%\JiraFilter!index!_!FilterID!.htm" ^
                  --user %USER_NAME%:%PASSWORD% ^
				  --insecure ^
				  !JiraURL!
   
   SET /A index+=1
)
Call Cscript %WORK_DIR%\convertHtmToExcel.vbs "%Export_DIR%" htm %SaveAs% %DeleteRowRange% %DeleteLastNumRows%
GOTO END
:Error
Echo.is %curbin% the bin dir for your curl?
:End
::Exit 0