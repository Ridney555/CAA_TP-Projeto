## **Relatorio do Projeto Final Projeto CAA**

##**Elementos do Grupo:								##Docente:**

* Ridney Alves									- Valerio Santos
* Helder Tavares
* Kely Furtado

  ##**Projeto -> Gerencidor de Enventos**

---

## 2. Estrutura do Programa e Arquitetura

  O sistema adota uma arquitetura modular baseada em Programação Orientada a Objetos (POO), separando as estruturas de dados, as entidades de negócio, a interface com o utilizador e a camada de persistência.

### 2.1. Descrição Resumida das Classes e Serviços

* **`EventoMain`**: Atua como o ponto de entrada da aplicação (classe cliente principal). Disponibiliza o menu interativo via terminal, captura os dados introduzidos pelo utilizador através da classe `Scanner` e coordena as chamadas aos serviços de dados e de persistência.
* **`Eventos`**: Classe que modela a entidade de um evento (ID, nome, data, hora de início/fim e capacidade máxima). Contém internamente uma `ArrayList<Participante>` para gerir em memória a lista de inscritos em cada evento específico.
* **`Participante`**: Classe que representa os utilizadores do sistema que se inscrevem nos eventos (armazenando ID, nome e telefone).
* **`No`**: Elemento fundamental das estruturas de dados dinâmicas. Funciona como um nó que encapsula um objeto do tipo `Eventos` e guarda referências bidirecionais (`prox` e `ant`).
* **`ListaDuplamwnteL`**: Implementação manual de uma **Lista Duplamente Ligada**. Armazena os eventos ativos em memória, permitindo inserções eficientes no fim, remoções por ID e iteração para listagem do cronograma.
* **`Pilha`**: Implementação manual de uma **Pilha (LIFO)**. Regista a ordem sequencial de cadastro de eventos para suportar exclusivamente a funcionalidade de "Desfazer Último Cadastro" (`pop`).
* **`BaseDeDados`**: Responsável pela infraestrutura de rede e ligação local. Fornece o método `getConnection()` configurado para uma base de dados MySQL (`SGE1`) e um método de teste de conectividade.
* **`GerenciadorEventos`**: Funciona como a camada de lógica de negócios (Business Logic) e persistência de dados (DAO). Realiza validações críticas (como conflitos de horários e limite de capacidade) e executa as instruções SQL (`INSERT`, `DELETE`) na base de dados.

### 2.2. Justificação da Escolha das Estruturas de Dados

1. **Lista Duplamente Ligada (`ListaDuplamwnteL`)**: A escolha desta estrutura justifica-se pela necessidade de percorrer o cronograma de eventos em ambas as direções e pela eficiência em operações de remoção e inserção de nós no meio da lista sem a necessidade de realocar ou reorganizar índices contíguos de memória, ao contrário de um array convencional.
2. **Pilha (`Pilha`)**: Ideal para a operação "Desfazer" , como o último evento inserido deve ser o primeiro a ser removido ao acionar o comando de desfazer, o comportamento LIFO (*Last In, First Out*) desta estrutura provou ser a solução técnica mais elegante e adequada.

---

## 3. Implementação do CRUD e Persistência de Dados

  O projeto cumpre integralmente o requisito de persistência de dados através da integração com um Sistema de Gestão de Bases de Dados Relacionais (SGBD) **MySQL**.

### Operações CRUD Mapeadas:

* **Create (C):** * Inserção de novos registos na tabela `eventos` via `salvarEventoNoBanco()`.
  * Inserção na tabela `participantes` via `salvarParticipanteNoBanco()`.
  * Criação de novos vínculos relacionais na tabela `inscricao` via `salvarInscricaoNoBanco()`.
* **Read (R):** * A leitura inicial e visualização do cronograma em memória é feita através do método `retornarTodosEventos()` da lista ligada.
  * O script SQL disponibiliza as consultas globais (`SELECT * FROM ...`) para auditoria direta no phpMyAdmin.
* **Update (U):**
  * A atualização dinâmica ocorre na tabela de junção `inscricao` sempre que um novo participante é associado a um evento existente.
* **Delete (D):** * Remoção física de eventos através do ID com a instrução `DELETE FROM eventos WHERE id = ?` implementada em `removerEventoDoBanco()`.

---

## 4. Testes, validações e casos de erro e execucao do programa

  O sistema inclui mecanismos de validação em tempo de execução para garantir a integridade dos dados e evitar falhas críticas:

1. **Validação de Conflito de Horários:** O método `criarEventos()` impede o registo de dois eventos na mesma data cujos horários se sobreponham:
   $$
   \text{Se } (\text{HoraInicio}_{\text{novo}} < \text{HoraFim}_{\text{existente}}) \land (\text{HoraFim}_{\text{novo}} > \text{HoraInicio}_{\text{existente}}) \implies \text{Bloqueia Cadastro}

   $$
2. **Controlo de Lotação Máxima:** O método `increverParticipantes()` verifica se o tamanho da lista atual de inscritos é estritamente inferior à capacidade máxima definida para o evento antes de efetivar a inscrição.
3. **Tratamento de Exceções SQL (Robustez):** * O sistema prevê falhas de ligação ao MySQL (ex: caso o Apache/MySQL não estejam ativos no XAMPP), capturando `SQLException` e instruindo o utilizador.
   * Tratamento do erro de chave duplicada (Error Code `1062`), permitindo que um participante já registado noutro evento continue o processo de inscrição sem que a aplicação trinque.
4. ****Execução do Programa**:
5. **

<---------------------------------------->
---------------->  MENU   <-----------------
<------------------------------------------>

1 -> Cadastrar Evento
2 -> Visualizar Cronograma
3 -> Inscrever Participante
4 -> Remover Evento por ID
5 -> Desfazer Último Cadastro
6 -> Sair
Escolha uma opção: 3
Digite o ID do evento para inscrição: 2
ID do Participante: 1
Nome do Participante: Ridney Alves
Telefone: 9876453
Participante Ridney Alves inscrito com sucesso no evento: Palestra
--> Participante gravado no MySQL!
Digite um ID para confirmar q sua inscricao: 1
A inscricao foi feita com sucesso

<---------------------------------------->
---------------->  MENU   <-----------------
<------------------------------------------>
1 -> Cadastrar Evento
2 -> Visualizar Cronograma
3 -> Inscrever Participante
4 -> Remover Evento por ID
5 -> Desfazer Último Cadastro
6 -> Sair
Escolha uma opção:

 <---------------------------------------->
---------------->  MENU   <-----------------
<------------------------------------------>
1 -> Cadastrar Evento
2 -> Visualizar Cronograma
3 -> Inscrever Participante
4 -> Remover Evento por ID
5 -> Desfazer Último Cadastro
6 -> Sair
Escolha uma opção: 3
Digite o ID do evento para inscrição: 1
ID do Participante: 1
Nome do Participante: Ridney Alves
Telefone: 9579769
Participante Ridney Alves inscrito com sucesso no evento: Palestra: Sa?de Mental

<---------------------------------------->
---------------->  MENU   <-----------------
<------------------------------------------>
1 -> Cadastrar Evento
2 -> Visualizar Cronograma
3 -> Inscrever Participante
4 -> Remover Evento por ID
5 -> Desfazer Último Cadastro
6 -> Sair
Escolha uma opção:

---

## 5. Manual do Utilizador

### 5.1. Sequência Correta de Operações

1. **Preparação do Ambiente:** Execute o servidor MySQL (XAMPP/WampServer). Execute o script contido em `BD_Gestao Eventos.sql` para gerar a base de dados `SGE1` e as suas respetivas tabelas.
2. **Arranque da Aplicação:** Execute a classe `EventoMain`. O sistema tentará estabelecer ligação imediata à base de dados, exibindo a mensagem: `"A Conexão estabelecida com a base de dados"`.
3. **Fluxo de Utilização Recomendado:**
   * **Passo 1:** Selecione a **Opção 1** para registar um ou mais eventos.
   * **Passo 2:** Selecione a **Opção 2** para validar se os eventos foram adicionados ordenadamente ao cronograma em memória.
   * **Passo 3:** Selecione a **Opção 3** para inscrever participantes nos eventos criados, informando os IDs correspondentes.
   * **Passo 4:** Caso tenha cometido um erro no último registo de evento, use a **Opção 5** para reverter a ação imediatamente.

### 5.2. Mensagens de Erro Comuns e Justificações

* *`"Já existe um evento nesta data"`*: Ocorre quando o utilizador tenta marcar um evento que colide no horário com um evento já presente na `ListaDuplamwnteL`.
* *`"O evento ja esta lotado"`*: Exibido quando a capacidade definida no cadastro do evento foi atingida e o sistema bloqueia novas adições ao `ArrayList`.
* *`"Houve uma falha ao conectar com a base de dados"`*: Ocorre se o serviço MySQL estiver desligado ou se as credenciais de acesso (User/Password) em `BaseDeDados.java` estiverem incorretas.

---

## 6. Conclusão

  O desenvolvimento deste Sistema de Gestão de Eventos permitiu consolidar na prática os conceitos teóricos de Estruturas de Dados Lineares e a sua ponte com a persistência de dados relacional. As estruturas de **Lista Duplamente Ligada** e **Pilha** mostraram-se eficientes e adequadas para os propósitos de manipulação em memória exigidos.

### Funcionalidades Adicionais para Melhorar o Sistema:

* **Automatização de Testes:** Implementação de uma suite de testes unitários com *JUnit* para automatizar a validação dos métodos de inserção, remoção e deteção de conflitos de horário.
* **Interface Gráfica (GUI):** Evolução da aplicação de terminal para uma interface visual utilizando *JavaFX* ou *Swing*, tornando a experiência do utilizador final mais intuitiva.
