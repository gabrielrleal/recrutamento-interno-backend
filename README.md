# Como rodar a aplicação de recrutamento interno 🚀

## Pré-requisitos 📋

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:

- **Java 17**: A versão do Java utilizada no projeto. Verifique se você tem a versão correta instalada. ☕
- **Maven**: O gerenciador de dependências do projeto. 🧑‍🔧
- **Git**: O sistema de controle de versão para clonar o repositório. 📚
- **PostgreSQL**: O banco de dados utilizado para armazenar os dados da aplicação. 🐘

## Passos para rodar a aplicação 👣

### Clonar o repositório:

git clone https://github.com/gabrielrleal/recrutamento-interno-backend.git
## Configurar o banco de dados:

###  Instalação local

Instale o PostgreSQL em sua máquina.
Crie um banco de dados chamado recrutamento.
Configure as informações de conexão (URL, usuário, senha) no arquivo application.properties.
Buildar e rodar a aplicação:
Abra o terminal na pasta raiz do projeto e execute:


mvn spring-boot:run

A aplicação estará disponível em http://localhost:8080. 🎉

### Rodando os testes unitários 🧪
Para garantir a qualidade do código, rode os testes unitários com o seguinte comando:


mvn test

### Documentação da API 📖

A documentação da API, gerada automaticamente pelo Springdoc OpenAPI, estará disponível em:

http://localhost:8080/swagger-ui/index.html

Referência ao Frontend
Para configurar e rodar o frontend do projeto, siga as instruções na documentação do frontend. https://github.com/gabrielrleal/recrutamento-interno-frontend

## Observações 📝
Certifique-se de ter o PostgreSQL em execução antes de rodar a aplicação.
Para rodar os testes unitários, o projeto utiliza um banco de dados H2 em memória, configurado no arquivo application-test.properties.
A configuração para conexão com o front-end está definida no arquivo WebConfig.java. Se você estiver utilizando um frontend em um endereço diferente, ajuste as configurações de CORS nesse arquivo.
