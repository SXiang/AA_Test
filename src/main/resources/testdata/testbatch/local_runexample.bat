Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
SET mainClass=ax.TestSuiteExample
SET testDataFile=testdata/ax/TestSuiteExample.xls
Rem ****************************************************


SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\local_runexample.pom
SET outputDir=C:\ACL\%pomDir%

mkdir %outputDir% 2>nul

START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DoutputDir=%outputDir% ^
      -f %pom% ^
      clean