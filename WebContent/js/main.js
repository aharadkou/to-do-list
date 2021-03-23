
$(document).ready(function() { 
    $("[data-toggle=confirmation]").confirmation({
  	  rootSelector: "[data-toggle=confirmation]",
  	}); 
    
    $("#clearBin").confirmation({
	  rootSelector: "#clearBin" ,
	  popout: true ,
	  singleton:true ,
	  onConfirm: function() {
		$("#checkAll").prop("checked", true);
			checkAll();
			var $form = $("#clearBin").parent();
			$form.submit();			  
	  }
	});
} );


function checkAll() {
	var flag = $("#checkAll").prop("checked");
	$("input[type='checkbox']").prop("checked", flag);
}


function submitOperationForm(form) {
	var checkInputs = $(".inputCheck");
	var count = 0;
	for(var i = 0; i < checkInputs.length; i++) {
		if($(checkInputs[i]).prop("checked")) {
			count++;
		}
	}	
	if(count == 0) {
		$("#error").text("No task selected!");
		return;
	}
	for(var i = 0; i < checkInputs.length; i++) {
		var $elem = $(checkInputs[i]).clone();
		$elem.appendTo(form);
		$elem.hide();
	}
	form.submit();
}

function submitDownload(ref) {
	$ref = $(ref);
	$ref.prev().submit();
}

function checkRegistrationForm() {
	var regForm = document.forms.registrationForm;
	if(regForm.login.value.trim().length < 4) {
		$("#error").text("Login must be at least 4 characters long!");
		return;		
	}
	if(regForm.password.value.trim().length < 6) {
		$("#error").text("Password must be at least 6 characters long!");
		return;		
	}		
	if(regForm.password.value.trim() != regForm.repeatPassword.value.trim()) {
		$("#error").text("Passwords don't match!");
		regForm.password.value = "";
		regForm.repeatPassword.value = "";
		return;			
	}
	regForm.submit();
}
