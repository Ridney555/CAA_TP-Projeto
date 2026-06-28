CAA - trabalho pratico funcionalidades do projeto (Arvores/Pilhas/Filas)
---------------------------------------------------------------------------------------------------

1 -> Gererenciamento de Contas(uso de Arvores):
    - Nós e Subárvores: para cadastrar e organizar as contas, em que elas serao ordenadas pela sua quantidade de saldo na conta(aqueles).
    - Manutenção da Árvore: balanceamento automático ou inserção/remoção dinâmica dos nós(contas).
2 -> CRUD
    - C: cadastrar uma conta;
    - R: consultar saldos e moveimentos na conta;
    - U: alterar o codigo para entar na conta;
    - R: remover ou bloqear a conta depois de varias tentativas de colocar o Pin(max 4 tentativas);

3 -> Operações e Lógica (Estruturas de Dados)
    - Pilha: Organização do histórico de transações, exibindo assim a ultima operacao no topo do recibo;
    - Validações e Alertas: o sistema vai bloquear a conta depois de 4 tentativas de colocar o PIN;
    - Fluxo de Operações: validacao para que tenha um limite de levantamento diario(20000 por dia), depósitos e transferências entre contas;
  
4 -> Persistência de Dados e Relatórios(CSV/PDF)
    - CSV: Armazenamento permanente de todas as contas e históricos de transações em ficheiros .csv , garantindo assim persistência ao desligar o sistema;
    - Relatórios em PDF: gerar um ficheiro PDF com o extrato bancário oficial do cliente a partir dos dados do do ficheiro .csv;
    