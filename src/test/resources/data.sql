INSERT INTO people(id, name) VALUES (-1, 'jan');
INSERT INTO people(id, name) VALUES (-2, 'klaas');

INSERT INTO nicks(id, personId, nick) VALUES(-1, -2, 'klaassie');

INSERT INTO provinces(id, personId, name, island, kingdom, race, personality) VALUES (-2, -1, 'prov', 15, 2, 2, 3);

INSERT INTO attacks(id, person, personId, returnDate) VALUES(-1, 'jan', -1, '2050-01-20');