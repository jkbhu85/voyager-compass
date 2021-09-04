<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.travel.model.*"%> 
<%@ page import="java.util.Enumeration,java.util.List"%>
<%@ page import="com.jk.travel.dao.*,com.jk.core.util.DateWrapper"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add Employee Passport | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<link type="text/css" rel="stylesheet" href="/css/jquery-ui.min.css"/>
	
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/form-util.js"></script>
	
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
<h2>Add Employee Passport</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>
<%
List<Country> cList = new CountryDAO().getCountryList();
request.setAttribute("cList", cList);

String eId = request.getParameter("empId");
Integer empId = Integer.parseInt(eId);

ProfileDAO profileDb = new ProfileDAO();
String loginId = profileDb.getUserID(empId);

if (loginId == null) {
	NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Employee does not exist.");
	request.getSession().setAttribute("status", noti);
	response.sendRedirect("/common/ViewEmployees.jsp");
	return;
}
@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
Profile profile = profileDb.getProfile(loginId, path);
request.setAttribute("profile", profile);
%>

<form method="post" name="register" action="/AddPassportAction">
<input type="hidden" value="<%=empId %>" name="empId" readonly="readonly"/>

<table class="form-grid">
	<tr>
		<td>Employee Name</td>
		<td>${profile.getFirstName()}&nbsp;${profile.getLastName()}</td>
		<td></td>
	</tr>
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
		<td>Passport Type<span class="mnd">*</span></td>
		<td><input type="text" name="passportType" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Passport Number<span class="mnd">*</span></td>
		<td><input type="text" name="passportNo" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Place of Birth<span class="mnd">*</span></td>
		<td><input type="text" name="birthPlace" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Date of Issue<span class="mnd">*</span></td>
		<td><input type="text" name="issueDate" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Date of Expiry<span class="mnd">*</span></td>
		<td><input type="text" name="expireDate" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Passport Issue Place<span class="mnd">*</span></td>
		<td><input type="text" name="placeIssued" value=""/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Address in Passport<span class="mnd">*</span></td>
		<td><textarea name="pptAddr"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Comments</td>
		<td><textarea name="pptComments"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="" value="Add Passport" class="button"/></td>
		<td></td>
	</tr>
</table></form>
</div>
</div><!-- page-conent -->

<script>
$(document).ready(function(){
	var myFmt = {
		dateFormat:'dd-mm-yy',
		changeYear:true,
		yearRange:'-50:+50'
	};
	$('[name="issueDate"]').datepicker(myFmt);
	$('[name="expireDate"]').datepicker(myFmt);
	
	$('[name="issueDate"]').keypress(preventKbInput);
	$('[name="expireDate"]').keypress(preventKbInput);
	
	function preventKbInput(event){
		event.preventDefault();
	}
});

var fields = [
	{name:'COUNTRY_ID', display:'Country', rules:'required|callback_check_select'},
	{name:'passportType', display:'Passport Type', rules:'required|max_length[20]'},
	{name:'passportNo', display:'Passport Number', rules:'required|max_length[20]'},
	{name:'birthPlace', display:'Place of Birth', rules:'required|max_length[50]'},
	{name:'issueDate', display:'Date of Issue', rules:'required'},
	{name:'expireDate', display:'Date of Expiry', rules:'required'},
	{name:'placeIssued', display:'Passport Issue Place', rules:'required|max_length[50]'},
	{name:'pptAddr', display:'Address in Passport', rules:'required|max_length[150]'},
	{name:'pptComments', display:'Comments', rules:'max_length[200]'}
];

function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = document.getElementById("formErrors");
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		document.register.submit();
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
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
