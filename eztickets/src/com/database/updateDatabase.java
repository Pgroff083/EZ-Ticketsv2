package com.database;

import java.util.*;
import java.io.*;
import java.sql.*;
import java.net.*;

//import org.apache.commons.io.IOUtils;
//import org.json.*;
import org.json.simple.*;
import org.json.simple.parser.*;

//import com.sun.javafx.scene.paint.GradientUtils.Parser;

import java.nio.charset.Charset;

public class updateDatabase 	{
    private static int count = 0;
    static Statement statement = null;
    static Connection connection = null;
    static String url = "jdbc:mysql://eztickets.ddns.net:3306/movies";
	static String user = "gccc";
	static String password = "gcccpassword";	
	static String query = null;
    
    public static void main(String [] args) throws IOException {
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
	//Su M T W R F Sa
	//1  2 3 4 5 6 7 
	//Theaters:
	//Aksarben 1, Alamo 2, Oakview 3, Westroads 4, Majestic 5, TC 6, VP 7 
	String[] theaters = {"Aksarben Cinema", "Alamo Drafthouse", "AMC Oakview" , "AMC Westroads" , "Marcus Majestic", "Marcus Twin Creek", "Marcus Village Pointe"};
	String[] days = {"Mon", "Tue", "Wed", "Thu" , "Fri", "Sat" , "Sun" };


	// Connect to the MySQL database
	try {
	    Class.forName("com.mysql.jdbc.Driver");
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
	//Clear out old entries first
	executeQuery("TRUNCATE Movies");
	executeQuery("TRUNCATE Dates");
	executeQuery("TRUNCATE Times");
	executeQuery("TRUNCATE Main");
	boolean same = false;
	String test = "";
	int ID;
	long startTime = System.nanoTime();
	
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
	    	String JSON = "";
	    	boolean sameJSON = false;
	    	String previousMovie = "";
	    	
	    	while(rs.next())	{
	    		test = rs.getString("MoviesName");
	    	//System.out.println(test);
	    		String otherTest = dataTable[z][1];
	    		if(dataTable[z][1].contains("\'"))	{
	    			otherTest = dataTable[z][1].replace("\\", "");
	    		}
    			//System.out.println(test + " " + otherTest);
	    		if (test.equals(otherTest))	{		    	
	    			same = true;		    	
	    			ID = rs.getInt("MoviesID");
	    		} else if(otherTest.contains(test))	{

			    	sameJSON = true;
			    	previousMovie = test;
			    }
		    
		    
	        }
	    	if(same == false)	{
	    		if(sameJSON == true)	{
	    			selectSQL = ("select * from Movies where MoviesName = '" + previousMovie + "';");
	    			rs = preparedStatement.executeQuery(selectSQL);
		            rs.next();
		            JSON = rs.getString("JSON");
	    		}
	    		else	{
	    			JSON = getJSON(dataTable[z][1]);
	            	if(JSON.contains("\'"))	{
		    			JSON = JSON.replace("\'", "\\'");
		    		}
	    		}
	    		query = "INSERT INTO Movies (MoviesName , JSON) VALUES ('" + dataTable[z][1] + "','" + JSON +"');";
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
	 	    //System.out.println(query);
	 	    executeQuery(query);
	    	
	 	    //System.out.println("Updating Database: Entry #" + (z+1) + " of " + count);
	 	    
	    }catch (SQLException se)	{
	    	System.out.println("A SQL Exception has occurred");
	    }

	//Fill the Main table with all IDs    
	  
		}
	float endTime = System.nanoTime()-startTime;
	System.out.printf("Total Runtime = %f seconds.\n" , endTime / 1000000000);
    }

    
    
 /*******************Methods**************************************************************/
    
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
    
    public static String getJSON(String movie) throws IOException	{
    	//System.out.println("Getting JSON...");
    	String JString = "";
    	String query = "https://api.themoviedb.org/3/search/movie?api_key=f9e1f6d31bef16452f7887267033d960&language=en-US&query=" + changeSpaces(movie) +"&page=1&include_adult=false";
    	JSONObject json = null;
    	//JSONObject JMovie = null;
    	Object id = null;
    	try	{
    		//Get the Search results JSON
    		//JSONParser parser = new JSONParser();
    		//System.out.println(query);
    		json = readJsonFromURL(query);
    		
    		//Retrieve the id from the JSON
    		JSONArray results = (JSONArray) json.get("results");
    		Iterator<JSONObject> iterator = results.iterator();
    		json = iterator.next();
    		
    		//Use the id to get the Movie Details JSON
    		id = json.get("id");
    		query = "https://api.themoviedb.org/3/movie/"+ id.toString() +"?api_key=f9e1f6d31bef16452f7887267033d960&language=en-US";
    		json = readJsonFromURL(query);
    		
    		
    	} catch(IOException ioe)	{
    		System.out.println("ERROR: IO Exception");
    		ioe.printStackTrace();
    		//System.exit(0);
    	} 
    	JString = json.toString();
    	//System.out.println(JString);
    	return JString;		
    }
    
    public static JSONObject readJsonFromURL(String url) throws IOException/*, JSONException*/ {
    	URL address = new URL(url);
    	InputStream is = address.openStream();
    	JSONObject json = null;
        
    	try {
        	BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
        	JSONParser parser = new JSONParser();
        	Object obj = parser.parse(rd);
        	json = (JSONObject) obj;
        	return json;
        } catch(ParseException pe)	{
        	System.out.println("ERROR: Parse Exception");
			System.exit(0);
    	}finally {
    		is.close();
        }
        return json;
    }
    
    public static String changeSpaces(String movie)  {
    	//Required because the API returns a HTTP 400 code is not %20 instead of spaces
    	String s = movie.replace(" ", "%20");
    	return s;
    }
    
}
