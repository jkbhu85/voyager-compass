<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
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
<c:if test="${not empty sessionScope.role}">
	<c:choose>
		<c:when test="${sessionScope.role.equals('admin')}">
			<c:redirect url="/admin/AdminHome.jsp" />
		</c:when>
		<c:otherwise>
			<c:redirect url="/emp/EmpHome.jsp" />
		</c:otherwise>
	</c:choose>
</c:if>

<jsp:include page="/oth/header.html"/>

<main>

<h2>Recover Password</h2>

<jk:status var="status"/>

<div class="page-content">

<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>
<form action="/RecoverPasswordAction" method="POST" name="register" autocomplete="off"><table class="form-grid">
	<tr>
		<td>Login ID</td>
		<td><input type="text" name="username" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Security Question</td>
		<td>
			<span class="select-wrapper">
				<select name="squest"><jsp:include page="/oth/securityQuestions.html"/></select>
				<span class="select-arrow"></span>
			</span>
		</td>
		<td></td>
	</tr>
	
	<tr>
		<td>Security Answer</td>
		<td><input name="sanswer" type="text" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input name="Input" type="submit" value="Recover Password" class="button"/></td>
		<td></td>
	</tr>
</table>

<input type="hidden" name="ownquest" value="" />
<input type="hidden" name="ch" value="" />
</form>
</div>
				
<div class="side-links">
	<a class="light-link" href="/index.jsp">Login</a>
</div>
</div><!-- page-conent -->

<script>
var fields = [
{name:'username', display:'Login ID', rules:'required|min_length[6]|max_length[30]|callback_check_username'},
{name:'squest', dispaly:'Security Question', rules:'required|callback_check_secques'},
{name:'sanswer', display:'Security Answer', rules:'required|min_length[5]|max_length[70]'}
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

validator.registerCallback('check_secques', function(value){
	return value != "0";
})
.setMessage('check_secques', 'The <i>%s</i> field is required.');

validator.registerCallback('check_username', function(value) {
	var usernamePat = /[a-zA-Z][a-zA-Z0-9_\-\.]*/;
	
	if (value == undefined || value == null) return false;
	
	return value.search(usernamePat) == 0;
})
.setMessage('check_username',
		'First character of <i>Username</i> should be a letter and '
		+ 'it can contain only letters, numbers, underscore(_), hyphen(-) and dot(.).');


function selOwnQues(field){
	if (document.register.ch.value == '1') {
		document.register.squest.disabled = "disabled";
		document.register.ownquest.disabled = "";
	} else {
		document.register.squest.disabled = "";
		document.register.ownquest.disabled = "disabled";
}
	}
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
