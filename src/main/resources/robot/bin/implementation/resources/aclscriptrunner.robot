*** Settings ***
Library           OperatingSystem
Library           AutoItLibrary    OutputDir=${outputdir}    CaptureScreenOnError=${True}
Library           Process
Library           String
Library           Collections
Resource          fileutil.robot
Variables         ../../../Execution/Local/Settings/user.py

*** Keywords ***
ACLScriptRunner
    [Arguments]    ${ProjectName}    ${ScriptName}    ${Timeout}    @{MasterFiles}
    Set Test Variable    ${ProjectName_ori}    ${projectBackupDir}\\${ProjectName}
    Set Test Variable    ${ProjectName}    ${projectDir}\\${ProjectName}
    Set Test Variable    ${testDir}    ${ProjectName}.ACL\\..
    Comment    Set Test Variable    ${doneFile}    ${doneFile}.LOG
    Set Test Variable    ${status}    FAIL
    Set Test Variable    ${count}    0
    Set Test Variable    ${master}    MasterFile
    Set Test Variable    ${effectiveMaster}    EffectiveMaster
    Set Test Variable    ${fileEncoding}    UTF-16LE
    Run Keyword Unless    '${Encoding}'=='Unicode'    Set Test Variable    ${fileEncoding}    UTF-8
    cleanup    ${sutDir}    ${imageName}    ${testDir}\\${doneFile}    ${ProjectName}    ${ProjectName_ori}    ${master}
    ...    ${effectiveMaster}
    Start Process    ScriptExecute.bat    ${testDir}    ${sutDir}\\${imageName}    ${ProjectName}    ${doneFile}    ${ScriptName}
    Wait Until Created    ${testDir}\\${doneFile}.LOG    ${Timeout}
    : FOR    ${file}    IN    ${doneFile}.LOG    @{MasterFiles}
    \    ${status}=    File Should Exist    ${testDir}\\${file}
    \    ${count}=    Count Files In Directory    ${testDir}\\${effectiveMaster}    ${file}
    \    Run Keyword Unless    '${count}'=='1'    Copy File    ${testDir}\\${file}    ${testDir}\\${effectiveMaster}\\${file}
    \    Run Keyword If    '${updateMasterFile}'=='True'    Copy File    ${testDir}\\${file}    ${testDir}\\${effectiveMaster}\\${file}
    \    Run Keyword And Ignore Error    Copy File    ${testDir}\\${master}\\${file}    ${testDir}\\${effectiveMaster}\\${file}
    \    ${_status}=    fileutil.CompareFile    ${testDir}\\${effectiveMaster}\\${file}    ${testDir}\\${file}    ${fileEncoding}
    [Teardown]    closeapp    ${status}    ${ScriptName}    ${ImageName}

cleanup
    [Arguments]    ${sutDir}    ${imageName}    ${doneFile}    ${ProjectName}    ${ProjectName_ori}    ${master}
    ...    ${effectiveMaster}
    File Should Exist    ${sutDir}\\${imageName}
    ${path}    ${pName}=    Split Path    ${ProjectName}
    Run Keyword If    '${updateMasterFile}'=='True'    Copy File    ${ProjectName_ori}\\..\\${pName}.ACL    ${path}\\${pName}.ACL
    Create Directory    ${path}\\${master}\\
    Create Directory    ${path}\\${effectiveMaster}\\
    Run Keyword And Ignore Error    OperatingSystem.Run    xcopy ${path}\\${pName}.ACL ${path}\\${master}\\${pName}.ACL /Y /E /D /R
    ${status}    ${value}=    Run Keyword And Ignore Error    File Should Exist    ${path}\\${master}\\${pName}.ACL
    Run Keyword Unless    '${status}'=='PASS'    Copy File    ${path}\\${pName}.ACL    ${path}\\${master}\\${pName}.ACL
    Run Keyword If    '${status}'=='PASS'    Copy File    ${path}\\${master}\\${pName}.ACL    ${path}\\${pName}.ACL
    Set Test Variable    ${pref}    acl10.prf
    Set Test Variable    ${screenshotDir}    ${screenshotDir}\\${Encoding}.${ImageName}.${pName}.${ScriptName}
    Run Keyword Unless    '${Encoding}'=='Unicode'    Set Test Variable    ${pref}    aclwin10.prf
    Run Keyword And Ignore Error    Copy File    ..\\settings\\${pref}    ${path}\\${pref}
    Run Keyword And Ignore Error    Remove File    ${doneFile}.LOG
    Run Keyword And Ignore Error    Remove File    ${doneFile}.LIX
    Run Keyword And Ignore Error    Remove File    ${ProjectName}.LOG
    Run Keyword And Ignore Error    Remove File    ${ProjectName}.LIX
    Run Keyword And Ignore Error    Remove File    ${ProjectName}.AC

closeapp
    [Arguments]    ${status}    ${ScriptName}    ${ImageName}
    Run Keyword Unless    '${status}'=='PASS'    Get Screen Image    ${screenshotDir}_Timeout.jpeg
    Run Keyword And Ignore Error    OperatingSystem.Run    taskkill /F /T /IM ${imageName}
