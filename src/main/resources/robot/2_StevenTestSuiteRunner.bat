:Test case info
::TestBy will be the root of test working folder
::TestDir is the directory of ACL Projects which contains: Project->Unicode(NonUnicode)->'relative project path in test cases'
::TestSuite can be a directory or test case files, separated by a single space
::Set DeleteWorkspace to true to reset projects.
SET TestBy=Demo
SET TestDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
SET TestSuite=%TestDir%\TestSuite\Demo
SET DeleteWorkspace=False

:Build info
SET Project=Zaxxon
SET Revision_Num=126
rem SET Revision_Num=106

:Desktop info
SET DesktopType=Unicode
SET DesktopPath=C:\ACL\Analytics10.5_Binary\Zaxxon\Build_126\Unicode
rem SET DesktopType=NonUnicode
rem SET DesktopPath=D:\ACL\Analytics10.5_Binary\Zaxxon\.\Build_106.\Release

:Ironhide info
SET IronhideType=Unicode
SET IronhidePath=C:\ACL\Analytics10.5_Binary\Zaxxon\Build_126\IronhideUnicode
rem SET IronhideType=NonUnicode
rem SET IronhidePath=D:\ACL\Analytics10.5_Binary\Zaxxon\.\Build_106.\IronhideRelease

:Report info - use '_' instead of any single space
::Test Name is the name of this test suite
SET _Test_Doc=Demo_the_usage_of_Robot_Framework_for_ACL_Script_testing
SET _Report_Title=ACL_Script_Runner[Demo]
SET _Test_Name=Robot


:Run
SET pathToRun=bin\execution\local\scripts
pushd %pathToRun%
Call StartRobotTest.bat