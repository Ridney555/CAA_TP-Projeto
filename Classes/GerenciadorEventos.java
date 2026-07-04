package Classes;

public class GerenciadorEventos {
    private ListaDuplamwnteL listaEventos;

    //verificar se a hora de um evento nao coicide com a hora de outro evento
    public boolean verificarConflito(Eventos outroEvento){
        if(this.data.equals(outroEvento.getData())){
            if((this.horaInicio.isBefore(outroEvento.getHoraFim()) && this.horaFim.isAfter(outroEvento.getHoraInicio()))){
                return true;
            }
        }
        return false;
    }

    /*metedo para inscrever um participante em um evento, esse  metodo verifica se lista de participante e menor 
    que a capacidade maxima do evento, se for menor ele inscreve o participante, se nao ele retorna
    uma mensagem que o evento esta cheio.
    */
    public void inscreverParticipante(Eventos evento, Participante participante){
        if(evento.getListaParticipantes().size() < evento.getCapcidadeMaxima()){
            evento.getListaParticipantes().add(participante);
            System.out.println("Mas um participante foi inscrito no evento "+evento.getNome());
        }else{
            System.out.println("Evento cheio");
        }
    }

    /*metodo para remover um participante se a ultima acao do particapnte foi a incricao num evento

    */
    public void removerParticipante(Eventos evento, Participante participante){
        if(evento.getListaParticipantes().contains(participante)){
            evento.getListaParticipantes().remove(participante);
            System.out.println("Participante removido do evento "+evento.getNome());
        }else{
            System.out.println("Participante nao esta inscrito no evento "+evento.getNome());
        }
    }
}
