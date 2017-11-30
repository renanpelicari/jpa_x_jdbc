-- SEQUENCE MANAGED BY JPA
CREATE TABLE T_PERSON_JPA (
  ID NUMERIC CONSTRAINT PERSON_ID_PK PRIMARY KEY,
  NAME VARCHAR(100) NOT NULL,
  AGE NUMERIC
);

CREATE SEQUENCE SEQ_PERSON_JPA;

-- SEQUENCE AUTO INCREMENT
CREATE TABLE T_PERSON_JDBC (
  ID SERIAL PRIMARY KEY,
  NAME VARCHAR(100) NOT NULL,
  AGE NUMERIC
);
