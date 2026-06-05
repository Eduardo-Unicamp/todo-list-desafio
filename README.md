# API TodoList
A clássica Todo list era para ser o "Hello World!" do desenvolvimento backend, só que dessa vez ela vem com bem mais do que apenas um CRUD simples: Tarefas com visibilidade pública e privada, gerenciamento de participantes, salvamento de arquivos bucket no banco remoto do Supabase (formato S3), e autenticação stateless com tokens JWT, entre outros...


## 🚀 Funcionalidades

* **Autenticação e Segurança:** Endpoints seguros de registro e login utilizando JSON Web Tokens (JWT) para autenticação stateless.
* **Gerenciamento Robusto de Tarefas:** Crie, leia, atualize e exclua tarefas. As tarefas incluem atributos detalhados como datas de vencimento, níveis de prioridade (LOW, MEDIUM, HIGH) e status de acompanhamento (TODO, DOING, DONE).
* **Colaboração e Compartilhamento:** Os usuários podem definir tarefas como públicas ou privadas e adicionar vários participantes a uma única tarefa.
* **Anexos de Arquivos na Nuvem:** Faça o upload direto de arquivos nas tarefas e gere URLs assinadas e temporárias para acesso seguro usando o Supabase.
* **Controle de Acesso Baseado em Funções:** Suporte integrado para as permissões `ADMIN` e `USER`.
* **Tratamento de Exceções:** Tratamento global de exceções para violações de integridade de dados, tokens inválidos, campos ausentes e erros de upload de arquivos.

## 🛠️ Tecnologias Utilizadas

* **Core:** Java, Spring Boot
* **Segurança:** Spring Security, Auth0 JWT
* **Persistência:** Spring Data JPA, Hibernate, banco na nuvem com Supabase
* **APIs Externas:** Spring WebFlux (WebClient) para integração com o Supabase
* **Utilitários:** Lombok (para reduzir código boilerplate), MapStruct (para mapeamento contínuo de DTOs)

## 📡 Referência da API

### Autenticação
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/auth/login` | Autentica um usuário e retorna um JWT. |
| `POST` | `/auth/register` | Registra um novo usuário com as funções `ADMIN` ou `USER`. |

### Gerenciamento de Tarefas
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/todo-manager/tasks` | Retorna todas as tarefas públicas. |
| `GET` | `/todo-manager/tasks/my-tasks` | Retorna as tarefas pertencentes ou atribuídas ao usuário autenticado. |
| `POST` | `/todo-manager/tasks` | Cria uma nova tarefa. |
| `GET` | `/todo-manager/tasks/{taskId}` | Retorna os detalhes de uma tarefa específica. |
| `PUT` | `/todo-manager/tasks/{taskId}` | Atualiza uma tarefa existente. |
| `DELETE` | `/todo-manager/tasks/{taskId}` | Exclui uma tarefa. |
| `POST` | `/todo-manager/tasks/{taskId}/{userId}` | Adiciona um participante a uma tarefa. |

### Armazenamento de Arquivos
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/todo-manager/tasks/{taskId}` | Faz o upload de um arquivo multipart como anexo para uma tarefa. |
| `GET` | `/todo-manager/tasks/{taskId}/attachment-url` | Gera e retorna URLs assinadas para os anexos de uma tarefa. |

### Gerenciamento de Usuários
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| `GET` | `/todo-manager/users` | Lista todos os usuários registrados (em ordem alfabética). |
| `POST` | `/todo-manager/users` | Cria um novo usuário manualmente. |
| `GET` | `/todo-manager/users/{userId}` | Retorna um usuário específico. |
| `PUT` | `/todo-manager/users/{userId}` | Atualiza os detalhes de um usuário. |
| `DELETE` | `/todo-manager/users/{userId}` | Remove um usuário do sistema. |
