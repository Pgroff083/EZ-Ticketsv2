$(document).ready(function()  {

  $("#btnSignUpSubmit").click(function() {	  
    var firstNameValue = $("#firstName").val();
    var lastNameValue = $("#lastName").val();
    var loginIDValue = $("#username").val();
    var emailValue = $("#email").val();
    var passwordValue = $("#Password").val();
    var emailValue2 = $("#email_confirm").val();
    var passwordValue2 = $("#Password_confirm").val();        
    
    if (passwordValue == passwordValue2 && emailValue == emailValue2)
    {
    	$.get('registration', {
        	firstName : firstNameValue,
        	lastName : lastNameValue,
        	loginID : loginIDValue,
        	email :	emailValue,
        	password : passwordValue
    	 }, function(responseText) {
    	 	if (responseText == 300) { //login successful
    	 		$('#failMessage').text("Email has been used. Plaese enter again!!");
    	 	} 
    	 	else if (responseText == 400)
    	 	{
    	 		window.location.assign("index.html")
    	 	} else {
    	 		$('#failMessage').text("System Error");
    	 		alert("system error");
    	 	}
    	 });    	
    }
    else
    {
    	$('#failMessage').text("Emails or Passwords DO NOT MATCH. Plaese enter again!!");
    }    
  })
})

function checkPass()
{
    //Store the password field objects into variables ...
    var pass1 = document.getElementById('Password');
    var pass2 = document.getElementById('Password_confirm');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage2');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(pass1.value == pass2.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Passwords Match!"
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Passwords Do Not Match!"
    }
}

function checkMail()
{
    //Store the password field objects into variables ...
    var mail1 = document.getElementById('email');
    var mail2 = document.getElementById('email_confirm');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage1');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(mail1.value == mail2.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        mail2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Emails Match!"
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        mail2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "Emails Do Not Match!"
    }
}

