Rem As RFT Generates logs at the end of a test run,
Rem we need the vbs to copy those logs after

Dim ArgObj,stime,from, toDir,dt
Const OverwriteExisting = True
Set ArgObj = WScript.Arguments

stime = ArgObj(0)
from = ArgObj(1)
toDir = Argobj(2)
dt = "Desktop"

Set objFSO = CreateObject("Scripting.FileSystemObject")
Rem Set WSHShell = CreateObject("WScript.Shell")
Wscript.sleep stime

Rem desktop = WSHShell.SpecialFolders(dt)
Rem pathstring = objFSO.GetAbsolutePathName(desktop)
Rem RootPath=Left(pathstring,(len(pathstring)-7))

Rem dtAt = InStr(from,dt)
Rem If dtAt>0 Then Replace(from,dt,RootPath,InStr(from,dt))
Rem dtAt = InStr(toDir,dt)
Rem If dtAt>0 Then Replace(toDir,dt,RootPath,InStr(from,dt))

WScript.Echo from
WScript.Echo toDir
objFSO.CopyFile from,toDir,OverwriteExisting
Rem objFSO.CopyFolder from,toDir,OverwriteExisting