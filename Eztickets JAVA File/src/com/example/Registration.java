package com.example;

import java.io.*;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class Registration extends HttpServlet {
   private static final long serialVersionUID = 1L;
   static String             url              = "jdbc:mysql://eztickets.ddns.net:3306/User";
   static String             user             = "gccc";
   static String             password         = "gcccpassword";
   static Connection         connection       = null;
   static String query = null;
   Statement statement = null;
   static Boolean same = false;
   
   private String host;
   private String port;
   private String muser;
   private String pass;

   public Registration() {
      super();
   }
   
   public void init() {
       // reads SMTP server setting from web.xml file
       ServletContext context = getServletContext();
       host = context.getInitParameter("host");
       port = context.getInitParameter("port");
       muser = context.getInitParameter("user");
       pass = context.getInitParameter("pass");
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
              
          // Take value from user input
	      String firstname = request.getParameter("firstName") ;
		  String lastName = request.getParameter("lastName") ;
		  String loginID = request.getParameter("loginID") ;
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
		            // if there has a same email with user's input, set "same" to true, and break it
		            if((temail.compareTo(email) == 0)) 
		            {		            	
		            	same = true;
		            	break;
		            }
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		  }
		  
		  // if "same" is true, send a error Message back to the registration page
		  // else if not, send a successful message to the home page
		  if(same == true) 
		  {
			  same = false;
			  request.setAttribute("errorMessage", "This email has been registered. Please enter again.");
			  request.getRequestDispatcher("/input.jsp").forward(request, response);			  
		  }
		  else 
		  {
			 query = "INSERT INTO infoTable (FIRSTNAME, LASTNAME, LOGINID, PASSWORD, EMAIL) VALUES ('" + firstname + "','"
					  + lastName + "','" + loginID + "','" + password + "','" + email + "');";			  
			  executeQuery(query);			  
			  	
			  	// Setting Email Subject and Content 
		        String subject = "Registration Successful!";
		        String content = "Hello "+ firstname + " " + lastName + "!\n\n"
		        		+ "Thank you for creating an account on eztickets.ddns.net. "
		        		+ "We are happy to welcome you to our website and can’t wait to help you in the future. "
		        		+ "EZ Tickets is your one-stop website for buying movie tickets "
		        		+ "and viewing any information a person could want related to movies.\n\n"
		        		+ "Sincerely,\n" + 
		        		"The EZ-Tickets Support Team\r\n";
		 
		        String resultMessage = "";
		 
		        try {
		            EmailUtility.sendEmail(host, port, muser, pass, email, subject,
		                    content);
		            resultMessage = "Thanks for registration. You are our member now.";
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            resultMessage = "There were an error: " + ex.getMessage();
		        } finally {
		        	request.setAttribute("successfulMessage", resultMessage);			  
					  request.getRequestDispatcher("/index.html").forward(request, response);
		        }	        
			  			  
		  }	   
   }

   private void executeQuery(String query) {
	   try {
		   statement = connection.createStatement();
		   statement.execute(query);
	   }
	   catch (SQLException e) {		 
		   e.printStackTrace();		 
	   }	
   }   
}