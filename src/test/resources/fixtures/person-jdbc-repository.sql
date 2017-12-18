-- since the sequence is configured native (directly in DB, not in the Model), its necessary to create when H2 starts
CREATE SEQUENCE T_PERSON_JDBC_ID_SEQ;

-- add fixture data
INSERT INTO T_PERSON_JDBC (ID, NAME, AGE) VALUES (1, 'Mocked Person 1', 21);
INSERT INTO T_PERSON_JDBC (ID, NAME, AGE) VALUES (2, 'Mocked Person 2', 22);
INSERT INTO T_PERSON_JDBC (ID, NAME, AGE) VALUES (3, 'Mocked Person 3', 23);
INSERT INTO T_PERSON_JDBC (ID, NAME, AGE) VALUES (4, 'Mocked Person 4', 24);
INSERT INTO T_PERSON_JDBC (ID, NAME, AGE) VALUES (5, 'Mocked Person 5', 25);

-- UPDATE SEQUENCE DUE MANUAL INSERTS
ALTER SEQUENCE T_PERSON_JDBC_ID_SEQ RESTART WITH 6;

