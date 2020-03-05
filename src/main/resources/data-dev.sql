DELETE FROM Registration WHERE true;
DELETE FROM Tournament_Discipline WHERE true;
DELETE FROM Discipline WHERE true;
DELETE FROM Tournament WHERE true;
DELETE FROM Division WHERE true;
DELETE FROM Player WHERE true;
DELETE FROM Batman_User WHERE true;

INSERT INTO Tournament (id, name, start, end, close_of_entries) VALUES (1, 'Turnier 1', '2020-02-01', '2020-02-03', '2020-01-01');
INSERT INTO Tournament (id, name, start, end, close_of_entries) VALUES (2, 'Stauseepokal 2020', '2021-06-20', '2021-06-21', '2021-06-14');

INSERT INTO Division (name) VALUES ('A');
INSERT INTO Division (name) VALUES ('B');

INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (1, 0, 0, 'A');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (2, 1, 0, 'A');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (3, 2, 2, 'A');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (4, 0, 1, 'A');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (5, 1, 1, 'A');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (6, 0, 0, 'B');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (7, 1, 0, 'B');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (8, 2, 2, 'B');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (9, 0, 1, 'B');
INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (10, 1, 1, 'B');

INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (1, 8, 5, 2, 1);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (2, 8, 10.5, 2, 2);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (3, 8, 10.5, 2, 3);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (4, 8, 5, 2, 4);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (5, 8, 10.5, 2, 5);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (6, 8, 5, 2, 6);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (7, 8, 10.5, 2, 7);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (8, 8, 10.5, 2, 8);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (9, 8, 5, 2, 9);
INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (10, 8, 10.5, 2, 10);

INSERT INTO Batman_User (id) VALUES (1);
INSERT INTO Batman_User (id) VALUES (2);
INSERT INTO Batman_User (id) VALUES (3);
INSERT INTO Batman_User (id) VALUES (4);
INSERT INTO Batman_User (id) VALUES (9);
INSERT INTO Batman_User (id) VALUES (10);

INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (5, 'Jana', 'Meier', 1, 'SV Meiershoven', 1);
INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (6, 'Janine', 'Meier', 1, 'SV Meiershoven', 4);
INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (7, 'Jonas', 'Meier', 0, 'SV Meiershoven', 1);
INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (8, 'Justus', 'Meier', 0, 'SV Meiershoven', 2);
INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (11, 'Markus', 'Müller', 0, 'SV Müllersbach', 9);
INSERT INTO Player (id, first_name, last_name, gender, club, created_by_user_id) VALUES (12, 'Magdalena', 'Müller', 1, 'SV Müllersbach', 10);

INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (1, 0, '2020-03-01', 1, null, 1);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (2, 1, '2020-03-01', 2, null, 1);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (3, 1, '2020-03-01', 3, null, 4);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (4, 1, '2020-03-01', 4, null, 4);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (5, 1, '2020-03-02', 2, 8, 2);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (7, 1, '2020-03-01', 4, 6, 5);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (8, 2, '2020-03-01', 1, 7, 2);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (9, 3, '2020-03-01', 1, 5, 3);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (11, 1, '2020-03-01', 9, null, 6);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (12, 1, '2020-03-01', 10, null, 9);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (13, 1, '2020-03-01', 9, 11, 7);
INSERT INTO Registration (id, state, registration_date, user_id, partner_id, tournament_discipline_id) VALUES (14, 1, '2020-03-01', 10, 12, 10);


