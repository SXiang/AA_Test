@ECHO OFF
        SET smtpServer=xchg-cas-array.acl.com
IF /I '%USER_NAME%'=='' SET USER_NAME=QAMAIL
IF /I '%PASSWORD%'=='' SET PASSWORD=Password00
		SET userName=ACL\%USER_NAME%
		SET password=%PASSWORD%
		SET fromAddress=%USER_NAME%@ACL.COM
		SET fromName=%USER_NAME%
IF /I '%subject%'=='' SET subject=
IF /I '%toAddress%'=='' SET toAddress=%USER_NAME%@ACL.com
IF /I '%body%'=='' SET body=empty email contents please set the value of 'body' in your script
IF /I '%attachFiles%'=='' SET attachFiles=
IF /I '%ccAddress%'=='' SET ccAddress=
IF /I '%bccAddress%'=='' SET bccAddress=
IF /I '%importance%'=='' SET importance=Normal
		
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