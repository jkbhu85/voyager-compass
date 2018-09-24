var _g = new Object();

function checkUsername(field, resultFieldId) {
	var resultField = document.getElementById(resultFieldId);
	var usernamePat = /[a-zA-Z][a-zA-Z0-9_\-\.]*/;
	
	initProgress();
	
	if (field.value.length < 6 || field.value.length > 30) {
		alert("Login ID must be at least 6 characters");
		setUsernameParams('reset', resultField);
		return;
	}
	
	if (field.value.search(usernamePat) != 0) {
		alert("First character of Login ID must be an alphabet, rest can be a-z,A-Z,0-9,underscore(_), dash(-), and dot(.).");
		setUsernameParams('reset', resultField);
		return;
	}
	
	var xhttp = null;
	
	if (window.XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} else {
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if (xhttp != null) {
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4) {
				endProgress();
				if (this.status == 200) {
					setUsernameParams(this.responseText, resultField);
					//console.log("success:: response: " + this.responseText);
				} else {
					alert("Error occurred while checking username. Try again later.");
					// console.log("failure:: status: " + this.statusText);
				}
			}
			
			if (this.readyState == 3) {
				beginProgress();
			}
		};
		
		xhttp.open("POST", "/CheckUsername", true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send('username=' + field.value);
	} else {
		alert('Registration can not be performed using your browser. ' 
				+ 'User a modern browser (Mozilla Firefox, Google Chrome etc).');
	}
}
 
var _prg = new Object();

function initProgress() {
	_prg.progress = false;
	_prg.wrapper = null;
	_prg.dialog = null;
}

function beginProgress() {
	_prg.wrapper = document.createElement("div");
	with(_prg.wrapper.style) {
		position = 'fixed';
		background = 'rgba(0,0,0,0.7)';
	}
	
	_prg.dialog = document.createElement('div');
	with (_prg.dialog.style) {
		position   = "absolute";
		textAlign  = 'center';
		background = '#f7f7f7';
		borderRadius = "4px";
	}
	_prg.dialog.innerHTML = "Please wait...";
	
	_prg.wrapper.appendChild(_prg.dialog);

	document.getElementsByTagName("body")[0].appendChild(_prg.wrapper);
	_prg.showing = true;
	setProgressSize();
}

function setProgressSize() {
	if (!_prg.showing) return;
	
	w = 300;
	h = 120;
	
	wndWidth = $(window).width();
	wndHeight = $(window).height();
	
	//console.log(wndWidth + "::" + wndHeight);
	
	l = (wndWidth-w) / 2;
	t = (wndHeight-h) / 2;
	
	with (_prg.wrapper.style) {
		width    = wndWidth + 'px';
		height   = wndHeight + 'px';
		top      = '0px';
		left     = '0px';
	}

	with (_prg.dialog.style) {
		left       = l + 'px';
		top        = t + 'px';
		width      = w + "px";
		height     = h + "px";
		lineHeight = h + "px";
	}
}

function endProgress() {
	//_prg.timeout = window.setTimeout(endProgress2, 0);
	endProgress2();
}

function endProgress2() {
	if (_prg.showing) {
		document.getElementsByTagName("body")[0].removeChild(_prg.wrapper);
		_prg.showing = false;
		
		if(_prg.timeout) window.clearTimeout(_prg.timeout);
	}
}

function setUsernameParams(status, resultField) {
	switch (status) {
	case 'fail':
		resultField.innerHTML = '<span class="red emph">Already taken</span>';
		_g.unStatus = '';
		break;
	case 'ok':
		resultField.innerHTML = '<span class="green emph">Available</span>';
		_g.unStatus = 'ok';
		break;
	default:
		resultField.innerHTML = '';
		_g.unStatus = '';
	}
}

$(document).ready(function(){
	$(window).resize(function(){
		setProgressSize();
	});
});
