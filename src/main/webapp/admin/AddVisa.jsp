<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.vc.model.*"%> 
<%@ page import="java.util.Enumeration,java.util.List"%>
<%@ page import="com.jk.vc.dao.*,com.jk.vc.util.DateUtils"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add Employee Visa | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<link type="text/css" rel="stylesheet" href="/css/jquery-ui.min.css"/>
	
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/form-util.js"></script>
	
	<jsp:include page="/oth/ieAndOther.html" />
<script type="text/javascript">
function getVisaTypes() {
	initProgress();
	
	if (document.register.COUNTRY_ID.selectedIndex == 0) {
		return;
	}
	
	var COUNTRY_ID = document.register.COUNTRY_ID.value;
	var url = "/GetVisaType";
	
	var xhttp = null;
	
	if (XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} else if(ActiveXML) {
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if (xhttp != null) {
		xhttp.onreadystatechange = function() {
			// request is complete
			if (this.readyState == 4) {
				endProgress();
				// request gets response
				if (this.status == 200) {
					
					var obj = JSON.parse(this.responseText);
					
					if (obj.status == 'ok') {
						addDataToSelect(obj.data);
					} else {
						alert("Request could not be completed. Try again later");
					}
				} else {
					// request failed
					alert("Error occurred while fetching data from server. Try again later.");
				}
			}
			if (this.readyState == 3) {
				beginProgress();
			}
		}
		xhttp.open("POST", url, true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send('COUNTRY_ID=' + COUNTRY_ID);
	}
}

function addDataToSelect(arr) {
	var sel = document.register.visaTypeId;
	
	sel.selectedIndex = -1;
	sel.innerHTML = '<option value="0">Select Visa Type</option>';
	var i;
	
	for(i = 0; i < arr.length; i++) {
		sel.innerHTML += '<option value="' + arr[i].id + '">' 
		+ arr[i].name + ' (' + arr[i].abbr + ')</option>';  
	}
}

$(document).ready(function(){
	getVisaTypes();
});
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
<h2>Add Employee Visa</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>
<%
List<Country> cList = new CountryDAO().findAllCountries();
request.setAttribute("cList", cList);

// checking if supplied passport id exist

int pptId = Integer.parseInt(request.getParameter("pptId"));
PassportDAO pptDb = new PassportDAO();

if (!pptDb.doesPassportExists(pptId)) {
	NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Passport does not exist.");
	request.getSession().setAttribute("status", noti);
	
	response.sendRedirect("/index.jsp");
	return;
}
%>

<form action="/AddVisaAction" method="post" name="register" id="register">
<input type="hidden" readonly="readonly" name="passportId" value="<%= pptId%>"/>
<table class="form-grid">
	<tr>
		<td>Country<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="COUNTRY_ID" onchange="getVisaTypes()">
			<option value="0">Select Country</option>
			<c:forEach var="country" items="${cList}">
				<option value="${country.getCountryID()}">${country.getCountryName()}</option>
			</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Select Visa Type<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="visaTypeId">
			<option value="0">Select Visa Type</option>			
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Visa number<span class="mnd">*</span></td>
		<td><input type="text" name="visaNo" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Date of Issue<span class="mnd">*</span></td>
		<td><input type="text" name="issueDate" value="" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Date of Expire<span class="mnd">*</span></td>
		<td><input type="text" name="expireDate" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Maximum&nbsp;Visits&nbsp;Allowed<span class="mnd">*</span></td>
		<td><input type="text" name="maxVisits" value=""/></td>
		<td>Enter M for multiple visits or number specified on visa</td>
	</tr>
	
	<tr>
		<td>Number of visits<span class="mnd">*</span></td>
		<td><input type="text" name="visitCount" value="0"/></td>
		<td>If visa has already been used and does not allow any number of visits</td>
	</tr>
	
	<tr>
		<td>Place of Issue<span class="mnd">*</span></td>
		<td><input type="text" name="placeIssued" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Visa Cost<span class="mnd">*</span></td>
		<td><input type="text" name="cost" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Comments</td>
		<td><textarea name="comments" cols="" rows=""></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="submit" value="Add Visa" class="button"/></td>
		<td></td>
	</tr>
	
	
</table></form>
</div>
</div><!-- page-conent -->

<script type="text/javascript">

try {

$(document).ready(function(){
	var myFmt = { dateFormat:'dd-mm-yy', changeYear:true };
	
	$('[name="issueDate"]').datepicker(myFmt);
	$('[name="expireDate"]').datepicker(myFmt);
	
	$('[name="issueDate"]').keypress(preventKbInput);
	$('[name="expireDate"]').keypress(preventKbInput);
	
	function preventKbInput(event){
		event.preventDefault();
	}
});

/*
var fields = [
	{name:'COUNTRY_ID',   display:'Country', rules:'required|callback_check_select'},
	{name:'visaTypeId',  display:'Visa Type', rules:'required|callback_check_select'},
	{name:'visaNo',      display:'Visa Number', rules:'required|max_length[20]|alpha_numeric'},
	{name:'issueDate',   display:'Date of Issue', rules:'required'},
	{name:'expireDate',  display:'Date of Expire', rules:'required'},
	{name:'maxVisits',   display:'Maximum Visits Allowed', rules:'required|callback_check_maxvisits'},
	{name:'visitCount',  display:'Number of visits', rules:'required|numeric'},
	{name:'placeIssued', display:'Place of Issue', rules:'required|max_length[50]'},
	{name:'comments',    display:'Comments', rules:'max_length[200]'}
];

function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = document.getElementById("formErrors");
	
	console.log(errors.length);
	
	if (errors.length === 0) {
		errWrapper.innerHTML = '';
		console.log('form submitted');
		var myForm = document.getElementById("register"); 
		myForm.submit();
		return true;
	}
	
	var errStr = '<li style="list-style:none;"><strong>Errors</strong></li>';
	
	for (var i = 0; i < errors.length; i++) {
		errStr += '<li>' + errors[i].message + '</li>';
	}
	errStr = '<ul>' + errStr + '</ul>';
	
	errWrapper.innerHTML = errStr;
	errWrapper.focus();
	return false;
}

var validator = new FormValidator('register', fields, handleFormErrors);

validator.registerCallback('check_maxvisits', function(value){
	return 'M' === value || (!isNaN(value) && parseInt(value) > 0);
})
.setMessage('check_maxvisits', 'The <i>%s</i> field should contain either M or a positive number.');

validator.registerCallback('check_select', function(value){
	return value != '0';
})
.setMessage('check_select', 'The <i>%s</i> field is required.');
*/
} catch (e) {console.log(e.stackTrace());}
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
