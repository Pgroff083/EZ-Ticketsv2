package com.client;

import java.util.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.*;

@WebServlet("/moviesSearch")
public class MovieSearch extends HttpServlet {
	static String url = "jdbc:mysql://eztickets.ddns.net:3306/movies";
	static String user = "gccc";
	static String password = "gcccpassword";
	static Connection connection = null;
	static String query = null;
	Statement statement = null;
		
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
		
		
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<String> JSONResults = new ArrayList<String>();
		JSONObject packagedJSON = new JSONObject();
		
		results.clear();
		//Get search request
		String search = request.getParameter("search");
		int numResults = searchDatabase(search, JSONResults);
		if(numResults == 0)		{
			response.getWriter().println("101");	//No results
		}
		else	{			
			//Send the Arraylist
			packagedJSON.put("query", search);
			packagedJSON.put("numResults", numResults);
			packagedJSON.put("results", JSONResults);
			response.getWriter().println(packagedJSON.toJSONString().replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}").replace("\\\\\"", "\\\""));
			packagedJSON.clear();
			
		}		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public static int searchDatabase(String s, ArrayList<String> JSONResults)	{	//Return # of matches		
		try	{	
			//First, fill the array with every movie entry...
			String selectSQL = "SELECT * FROM Movies";		         
	    	Statement preparedStatement = connection.createStatement();		         
	    	ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while(rs.next())	{
            	if(rs.getString("MoviesName").toLowerCase().contains(s.toLowerCase())) 
            	{
            		if(rs.getString("MoviesName").contains("3D") || rs.getString("MoviesName").contains("2D")) 
            		{
            			
            		}
            		else
            			JSONResults.add(rs.getString("JSON"));
            	}
	        }            
		} catch(SQLException se)	{
			se.printStackTrace();
		}		
		return JSONResults.size();
	}
}
