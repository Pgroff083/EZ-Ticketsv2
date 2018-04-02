function login_in_fun(check) {
  if (check == 100) {
    $(".profile-dropdown").hide();
    $(".nav-signup-button").show();
    $(".nav-login-button").show();
  } else if(check == 200) {
    $(".profile-dropdown").show();
    $(".nav-login-button").hide();
    $(".nav-signup-button").hide();
  }
}

function logout_fun(check) {
  if (check == 300) {
    $(".profile-dropdown").hide();
    $(".nav-signup-button").show();
    $(".nav-login-button").show();
  }
}


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

    $('.btn-below-card').click(function() {
      var movieIndex = this.value;
      console.log(movieIndex);
      $('.modal-title').text(movies_albums[movieIndex].title);
      $('.modal-discription').text(movies_albums[movieIndex].overview);
      $('.modal-img').attr('src', "http://image.tmdb.org/t/p/w185/" + movies_albums[movieIndex].poster_path);
    });

  });

// ================== poster card update ==================

// ================== movie detail pop up update ==================


// ================== movie detail pop up update ==================







  var globle_loginstatus = 100;
  console.log(globle_loginstatus);
  $(".profile-dropdown").hide();

  $('#btnSubmit').click(function() {

    // $.get('MyServlet', {
    //   emailAddress: emailAddressValue,
    //   password: passwordValue
    // }, function(responseText) {
    //   $('.login-btn').replaceWith()；
    // });

    var emailAddressValue = $('#emailAddress').val();
    var passwordValue = $('#passwordValue').val();

    console.log(emailAddressValue);
    console.log(passwordValue);

    if (emailAddressValue == "123" && passwordValue == "123") {
      globle_loginstatus = 200;
      console.log(globle_loginstatus);
      login_in_fun(globle_loginstatus);
    } else {
      globle_loginstatus = 100;
      console.log(globle_loginstatus);
      login_in_fun(globle_loginstatus);
    }
  })

  $('#logout_button').click(function() {
    globle_loginstatus = 300;
    logout_fun(globle_loginstatus);
  })


})
