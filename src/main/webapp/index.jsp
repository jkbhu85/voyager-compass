<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Login | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="css/global.css"/>
	<link type="text/css" rel="stylesheet" href="css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="css/primary-menu.css"/>
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<jsp:include page="oth/ieAndOther.html" />
</head>


<body>

<%-- Checking if user is logged in or not --%>
<c:if test="${not empty sessionScope.role}">
	<c:choose>
		<c:when test="${sessionScope.role.equals('admin')}">
			<c:redirect url="admin/AdminHome.jsp" />
		</c:when>
		<c:otherwise>
			<c:redirect url="employee/EmpHome.jsp" />
		</c:otherwise>
	</c:choose>
</c:if>

<jsp:include page="/oth/header.html"/>
<main>
<h2>Login</h2>

<%-- Status to show notification --%>
<jk:status var="status"/>

<div class="page-content"><%-- Page content goes here --%>	
<div class="form-wrapper">
<div id="formErrors"></div>
<form action="LoginAction" name="register" autocomplete="on" method="POST"><table class="form-grid">
	<tr>
		<td>Login ID</td>
		<td><input type="text" name="username" id="username" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Password</td>
		<td><input type="password" name="password" id="password"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>
		<td><input type="submit" value="Login" class="button"/></td>
		<td></td> 
	</tr>
</table></form>
</div>
				
<div class="side-links">
	<a class="light-link" href="RecoverPassword.jsp">Forgot password</a>
</div>
</div><!-- page-conent -->

<script>
var fields = [
{name:'username',display:'Login ID',rules:'required|min_length[6]|max_length[30]|callback_check_username'},
{name:'password',display:'Password',rules:'required|min_length[6]|max_length[30]'}
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

validator.registerCallback('check_username', function(value) {
	var usernamePat = /[a-zA-Z][a-zA-Z0-9_\-\.]*/;
	
	if (value == undefined || value == null) return false;
	
	//console.log('value: ' + value + "-- Match: " + value.search(usernamePat) == 0);
	
	return value.search(usernamePat) == 0;
})
.setMessage('check_username',
		'First character of <i>Username</i> should be a letter and '
		+ 'it can contain only letters, numbers, underscore(_), hyphen(-) and dot(.).');
</script>

</main>

<jsp:include page="oth/footer.html"/>

</body>
</html>