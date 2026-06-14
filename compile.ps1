# Compilar com JavaFX
$JAVA_FX_HOME = "C:\Users\kelyf\Downloads\javafx-sdk-26.0.1"
mkdir -p bin
javac -d bin --module-path "$JAVA_FX_HOME/lib" --add-modules javafx.controls,javafx.fxml Classes/*.java
Write-Host "Compilação concluída!" -ForegroundColor Green
