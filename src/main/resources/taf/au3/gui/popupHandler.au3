require 'watir'
require 'win32ole'

#Supply AutoItX3 as an argument to the constructor of WIN32OLE
#for handling pop-up windows using WIN32OLE objects.WIN32OLE will act as an interface
#for handling pop-up windows using AutoItX3 functions.
ai = WIN32OLE.new("AutoItX3.Control")

#New IE Browser window
ie=Watir::IE.new

#Go to AutoIt website
ie.goto("http://www.autoitscript.com/autoit3/downloads.shtml")

#Click Download AutoIt image
ie.image(:src,/download_autoit/).click_no_wait


#Wait for the pop-up window with the specified title supplied to come
res=ai.WinWait("File Download - Security Warning","",30)
puts res  #Used for debugging purpose

#Always try to Activate the pop-up window before using ControlClick fn()
res=ai.WinActivate("File Download - Security Warning")
puts res  #Used for debugging purpose

#Click on the specified control on the pop-up window
res=ai.ControlClick("File Download - Security Warning","","&Save")
puts res  #Used for debugging purpose


puts"\n" #Place the cursor on the next line
#Wait for the pop-up window with the specified title supplied to come
res=ai.WinWait("Save As","",30)
puts res  #Used for debugging purpose

#Always try to Activate the pop-up window before using ControlClick fn()
res=ai.WinActivate("Save As")
puts res  #Used for debugging purpose

#Send the path in the pop-up window where you want to store the respective file
res=ai.ControlSend("Save As","","Edit1", "C:\AutoItV3.exe")
puts res  #Used for debugging purpose

#Click on the specified control on the pop-up window
res=ai.ControlClick("Save As","","&Save")
puts res  #Used for debugging purpose

#Close the IE Browser window
ie.close