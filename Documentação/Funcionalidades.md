CAA - trabalho pratico funcionalidades do projeto (ListasDuplamenteLigada/Pilhas)
---------------------------------------------------------------------------------------------------

1 -> Gererenciamento de Cronograma(uso de Listas Duplamente Ligadas):
    - Nós e Apontadores: para cadastrar e organizar as atividades do evento, em que elas serao ordenadas pelo seu horario de inicio.
    - Manutenção da Lista: inserção dinâmica ordenada ou remoção de nós(eventos) em qualquer posição sem quebrar os ponteiros anteriores e próximos.
2 -> CRUD
    - C: cadastrar uma atividade ou participante;
    - R: consultar cronograma e participantes inscritos na atividade;
    - U: alterar o horario ou lugar para realizar a atividade;
    - R: remover ou cancelar a atividade depois de ultrapassar o limite maximo de atraso permitido;

3 -> Operações e Lógica (Estruturas de Dados)
    - Pilha: Organização do histórico de modificações, exibindo assim a ultima operacao no topo para o sistema de desfazer;
    - Validações e Alertas: o sistema ira bloquear novas inscricoes depois de atingir a capacidade maxima do lugar;
    - Fluxo de Operações: validacao para que tenha um limite de atividades por participante(max 3 por dia), choques de horarios na mesma sala e cancelamentos;
  
4 -> Persistência de Dados e Relatórios(CSV/PDF)
    - CSV: Armazenamento permanente de todas as atividades e históricos de inscricoes em ficheiros .csv , garantindo assim persistência ao desligar o sistema;
    - Relatórios em PDF: gerar um ficheiro PDF com o cronograma oficial e mapa de ocupação do evento a partir dos dados do do ficheiro .csv;
    