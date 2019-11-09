INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime, geslo) VALUES ('Petra', 'Kos', 'petra.kos@hotmail.com', 'petrakos', '12345');
INSERT INTO uporabnik (ime, priimek, email, uporabnisko_ime, geslo) VALUES ('Miha', 'Novak', 'miha.novak@gmail.com', 'mihanovak', '1998');
INSERT INTO nakupovalni_seznam(naziv, opis, ustvarjen, uporabnik_id) VALUES ('Zivila', 'Kupi sledeca zivila', E'\\xACED00057372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770D02000000005DC6DC02246B1EC078', '1');
INSERT INTO nakupovalni_seznam(naziv, opis, ustvarjen, uporabnik_id) VALUES ('Tech', 'Kupi seldece komponente', E'\\xACED00057372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770D02000000005DC6DC02246B1EC078', '2');
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Jajca', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Mleko', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Sir', 1)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('CPU', 2)
INSERT INTO artikel(ime_artikla, nakupovalni_seznam_id) VALUES ('Maticna plosca', 2)
