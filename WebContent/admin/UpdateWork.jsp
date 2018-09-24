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
	<title>Update Work | Voyager Compass</title>
	
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
<h2>Update Work</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
int workId = Integer.parseInt(request.getParameter("workId"));
Work work = new WorkDAO().get(workId);

if (work == null) {
	request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
	return;
}
request.setAttribute("work", work);
%>

<form method="post" name="register" action="/UpdateWorkAction"><table class="form-grid">
	<tr>
		<td>Work Title</td>
		<td>${work.getTitle()}</td>
		<td></td>
	</tr>
	
	<tr>
		<td>Country</td>
		<td>${work.getCntName()}</td>
		<td></td>
	</tr>
	
	<tr>
		<td>Work Description<span class="mnd">*</span></td>
		<td><textarea name="desc">${work.getDescription()}</textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Work Status<span class="mnd">*</span></td>
		<td>
			<span class="select-wrapper"><select name="status">
				<option value="">Select Status</option>
				<option value="${Work.STATUS_CANCELLED}">Cancelled</option>
				<option value="${Work.STATUS_SUCCESSFUL}">Successful</option>
				<option value="${Work.STATUS_PARTIALLY_SUCCESSFUL}">Partially Successful</option>
				<option value="${Work.STATUS_FAILED}">Failed</option>
			</select><span class="select-arrow"></span></span>
		</td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td><input type="submit" value="Update Work" class="button"/></td>
		<td></td>
	</tr>
</table>
<input type="hidden" value="${work.getWorkId()}" name="workId" readonly="readonly"/>
</form>
</div>
</div><!-- page-conent -->

<script>
var fields = [
	{name:'desc', display:'Description', rules:'required'},
	{name:'status', display:'Status', rules:'required'}
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
