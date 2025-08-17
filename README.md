# LiterAlura 📖

> Estado: Concluído ✔️

## 📝 Descrição do Projeto
O LiterAlura é um catálogo de livros interativo que funciona através da consola (terminal). Este projeto foi desenvolvido como parte do desafio de programação da [Alura](https://www.alura.com.br/), com o objetivo de praticar o consumo de APIs e a persistência de dados numa base de dados relacional.

A aplicação consome a API gratuita [Gutendex](https://gutendex.com/) para procurar informações sobre livros e armazena-as numa base de dados PostgreSQL, permitindo que o utilizador interaja com o seu próprio catálogo literário.

## ✨ Funcionalidades
A aplicação oferece um menu interativo com as seguintes opções:

1.  **Procurar livro pelo título:** Consulta a API Gutendex, procura por um livro e guarda o resultado na base de dados local para consultas futuras.
2.  **Listar livros registados:** Exibe todos os livros que já foram guardados na base de dados.
3.  **Listar autores registados:** Exibe todos os autores dos livros guardados, juntamente com os seus dados biográficos e uma lista das suas obras registadas.
4.  **Listar autores vivos num determinado ano:** Permite ao utilizador inserir um ano e exibe uma lista de autores que estavam vivos nesse período.
5.  **Listar livros num determinado idioma:** Mostra uma lista de livros registados num idioma específico (ex: português, inglês, etc.).

## 🛠️ Tecnologias Utilizadas
- **Java 17:** Linguagem de programação principal.
- **Spring Boot:** Framework para a criação da aplicação, gestão de dependências e configurações.
- **Spring Data JPA:** Para persistência de dados e comunicação com a base de dados de forma simplificada.
- **PostgreSQL:** Base de dados relacional utilizada para armazenar os livros e autores.
- **Maven:** Ferramenta de gestão de projetos e dependências.
- **API Gutendex:** Fonte externa de dados sobre os livros.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar o LiterAlura na sua máquina.

### Pré-requisitos
Antes de começar, vai precisar de ter instalado na sua máquina as seguintes ferramentas:
- [Git](https://git-scm.com)
- [Java (JDK 17 ou superior)](https://www.oracle.com/java/technologies/downloads/)
- [PostgreSQL](https://www.postgresql.org/download/)
- Um IDE da sua preferência (ex: [IntelliJ IDEA](https://www.jetbrains.com/idea/))

### Passo a Passo
1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-utilizador/literalura.git](https://github.com/seu-utilizador/literalura.git)
   cd literalura
   ```

2. **Configure a Base de Dados:**
   - Abra o PostgreSQL (usando o pgAdmin, por exemplo).
   - Crie uma nova base de dados com o nome `literalura`.

3. **Configure a Aplicação:**
   - Abra o projeto no seu IDE.
   - Navegue até ao ficheiro `src/main/resources/application.properties`.
   - Altere as seguintes propriedades com as suas credenciais do PostgreSQL:
     ```properties
     spring.datasource.username=seu_utilizador_do_postgres
     spring.datasource.password=sua_senha_do_postgres
     ```

4. **Execute a Aplicação:**
   - Pode executar o projeto diretamente pelo seu IDE, localizando a classe `LiteraluraApplication.java` e executando o método `main`.
   - Ou, se preferir, execute através do terminal na pasta raiz do projeto:
     ```bash
     ./mvnw spring-boot:run
     ```
   A aplicação será iniciada e o menu interativo aparecerá na consola.

## 📂 Estrutura do Projeto
O código-fonte está organizado da seguinte forma para promover a separação de responsabilidades:
- `dto`: Contém os Data Transfer Objects (Records), que são moldes para os dados recebidos da API Gutendex.
- `model`: Contém as Entidades JPA (`Autor`, `Livro`), que representam as tabelas da base de dados.
- `repository`: Contém as interfaces do Spring Data JPA, responsáveis pela abstração do acesso aos dados.
- `service`: Contém as classes de serviço, responsáveis pela lógica de negócio, como o consumo da API e a conversão de dados.

## 👨‍💻 Autor
Projeto desenvolvido por **[NATALINO LEITE]**.

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/natalino-leite-dev/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/NatalinoLeite/)
