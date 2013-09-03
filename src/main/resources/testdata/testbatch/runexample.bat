SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\runexample.pom
SET outputDir=C:\ACL\%pomDir%

mkdir %outputDir% 2>nul

START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U -f %pomServer%\%pomDir%\%pom% clean