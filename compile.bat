@echo off
REM Script de compilação com JavaFX
REM Modifique JAVAFX_PATH se necessário

REM Tentar encontrar JavaFX em locais comuns
set JAVAFX_PATH=

if exist "%ProgramFiles%\javafx-sdk" (
    set JAVAFX_PATH=%ProgramFiles%\javafx-sdk
) else if exist "%ProgramFiles(x86)%\javafx-sdk" (
    set JAVAFX_PATH=%ProgramFiles(x86)%\javafx-sdk
) else if exist "%USERPROFILE%\javafx-sdk" (
    set JAVAFX_PATH=%USERPROFILE%\javafx-sdk
) else (
    echo.
    echo ERRO: JavaFX SDK nao encontrado!
    echo.
    echo Por favor:
    echo 1. Baixe JavaFX em: https://gluonhq.com/products/javafx/
    echo 2. Extraia em uma pasta (ex: C:\javafx-sdk)
    echo 3. Modifique JAVAFX_PATH neste arquivo
    echo.
    pause
    exit /b 1
)

echo Compilando com JavaFX de: %JAVAFX_PATH%
echo.

REM Compilar
javac --module-path "%JAVAFX_PATH%\lib" ^
      --add-modules javafx.controls,javafx.graphics,javafx.fxml ^
      -d . ^
      Classes\*.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo Erro na compilacao!
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Compilacao concluida com sucesso!
pause
