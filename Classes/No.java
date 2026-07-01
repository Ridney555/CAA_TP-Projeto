package Classes;

public class No{
    private No prox;
    private No ant;
    private int info;

    //construtores
    public No() {
        this.info = 0;
        this.prox = null;
    }
    
    public No(int info){
        this.info = info;
        this.prox = null;
        this.ant = null;
    }

    public No(int info, No prox, No ant){
        this.info = info;
        this.prox = prox;
        this.ant = ant;
    }
    
    public int getInfo(){
        return info;
    }
    public void setInfo(int info){
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