package com.client;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/checkOut")
public class CheckOut extends HttpServlet {	
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

	public CheckOut() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		// Take value from user input
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");		
		String email = request.getParameter("email");
		
		String showtime = null;

		sendingEmail.messages(firstName,lastName, email, host, port, muser, pass, showtime, 2);					
		response.getWriter().print("successful");
	}
	
}