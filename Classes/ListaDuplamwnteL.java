package Classes;

public class ListaDuplamwnteL {
    private No inicio;
    private No fim;

    public ListaDuplamwnteL() {
        this.inicio = null;
        this.fim = null;
    }

    public No getInicio() {
        return inicio;
    }

    public No getFim() {
        return fim;
    }

    public boolean isVazia(){ 
        return(inicio == null || fim == null);
    }

    //metodos para inserir no inicio e no fim
    public void inserirInicio(Eventos info){
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

    public void inserirFim(Eventos info){
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
    public void removerPorId(int id){
        if(isVazia()){
            System.out.println("Lista vazia");
            return;
        }
        No atual = inicio;

        while(atual != null && atual.getInfo().getId() != id){ 
            atual = atual.getProx();
        }

        if(atual == null){
            System.out.println("Evento não foi encontrado");
            return;
        }

        //vai remover o evento da lista
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
    public Eventos procurarPorId(int id){
        if(isVazia()){
            return null;
        }
        No atual = inicio;

        while(atual != null && atual.getInfo().getId() != id){
            atual = atual.getProx();
        }
        return (atual != null) ? atual.getInfo() : null;
    }

    //retorna umm array com todos os eventos da lista
    public Eventos[] retornarTodosEventos(){
        if(isVazia()){
            System.out.println("Lista vazia");
            return new Eventos[0];
        }
        No atual = inicio;
        int tamanho = 0;

        //vai contar o tamanho da lista
        while(atual != null){
            tamanho++;
            atual = atual.getProx();
        }

        Eventos[] eventos = new Eventos[tamanho];//cria uma array com o tamanho da lista
        atual = inicio;
        for(int i = 0; i < tamanho; i++){
            eventos[i] = atual.getInfo();
            atual = atual.getProx();
        }
        return eventos;
    }
}
