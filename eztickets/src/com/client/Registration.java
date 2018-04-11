package com.client;

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
import com.client.*;

public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url = "jdbc:mysql://eztickets.ddns.net:3306/User";
	static String user = "gccc";
	static String password = "gcccpassword";
	static Connection connection = null;
	static String query = null;
	Statement statement = null;
	static Boolean same = false;

	private String host;
	private String port;
	private String muser;
	private String pass;
	
	public void init() {
		// reads SMTP server setting from web.xml file
		ServletContext context = getServletContext();
		host = context.getInitParameter("host");
		port = context.getInitParameter("port");
		muser = context.getInitParameter("user");
		pass = context.getInitParameter("pass");
	}

	public Registration() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
		String firstname = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String loginID = request.getParameter("loginID");
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		// Check the database whether or not it has a same eamil with user's input email
		try {
			// connect the database
			String selectSQL = "SELECT * FROM infoTable";
			Statement preparedStatement = connection.createStatement();
			ResultSet rs = preparedStatement.executeQuery(selectSQL);

			while (rs.next()) {
				String temail = rs.getString("EMAIL");
				// if there has a same email with user's input, set "same" to true, and break it
				if ((temail.compareTo(email) == 0)) {
					response.getWriter().println("300");
					break;
				}
				else {
					query = "INSERT INTO infoTable (FIRSTNAME, LASTNAME, LOGINID, PASSWORD, EMAIL) VALUES ('" + firstname
							+ "','" + lastName + "','" + loginID + "','" + password + "','" + email + "');";
					executeQuery(query);					
					sendingEmail.messages(firstname,lastName, email, host, port, muser, pass);					
					response.getWriter().println("400");
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void executeQuery(String query) {
		try {
			statement = connection.createStatement();
			statement.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}