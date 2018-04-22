jQuery(document).ready(function() {
  $('.theater-1').hide();
  $('.theater-2').hide();
  $('.theater-3').hide();
  $('.theater-4').hide();
  $('.theater-5').hide();
  $('.theater-6').hide();
  $('.theater-7').hide();

  var pic = search(getID());
  $('.poster').attr("src", pic);

   var response = getData();
   var input = JSON.parse(response);
   var name = input.Movie;
   $('.movie_name').html(name);

   // Adds all of the necessary showtime buttons and hides the rest
   for (var i = 0; i < input.Theaters.length; i++) {
     for (var j = 0; j < 7; j++) {
       for (var k = 0; k < 16; k++) {
         if (k < input[i][j].length) {
           var temp = ((112*i) + (16*j) + k);
           $('label[for=test' + temp + ']').html(input[i][j][k]);
         } else {
           var temp = ((112*i) + (16*j) + k);
           $('input[for=test' + temp + ']').hide();
           $('label[for=test' + temp + ']').hide();
         }
       }
     }
   }

   //Finds the selected button so that the information can be stored as a cookie
   $('.checkout').on( "click", function() {
    var radios = document.getElementsByTagName('input');
     for (var i = 0; i < radios.length; i++) {
         if (radios[i].type === 'radio' && radios[i].checked) {
             var checkTheater = Math.floor(i / 112);
             var rem1 = i % 112;
             var checkDay = Math.floor(rem1 / 16);
             var rem2 = (rem1 % 16) - 4;
             var day = '';

             if (checkDay == 0) {
               day = 'Sunday';
             } else if (checkDay == 1) {
               day = 'Monday';
             } else if (checkDay == 2) {
               day = 'Tuesday';
             } else if (checkDay == 3) {
               day = 'Wednesday';
             } else if (checkDay == 4) {
               day = 'Thursday';
             } else if (checkDay == 5) {
               day = 'Friday';
             } else if (checkDay == 6) {
               day = 'Saturday';
             }

             alert(input.Theaters[checkTheater] + "\n" + day + "\n" + input[checkTheater][checkDay][rem2]);
         }
     }
   });

   //Shows the appropriate theater divs after they have been populated
   for (var i = 0; i < input.Theaters.length; i++) {
     switch(i) {
          case 0:
              $('.theater_1_name').html(input.Theaters[i]);
              $('.theater-1').show();
              break;
          case 1:
              $('.theater_2_name').html(input.Theaters[i]);
              $('.theater-2').show();
              break;
          case 2:
              $('.theater_3_name').html(input.Theaters[i]);
              $('.theater-3').show();
              break;
          case 3:
              $('.theater_4_name').html(input.Theaters[i]);
              $('.theater-4').show();
              break;
          case 4:
              $('.theater_5_name').html(input.Theaters[i]);
              $('.theater-5').show();
              break;
          case 5:
              $('.theater_6_name').html(input.Theaters[i]);
              $('.theater-6').show();
              break;
          case 6:
              $('.theater_7_name').html(input.Theaters[i]);
              $('.theater-7').show();
              break;
          default:
              var l = i + 1;
              $('.theater-'+ l).hide();
              break;
      }
   }

   //Controls the tab functionality
  jQuery('.tabs .nav-tabs a').on('click', function(e)  {
    var currentAttrValue = jQuery(this).attr('href');
    // Show/Hide Tabs
    jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
    // Change/remove current tab to active
    jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
    e.preventDefault();
  });

  //Create a cookie
  function createCookie(name,value,days) {
  	if (days) {
  		var date = new Date();
  		date.setTime(date.getTime()+(days*24*60*60*1000));
  		var expires = "; expires="+date.toGMTString();
  	}
  	else var expires = "";
  	document.cookie = name+"="+value+expires+"; path=/";
  }

  //Read a Cookie
  function readCookie(name) {
  	var nameEQ = name + "=";
  	var ca = document.cookie.split(';');
  	for(var i=0;i < ca.length;i++) {
  		var c = ca[i];
  		while (c.charAt(0)==' ') c = c.substring(1,c.length);
  		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
  	}
  	return null;
  }
});

  function getData() {
    var data = readCookie("ShowTime");
    return data;
  }

  function getID() {
    var data2 = readCookie("ID");
    return data2;
  }

  search function(id) {
  	var settings = {
  	  "async": true,
  	  "crossDomain": true,
  	  "url": "https://api.themoviedb.org/3/movie/"+id+"?language=en-US&api_key=f9e1f6d31bef16452f7887267033d960",
  	  "method": "GET",
  	  "headers": {},
  	  "data": "{}"
  	}

  	$.ajax(settings).done(function (response) {
  	  var posterUrl = 'http://image.tmdb.org/t/p/w185/'+response.poster_path;
  	  // append the image here
  	});
  }
