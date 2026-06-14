package Classes;

/**
 * Classe para representar uma encomenda
 */
public class Encomenda {
    private int id;
    private String descricao;
    private int idOrigem;
    private int idDestino;
    private int idVeiculo;
    private String statusEncomenda; // Pendente, Em Rota, Entregue
    
    public Encomenda() {
        this.statusEncomenda = "Pendente";
    }
    
    public Encomenda(int id, String descricao, int idOrigem, int idDestino, 
                    int idVeiculo, String statusEncomenda) {
        this.id = id;
        this.descricao = descricao;
        this.idOrigem = idOrigem;
        this.idDestino = idDestino;
        this.idVeiculo = idVeiculo;
        this.statusEncomenda = statusEncomenda;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public int getIdOrigem() {
        return idOrigem;
    }
    
    public void setIdOrigem(int idOrigem) {
        this.idOrigem = idOrigem;
    }
    
    public int getIdDestino() {
        return idDestino;
    }
    
    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }
    
    public int getIdVeiculo() {
        return idVeiculo;
    }
    
    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }
    
    public String getStatusEncomenda() {
        return statusEncomenda;
    }
    
    public void setStatusEncomenda(String statusEncomenda) {
        this.statusEncomenda = statusEncomenda;
    }
    
    @Override
    public String toString() {
        return "Encomenda{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", idOrigem=" + idOrigem +
                ", idDestino=" + idDestino +
                ", idVeiculo=" + idVeiculo +
                ", status='" + statusEncomenda + '\'' +
                '}';
    }
}
