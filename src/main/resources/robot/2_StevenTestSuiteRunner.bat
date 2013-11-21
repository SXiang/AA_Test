:Test case info
::TestBy will be the root of test working folder
::TestDir is the directory of ACL Projects which contains: Project->Unicode(NonUnicode)->'relative project path in test cases'
::TestSuite can be a directory or test case files, separated by a single space
::Set DeleteWorkspace to true to reset projects.
SET TestBy=Demo
SET TestDir=D:\ACL\Automation\AutomationScriptRunner
SET TestSuite=\\nas2-dev\QA_A\AN\AutomationScriptRunner\TestSuite
:: *****Daily, Regression ******
SET TEST_CATEGORY=Daily
SET DeleteWorkspace=False
SET DisableProjectUpdate=False
SET tagExclude=--exclude TBD
rem SET tagExclude=--exclude Nothing
REM SET OS_NAME=%computername%
rem SET OS_NAME=JUN-WANG
:Build info
SET Project=Frogger
SET Revision_Num=32

:Desktop info
SET DesktopType=Unicode
SET DesktopPath=C:\ACL\Analytics11_Binary\%Project%\Build_%Revision_Num%\Unicode
rem SET DesktopType=NonUnicode
rem SET DesktopPath=C:\ACL\Analytics10.5_Binary\%Project%\Build_%Revision_Num%.\Release

:Ironhide info
rem SET IronhideType=Unicode
rem SET IronhidePath=C:\ACL\Analytics10.5_Binary\%Project%\Build_%Revision_Num%\IronhideUnicode
SET IronhideType=Unicode
SET IronhidePath=C:\ACL\Analytics11_Binary\%Project%\Build_%Revision_Num%.\IronhideUnicode

:Report info - use '_' instead of any single space
::Test Name is the name of this test suite
SET _Test_Doc=Demo_the_usage_of_Robot_Framework_for_ACL_Script_testing
SET _Report_Title=ACL_Script_Runner[Demo]
SET _Test_Name=Robot


:Run
:Run
SET pathToRun=\\nas2-dev\QA_A\AN\AutomationScriptRunner\bin\execution\local\scripts\
IF NOT EXIST %pathToRun% (
   IF /I NOT "%USER_NAME%"=="" NET USE * %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes
   IF /I "%USER_NAME%"=="" NET USE * %SRCROOT% /P:Yes
   )
pushd %pathToRun% 2>NUL
Call %pathToRun%StartRobotTest.bat
popd %pathToRun% 2>NUL
pause