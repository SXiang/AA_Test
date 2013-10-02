

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
SET outputDir=C:\ACL\%pomDir%
Rem ****************************************************

Rem *******  Run Test **********************************

SET pom=testdata\testbatch\local_runexample.pom

mkdir %outputDir% 2>nul

START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DoutputDir=%outputDir% ^
      -f %pom% ^
      clean