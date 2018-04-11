package com.client;

public class sendingEmail{
	public static void messages(String firstname, String lastName, String email, String host, String port, String muser, String pass) {
				
		String subject = "Registration Successful!";
		String content = "Hello " + firstname + " " + lastName + "!\n\n"
							+ "Thank you for creating an account on eztickets.ddns.net. "
							+ "We are happy to welcome you to our website and can’t wait to help you in the future. "
							+ "EZ Tickets is your one-stop website for buying movie tickets "
							+ "and viewing any information a person could want related to movies.\n\n" + "Sincerely,\n"
							+ "The EZ-Tickets Support Team\r\n";

		String resultMessage = "";
		try {
				EmailUtility.sendEmail(host, port, muser, pass, email, subject, content);
				resultMessage = "Thanks for registration. You are our member now.";
			} catch (Exception ex) {
				ex.printStackTrace();
				resultMessage = "There were an error: " + ex.getMessage();
			} 
	}
}
	
	

