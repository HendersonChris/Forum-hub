ForumHub API

API REST desenvolvida em Java com Spring Boot para gerenciamento de um fórum de discussão.
A aplicação permite cadastro de usuários, criação de tópicos, gerenciamento de cursos e autenticação segura utilizando JWT (JSON Web Token).

Tecnologias utilizadas

Java 17+

Spring Boot

Spring Security

Spring Data JPA

Hibernate

JWT (Auth0)

Lombok

Maven

MySQL / PostgreSQL (ou outro banco relacional)

Funcionalidades
Autenticação

Login de usuário

Geração de token JWT

Proteção de rotas com Spring Security

Usuários

Cadastro de usuários

Atualização de dados

Exclusão lógica de usuários

Consulta de usuário

Cursos

Cadastro de cursos

Listagem de cursos

Atualização de cursos

Consulta por ID

Tópicos

Criação de tópicos

Listagem de tópicos

Atualização de tópicos

Exclusão de tópicos

Detalhamento de tópico

Validações

Usuário ativo

Tópico duplicado

Validação de dados de entrada

Tratamento global de erros

Estrutura do Projeto
src
 └─ main
     └─ java
         └─ br.com.forumhub
             ├─ controller
             ├─ domain
             │   ├─ usuario
             │   ├─ curso
             │   ├─ topico
             │   └─ validacoes
             ├─ infra
             │   ├─ security
             │   └─ exception
             └─ ForumhubApplication.java
Segurança

A API utiliza Spring Security + JWT.

Fluxo de autenticação:

Usuário faz login em /login

API retorna um token JWT

O token deve ser enviado no header das requisições:

Authorization: Bearer SEU_TOKEN_AQUI
Endpoints principais
Autenticação
POST /login
Usuários
POST /usuarios
GET /usuarios/{id}
PUT /usuarios
DELETE /usuarios/{id}
Cursos
POST /cursos
GET /cursos
GET /cursos/{id}
PUT /cursos
Tópicos
POST /topicos
GET /topicos
GET /topicos/{id}
PUT /topicos/{id}
DELETE /topicos/{id}
Como executar o projeto
1️⃣ Clonar o repositório
git clone https://github.com/seu-usuario/forumhub.git
2️⃣ Entrar na pasta
cd forumhub
3️⃣ Configurar o banco de dados

Editar o arquivo:

application.properties

Exemplo:

spring.datasource.url=jdbc:mysql://localhost/forumhub
spring.datasource.username=root
spring.datasource.password=senha
4️⃣ Definir a chave JWT
api.security.token.jwt=seu_segredo_super_seguro
5️⃣ Rodar o projeto
./mvnw spring-boot:run

ou

mvn spring-boot:run
Documentação da API

Se o projeto tiver Swagger habilitado:

http://localhost:8080/swagger-ui.html

ou

http://localhost:8080/swagger-ui/index.html
Autor

Projeto desenvolvido para fins de estudo utilizando Spring Boot e arquitetura REST.
