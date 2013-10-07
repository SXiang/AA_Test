SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\Remote_RunFrontEnd_Chrome.pom
SET outputDir=C:\ACL\%pomDir%
SET sysPropPrefix=AutoQA.
SET toAddress = ramneet_kaur@acl.com
SET testCategory = Daily
SET axServerName = autoqawin2012.aclqa.local


Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
SET mainClass=ax.TestSuiteFrontEnd
SET testDataFile=testdata/ax/TestSuiteFrontEnd.xls
Rem ****************************************************


mkdir %outputDir% 2>nul


START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -D%sysPropPrefix%testDataFile=%testDataFile% ^
      -f %pomServer%\%pomDir%\%pom% ^
      clean

