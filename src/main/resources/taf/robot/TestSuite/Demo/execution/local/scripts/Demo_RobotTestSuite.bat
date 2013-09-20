cls
SET aclScriptRunner=..\..\..\implementation\resources\aclScriptRunner.txt
SET doneFile=TestCompleted
SET varFile=..\settings\user.py
SET testDir=\\nas2-dev\QA_A\AN\AutomationScriptRunner
SET testSuite=%testDir%\TestSuite\Demo
REM  FAIL, WARN, INFO, DEBUG, TRACE
SET logLevel=TRACE

SET sut=Desktop
SET sutDir=C:\ACL\Analytics10.5
SET Encoding=Unicode

SET tagInclusion=Demo
SET subOutputDir=%Encoding%\%tagInclusion%\%sut%\
SET imageName=ACLWin.exe
SET outDir=%testDir%\TestResult\%subOutputDir%

del %outDir%\*.* /Q
pybot --variablefile %varFile% ^
      --variable testDir:%testDir% ^
      --variable testSuite:%testSuite% ^
      --variable aclScriptRunner:%aclScriptRunner% ^
	  --variable doneFile:%doneFile% ^
	  --variable sutDir:%sutDir% ^
	  --variable imageName:%imageName% ^
	  --variable Encoding:%Encoding% ^
      --outputdir %outDir% ^
	  -i %tagInclusion%AND%Encoding%AND%sut% ^
	  --tagstatcombine %tagInclusion%AND%Encoding%AND%sut%:%sut%-%Encoding%[%tagInclusion%] ^
	  -L %logLevel% ^
	  %testSuite%

SET sut=Ironhide
SET sutDir=C:\ACL\Analytics10.5\%sut%
SET Encoding=Unicode
SET tagInclusion=Demo
SET subOutputDir=%Encoding%\%tagInclusion%\%sut%\
SET imageName=ACLScript.exe
SET outDir=%testDir%\TestResult\%subOutputDir%
del %outDir%\*.* /Q
pybot --variablefile %varFile% ^
      --variable testDir:%testDir% ^
      --variable testSuite:%testSuite% ^
      --variable aclScriptRunner:%aclScriptRunner% ^
	  --variable doneFile:%doneFile% ^
	  --variable sutDir:%sutDir% ^
	  --variable imageName:%imageName% ^
	  --variable Encoding:%Encoding% ^
      --outputdir %outDir% ^
	  -i %tagInclusion%AND%Encoding%AND%sut% ^
	  --tagstatcombine %tagInclusion%AND%Encoding%AND%sut%:%sut%-%Encoding%[%tagInclusion%]
	  -L %logLevel% ^
	  %testSuite%
	  
