package Classes;

public class No{
    private No prox;
    private No ant;
    private Eventos info;

    //construtores
    public No() {
        this.info = null;
        this.prox = null;
        this.ant = null;
    }
    
    public No(Eventos info){
        this.info = info;
        this.prox = null;
        this.ant = null;
    }

    public No(Eventos info, No prox, No ant){
        this.info = info;
        this.prox = prox;
        this.ant = ant;
    }
    
    public Eventos getInfo(){
        return info;
    }
    public void setInfo(Eventos info){
        this.info = info;
    }
    public No getProx() {
        return prox;
    }
    public void setProx(No prox){
        this.prox = prox;
    }
    public No getAnt(){
        return ant;
    }
    public void setAnt(No ant){
        this.ant = ant;
    }
}