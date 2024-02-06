# 🍊 API Orange Juice - Backend

O Orange Portfolio foi desenvolvido durante o Hackathon, a última fase final do processo seletivo da Fcamara, na quinta edição do programa. O backend do projeto desenvolvido em Java com o framework Spring Boot seguindo a arquitetura MVC. A API gerencia usuários e projetos, fornecendo endpoints para registro, autenticação, criação, atualização, exclusão e busca de projetos.

## 🛠️ Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security com autenticação JWT
- Lombok
- JPA
- Flyway
- Validation
- Java JWT
- PostgreSQL
- AWS (EC2 para hospedagem remota)

## 📁 Estrutura do Projeto

O projeto segue a arquitetura MVC (Model-View-Controller) para uma organização clara e modular do código. Consiste em entidades Java que representam os usuários e projetos, além de DTOs (Data Transfer Objects) para transferência de dados entre o frontend e o backend. Aqui está uma visão geral das entidades e seus DTOs correspondentes:

### 🧑‍💼 Entidades

#### User

Representa um usuário da aplicação.

- Campos:
   - id
   - firstname
   - lastname
   - email
   - password
   - profileImageAddress
   - role

#### Project

Representa um projeto criado por um usuário.

- Campos:
   - id
   - title
   - description
   - link
   - imageProject
   - tags
   - deleted
   - user
   - createdAt

### 📝 DTOs

- ProjectRequest
- ProjectResponse
- UserRequest
- UserResponse
- FailLoginResponse
- LoginRequest
- SuccessLoginResponse

## 🎮 Controllers

Existem dois controladores principais:

### UserController

Controla operações relacionadas a usuários, como registro e autenticação.

### ProjectController

Controla operações relacionadas a projetos, como criação, atualização, exclusão e busca.

## 📚 Documentação

A documentação da API é gerada utilizando o Swagger, fornecendo uma interface interativa para explorar os endpoints disponíveis, bem como suas descrições e requisitos de segurança.

## 🔗 Endpoints

- **POST /api/register**: Registra um novo usuário.
- **POST /api/login**: Autentica um usuário e gera um token de acesso.
- **POST /api/projects**: Salva um novo projeto para um usuário.
- **GET /api/projects**: Busca todos os projetos cadastrados.
- **GET /api/projects/user**: Busca todos os projetos do usuário por ID.
- **PUT /api/projects**: Atualiza um projeto existente.
- **DELETE /api/projects/{projectId}**: Exclui um projeto.

Todos os endpoints exigem autenticação com um token JWT no cabeçalho da requisição.

## Tratamento de Exceções

Exceções são tratadas de forma adequada em todo o código, garantindo uma experiência de usuário consistente e robusta.

## Deleção Lógica Implementada

A deleção lógica foi implementada para garantir que os registros excluídos não sejam removidos permanentemente, mantendo a integridade dos dados.

---
## Desenvolvido por:

## Participantes

| Nome          | Função   | Github                                                                                                               | Foto                                                                                                                                       |
| ------------- | -------- | -------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------ |
| Felipe Silva  | Frontend | [![GitHub](https://img.shields.io/badge/-GitHub-black.svg?logo=github&style=flat)](https://github.com/felipel7)      | <img src="https://avatars.githubusercontent.com/u/14916843?s=400&u=f1a2b4919c60d51eba5b1a7736a467f88ff050ee&v=4" height="75" width="75" /> |
| Marcos        | Frontend | [![GitHub](https://img.shields.io/badge/-GitHub-black.svg?logo=github&style=flat)](https://github.com/marcosrsalles) | <img src="https://avatars.githubusercontent.com/u/64453305?v=4" height="75" width="75" />                                                  |
| Fhelipe Alves | Backend  | [![GitHub](https://img.shields.io/badge/-GitHub-black.svg?logo=github&style=flat)](https://github.com/fhelipe27)     | <img src="https://avatars.githubusercontent.com/u/68212163?v=4" height="75" width="75" />                                                  |
| Ramir Junior  | Backend  | [![GitHub](https://img.shields.io/badge/-GitHub-black.svg?logo=github&style=flat)](https://github.com/RamirJunior)   | <img src="https://avatars.githubusercontent.com/u/26365419?v=4" height="75" width="75" />                                                  |

<br/>
<br/>

## 📝 License

Este projeto está sob a licença MIT. Consulte o [LICENSE](LICENSE) para obter mais detalhes.