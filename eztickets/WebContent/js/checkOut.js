$(document).ready(function() {
	$("#btnCheckOutSubmit").click(function(){
		var firstNameValue = $("#firstName").val();
		var lastNameValue = $("#lastName").val();
		var emailValue = $("#email").val();
		var emailValue2 = $("#email_confirm").val();
		if(emailValue == emailValue2)
		{
			 $.get('checkOut', {					 
				 firstName : firstNameValue,
				 lastName : lastNameValue,
				 email :	emailValue
	 			}, function(responseText) {
	        if(responseText == 'successful')
	        {
	        	alert("Checkout successfully. Please see your email.");
	        	window.location.assign("index.html")
	        }
	        else
	        {
	        	alert(responseText);
	        }
	      })
		}
		else
		{
			$('#failMessage').text("Emails DO NOT MATCH. Plaese enter again!!");
		}
	 })
})
