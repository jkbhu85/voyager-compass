<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.travel.model.*"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.travel.dao.*,com.jk.core.util.DateWrapper"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<script type="text/javascript" src="/js/validate.min.js"></script>
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

<h2>Change Password</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>
<%--

PAGE CONTENT goes here
-------------------------------

 --%>
<div class="page-content">
<div id="formErrors"></div>

<form action="/ChangePasswordAction" method="post" name="register">
	<table class="form-grid">
		<tr>
			<td>Current Password<span class="mnd">*</span></td>
			<td><input type="password" name="oldpassword" /></td>
			<td></td>
		</tr>
		<tr>
			<td>New Password<span class="mnd">*</span></td>
			<td><input type="password" name="newpassword" /></td>
			<td></td>
		</tr>
		<tr>
			<td>Confirm Password<span class="mnd">*</span></td>
			<td><input type="password" name="newpassword2" /></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="Submit" value="Change Password" class="button"/></td>
			<td></td>
		</tr>
	</table>
</form>
</div><!-- page-conent -->

<script>
var rules = [
{ name:'oldpassword', display:'Current Password', rules:'required|min_length[6]|max_length[30]'},
{ name:'newpassword', display:'New Password', rules:'required|min_length[6]|max_length[30]'},
{ name:'newpassword2', display:'Confirm Password', rules:'required|matches[newpassword]'}
];

function handleFormErrors(errors, event) {
	//event.preventDefault();
	var errWrapper = document.getElementById("formErrors");
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		console.log("fine");
		return true;
	}
	
	var errStr = '<li style="list-style:none;"><strong>Errors</strong></li>';
	
	for (var i = 0; i < errors.length; i++) {
		errStr += '<li>' + errors[i].message + '</li>';
	}
	errStr = '<ul>' + errStr + '</ul>';
	
	errWrapper.innerHTML = errStr;
	console.log("errors");
	return false;
}

var validator = new FormValidator('register', rules, handleFormErrors);
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
