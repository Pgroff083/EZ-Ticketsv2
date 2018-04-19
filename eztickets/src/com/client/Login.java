package com.client;

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
	static String url = "jdbc:mysql://eztickets.ddns.net:3306/User";
	static String user = "gccc";
	static String password = "gcccpassword";
	static Connection connection = null;
	static String query = null;
	Statement statement = null;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
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

		// Take values from user input
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		// Check the database whether or not it has a same eamil with user's input email
		try {
			// connect the database
			String selectSQL = "SELECT * FROM infoTable";
			String rptext = null;
			Statement preparedStatement = connection.createStatement();
			ResultSet rs = preparedStatement.executeQuery(selectSQL);

			while (rs.next()) {
				String temail = rs.getString("EMAIL");
				String tpassword = rs.getString("PASSWORD");
				
				// if there has a same email and password with user's input, then 
				// set login true and break it
				if (email.equals(temail) && password.equals(tpassword)) {
					rptext = email;
					break;
				}
				else 
				{
					rptext = "100";
				}					
			}
			response.getWriter().print(rptext);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}