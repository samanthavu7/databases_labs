DROP TABLE IF EXISTS supervise;
DROP TABLE IF EXISTS work_proj;
DROP TABLE IF EXISTS work_in;
DROP TABLE IF EXISTS manage;
DROP TABLE IF EXISTS runs;
DROP TABLE IF EXISTS work_dept;
DROP TABLE IF EXISTS Professor;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS advise;
DROP TABLE IF EXISTS major;
DROP TABLE IF EXISTS Graduate;
DROP TABLE IF EXISTS Dept;

CREATE TABLE Professor (pssn CHAR(11),
			name CHAR(20),
			age INTEGER,
			rank CHAR(20),
			speciality CHAR(20),
			PRIMARY KEY (pssn));

CREATE TABLE Project (pno INTEGER,
		sponsor CHAR(20),
		start_date CHAR(10),
		end_date CHAR(10),
		budget INTEGER,
		PRIMARY KEY (pno));

CREATE TABLE work_in (pssn CHAR(11) NOT NULL,
		pno INTEGER,
		PRIMARY KEY (pssn, pno),
		FOREIGN KEY (pssn) REFERENCES Professor,
		FOREIGN KEY (pno) REFERENCES Project);

CREATE TABLE manage (pssn CHAR(11) NOT NULL,
		pno INTEGER,
		PRIMARY KEY (pno),
		FOREIGN KEY (pssn) REFERENCES Professor,
		FOREIGN KEY (pno) REFERENCES Project);

CREATE TABLE Graduate (gssn CHAR(11),
		name CHAR(20),
		age INTEGER,
		deg_pg CHAR(20),
		major CHAR(20),
		PRIMARY KEY (gssn));

CREATE TABLE advise (senior_SSN CHAR(11) NOT NULL,
		grad_SSN CHAR(11),
		PRIMARY KEY (grad_SSN),
		FOREIGN KEY (senior_SSN) REFERENCES Graduate,
		FOREIGN KEY (grad_SSN) REFERENCES Graduate);

CREATE TABLE work_proj (pno INTEGER,
			since CHAR(10),
			gssn CHAR(11),
			PRIMARY KEY (pno, gssn),
			FOREIGN KEY (pno) REFERENCES Project,
			FOREIGN KEY (gssn) REFERENCES Graduate);

CREATE TABLE supervise (pssn CHAR(11) NOT NULL,
			pno INTEGER,
			gssn CHAR(11),
			PRIMARY KEY (pno, gssn),
			FOREIGN KEY (pssn) REFERENCES Professor,
			FOREIGN KEY (pno) REFERENCES Project,
			FOREIGN KEY (gssn) REFERENCES Graduate);

CREATE TABLE Dept (dno INTEGER,
		dname CHAR(20),
		office CHAR(20),
		PRIMARY KEY (dno));

CREATE TABLE major (dno INTEGER NOT NULL,
		gssn CHAR(11),
		PRIMARY KEY (gssn),
		FOREIGN KEY (dno) REFERENCES Dept,
		FOREIGN KEY (gssn) REFERENCES Graduate);

CREATE TABLE runs (pssn CHAR(11),
		dno INTEGER,
		PRIMARY KEY (dno),
		FOREIGN KEY (pssn) REFERENCES Professor,
		FOREIGN KEY (dno) REFERENCES Dept);

CREATE TABLE work_dept (pssn CHAR(11),
			time_pc CHAR(20),
			dno INTEGER,
			PRIMARY KEY (pssn, dno),
			FOREIGN KEY (pssn) REFERENCES Professor,
			FOREIGN KEY (dno) REFERENCES Dept);
