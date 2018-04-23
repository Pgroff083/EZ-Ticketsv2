
# EZ-Ticketsv2
Software Enginnering Web App Project

This is a brief description of the group project by G-Triple-C:

The goal of this project is to develop a web application using HTML 5 and JavaScript along with a MySQL controled database stored on an Amazon AWS EC2 server that runs Unbuntu.

Pull before edit anything!

The template file contains navbar and footer as standard elements of the website.

This is a test to see if Paul can get Version Control, under control.

Firday March 16, 2018: Begins the beta version 1.0 of the project. Use numbering and good patch notes to make it easier to revert changes.

------------------------------------------------------------------------------------------------------------------------------
Beta v1.1 - Kwok (March 16 at 15:26)
	Begins to use README.md to organize our work like that.
	So, every time we have change in the project, we need add the command in the READNE.md.
	The format like that:
		Beta v(number) - (name) (date at time)
			(What change)
			(where starting changing)
			(where end of the change)
	
Beta v1.2, Zhicheng, 2 April 2018
	In HTML file:
	Home page's update(the html file and corresponding js file) 
	Implemente MovieDB API to update homepage's movie card
	Get rid of check out button from the nav-bar
	Add button to the login popup to navigate to the registration page.
	In JavaScript file:
	Implement jQuery to handle login/out function and update home page's movie card information. 
	
Beta v1.3 - Kwok (April 11 at 12:40)
	1.	I create a class for sending email which is called sendingEmail.java
			It contains the confirmation messages 
	2.	organize the Java src folder (separate the example folder to client and database)
			The client folder contains Login, Registration, EmailUtility, and sendingEmail
			The database folder contains ReadMoviesDB, updateDatabase, and showtiime.database
	3.	I also update the index.html
			change the sign up button page from other/Registration.html to Registration.html
			
Beta v1.4 - Noah (April 12 at 3:00)
	1. updateDatabase now handles grabbing the JSON files for each movie and storing them in 
		the database. It stores them as a string, a very long string, and will eventually be sent
		off to the front-end.
	2. showtimes.data has been updated with today's showtimes. Will continue to update until launch
		day.
		
Beta v1.5 - Kwok (April 14 at 16:15)
	1. Newer version of ReadMoviesDB.java
		Capable to pass a JSON object to the front-end, however, the object is having problem for the front-end to read.
	
Beta v1.6 - Noah (April 17 at 2:40)
	1. MovieSearch.java for the searching process
	2. Sent a second push to the server to fix numbering mistake
	
Beta v1.7 - Zhicheng (April 17 at 3:00)
	1. Added changes to home page
	
Beta v1.8 - Noah (April 18 at 2:09)
	1. Adding 404 page
	2. Added the EZ tickets icon to homepage, may need to add to every page
	
Beta v1.9 - Kwok (April 18 at 6:52)
	1. take all of the html page out from the other_page folder
	2. After that, I changed some code in index.html, 404.html, AllMovies.html
	3. change little bit with updateDatabase and SearchMovies
	4. change with the allmovie.js which after the search bar send the search keyword to back-end, back-end will send a JSON object as a result
	5. I also add a link in web.xml to link to the 404 page

Beta v1.10 - Paul (April 22 at 3:57 pm)
	1. Added See_Showtime.html    Note: Page should work flawlessly except that I haven't tested Cookies on it yet.
	2. Added See_Showtime.css
	3. Added See_Showtimes.js
	4. Added About_Us.html        Note: Descriptions still needed for this page, and an image for Kwok
	5. Added About_Us.css
	
Beta v1.11 - Kwok (April 22 at 6:12)
	1. I made some change with the src folder
	2. I made some change with index.html, ALlMovies.html, allMovies.js
	
Beta v1.12 - Kwok (April 23 at 5:53)
	1. now allmovies page can link to the showtime page and pass value to it
	2. clear some useless code in some html pages
	3. I might do something wrong with the about_us page, so go to see it and fix it. Thanks.