package Classes;

public class GerenciadorEventos {
   
    /*esse metodo ira percorrer uma lista e verificar se ja nao existe um evento para o mesmo horario,
    se ja existe no mesmo horario, o evento nao sera criado, caso contrario, o evento sera criado 
    e adicionado a lista de eventos
    */ 
    public boolean criarEventos(Eventos eventoExistente, Eventos novoEvento){
        if (eventoExistente.getData().equals(novoEvento.getData())) {
            if ((novoEvento.getHoraInicio().isBefore(eventoExistente.getHoraFim()) && 
                 novoEvento.getHoraFim().isAfter(eventoExistente.getHoraInicio()))) {
                return true; 
            }
        }
        return false;
    }

    /*vai verificar se o evento que foi criado ja atiniu a sua capacidade maxima, 
    se ja estiver lotado o metodo exibe uma mensagem que ja esta na capacidade maxima se nao
    estiver lotada, o metodo ira inscrever o participante no evento e adicionar o participante a lista de participantes
    */ 
    public void increverParticipantes(Participante participante, Eventos evento){
        if (evento.getListaParticipantes().size() < evento.getCapacidadeMaxima()) {
            evento.getListaParticipantes().add(participante);
            System.out.println("Participante " + participante.getNome() + " inscrito com sucesso no evento: " + evento.getNome());
        } else {
            System.out.println("O evento ja esta lotado");
        }
    }

    /* essse metodo pega a ultima acao realizada, se a ultima acao foi criar um evento,
     o metodo ira remover o evento da lista de eventos
     */
    public void remover(Participante participante, Eventos evento){
        if (evento.getListaParticipantes().contains(participante)) {
            evento.getListaParticipantes().remove(participante);
            System.out.println("Participante removido do evento " + evento.getNome());
        } else {
            System.out.println("Participante não está inscrito no evento " + evento.getNome());
        }
    }

}
