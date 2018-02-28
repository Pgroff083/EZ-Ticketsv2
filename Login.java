package com.example;

import java.io.IOException;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String             url              = "jdbc:mysql://eztickets.ddns.net:3306/User";
   static String             user             = "gccc";
   static String             password         = "gcccpassword";
   static Connection         connection       = null;
   static String query = null;
   Statement statement = null;
   static Boolean login = false;
   static String loginID;

   public Login() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   doPost(request, response);
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   response.setContentType("text/html;charset=UTF-8");
	      
	   	 // Connect to the MySQL database
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
              
          // Take values from user input	      
		  String password = request.getParameter("password") ;
		  String email = request.getParameter("email") ;
		  
		  // Check the database whether or not it has a same eamil with user's input email
		  try {
			  	 // connect the database
		         String selectSQL = "SELECT * FROM infoTable";		         
		         Statement preparedStatement = connection.createStatement();		         
		         ResultSet rs = preparedStatement.executeQuery(selectSQL);
		         
		         while (rs.next()) {		        	
		            String temail = rs.getString("EMAIL");
		            String tpassword = rs.getString("PASSWORD");
		            loginID = rs.getString("LOGINID");
		            // if there has a same email with user's input, then check
		            // the password. If the password is also same, then set login true and break it
		            if((temail.compareTo(email) == 0)) 
		            {		            	
		            	if((tpassword.compareTo(password)) == 0) 
		            	{
		            		login = true;
		            		break;
		            	}
		            }
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		  }
		  
		  // if "login" is true, send a successful Message back to the home page
		  // else if not, send a error message to the login page
		  if(login == true) 
		  {
			  login = false;			  
			  request.setAttribute("successfulMessage", "Welcome! " + loginID);
			  request.getRequestDispatcher("/login.jsp").forward(request, response);			  
		  }
		  else 
		  {
			  request.setAttribute("errorMessage", "Email or password is invalid. Please enter again.");
			  request.getRequestDispatcher("/login.jsp").forward(request, response);			  
		  }	   
   }  
   
}