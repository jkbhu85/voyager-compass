<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
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
	<title>Profile | Voyager Compass</title>
	
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

<h2>Profile</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>

<div class="page-content">
<%
String name = (String) session.getAttribute("user");
@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
Profile profile = new ProfileDAO().getProfile(name, path);
Department dept = new DepartmentDAO().getDeptatment(profile.getDeptID());

String incharge = dept.getDepartmentInChgID() == profile.getEmpid() ? "(Incharge)" : "";

int pptId = new PassportDAO().getPptIdFromEmp(profile.getEmpid());

request.setAttribute("pptId", pptId);
request.setAttribute("dept", dept);
request.setAttribute("incharge", incharge);
request.setAttribute("profile", profile);
%>
<table class="profile-grid">
	<tr>
		<td colspan="2"><p class="data-grid-heading">Personal Details</p></td>
	</tr>
	<tr>
		<td>First Name</td>
		<td>${profile.getFirstname()}</td>
		<td style="vertical-align:top;" rowspan="6"><img alt="Photo" src="/userimages/${profile.getPhoto()}" class="profile-img"></td>
	</tr>
	<tr>
		<td>Last Name</td>
		<td>${profile.getLastname()}</td>
	</tr>
	<tr>
		<td>Dob</td>
		<td>${profile.getBdate()}</td>
	</tr>
	<tr>
		<td>Email</td>
		<td>${profile.getEmail()}</td>
	</tr>
	<tr>
		<td>Phone</td>
		<td>${profile.getPhone()}</td>
	</tr>
	
	<tr>
		<td>Department</td>
		<td>${dept.getDepartmentName()}&nbsp;${incharge}</td>
	</tr>
	<tr>
		<td>Login ID</td>
		<td>${profile.getLoginID()}</td>
	</tr>
	
	<tr>
		<td>Gender</td>
		<td>${profile.getGender()}</td>
	</tr>
	
	<tr>
		<td colspan="2"><p class="data-grid-heading">Address Details</p></td>
	</tr>
	
	<c:if test="${not empty profile.getHno()}">
	<tr>
		<td>Permanent Address</td>
		<td>
			<div>${profile.getHno()}</div>
			<div>${profile.getStreet()}</div>
			<div>${profile.getCity()}</div>
			<div>${profile.getState()}</div>
			<div>${profile.getCountry()}</div>
		</td>
		<td></td>
	</tr>
	</c:if>
	
	<c:if test="${not empty profile.getChno()}">
	<tr>
		<td>Current Address</td>
		<td>
			<div>${profile.getChno()}</div>
			<div>${profile.getCstreet()}</div>
			<div>${profile.getCcity()}</div>
			<div>${profile.getCstate()}</div>
			<div>${profile.getCcountry()}</div>
		</td>
		<td></td>
	</tr>
	</c:if>
</table>
<div class="side-links">
<c:if test="${pptId > 0}">
	<a class="dark-link" href="/common/ViewPassport.jsp?empId=${profile.getEmpid()}">See Passport</a>
</c:if>
</div>
</div><!-- page-conent -->
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
