package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.json.simple.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/readMoviesDB")
public class ReadMoviesDB extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String             url              = "jdbc:mysql://eztickets.ddns.net:3306/movies";
   static String             user             = "gccc";
   static String             password         = "gcccpassword";
   static Connection         connection       = null;

   public ReadMoviesDB() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
      try {
         Class.forName("com.mysql.jdbc.Driver");
      } catch (ClassNotFoundException e) {
         System.out.println("Where is your MySQL JDBC Driver?");
         e.printStackTrace();
         return;
      }
      connection = null;
      try {
         connection = DriverManager.getConnection(url, user, password);
      } catch (SQLException e) {
         System.out.println("Connection Failed! Check output console");
         e.printStackTrace();
         return;
      }
      if (connection != null) {
    	  
      } else {
         System.out.println("Failed to make connection!");
      }
      try {
    	    String action = request.getParameter("action");
    	    System.out.println(action);
    	  	//String action = "Blockers";
    	  	JSONObject json = new JSONObject();
    	  	String text;
    	  	
    	  	if (action.equals("AllMovies999")) 
    	  	{
    	  		String selectSQL = "SELECT * FROM Movies";
				Statement preparedStatement = connection.createStatement();
				ResultSet rs = preparedStatement.executeQuery(selectSQL);
				List<String> moviesList = new ArrayList<String>();				
				while (rs.next()) {
					if(rs.getString("MoviesName").contains("3D") || rs.getString("MoviesName").contains("2D")) 
            		{
            			
            		}
            		else
            			moviesList.add(rs.getString("JSON"));
				}
				json.put("results", moviesList);					
				text = json.toJSONString();
				response.getWriter().print((text.replace("\\\\\"", "\\\"").replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}")));						
    	  	}
    	  	else 
    	  	{
    	  		int MoviesID = 0;
    	  		ArrayList<String> theaters = new ArrayList<String>();    	  		
    	  		ArrayList<Integer> theatersID = new ArrayList<Integer>();
    	  		String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu" , "Fri", "Sat" };
    	  		json.put("Movie", action);
    	  		
    	  		String selectSQL = "SELECT * FROM Movies";				
    	  		Statement preparedStatement = connection.createStatement();
    	  		ResultSet rs = preparedStatement.executeQuery(selectSQL);
				while (rs.next()) {
					if (action.equals(rs.getString("MoviesName")))
					{
						MoviesID = rs.getInt("MoviesID");
						break;
					}
				}
    	  		for(int i = 1; i < 8; i++) 
    	  		{
    	  			selectSQL = "SELECT * FROM Main where MoviesID = " + MoviesID;
    	  			rs = preparedStatement.executeQuery(selectSQL);
    	  			while(rs.next()) 
    	  			{
    	  				if ( i == rs.getInt("TheaterID")) 
    	  				{
    	  					theatersID.add(i);
    	  					selectSQL = "SELECT * FROM Theater where TheaterID = " + rs.getInt("TheaterID");
    	  					Statement preparedStatement2 = connection.createStatement();
    	  					ResultSet rs2 = preparedStatement2.executeQuery(selectSQL);
    	  					while(rs2.next()) 
    	    	  			{
    	  						if ( i == rs.getInt("TheaterID")) 
    	    	  				{
    	  							theaters.add(rs2.getString("TheaterName"));
            	  					break;
    	    	  				}    	  						
    	    	  			}
    	  					break;    	  					
    	  				}
    	  			}    	  			
    	  		}
    	  		json.put("Theaters", theaters);
    	  		JSONObject json2 = new JSONObject();
    	  		for(int j = 0; j < theatersID.size(); j++)
    	  		{
    	  			for(int i = 0; i < 7; i++) 
        	  		{
    	  				ArrayList<String> times = new ArrayList<String>();
        	  			times.clear();
        	  			selectSQL = "SELECT * FROM Main where MoviesID = " + MoviesID +" AND TheaterID = " + theatersID.get(j) + " AND DayID = " + (i+1);
        	  			rs = preparedStatement.executeQuery(selectSQL);
        	  			while(rs.next()) 
        	  			{
        	  				selectSQL = "SELECT * FROM Times where TimesID = " + rs.getInt("TimesID");
        	  				Statement preparedStatement2 = connection.createStatement();
    	  					ResultSet rs2 = preparedStatement2.executeQuery(selectSQL);
    	  					while(rs2.next()) 
    	    	  			{
    	  						times.add(rs2.getString("Times"));
    	    	  			}     	  					
        	  			}
        	  			json2.put(i, times);		
        	  		}
    	  			json.put(j, json2.toJSONString());
    	  		}    	  		   	  		    	  		
    	  		text = json.toJSONString();    	  		
    	  		response.getWriter().print((text.replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}")));				
    	  	}
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }   
}