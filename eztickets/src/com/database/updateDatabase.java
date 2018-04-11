package com.database;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.nio.charset.Charset;

public class updateDatabase	{
    private static int count = 0;
    static Statement statement = null;
    static Connection connection = null;
    static String url = "jdbc:mysql://eztickets.ddns.net:3306/movies";
	static String user = "gccc";
	static String password = "gcccpassword";	
	static String query = null;
    
    public static void main(String [] args) {
        ArrayList<String> data;
        data = new ArrayList<String>();
	data = readFile(data);				//Read data output from scraper.pl
	String[][] dataTable = new String[count][5];
	int[][] tableIDs = new int[count][5];
	for(int x = 0; x < count; x++)	{		//Separate the data into the 5 parts
	    String line = data.get(x);
	    //System.out.println(line);
	    String[] tokens = line.split("\\|");

	    for(int y = 0; y < tokens.length; y++)	{
		//System.out.println( tokens[y]);
		dataTable[x][y] = tokens[y];
	    }
	}

	//Data Order:
	// Theater Movie DoW Date Time
	//Days:
	//M T W R F Sa Su
	//1 2 3 4 5 6  7
	//Theaters:
	//Aksarben 1, Oakview 2, Westroads 3, TC 4, VP 5, Alamo 6, Majestic 7
	String[] theaters = {"Aksarben Cinema", "AMC Oakview" , "AMC Westroads" , "Marcus Twin Creek", "Marcus Village Pointe", "Alamo Drafthouse" , "Marcus Majestic"};
	String[] days = {"Mon", "Tue", "Wed", "Thu" , "Fri", "Sat" , "Sun" };

	

	// Connect to the MySQL database
	try {
	    Class.forName("com.mysql.jdbc.Driver");     //Issue occurring on runtime
	} catch (ClassNotFoundException cnfe) {	         
	    cnfe.printStackTrace();
	    return;
	}
	/*MysqlDataSource dataSource = new MysqlDataSource();
	dataSource.setUser(user);
	dataSource.setPassword(password);
	dataSource.setServerName(url);*/
	      
	connection = null;
	try {						//Use the driver to connect to database
	    //connection = dataSource.getConnection();      
	    connection = DriverManager.getConnection(url, user, password);
	} catch (SQLException e) {	         
	    e.printStackTrace();
	    return;
	}
	if (connection != null) {	                 
	} else {
	    System.out.println("Failed to make connection!");                 
	}
	//Beginning of database filling...
	//Clearlear out old entries first
	executeQuery("TRUNCATE Movies");
	executeQuery("TRUNCATE Dates");
	executeQuery("TRUNCATE Times");
	executeQuery("TRUNCATE Main");
	boolean same = false;
	String test = "";
	int ID;
	
	for(int z = 0; z < count; z++)	{	//For each movie showtime entry...
	    for(int a = 0; a < 7; a++)	{	//Fill in keys for theater
	    	if(dataTable[z][0].equals(theaters[a]))
	    		tableIDs[z][0] = a+1;
	    }	        
	    
	    try		{
	    	String selectSQL = "SELECT * FROM Movies";		         
	    	Statement preparedStatement = connection.createStatement();		         
	    	ResultSet rs = preparedStatement.executeQuery(selectSQL);
	        ID = 0;
	    	same = false;    	    	
	    	
	    	while(rs.next())	{
	    		test = rs.getString("MoviesName");
	    		
		    if (test.equals(dataTable[z][1]))	{		    	
		    	same = true;		    	
		    	ID = rs.getInt("MoviesID");
		    }
		    
		    
	        }
	    	if(same == false)	{
	            query = "INSERT INTO Movies (MoviesName) VALUES ('" + dataTable[z][1] + "');";
	            executeQuery(query);
	            selectSQL = ("select * from Movies where MoviesName = '" + dataTable[z][1] + "';");
	            rs = preparedStatement.executeQuery(selectSQL);
	            rs.next();
	            ID = rs.getInt("MoviesID");
	            
	    	}
	    	tableIDs[z][1] = ID;	//Store the ID
	    
	    	for(int b = 0; b < 7; b++)	{
	    		if(dataTable[z][2].equals(days[b]))
	    			tableIDs[z][2] = b+1;
	    	}
	    
	    	selectSQL = "SELECT * FROM Dates";		         
	    	preparedStatement = connection.createStatement();		         
	    	rs = preparedStatement.executeQuery(selectSQL);

	    	ID = 0;
	    	same = false;
	    	while(rs.next())	{
		    test = rs.getString("Dates");

		    if (test.equals(dataTable[z][3]))	{
		    	same = true;		    	
		    	ID = rs.getInt("DatesID");
		    }
	    	}
	    	if(same == false)	{
	            query = "INSERT INTO Dates (Dates) VALUES ('" + dataTable[z][3] + "');";
	            executeQuery(query);

	            selectSQL = ("select * from Dates where Dates ='" + dataTable[z][3] + "'");
	            rs = preparedStatement.executeQuery(selectSQL);
	            rs.next();
	            ID = rs.getInt("DatesID");
	    	}
	    	tableIDs[z][3] = ID;	//Store the ID
	    	

	    
	    	selectSQL = "SELECT * FROM Times";		         
	    	preparedStatement = connection.createStatement();		         
	    	rs = preparedStatement.executeQuery(selectSQL);
	    	ID = 0;
	    	same = false;
	    	while(rs.next())	{
	    		test = rs.getString("Times");   		
	    		
		    if (test.equals(dataTable[z][4]))	{
		    	same = true;
		    	ID = rs.getInt("TimesID");		    	
		    }
	        }
	    	if(same == false)	{
	            query = "INSERT INTO Times (Times) VALUES ('" + dataTable[z][4] + "');";
	            executeQuery(query);
	            selectSQL = ("select * from Times where Times ='" + dataTable[z][4] + "'");
	            rs = preparedStatement.executeQuery(selectSQL);	
	            rs.next();
	            ID = rs.getInt("TimesID");
	    	}
	    	tableIDs[z][4] = ID;	//Store the ID
	    	
	    	
	    	query = "INSERT INTO Main(TheaterID, MoviesID, DayID, DatesID, TimesID) VALUES (" + tableIDs[z][0] + "," +  tableIDs[z][1]+ "," + tableIDs[z][2] + "," + tableIDs[z][3] + ","  + tableIDs[z][4] + ");";
	 	    System.out.println(query);
	 	    executeQuery(query);
	    	
	    }catch (SQLException se)	{
	    	System.out.println("A SQL Exception has occurred");
	    }

	//Fill the Main table with all IDs    
	  
		}
 
    }

    public static ArrayList<String> readFile(ArrayList<String> data)	{
	String line = "";
	try {
		FileReader fileReader = new FileReader("src/com/database/showtime.data");
		BufferedReader br = new BufferedReader(fileReader);
	while((line = br.readLine()) != null)	{
	    data.add(line);
	    count++;
	}
	br.close();
	}catch(IOException ioe)	{
		System.out.println("Error getting showtime.data");
	}
	
	return data;
    }

    private static void executeQuery(String query) {
 	   try {
 		   statement = connection.createStatement();
 		   statement.execute(query);
 	   }
 	   catch (SQLException e) {		 
 		   e.printStackTrace();		 
 	   }	
    }   
}
