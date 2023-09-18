@echo off

@REM == Copyright 2021 Diffblue Limited. All Rights Reserved.
@REM == Unpublished proprietary source code.
@REM == Use is governed by https://docs.diffblue.com/licenses/eula

set current_version=2023.08.01
set cover_ui_path=%~dp0
set build_cli_path="%cover_ui_path%\cover-cli-%current_version%-jar-with-dependencies.jar"
set dist_cli_path="%cover_ui_path%\cover-cli.jar"
set build_dcover_agent_path="%cover_ui_path%\launcher-%current_version%-shared-jar.jar"
set dist_dcover_agent_path="%cover_ui_path%\launcher-shared-jar.jar"
set build_dcover_spring_path="%cover_ui_path%\cover-isolation-layer-%current_version%.jar"
set dist_dcover_spring_path="%cover_ui_path%\cover-isolation-layer.jar"
set build_dcover_service_path="%cover_ui_path%\cover-service-analyzer-%current_version%-jar-with-dependencies.jar"
set dist_dcover_service_path="%cover_ui_path%\cover-service-analyzer.jar"

@REM ==== VALIDATE JAVA_HOME ====
if not "%JAVA_HOME%" == "" goto OkJHome
  @REM No java home, look for java.exe on PATH
  @REM Based on https://stackoverflow.com/a/4781795/3408
  set JAVA_EXE=""
  for %%X in (java.exe) do (set JAVA_EXE="%%~$PATH:X")
  if not %JAVA_EXE% == "" goto OKJavaExe

  @REM if that didn't work, ask the user directly to set JAVA_HOME
  echo Error: JAVA_HOME not found in your environment. >&2
  echo Please set the JAVA_HOME variable in your environment to match the >&2
  echo location of your Java installation. >&2
  echo.
  cmd /C exit /B 1
:OkJHome

if exist "%JAVA_HOME%\bin\java.exe" goto OkJHome2
  echo Error: bin\java.exe not found in your JAVA_HOME directory. >&2
  echo JAVA_HOME = "%JAVA_HOME%" >&2
  echo Please set the JAVA_HOME variable in your environment to match the >&2
  echo location of your Java installation. >&2
  echo.
  cmd /C exit /B 1
:OkJHome2

SET JAVA_EXE="%JAVA_HOME%\bin\java.exe"

:OKJavaExe

if exist %build_cli_path% (
    set cli_path=%build_cli_path%
) else (
    set cli_path=%dist_cli_path%
)
if exist %build_dcover_agent_path% (
    set dcover_agent_path=%build_dcover_agent_path%
) else (
    set dcover_agent_path=%dist_dcover_agent_path%
)
if exist %build_dcover_spring_path% (
    set spring_path=%build_dcover_spring_path%
) else (
    set spring_path=%dist_dcover_spring_path%
)
if exist %build_dcover_service_path% (
    set service_path=%build_dcover_service_path%
) else (
    set service_path=%dist_dcover_service_path%
)
rem Get datetime string
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set dateTime=%dt:~0,8%T%dt:~8,6%

set diffblue_tmp=%TEMP%\diffblue
md %diffblue_tmp% 2>NUL
set log_directory=%diffblue_tmp%\log
md %log_directory% 2>NUL
set stderr_file=%log_directory%\dcover.jvm-%dateTime%.log

rem allowAttachSelf is required by Mockito on old JDKs (e.g. IBM J9)
%JAVA_EXE% %JVM_ARGS% -ea -Xshare:off -javaagent:%dcover_agent_path% -Djdk.attach.allowAttachSelf=true -Dcom.diffblue.launcherJar=%dcover_agent_path% -Dcover.jar.path=%cli_path% -Dcom.diffblue.springIsolationJar=%spring_path% -Dcom.diffblue.assertionSuggestionJar=%service_path% -jar %dcover_agent_path% %* 2>>"%stderr_file%"
set exit_status=%ERRORLEVEL%

call :getFileSize "%stderr_file%" stderrFileSize

rem Check for output to standard error
if %stderrFileSize% NEQ 0 (
  echo JVM log written to: %stderr_file% 1>&2
) else (
  rem Remove empty stderr file
  del %stderr_file%
)

exit /B %exit_status%

goto :eof

:getFileSize
  for %%a in (%~1) do set "size=%%~Za"
  if "%size%" == "" set size=0
  set "%~2=%size%"
goto :eof
