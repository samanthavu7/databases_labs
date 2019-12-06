DROP TABLE IF EXISTS Booking;
DROP TABLE IF EXISTS Customer;
DROP TABLE IF EXISTS Repair;
DROP TABLE IF EXISTS Requests;
DROP TABLE IF EXISTS Maintenance_Company;
DROP TABLE IF EXISTS Receptionist;
DROP TABLE IF EXISTS Assigned;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS House_Cleaning;
DROP TABLE IF EXISTS Manager CASCADE;
DROP TABLE IF EXISTS Staff CASCADE;
DROP TABLE IF EXISTS Hotel CASCADE;

-- Cannot enforce: Hotel must have at least one room
CREATE TABLE Hotel (hotel_id INTEGER NOT NULL,
                address CHAR(100),
                PRIMARY KEY (hotel_id));

CREATE TABLE Room (room_no INTEGER NOT NULL,
                room_type CHAR(20),
                hotel_id INTEGER NOT NULL,
                PRIMARY KEY (hotel_id, room_no),
                FOREIGN KEY (hotel_id) REFERENCES Hotel (hotel_id));

CREATE TABLE Staff (ssn CHAR(11) NOT NULL,
		fname CHAR(20),
		lname CHAR(20),
		address CHAR(20),
		hotel_id INTEGER NOT NULL,
		PRIMARY KEY (ssn),
		FOREIGN KEY (hotel_id) REFERENCES Hotel (hotel_id));

CREATE TABLE Receptionist (ssn CHAR(11) NOT NULL,
			PRIMARY KEY (ssn),
			FOREIGN KEY (ssn) REFERENCES Staff (ssn));

CREATE TABLE House_Cleaning (ssn CHAR(11) NOT NULL,
			PRIMARY KEY (ssn),
			FOREIGN KEY (ssn) REFERENCES Staff (ssn));

CREATE TABLE Assigned (ssn CHAR(11) NOT NULL,
                hotel_id INTEGER NOT NULL,
		room_no INTEGER NOT NULL,
                PRIMARY KEY (ssn, hotel_id, room_no),
                FOREIGN KEY (ssn) REFERENCES House_Cleaning (ssn),
                FOREIGN KEY (hotel_id, room_no) REFERENCES Room (hotel_id, room_no));

-- Cannot enforce: Manager must manage at least one hotel
CREATE TABLE Manager (ssn CHAR(11) NOT NULL,
		PRIMARY KEY (ssn),
		FOREIGN KEY (ssn) REFERENCES Staff (ssn));

ALTER TABLE Hotel
ADD ssn CHAR(11) NOT NULL  REFERENCES Manager (ssn);

CREATE TABLE Maintenance_Company (cmp_id INTEGER NOT NULL,
				name CHAR(50),
				address CHAR(100),
				certified CHAR(100),
				PRIMARY KEY (cmp_id));

CREATE TABLE Requests (ssn CHAR(11) NOT NULL,
		cmp_id INTEGER NOT NULL,
		request_date CHAR(10),
		description CHAR(100),
		PRIMARY KEY (ssn, cmp_id),
		FOREIGN KEY (ssn) REFERENCES Manager (ssn),
		FOREIGN KEY (cmp_id) REFERENCES Maintenance_Company (cmp_id));

CREATE TABLE Repair (hotel_id INTEGER NOT NULL,
		room_no INTEGER NOT NULL,
		cmp_id INTEGER NOT NULL,
		repair_date CHAR(10),
		description CHAR(100),
		repair_type CHAR(20),
		PRIMARY KEY (hotel_id, room_no, cmp_id),
		FOREIGN KEY (hotel_id, room_no) REFERENCES Room (hotel_id, room_no),
		FOREIGN KEY (cmp_id) REFERENCES Maintenance_Company (cmp_id));

CREATE TABLE Customer (customer_id INTEGER NOT NULL,
                fname CHAR(20),
                lname CHAR(20),
                address CHAR(100),
                phno CHAR(12),
                dob CHAR(10),
                gender CHAR(20),
                PRIMARY KEY (customer_id));

CREATE TABLE Booking (customer_id INTEGER NOT NULL,
                hotel_id INTEGER NOT NULL,
		room_no INTEGER NOT NULL,
                book_date CHAR(10),
                num_people INTEGER,
                price INTEGER,
                PRIMARY KEY (customer_id, hotel_id, room_no),
                FOREIGN KEY (customer_id) REFERENCES Customer (customer_id),
                FOREIGN KEY (hotel_id, room_no) REFERENCES Room (hotel_id, room_no));

