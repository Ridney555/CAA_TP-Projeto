package Classes;

import java.time.LocalTime;
import java.util.Scanner;

public class EventoMain {
    private static boolean conflitoEncontrado;

    public static void main(String[] args){
        BaseDeDados.testeDeConecxao();
        Scanner scanner = new Scanner(System.in);

        GerenciadorEventos gerenciador = new GerenciadorEventos();

        ListaDuplamwnteL lista = new ListaDuplamwnteL();

        Pilha pilha = new Pilha();
        int opcao = -1;

        while(opcao != 0) {
            System.out.println("|\n---------------------------------------->|");
            System.out.println("|--------------->  MENU   <-----------------|");
            System.out.println("|-------------------------------------------|");
            System.out.println("1 -> Cadastrar Evento ");
            System.out.println("2 -> Visualizar Cronograma");
            System.out.println("3 -> Inscrever Participante");
            System.out.println("4 -> Remover Evento por ID");
            System.out.println("5 -> Desfazer Último Cadastro");
            System.out.println("6 -> Gerar Relatorio em Eventos");
            System.out.println("0 -> Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch(opcao) {
                case 1:
                    System.out.print("ID do Evento: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nome do Evento: ");
                    String nome = scanner.nextLine();
                    System.out.print("Data (AAAA-MM-DD): ");
                    String data = scanner.nextLine();
                    System.out.print("Hora de Início (h:m): ");
                    LocalTime inicio = LocalTime.parse(scanner.nextLine());
                    System.out.print("Hora de Fim (h:m): ");
                    LocalTime fim = LocalTime.parse(scanner.nextLine());
                    System.out.print("Capacidade Máxima: ");
                    int capacidade = scanner.nextInt();

                    Eventos novo = new Eventos(nome, data, inicio, fim, capacidade, id);

                    conflitoEncontrado = false;
                    No atual = lista.getInicio();
                    while(atual != null){
                        if(gerenciador.criarEventos(atual.getInfo(), novo)) {
                            conflitoEncontrado = true;
                            break;
                        }
                        atual = atual.getProx();
                    }

                    if(conflitoEncontrado){
                        System.out.println("Já existe um evento nesta data");
                    }else{
                        lista.inserirFim(novo);
                        pilha.push(novo); 
                        System.out.println("Evento  foi cadastrado com sucesso");

                        //envia os dados para o phdmyadmin
                        gerenciador.salvarEventoNoBanco(novo);
                    }
                    break;

                case 2:
                    if(lista.isVazia()){
                        System.out.println("Nenhum evento no cronograma.");
                    }else{
                        System.out.println("\n--- Cronograma atual ---");
                        Eventos[] todos = lista.retornarTodosEventos();
                        for(Eventos ev : todos) {
                            System.out.println("ID: " + ev.getId() + " | " + ev.getNome() + " | " + ev.getData() + " [" + ev.getHoraInicio() + " - " + ev.getHoraFim() + "]");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o ID do evento para inscrição: ");
                    int idBusca = scanner.nextInt();
                    Eventos ev = lista.procurarPorId(idBusca);
                    
                    if(ev == null){
                        System.out.println("Evento não encontrado!");
                    }else{
                        System.out.print("ID do Participante: ");
                        int partId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nome do Participante: ");
                        String partNome = scanner.nextLine();
                        System.out.print("Telefone: ");
                        int partTel = scanner.nextInt();

                        Participante p = new Participante(partId, partNome, partTel);
                        gerenciador.increverParticipantes(p, ev);
                        if(ev.getListaParticipantes().contains(p)){
                            //vai salvar os dados do participante na tabela do banco de dados
                            gerenciador.salvarParticipanteNoBanco(p);
            
                            //vai criar um id unico para a tabela incrcao
                             System.out.print("Digite um ID para confirmar q sua inscricao: ");
                            int idInscricao = scanner.nextInt();
                             //faz a conecxao com a tabela inscricao
                             gerenciador.salvarInscricaoNoBanco(idInscricao, p.getId(), ev.getId());
                        }
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID do evento a remover: ");
                    int idRemover = scanner.nextInt();
                    lista.removerPorId(idRemover);
                    //remove o evento por id no banco de dados
                    gerenciador.removerEventoDoBanco(idRemover);
                    break;

                case 5:
                    Eventos ultimoDesfeito = pilha.pop();
                    if(ultimoDesfeito == null){
                        System.out.println("Nenhuma ação para desfazer.");
                    }else{
                        lista.removerPorId(ultimoDesfeito.getId());
                        System.out.println("Evento '" + ultimoDesfeito.getNome() + "' desfeito.");
                    }
                    break;

                case 6:
                    System.out.println("Gerando relatorio dos eventos...");
                    gerenciador.gerarRelatoriosPDF(lista.retornarTodosEventos());
                    break;

                case 0: 
                    System.out.println("Saindo do sistema...");
                    
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
        }

}
