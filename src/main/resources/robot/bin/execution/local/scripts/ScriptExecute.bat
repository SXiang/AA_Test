@Echo OFF


rem Ironhide returns codes:
rem 1.  ACL start-up returns code:
SET ironhideCode_1000=Preference file not exists
SET ironhideCode_1001=Error with preference file
SET ironhideCode_1002=Upgrading from previous version
SET ironhideCode_1003=Using last saved project
SET ironhideCode_1004=Project name is missing
SET ironhideCode_1005=Project file not exist ( not found )
SET ironhideCode_1006=Project is read only
SET ironhideCode_1007=Project is in use
SET ironhideCode_1008=Trying to open project with extension *.OLD
SET ironhideCode_1009=Project format is incorrect
SET ironhideCode_1010=Project is not an Unicode project
SET ironhideCode_1011=Project was created by later ACL version
SET ironhideCode_1012=Can't write to log file
SET ironhideCode_1013=Script name is missing
SET ironhideCode_1014=Script not exists in the project
SET ironhideCode_1015=No Licence
SET ironhideCode_1016=Dll Not Found
SET ironhideCode_1017=UnkownError
rem 2. Command processing return codes:
SET ironhideCode_1=SAMPLE
SET ironhideCode_2=EXTRACT
SET ironhideCode_3=LIST
SET ironhideCode_4=TOTAL
SET ironhideCode_5=DEFINE
SET ironhideCode_6=COMMENT
SET ironhideCode_7=QUIT
SET ironhideCode_8=STOP
SET ironhideCode_9=BYE
SET ironhideCode_10=USE
SET ironhideCode_11=OPEN
SET ironhideCode_12=SAVE
SET ironhideCode_13=DISPLAY
SET ironhideCode_14=ACTIVATE
SET ironhideCode_15=CLOSE
SET ironhideCode_16=HELP
SET ironhideCode_17=COUNT
SET ironhideCode_18=STATISTICS
SET ironhideCode_19=HISTOGRAM
SET ironhideCode_20=STRATIFY
SET ironhideCode_21=SUMMARIZE
SET ironhideCode_22=EXPLAIN ( old help )
SET ironhideCode_23=GROUP
SET ironhideCode_24=ELSE
SET ironhideCode_25=END
SET ironhideCode_26=CANCEL ( only for script )
SET ironhideCode_27=SUBMIT ( should work as do command, but doesn't)
SET ironhideCode_28=DELETE
SET ironhideCode_29=RANDOM
SET ironhideCode_30=SORT
SET ironhideCode_31=FIND
SET ironhideCode_32=DIRECTORY
SET ironhideCode_33=TYPE	( same as PRINT )
SET ironhideCode_34=DUMP
SET ironhideCode_35=INDEX
SET ironhideCode_36=EXIT ( for Z\OS )
SET ironhideCode_37=SET
SET ironhideCode_38=EDIT	( for Z\OS )
SET ironhideCode_39=RUN	( for Z\OS )
SET ironhideCode_40=DO
SET ironhideCode_41=TOP
SET ironhideCode_42=EXPORT
SET ironhideCode_43=VERIFY
SET ironhideCode_44=SEEK
SET ironhideCode_45=JOIN
SET ironhideCode_46=MERGE
SET ironhideCode_47=SEQUENCE
SET ironhideCode_48=CALCULATE
SET ironhideCode_49=PRINT
SET ironhideCode_50=LOCATE
SET ironhideCode_51=RENAME
SET ironhideCode_52=SHOW	    ( for Z\OS )
SET ironhideCode_53=PROCESS  ( for Z\OS )
SET ironhideCode_54=COPY	 ( same as EXTRACT )
SET ironhideCode_55=REPORT
SET ironhideCode_56=EJECT  ( not implemented )
SET ironhideCode_57=MENU	 ( for Z\OS )
SET ironhideCode_58=LET
SET ironhideCode_59=ACCUMULATE
SET ironhideCode_60=CLS	  ( for Z\OS )
SET ironhideCode_61=ANALYZE ( for Z\OS )
SET ironhideCode_62=TAPE	 ( for Z\OS )
SET ironhideCode_63=ACCEPT  ( not valid )
SET ironhideCode_64=ASSIGN
SET ironhideCode_65=AGE
SET ironhideCode_66=CLASSIFY
SET ironhideCode_67=PROFILE
SET ironhideCode_68=DO REPORT
SET ironhideCode_69=LOOP
SET ironhideCode_70=PAUSE
SET ironhideCode_71=SIZE
SET ironhideCode_72=EVALUATE
SET ironhideCode_73=DIALOG  ( not valid )
SET ironhideCode_74=IF
SET ironhideCode_75=GAPS
SET ironhideCode_76=DUPS
SET ironhideCode_77=SQLOPEN  ( internal command )
SET ironhideCode_78=PASSWORD
SET ironhideCode_79=IMPORT
SET ironhideCode_80=REFRESH
SET ironhideCode_81=NOTIFY
SET ironhideCode_82=CONNECT
SET ironhideCode_83=RETRIEVE ( SAP background queries )
SET ironhideCode_84=FIELDSHIFT
SET ironhideCode_85=BENFORD
SET ironhideCode_86=CROSSTAB
SET ironhideCode_87=CRYSTAL
SET ironhideCode_88=ESCAPE
SET ironhideCode_89=NOTES
rem 3. ACL Exit codes
SET ironhideCode_200=Exit from Wizard for AS400
SET ironhideCode_201=Exit from Wizard because of memory allocation problem(s)
SET ironhideCode_202=ExitSystemErrorMessage
SET ironhideCode_203=Exit from ACL because of time already is expired
SET ironhideCode_204=Exit from ACL becase of time is expired
SET ironhideCode_205=Exit bacause activation failed
SET ironhideCode_206=Exit because of session numbers allowed already reached
SET ironhideCode_207=Exit because of memory initialization problem(s)
SET ironhideCode_208=Exit because offline mode canceled	( not called )
SET ironhideCode_209=ExitUnknownScriptError exit from acl_fatal()
SET ironhideCode_210=Exit because database profile password is missing
SET ironhideCode_211=ExitFailedConnectToServer
SET ironhideCode_212=ExitNotValidCommand ( for DIALOG, ACCEPT )
SET ironhideCode_213=ExitDialogPopUpped
SET ironhideCode_256=Exit because ACL failed to start




rem *********************************
SETLOCAL enabledelayedexpansion
SET _testDir=%1
SET _sutDir=%2
SET _imageName=%3
SET _sutfullname=%_sutDir%\%_imageName%
SET _ProjectName=%4
SET _doneFile=%5
SET _ScriptName=%6


Echo. Start "ACLScriptRunner" /D%_testDir% /MAX /SEPARATE /B /WAIT %_sutfullname% %_ProjectName% /x /vdoneFile="%_doneFile%" /b%_ScriptName%
pushd %_testDir%
Start "ACLScriptRunner" /D%_testDir% /MAX /SEPARATE /B /WAIT %_sutfullname% %_ProjectName% /x /vdoneFile="%_doneFile%" /b%_ScriptName%
SET /a ironhideCode=%ERRORLEVEL%
IF %ironhideCode% GTR 1 (
 Echo.ScriptExitCode_%ironhideCode%:!ironhideCode_%ironhideCode%! > %_testDir%\%_imageName%_return.LOG
 ) ELSE (
 Type NUL > %_testDir%\%_imageName%_return.LOG
 )
 
popd
