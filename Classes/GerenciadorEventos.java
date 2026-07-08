package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

    /*o metodo pega a ultima acao realizada, se a ultima acao foi criar um evento,
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

    //esse metodo tem como funcao salvar os dados no banco dedos
    public void salvarEventoNoBanco(Eventos evento){
        String sql = "insert into eventos (id, nome, Data, horaInicio, horaFim, capcidadeMaxima) values (?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = BaseDeDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, evento.getId());
            stmt.setString(2, evento.getNome());
            
            stmt.setString(3, evento.getData()); 
            
            stmt.setTime(4, java.sql.Time.valueOf(evento.getHoraInicio()));
            stmt.setTime(5, java.sql.Time.valueOf(evento.getHoraFim()));
            stmt.setInt(6, evento.getCapacidadeMaxima());
            
            stmt.executeUpdate();
            System.out.println("O Evento foi Gravado com Sucesso no banco de dados");
            
        }catch(SQLException e){
            System.out.println("Houve um erro ao salvar na base de dados: " + e.getMessage());
        }
    }

    /*metodo para que ao escolher a opcao de remover um evento por id  ele vai remover 
    no banco de dados MySql
    */
    public void removerEventoDoBanco(int id) {
        String sql = "delete from eventos where id = ?";
        
        try(Connection conct = BaseDeDados.getConnection();
             PreparedStatement stmt = conct.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("O Evento de id " + id + " foi apagado com sucesso do banco de dados");
            }else{
                System.out.println("Nao existe nenhum evento com esse id " + id + " no banco de dados");
            }
        }catch (SQLException e){
            System.out.println("Aconteceu um erro ao remover o evento da base de dados: " + e.getMessage());
        }
    }

    //vai salvar os dados do participante no banco de dados
    public void salvarParticipanteNoBanco(Participante participante) {
        String sql = "insert into participantes (id, nome, telefone) values (?, ?, ?)";
        
        try(Connection conn = BaseDeDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, participante.getId());
            stmt.setString(2, participante.getNome());
            stmt.setInt(3, participante.getTelefone());
            stmt.executeUpdate();
            System.out.println("--> Participante gravado no MySQL!");
        } catch (SQLException e) {
            //se ja estiver um participante com o mesmo id e le exibe uma mensagem de erro e continua a inscricao
            if (e.getErrorCode() == 1062) { 
                System.out.println("O participante foi cadastrado com sucesso no banco de dados");
            } else {
                System.out.println("Aconteceu um erro ao salvar p participante" + e.getMessage());
            }
        }
    }

    //esse metodo ira gravar os participantes e eventos na tabela incricao 
    public void salvarInscricaoNoBanco(int idInscricao, int idParticipante, int idEvento) {
        String sql = "insert into inscricao (id, idParticipantes, idEventos) values (?, ?, ?)";
        
        try (Connection conn = BaseDeDados.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idInscricao);
            stmt.setInt(2, idParticipante);
            stmt.setInt(3, idEvento);
            
            stmt.executeUpdate();
            System.out.println("A inscricao foi feita com sucesso");
        } catch (SQLException e) {
            System.out.println("Aconteceu um erro ao salvar a incricao no banco de dados: " + e.getMessage());
        }
    }

}
