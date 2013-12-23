SET pomServer=\\192.168.10.129
SET pomDir=Automation\MavenTest\AX_TestAutomation
SET pom=testdata\testbatch\Remote_RunFrontEnd_Chrome.pom
SET outputDir=C:\ACL\%pomDir%
SET sysPropPrefix=AutoQA.
SET toAddress=kelvin_huang@acl.com
SET testCategory=Daily
SET axServerName=ATVM-2008-AXPSG.aclqa.local
SET appLocale=EN


Rem ****************************************************
Rem *** These 2 lines could be changed for your test ***
SET mainClass=ax.TestSuiteFrontEnd
SET testDataFile=testdata/ax/TestSuiteFrontEnd.xls
Rem ****************************************************


mkdir %outputDir% 2>nul


START "Run Maven Script..." /D"%outputDir%" /WAIT /B mvn -U ^
      -Dexec.mainClass=%mainClass% ^
      -DtestDataFile=%testDataFile% ^
      -DappLocale=%appLocale% ^
      -DtestCategory=%testCategory% ^
      -DaxServerName=%axServerName% ^
      -DtoAddress=%toAddress% ^
      -DsysPropPrefix=%sysPropPrefix% ^
      -f %pomServer%\%pomDir%\%pom% ^
      clean
