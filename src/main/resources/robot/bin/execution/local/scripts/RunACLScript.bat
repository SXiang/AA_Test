REM @Echo OFF
SET aclScriptRunner=..\..\..\implementation\resources\aclScriptRunner.robot
SET doneFile=TestCompleted
SET varFile=..\settings\user.py

REM  FAIL, WARN, INFO, DEBUG, TRACE
SET logLevel=TRACE

rem IF Not '%outDir%'=='' del %outDir%\*.* /Q 2>NUL
IF Not '%outDir%'=='' (
  IF exist '%outDir%'=='' DEL "%outDir%" /S /Q 2>NUL
  )
IF Not Exist %sutDir%\%imageName% (
  Echo. Not found? %sutDir%\%imageName%
  GOTO END
)
IF '%screenshotDir%'=='' SET screenshotDir=Screenshot

IF /I '%Encoding%'=='Unicode' (
   SET Encoding=Unicode
   ) ELSE (
   SET Encoding=NonUnicode
   )
pybot --name %Test_Name% ^
      --doc %Test_Doc% ^
	  --reporttitle %Report_Title% ^
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
	  --variable updateMasterFile:%UPDATE_MASTER_FILE% ^
	  --variable screenshotDir:%screenshotDir% ^
      --outputdir %outDir% ^
	  %nonCritical% ^
	  --settag @%OS_NAME% ^
	  --settag %sut%-%Encoding% ^
      %tagstatInclude% ^
	  --tagstatinclude %sut%-%Encoding% ^
	  %tagExclude% ^
	  --runemptysuite ^
	  -i %Encoding%AND%sut% ^
	  -L %logLevel% ^
	  %testSuite%
	  

:END
:EOF