cls

:Robot
:: Input parameters from calling script
IF '%OS_NAME%'=='' SET OS_NAME=64BitWIN7
IF '%testDir%'=='' SET testDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
IF '%updateMasterFile%'=='' SET updateMasterFile=False
IF '%testSuite%'=='' SET testSuite=%testDir%\TestSuite
IF '%sutDir%'=='' SET sutDir=D:\ACL\Analytics10.5
IF '%Encoding%'=='' SET Encoding=Unicode
:: Constant Variable
SET combineDir=%testDir%\TestResult\%OS_NAME%
SET projectDir=%testDir%\TestResult\%OS_NAME%\%Encoding%
SET projectBackupDir=%testDir%\Project\%Encoding%
SET tagInclusion=Test
SET tagInclusion2=Debug
SET tagstatinclude=--tagstatinclude %tagInclusion% ^
	       --tagstatinclude %tagInclusion2% ^
	       --tagstatinclude @* ^
	       --tagstatinclude OS:*
SET tagsCombine=%tagstatinclude%
:: Update ACL Projects
Call UpdateACLProject.bat

:Desktop
SET imageName=ACLWin.exe
SET sut=Desktop

:: ***Demo variables - will be removed later ***
SET Encoding=NonUnicode
SET sutDir=D:\ACL\Analytics10.5_Binary\Zaxxon\Build_58\Release
SET projectDir=%testDir%\TestResult\%OS_NAME%\%Encoding%
SET projectBackupDir=%testDir%\Project\%Encoding%
:: ***     End of demo variables             ***
SET sutDir=%sutDir%

Call :RunScript

:Ironhide
SET imageName=ACLScript.exe
SET sut=Ironhide

:: Demo variables - will be removed later
SET Encoding=Unicode
SET sutDir=C:\ACL\Analytics10.5
SET projectDir=%testDir%\TestResult\%OS_NAME%\%Encoding%
SET projectBackupDir=%testDir%\Project\%Encoding%
:: ***     End of demo variables             ***
SET sutDir=%sutDir%\%sut%

Call :RunScript

:Rebot
SET outDir=%combineDir%
SET Report_Title=ACL_Script_Runner_-_RF_Demo
SET Report_Doc=Demo_the_usage_of_Robot_Framework_for_ACL_Script_testing
Call CombineReport
:: Open report - Demo only
Call Start "Report" /D%outDir% chrome.exe report.html     
:EOF
pause

::********************************::
::Submodule
:RunScript

SET outDir=%combineDir%\%Encoding%\%sut%_TestReport
SET Report_Title=%sut%_%Encoding%
SET Report_Doc=Run_test_scripts_on_%sut%
:: Run Script
Call RunACLScript.bat
:: Set parameter for combining reports
IF Exist %outDir%\output.xml (
   SET outputxml=%outputxml% %outDir%\output.xml
   SET tagsCombine=%tagsCombine% --tagstatinclude %sut%-%Encoding%
)
:: Open report - Demo only
Call Start "Report" /D%outDir% chrome.exe report.html	  
GOTO :EOF
