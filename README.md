# POC: JPA x JDBC
POC to show difference between JPA and JDBC

[![Build Status](https://travis-ci.org/renanpelicari/jpa_x_jdbc.svg?branch=master)](https://travis-ci.org/renanpelicari/jpa_x_jdbc)

This project use the following technologies and frameworks:
* Spring Boot;
* RESTFul Web Services (to expose the endpoints);
* Spring Data JPA (for our JPA Repository);
* JDBC Template (pattern for JDBC);
* Lombok;
* Actuator;
* Swagger (to expose the docs of endpoints);
* JUnit and Mockito for unit test purposes;
* Jersey and JAX-RS (handler for media type used at endpoints);
* Flyway (DB versioning);

# Requirements
* Java SE 8
* Gradle 4.2
* Database Postgres

# Install and start Postgres at Docker
docker pull postgres
docker run --name poc_jpa_x_jdbc -e POSTGRES_PASSWORD=admin1234 -d postgres

# Adjusts DB configuration in application.yml
1. Since the DB is up, you need to check the IP used by docker:
* docker inspect poc_jpa_x_jdbc

2. Change host configuration in application.yml;

# Preparing DB
1. Create DB: 
* CREATE DATABASE DB_POC_JPA_X_JDBC;

2. Create Schema:
* CREATE SCHEMA tables;

3. Execute gradle in order to create changelog table:
* gradle -i flywayMigrate

4. At the first tme and every time you create a new versioned sql file:
* gradle flywayMigrate

# Lombok
* Java Library to auto generate codes like getters and setters, toString, builder, construct with none or all arguments.
* It was choose to let the focus in difference between JPA and JDBC, and do not waste time in another things.
* However, if you want to play with project, you will need to download the plugin in your IDE, and enable the annotation processing.

# Building Project
gradle clean build

# Run Application
gradle bootRun

# Usage
Application will start on port 8080, and will expose some endpoints.

You can check the endpoints with the properly documentation at the address:
http://localhost:8080/swagger-ui.html

Also I attached the postman collection at resource folder.

# Author
Renan Peliçari Rodrigues

renanpelicari@gmail.com

https://www.linkedin.com/in/renanpelicari/

https://github.com/renanpelicari
