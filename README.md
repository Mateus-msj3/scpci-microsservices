# ScpciWeb

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/Mateus-msj3/license-mit)

# Sobre o projeto

O SCPCI-MICROSSERVICE [Sistema de Cadastro de Pessoas, Cursos e Inscrições] é o backend do SCPCI-WEB e contém os microsservices de Pessoa, Curso e Inscrções, Eureka Server e o Gateway.

### Funcionalidades

- API com um CRUD de Pessoas
- API para cadastro de Cursos
- API de cadastro e finalização de Inscrições.
- Eureka Server
- Cloud Gateway

# Tecnologias utilizadas

## Backend
- Java 17
- Spring
- JPA / Hibernate
- MySQL
- Eureka Server
- OpenFeign

## Integrações
- RabbitMQ

# Conceitos utilzados
- Conexão de microsservices no Eureka Server
- Gerenciamento de escala entre os microsservices com o Gateway
- Comunicação entre diferentes microsservices com o OpenFeign
- Configuração de CORS
- Bean Validation
- ModelMapper para conversão Entidade/DTO
- Testes unitários

# Como executar o projeto

## Backend
Pré-requisitos: Java 17 / Maven

```bash
# clonar repositório
git clone https://github.com/Mateus-msj3/scpci-web.git

# entrar na pasta do projeto front end web
cd scpci-microsservice

# instalar dependências
mvn clean install

# executar o projeto
./mvnw spring-boot:run
```

# Autor

Mateus Souza de Jesus

https://www.linkedin.com/in/mateus-souza-de-jesus/
