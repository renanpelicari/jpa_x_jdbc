# POC: JPA x JDBC
POC to show difference between JPA and JDBC

[![Build Status](https://travis-ci.org/renanpelicari/jpa_x_jdbc.svg?branch=master)](https://travis-ci.org/renanpelicari/jpa_x_jdbc)
[![Coverage Status](https://coveralls.io/repos/github/renanpelicari/jpa_x_jdbc/badge.svg?branch=master)](https://coveralls.io/github/renanpelicari/jpa_x_jdbc?branch=master)
[![Maintainability](https://api.codeclimate.com/v1/badges/8fa8f8f14f1c1e0c6d74/maintainability)](https://codeclimate.com/github/renanpelicari/jpa_x_jdbc/maintainability)

This project use the following technologies and frameworks:
* [Gradlew](https://docs.gradle.org/current/userguide/gradle_wrapper.html) (build automation - wrapper version);
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
* [Jacoco](http://www.eclemma.org/jacoco/) (free code coverage library for Java);

## Requirements
* Java SE 8
* Database Postgres

## About gradlew
This project contains gradlew 4.2.

So you don't need to install or config gradle, you can use this embedded version.

To execute you just need to be at project initial folder and run the command: `./gradlew <task>`

## [Optional] Install and start Postgres at Docker
Since this project is only for study purpose, I recommend to use Postgres under the [docker](https://www.docker.com/) instead native installation.

Donwload the Postgres for docker:

`$ docker pull postgres`

Config postgres for project:

`$ docker run --name poc_jpa_x_jdbc -e POSTGRES_PASSWORD=admin1234 -d postgres`

PS: the parameters are optional, if you want to change, you should change at application.yml and flyway task at gradle builder also.

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
`$ ./gradlew -i flywayMigrate`

4. At the first tme and every time you create a new versioned sql file:
`$ ./gradlew flywayMigrate`

## Lombok
* Java Library to auto generate codes like getters and setters, toString, builder, construct with none or all arguments.
* It was choose to let the focus in difference between JPA and JDBC, and do not waste time in another things.
* However, if you want to play with project, you will need to download the plugin in your IDE, and enable the [annotation processing](https://www.jetbrains.com/help/idea/annotation-processors.html).

## Building Project
`$ ./gradlew clean build`

## Run Application
`$ ./gradlew bootRun`

## Usage
By default, the application will start at port 8080, and will expose some endpoints.

You can check the endpoints with the properly documentation at the address:
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Also I attached the [postman](https://www.getpostman.com/) collection at resource folder.

## Test and coverage
`./gradlew jacocoTestReport`

The result you can check at: `build/reports/jacoco/test/html/index.html`

  

## Author
Renan Peliçari Rodrigues

renanpelicari@gmail.com

https://www.linkedin.com/in/renanpelicari/

https://github.com/renanpelicari
