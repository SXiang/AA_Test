@Echo OFF
SETLOCAL enabledelayedexpansion
rem SET env=%1
SET unc=%2
rem Echo.original ProjectDir is '%projectDir%'
pushd %unc%
IF ERRORLEVEL 0 SET %1=%cd%
set projectDir=%cd%
Echo.Eff PrDir is '%projectDir%'
popd
:END
:EOF
