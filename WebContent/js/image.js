
var maxWidth=190;

var maxHeight=190;

var fileTypes=["bmp","gif","png","jpg","jpeg"];

var outImage="previewField";
var outImage1="previewField1";
var defaultPic="blank.gif";

function preview(input) {
try {
	var ext = input.value.substring(input.value.lastIndexOf('.')+1).toLowerCase();
	var extNum = fileTypes.length;
	var extValid = false;
	
	console.log("File ext: " + ext);
	for (var i = 0; i < extNum; i++) {
		if (fileTypes[i] === ext) {
			extValid = true;
			break;
		}
	}
	
	if (!extValid) {
		var msg = 'This is not a valid image.\nPlease load an image with one of the following extensions:\n' + fileTypes.join(', ');
		alert(msg);
		return;
	}
	
	if (input.files[0]) {
		/*
		if (input.files[0].size > 60000 || input.files[0].size < 10000) {
			alert("File size should be between 10KB and 50KB.");
			return;
		}*/
		
		var loader = new FileReader();
		loader.onload = function(e) {
			document.getElementById(outImage).src = e.target.result;
		}
		loader.readAsDataURL(input.files[0]);
	}
} catch (e) { console.log(e); }
}


function preview2(what) {
	var source = what.value;
	var ext = source.substring(source.lastIndexOf(".") + 1, source.length).toLowerCase();
	
	console.log(source);

	for (var i = 0; i < fileTypes.length; i++) {
		if (fileTypes[i] == ext) {
			break;
		}
	}
	globalPic = new Image();
	

	if (i < fileTypes.length) {
		globalPic.src = source;
	} else {
		globalPic.src = defaultPic;
		alert("THAT IS NOT A VALID IMAGE\nPlease load an image with an extention of one of the following:\n\n"
				+ fileTypes.join(", "));
	}

	setTimeout("applyChanges()", 200);
}


function preview1(what) {
	var source = what.value;
	var ext = source.substring(source.lastIndexOf(".") + 1, source.length)
			.toLowerCase();
	for (var i = 0; i < fileTypes.length; i++)
		if (fileTypes[i] == ext)
			break;
	globalPic1 = new Image();
	if (i < fileTypes.length)
		globalPic1.src = source;
	else {
		globalPic1.src = defaultPic;
		alert("THAT IS NOT A VALID IMAGE\nPlease load an image with an extention of one of the following:\n\n"
				+ fileTypes.join(", "));
	}
	setTimeout("applyChanges1()", 200);
}

var globalPic1;
function applyChanges1() {
	var field = document.getElementById(outImage1);
	var x = parseInt(globalPic1.width);
	var y = parseInt(globalPic1.height);
	if (x > maxWidth) {
		y *= maxWidth / x;
		x = maxWidth;
	}
	if (y > maxHeight) {
		x *= maxHeight / y;
		y = maxHeight;
	}
	field.style.display = (x < 1 || y < 1) ? "none" : "";
	field.src = globalPic1.src;
	// field.width=x;
	// field.height=y;
}




var globalPic;
function applyChanges() {
	var field = document.getElementById(outImage);
	var x = parseInt(globalPic.width);
	var y = parseInt(globalPic.height);
	if (x > maxWidth) {
		y *= maxWidth / x;
		x = maxWidth;
	}
	if (y > maxHeight) {
		x *= maxHeight / y;
		y = maxHeight;
	}
	field.style.display = (x < 1 || y < 1) ? "none" : "";
	field.src = globalPic.src;
}

