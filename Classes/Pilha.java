package Classes;


public class Pilha {
    private No topo;

    public Pilha(){
        this.topo = null;
    }

    public boolean isVazia(){
        return (topo == null);
    }

    public void push(Eventos evento){
        No novo = new No(evento);

        if(isVazia()){
            topo = novo;
        }else{
            novo.setProx(topo);
            topo = novo;
        }
    }

    public Eventos pop() {
        if (isVazia()){
            System.out.println("Pilha vazia");
            return null;
        }else{
            Eventos evento = topo.getInfo();
            topo = topo.getProx();
            return evento;
        }
    }
    
    public void remover(Eventos evento){
        if (isVazia()){
            System.out.println("Pilha vazia");
            return;
        }else{
            No atual = topo;
            No anterior = null;

            while (atual != null && atual.getInfo() != evento){
                anterior = atual;
                atual = atual.getProx();
            }
            if (atual == null){
                System.out.println("Elemento nao foi encontrado na pilha");
                return;
            }
            if(anterior == null){
                topo = atual.getProx();
            }else{
                anterior.setProx(atual.getProx());
            }
        }
    }
    
}
