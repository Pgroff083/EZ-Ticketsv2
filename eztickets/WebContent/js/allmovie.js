function renderImg(obj) {
	
	var imgTagsrc = obj.results;
	var pOpen = '<p class="card-body-p"></p>';
	
	for (var i = 0; i < imgTagsrc.length; i++) {

	      var cardImgOpen = '<div class="card" value = "' + i + '"><div class="imgSpace">';
	      var divDClose = '<div/><div/>';
	      var imgTagOpen = '<img class="card-img-top" src="';
	      var imgTagClose = '" alt="Card image cap" data-toggle="modal" data-target=".bd-example-modal-lg">';

	      $(".all_movie_lists").append(cardImgOpen + (imgTagOpen + "http://image.tmdb.org/t/p/w185/" + obj.results[i].poster_path + imgTagClose) + divDClose);
	}

	  $('.card').click(function() {
	      var movieCardIndex = this.getAttribute("value");

	      var title = obj.results[movieCardIndex].title;
	      var poster = 'http://image.tmdb.org/t/p/w185/' + obj.results[movieCardIndex].poster_path;
	      var overview = obj.results[movieCardIndex].overview;
	      var id = obj.results[movieCardIndex].id;
	      var vote = obj.results[movieCardIndex].vote_average;

	      var noResult = "No result";

	      $("#exampleModalLongTitle").html(title);
	      $(".modal-img").attr('src', poster);
	      $(".modal-discription").html(overview);
	      $(".exampleModalLongRating").html(vote);

        function setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires="+d.toUTCString();
            document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";

        }

        $('.get-ticket').click(function(){
          var movieName = title;
          var movieID = id;
          $.get('readMoviesDB', {
     				action : movieName
     			}, function(responseText) {
            setCookie('ShowTime', responseText, 60);
            setCookie('ID', movieID, 60);
            window.location.assign("See_Showtimes.html");
          })
        })

	      videoKey(id);

	      function videoKey(id) {
	        var settings = {
	        "async": true,
	        "crossDomain": true,
	        "url": "https://api.themoviedb.org/3/movie/"+id+"/videos?language=en-US&api_key=f9e1f6d31bef16452f7887267033d960",
	        "method": "GET",
	        "headers": {},
	        "data": "{}"
	        }

	        $.ajax(settings).done(function (response) {
	          var res = "https://www.youtube.com/embed/"+response.results[0].key;
	          $('.embed-responsive-item').attr('src', res);
	        });
	      }
	  })
}

$(document).ready(function() {

  $.get('readMoviesDB',{
    action : 'AllMovies999'
  }, function(responseText) {
      var obj = {};
      obj = JSON.parse(responseText);
      renderImg(obj);
  });

  // ==============search===============
  $( ".nav-btn-search" ).click(function() {

	  $(".all_movie_lists").empty();
	     var searchInput = $( '.nav-input-search' ).val();
	     $.get('moviesSearch', {
				search : searchInput
			}, function(responseText) {
				if (responseText == 101)
				{
					obj = {};
				}
				else
				{
					obj = {};
				    obj = JSON.parse(responseText);
				    renderImg(obj);
				}				
			});
	   })
})
