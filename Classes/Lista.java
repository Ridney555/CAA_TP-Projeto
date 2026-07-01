package Classes;

public class Lista {
    private No inicio;
    private No fim;

    public Lista() {
        this.inicio = null;
        this.fim = null;
    }

    public boolean isVazia(){ 
        return(inicio == null || fim == null);
    }

    //metodos para inserir no inicio e no fim
    public void inserirInicio(int info){
        No novo = new No(info);

        if(isVazia()){
            inicio = novo;
            fim = novo;
        }else{
            novo.setProx(inicio);
            inicio.setAnt(novo);
            inicio = novo;
        }
    }

    public void inserirFim(int info){
        No novo = new No(info);

        if(isVazia()){
            inicio = novo;
            fim = novo;
        }else{
            fim.setProx(novo);
            novo.setAnt(fim);
            fim = novo;
        }
    }
    
    //metodo para remover um evento da lista por id
    public void removerPorId(int info){
        if(isVazia()){
            System.out.println("Lista vazia");
            return;
        }
        No atual = inicio;

        while(atual != null && atual.getInfo() != info){
            atual = atual.getProx();
        }

        if(atual == null){
            System.out.println("Evento não foi encontrado");
            return;
        }

        if(atual == inicio){
            inicio = atual.getProx();
            if(inicio != null){
                inicio.setAnt(null);
            }else{
                fim = null;
            }
        }else if(atual == fim){
            fim = atual.getAnt();
            if(fim != null){
                fim.setProx(null);
            }else{
                inicio = null;
            }
        }else{
            atual.getAnt().setProx(atual.getProx());
            atual.getProx().setAnt(atual.getAnt());
        }
    }

    //metodo para procurar um evento por id
    public void procurarPorId(int info){
        if(isVazia()){
            System.out.println("Lista vazia");
            return;
        }
        No atual = inicio;

        while(atual != null && atual.getInfo() != info){
            atual = atual.getProx();
        }
        if(atual == null){
            System.out.println("Evento não foi encontrado");
        }else{
            System.out.println("Evento foi encontrado: " + atual.getInfo());
        }
    }


}
