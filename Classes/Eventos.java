package Classes;

import java.time.LocalTime;

public class Eventos {
    private int id;
    private String nome;
    private String data;
    private LocalTime HoraInicio;
    private LocalTime HoraFim;
    private int capcidadeMaxima;

    private ListaDuplamwnteL listaParticipantes = new ListaDuplamwnteL();

    
    public Eventos(String nome, String data, LocalTime HoraInicio, LocalTime HoraFim, int capcidadeMaxima, int id){
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.HoraInicio = HoraInicio;
        this.HoraFim = HoraFim;
        this.capcidadeMaxima = capcidadeMaxima;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public LocalTime getHoraInicio(){
        return HoraInicio;
    }

    public void setHoraInicio(LocalTime HoraInicio){
        this.HoraInicio = HoraInicio;
    }

    public LocalTime getHoraFim(){
        return HoraFim;
    }

    public void setHoraFim(LocalTime HoraFim){
        this.HoraFim = HoraFim;
    }
    
    public int getCapcidadeMaxima(){
        return capcidadeMaxima;
    }

    public void setCapcidadeMaxima(int capcidadeMaxima){
        this.capcidadeMaxima = capcidadeMaxima;
    }

    //conversao para csv
    
}
