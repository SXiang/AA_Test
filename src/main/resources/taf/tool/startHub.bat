SET hubHost=%computername%
SET hubPort=4444
SET hubScript=\\192.168.10.129\Automation\MavenTest\AX_TestAutomation\taf\tool\
SET hubJar=%hubScript%selenium-server-standalone-2.37.0.jar
::SET hubStater=java -jar %hubJar% -role hub -port %hubPort%
SET log="%hubScript%log\selenium_hub%hubHost%.log"
SET hubStater=java -jar "%hubJar%" -role hub -hubConfig "%hubScript%hubConfig.json"
Echo. ACLQA Selenium Hub - %hubHost% Date: %date% - %time% > %log%
Echo. ********************************************************* >> %log%
Call %hubStater% >> %log%
