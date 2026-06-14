package Classes;

/**
 * Classe para representar uma localidade (vértice do grafo)
 * com posição no mapa para visualização
 */
public class Localidade {
    private int id;
    private String nome;
    private double posX;
    private double posY;
    
    public Localidade(int id, String nome) {
        this.id = id;
        this.nome = nome;
        this.posX = 0;
        this.posY = 0;
    }
    
    public Localidade(int id, String nome, double posX, double posY) {
        this.id = id;
        this.nome = nome;
        this.posX = posX;
        this.posY = posY;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public double getPosX() {
        return posX;
    }
    
    public void setPosX(double posX) {
        this.posX = posX;
    }
    
    public double getPosY() {
        return posY;
    }
    
    public void setPosY(double posY) {
        this.posY = posY;
    }
    
    @Override
    public String toString() {
        return "Localidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pos=(" + posX + ", " + posY + ")" +
                '}';
    }
}
