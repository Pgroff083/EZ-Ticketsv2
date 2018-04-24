Perl Web Scraping Code ReadME

The 2 Perl files in this folder are the main web scraping files for the EZ Tickets web-app:


pageGrabber.pl

This file is takes the showtime data using the lynx command on Unix from the website
tributemovies.com, for each specific theater we are covering. It takes the lynx webpage,
as plaintext and stores that in a file for each theater.

scraper.pl

This file takes the 7 files for each theater and using regular expressions gathers 
the necessary showtimes for each movie. this data was stored in the large file
showtimes.data which stores each showtime in the format:
	Theater|MovieName|DayOfWeek|Date|ShowTime
	
showtime.data is fed into the java program updateDatabase.java which puts the data into
our database.