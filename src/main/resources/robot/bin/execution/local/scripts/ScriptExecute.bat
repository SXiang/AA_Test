@Echo OFF
SET _testDir=%1
SET _sutfullname=%2
SET _ProjectName=%3
SET _doneFile=%4
SET _ScriptName=%5

Echo. Start "ACLScriptRunner" /D%_testDir% /MAX /SEPARATE /B %_sutfullname% %_ProjectName% /x /vdoneFile="%_doneFile%" /b%_ScriptName%
pushd %_testDir%
Call Start "ACLScriptRunner" /D%_testDir% /MAX /SEPARATE /B %_sutfullname% %_ProjectName% /x /vdoneFile="%_doneFile%" /b%_ScriptName%
popd