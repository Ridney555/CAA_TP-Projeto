# SGE — Sistema de Gestão de Entregas

Trabalho Prático da disciplina de **CAA**. Aplicação **Java + JavaFX** que visualiza, sob
a forma de **grafo**, as zonas da **Ilha de Santiago (Cabo Verde)** e a rede de **estradas**
usada para fazer **entregas de automóvel**.

- **Vértices** → zonas/localidades de Santiago
- **Arestas** → estradas entre zonas (com a distância em km como peso)
- **Marcadores** → encomendas em rota

> Os dados são, de momento, de exemplo (*hardcoded*) em [`Classes/DataManager.java`](Classes/DataManager.java).
> O backend real pode substituir o método `initializeDummyData()` mantendo a mesma API.

## Funcionalidades

- Mapa-grafo da ilha de Santiago com a capital (**Praia**) destacada (anel + ★).
- Distância (km) de cada estrada apresentada sobre a aresta.
- Realce das estradas com encomendas **Em Rota**.
- **Zoom** (roda do rato ou botões) e **pan** (arrastar o fundo).
- **Seleção de zona** (clique) com painel de detalhes: estradas ligadas e encomendas.
- Filtro de encomendas por estado e rastreio de veículo.
- Vértices **fixos** (não arrastáveis).

## Requisitos

- **JDK 21+** (testado com Java 26)
- **JavaFX SDK** (ex.: `javafx-sdk-26.0.1`)

Por omissão os scripts apontam para:
`C:\Users\kelyf\Downloads\javafx-sdk-26.0.1`
Edita o caminho em [`compile.ps1`](compile.ps1) e [`run.ps1`](run.ps1) se o teu for diferente.

## Como compilar e executar

### PowerShell (recomendado no Windows)
```powershell
./compile.ps1   # compila para a pasta bin/
./run.ps1       # executa a aplicação
```

### Comando manual
```powershell
javac -d bin --module-path "CAMINHO/javafx-sdk/lib" `
      --add-modules javafx.controls,javafx.fxml Classes/*.java

java --module-path "CAMINHO/javafx-sdk/lib" `
     --add-modules javafx.controls,javafx.fxml -cp bin Classes.Main
```

## Estrutura do projeto

```
Classes/
  Main.java               -> ponto de entrada (lança a aplicação JavaFX)
  MapViewController.java   -> janela principal e interface (cabeçalho, controlos, detalhes)
  MapPanel.java            -> desenho do grafo (vértices, arestas, zoom/pan, seleção)
  DataManager.java         -> dados de exemplo (zonas, estradas, veículos, encomendas)
  Localidade.java          -> modelo: zona/localidade (vértice)
  Rota.java                -> modelo: estrada (aresta, com distância)
  Automovel.java           -> modelo: veículo de entrega
  Encomenda.java           -> modelo: encomenda
compile.ps1 / run.ps1      -> scripts de build e execução
```

## Notas

- Desenvolvido em JavaFX puro (sem bibliotecas externas).
- As classes `Localidades`, `Rotas`, `Automoveis` e `Testes` são versões antigas/auxiliares
  e não são usadas pela aplicação atual.
