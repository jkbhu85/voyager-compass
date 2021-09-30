<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>Change Security Question | Voyager Compass</title>
	
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

<h2>Change Security Question</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>

<div class="page-content">
<div id="formErrors"></div>
<form action="/ChangeQuestionAction" method="post" name="register">
<table class="form-grid">
	<tr>
		<td>Security Question<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="squest">
			<jsp:include page="/oth/securityQuestions.html"/>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Security Answer<span class="mnd">*</span></td>
		<td><input name="sanswer" type="text" /></td>
		<td></td>
	</tr>
	<tr>
		<td>Password<span class="mnd">*</span></td>
		<td><input type="password" name="password" /></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td><input name="Input" type="submit" value="Change Question" class="button" /></td>
		<td></td>
	</tr>
</table>

<input type="hidden" name="ownquest" value="" />
<input type="hidden" name="ch" value="" />
</form>
</div><!-- page-conent -->

<script>
var fields = [
{name:'squest',display:'Security Question',rules:'required|callback_check_secques'},
{name:'sanswer',display:'Security Answer',rules:'required|min_length[5]|max_length[70]'},
{name:'password',display:'Password',rules:'required|min_length[6]|max_length[30]'}
];

function handleFormErrors(errors, event) {
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

var validator = new FormValidator('register', fields, handleFormErrors);

validator.registerCallback('check_secques', function(value){
	return value != "0";
})
.setMessage('check_secques', 'The <i>%s</i> field is required.');


function selOwnQues(field){
	if (document.register.ch.value == '1') {
		document.register.squest.disabled = "disabled";
		document.register.ownquest.disabled = "";
	} else {
		document.register.squest.disabled = "";
		document.register.ownquest.disabled = "disabled";
		document.register.ownquest.value = "";
	}
}
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
