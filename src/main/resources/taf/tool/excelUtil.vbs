Option Explicit
On Error Resume Next  
'===========================================
Rem Main menu invoke methods

    Dim argObj 
    Dim objStdOut 
    Dim objExcel 
    Dim objWorkBook 
    Dim objWorkSheet 
    Dim iNoError 
    Dim mName,bName,sName,result
    Dim cells

    setup()
    
    Select Case mName
    	Case "Activate"
    		result = activateBook
    	Case "SelectCell"
            result = selectCells
        Case "Delete"
            result = deleteSheet				
    End Select 
    
    teardown()

' *********** Select Cells *******************
Function selectCells ()    '--- as variant
    Dim column 
	    column = 1
	Dim row 
	    row = 1
    cells = argObj(1)
	'objExcel.DisplayAlerts = False;
	If Not cells = "" Then
       objWorkSheet.Range(cells).Select
	Else
	   objWorkSheet.Columns.item(column).Select
	   cells = "Column " & column & "Selected"
    End If
	
    If Err.Number = 0 Then
       selectCells = objWorkBook.Name & "," & objWorkSheet.Name & "," & cells
    Else
       selectCells = "NotFound - " & Err.Number
       'objStdOut.Write "Failed to activate workbook '" & bName &"', worksheet '" & sName & "'"
       Err.Clear
    End If
    'objExcel.DisplayAlerts = True;
End Function    


' *********** Activate  Book & Sheet *******************
Function activateBook ()    '--- as variant
    
    objWorkBook.Activate 
    objWorkSheet.Activate
    objWorkSheet.Range("A1").Select  
    If Err.Number = 0 Then
       activateBook = objWorkBook.Name & "," & objWorkSheet.Name
    Else
       activateBook = "NotFound"
       'objStdOut.Write "Failed to activate workbook '" & bName &"', worksheet '" & sName & "'"
       Err.Clear
    End If
    
End Function
' *********** Delete Sheet *******************
Function deleteSheet ()    '--- as variant
    
    objWorkBook.Activate 
    objWorkSheet.Activate
      
    'objWorkSheet.Delete	  
    If Err.Number = 0 Then
       deleteSheet = objWorkBook.Name & "," & objWorkSheet.Name & " has been deleted "
    Else
       deleteSheet = "Failed to delete sheet " & Err.Number
       'objStdOut.Write "Failed to activate workbook '" & bName &"', worksheet '" & sName & "'"
       Err.Clear
    End If
    
End Function

' *********** Setup *******************
Sub setup()
    Set argObj = WScript.Arguments
    Set objStdOut = WScript.StdOut
    If argObj.count > 0  Then
        mName = argObj(0)
	Else
	    mName = "Default"
	End If
	
	If mName = "Activate" Then
        bName = argObj(1)
        sName = argObj(2)
    Else 
        bName = "ActiveBook" 
        sName = "ActiveSheet" 
    End If
    
    '--Get current Excel object
    Set objExcel = GetObject(,"Excel.Application") 
        
		
    '--Get the workbook
    If (bName = "ActiveBook") Then 
       Set objWorkBook = objExcel.ActiveWorkbook   
    Else
       Set objWorkBook = objExcel.Workbooks(bName)  
    End If 
    
	
    '--Set up a reference to the sheet in the workbook
    If (Not sName = "ActiveSheet") Then 
       Set objWorkSheet = objWorkBook.WorkSheets(sName)
    Else    
       Set objWorkSheet = objWorkBook.ActiveSheet
    End If  
End Sub   
 
' *********** Teardown *******************
Sub teardown()
   ' Write result
    objStdOut.Write result
   '--Clear all the references to the objects
    Set objWorkBook = Nothing
    Set objWorkSheet = Nothing
	'	objExcel.quit
    Set objExcel = Nothing
    Set argObj = Nothing
    Set objStdOut = Nothing
End Sub

' *********** Print Items *******************
Function printItems(items)
   Dim value
   Dim item
   For Each item In items
       value = value & ", " & item.Name
   Next
   printItems = value
End Function
