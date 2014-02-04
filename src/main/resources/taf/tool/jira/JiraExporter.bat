@ECHO OFF
COLOR 1F
MODE CON: COLS=75 LINES=40

:INPUT
Rem *** Requried variables ***
SET Filters=13508 13600 11504
SET USER_NAME=xxxxxxxx@acl.com
SET PASSWORD=xxxxxxx
Rem **************************

Rem *** Optional variables ***
SET Export_Type=excel-all-fields
SET TempMax=1000
SET URL=https://aclgrc.atlassian.net
SET Export_DIR=%userprofile%\Desktop\JiraDownload\
Rem SaveAs = xls, other format is not supported yet
SET SaveAs=xls
Rem KeepHistory = False|True, if 'True', all files will be exported to sub folder named 'yyyymmdd', if 'False', overwrite pre exported file in Export_DIR
SET KeepHistory=True
Rem DeleteRowRange = fromRow:toRow|None, if 'None', no row will be deleted
SET DeleteRowRange=1:3
Rem DeleteLastNumRows = the last num rows to be deleted, if '0', no deletion.
SET DeleteLastNumRows=1
Rem **************************

:Get
Call \\192.168.10.129\Automation\Shared\ExportFromJira.bat

:END
EXIT 0