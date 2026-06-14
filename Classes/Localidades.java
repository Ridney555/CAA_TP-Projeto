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

    public void latitude(double latitude){
        if(latitude <= 0 || latitude >= 90){
            throw new IllegalArgumentException("A coordenada esta errada");
        }
    }

    public void longitude(double longitude){
        if(longitude <= 0 || longitude >= 180){
            throw new IllegalArgumentException("A coordenada esta errada");  
        }
    }
    
}