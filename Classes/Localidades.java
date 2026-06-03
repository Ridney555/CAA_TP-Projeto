package Classes;

//irá representar cada ponto no mapa
public class Localidades{
    private int id;
    private String nome;
    private double[] cordenadas;  // latitude e longitude
    
    public Localidades(int id, String nome, double latitude, double longitude) {
        this.id = id;
        this.nome = nome;
        this.cordenadas = new double[]{latitude, longitude};
    }

    public int getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public double[] getCordenadas(){
        return cordenadas;
    }

    public double getLatitude(){
        return cordenadas[0];
    }

    public double getLongitude(){
        return cordenadas[1];
    }

    
}