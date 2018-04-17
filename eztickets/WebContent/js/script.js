$(document).ready(function()  {

// ================== poster card update ==================

  var settingsNew = {
    "async": true,
    "crossDomain": true,
    // "url": "https://api.themoviedb.org/3/movie/337167?language=en-US&api_key=f9e1f6d31bef16452f7887267033d960",
    "url": "https://api.themoviedb.org/3/movie/now_playing?api_key=f9e1f6d31bef16452f7887267033d960&language=en-US&page=1",
    "method": "GET",
    "headers": {},
    "data": "{}"
  }

  $.ajax(settingsNew).done(function (response) {

    var movies_albums = response.results;
    console.log(movies_albums);

    // ---------- discription ----------
        $('.discription_pic').attr('src', "http://image.tmdb.org/t/p/w185/" + movies_albums[12].poster_path)
        $('.discription-head').append(movies_albums[12].title);
        $('.discription-paragraph').append(movies_albums[12].overview);
    // ---------- discription ----------


    $('.cardimg-custom').each(function (index) {
      $(this).attr('src', "http://image.tmdb.org/t/p/w185/" + movies_albums[index].poster_path);
    });

    $('.release_time').each(function (index) {
      $(this).append(movies_albums[index].release_date);
    });

    $('.movie-card-title').each(function (index) {
      $(this).append(movies_albums[index].title);
    });

    $('.movie_rating').each(function (index) {
      $(this).append(movies_albums[index].vote_average);
    });

    // ================== movie detail pop up update ==================
    $('.btn-below-card').click(function() {
      var movieIndex = this.value;
      videoKey(movies_albums[movieIndex].id);

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

      $('.modal-title').text(movies_albums[movieIndex].title);
      $('.modal-discription').text(movies_albums[movieIndex].overview);
      $('.modal-img').attr('src', "http://image.tmdb.org/t/p/w185/" + movies_albums[movieIndex].poster_path);
    });
    // ================== movie detail pop up update ==================

  });

  $( ".nav-btn-search" ).click(function() {
    var searchInput = $( '.nav-input-search' ).val();
    console.log(searchInput);
  });

})
