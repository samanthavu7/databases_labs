/*
 * Template JAVA User Interface
 * =============================
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 *
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   public String executeQuery0 (String query) throws SQLException {
      Statement stmt = this._connection.createStatement ();
      ResultSet rs = stmt.executeQuery (query);
      ResultSetMetaData rsmd = rs.getMetaData();
      int numCol = rsmd.getColumnCount();
      int rowCount = 0;
      boolean outputHeader = true;
      rs.next();
      return rs.getString(1);
   }

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
	  // Given customer details add the customer in the DB 
      // Your code goes here.
      // ...
      // ...
      try{
         String query = "INSERT INTO Customer(customerID, fName, lName, Address, phNo, DOB, gender) VALUES (";
    	   String query0 = "SELECT MAX(c.customerID) FROM Customer c";
         String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;
	 query += String.valueOf(x_number) + ", ";              
         System.out.print("\tEnter Customer's first name: ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Customer's last name: ");
         query += "'" + in.readLine() + "', ";
	 System.out.print("\tEnter Customer's address: ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Customer's phone number (format xxxxxxxxxx): ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Customer's date of birth (format xxxx-xx-xx): ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Customer's gender (Male/Female/Other): ");
         query += "'" + in.readLine() + "')";         
	 int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end addCustomer

   public static void addRoom(DBProject esql){
	  // Given room details add the room in the DB
      // Your code goes here.
      // ...
      // ...
      try{
         String query = "INSERT INTO Room(hotelID, roomNo, roomType) VALUES (";
         System.out.print("\tEnter Room's hotel ID: ");
         String hotelID = in.readLine();
	 query += hotelID + ", ";         
	 String query0 = "SELECT MAX(r.roomNo) FROM Room r WHERE r.hotelID = " + hotelID;
         String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
	 x_number = x_number + 1;
         query += String.valueOf(x_number) + ", ";	 
         System.out.print("\tEnter Room's room type: ");
         query += "'" + in.readLine() + "')";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());  
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      // ...
      // ...
      try{
         String query = "INSERT INTO MaintenanceCompany(cmpID, name, address, isCertified) VALUES (";
         String query0 = "SELECT MAX(m.cmpID) FROM MaintenanceCompany m";
         String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;
         query += String.valueOf(x_number) + ", ";
         System.out.print("\tEnter Company's name: ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Company's address: ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tIs Company certified? (TRUE/FALSE): ");
         query += in.readLine() + ")";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }  
   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  // Given repair details add repair in the DB
      // Your code goes here.
      // ...
      // ...
      try{
         String query = "INSERT INTO Repair(rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (";
         String query0 = "SELECT MAX(r.rID) FROM Repair r";
         String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;
	 query += String.valueOf(x_number) + ", ";
	 System.out.print("\tEnter Repair's hotel ID: ");
         query += in.readLine() +  ", ";
         System.out.print("\tEnter Repair's room number: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Repair's maintenance company ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Repair's date (xxxx-xx-xx): ");
         query += "'" + in.readLine() + "', ";
	 System.out.print("\tEnter Repair's description: ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Repair's type: ");
         query += "'" + in.readLine() + "')";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
      // Your code goes here.
      // ...
      // ...
      try{        
	 String query = "INSERT INTO Booking(bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price) VALUES ("; 
	 String query0 = "SELECT MAX(b.bID) FROM Booking b";
	 String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;         
	 query += String.valueOf(x_number) + ", ";
         System.out.print("\tEnter Booking customer's ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Booking's hotel ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Booking room number: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Booking date (xxxx-xx-xx): ");
         query += "'" + in.readLine() +  "', ";
         System.out.print("\tEnter Booking number of people: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Booking price: ");
         query += in.readLine() + ")";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  // Given Staff SSN, HotelID, roomNo Assign the staff to the room 
      // Your code goes here.
      // ...
      try{
         String query = "INSERT INTO Assigned(asgID, staffID, hotelID, roomNo) VALUES (";
         String query0 = "SELECT MAX(a.asgID) FROM Assigned a";
	 String x_string = esql.executeQuery0(query0);
	 int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;
         query += String.valueOf(x_number) + ", ";
         System.out.print("\tEnter Assignment's staff ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Assignment's hotel ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Assignment's room number: ");
         query += in.readLine() + ")";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...
   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      // Your code goes here.
      // ...
      try{
         String query = "INSERT INTO Request(reqID, managerID, repairID, requestDate, description) VALUES (";
	 String query0 = "SELECT MAX(r.reqID) FROM Request r";
         String x_string = esql.executeQuery0(query0);
         int x_number = Integer.parseInt(x_string);
         x_number = x_number + 1;
         query += String.valueOf(x_number) + ", ";
	 System.out.print("\tEnter Request's manager ID: ");
         query += in.readLine() +  ", ";
         System.out.print("\tEnter Request's repair ID: ");
         query += in.readLine() + ", ";
         System.out.print("\tEnter Request's request date (xxxx-xx-xx): ");
         query += "'" + in.readLine() + "', ";
         System.out.print("\tEnter Request's description: ");
         query += "'" + in.readLine() + "')";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
      // Your code goes here.
      // ...
      try{
         System.out.print("\tEnter Hotel ID: ");
         String hotelID = in.readLine();
	 String query = "SELECT COUNT(r.roomNo) FROM Room r WHERE r.hotelID = " + hotelID + " AND r.roomNo IN (SELECT r.roomNo FROM Room r WHERE r.hotelID = " + hotelID + "EXCEPT SELECT b.roomNo FROM Booking b WHERE b.hotelID = " + hotelID + ")";
         int rowCount = esql.executeQuery(query);
         System.out.println ("total row(s): " + rowCount);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...
   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){//START HERE
	  // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      // ...
      // ...
      //TODO: Remove Hotel H and h.hotelID and replace with asked value 
      try{
         
         String hotelID;
         boolean valid = false;
         do{
            System.out.print("\tEnter Hotel ID: ");
            hotelID = in.readLine();
               try {
                  int x = Integer.parseInt(hotelID);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("HotelID should be an int"); 
               }
            }while(!valid);
         String query = "SELECT COUNT(DISTINCT R.roomNo) FROM Room R, Booking B WHERE B.hotelID = ";
         query += hotelID;
         query += " AND R.roomNo = B.roomNo AND R.hotelID = ";
         query += hotelID;
         query +=";";         
         int rowCount = esql.executeQuery(query);
         System.out.println ("Room(s) have been booked");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
	  // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      // Your code goes here.
      // ...
      // ...
   try{
      
         String hotelID;
         boolean valid = false;
         do{
            System.out.print("\tEnter Hotel ID: ");
            hotelID = in.readLine();
               try {
                  int x = Integer.parseInt(hotelID);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("HotelID should be an int"); 
               }
            }while(!valid);
            valid = false;
            String date;
            do{
            System.out.print("\tEnter date as \"yyyy-mm-dd\": ");
            date = in.readLine();
               if(date.matches("\\d{4}-\\d{2}-\\d{2}")){
                  valid = true;
               }
               else{
                  System.out.println("Enter the date correctly.");
               }
            }while(!valid);
         String query = "SELECT b.roomNo FROM Booking b WHERE b.hotelID = H.hotelID AND (b.bookingDate < date 'REPLACEDATE' + integer '7' AND b.bookingDate >= 'REPLACEDATE');";
         query = query.replace("H.hotelID", hotelID);
         query = query.replace("REPLACEDATE", date);
         int rowCount = esql.executeQuery(query);
         System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
	  // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
      try{      
         String kRooms;
         boolean valid = false;
         do{
            System.out.print("\tHow many rooms?: ");
            kRooms = in.readLine();
               try {
                  int x = Integer.parseInt(kRooms);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("The amount of rooms should be an int"); 
               }
            }while(!valid);
            valid = false;
            String bDate;
            String eDate;
            System.out.println("Make sure beginning date is before ending date");
            do{
            System.out.print("\tEnter beginning date as \"yyyy-mm-dd\": ");
            bDate = in.readLine();
               if(bDate.matches("\\d{4}-\\d{2}-\\d{2}")){
                  valid = true;
                  System.out.println("Beginning date is " + bDate);
               }
               else{
                  System.out.println("Enter the date correctly.");
               }
            }while(!valid);
            valid = false;
            do{
            System.out.print("\tEnter ending date as \"yyyy-mm-dd\": ");
            eDate = in.readLine();
               if(eDate.matches("\\d{4}-\\d{2}-\\d{2}")){
                  valid = true;
                  System.out.println("Ending date is " + eDate);
               }
               else{
                  System.out.println("Enter the date correctly.");
               }
            }while(!valid);
         String query = "SELECT B.roomNo, B.price FROM (SELECT * FROM BOOKING B WHERE B.bookingDate >='bDate' AND B.bookingDate <= 'eDate') AS B ORDER BY B.price DESC LIMIT kRooms;";
         query = query.replace("bDate", bDate);
         query = query.replace("eDate", eDate);
         query = query.replace("kRooms", kRooms);
         int rowCount = esql.executeQuery(query);
         //System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
	  // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      // ...
      // ...
      try{      
         String kPrice;
         boolean valid = false;
         do{
            System.out.print("\tHow many prices?: ");
            kPrice = in.readLine();
               try {
                  int x = Integer.parseInt(kPrice);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("The amount of prices should be an int"); 
               }
            }while(!valid);
            valid = false;
            String cfname;
            String clname;
            System.out.print("\tEnter first name of customer: ");
            cfname = in.readLine();
            System.out.print("\tEnter last name of customer: ");
            clname = in.readLine();

         String query = "SELECT B.price FROM Booking B, (SELECT C.customerID FROM Customer C  WHERE C.fname= 'CFNAME' AND C.lname = 'CLNAME') AS C WHERE B.customer = C.customerID ORDER BY B.price DESC LIMIT kPrice;";
         query = query.replace("kPrice", kPrice);
         query = query.replace("CFNAME", cfname);
         query = query.replace("CLNAME", clname);
         int rowCount = esql.executeQuery(query);
         if(rowCount == 0){
            System.out.println("No customer found with that name");
         }
         //System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }


   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
	  // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
      try{      
         String hotelID;
         boolean valid = false;
         do{
            System.out.print("\tEnter Hotel ID: ");
            hotelID = in.readLine();
               try {
                  int x = Integer.parseInt(hotelID);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("HotelID should be an int"); 
               }
            }while(!valid);
            String cfname;
            String clname;
            System.out.print("\tEnter first name of customer: ");
            cfname = in.readLine();
            System.out.print("\tEnter last name of customer: ");
            clname = in.readLine();
            valid = false;
            String bDate;
            String eDate;
            System.out.println("Make sure beginning date is before ending date");
            do{
            System.out.print("\tEnter beginning date as \"yyyy-mm-dd\": ");
            bDate = in.readLine();
               if(bDate.matches("\\d{4}-\\d{2}-\\d{2}")){
                  valid = true;
                  System.out.println("Beginning date is " + bDate);
               }
               else{
                  System.out.println("Enter the date correctly.");
               }
            }while(!valid);
            valid = false;
            do{
            System.out.print("\tEnter ending date as \"yyyy-mm-dd\": ");
            eDate = in.readLine();
               if(eDate.matches("\\d{4}-\\d{2}-\\d{2}")){
                  valid = true;
                  System.out.println("Ending date is " + eDate);
               }
               else{
                  System.out.println("Enter the date correctly.");
               }
            }while(!valid);
         String query = "SELECT SUM(B.price) FROM Booking B, (SELECT C.customerID FROM Customer C  WHERE C.fname= 'CFNAME' AND C.lname = 'CLNAME') AS C WHERE B.customer = C.customerID AND B.hotelID = H.hotelID AND B.bookingDate >='bDate' AND B.bookingDate <= 'eDate';";
         query = query.replace("bDate", bDate);
         query = query.replace("eDate", eDate);
         query = query.replace("H.hotelID", hotelID);
         query = query.replace("CFNAME", cfname);
         query = query.replace("CLNAME", clname);
         int rowCount = esql.executeQuery(query);
         //System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...//Replace dates with input

   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){//FIXME
	  // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      // ...
      try{      
         String cNAME;
         System.out.print("\tEnter Company name: ");
         cNAME = in.readLine();
         String query = "SELECT R.repairType, R.hotelID, R.roomNo FROM (SELECT C.cmpID FROM MaintenanceCompany C WHERE C.name='CNAME') AS M, Repair R WHERE M.cmpID = R.mCompany;"; //replace abpk with input
         query = query.replace("CNAME", cNAME);
         int rowCount = esql.executeQuery(query);
         //System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }

      // ...
   }//end listRepairsMade
   
   public static void topKMaintenanceCompany(DBProject esql){
	  // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      try{      
         String kRooms;
         boolean valid = false;
         do{
            System.out.print("\tHow many companies?: ");
            kRooms = in.readLine();
               try {
                  int x = Integer.parseInt(kRooms);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("The amount of companies should be an int"); 
               }
            }while(!valid);

         String query = "SELECT M.name FROM (SELECT M.name, COUNT(*) AS repairs FROM MaintenanceCompany M, Repair R WHERE M.cmpID = R.mCompany GROUP BY M.name) AS M ORDER BY M.repairs DESC limit kRooms;";
         query = query.replace("kRooms", kRooms);
         int rowCount = esql.executeQuery(query);
         //System.out.println ("Room(s) are available");
      }catch(Exception e){
         System.err.println (e.getMessage());
      }
      // ...


   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
	  // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      // ...
      // ...

      try{      
         String hotelID;
         String roomNumber;
         boolean valid = false;
         do{
            System.out.print("\tHotelID?: ");
            hotelID = in.readLine();
               try {
                  int x = Integer.parseInt(hotelID);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("Enter a proper hotel ID"); 
               }
            }while(!valid);
            valid = false;
            do{
            System.out.print("\tRoom number?: ");
            roomNumber = in.readLine();
               try {
                  int x = Integer.parseInt(roomNumber);
                  valid = true; 
               }catch(NumberFormatException e) {
                  System.out.println("Enter a proper hotel ID"); 
               }
            }while(!valid);

         String query = "SELECT date_part('year', R.repairDate), COUNT(*) FROM Repair R WHERE R.hotelID = HOTELID AND R.roomNo = ROOMNUMBER GROUP BY date_part('year', R.repairDate);";
         query = query.replace("HOTELID", hotelID);
         query = query.replace("ROOMNUMBER", roomNumber);
         int rowCount = esql.executeQuery(query);
      }catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end listRepairsMade

}//end DBProject
