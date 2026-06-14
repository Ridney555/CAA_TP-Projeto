@echo off
REM Script para executar a aplicacao JavaFX
REM Modifique JAVAFX_PATH se necessário

set JAVAFX_PATH=

if exist "%ProgramFiles%\javafx-sdk" (
    set JAVAFX_PATH=%ProgramFiles%\javafx-sdk
) else if exist "%ProgramFiles(x86)%\javafx-sdk" (
    set JAVAFX_PATH=%ProgramFiles(x86)%\javafx-sdk
) else if exist "%USERPROFILE%\javafx-sdk" (
    set JAVAFX_PATH=%USERPROFILE%\javafx-sdk
) else (
    echo ERRO: JavaFX SDK nao encontrado!
    pause
    exit /b 1
)

echo Executando aplicacao JavaFX...
echo.

java --module-path "%JAVAFX_PATH%\lib" ^
     --add-modules javafx.controls,javafx.graphics,javafx.fxml ^
     -cp . ^
     Classes.Main

pause
