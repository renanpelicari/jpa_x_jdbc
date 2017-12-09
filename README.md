# POC: JPA x JDBC
POC to show difference between JPA and JDBC

[![Build Status](https://travis-ci.org/renanpelicari/jpa_x_jdbc.svg?branch=master)](https://travis-ci.org/renanpelicari/jpa_x_jdbc)
[![Maintainability](https://api.codeclimate.com/v1/badges/8fa8f8f14f1c1e0c6d74/maintainability)](https://codeclimate.com/github/renanpelicari/jpa_x_jdbc/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/8fa8f8f14f1c1e0c6d74/test_coverage)](https://codeclimate.com/github/renanpelicari/jpa_x_jdbc/test_coverage)

This project use the following technologies and frameworks:
* [Spring Boot](https://projects.spring.io/spring-boot/);
* [RESTFul API](https://restfulapi.net/) (webservices to expose the endpoints);
* [Spring Data JPA](https://projects.spring.io/spring-data-jpa/) (for our JPA Repository);
* [JDBC Template](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) (Spring JDBC Pattern);
* [Lombok](https://projectlombok.org/) (more details at Lombok session);
* [Actuator](https://spring.io/guides/gs/actuator-service/) (to expose some endpoints to manage application easily);
* [Swagger](https://swagger.io/) (to expose the docs of endpoints);
* [JUnit 4](http://junit.org/junit4/) and [Mockito](http://site.mockito.org/) for unit test purposes;
* [Jersey](https://jersey.github.io/) and [JAX-RS](https://github.com/jax-rs) (turn easy RESTFul WEB Services development);
* [Flyway](https://flywaydb.org/) (DB versioning);

## Requirements
* Java SE 8
* Gradle 4.2
* Database Postgres

## Install and start Postgres at Docker
`$ docker pull postgres`

`$ docker run --name poc_jpa_x_jdbc -e POSTGRES_PASSWORD=admin1234 -d postgres`

## Adjusts DB configuration in application.yml
1. Since the DB is up, you need to check the IP used by docker:
`$ docker inspect poc_jpa_x_jdbc`

2. Change host configuration in *application.yml*;

## Preparing DB
1. Create DB: 
```SQL
CREATE DATABASE DB_POC_JPA_X_JDBC;
```
2. Create Schema:
```SQL
CREATE SCHEMA tables;
```
3. Execute gradle in order to create changelog table:
`$ gradle -i flywayMigrate`

4. At the first tme and every time you create a new versioned sql file:
`$ gradle flywayMigrate`

## Lombok
* Java Library to auto generate codes like getters and setters, toString, builder, construct with none or all arguments.
* It was choose to let the focus in difference between JPA and JDBC, and do not waste time in another things.
* However, if you want to play with project, you will need to download the plugin in your IDE, and enable the [annotation processing](https://www.jetbrains.com/help/idea/annotation-processors.html).

## Building Project
`$ gradle clean build`

## Run Application
`$ gradle bootRun`

## Usage
By default, the application will start at port 8080, and will expose some endpoints.

You can check the endpoints with the properly documentation at the address:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Also I attached the [postman](https://www.getpostman.com/) collection at resource folder.

## Author
Renan Peliçari Rodrigues

renanpelicari@gmail.com

https://www.linkedin.com/in/renanpelicari/

https://github.com/renanpelicari
