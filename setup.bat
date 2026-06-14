@echo off
REM Script de setup para JavaFX

echo.
echo ========================================
echo   Setup - SGE com JavaFX
echo ========================================
echo.

REM Verificar se Java esta instalado
where java >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo ERRO: Java nao encontrado!
    echo Por favor instale Java 11 ou superior
    echo Download: https://www.oracle.com/java/technologies/downloads/
    pause
    exit /b 1
)

echo [OK] Java encontrado
java -version
echo.

REM Verificar JavaFX
if exist "%ProgramFiles%\javafx-sdk" (
    echo [OK] JavaFX encontrado em: %ProgramFiles%\javafx-sdk
    goto compile
) else if exist "%ProgramFiles(x86)%\javafx-sdk" (
    echo [OK] JavaFX encontrado em: %ProgramFiles(x86)%\javafx-sdk
    goto compile
) else if exist "%USERPROFILE%\javafx-sdk" (
    echo [OK] JavaFX encontrado em: %USERPROFILE%\javafx-sdk
    goto compile
) else (
    echo [AVISO] JavaFX nao encontrado automaticamente
    echo.
    echo Para usar a aplicacao, voce precisa:
    echo 1. Fazer download do JavaFX SDK:
    echo    https://gluonhq.com/products/javafx/
    echo.
    echo 2. Descompactar em um dos locais:
    echo    - C:\Program Files\javafx-sdk
    echo    - C:\Program Files (x86)\javafx-sdk
    echo    - %USERPROFILE%\javafx-sdk
    echo.
    echo 3. Voltar a executar este script
    echo.
    pause
    exit /b 1
)

:compile
echo.
echo Compilando projeto...
call compile.bat
