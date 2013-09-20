SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\local_runexample.pom
SET outputDir=C:\ACL\%pomDir%
SET sysPropPrefix=AutoQA.

Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
SET mainClass=ax.TestSuiteExample
SET testDataFile=testdata/ax/TestSuiteExample.xls
Rem ****************************************************

mkdir %outputDir% 2>nul

START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -D%sysPropPrefix%testDataFile=%testDataFile% ^
      -f %pom% ^
      clean