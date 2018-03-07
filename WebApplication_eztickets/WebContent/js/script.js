$(document).ready(function() {
  
	// The next line is a call to the ratings.js file that handles the rating system
	var r = new SimpleStarRating(document.getElementById('rating_1'));
	
  $(".ratting-star").click(function() {
    this.replaceWith('star');
  });

  $(".final-login-btn").click(function() {
    $(".after-login-loginbtn").replaceWith('<i class="material-icons">account_box</i>');
    $(".after-login-signupbtn").replaceWith("");
  })
})
