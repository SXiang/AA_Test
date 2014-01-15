<<<<<<< HEAD
SET hubHost=192.168.10.129
SET hubPort=4444
SET hostIP=%computername%

SET hubScript=\\192.168.10.129\Automation\MavenTest\AX_TestAutomation\taf\tool\
SET hubJar=%hubScript%selenium-server-standalone-2.37.0.jar
::SET hubStater=java -jar %hubJar% -role hub -port %hubPort%
SET log="%hubScript%log\selenium_node%hostIP%.log"
SET remotedriver=-Dwebdriver.ie.driver=%hubScript%IEDriverServer.exe ^
	 -Dwebdriver.chrome.driver=%hubScript%chromedriver.exe
SET nodeStater=java -classpath %hubJar% -jar "%hubJar%" -role node %hostIP% -nodeConfig "%hubScript%nodeConfig.json" %remotedriver%
Echo. ACLQA Selenium Node - %hostIP% Date: %date% - %time% > %log%
Echo. ********************************************************* >> %log%
Call %nodeStater% >> %log%
=======
SET hubHost=192.168.10.129
SET hubPort=4444
SET hostIP=%computername%

SET hubScript=\\192.168.10.129\Automation\MavenTest\AX_TestAutomation\taf\tool\
SET hubJar=%hubScript%selenium-server-standalone-2.37.0.jar
::SET hubStater=java -jar %hubJar% -role hub -port %hubPort%
SET log="%hubScript%log\selenium_node%hostIP%.log"
SET remotedriver=-Dwebdriver.ie.driver=%hubScript%IEDriverServer.exe ^
	 -Dwebdriver.chrome.driver=%hubScript%chromedriver.exe
SET nodeStater=java -classpath %hubJar% -jar "%hubJar%" -role node %hostIP% -nodeConfig "%hubScript%nodeConfig.json" %remotedriver%
Echo. ACLQA Selenium Node - %hostIP% Date: %date% - %time% > %log%
Echo. ********************************************************* >> %log%
Call %nodeStater% >> %log%
>>>>>>> a7f968b11d86902996535ee41b74b1f99919bc8b
