# Sistema de Gestão de Estoque e Vendas

API REST desenvolvida com **Java 21** e **Spring Boot** para gerenciamento de estoque e vendas. O projeto foi criado com foco em boas práticas de desenvolvimento back-end, servindo como projeto de portfólio para demonstrar conhecimentos em APIs REST, autenticação, banco de dados relacional e arquitetura em camadas.

## Objetivos

* Desenvolver uma API REST seguindo boas práticas.
* Aplicar conceitos de orientação a objetos.
* Utilizar autenticação e autorização com JWT.
* Trabalhar com persistência de dados utilizando PostgreSQL.
* Demonstrar conhecimentos em Docker, Git e testes automatizados.

## Tecnologias

* Java 21
* Spring Boot
* Spring Security
* JWT
* Spring Data JPA (Hibernate)
* PostgreSQL
* Docker & Docker Compose
* Maven
* Swagger / OpenAPI
* JUnit

## Funcionalidades

### Usuários

* Cadastro de usuários
* Login com autenticação JWT
* Controle de permissões por perfil (Administrador e Funcionário)

### Produtos

* Cadastro de produtos
* Consulta de produtos
* Atualização de produtos
* Exclusão de produtos
* Controle de estoque

### Clientes

* Cadastro de clientes
* Consulta de clientes
* Atualização de clientes
* Exclusão de clientes

### Vendas

* Registro de vendas
* Atualização automática do estoque
* Histórico de vendas
* Consulta dos produtos mais vendidos

## Estrutura do Projeto

```
src
 ├── config
 ├── controller
 ├── dto
 ├── entity
 ├── enums
 ├── mapper
 ├── exception
 ├── repository
 ├── security
 ├── service
 └── util

```

## Regras de Negócio

* Não é permitido cadastrar produtos com preço menor ou igual a zero.
* O estoque nunca pode ficar negativo.
* Produtos excluídos não podem participar de novas vendas.
* Apenas usuários autenticados podem acessar os endpoints protegidos.

## Como executar

### Pré-requisitos

* Java 21
* Maven
* PostgreSQL

### Clonar o projeto

```bash
git clone https://github.com/fernandos01/estoque-api.git
```

```bash
cd estoque-api
```

### Executar

```bash
./mvnw spring-boot:run
```

Ou, no Windows:

```cmd
mvnw.cmd spring-boot:run
```

## Documentação da API

Após iniciar a aplicação, a documentação estará disponível em:

```
http://localhost:8080/swagger-ui/index.html
```

## Status do Projeto

🚧 Em desenvolvimento.

## Autor

Fernando

Estudante de Ciência da Computação e desenvolvedor back-end em formação.

Projeto desenvolvido para fins de estudo e composição de portfólio.
