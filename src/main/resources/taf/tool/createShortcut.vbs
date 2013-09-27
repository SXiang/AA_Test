    shortcutName =  removeDQuotes(WScript.Arguments.Item(0))
    targetPath =  removeDQuotes(WScript.Arguments.Item(1))
	arguments =  removeDQuotes(WScript.Arguments.Item(2))
	hotKey =  removeDQuotes(WScript.Arguments.Item(3))
	iconLocation =  removeDQuotes(WScript.Arguments.Item(4))
	workingDir =  removeDQuotes(WScript.Arguments.Item(5))
	description = removeDQuotes(WScript.Arguments.Item(6))


'    shortcutName =  "ACL Ironhide 10"
'    targetPath =  "C:\WINDOWS\system32\cmd.exe"
'	iconLocation =  "\\192.168.10.129\Automation\Monaco\userContent\RFTBats\acl10icon.exe" 
'	workingDir =  "C:\ACL\Analytics10\Ironhide"
'	description = "ACLQA_Automation"

set WshShell = WScript.CreateObject("WScript.Shell")
strDesktop = WshShell.SpecialFolders("Desktop")
'strDesktop = WshShell.SpecialFolders("AllUsersDesktop")
strShortcut = strDesktop & "\" & shortcutName & ".lnk"
Set fso = CreateObject("Scripting.FileSystemObject")
If fso.FileExists(strShortcut) Then fso.DeleteFile(strShortcut)
set oShellLink = WshShell.CreateShortcut(strShortcut)
oShellLink.TargetPath = targetPath
oShellLink.Arguments = arguments
oShellLink.HotKey = hotKey
oShellLink.WindowStyle = 1
oShellLink.IconLocation = iconLocation
oShellLink.Description = description
oShellLink.WorkingDirectory = workingDir
oShellLink.Save

Function removeDQuotes(strContents)
    strFindText = Chr(34) & Chr(34)
    strReplaceText = Chr(34)
    removeDQuotes = Replace(strContents, strFindText, strReplaceText)
	
	'removeDQuotes = strContents
End Function