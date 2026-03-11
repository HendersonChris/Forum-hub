📚 ForumHub

ForumHub é uma API REST desenvolvida em Java com Spring Boot que simula o funcionamento de um fórum de discussão.
A aplicação permite cadastrar usuários, criar tópicos relacionados a cursos, autenticar usuários com JWT e realizar operações completas de gerenciamento do sistema.

🚀 Funcionalidades

A API possui funcionalidades para gerenciamento de:

👤 Usuários

Cadastro de novos usuários

Atualização de dados

Exclusão lógica de usuários

Consulta de usuário por ID

📚 Cursos

Cadastro de cursos

Listagem de cursos

Atualização de cursos

Consulta de cursos

💬 Tópicos

Criação de novos tópicos

Listagem de tópicos

Atualização de tópicos

Exclusão de tópicos

Detalhamento de tópicos

🔐 Autenticação

Login de usuários

Geração de token JWT

Proteção de rotas com Spring Security

🌐 Segurança

O sistema utiliza autenticação baseada em JWT (JSON Web Token).

Fluxo de autenticação:

O usuário realiza login em /login

A API retorna um token JWT

Esse token deve ser enviado no header das requisições protegidas

Exemplo:

Authorization: Bearer SEU_TOKEN_AQUI
🛠 Tecnologias utilizadas

Java

Spring Boot

Spring Security

Spring Data JPA

Hibernate

JWT (Auth0)

Lombok

Maven

📂 Estrutura do projeto
src/main/java/br/com/forumhub
controller   → controladores da API
domain       → entidades, DTOs e regras de negócio
infra        → segurança e tratamento de exceções

Arquivos principais:

ForumhubApplication.java → classe principal da aplicação
▶️ Como executar o projeto
1️⃣ Clonar o repositório
git clone https://github.com/seu-usuario/forumhub.git
2️⃣ Entrar na pasta do projeto
cd forumhub
3️⃣ Configurar o banco de dados

No arquivo application.properties configure:

spring.datasource.url=jdbc:mysql://localhost/forumhub
spring.datasource.username=root
spring.datasource.password=senha
4️⃣ Definir a chave JWT
api.security.token.jwt=seu_segredo
5️⃣ Executar o projeto
./mvnw spring-boot:run

ou

mvn spring-boot:run
📖 Endpoints principais
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
👨‍💻 Autor

Projeto desenvolvido para fins de estudo utilizando Java e Spring Boot.
