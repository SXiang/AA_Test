
SET aclScriptRunner=..\..\..\implementation\resources\aclScriptRunner.txt
SET doneFile=TestCompleted
SET varFile=..\settings\user.py
REM  FAIL, WARN, INFO, DEBUG, TRACE
SET logLevel=TRACE


del %outDir%\*.* /Q
IF Not Exist %sutDir%\%imageName% (
  GOTO END
)

pybot --name %Report_Title% ^
      --doc %Report_Doc% ^
	  --variablefile %varFile% ^
      --variable testDir:%testDir% ^
      --variable testSuite:%testSuite% ^
      --variable aclScriptRunner:%aclScriptRunner% ^
	  --variable doneFile:%doneFile% ^
	  --variable sutDir:%sutDir% ^
	  --variable imageName:%imageName% ^
	  --variable Encoding:%Encoding% ^
	  --variable projectDir:%projectDir% ^
	  --variable projectBackupDir:%projectBackupDir% ^
	  --variable updateMasterFile:%updateMasterFile% ^
      --outputdir %outDir% ^
	  --noncritical %tagInclusion2% ^
	  --settag %sut%-%Encoding% ^
	  --settag OS:%OS_NAME% ^
	  --tagstatinclude %sut%-%Encoding% ^
	  %tagstatinclude% ^
	  --runemptysuite ^
	  -i %tagInclusion2%AND%Encoding%AND%sut% ^
	  -i %tagInclusion%AND%Encoding%AND%sut% ^
	  -L %logLevel% ^
	  %testSuite%
:END