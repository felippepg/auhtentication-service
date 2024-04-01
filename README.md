# Aplicação de Autenticação 2 fatores

## Descrição

Este é o backend do projeto de autenticação que complementa o frontend [Frontend](https://github.com/felippepg/authentication-service-frontend). Este serviço é responsável por gerenciar a autenticação de usuários, incluindo a funcionalidade de autenticação de dois fatores (2FA), gerando um código em QRCode.


## Funcionalidades

- [x] Autenticação de Usuário: Permite que usuários registrados façam login na aplicação.
- [x] Registro de Usuário: Permite que novos usuários se cadastrem na aplicação, com a opção de habilitar autenticação de dois fatores.
- [x] Geração de QR Code: Para autenticação de dois fatores, o backend gera um QR code que pode ser escaneado por um aplicativo de autenticação para gerar um código numérico.
- [x] Validação de Tokens JWT: Utiliza JSON Web Tokens (JWT) para autenticação e autorização de usuários.

## Tecnologias Utilizadas

- Java: Linguagem de programação utilizada para desenvolvimento do backend.
- Spring Boot: Framework para criação de aplicativos Java, facilitando a configuração e desenvolvimento de aplicativos Spring.
- MySQL: Banco de dados relacional utilizado para armazenar dados dos usuários.
- Docker: Plataforma para desenvolvimento, envio e execução de aplicativos em contêineres.

## Requisitos
Requisitos mínimos para executar a aplicação
- Java Development Kit (JDK) - versão 20
- Docker - versão 20.10.17
- Docker Compose - versão 1.29.2
- Maven 3

Para executar o projeto também é necessário definir as variáveis de ambiente 
- DATABASE_URL: Url do banco de dados
- DATABASE_USER: Usuário do banco de dados
- DATABASE_PASSWORD: Senha do banco de dados
- API_TOKEN: Token da API, necessário para a criação dos tokens JWT
  
### 🎲 Rodando o Projeto

```bash
$ git clone https://github.com/felippepg/authentication-service

# Acesse a pasta do projeto no terminal/cmd
$ cd authentication-service

# Execute o container Docker com a aplicação e o banco de dados
$ $ docker-compose up
```

Desenvolvido Por: Felippe Pires 🚀🎨
