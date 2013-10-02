@ECHO OFF

SET TEAM_NAME=AN_TestAutomation
SET Project=Zoxxon

rem SET AutomationScriptDir=\\192.168.10.129\aclqa\Automation\Jenkins\userContent\CI_Content
SET AutomationScriptDir=\\192.168.10.129\Automation\MavenTest\%TEAM_NAME%
SET RobotBats=taf\jenkins\userContent\CI_Content\RobotBats
SET RobotExecution=robot\bin

SET JenkinsServer=\\biollante02
IF NOT EXIST %AutomationScriptDir% NET USE %AutomationScriptDir% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL
IF NOT EXIST %JenkinsServer% NET USE %JenkinsServer% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL
IF NOT EXIST %AutomationScriptDir% GOTO Error
SET AutomationDestDir=%JENKINS_HOME%\userContent\RobotBats
IF /I NOT '%COMPUTERNAME%' == 'BIOLLANTE02' (
  SET AutomationDestDir=%JenkinsServer%\userContent\RobotBats
  SET AutomationScriptDir=D:\ACL\Automation\AN_TestAutomation\src\main\resources
)

:GetUpdates
SET XCSWITCH=/Y /E /R /I
ECHO START "" /B /WAIT XCOPY "%AutomationScriptDir%\%RobotBats%" "%AutomationDestDir%"\ %XCSWITCH%
START "" /B /WAIT XCOPY "%AutomationScriptDir%\%RobotBats%" "%AutomationDestDir%"\ %XCSWITCH%
ECHO START "" /B /WAIT XCOPY "%AutomationScriptDir%\%RobotExecution%" "%AutomationDestDir%"\ %XCSWITCH%
START "" /B /WAIT XCOPY "%AutomationScriptDir%\%RobotExecution%" "%AutomationDestDir%"\ %XCSWITCH%
:DONE
ECHO Update Automation Scripts Completed!!!
GOTO EOF
:Error
Echo Failed to update automation scripts from %AutomationScriptDir% to %AutomationDestDir%\, check it out!
GOTO EOF
:EOF

EXIT 0
