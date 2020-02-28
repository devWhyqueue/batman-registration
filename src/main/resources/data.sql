DELETE FROM Registration;
DELETE FROM Tournament_Discipline;
DELETE FROM Discipline;
DELETE FROM Tournament;
DELETE FROM Division;
DELETE FROM Player;

INSERT INTO Tournament (id, name, start, end, close_of_entries) VALUES (1, 'Turnier 1', '2021-02-01', '2021-02-03', '2021-01-01');
INSERT INTO Tournament (id, name, start, end, close_of_entries) VALUES (2, 'Turnier 2', '2021-03-01', '2021-03-03', '2021-02-01');

INSERT INTO Division (name) VALUES ('A');

INSERT INTO Discipline (id, discipline_type, field_type, division_name) VALUES (1, 0, 0, 'A');

INSERT INTO Tournament_Discipline (id, capacity, registration_fee, tournament_id, discipline_id) VALUES (1, 8, 10.5, 2, 1);

INSERT INTO Player (id, first_name, last_name, gender, club) VALUES (1, 'Max', 'Mustermann', 0, 'SV Musterstadt');

INSERT INTO Registration (id, status, registration_date, player_id, partner_id, tournament_discipline_id) VALUES (1, 0, '2020-01-01', 1, null, 1);


