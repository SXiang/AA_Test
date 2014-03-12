MsgBox(0, "automation", "Monitoring windows popup/security warning etc..", 2)

$warningTitle = "[CLASS:SunAwtDialog]"
$warningTitle = "Security Warning"
$warningText = ""

If WinWait($warningTitle, $warningText, 600) <> 0 Then
	  MsgBox(0,"Automation", "Found popup " & $warningTitle,2)
	  WinActivate($warningTitle, $warningText)
	  If WinWaitActive($warningTitle, $warningText,600) <> 0 Then
		  ;MsgBox(0, "Automation", "Activate popup" & $warningTitle,2)
          Send("{TAB}{TAB}{SPACE}")
		  Sleep(2000)
          Send("{TAB}{ENTER}")
	  EndIf
  EndIf

  MsgBox(0, "Automation", "Finished popup monitoring!", 2)

