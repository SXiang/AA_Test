@echo off
setlocal enableextensions enabledelayedexpansion
color 17
title Java removal/update tool v0.9

echo.
echo This software is brought to you by Grintor.
echo ^<Grintor at Gmail dot Com^>
echo.
echo This program is free software.
echo This program IS PROVIDED WITHOUT WARRANTY, EITHER EXPRESSED OR IMPLIED.
echo This program is copyrighted under the terms of GPLv3:
echo see ^<http://www.gnu.org/licenses/^>.
echo.
echo This program uses cURL, also free software, source code available:
echo see ^<http://curl.haxx.se/^>.
echo.
echo This binary is also a compressed package.
echo The source code is available within.
echo It can be decompressed using 7-zip, also free software:
echo see ^<http://www.7-zip.org/^>.
echo.

FOR /L %%n IN (1,1,10) DO ping -n 2 127.0.0.1 > nul & <nul set /p =.

cls
echo.
echo Searching for latest version of Java...
ping -n 1 acl.com > nul || goto error


::----------- Find the latest java version----------------------------------
FOR /F "tokens=2 delims=<> " %%n IN ('curl.exe -s -L http://javadl-esd.sun.com/update/1.7.0/map-m-1.7.0.xml ^| find /i "https:"') DO set URL1=%%n
FOR /F "tokens=2 delims=<> " %%n IN ('curl.exe -s -L -k %URL1% ^| find /i "<version>"') DO set RemoteJavaVersion=%%n
set RemoteJavaVersion=%RemoteJavaVersion:~0,8%
echo The latest version of java is %RemoteJavaVersion%.




::----------- Find the local Java version-----------------------------------
set LocalJavaVersion=None
FOR /F "tokens=1-15" %%n IN ('reg query HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall /s ^& reg query HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall /s 2^> nul') DO (

   if '%%n'=='InstallSource' (
     set p=%%p%%q%%r%%s%%t%%u%%v%%w%%x%%y%%z
     set p=!p: =\!
     set p=!p:\= !
     for %%n in (!p!) do set c=%%n
   )

   if '%%n'=='DisplayName' (
      set p=%%p
      if "!p:~0,4!"=="Java" if not "%%q"=="Auto" if not '!LocalJavaVersion!'=='None' (set LocalJavaVersion=Multi) ELSE (set LocalJavaVersion=!c:~3!)
      if "!p:~0,4!"=="J2SE" if not '!LocalJavaVersion!'=='None' (set LocalJavaVersion=Multi) ELSE (set LocalJavaVersion=!c:~3!)
   )
)

if '%LocalJavaVersion%'=='None' echo There is no local version of Java. & goto install
if '%LocalJavaVersion%'=='Multi' echo There are multiple local versions of Java installed. & goto uninstall
echo The local version of Java is %LocalJavaVersion%.




::----------- If they match, skip to the end---------------------------------
if '%RemoteJavaVersion%'=='%LocalJavaVersion%' (goto finished) ELSE (echo The Local version of Java is out of date.)



pause
::----------- Uninstall all currently installed java versions----------------
:uninstall
if '%LocalJavaVersion%'=='Multi' (echo Uninstalling all local versions of Java...) ELSE (echo Uninstalling the local version of Java...)
FOR /F "tokens=1-4" %%n IN ('reg query HKEY_LOCAL_MACHINE\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall /s ^& reg query HKEY_LOCAL_MACHINE\SOFTWARE\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall /s 2^> nul') DO (

   if '%%n'=='UninstallString' (
     set c=%%q
     set c=!c:/I=/X!
   )

   if '%%n'=='DisplayName' (
      set d=%%p
      if "!d:~0,4!"=="Java" if not "%%q"=="Auto" msiexec.exe !c! /qn & ping -n 11 127.0.0.1 > nul
      if "!d:~0,4!"=="J2SE" msiexec.exe !c! /qn & ping -n 11 127.0.0.1 > nul
   )
)



::----------- Download the latest java, install, delete the installer.-------
pause
:install
echo Downloading latest version of Java...
FOR /F "tokens=2 delims=<> " %%n IN ('curl.exe -s -L -k %url1% ^| find /i ".exe"') DO set url2=%%n
curl.exe -s -L -k -o java.exe %url2%
echo Installing latest version of Java...
start /wait java.exe /s
ping 127.0.0.1 > nul
del java.exe



::----------- Up to date ----------------------------------------------------
:finished
echo Your Java is up to date.
echo.


::----------- There was an error---------------------------------------------
goto noerror
:error
echo There was a network error. Please try again.
:noerror


FOR /L %%n IN (1,1,10) DO ping -n 2 127.0.0.1 > nul & <nul set /p =.