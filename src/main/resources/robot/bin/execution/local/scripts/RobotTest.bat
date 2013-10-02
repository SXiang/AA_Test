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

:: Constant Variable
SET projectDir=%testDir%\TestResult\%TestBy%\%OS_NAME%\%Encoding%
SET projectBackupDir=%testDir%\Project\%Encoding%
IF '%reportDir%'=='' SET reportDir=%projectDir%\RobotReport\%sut%
SET outDir=%reportDir%

:: Update ACL Projects
IF /I Not '%ProjectUpdated%'=='True' Call UpdateACLProject.bat
SET Test_Name=%sut%_%Encoding%(%OS_NAME%)
SET Test_Doc=Run_test_scripts_on_%sut%
IF '%Project%'=='' SET Project=Zaxxon?
IF '%Revision_Num%'=='' SET Revision_Num=Latest?
SET Report_Title=%PROJECT%#%Revision_Num%_Test_Report(%sut%_%Encoding%)
:: Run Script
Call RunACLScript.bat
:CombineInfo
IF Exist %outDir%\output.xml (
   SET outputxml=%outputxml% %outDir%\output.xml
   SET tagsCombine=%tagsCombine% --tagstatinclude %sut%-%Encoding%
)

:END
:EOF