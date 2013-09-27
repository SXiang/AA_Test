
:: XCSWITCH /Y: Suppresses prompting to confirm you want to overwrite existing destination file.
:: XCSWITCH /E: Copies directories and subdirectories, including empty ones. Same as /S /E. May be used to modify /T.
:: XCSWITCH /D: Copies files changed on or after the specified date.If no date is given, copies only those files whose source time is newer than the destination time.
:: XCSWITCH /R:  Overwrites read-only files
:UpdateProject
SET XCSWITCH=/Y /E /D /R /EXCLUDE:aclExclude
SET XCSWITCH_=/Y /D /R /EXCLUDE:aclExclude
SET XCSWITCH_REPLACE=/Y /R /E /I /EXCLUDE:aclExclude
SET XCSWITCH_Inclusion=/Y /R /EXCLUDE:aclExclude

Rem Update or Replace all projects
SET SWITCH=%XCSWITCH%
If /I '%updateMasterFile%'=='True' (
   DEL "%projectDir%" /S /Q
   SET SWITCH=%XCSWITCH_REPLACE%
   )
ECHO. XCOPY %projectBackupDir% %projectDir%\ %SWITCH%
      XCOPY %projectBackupDir% %projectDir%\ %SWITCH%
	  
Rem Update .ACL master file inside keyword

GOTO EOF