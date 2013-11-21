@Echo OFF

IF '%sutDir%'=='' GOTO END
Rem SET sutDir=D:\ACL\Analytics10.5

:Robot
:: Input parameters from calling script
IF '%testDir%'==''  SET testDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
IF '%testSuite%'=='' SET testSuite=\\nas2-dev\QA_A\AN\AutomationScriptRunner\TestSuite
IF '%Encoding%'=='' SET Encoding=Unicode
IF '%sut%'=='' SET sut=Desktop
IF '%imageName%'=='' SET imageName=ACLWin.exe
IF '%UPDATE_PROJECTS%'=='' SET UPDATE_PROJECTS=False
IF '%UPDATE_MASTER_FILE%'=='' SET UPDATE_MASTER_FILE=False
IF '%OS_NAME%'=='' SET OS_NAME=%computername%
IF '%pathToRun%'=='' SET pathToRun=\\nas2-dev\QA_A\AN\AutomationScriptRunner\bin\execution\local\scripts\

IF "%tagExclude%"=="" SET tagExclude=--exclude TBD

IF /I '%TEST_CATEGORY%'=='Daily' SET tagExclude=%tagExclude% --exclude Regression --exclude Smoke

IF /I '%TEST_CATEGORY%'=='Smoke' SET tagExclude=%tagExclude% --exclude Regression --exclude Daily

IF /I '%TEST_CATEGORY%'=='Regression' SET tagExclude=%tagExclude% --exclude Daily

rem IF /I '%TEST_CATEGORY%'=='Baseline' SET tagExclude=%tagExclude%


:: Constant Variable
rem IF '%projectDir%'=='' SET projectDir=%testDir%\TestResult\%TestBy%\%OS_NAME%\%Encoding%
rem IF '%projectBackupDir%'=='' SET projectBackupDir=%testDir%\Project\%Encoding%

IF '%projectDir%'=='' SET projectDir=%testDir%\TestResult\%TestBy%\%OS_NAME%
IF '%projectBackupDir%'=='' SET projectBackupDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner\Project\
SET projectDir=%projectDir%\%Encoding%
SET projectBackupDir=%projectBackupDir%\%Encoding%

IF '%reportDir%'=='' SET reportDir=%projectDir%\RobotReport\%sut%
SET outDir=%reportDir%

:: Update ACL Projects
IF /I Not '%ProjectUpdated%'=='True' Call %pathToRun%UpdateACLProject.bat
SET Test_Name=%sut%_%Encoding%(%OS_NAME%)
SET Test_Doc=Run_test_scripts_on_%sut%
IF '%Project%'=='' SET Project=Zaxxon?
IF '%Revision_Num%'=='' SET Revision_Num=Latest?
SET Report_Title=%PROJECT%#%Revision_Num%_Test_Report(%sut%_%Encoding%)
:: Run Script

Call %pathToRun%RunACLScript.bat

:CombineInfo
IF Exist %outDir%\output.xml (
   SET outputxml=%outputxml% %outDir%\output.xml
   SET tagsCombine=%tagsCombine% --tagstatinclude %sut%-%Encoding%
)

:END
:EOF