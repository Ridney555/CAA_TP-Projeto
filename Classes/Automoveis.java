package Classes;
public class Automoveis{
    private String modelo;
    private String placa;
    private double capacidade;
    
    public Automoveis(String modelo, String placa, double capaciadade){
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade = capaciadade;
    }

    public String getModelo(){
        return modelo;
    }

    public void setModelo(String modelo){
        this.modelo = modelo;
    }

    public String getPlaca(){
        return placa;
    }

    public void setPlaca(String placa){
        this.placa = placa;
    }

    public double getCapaciadade(){
        return capacidade;
    }

    public void setCapaciadade(double capaciadade){
        this.capacidade = capaciadade;
    }

    
}