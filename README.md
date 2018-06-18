#Sistema de Pagamento de Boletos
##Sistema que compreende:

- Realizar transações financeiras

##Tecnologias utilizadas:

- Spring Boot
- Spring-data
- Hibernate
- Jackson 2
- Embedded Jetty
- Docker

###Para os testes Unitarios
- Junit
- Spring-test
### Banco de dados
- MongoDB

##Serviços
- Os serviços estão documentados com o swagger: http://localhost:8080/rest/swagger-ui.html#
- Na aplicação, os servicos estão documentados atraves dos testes integrados

###Passo a passo:

* Criar Docker Network:
```
docker network create springbootmongo

```

* Como rodar o MongoDB no Docker:
```
mkdir -p ~/mongo-data

```

* Agora vamos iniciar o container do mongo:
```
docker run -d --name mongocontainer --network=springbootmongo -v ~/mongo-data:/data/db -d mongo

```

* Compilar o projeto
Entre na pasta ./pagamento
Execute:
```
mvn clean install dockerfile:build
```
* Execute o projeto:
```
docker run --network=springbootmongo -p 8080:8080  -t springio/pagamento

```