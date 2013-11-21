@Echo OFF

:: Rebot For Jenkins - Steven
rem SET Test_Doc=Demo_the_usage_of_Robot_Framework_for_ACL_Script_testing
rem SET Report_Title=%PROJECT%#%Revision_Num%_-_ACL_Script_Runner_-_Demo
SET Test_Doc=ACL_Script_testing
SET Report_Title=%PROJECT%#%Revision_Num%_-_ACL_Script_Runner_-_Robot
SET Test_Name=Robot
SET ReportDir=%workspace%\TestReport
Set screenshotDir=Screenshot

SET tagsCombine=--tagstatinclude Desktop-Unicode ^
--tagstatinclude Desktop-NonUnicode ^
--tagstatinclude Ironhide-Unicode ^
--tagstatinclude Ironhide-NonUnicode

set /p _outputxml=<%reportFile%.robot 2>NUL

rem del %reportDir%\*.* /Q 2>NUL
IF Not '%reportDir%'=='' (
  IF exist '%reportDir%'=='' DEL "%reportDir%" /S /Q 2>NUL
  )
rem IF '%_outputxml%'=='' (
rem    Echo. Failed to get out put xmls for rebot?
rem    Goto End
rem   )

:: Rebot - Combine reports 
Call Rebot --name %Test_Name% ^
--doc %Test_Doc% ^
--reporttitle %Report_Title% ^
%nonCritical% ^
%tagExclude% ^
%tagstatInclude% ^
%tagsCombine% ^
--removekeywords passed ^
--removekeywords for ^
--outputdir %ReportDir% ^
--output output.xml ^
%_outputxml%

:CopyArtifacts
Echo.Call XCOPY \\biollante02\userContent\TestReport\%screenshotDir% %ReportDir%\%screenshotDir%\ /Y /R /E /I
Call XCOPY \\biollante02\userContent\TestReport\%screenshotDir% %ReportDir%\%screenshotDir%\ /Y /R /E /I
:EOF
