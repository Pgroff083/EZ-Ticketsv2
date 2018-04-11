package com.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReadMoviesDB")
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
    	  
    	  	String action = request.getParameter("code");    	  
    	  	String object = null;

			if (action.equals("AllMovies999"))
			{
				object = "{ \"Movies\" : [";
				// connect the database
				String selectSQL = "SELECT * FROM Movies";
				Statement preparedStatement = connection.createStatement();
				ResultSet rs = preparedStatement.executeQuery(selectSQL);
				while (rs.next()) {
					if (!rs.isLast())
					{
						object = object + "\"" + rs.getString("MoviesName") + "\", ";
					}
					else
					{
						object = object + "\"" + rs.getString("MoviesName") + "\" ] }";
					}
				}
				response.getWriter().println(object);				
			}
			else 
			{
				int id;
				object = "{ \"Movies\" : [\"";
				String selectSQL = "SELECT * FROM Movies";
				Statement preparedStatement = connection.createStatement();
				ResultSet rs = preparedStatement.executeQuery(selectSQL);
				while (rs.next()) {
					if (action.equals(rs.getString("MoviesName")))
					{
						id = rs.getInt("MoviesID");
						object = object + "\"], \"Theaters\": [";						
					}
				}
				selectSQL = "SELECT * FROM Theater";
				rs = preparedStatement.executeQuery(selectSQL);

				while (rs.next()) {
					if (!rs.isLast())
					{
						object = object + "\"" + rs.getString("TheaterName") + "\", ";
					}
					else
					{
						object = object + "\"" + rs.getString("TheaterName") + "\" ], ";
					}			
				}
			}
			
	
			
			//response.getWriter().println(object);
         
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }
}