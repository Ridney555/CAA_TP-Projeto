package Classes;

/**
 * Classe para representar uma rota entre localidades
 * com informações de distância
 */
public class Rota {
    private int idOrigem;
    private int idDestino;
    private double distancia;
    
    public Rota(int idOrigem, int idDestino, double distancia) {
        this.idOrigem = idOrigem;
        this.idDestino = idDestino;
        this.distancia = distancia;
    }
    
    // Getters e Setters
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
    
    public double getDistancia() {
        return distancia;
    }
    
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
    
    @Override
    public String toString() {
        return "Rota{" +
                "idOrigem=" + idOrigem +
                ", idDestino=" + idDestino +
                ", distancia=" + distancia +
                " km}";
    }
}
