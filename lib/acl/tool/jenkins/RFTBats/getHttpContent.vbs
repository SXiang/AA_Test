' Set your settings
    strFileURL =  WScript.Arguments.Item(0)
    strHDLocation =  WScript.Arguments.Item(1)
	jenkinsURL =  WScript.Arguments.Item(2) 
	buildURL =  WScript.Arguments.Item(3)
	subject = WScript.Arguments.Item(4)
'	"href= src= URL= loadScript(
	find1 =  " href=" 
	find2 =  " src="
	find3 =  "URL="
	find4 =  "loadScript("
	find5 =  "url"
	find6 =  "createSearchBox("
	find10 = "Â"
	
' Fetch the file
Set objFSO = Createobject("Scripting.FileSystemObject")
Set objTextStream = objFSO.OpenTextFile(strHDLocation, 1)
text = objTextStream.ReadAll
objTextStream.Close
REM if objFSO.FileExists(strHDLocation) Then objFSO.DeleteFile strHDLocation
strHDLocation = strHDLocation & "email.html"
if objFSO.FileExists(strHDLocation) Then objFSO.DeleteFile strHDLocation
Rem MsgBox("Text" & text)
If IsNull(text) Or jenkinsURL="" Or buildURL="" Then WScript.Quit 0

'---------------------------------
find = find1
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL
text = Replace(text,findStr,replaceStr)
findStr = find & """#"
replaceStr = find & "##""#"
text = Replace(text,findStr,replaceStr)

findStr = find & """"
replaceStr = find & """" & buildURL
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)

find = find1
findStr = find & "'/"
replaceStr = find & "##'" & jenkinsURL
text = Replace(text,findStr,replaceStr)
findStr = find & "'"
replaceStr = find & "'" & buildURL
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)

find = find2
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)


find = find3
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)


find = find4
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)

find = find5
findStr = find & "(/"
replaceStr = find & "##(" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "("
replaceStr = find & "(" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)


find = find6
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)
findStr = " ""/"
replaceStr = " """ & jenkinsURL
text = Replace(text,findStr,replaceStr)

text = Replace(text,find10,"")

'---------------Header Removal------------
startString = "<a href="&""""&"#skip2content"
endString = "<table id="&""""&"main-table"
replaceStr = "<br />"&"<b>ACLQA JENKINS - <a href="&""""&buildURL&""""&">"&Replace(subject,"""","")&"</a></b>" & "<br /><hr />"
startPt = inStr(text,startString)
endPt = inStr(text,endString)

If startPt>0 And endPt>=startPt Then
   findStr = Mid(text,startPt,endPt-startPt)
   text = Replace(text,findStr,replaceStr)
End If

'--------------Footer Removal ------------
startString = "<table width=""100%""><tr><td id=""footer"
endString = "</script></body></html>"
replaceStr = "<hr />"
startPt = inStr(text,startString)
endPt = inStr(text,endString)
If startPt>0 And endPt>=startPt Then
   findStr = Mid(text,startPt,endPt-startPt)
   text = Replace(text,findStr,replaceStr)
End If
'------------- For Project URL -----------
startString ="<div class=""healthReportDetails"">"
endString = "</div>Build History</td></t"
replaceStr = ""
startPt = inStr(text,startString)
endPt = inStr(text,endString)
If startPt>0 And endPt>=startPt Then
   findStr = Mid(text,startPt,endPt-startPt)
   text = Replace(text,findStr,replaceStr)
End If
'----------------------------------------------------------
'startString = "<a name="&""""&"skip2content"
'startString = "<h1><div style=""float:right""><table class=""middle-align""><tr><td>Progress:<"
'endString = "Build #"
'replaceStr = "<h1>"
'startPt = inStr(text,startString)
'endPt = inStr(text,endString)
'findStr = Mid(text,startPt,endPt-startPt)
'text = Replace(text,findStr,replaceStr)
'------------------------------------------------------

IF InStr(text,jenkinsURL) > 0 Then
 Set objTextStream = objFSO.CreateTextFile(strHDLocation)
 objTextStream.WriteLine text
 'Close the file and clean up
 objTextStream.Close
 Set objTextStream = Nothing
 Set objFSO = Nothing
 WScript.Quit 0
End If