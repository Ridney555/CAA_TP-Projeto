package Classes;

import java.util.*;

/**
 * Gerenciador de dados para a interface gráfica.
 * Fornece acesso aos dados de localidades, rotas, veículos e encomendas.
 *
 * Dados de exemplo: zonas da ILHA DE SANTIAGO (Cabo Verde), ligadas por
 * ESTRADAS (entregas feitas de automóvel). Dados estáticos — o backend real
 * será integrado por outro programador, mantendo esta mesma API.
 */
public class DataManager {

    private List<Localidade> localidades;
    private List<Rota> rotas;
    private List<Automovel> automoveis;
    private List<Encomenda> encomendas;

    public DataManager() {
        initializeDummyData();
    }

    /**
     * Inicializa dados de exemplo da ilha de Santiago para visualização.
     */
    private void initializeDummyData() {
        localidades = new ArrayList<>();
        rotas = new ArrayList<>();
        automoveis = new ArrayList<>();
        encomendas = new ArrayList<>();

        // ----------------------------------------------------- ZONAS (vértices)
        // Posições aproximadas à geografia da ilha (alongada N–S:
        // Tarrafal no norte, Praia no sul).
        localidades.add(new Localidade(1, "Tarrafal",            375,  90));
        localidades.add(new Localidade(2, "Calheta S. Miguel",   475, 165));
        localidades.add(new Localidade(3, "Assomada",            305, 235));
        localidades.add(new Localidade(4, "Picos",               390, 265));
        localidades.add(new Localidade(5, "Pedra Badejo",        495, 285));
        localidades.add(new Localidade(6, "S. L. Órgãos",        375, 345));
        localidades.add(new Localidade(7, "São Domingos",        405, 420));
        localidades.add(new Localidade(8, "Cidade Velha",        330, 485));
        localidades.add(new Localidade(9, "Praia",               440, 495)); // capital

        // ----------------------------------------------------- ROTAS = ESTRADAS
        rotas.add(new Rota(1, 3, 35));   // Tarrafal – Assomada
        rotas.add(new Rota(1, 2, 30));   // Tarrafal – Calheta S. Miguel
        rotas.add(new Rota(2, 5, 25));   // Calheta – Pedra Badejo
        rotas.add(new Rota(3, 4, 12));   // Assomada – Picos
        rotas.add(new Rota(4, 6, 10));   // Picos – S. L. Órgãos
        rotas.add(new Rota(3, 6, 20));   // Assomada – S. L. Órgãos
        rotas.add(new Rota(6, 7, 15));   // S. L. Órgãos – São Domingos
        rotas.add(new Rota(7, 9, 25));   // São Domingos – Praia
        rotas.add(new Rota(9, 8, 12));   // Praia – Cidade Velha
        rotas.add(new Rota(5, 7, 30));   // Pedra Badejo – São Domingos
        rotas.add(new Rota(5, 9, 45));   // Pedra Badejo – Praia
        rotas.add(new Rota(4, 5, 22));   // Picos – Pedra Badejo

        // ----------------------------------------------------- VEÍCULOS (carros)
        automoveis.add(new Automovel(1, "Toyota Hilux",   "ST-12-34", 1200));
        automoveis.add(new Automovel(2, "Renault Kangoo", "ST-45-67",  800));
        automoveis.add(new Automovel(3, "Hyundai H100",   "ST-78-90", 1500));

        // ----------------------------------------------------- ENCOMENDAS
        encomendas.add(novaEncomenda(101, "Eletrónicos",  9, 7, 1, "Em Rota"));   // Praia → São Domingos
        encomendas.add(novaEncomenda(102, "Livros",       3, 4, 2, "Entregue"));  // Assomada → Picos
        encomendas.add(novaEncomenda(103, "Vestuário",    1, 2, 3, "Pendente"));  // Tarrafal → Calheta
        encomendas.add(novaEncomenda(104, "Alimentos",    5, 9, 1, "Em Rota"));   // Pedra Badejo → Praia
        encomendas.add(novaEncomenda(105, "Medicamentos", 9, 8, 2, "Em Rota"));   // Praia → Cidade Velha
        encomendas.add(novaEncomenda(106, "Mobiliário",   4, 6, 3, "Entregue"));  // Picos → S. L. Órgãos
    }

    private Encomenda novaEncomenda(int id, String desc, int origem, int destino,
                                    int veiculo, String status) {
        Encomenda e = new Encomenda();
        e.setId(id);
        e.setDescricao(desc);
        e.setIdOrigem(origem);
        e.setIdDestino(destino);
        e.setIdVeiculo(veiculo);
        e.setStatusEncomenda(status);
        return e;
    }

    // ----------------------------------------------------------- GETTERS
    public List<Localidade> getLocalidades() {
        return new ArrayList<>(localidades);
    }

    public List<Rota> getRotas() {
        return new ArrayList<>(rotas);
    }

    public List<Automovel> getAutomoveis() {
        return new ArrayList<>(automoveis);
    }

    public List<Encomenda> getEncomendas() {
        return new ArrayList<>(encomendas);
    }

    public Localidade getLocalidade(int id) {
        return localidades.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Rota getRota(int origem, int destino) {
        return rotas.stream()
                .filter(r -> r.getIdOrigem() == origem && r.getIdDestino() == destino)
                .findFirst()
                .orElse(null);
    }

    public Automovel getAutomovel(int id) {
        return automoveis.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Encomenda getEncomenda(int id) {
        return encomendas.stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ----------------------------------------------- INFORMAÇÕES CONSOLIDADAS
    public double getDistanciaTotal() {
        return rotas.stream().mapToDouble(Rota::getDistancia).sum();
    }

    public int getEncomendasPorStatus(String status) {
        return (int) encomendas.stream()
                .filter(e -> status.equals(e.getStatusEncomenda()))
                .count();
    }
}
