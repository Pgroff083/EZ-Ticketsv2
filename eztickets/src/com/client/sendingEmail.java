package com.client;

public class sendingEmail{
	public static void messages(String firstname, String lastName, String email, String host, String port, String muser, String pass, String showtime, int i) {
		
		String subject = null;
		String content = null;
		System.out.println(firstname + ", " + lastName + ", " + email + ", " + host + ", " + port + ", " + muser + ", " + pass + ", " + i);
		if(i == 1) {
			subject = "Registration Successful!";
			content = "Hello " + firstname + " " + lastName + "!\n\n"
								+ "Thank you for creating an account on eztickets.ddns.net. "
								+ "We are happy to welcome you to our website and can’t wait to help you in the future. "
								+ "EZ Tickets is your one-stop website for buying movie tickets "
								+ "and viewing any information a person could want related to movies.\n\n" + "Sincerely,\n"
								+ "The EZ-Tickets Support Team\r\n";
		}
		else if (i==2) 
		{
			subject = "";
			content = "";
		}
		
		try {
				EmailUtility.sendEmail(host, port, muser, pass, email, subject, content);
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
	}
}
	
	

