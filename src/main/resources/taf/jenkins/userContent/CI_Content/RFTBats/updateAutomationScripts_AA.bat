@ECHO OFF

SET TEAM_NAME=AN_Automation
SET Project=Zoxxon

rem SET AutomationScriptDir=\\192.168.10.129\aclqa\Automation\Jenkins\userContent\CI_Content
SET AutomationScriptDir=\\192.168.10.129\Automation\%Team_Name%\userContent\CI_Content
SET JenkinsServer=\\biollante02
IF NOT EXIST %AutomationScriptDir% NET USE %AutomationScriptDir% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL
IF NOT EXIST %JenkinsServer% NET USE %JenkinsServer% "%PASSWORD%" /USER:"ACL\%USER_NAME%" /P:Yes 2>NUL
IF NOT EXIST %AutomationScriptDir% GOTO Error
SET AutomationDestDir=%JENKINS_HOME%\userContent
IF /I NOT '%COMPUTERNAME%' == 'BIOLLANTE02' (
  SET AutomationDestDir=%JenkinsServer%\userContent
)

:GetUpdates
SET XCSWITCH=/Y /E /R /I
ECHO START "" /B /WAIT XCOPY "%AutomationScriptDir%" "%AutomationDestDir%"\ %XCSWITCH%
START "" /B /WAIT XCOPY "%AutomationScriptDir%" "%AutomationDestDir%"\ %XCSWITCH%
:DONE
ECHO Update Automation Scripts Completed!!!
GOTO EOF
:Error
Echo Failed to update automation scripts from %AutomationScriptDir% to %AutomationDestDir%\, check it out!
GOTO EOF
:EOF

EXIT 0
