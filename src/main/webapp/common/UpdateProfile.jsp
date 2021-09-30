<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.vc.model.*"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.vc.dao.*,com.jk.vc.util.DateUtils"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Update Profile | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<script type="text/javascript" src="/js/img-util.js"></script>
	
	<jsp:include page="/oth/ieAndOther.html" />
</head>


<body>
<%-- Checking if user is logged in or not --%>
<c:if test="${empty sessionScope.role}">
	<c:redirect url="/index.jsp" />
</c:if>

<jsp:include page="/oth/header.html"/>

<jsp:include page="/oth/selectMenu.jsp"/>


<main>

<h2>Update Profile</h2>

<jk:status var="status"/>

<div class="page-content">
<%
String name = (String) session.getAttribute("user");
@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");

Profile prs = new ProfileDao().findProfile(name, path);
request.setAttribute("prs", prs);
%>
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<form action="/UpdateAction" name="register" enctype="multipart/form-data" method="POST"><table class="form-grid">
	<tr>
		<td>First Name</td>
		<td><input type="text" name="FIRST_NAME" value="${prs.getFirstname()}" readonly="readonly" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Last Name</td>
		<td><input type="text" name="LAST_NAME" value="${prs.getLastname()}" readonly="readonly" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Birth Date</td>
		<td><input type="text" name="dob" value="${prs.getBdate()}" readonly="readonly" /></td>
		<td></td>
	</tr>

	<tr>
		<td>Browse Photo</td>
		<td><img alt="Photo" id="previewField" src="/userimages/${prs.getPhoto()}" class="profile-img"><br>
			<input type="file" name="photo" class="textfield" onChange="previewPhoto(this, 'previewField')"/>
		</td>
		<td></td>
	</tr>

	<tr>
		<td>Email</td>
		<td><input type="text" name="email" value="${prs.getEmail()}" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Phone No</td>
		<td><input type="text" name="PhoneNo" value="${prs.getPhone()}" size="20" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td colspan="3"><p class="form-part-heading">Permanent Address</p></td>
	</tr>
	
	<tr>
		<td>House No</td>
		<td><input type="text" name="homehouseno" value="${prs.getHno()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Street/Locality</td>
		<td><input type="text" name="homestreet" value="${prs.getStreet()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>City</td>
		<td><input type="text" name="homecity" value="${prs.getCity()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>State</td>
		<td><input type="text" name="homestate" value="${prs.getState()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Country</td>
		<td><input type="text" name="homecountry" value="${prs.getCountry()}"/></td>
		<td></td>
	</tr>

	<tr>
		<td>Pin</td>
		<td><input type="text" name="homepin" value="${prs.getPin()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td colspan="3"><p class="form-part-heading">Current Address</p></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="checkbox" onclick="toggleAddress(this)" name="sameAsPrm"  value="1" class="checkbox" id="tad"/>
		<label for="tad">Same as permanent address</label></td>
		<td id="cbt"></td>
	</tr>
	
	<tr>
		<td>House No</td>
		<td><input type="text" name="personalhouseno" value="${prs.getChno()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Street/Locality</td>
		<td><input type="text" name="personalstreet" value="${prs.getCstreet()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>City</td>
		<td><input type="text" name="personalcity" value="${prs.getCcity()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>State</td>
		<td><input type="text" name="personalstate" value="${prs.getCstate()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Country</td>
		<td><input type="text" name="personalcountry" value="${prs.getCcountry()}"/></td>
		<td></td>
	</tr>

	<tr>
		<td>Pin</td>
		<td><input type="text" name="personalpin" value="${prs.getCpin() }"/></td>
		<td></td>
	</tr>
	
	
	<tr>
		<td></td>
		<td>
			<input type="submit" name="createAccount" value="Update" class="button"/>
		</td>
		<td></td>
	</tr>
</table>

<input type="hidden" name="homeaddresstype" value="home" />
<input type="hidden" name="personaladdresstype" value="personal" />
</form></div>
</div><!-- page-conent -->

<script>
try {
var rules = [
{ name:"photo", display:"Photograph", rules:"is_file_type[jpg,jpeg,png]|callback_check_photo"},
{ name:"PhoneNo", display:"Phone No", rules:"exact_length[10]|numeric"},
{ name:"email", display:"Email", rules:"required|valid_email||max_length[255]"},
{ name:"homehouseno", display:"Current House No", rules:"required|min_length[1]|max_length[30]|!callback_home_addr"},
{ name:"homestreet", display:"Current Street/Locality", rules:"min_length[1]|max_length[30]"},
{ name:"homecity", display:"Current City", rules:"min_length[1]|max_length[30]"},
{ name:"homestate", display:"Current State", rules:"min_length[1]|max_length[30]"},
{ name:"homecountry", display:"Current Country", rules:"min_length[1]|max_length[30]"},
{ name:"homepin", display:"Current PIN", rules:"min_length[1]|max_length[10]|alpha_numeric"},
{ name:"personalhouseno", display:"Permanent House No", rules:"min_length[1]|max_length[30]|!callback_personal_addr"},
{ name:"personalstreet", display:"Permanent Street/Locality", rules:"min_length[1]|max_length[30]"},
{ name:"personalcity", display:"Permanent City", rules:"min_length[1]|max_length[30]"},
{ name:"personalstate", display:"Permanent State", rules:"min_length[1]|max_length[30]"},
{ name:"personalcountry", display:"Permanent Country", rules:"min_length[1]|max_length[30]"},
{ name:"personalpin", display:"Permanent PIN", rules:"min_length[1]|max_length[10]|alpha_numeric"}
];

//console.log("Number of rules: " + rules.length);
function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = document.getElementById("formErrors");
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		document.register.submit();
		return true;
	}
	//console.log(errors.length + "  errors");
	
	var errStr = '<li style="list-style:none;"><strong>Errors</strong></li>';
	
	for (var i = 0; i < errors.length; i++) {
		errStr += '<li>' + errors[i].message + '</li>';
	}
	errStr = '<ul>' + errStr + '</ul>';
	
	errWrapper.innerHTML = errStr;
	errWrapper.focus();
	return false;
}

var validator = new FormValidator('register', rules, handleFormErrors);

validator.registerCallback('check_photo', function(value) {
	var photo = document.getElementById("photo");
	var minSize = 10240;
	var maxSize = 51200;
	
	if (photo && photo.files[0]) {
		var file = photo.files[0];
		
		if (file.size >= minSize || file.size <= maxSize) return true;
		else return false;
	}
	
	return true;
})
.setMessage('check_photo', 'Photograph should be between 10 KiloBytes and 50 KiloBytes.');



validator.registerCallback('home_addr', function() {
	// home addr is required
	var status = false;
	
	with (document.register) {
		status = 
		homehouseno.value.length > 0 && 
		homestreet.value.length > 0 && 
		homecountry.value.length > 0 && 
		homepin.value.length > 0 && 
		homestate.value.length > 0 && 
		homecity.value.length > 0;
	}
	
	return status;	
})
.setMessage('home_addr', 'Fill all fields of <i>Personal Address</i>.');


validator.registerCallback('personal_addr', function() {
	var allFilled;
	var atLeastOneFilled;
	
	with(document.register) {
		allFilled = 
		personalhouseno.value.length > 0 && 
		personalstreet.value.length > 0 && 
		personalcountry.value.length > 0 && 
		personalpin.value.length > 0 && 
		personalstate.value.length > 0 && 
		personalcity.value.length > 0; 
	}

	with(document.register) {
		atLeastOneFilled =  
		personalhouseno.value.length > 0 ||
		personalstreet.value.length > 0 ||
		personalcountry.value.length > 0 || 
		personalpin.value.length > 0 ||
		personalstate.value.length > 0 || 
		personalcity.value.length > 0; 
	}
	/*
	+---------------+-------------------+--------+
	|   allFilled   | atLeastOneFilled  | result |
	+---------------+-------------------+---------
	|      true     |      true         | no problem
	|      true     |      false        | IMPOSSIBLE
	|      false    |      true         | PROBLEM!!!
	|      false    |      false        | no problem
	+---------------+-------------------+---------
	*/
	
	if (!allFilled && atLeastOneFilled) return false;
	else return true;
})
.setMessage('personal_addr', 'Fill all fields of <i>Current Address</i>.');
} catch (e) {alert(e);}

function toggleAddress(cb) {
	var curAddr = ['personalhouseno', 'personalstreet', 'personalcity', 'personalstate', 'personalcountry', 'personalpin'];
	var prmAddr = ['homehouseno', 'homestreet', 'homecity', 'homestate', 'homecountry', 'homepin'];
	var i, prop1, prop2;
	var f = document.getElementsByTagName("form")[0];
	
	//var cbt = document.getElementById("cbt");
	
	if (cb.checked) {
		// fill all current address fields
		for (i = 0; i < prmAddr.length; i++) {
				prop1 = curAddr[i];
				prop2 = prmAddr[i];
				
				//console.log(prop1 + "--" + prop2);
				f.elements.namedItem(prop1).value = f.elements.namedItem(prop2).value;
				f.elements.namedItem(prop1).setAttribute("disabled", "disabled");
		}
	} else {
		for (i = 0; i < prmAddr.length; i++) {
			{
				prop1 = curAddr[i];
				
				f.elements.namedItem(prop1).value = '';
				f.elements.namedItem(prop1).removeAttribute("disabled");
			}
		}
	}
	
}
</script>

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
