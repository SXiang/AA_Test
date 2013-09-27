SET testDir=%1
SET sut=%2
SET ProjectName=%3
SET doneFile=%4
SET ScriptName=%5


Start "ACLScriptRunner" /D%testDir% /MAX /SEPARATE /B %sut% %ProjectName% /x /vdoneFile="%doneFile%" /b%ScriptName%