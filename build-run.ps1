# Compilar e executar
$JAVA_FX_HOME = "C:\Users\kelyf\Downloads\javafx-sdk-26.0.1"
mkdir -p bin
javac -d bin --module-path "$JAVA_FX_HOME/lib" --add-modules javafx.controls,javafx.fxml Classes/*.java
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilação bem-sucedida! Iniciando aplicação..." -ForegroundColor Green
    java --module-path "$JAVA_FX_HOME/lib" --add-modules javafx.controls,javafx.fxml -cp bin Classes.Main
} else {
    Write-Host "Erro na compilação!" -ForegroundColor Red
}
