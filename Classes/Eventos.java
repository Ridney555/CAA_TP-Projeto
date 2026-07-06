package Classes;

import java.time.LocalTime;
import java.util.ArrayList;

public class Eventos {
    private int id;
    private String nome;
    private String data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private int capcidadeMaxima;

    private ArrayList<Participante> listaParticipantes = new ArrayList<>();

    
    public Eventos(String nome, String data, LocalTime HoraInicio, LocalTime HoraFim, int capcidadeMaxima, int id){
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.horaInicio = HoraInicio;
        this.horaFim = HoraFim;
        this.capcidadeMaxima = capcidadeMaxima;
    }

    public ArrayList<Participante> getListaParticipantes(){
        return listaParticipantes;
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
        return horaInicio;
    }

    public void setHoraInicio(LocalTime HoraInicio){
        this.horaInicio = HoraInicio;
    }

    public LocalTime getHoraFim(){
        return horaFim;
    }

    public void setHoraFim(LocalTime HoraFim){
        this.horaFim = HoraFim;
    }
    
    public int getCapacidadeMaxima(){
        return capcidadeMaxima;
    }

    public void setCapacidadeMaxima(int capcidadeMaxima){
        this.capcidadeMaxima = capcidadeMaxima;
    }  
    
}
