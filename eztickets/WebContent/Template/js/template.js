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
