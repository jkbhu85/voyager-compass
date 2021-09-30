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
	<title>Add Embassy | Voyager Compass</title>
	
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
<h2>Add Embassy</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>
<%
List<Country> cList = new CountryDAO().findAllCountries();
request.setAttribute("cList", cList);
%>
<form method="post" name="register" action="/EmbassyAction"><table class="form-grid">
	<tr>
		<td>Country<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="COUNTRY_ID">
			<option value="0">Select Country</option>
			<c:forEach var="country" items="${cList }">
				<option value="${country.getCountryID()}">${country.getCountryName()}</option>
			</c:forEach>
		</select>
		<span class="select-arrow"></span>
		</span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Embassy Name<span class="mnd">*</span></td>
		<td><input type="text" name="EMBASSY_NAME"></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Contact No<span class="mnd">*</span></td>
		<td><input type="text" name="PhoneNo" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Embassy Address<span class="mnd">*</span></td>
		<td><textarea name="Address"></textarea></td>
		<td></td>
	</tr>

	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Add Embassy" class="button"/></td>
		<td></td>
	</tr>
</table></form>
</div>
</div><!-- page-conent -->

<script>
var fields = [
{name:'COUNTRY_ID',display:'Country',rules:'required|callback_check_select'},
{name:'EMBASSY_NAME',display:'Embassy Name',rules:'required|max_length[100]'},
{name:'PhoneNo',display:'Contact no',rules:'required|max_length[20]'},
{name:'Address',display:'Embassy Address',rules:'required|max_length[200]'}
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

validator.registerCallback('check_select', function(value){
	return document.register.COUNTRY_ID.selectedIndex > 0;
})
.setMessage('check_select', 'The <i>%s</i> field is required.');
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
