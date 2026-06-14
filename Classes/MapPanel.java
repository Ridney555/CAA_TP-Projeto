package Classes;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Pane;

import java.util.*;

/**
 * Painel que desenha o mapa como um GRAFO.
 *  - Vértices  -> Localidades
 *  - Arestas   -> Rotas (com a distância como "peso")
 *  - Marcadores -> Encomendas em rota
 *
 * Suporta zoom, pan (arrastar o fundo), arrastar vértices,
 * seleção de vértices e realce de rotas.
 */
public class MapPanel extends Pane {

    private final Canvas canvas;
    private final DataManager dataManager;

    // Estado da vista
    private double zoomLevel = 1.0;
    private double panX = 0;
    private double panY = 0;
    private double lastMouseX = 0;
    private double lastMouseY = 0;
    private boolean isPanning = false;

    private Localidade selectedNode = null;
    private Localidade hoveredNode = null;
    // Nota: os vértices são FIXOS — não são arrastáveis.

    // Filtros / realces controlados pela interface
    private String statusFiltro = "Todas";   // Todas, Pendente, Em Rota, Entregue
    private int veiculoRealcado = -1;          // id do veículo a rastrear (-1 = nenhum)

    // Dimensões "virtuais" do mapa
    private final double mapWidth = 800;
    private final double mapHeight = 600;

    // Paleta (tom profissional mas sóbrio)
    private static final Color COLOR_BACKGROUND        = Color.web("#f4f6f8");
    private static final Color COLOR_GRID              = Color.web("#e4e9ef");
    private static final Color COLOR_NODE              = Color.web("#3b82f6");
    private static final Color COLOR_NODE_SELECTED     = Color.web("#1d4ed8");
    private static final Color COLOR_NODE_HOVER        = Color.web("#60a5fa");
    private static final Color COLOR_NODE_BORDER       = Color.web("#1e3a8a");
    private static final Color COLOR_EDGE              = Color.web("#94a3b8");
    private static final Color COLOR_EDGE_ACTIVE       = Color.web("#f59e0b");
    private static final Color COLOR_EDGE_HIGHLIGHT    = Color.web("#10b981");
    private static final Color COLOR_TEXT              = Color.web("#1f2937");
    private static final Color COLOR_TEXT_MUTED        = Color.web("#64748b");
    private static final Color COLOR_DELIVERY          = Color.web("#f59e0b");
    private static final Color COLOR_CAPITAL           = Color.web("#ef4444");

    private java.util.function.Consumer<Localidade> onNodeSelected;

    public MapPanel(DataManager dataManager) {
        this.dataManager = dataManager;
        this.canvas = new Canvas(800, 600);
        getChildren().add(canvas);

        canvas.setOnMousePressed(this::handleMousePressed);
        canvas.setOnMouseDragged(this::handleMouseDragged);
        canvas.setOnMouseReleased(this::handleMouseReleased);
        canvas.setOnScroll(this::handleScroll);
        canvas.setOnMouseMoved(this::handleMouseMoved);

        // Acompanhar o tamanho do painel
        widthProperty().addListener((obs, o, n) -> { canvas.setWidth(n.doubleValue()); draw(); });
        heightProperty().addListener((obs, o, n) -> { canvas.setHeight(n.doubleValue()); draw(); });

        draw();
    }

    // ----------------------------------------------------------------- DESENHO

    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(COLOR_BACKGROUND);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.save();
        gc.translate(canvas.getWidth() / 2 + panX, canvas.getHeight() / 2 + panY);
        gc.scale(zoomLevel, zoomLevel);
        gc.translate(-mapWidth / 2, -mapHeight / 2);

        drawGrid(gc);
        drawEdges(gc);
        drawNodes(gc);
        drawDeliveries(gc);

        gc.restore();

        // Sobreposições em coordenadas de ecrã (não afetadas pelo zoom)
        drawLegend(gc);
    }

    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(COLOR_GRID);
        gc.setLineWidth(0.5);
        int gridSize = 50;
        for (int i = 0; i <= mapWidth; i += gridSize)  gc.strokeLine(i, 0, i, mapHeight);
        for (int i = 0; i <= mapHeight; i += gridSize) gc.strokeLine(0, i, mapWidth, i);
    }

    /** Desenha as rotas (arestas do grafo) com a distância como peso. */
    private void drawEdges(GraphicsContext gc) {
        List<Encomenda> emRota = dataManager.getEncomendas().stream()
                .filter(e -> "Em Rota".equals(e.getStatusEncomenda()))
                .toList();

        for (Rota rota : dataManager.getRotas()) {
            Localidade origem  = dataManager.getLocalidade(rota.getIdOrigem());
            Localidade destino = dataManager.getLocalidade(rota.getIdDestino());
            if (origem == null || destino == null) continue;

            double x1 = origem.getPosX(),  y1 = origem.getPosY();
            double x2 = destino.getPosX(), y2 = destino.getPosY();

            boolean temEncomenda = emRota.stream().anyMatch(e ->
                    matchesRoute(e, rota));
            boolean realcada = veiculoRealcado != -1 && emRota.stream().anyMatch(e ->
                    e.getIdVeiculo() == veiculoRealcado && matchesRoute(e, rota));

            if (realcada) {
                gc.setStroke(COLOR_EDGE_HIGHLIGHT);
                gc.setLineWidth(4);
            } else if (temEncomenda) {
                gc.setStroke(COLOR_EDGE_ACTIVE);
                gc.setLineWidth(3);
            } else {
                gc.setStroke(COLOR_EDGE);
                gc.setLineWidth(1.5);
            }
            gc.strokeLine(x1, y1, x2, y2);
            drawArrow(gc, x1, y1, x2, y2);

            // Peso (distância) numa pequena "etiqueta"
            double midX = (x1 + x2) / 2, midY = (y1 + y2) / 2;
            String peso = String.format("%.0f km", rota.getDistancia());
            gc.setFont(Font.font("Segoe UI", 9));
            double w = peso.length() * 5.6 + 8;
            gc.setFill(Color.web("#ffffff", 0.85));
            gc.fillRoundRect(midX - w / 2, midY - 8, w, 14, 6, 6);
            gc.setFill(COLOR_TEXT_MUTED);
            gc.fillText(peso, midX - w / 2 + 4, midY + 2.5);
        }
    }

    private boolean matchesRoute(Encomenda e, Rota r) {
        return (e.getIdOrigem() == r.getIdOrigem() && e.getIdDestino() == r.getIdDestino())
            || (e.getIdOrigem() == r.getIdDestino() && e.getIdDestino() == r.getIdOrigem());
    }

    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2) {
        double dx = x2 - x1, dy = y2 - y1;
        double dist = Math.hypot(dx, dy);
        if (dist < 30) return;

        // Colocar a seta a 70% da origem, fora do raio do nó destino
        double t = 0.62;
        double tipX = x1 + dx * t, tipY = y1 + dy * t;
        double angle = Math.atan2(dy, dx);
        double size = 9;
        double ax = tipX - size * Math.cos(angle - Math.PI / 6);
        double ay = tipY - size * Math.sin(angle - Math.PI / 6);
        double bx = tipX - size * Math.cos(angle + Math.PI / 6);
        double by = tipY - size * Math.sin(angle + Math.PI / 6);
        gc.fillPolygon(new double[]{tipX, ax, bx}, new double[]{tipY, ay, by}, 3);
    }

    /** Desenha as localidades (vértices do grafo). */
    private void drawNodes(GraphicsContext gc) {
        for (Localidade loc : dataManager.getLocalidades()) {
            double x = loc.getPosX(), y = loc.getPosY();
            boolean isSelected = loc == selectedNode;
            boolean isHovered  = loc == hoveredNode;
            boolean isCapital  = "Praia".equals(loc.getNome());
            double radius = isSelected ? 18 : 15;

            // Sombra suave
            gc.setFill(Color.color(0, 0, 0, 0.10));
            gc.fillOval(x - radius, y - radius + 16, radius * 2, 7);

            // Anel de destaque para a capital
            if (isCapital) {
                gc.setStroke(COLOR_CAPITAL);
                gc.setLineWidth(2.5);
                gc.strokeOval(x - radius - 5, y - radius - 5, radius * 2 + 10, radius * 2 + 10);
            }

            // Corpo do vértice
            Color fill = isSelected ? COLOR_NODE_SELECTED : (isHovered ? COLOR_NODE_HOVER : COLOR_NODE);
            gc.setFill(fill);
            gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            gc.setStroke(COLOR_NODE_BORDER);
            gc.setLineWidth(isSelected ? 3 : 2);
            gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);

            // ID dentro do círculo
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, isSelected ? 13 : 12));
            String id = String.valueOf(loc.getId());
            gc.fillText(id, x - id.length() * 3.3, y + 4.5);

            // Nome por baixo
            gc.setFill(COLOR_TEXT);
            gc.setFont(Font.font("Segoe UI", isSelected ? FontWeight.BOLD : FontWeight.NORMAL, 11));
            double nameW = loc.getNome().length() * 5.6;
            gc.fillText(loc.getNome(), x - nameW / 2, y + radius + 16);

            // Estrela de capital por cima do vértice
            if (isCapital) {
                gc.setFill(COLOR_CAPITAL);
                gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
                gc.fillText("★", x - 6, y - radius - 9);
            }

            // Distintivo com nº de encomendas em rota neste vértice
            long emRotaAqui = dataManager.getEncomendas().stream()
                    .filter(e -> "Em Rota".equals(e.getStatusEncomenda()))
                    .filter(e -> e.getIdOrigem() == loc.getId() || e.getIdDestino() == loc.getId())
                    .count();
            if (emRotaAqui > 0) {
                double bx = x + radius - 3, by = y - radius + 1;
                gc.setFill(COLOR_DELIVERY);
                gc.fillOval(bx - 7, by - 7, 14, 14);
                gc.setStroke(Color.WHITE);
                gc.setLineWidth(1.5);
                gc.strokeOval(bx - 7, by - 7, 14, 14);
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 9));
                gc.fillText(String.valueOf(emRotaAqui), bx - 2.5, by + 3);
            }
        }
    }

    /** Desenha marcadores das encomendas (respeitando o filtro de status). */
    private void drawDeliveries(GraphicsContext gc) {
        for (Encomenda enc : dataManager.getEncomendas()) {
            if (!"Todas".equals(statusFiltro) && !statusFiltro.equals(enc.getStatusEncomenda())) continue;
            if (!"Em Rota".equals(enc.getStatusEncomenda())) continue;

            Localidade origem  = dataManager.getLocalidade(enc.getIdOrigem());
            Localidade destino = dataManager.getLocalidade(enc.getIdDestino());
            if (origem == null || destino == null) continue;

            // Posicionar o marcador a ~35% da origem ao destino
            double mx = origem.getPosX() + (destino.getPosX() - origem.getPosX()) * 0.35;
            double my = origem.getPosY() + (destino.getPosY() - origem.getPosY()) * 0.35;

            gc.setFill(COLOR_DELIVERY);
            gc.fillRoundRect(mx - 11, my - 8, 22, 16, 5, 5);
            gc.setStroke(Color.web("#b45309"));
            gc.setLineWidth(1);
            gc.strokeRoundRect(mx - 11, my - 8, 22, 16, 5, 5);
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 9));
            String lbl = "E" + enc.getId();
            gc.fillText(lbl, mx - lbl.length() * 2.6, my + 3);
        }
    }

    /** Caixa de legenda fixa no canto superior esquerdo. */
    private void drawLegend(GraphicsContext gc) {
        double x = 12, y = 12, w = 192, h = 138;

        gc.setFill(Color.web("#ffffff", 0.95));
        gc.fillRoundRect(x, y, w, h, 10, 10);
        gc.setStroke(COLOR_GRID);
        gc.setLineWidth(1);
        gc.strokeRoundRect(x, y, w, h, 10, 10);

        gc.setFill(COLOR_TEXT);
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        gc.fillText("Legenda", x + 12, y + 22);

        double ly = y + 42;
        legendNode(gc, x + 20, ly, COLOR_NODE);
        legendText(gc, x + 36, ly, "Localidade (vértice)");

        ly += 20;
        legendLine(gc, x + 12, ly, COLOR_EDGE, 1.5);
        legendText(gc, x + 36, ly, "Estrada (km)");

        ly += 20;
        legendLine(gc, x + 12, ly, COLOR_EDGE_ACTIVE, 3);
        legendText(gc, x + 36, ly, "Rota com encomenda");

        ly += 20;
        legendNode(gc, x + 20, ly, COLOR_DELIVERY);
        legendText(gc, x + 36, ly, "Encomenda em rota");

        ly += 20;
        gc.setFill(COLOR_CAPITAL);
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        gc.fillText("★", x + 15, ly + 4);
        legendText(gc, x + 36, ly, "Capital (Praia)");
    }

    private void legendNode(GraphicsContext gc, double cx, double cy, Color c) {
        gc.setFill(c);
        gc.fillOval(cx - 6, cy - 6, 12, 12);
    }

    private void legendLine(GraphicsContext gc, double sx, double sy, Color c, double lw) {
        gc.setStroke(c);
        gc.setLineWidth(lw);
        gc.strokeLine(sx, sy, sx + 24, sy);
    }

    private void legendText(GraphicsContext gc, double tx, double ty, String s) {
        gc.setFill(COLOR_TEXT_MUTED);
        gc.setFont(Font.font("Segoe UI", 10));
        gc.fillText(s, tx, ty + 3.5);
    }

    // ------------------------------------------------------ EVENTOS DE RATO

    /** Converte coordenadas do ecrã para coordenadas do "mundo" do mapa. */
    private double[] toWorld(double sx, double sy) {
        double wx = (sx - canvas.getWidth() / 2 - panX) / zoomLevel + mapWidth / 2;
        double wy = (sy - canvas.getHeight() / 2 - panY) / zoomLevel + mapHeight / 2;
        return new double[]{wx, wy};
    }

    private Localidade nodeAt(double sx, double sy) {
        double[] w = toWorld(sx, sy);
        for (Localidade loc : dataManager.getLocalidades()) {
            if (Math.hypot(w[0] - loc.getPosX(), w[1] - loc.getPosY()) < 18) return loc;
        }
        return null;
    }

    private void handleMousePressed(MouseEvent e) {
        Localidade hit = nodeAt(e.getX(), e.getY());
        if (hit != null) {
            // Clicar num vértice apenas o seleciona (os vértices são fixos).
            selectedNode = hit;
            if (onNodeSelected != null) onNodeSelected.accept(hit);
            draw();
        } else {
            // No fundo, arrastar faz pan da vista.
            isPanning = true;
        }
        lastMouseX = e.getX();
        lastMouseY = e.getY();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (isPanning) {
            panX += e.getX() - lastMouseX;
            panY += e.getY() - lastMouseY;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
            draw();
        }
    }

    private void handleMouseReleased(MouseEvent e) {
        isPanning = false;
    }

    private void handleScroll(ScrollEvent e) {
        zoomLevel *= e.getDeltaY() > 0 ? 1.1 : 0.9;
        zoomLevel = Math.max(0.5, Math.min(3.0, zoomLevel));
        draw();
        e.consume();
    }

    private void handleMouseMoved(MouseEvent e) {
        Localidade hit = nodeAt(e.getX(), e.getY());
        canvas.setCursor(hit != null ? javafx.scene.Cursor.HAND : javafx.scene.Cursor.DEFAULT);
        if (hit != hoveredNode) {
            hoveredNode = hit;
            draw();
        }
    }

    // ------------------------------------------------------- API PÚBLICA

    public void zoomIn()  { zoomLevel = Math.min(3.0, zoomLevel * 1.2); draw(); }
    public void zoomOut() { zoomLevel = Math.max(0.5, zoomLevel / 1.2); draw(); }

    public void resetZoom() {
        zoomLevel = 1.0;
        panX = panY = 0;
        selectedNode = null;
        veiculoRealcado = -1;
        statusFiltro = "Todas";
        draw();
    }

    public void setStatusFiltro(String status) {
        this.statusFiltro = (status == null) ? "Todas" : status;
        draw();
    }

    public void rastrearVeiculo(int idVeiculo) {
        this.veiculoRealcado = idVeiculo;
        draw();
    }

    public void limparRealce() {
        this.veiculoRealcado = -1;
        draw();
    }

    /** Seleciona um vértice pelo id (usado pela interface). */
    public void selecionarLocalidade(int id) {
        Localidade loc = dataManager.getLocalidade(id);
        if (loc != null) {
            selectedNode = loc;
            if (onNodeSelected != null) onNodeSelected.accept(loc);
            draw();
        }
    }

    public void setOnNodeSelected(java.util.function.Consumer<Localidade> callback) {
        this.onNodeSelected = callback;
    }

    public Localidade getSelectedNode() {
        return selectedNode;
    }
}
