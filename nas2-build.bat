SET SRCROOT=\\nas2-dev\devroot\gitSubmodules\TestAutomation
SET PASSWORD=@DEV4bu1ld#
SET UserFullName=ACL\acldbld

NET USE %SRCROOT% /DELETE
NET USE %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes

PAUSE