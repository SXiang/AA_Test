SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=runexample.pom
SET outputDir=C:\ACL\%pomDir%

mkdir %outputDir% 2>nul
call mvn -U -f %pomServer%\%pomDir%\%pom% install
START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U -f %pomServer%\%pomDir%\%pom% exec:java