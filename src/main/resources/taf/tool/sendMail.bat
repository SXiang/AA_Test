@ECHO OFF
SET smtpServer=xchg-cas-array.acl.com
IF "%USER_NAME%"=="" SET USER_NAME=QAMAIL
IF "%PASSWORD%"=="" SET PASSWORD=Password00
SET userName=ACL\%USER_NAME%
SET password=%PASSWORD%
SET fromAddress=%USER_NAME%@ACL.COM
SET fromName=%USER_NAME%

IF "%toAddress%"=="" SET toAddress=%USER_NAME%@ACL.com
IF "%attachFiles%"=="" SET attachFiles=
IF "%ccAddress%"=="" SET ccAddress=
IF "%bccAddress%"=="" SET bccAddress=
IF "%importance%"=="" SET importance=Normal
IF "%subject%"=="" SET subject=Test Subject
IF "%body%"=="" SET body=empty email contents please set the value of 'body' in your script
SET ipPort=25
SET ssl=1
SET d=,
set s= 
SET emailCmd=%subject%%d%%smtpServer%%d%%fromName%%d%%fromAddress%%d%%toAddress%%d%%body%%d%%attachFiles%%d%%ccAddress%%d%%bccAddress%%d%%importance%%d%%userName%%d%%password%%d%%ipPort%%d%%ssl%
ECHO \\Winrunner\RFT\SharedFiles\Tools\CDOMessage.exe %emailCmd%
Call \\Winrunner\RFT\SharedFiles\Tools\CDOMessage.exe %emailCmd%
PAUSE
:END
EXIT 0