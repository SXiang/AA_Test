@Echo OFF
rem IF Not '%combineReportDir%'=='' del %combineReportDir%\*.* /Q 2>NUL
Call Rebot --name %_Test_Name% ^
--doc %_Test_Doc% ^
--reporttitle %PROJECT%#%Revision_Num%-%_Report_Title% ^
%nonCritical% ^
%tagExclude% ^
%tagstatInclude% ^
%tagsCombine% ^
--removekeywords passed ^
--removekeywords for ^
--outputdir %combineReportDir% ^
--output output.xml ^
%outputxml%
:END
:EOF