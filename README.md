## Frameworks e Ferramentas

* Java 11
* Spring Boot
* Spring Data JPA
* Lombok
* Swagger
* Maven
* JUnit
* Docker

## Arquitetura do projeto

A aplicação foi desenvolvida consumindo dois serviços que foram disponibilizados através do Docker. Uma é um serviço de banco de dados MySql e o outro uma API que deve ser consumida
pela aplicação desenvolvida.


REST - Esta aplicação disponibiliza um serviço REST para disponibilizar recursos de uma API registro de cotação de ações. Consome e responde no formato JSON.

## Documentação

Pelo [Swagger](http://localhost:8081/swagger-ui/index.html) você verá a documentação dos serviços da API, como também todos endpoints e modelos utilizados pela aplicação.
