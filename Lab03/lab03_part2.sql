DROP TABLE IF EXISTS home;
DROP TABLE IF EXISTS live;
DROP TABLE IF EXISTS Place;
DROP TABLE IF EXISTS Telephone;
DROP TABLE IF EXISTS produce;
DROP TABLE IF EXISTS perform;
DROP TABLE IF EXISTS play;
DROP TABLE IF EXISTS Musician;
DROP TABLE IF EXISTS appear;
DROP TABLE IF EXISTS Album;
DROP TABLE IF EXISTS Song;
DROP TABLE IF EXISTS Instrument;

CREATE TABLE Musician (ssn CHAR(11),
			name CHAR(20),
			PRIMARY KEY (ssn));

CREATE TABLE Place (address CHAR(20),
		PRIMARY KEY (address));

CREATE TABLE Telephone (phone_no CHAR(20),
			PRIMARY KEY (phone_no));

CREATE TABLE home (address CHAR(20) NOT NULL,
		phone_no CHAR(20),
		PRIMARY KEY (phone_no),
		FOREIGN KEY (address) REFERENCES Place,
		FOREIGN KEY (phone_no) REFERENCES Telephone);

CREATE TABLE live (ssn CHAR(11),
		phone_no CHAR(20),
		PRIMARY KEY (ssn, phone_no),
		FOREIGN KEY (ssn) REFERENCES Musician,
		FOREIGN KEY (phone_no) REFERENCES Telephone);

CREATE TABLE Album (albumIdentifier INTEGER,
		copyrightDate CHAR(10),
		speed CHAR(20),
		title CHAR(20),
		PRIMARY KEY (albumIdentifier));

CREATE TABLE produce (ssn CHAR(11) NOT NULL,
		albumIdentifier INTEGER,
		PRIMARY KEY (albumIdentifier),
		FOREIGN KEY (ssn) REFERENCES Musician,
		FOREIGN KEY (albumIdentifier) REFERENCES Album);

CREATE TABLE Song (songId INTEGER,
		title CHAR(20),
		author CHAR(20),
		PRIMARY KEY (songId));

CREATE TABLE appear (albumIdentifier INTEGER NOT NULL,
		songId INTEGER,
		PRIMARY KEY (songId),
		FOREIGN KEY (albumIdentifier) REFERENCES Album,
		FOREIGN KEY (songId) REFERENCES Song);

CREATE TABLE perform (ssn CHAR(11),
		songId INTEGER,
		PRIMARY KEY (ssn, songId),
		FOREIGN KEY (ssn) REFERENCES Musician,
		FOREIGN KEY (songId) REFERENCES Song);

CREATE TABLE Instrument (instrId INTEGER,
			dname CHAR(20),
			key CHAR(20),
			PRIMARY KEY (instrId));

CREATE TABLE play (ssn CHAR(11),
		instrId INTEGER,
		PRIMARY KEY (ssn, instrId),
		FOREIGN KEY (ssn) REFERENCES Musician,
		FOREIGN KEY (instrId) REFERENCES Instrument);
