<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.vc.model.*,java.util.List"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.vc.dao.*,com.jk.vc.util.DateUtils"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Registration | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<link type="text/css" rel="stylesheet" href="/css/jquery-ui.min.css"/>
	
	<jsp:include page="/oth/ieAndOther.html" />

	<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/validate.min.js"></script>	
	<script type="text/javascript" src="/js/img-util.js"></script>
	<script type="text/javascript" src="/js/form-util.js"></script>
	
	<script type="text/javascript">
	_g.unStatus = '';
	</script>
</head>

<body>
<%-- Checking if user is logged in or not --%>
<c:if test="${empty sessionScope.role}">
	<c:redirect url="/index.jsp" />
</c:if>

<jsp:include page="/oth/header.html"/>

<jsp:include page="/oth/selectMenu.jsp"/>


<main>

<h2>Employee Registration</h2>

<jk:status var="status"/>

<div class="page-content">

<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<form action="/Register" method="post" name="register" id="register" enctype="multipart/form-data">
<table class="form-grid">
<%
List<Department> dList = new DepartmentDao().findAllDepartments();
request.setAttribute("dList", dList);
%>
<tr>
	<td colspan="3"><div class="form-part-heading">Account Details</div></td>
</tr>

<tr>
	<td>Username</td>
	<td>
		<input type="text" name="userName" value="" onBlur="checkUsername(this, 'uidAval')"/>
	</td>
	<td><span id="uidAval"></span></td>
</tr>

<tr>
	<td>Password</td>
	<td><input type="password" name="password"></td>
	<td></td>
</tr>

<tr>
	<td>Confirm password</td>
	<td><input type="password" name="conformpassword" value="" /></td>
	<td></td>
</tr>

<tr>
	<td>Security Question</td>
	<td><span class="select-wrapper"><select name="squest">
		<jsp:include page="/oth/securityQuestions.html"/>
	</select><span class="select-arrow"></span></span></td>
	<td></td>
</tr>
	<tr>
		<td>Security Answer</td>
		<td><input type="text" name="secrete" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Designation</td>
		<td><span class="select-wrapper"><select name="Desingation">
				<option value="0">Select Designation</option>
				<option value="admin">Administrator</option>
				<option value="employee">Employee</option>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
<tr>
	<td>Department</td>
	<td><span class="select-wrapper"><select name="DEPARTMENT_ID">
		<option value="0">Select Department</option>
	<c:forEach var="dept" items="${dList }">
		<option value="${dept.getDepartmentID()}">${dept.getDepartmentName()}</option>
	</c:forEach>
	</select><span class="select-arrow"></span></span></td>
</tr>

<tr>
	<td colspan="3"><div class="form-part-heading">Personal Details</div></td>
</tr>

<tr>
	<td>First Name</td>
	<td><input type="text" name="FIRST_NAME" value=""></td>
	<td></td>
</tr>

<tr>
	<td>Last Name</td>
	<td><input type="text" name="LAST_NAME" value=""/></td>
	<td></td>
</tr>

<tr>
	<td>Date of Birth</td>
	<td>
		<input type="text" name="dob" value="" />
	</td>
	<td></td>
</tr>

<tr>
	<td>Photograph</td>
	<td>
		<img alt="Photo" id="previewField" src="/img/default-profile-img.jpg"
			 height="150" width="120" class="profile-img"><br>
		<input type="file" name="photo" id="photo" class="textfield" 
		onchange="previewPhoto(this, 'previewField')"/>
	</td>
	<td></td>
</tr>

<tr>
	<td>Gender</td>
	<td><span class="select-wrapper"><select name="gender">
			<option value="0">Select Gender</option>
			<option value="Male">Male</option>
			<option value="Female">Female</option>
			<option value="Other">Other</option>
	</select><span class="select-arrow"></span></span></td>
	<td></td>
</tr>

<tr>
	<td colspan="3"><div class="form-part-heading">Contact Details</div></td>
</tr>

<tr>
	<td>Mobile</td>
	<td>
		<input type="text" name="PhoneNo" value="" id="PhoneNo">
		<label id="msgPhoneNo" style="color: red; font-size: 16px; font-style: italic;"></label>
		</td>
		<td></td>
	</tr>

	<tr>
		<td>Email</td>
		<td><input type="text" name="email" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td colspan="3"><div class="form-part-heading">Permanent Address</div></td>
	</tr>
	
	<tr>
		<td>House No</td>
		<td><input type="text" name="homehouseno" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Street/Locality</td>
		<td><input type="text" name="homestreet" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>City</td>
		<td><input type="text" name="homecity" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>State</td>
		<td><input type="text" name="homestate" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Country</td>
		<td><input type="text" name="homecountry" value=""/></td>
		<td></td>
	</tr>

	<tr>
		<td>Pin</td>
		<td><input type="text" name="homepin" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td colspan="3"><div class="form-part-heading">Current Address</div></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="checkbox" onclick="toggleAddress(this)" name="sameAsPrm" value="1" class="checkbox" id="tad"/>
		<label for="tad">Same as permanent address</label></td>
		<td id="cbt"></td>
	</tr>

	
	<tr>
		<td>House No</td>
		<td><input type="text" name="personalhouseno" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Street/Locality</td>
		<td><input type="text" name="personalstreet" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>City</td>
		<td><input type="text" name="personalcity" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>State</td>
		<td><input type="text" name="personalstate" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Country</td>
		<td><input type="text" name="personalcountry" value=""/></td>
		<td></td>
	</tr>

	<tr>
		<td>Pin</td>
		<td><input type="text" name="personalpin" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td>
			<input type="submit" value="Register" class="button"/>
			<input type="reset" name="cancel" value="Cancel" class="button"/>
		</td>
		<td></td>
	</tr>
</table>

<input type="hidden" name="homeaddresstype" value="home"/>
<input type="hidden" name="personaladdresstype" value="personal"/>
<input type="hidden" name="officeaddresstype" value=""/>

<input type="hidden" name="officehouseno"/>
<input type="hidden" name="officestreet"/>
<input type="hidden" name="officecountry"/>
<input type="hidden" name="officepin"/>
<input type="hidden" name="officestate" value=""/>
<input type="hidden" name="officecity"/>
<input type="hidden" name="ownquest" disabled="disabled"/>
<input type="hidden" name="ch" value=""/>

</form>
</div> <!-- form-wrapper -->
</div><!-- page-conent -->
	
<script>
function toggleAddress(cb) {
	var curAddr = ['personalhouseno', 'personalstreet', 'personalcity', 'personalstate', 'personalcountry', 'personalpin'];
	var prmAddr = ['homehouseno', 'homestreet', 'homecity', 'homestate', 'homecountry', 'homepin'];
	var i, prop1, prop2;
	var f = document.getElementById("register");
	
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

<script>

$(document).ready(function(){
	var today = new Date();
	var curYear = today.getFullYear();
	var minYear = curYear - 100;
	var yRange = minYear + ":" + curYear;
	
	var myFmt = {
		dateFormat:'dd-mm-yy',
		changeYear:true,
		yearRange:yRange
	};
	$('[name="dob"]').datepicker(myFmt);
	
	$('[name="dob"]').keypress(preventKbInput);
	
	function preventKbInput(event){
		event.preventDefault();
	}
});

var rules = [
{ name:"userName", display:"Username", rules:"required|min_length[6]|max_length[30]|callback_check_username"},
{ name:"password", display:"Password", rules:"required|min_length[6]|max_length[30]" },
{ name:"conformpassword", display:"Confirm Password", rules:"required|matches[password]"},
{ name:"squest", display:"Security Question", rules:"required|callback_check_select"},
{ name:"secrete", display:"Security Answer", rules:"required|min_length[5]|max_length[70]"},
{ name:"Desingation", display:"Designation", rules:"required|callback_check_select"},
{ name:"DEPARTMENT_ID", display:"Department", rules:"required|callback_check_select"},
{ name:"FIRST_NAME", display:"First Name", rules:"required|max_length[20]"},
{ name:"LAST_NAME", display:"Last Name", rules:"required|max_length[20]"},
{ name:"dob", display:"Date of Birth", rules:"required"},
{ name:"photo", display:"Photograph", rules:"required|is_file_type[jpg,jpeg,png]|callback_check_photo"},
{ name:"gender", display:"Gender", rules:"required|callback_check_select"},
{ name:"passport", display:"Passport Obtained", rules:"required"},
{ name:"PhoneNo", display:"Phone No", rules:"required|exact_length[10]|numeric"},
{ name:"email", display:"Email", rules:"required|valid_email|max_length[255]"},
{ name:"homehouseno", display:"House No", rules:"required|min_length[1]|max_length[30]|callback_home_addr"},
{ name:"homestreet", display:"Street/Locality", rules:"min_length[1]|max_length[30]"},
{ name:"homecity", display:"City", rules:"min_length[1]|max_length[30]"},
{ name:"homestate", display:"State", rules:"min_length[1]|max_length[30]"},
{ name:"homecountry", display:"Country", rules:"min_length[1]|max_length[30]"},
{ name:"homepin", display:"PIN", rules:"min_length[1]|max_length[10]|alpha_numeric"},
{ name:"personalhouseno", display:"House No", rules:"min_length[1]|max_length[30]|!callback_personal_addr"},
{ name:"personalstreet", display:"Street/Locality", rules:"min_length[1]|max_length[30]"},
{ name:"personalcity", display:"City", rules:"min_length[1]|max_length[30]"},
{ name:"personalstate", display:"State", rules:"min_length[1]|max_length[30]"},
{ name:"personalcountry", display:"Country", rules:"min_length[1]|max_length[30]"},
{ name:"personalpin", display:"PIN", rules:"min_length[1]|max_length[10]|alpha_numeric"}
];

//console.log("Number of rules: " + rules.length);
function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = $("#formErrors")[0];
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		//console.log('no errors');
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


validator.registerCallback('check_secques', function(){
	return ((document.register.ch.value == '1' && document.register.ownquest.value.length > 0) 
		 || (document.register.ch.value == '0' && document.register.squest.selectedIndex > 0));
})
.setMessage('check_secques', 'The <i>%s</i> field is required.');


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


validator.registerCallback('check_username', function(value) {
	var usernamePat = /[a-zA-Z][a-zA-Z0-9_\-\.]*/;
	
	if (value == undefined || value == null) return false;
	
	//console.log('value: ' + value + "-- Match: " + value.search(usernamePat) == 0);
	
	return value.search(usernamePat) == 0;
})
.setMessage('check_username',
		'First character of <i>Username</i> should be a letter and '
		+ 'it can contain only letters, numbers, underscore(_), hyphen(-) and dot(.).');
		
validator.registerCallback('check_select', function(value){
	return value != "0";
})
.setMessage('check_select', "The <i>%s</i> field is required.");


</script>
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
