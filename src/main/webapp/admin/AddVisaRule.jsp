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
	<title>Add Visa Rule | Voyager Compass</title>
	
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
<h2>Add Visa Rule</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
request.setAttribute("cList", new CountryDao().findAllCountries());
request.setAttribute("vtList", new VisaTypeDao().getVisaTypeList());
%>

<form method="post" name="register" action=""><table class="form-grid">
	<tr>
		<td>Country Name</td>
		<td><span class="select-wrapper"><select name="COUNTRY_ID">
			<option value="0">Select Country</option>
			<c:forEach var="country" items="${cList}" varStatus="counter">
				<option value="${country.getCountryID()}">${country.getCountryName()}</option>			
			</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	<tr>
		<td>Visa Type</td>
		<td><span class="select-wrapper"><select name="VisaTypeID">
			<option value="0">Select Visa Type</option>
			
			<c:forEach var="visaType" items="${vtList}" varStatus="counter">
				<option value="${visaType.getVisaTypeID()}">${visaType.getVisaTypeName()}</option>			
			</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Visa Type as Per Country</td>
		<td><input type="text" name="VisaTypeasPerCountry" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Eligibility</td>
		<td><input type="text" name="Eligibility" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Visa Cost</td>
		<td><input type="text" name="VisaCost"></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Max Visa Permit Time</td>
		<td><input type="text" name="PermitTime"></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Rules and Regulations</td>
		<td><textarea name="Rules&Regulations"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Stamping Guidelines</td>
		<td><textarea name="Guidelines"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Remarks</td>
		<td><textarea name="Remarks"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Add Visa" class="button"/></td>
		<td></td>
	</tr>
</table></form>
</div>
</div><!-- page-conent -->

<script>
var fields = [
{}
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

