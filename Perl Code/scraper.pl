#!/usr/bin/perl
use lib;
use strict;
use warnings;
use utf8;
use JSON;
use Data::Dumper qw(Dumper);


my @filenames = ("villagepointe.movielist",  "majestic.movielist" , "westroads.movielist" , "oakview.movielist" , "twin.movielist" , "alamo.movielist" , "aksarben.movielist" );
my $nameRegex = '\[\d+\](.+)\((?:PG|G|PG-13|R|NC-17)\)\s+';
my $movieRegex = '.+\s+(.+\n)+';
my $dateRegex = '((?:Mon|Tue|Wed|Thu|Fri|Sat|Sun)\s(?:Feb|Mar|Apr|May)\s\d+).+';#gathers the day and showings
#my $timeRegex = '\[\d+\](\d+:\d\d[a|p]m)';		#feed in the above output, and get times
my $timeRegex = '(\d+:\d\d[a|p]m)';		#feed in the above output, and get times
my $movieName = "";
my @everyMovie;
my @outFile;

#my $json = JSON->new;
#my %data =(
#	'villagepointe.movielist' => {	},
#	'majestic.movielist' => {  },
#	'westroads.movielist' => {},
#	'oakview.movielist' =>	{},
#	'twin.movielist' =>	{},
#	'alamo.movielist' =>	{},
#	'aksarben.movielist' => {}
#);

foreach my $file (@filenames)	{
    my @movieNames;
    my @movieTimes;
    my $outString;
    my $currentDay;
    my $theaterName;
    my $DoW;
    my $date;
    my $time;
    if($file =~ m/(\w+).movielist/)	{
	$theaterName = $1;
	if($theaterName eq "villagepointe")	{
	    $theaterName = "Marcus Village Pointe";
	} elsif($theaterName eq "majestic")	{
	    $theaterName = "Marcus Majestic";
	} elsif($theaterName eq "westroads")     {
            $theaterName = "AMC Westroads";
	} elsif($theaterName eq "oakview")     {
            $theaterName = "AMC Oakview";
	} elsif($theaterName eq "twin")     {
            $theaterName = "Marcus Twin Creek";
	} elsif($theaterName eq "alamo")     {
            $theaterName = "Alamo Drafthouse";
	} elsif($theaterName eq "aksarben")     {
            $theaterName = "Aksarben Cinema";
	}
    }
    open(my $fh ,"<" , $file);
    #print "$file\n\n";
    while(my $line = <$fh>)	{
        if ($line =~ m/$nameRegex/)	{
	    $movieName = $1;
	    #print "$movieName\n";
	    #$data{$file} => \$movieName;

	    push @movieNames , $movieName;	#Store the name of the movie
	    #if($movieName =~ m/\'/)	{
	#	$movieName = &dropApost($movieName);
	    #}
	    $movieName =~ s/\'/\\\'/g;
	} elsif ($line =~ m/$dateRegex/)	{
	    $currentDay = $1;
	    #print "\n$currentDay\n";
	    if($currentDay =~ m/(Mon|Tue|Wed|Thu|Fri|Sat|Sun).+/)	{
		$DoW = $1;
	    }
	    if($currentDay =~ m/((?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\s\d+)/)	{
		$date = $1;
	    }
	    #print "$DoW $date\n";
	    #$data{$file}{$movieName} => \$currentDay;
	} elsif ($line =~ m/$timeRegex/g)	{	
	    @movieTimes = ();
	    push @movieTimes , $1;
	    while($line =~ m/$timeRegex/g)	{
		#push @movieTimes , $1;
		$time = $1;
		$outString = "$theaterName|$movieName|$DoW|$date|$time"; 			
		#print "$outString\n";
		push @outFile, $outString;
	    }
	    #&printArray(@movieTimes);
	    #$data{$file}{$movieName}{$currentDay} = [@movieTimes];
	}
    }
    close($fh);
    &checkMovieList(@movieNames);

}
my $writeFile = 'showtime.data';
open(my $fh, ">", $writeFile) or die "Could not open file '$writeFile' $!";
foreach my $p (@outFile)	{
    print $fh "$p\n";
}
close($fh);
#print Dumper \%data;
#$json = encode_json \%data;
#print "$json\n";
&writeFile(@everyMovie);
#open(my $fh , ">" , 'showtimes.json') or die "Could not open file showtimes.json $!";
#print $fh "$json\n";
print "Write Completed\n";


#-------------------------------FUNCTIONS---------------------------------------#

sub printArray	{
    my (@array) = @_;
    print "\n\n";
    foreach (@array)	{
	print "$_\n";
    }
    return;
}

sub dropApost	{
    my $movie = $_;
    $movie =~ s/\'/\\\'/g;
    print "$movie\n";
    return $movie;
}

sub checkMovieList    {
    my $checked = 0;	#0 if not there, 1 if founf
    my (@tempMovies) = @_;
    foreach my $n (@tempMovies)	{
	$checked = 0;
	foreach my $m (@everyMovie)	{
	    if($n eq $m)  {
		$checked = 1;
	    }
	}
	if($checked == 0)	{
	    push @everyMovie, $n;
	}
    }
}

sub writeFile	{
    my (@array) = @_;
    my $writeFile = 'movieList.txt';
    my @sortedArray = sort @array;
    open(my $fh, ">", $writeFile) or die "Could not open file '$writeFile' $!";

    foreach my $p (@sortedArray)	{
	print $fh "$p\n";
    }
    close($fh);
}

