@ECHO OFF
for /f "tokens=2 delims=[]" %%f in ('ping -4 -n 1 %COMPUTERNAME% ^|find /i "pinging"') do echo IP=%%f

rem for /f "tokens=1-2 delims=:" %%a in ('ipconfig^|find "IPv4"') do set ip=%%b
rem set ip=%ip:~1%
rem echo %ip%
pause