' Set your settings
    strFileURL =  WScript.Arguments.Item(0)
    strHDLocation =  WScript.Arguments.Item(1)
	jenkinsURL =  WScript.Arguments.Item(2) 
	buildURL =  WScript.Arguments.Item(3)
'	"href= src= URL= loadScript(
	find1 =  " href=" 
	find2 =  " src="
	find3 =  "URL="
	find4 =  "loadScript("
	
' Fetch the file
Set objFSO = Createobject("Scripting.FileSystemObject")
Set objTextStream = objFSO.OpenTextFile(strHDLocation, 1)
text = objTextStream.ReadAll
objTextStream.Close

find = find1
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & """#"
replaceStr = find & "##""#"
text = Replace(text,findStr,replaceStr)

findStr = find & """"
replaceStr = find & """" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)

find = find1
findStr = find & "'/"
replaceStr = find & "##'" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "'"
replaceStr = find & "'" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)

find = find2
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)


find = find3
findStr = find & """/"
replaceStr = find & "##""" & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & """"
replaceStr = find & """" & buildURL & "/"
text = Replace(text,findStr,replaceStr)
findStr = find & "##"
replaceStr = find
text = Replace(text,findStr,replaceStr)


find = find4
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
replaceStr = " """ & jenkinsURL & "/"
text = Replace(text,findStr,replaceStr)


Set objTextStream = objFSO.OpenTextFile(strHDLocation, 2)
objTextStream.WriteLine text

'Close the file and clean up
objTextStream.Close
Set objTextStream = Nothing
Set objFSO = Nothing

