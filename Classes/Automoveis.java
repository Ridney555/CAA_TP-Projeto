package Classes;


public class Automoveis {
    private int id;
    private String modelo;
    private String placa;
    private double capacidade;
    
    public Automoveis(String modelo, String placa, double capacidade){
        this.modelo = modelo;
        this.placa = placa;
        this.capacidade = capacidade;
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

    public double getCapacidade(){
        return capacidade;
    }

    public void setCapacidade(double capacidade){
        this.capacidade = capacidade;
    }

    @Override
    public String toString(){
        return "-----+ Automovel -------\n" +
               "Modelo: "+modelo+"\n"+
               "Placa: "+placa+"\n"+
               "Capacidade: "+capacidade;
    }
}