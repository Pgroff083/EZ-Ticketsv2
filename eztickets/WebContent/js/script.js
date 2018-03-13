$(document).ready(function() {

  $(".ratting-star").click(function() {
    this.replaceWith('star');
  });

  $(".final-login-btn").click(function() {
    $(".after-login-loginbtn").replaceWith('<i class="material-icons">account_box</i>');
    $(".after-login-signupbtn").replaceWith("");
  })
})
