
Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
Rem ** Set mainClass to ax.testdriver.restapi.TestDriverExample
Rem ** for a single test suite of test cases
Rem ** ax.TestSuiteExample is for a suite of test suites
Rem ** 
SET mainClass=ax.TestSuiteExample
SET testDataFile=testdata/ax/TestSuiteExample.xls
Rem ****************************************************



Rem ******* Other parameters ***************************
Rem ** All properties in pom can be specified here    **
SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\remote_runexample.pom
SET outputDir=C:\ACL\%pomDir%
Rem ****************************************************



Rem *******  Run Test **********************************

mkdir %outputDir% 2>nul

START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DoutputDir=%outputDir% ^
      -f %pomServer%\%pomDir%\%pom% ^
      clean