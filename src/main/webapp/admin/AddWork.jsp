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
	<title>Add Work | Voyager Compass</title>
	
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
<h2>Add Work</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>
<form method="POST" action="/AddWork" name="register"><table class="form-grid">
<%
List<Profile> aList = new ProfileDao().findAllAdmins();
List<Country> cList = new CountryDao().findAllCountries();

request.setAttribute("aList", aList);
request.setAttribute("cList", cList);
%>

	<tr>
		<td>Country<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="COUNTRY_ID">
			<option value="0">Select Country</option>
			<c:forEach var="country" items="${cList }">
				<option value="${country.getCountryID()}">${country.getCountryName()}</option>
			</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>

	<tr>
		<td>Incharge ID<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="InchargeID">
			<option value="0">Select Incharge Id</option>
		<c:forEach var="admin" items="${aList}">
			<option value="${admin.getEmpid()}">${admin.getLoginID()}</option>
		</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Work Title<span class="mnd">*</span></td>
		<td><textarea name="Title"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Description<span class="mnd">*</span></td>
		<td><textarea name="Desc"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Responsibilities<span class="mnd">*</span></td>
		<td><textarea name="Response"></textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Add Work"  class="button"/></td>
		<td></td>
	</tr>
</table>
	<input type="hidden" value="" name="WorkPermit">
</form>
</div>

</div><!-- page-conent -->

<script>
var fields = [
{name:'COUNTRY_ID', display:'Country', rules:'required|callback_check_select'},
{name:'InchargeID', display:'Incharge ID', rules:'required|callback_check_select'},
{name:'Title', display:'Work Title', rules:'required|max_length[200]'},
{name:'Response', display:'Responsibilities', rules:'required|max_length[200]'},
{name:'Desc', display:'Description', rules:'require|max_length[400]'}
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

validator.registerCallback("check_select", function(value){
	return value != "0";
})
.setMessage("check_select", "The field <i>%s</i> is required.");
</script>
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
