# NLW EXPERT JAVA - Certificação

Esta é uma API em Java que permite que o usuário responda à perguntas e obtenha sua pontuação de acordo com seus acertos.

## ⚙️ Funcionalidades

- **Verificar Permissão para a Prova:** Os usuários podem verificar se possuem permissão para fazer a prova, garantindo que não tenham feito anteriormente.
- **Buscar Questões e Alternativas:** Os usuários podem buscar as questões disponíveis para a prova, bem como as alternativas de resposta.
- **Responder à Prova:** Os usuários podem responder à prova de certificação.
- **Consultar Ranking:** Os usuários podem consultar o ranking com as 10 melhores notas.

## 🧪 Tecnologias Utilizadas

- **Java 17:** Linguagem de programação utilizada no backend da aplicação.
- **Spring Boot 3.2.2:** Framework utilizado para desenvolver o backend da aplicação de forma ágil e eficiente.
- **PostgreSQL:** Banco de dados utilizado para armazenar os dados da aplicação.
- **Docker:** Utilizado para criar, implantar e executar aplicativos em contêineres.

## 📚 O que Aprendi

Neste projeto, aprendi diversas técnicas e ferramentas importantes para o desenvolvimento de aplicações em Java, incluindo:

- **Builder:** Aprendi a usar o padrão de projeto Builder para a criação de objetos complexos de forma simplificada e flexível.
- **Uso de Stream em Java:** Explorei o uso de streams em Java para operações em coleções de dados de forma mais concisa e eficiente.
- **Criação de Seed com JdbcTemplate:** Aprendi a usar o JdbcTemplate para criar dados de seed no banco de dados, facilitando o desenvolvimento e teste da aplicação.
- **Uso do Docker:** Aprendi a utilizar o Docker para empacotar, distribuir e executar a aplicação de forma consistente em diferentes ambientes.

## 🔃 Rotas
### Students
#### **POST** `/students`
- Verifica se pode realizar teste na tecnologia desejada todas empresas

#### BODY
```
{
    "email": "gafe.souza@gmail.com",
    "technology": "Java"
}
```

#### **POST** `/students/certification/answer`
- Cadastra a resposta do teste do usuário
#### BODY
```
{
    "email": "email@email.com",
    "technology": "JAVA",
    "questionsAnswers": [
        {
            "questionID": "c5f02721-6dc3-4fa6-b46d-6f2e8dca9c66",
            "alternativeID": "bafdf631-6edf-482a-bda9-7dce1efb1890"
        },
        {
            "questionID": "b0ec9e6b-721c-43c7-9432-4d0b6eb15b01",
            "alternativeID": "f8e6e9b3-199b-4f0d-97ce-7e5bdc080da9"
        },
        {
            "questionID": "f85e9434-1711-4e02-9f9e-7831aa5c743a",
            "alternativeID": "63c0210c-2a9a-4e93-98ec-8d9f3984e2b0"
        }
    ]
}
```
 
### Questions
#### **GET** `/questions/technology/{tech}`
- Retorna as questões de uma determinada tecnologia, por exemplo: `/questions/technology/JAVA`

### Ranking
#### **GET** `/ranking/top10`
- Retorna o ranking das 10 pessoas com a maior pontuação


## ▶️ Instalação
1. Baixe o repositório para sua máquina local.
2. Inicie o docker com o seguinte comando `docker-compose up -d`
3. Inicie o projeto spring.
4. URL base de requisição: http://localhost:8085

**Obs.: Na primeira execução e sempre que apagar as tabelas 'questions' e 'alternatives' deve ser executada a classe CreateSeed com a aplicação rodando. Isso fará com que essas tabelas sejam criadas e carregadas com os dados do arquivo create.sql**
