function previewPhoto(photo, previewField) {
	
	if (previewField === undefined) return;
	
	var imgPrv = document.getElementById(previewField);
	
	if (photo && photo.files[0] && FileReader) {
		var reader = new FileReader();
		
		reader.onload = function(e){
			imgPrv.src = e.target.result;
			imgPrv.alt = "";
		}
		
		reader.readAsDataURL(photo.files[0]);
	} else {
		imgPrv.alt = "Image preview not available";
	}
	
} // previewPhoto