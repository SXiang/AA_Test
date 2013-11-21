*** Settings ***
Library           OperatingSystem
Library           AutoItLibrary    OutputDir=${outputdir}    CaptureScreenOnError=${True}
Library           Process
Library           String
Library           Collections

*** Keywords ***
CompareFile
    [Arguments]    ${file1}    ${file2}    ${fileEncoding}
    Set Test Variable    ${masterdata}    None
    Set Test Variable    ${actualdata}    None
    ${status}    ${value}    Run Keyword And Ignore Error    Should Match Regexp    ${file1}    (?i).*\.LOG
    Run Keyword Unless    '${status}'=='PASS'    Set Test Variable    ${fileEncoding}    UTF-8
    ${masterdata}=    OperatingSystem.Get File    ${file1}    ${fileEncoding}
    ${actualdata}=    OperatingSystem.Get File    ${file2}    ${fileEncoding}
    ${md}=    Run Keyword If    '${status}'=='PASS'    SanitizeLog    ${masterdata}
    ${ad}=    Run Keyword If    '${status}'=='PASS'    SanitizeLog    ${actualdata}
    Run Keyword Unless    '${status}'=='PASS'    Set Test Variable    ${md}    ${masterdata}
    Run Keyword Unless    '${status}'=='PASS'    Set Test Variable    ${ad}    ${actualdata}
    @{masterlist}=    Split To Lines    ${md}
    @{actuallist}=    Split To Lines    ${ad}
    Set Test Variable    ${status}    FAIL
    : FOR    ${line}    IN    @{masterlist}
    \    Run Keyword And Ignore Error    Continue For Loop If    '${line}'=='-DTD-'
    \    ${index}=    Get Index From List    ${actuallist}    ${line}
    \    Run Keyword Unless    ${index}>-1    Fail    '${line}' not found in actual '${file2}'
    #    ${status}=    Should Contain    ${actualdata}    ${masterdata}
    Set Test Variable    ${status}    PASS
    [Return]    ${status}

SanitizeLog
    [Arguments]    ${text}
    #    ${text}=    Replace String Using Regexp    ${text}    \\n    LINEFEED
    Set Test Variable    @{pattern}    [\\d]{1,2}[/-][0-9A-Za-z]{1,3}[/-][\\d]{2,4}
    #    Append To List    ${pattern}    [\\uEFEE-\\uFFFF]|\\p{Cc}|\\p{Cntrl}
    Append To List    ${pattern}    [\\d]{1,2}[/-][0-9A-Za-z]{1,3}[/-][\\d]{2,4}
    Append To List    ${pattern}    [\\d]{1,2}[-:][\\d]{1,2}[-:][\\d]{2}
    Append To List    ${pattern}    \\([A-Z]{3}-[\\d]{2}[/:][\\d]{2}\\)
    Append To List    ${pattern}    .*[a-zA-Z]:\\\\.*
    Append To List    ${pattern}    .*\\\\.*
    Append To List    ${pattern}    .*[@]+.*
    Set Test Variable    @{vxml}    .*>.*<.*    .*<.*>.*
    Set Test Variable    @{aclPattern}    Produced with ACL by:.*
    Append To List    ${aclPattern}    .*OUTPUTFOLDER.*
    Append To List    ${aclPattern}    [Uu]ser[: ].*    [sS]erial.*
    Append To List    ${aclPattern}    Total string space available:.*
    Append To List    ${aclPattern}    .*Switching to an alternate log file.*
    Append To List    ${aclPattern}    .*TestCompleted.*
    : FOR    ${temp}    IN    @{pattern}    @{vxml}    @{aclPattern}
    \    ${text}=    Replace String Using Regexp    ${text}    ${temp}    -DTD-
    #    ${text}=    Replace String    ${text}    LINEFEED    \\n
    [Return]    ${text}
