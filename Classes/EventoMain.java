package Classes;

import java.time.LocalTime;
import java.util.Scanner;

public class EventoMain {
    private static boolean conflitoEncontrado;

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        GerenciadorEventos gerenciador = new GerenciadorEventos();

        ListaDuplamwnteL lista = new ListaDuplamwnteL();

        Pilha pilha = new Pilha();
        int opcao = 0;

        while(opcao !=5) {
            System.out.println("\n<---------------------------------------->");
            System.out.println("---------------->  MENU   <-----------------");
            System.out.println("<------------------------------------------>");
            System.out.println("1 -> Cadastrar Evento ");
            System.out.println("2 -> Visualizar Cronograma");
            System.out.println("3 -> Inscrever Participante");
            System.out.println("4 -> Remover Evento por ID");
            System.out.println("5 -> Desfazer Último Cadastro");
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
                    System.out.print("Data (DD/MM/AAAA): ");
                    String data = scanner.nextLine();
                    System.out.print("Hora de Início (HH:MM): ");
                    LocalTime inicio = LocalTime.parse(scanner.nextLine());
                    System.out.print("Hora de Fim (HH:MM): ");
                    LocalTime fim = LocalTime.parse(scanner.nextLine());
                    System.out.print("Capacidade Máxima: ");
                    int capacidade = scanner.nextInt();

                    Eventos novo = new Eventos(nome, data, inicio, fim, capacidade, id);

                    No atual = lista.getInicio();
                    while(atual != null){
                        if(gerenciador.criarEventos(atual.getInfo(), novo)) {
                            break;
                        }
                        atual = atual.getProx();
                    }

                    if(conflitoEncontrado){
                        System.out.println("Já existe um evento neste data");
                    }else{
                        lista.inserirFim(novo);
                        pilha.push(novo); 
                        System.out.println("Evento cadastrado com sucesso");
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
                    }
                    break;

                case 4:
                    System.out.print("Digite o ID do evento a remover: ");
                    int idRemover = scanner.nextInt();
                    lista.removerPorId(idRemover);
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
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
        }

}
