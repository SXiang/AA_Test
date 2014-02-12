Dim objWorkbook, objExcel,fso
    FileDir =  removeDQuotes(WScript.Arguments.Item(0))
    FromExt =  removeDQuotes(WScript.Arguments.Item(1))
	ToExt =  removeDQuotes(WScript.Arguments.Item(2))
	DeleteRowRange =  removeDQuotes(WScript.Arguments.Item(3))
	DeleteLastNumRows =  removeDQuotes(WScript.Arguments.Item(4))
	
Set objExcel = CreateObject("Excel.Application")
objExcel.Visible = False
objExcel.DisplayAlerts = False

Set fso = CreateObject("Scripting.FileSystemObject")
For Each f In fso.GetFolder(FileDir).Files
  If LCase(fso.GetExtensionName(f)) = FromExt Then
    Set wb = objExcel.Workbooks.Open(f.Path)
    newname = fso.BuildPath(wb.Path, fso.GetBaseName(wb.Name) & "." & ToExt)
    wb.SaveAs newname, -4143
	Set ws=wb.Worksheets(1)
	If InStr(DeleteRowRange,":") > 0 Then ws.Rows(DeleteRowRange).Delete()
	lastrow = ws.UsedRange.rows.count
	If DeleteLastNumRows > 0 Then ws.Rows((lastrow+1-DeleteLastNumRows) & ":" & lastrow).Delete()
	wb.Save
    wb.Close True
	f.Delete True
  End if
Next

objExcel.Quit
Set objExcel = Nothing
Set fso = Nothing


Function removeDQuotes(strContents)
    strFindText = Chr(34) & Chr(34)
    strReplaceText = Chr(34)
    removeDQuotes = Replace(strContents, strFindText, strReplaceText)
	
	'removeDQuotes 
End Function