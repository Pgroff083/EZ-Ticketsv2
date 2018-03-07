$(document).ready(function()  {
  var output1;
  var output2;

  $(".submit_btn").click(function() {
    var str1 = $(".email").val();
    var str2 = $(".email_confirm").val();

    if (str1 === str2) {
      // alert("Email verified");
      output1 = true;
    }
  });

  $(".submit_btn").click(function() {
    var str3 = $(".pwd").val();
    var str4 = $(".pwd_confirm").val();

    if (str3 === str4) {
      // alert("Password verified");
      output2 = true;
    }
  });

  $(".submit_btn").click(function() {
    if (output1 == true && output2 == true) {
      alert("Registration Successful!");
      // Link back to homepage here
    }
  });

  $(".final-login-btn").click(function() {
    $(".after-login-loginbtn").replaceWith('<i class="material-icons user_login_icon">account_box</i>');
    $(".after-login-signupbtn").replaceWith("");
  })
})
