INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime, geslo) VALUES ('Petra', 'Kos', 'petra.kos@hotmail.com', 'petrakos', '12345');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime, geslo) VALUES ('Miha', 'Novak', 'miha.novak@gmail.com', 'mihanovak', '1998');
INSERT INTO nakupovalni_seznam(naziv, opis, ustvarjen, uporabnik_id) VALUES ('Zivila', 'Kupi sledeca zivila', '2019-11-07T15:38:43.606Z', '1');
INSERT INTO nakupovalni_seznam(naziv, opis, ustvarjen, uporabnik_id) VALUES ('Tech', 'Kupi seldece komponente', '2019-11-07T15:38:43.606Z', '2');
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Jajca', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Mleko', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Sir', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('CPU', 2)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Maticna plosca', 2)