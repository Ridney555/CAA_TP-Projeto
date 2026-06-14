package Classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * Janela principal da aplicação SGE.
 * Organiza o ecrã em: cabeçalho, painel de controlo (esquerda),
 * mapa-grafo (centro), painel de detalhes (direita) e barra de estado (rodapé).
 */
public class MapViewController extends Application {

    private MapPanel mapPanel;
    private DataManager dataManager;

    // Componentes que precisamos de atualizar
    private VBox detailsContent;
    private Label statusLabel;
    private Label countLabel;
    private Label distLabel;
    private ComboBox<Localidade> localidadeCombo;
    private ComboBox<Automovel> veiculoCombo;
    private ComboBox<String> statusCombo;

    // ---------- Paleta da interface (consistente com o mapa) ----------
    private static final String PRIMARY     = "#2563eb";
    private static final String PRIMARY_DK  = "#1d4ed8";
    private static final String PANEL_BG    = "#ffffff";
    private static final String APP_BG      = "#f4f6f8";
    private static final String BORDER      = "#e4e9ef";
    private static final String TEXT        = "#1f2937";
    private static final String TEXT_MUTED  = "#64748b";

    @Override
    public void start(Stage primaryStage) {
        try {
            dataManager = new DataManager();

            BorderPane root = new BorderPane();
            root.setStyle("-fx-background-color: " + APP_BG + ";");

            mapPanel = new MapPanel(dataManager);
            mapPanel.setOnNodeSelected(this::updateDetails);

            root.setTop(createHeader());
            root.setLeft(createControlPanel());
            root.setCenter(mapPanel);
            root.setRight(createDetailsPanel());
            root.setBottom(createInfoBar());

            updateTotals();

            Scene scene = new Scene(root, 1400, 820);
            primaryStage.setTitle("SGE - Sistema de Gestão de Entregas");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------- CABEÇALHO

    private VBox createHeader() {
        Label title = new Label("SGE — Sistema de Gestão de Entregas");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("Ilha de Santiago (Cabo Verde) — zonas e estradas · entregas por automóvel");
        subtitle.setStyle("-fx-font-size: 11px; -fx-text-fill: #dbeafe;");

        VBox titles = new VBox(2, title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label badge = new Label("Trabalho Prático · CAA");
        badge.setStyle("-fx-font-size: 11px; -fx-text-fill: white; -fx-background-color: rgba(255,255,255,0.18);"
                + " -fx-padding: 4 10 4 10; -fx-background-radius: 12;");

        HBox bar = new HBox(12, titles, spacer, badge);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(14, 20, 14, 20));
        bar.setStyle("-fx-background-color: linear-gradient(to right, " + PRIMARY_DK + ", " + PRIMARY + ");");

        VBox box = new VBox(bar);
        return box;
    }

    // ------------------------------------------------------ PAINEL DE CONTROLO

    private VBox createControlPanel() {
        VBox panel = new VBox(16);
        panel.setPadding(new Insets(16));
        panel.setPrefWidth(260);
        panel.setStyle("-fx-background-color: " + PANEL_BG + ";"
                + " -fx-border-color: " + BORDER + "; -fx-border-width: 0 1 0 0;");

        Label header = sectionTitle("Controlos");
        header.setStyle(header.getStyle() + " -fx-font-size: 15px;");
        panel.getChildren().add(header);

        // ---- Localidades ----
        localidadeCombo = new ComboBox<>();
        localidadeCombo.getItems().addAll(dataManager.getLocalidades());
        localidadeCombo.setPromptText("Selecionar localidade");
        localidadeCombo.setMaxWidth(Double.MAX_VALUE);
        localidadeCombo.setConverter(new StringConverter<>() {
            public String toString(Localidade l) { return l == null ? "" : l.getId() + " – " + l.getNome(); }
            public Localidade fromString(String s) { return null; }
        });

        Button verLocalBtn = primaryButton("Ver Detalhes");
        verLocalBtn.setOnAction(e -> {
            Localidade l = localidadeCombo.getValue();
            if (l != null) {
                mapPanel.selecionarLocalidade(l.getId());
                setStatus("Localidade selecionada: " + l.getNome());
            }
        });

        panel.getChildren().add(card("Localidades", localidadeCombo, verLocalBtn));

        // ---- Rotas ----
        Button rotasBtn = secondaryButton("Repor Vista das Rotas");
        rotasBtn.setOnAction(e -> {
            mapPanel.limparRealce();
            mapPanel.setStatusFiltro("Todas");
            statusCombo.setValue("Todas");
            setStatus("A mostrar todas as rotas");
        });
        Label rotasInfo = mutedLabel(dataManager.getRotas().size() + " rotas · "
                + dataManager.getLocalidades().size() + " localidades");
        panel.getChildren().add(card("Rotas", rotasInfo, rotasBtn));

        // ---- Veículos ----
        veiculoCombo = new ComboBox<>();
        veiculoCombo.getItems().addAll(dataManager.getAutomoveis());
        veiculoCombo.setPromptText("Selecionar veículo");
        veiculoCombo.setMaxWidth(Double.MAX_VALUE);
        veiculoCombo.setConverter(new StringConverter<>() {
            public String toString(Automovel a) { return a == null ? "" : a.getModelo() + " (" + a.getPlaca() + ")"; }
            public Automovel fromString(String s) { return null; }
        });

        Button rastrearBtn = primaryButton("Rastrear Veículo");
        rastrearBtn.setOnAction(e -> {
            Automovel a = veiculoCombo.getValue();
            if (a != null) {
                mapPanel.rastrearVeiculo(a.getId());
                setStatus("A rastrear: " + a.getModelo());
            }
        });
        panel.getChildren().add(card("Veículos", veiculoCombo, rastrearBtn));

        // ---- Encomendas ----
        statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll("Todas", "Pendente", "Em Rota", "Entregue");
        statusCombo.setValue("Todas");
        statusCombo.setMaxWidth(Double.MAX_VALUE);

        Button filtrarBtn = primaryButton("Filtrar por Estado");
        filtrarBtn.setOnAction(e -> {
            String s = statusCombo.getValue();
            mapPanel.setStatusFiltro(s);
            int n = "Todas".equals(s) ? dataManager.getEncomendas().size()
                                      : dataManager.getEncomendasPorStatus(s);
            setStatus(n + " encomenda(s) com estado \"" + s + "\"");
        });
        panel.getChildren().add(card("Encomendas", statusCombo, filtrarBtn));

        // ---- Zoom ----
        Button zoomIn = secondaryButton("+");
        Button zoomOut = secondaryButton("−");
        Button reset = secondaryButton("Repor");
        zoomIn.setMaxWidth(Double.MAX_VALUE);
        zoomOut.setMaxWidth(Double.MAX_VALUE);
        reset.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(zoomIn, Priority.ALWAYS);
        HBox.setHgrow(zoomOut, Priority.ALWAYS);
        HBox.setHgrow(reset, Priority.ALWAYS);
        zoomIn.setOnAction(e -> mapPanel.zoomIn());
        zoomOut.setOnAction(e -> mapPanel.zoomOut());
        reset.setOnAction(e -> { mapPanel.resetZoom(); statusCombo.setValue("Todas"); setStatus("Vista reposta"); });
        HBox zoomBox = new HBox(8, zoomIn, zoomOut, reset);
        panel.getChildren().add(card("Zoom", zoomBox));

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        panel.getChildren().add(spacer);

        return panel;
    }

    // ------------------------------------------------------ PAINEL DE DETALHES

    private VBox createDetailsPanel() {
        VBox panel = new VBox(12);
        panel.setPadding(new Insets(16));
        panel.setPrefWidth(280);
        panel.setStyle("-fx-background-color: " + PANEL_BG + ";"
                + " -fx-border-color: " + BORDER + "; -fx-border-width: 0 0 0 1;");

        panel.getChildren().add(sectionTitle("Detalhes"));

        detailsContent = new VBox(8);
        Label hint = mutedLabel("Clique numa localidade do mapa\npara ver os detalhes.");
        hint.setWrapText(true);
        detailsContent.getChildren().add(hint);

        panel.getChildren().add(detailsContent);
        return panel;
    }

    private void updateDetails(Localidade loc) {
        detailsContent.getChildren().clear();
        if (loc == null) return;

        Label name = new Label(loc.getNome());
        name.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");

        detailsContent.getChildren().addAll(
                name,
                detailRow("ID", String.valueOf(loc.getId())),
                detailRow("Posição", String.format("(%.0f, %.0f)", loc.getPosX(), loc.getPosY())),
                new Separator()
        );

        // Rotas que partem/chegam a esta localidade
        Label rotasTitle = new Label("Rotas ligadas");
        rotasTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        detailsContent.getChildren().add(rotasTitle);

        boolean temRota = false;
        for (Rota r : dataManager.getRotas()) {
            Integer outro = null;
            if (r.getIdOrigem() == loc.getId())  outro = r.getIdDestino();
            else if (r.getIdDestino() == loc.getId()) outro = r.getIdOrigem();
            if (outro != null) {
                Localidade dest = dataManager.getLocalidade(outro);
                String nome = dest != null ? dest.getNome() : ("#" + outro);
                detailsContent.getChildren().add(
                        detailRow("→ " + nome, String.format("%.0f km", r.getDistancia())));
                temRota = true;
            }
        }
        if (!temRota) detailsContent.getChildren().add(mutedLabel("Sem rotas diretas."));

        // Encomendas relacionadas
        Separator sep = new Separator();
        Label encTitle = new Label("Encomendas");
        encTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        detailsContent.getChildren().addAll(sep, encTitle);

        boolean temEnc = false;
        for (Encomenda e : dataManager.getEncomendas()) {
            if (e.getIdOrigem() == loc.getId() || e.getIdDestino() == loc.getId()) {
                detailsContent.getChildren().add(
                        detailRow("E" + e.getId() + " · " + e.getDescricao(), e.getStatusEncomenda()));
                temEnc = true;
            }
        }
        if (!temEnc) detailsContent.getChildren().add(mutedLabel("Sem encomendas."));
    }

    // ----------------------------------------------------------- BARRA DE ESTADO

    private HBox createInfoBar() {
        statusLabel = new Label("Pronto");
        countLabel = new Label();
        distLabel = new Label();

        for (Label l : new Label[]{statusLabel, countLabel, distLabel})
            l.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");

        HBox bar = new HBox(16,
                statusLabel, vsep(),
                countLabel, vsep(),
                distLabel);
        bar.setAlignment(Pos.CENTER_LEFT);
        bar.setPadding(new Insets(8, 16, 8, 16));
        bar.setStyle("-fx-background-color: " + PANEL_BG + ";"
                + " -fx-border-color: " + BORDER + "; -fx-border-width: 1 0 0 0;");
        return bar;
    }

    private void updateTotals() {
        countLabel.setText("Encomendas: " + dataManager.getEncomendas().size()
                + "  (Em Rota: " + dataManager.getEncomendasPorStatus("Em Rota") + ")");
        distLabel.setText(String.format("Distância total das rotas: %.0f km", dataManager.getDistanciaTotal()));
    }

    private void setStatus(String s) { statusLabel.setText(s); }

    // --------------------------------------------------------- HELPERS DE UI

    /** Caixa/cartão com título e conteúdos. */
    private VBox card(String title, Region... children) {
        VBox box = new VBox(8);
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: #fbfcfe; -fx-background-radius: 8;"
                + " -fx-border-color: " + BORDER + "; -fx-border-radius: 8; -fx-border-width: 1;");
        box.getChildren().add(sectionTitle(title));
        for (Region c : children) box.getChildren().add(c);
        return box;
    }

    private Label sectionTitle(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");
        return l;
    }

    private Label mutedLabel(String s) {
        Label l = new Label(s);
        l.setStyle("-fx-font-size: 11px; -fx-text-fill: " + TEXT_MUTED + ";");
        return l;
    }

    private HBox detailRow(String key, String value) {
        Label k = new Label(key);
        k.setStyle("-fx-font-size: 12px; -fx-text-fill: " + TEXT_MUTED + ";");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label v = new Label(value);
        v.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: " + TEXT + ";");
        HBox row = new HBox(8, k, spacer, v);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private Button primaryButton(String text) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        String base = "-fx-text-fill: white; -fx-font-size: 12px; -fx-background-radius: 6;"
                + " -fx-cursor: hand; -fx-padding: 6 12 6 12; -fx-background-color: ";
        b.setStyle(base + PRIMARY + ";");
        b.setOnMouseEntered(e -> b.setStyle(base + PRIMARY_DK + ";"));
        b.setOnMouseExited(e -> b.setStyle(base + PRIMARY + ";"));
        return b;
    }

    private Button secondaryButton(String text) {
        Button b = new Button(text);
        String base = "-fx-font-size: 12px; -fx-background-radius: 6; -fx-cursor: hand;"
                + " -fx-padding: 6 12 6 12; -fx-text-fill: " + TEXT + ";"
                + " -fx-border-color: " + BORDER + "; -fx-border-radius: 6; -fx-background-color: ";
        b.setStyle(base + "#ffffff;");
        b.setOnMouseEntered(e -> b.setStyle(base + "#eef2f7;"));
        b.setOnMouseExited(e -> b.setStyle(base + "#ffffff;"));
        return b;
    }

    private Separator vsep() {
        return new Separator(javafx.geometry.Orientation.VERTICAL);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
