package Classes;

/**
 * Classe para representar um automóvel da frota
 */
public class Automovel {
    private int id;
    private String modelo;
    private String placa;
    private double capacidade;
    private String status; // Livre, Em Rota, Manutenção
    
    public Automovel(int id, String modelo, String placa, double capacidade) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade = capacidade;
        this.status = "Livre";
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getModelo() {
        return modelo;
    }
    
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    
    public String getPlaca() {
        return placa;
    }
    
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
    public double getCapacidade() {
        return capacidade;
    }
    
    public void setCapacidade(double capacidade) {
        this.capacidade = capacidade;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Automovel{" +
                "id=" + id +
                ", modelo='" + modelo + '\'' +
                ", placa='" + placa + '\'' +
                ", capacidade=" + capacidade +
                ", status='" + status + '\'' +
                '}';
    }
}
