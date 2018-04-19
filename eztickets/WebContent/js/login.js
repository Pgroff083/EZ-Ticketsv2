

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
    console.log("Cookie2.1: " + getCookie("userEmail"));
}

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function checkCookie() {
    var user = getCookie("userEmail");
    if (user != "") {
    	login_in_fun(200);
    }
}

function login_in_fun(check) {
	if (check == 100) { //login fail
		alert("Email or password is invalid. Please enter again.");
	} else if (check == 200) { //login successful
	    var profileString = '<div class="profile-dropdown"><img class="dropdown-toggle nav-profile-img" id="dropdownMenuButton-profile" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" src="graphics/cardimg.jpg" alt="no pic!"><ul class="dropdown-menu" role="menu"><li><a class="dropdown-item" href="#">Profile</a></li><li><a class="dropdown-item" href="#">Ticktes</a></li><li><a class="dropdown-item" id="logout_button" href="#">Log out</a></li></ul></div>';
	    $(".wheredropdowis").append(profileString);
	    var loginBB = '<button class="dropdown-item" href="AllMovie.html">Log Out</button>';
	    $(".logout_button").append(loginBB);
		$(".nav-login-button").hide();
		$(".nav-signup-button").hide();
	} else {
		alert("system error");
		$('#failMessage').text("system error");
	}
}

function logout_fun(check) {
	if (check == 300) { //
    var profileString = '';
    $(".wheredropdowis").append(profileString);
		$(".nav-signup-button").show();
		$(".nav-login-button").show();
	}
}

$(document).ready(function() {

	$('#btnSubmit').click(function() {
		var emailAddressValue = $('#emailAddress').val();
		var passwordValue = $('#passwordValue').val();
		 $.get('login', {
		 	email : emailAddressValue,
		 	password : passwordValue
		 }, function(responseText) {
		 	if (responseText == emailAddressValue) { //login successful
		 		setCookie("userEmail", responseText, 60);
		 		login_in_fun(200);
		 	} else {
		 		login_in_fun(responseText);
		 	}
		 });

	})

	$('.logout_button').click(function() {
    console.log("logout");
		setCookie("userEmail", "", 60);
		globle_loginstatus = 300;
		logout_fun(globle_loginstatus);
	})

})
