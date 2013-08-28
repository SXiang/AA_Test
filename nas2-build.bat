SET SRCROOT=\\nas2-dev\devroot\gitSubmodules\TestAutomation
SET DeviceName=H:
SET PASSWORD=@DEV4bu1ld#
SET UserFullName=ACL\acldbld

NET USE %SRCROOT% /DELETE
NET USE %DeviceName% /DELETE
NET USE %DeviceName% %SRCROOT% "%PASSWORD%" /USER:"%UserFullName%" /P:Yes

PAUSE