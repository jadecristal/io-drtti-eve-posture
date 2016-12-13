USE mysql;

DROP DATABASE IF EXISTS drtti_eve;
CREATE DATABASE drtti_eve;

GRANT ALL ON drtti_eve TO 'drtti_eve'@'localhost'
IDENTIFIED BY 'NOT_THE_REAL_PASSWORD';

FLUSH PRIVILEGES;

USE drtti_eve;

DROP TABLE IF EXISTS Pilot;
CREATE TABLE Pilot (
  ID            BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  CharacterID   BIGINT        NOT NULL,
  CharacterName NVARCHAR(255) NOT NULL,
  UNIQUE(CharacterID)
);

INSERT INTO Pilot (CharacterID, CharacterName) VALUES (96363755, 'Cale Cloudstrike');

DROP TABLE IF EXISTS SolarSystem;
CREATE TABLE SolarSystem (
  ID              BIGINT        NOT NULL AUTO_INCREMENT PRIMARY KEY,
  SolarSystemID   BIGINT        NOT NULL,
  SolarSystemName NVARCHAR(255) NOT NULL,
  UNIQUE(SolarSystemID)
);

INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30000142, 'Jita');
INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30003769, 'R3-K7K');
# INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30003770, 'X-R3NM');
# INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30003772, 'G-B22J');
# INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30003773, 'X6AB-Y');
# INSERT INTO SolarSystem (SolarSystemID, SolarSystemName) VALUES (30003771, '8B-VLX');
