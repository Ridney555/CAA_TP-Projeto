package Classes;


public class Pilha {
    private No topo;

    public Pilha(){
        this.topo = null;
    }

    public boolean isVazia(){
        return (topo == null);
    }

    public void push(int info){
        No novo = new No(info);

        if(isVazia()){
            topo = novo;
        }else{
            novo.setProx(topo);
            topo = novo;
        }
    }

    public int pop() {
        if (isVazia()){
            System.out.println("Pilha vazia");
            return -1;
        }else{
            int info = topo.getInfo();
            topo = topo.getProx();
            return info;
        }
    }
    
    
}
