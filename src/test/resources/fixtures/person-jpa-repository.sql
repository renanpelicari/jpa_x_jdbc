INSERT INTO T_PERSON_JPA (ID, NAME, AGE) VALUES (1, 'Mocked Person 1', 21);
INSERT INTO T_PERSON_JPA (ID, NAME, AGE) VALUES (2, 'Mocked Person 2', 22);
INSERT INTO T_PERSON_JPA (ID, NAME, AGE) VALUES (3, 'Mocked Person 3', 23);
INSERT INTO T_PERSON_JPA (ID, NAME, AGE) VALUES (4, 'Mocked Person 4', 24);
INSERT INTO T_PERSON_JPA (ID, NAME, AGE) VALUES (5, 'Mocked Person 5', 25);

-- UPDATE SEQUENCE DUE MANUAL INSERTS
ALTER SEQUENCE SEQ_PERSON_JPA RESTART WITH 6;

