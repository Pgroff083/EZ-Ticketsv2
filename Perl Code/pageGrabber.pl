#!/usr/bin/perl

use lib;
use strict;
use warnings;
#use Modern::Perl;

my @urls = ("http://www.tributemovies.com/cinema/Nebraska/Omaha/Marcus+Village+Pointe+Cinema/8763/" ,	    "http://www.tributemovies.com/cinema/Nebraska/Omaha/Marcus+Majestic+Cinema+of+Omaha/2676/" ,     "http://www.tributemovies.com/cinema/Nebraska/Omaha/AMC+Classic+Westroads+14/31510/",	          "http://www.tributemovies.com/cinema/nebraska/omaha/AMC+Oakview+Plaza+24/4892/" ,   	           "http://www.tributemovies.com/cinema/Nebraska/Bellevue/Marcus+Twin+Creek+Cinema/7605/",  	    "http://www.tributemovies.com/cinema/Nebraska/Omaha/Alamo+Drafthouse+Cinema+-+La+Vista/40660/",  "http://www.tributemovies.com/cinema/Nebraska/Omaha/Aksarben+Cinema/26260/"
	   );

my @filenames = ("villagepointe.movielist",  "majestic.movielist" , "westroads.movielist" , "oakview.movielist" , "twin.movielist" , "alamo.movielist" , "aksarben.movielist" );

for (my $num = 0; $num < scalar(@urls); $num++)	{
    my $command = "sudo /usr/bin/lynx -dump -width=150 $urls[$num]";
    my $data = `$command`;
    open(FH, '>' , $filenames[$num]) or die "Could not open file '$filenames[$num]' $!";
    print FH $data;
    close FH;
}

print "Data transfer complete\n";
