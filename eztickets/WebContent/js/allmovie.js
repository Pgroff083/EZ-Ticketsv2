// var obj = JSON.parse('{"original_language":"en","imdb_id":"tt1825683","video":false,"title":"Black Panther","backdrop_path":"\/b6ZJZHUdMEFECvGiDpJjlfUWela.jpg","revenue":1300189694,"genres":[{"name":"Action","id":28},{"name":"Adventure","id":12},{"name":"Fantasy","id":14},{"name":"Science Fiction","id":878}],"popularity":172.15788,"production_countries":[{"iso_3166_1":"US","name":"United States of America"}],"id":284054,"vote_count":4112,"budget":200000000,"overview":"King T\'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country\'s new leader. However, T\'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T\'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan \\\"special forces\\\"), and an American secret agent, to prevent Wakanda from being dragged into a world war.","original_title":"Black Panther","runtime":134,"poster_path":"\/uxzzxijgPIY7slzFvMotPv8wjKA.jpg","spoken_languages":[{"name":"English","iso_639_1":"en"},{"name":"???\/???","iso_639_1":"ko"},{"name":"Kiswahili","iso_639_1":"sw"},{"name":"","iso_639_1":"xh"}],"production_companies":[{"logo_path":"\/hUzeosd33nzE5MCNsZxCGEKTXaQ.png","name":"Marvel Studios","id":420,"origin_country":"US"},{"logo_path":"\/4MbjW4f9bu6LvlDmyIvfyuT3boj.png","name":"Walt Disney Pictures","id":2,"origin_country":"US"}],"release_date":"2018-02-13","vote_average":7.3,"belongs_to_collection":null,"tagline":"Long live the king","adult":false,"homepage":"https:\/\/marvel.com\/movies\/movie\/224\/black_panther","status":"Released"}');
// var obj = JSON.parse('{"results":[{"original_language":"en","imdb_id":"tt1825683","video":false,"title":"Black Panther","backdrop_path":"\/b6ZJZHUdMEFECvGiDpJjlfUWela.jpg","revenue":1300189694,"genres":[{"name":"Action","id":28},{"name":"Adventure","id":12},{"name":"Fantasy","id":14},{"name":"Science Fiction","id":878}],"popularity":172.15788,"production_countries":[{"iso_3166_1":"US","name":"United States of America"}],"id":284054,"vote_count":4112,"budget":200000000,"overview":"King T\'Challa returns home from America to the reclusive, technologically advanced African nation of Wakanda to serve as his country\'s new leader. However, T\'Challa soon finds that he is challenged for the throne by factions within his own country as well as without. Using powers reserved to Wakandan kings, T\'Challa assumes the Black Panther mantel to join with girlfriend Nakia, the queen-mother, his princess-kid sister, members of the Dora Milaje (the Wakandan \\\"special forces\\\"), and an American secret agent, to prevent Wakanda from being dragged into a world war.","original_title":"Black Panther","runtime":134,"poster_path":"\/uxzzxijgPIY7slzFvMotPv8wjKA.jpg","spoken_languages":[{"name":"English","iso_639_1":"en"},{"name":"???\/???","iso_639_1":"ko"},{"name":"Kiswahili","iso_639_1":"sw"},{"name":"","iso_639_1":"xh"}],"production_companies":[{"logo_path":"\/hUzeosd33nzE5MCNsZxCGEKTXaQ.png","name":"Marvel Studios","id":420,"origin_country":"US"},{"logo_path":"\/4MbjW4f9bu6LvlDmyIvfyuT3boj.png","name":"Walt Disney Pictures","id":2,"origin_country":"US"}],"release_date":"2018-02-13","vote_average":7.3,"belongs_to_collection":null,"tagline":"Long live the king","adult":false,"homepage":"https:\/\/marvel.com\/movies\/movie\/224\/black_panther","status":"Released"}]}');
// var obj = JSON.parse('');


// TWO CONDITION:
// [1]. json object from backend is ALL MOVIE OBJ (default)
// [2]. json object from backend is SEARCH RESULT


$(document).ready(function() {

	var obj={};
  // ==============search===============  
  $( ".nav-btn-search" ).click(function() {
	  $(".all_movie_lists").empty();
	     var searchInput = $( '.nav-input-search' ).val();
	     $.get('moviesSearch', {
				search : searchInput
			}, function(responseText) {
//				console.log(responseText);
				obj = {};
			    obj = JSON.parse(responseText);

			  var imgTagsrc = obj.results;
			  //console.log(responseText);
			  console.log(imgTagsrc);
			  var pOpen = '<p class="card-body-p"></p>';
			  var i;
			  for (i = 0; i < imgTagsrc.length; i++) {
			
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

			});
	   })
})
