package com.client;

import java.util.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.json.simple.*;

public class MovieSearch extends HttpServlet {
	static String url = "jdbc:mysql://eztickets.ddns.net:3306/movies";
	static String user = "gccc";
	static String password = "gcccpassword";
	static Connection connection = null;
	static String query = null;
	Statement statement = null;
	
	static ArrayList<String> JSONResults = new ArrayList<String>();
	static ArrayList<String> results = new ArrayList<String>();
	JSONObject packagedJSON = new JSONObject();
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
	//Connect to the Database
		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);

		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		if (connection != null) {

		} else {
			System.out.println("Failed to make connection!");
		}
		
	//Get search request
		String search = request.getParameter("search");
		int numResults = searchDatabase(search);
		if(numResults == 0)		{
			response.getWriter().println("101");	//No results
		}
		else	{
			//Send the Arraylist
			packagedJSON.put("query", search);
			packagedJSON.put("numResults", numResults);
			packagedJSON.put("results", JSONResults);
			response.getWriter().println(packagedJSON.toJSONString());
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static int searchDatabase(String s)	{	//Return # of matches		
		try	{	
				//First, fill the array with every movie entry...
			String json = null;
			String selectSQL = "SELECT * FROM Movies";		         
	    	Statement preparedStatement = connection.createStatement();		         
	    	ResultSet rs = preparedStatement.executeQuery(selectSQL);
            rs.next();
            while(rs.next())	{
	    		String movie = rs.getString("MoviesName");
	    		json = rs.getString("JSON");
	    		results.add(movie);
	        }
            
            //Now remove entries without a match
			for(int x = 0; x < results.size(); x++)	{
				if(results.get(x).contains(s))	{
					JSONResults.add(json);
				}
			}
            
            
		} catch(SQLException se)	{
			se.printStackTrace();
		}
		
		return JSONResults.size();
	}
	
	
	
}