<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.vc.model.*,java.util.*"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.vc.dao.*,com.jk.vc.util.DateUtils"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Employees with Passport | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
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

<h2>Employees without Passport</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>
<%
@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
List<Profile> pList = new ProfileDao().getAllEmpProfiles(path);
request.setAttribute("pList", pList);
int count = 0;
request.setAttribute("pptDb", new PassportDao());
%>

<div class="page-content">
<table class="data-grid"><thead>
	<tr>
		<th>Login&nbsp;ID</th>
		<th>First Name</th>
		<th>Last Name</th>
		<th>DOB</th>
		<th>Phone No</th>
		<th>Email</th>
		<th>Photograph</th>
	</tr></thead><tbody>
	
	<c:if test="${pList.size() > 0}"><c:forEach var="profile" items="${pList }">
		<c:if test="${pptDb.getPptIdFromEmp(profile.getEmpid()) == 0}"><tr>
			<% count++; %>
			<td>${profile.getLoginID()}</td>
			<td>${profile.getFirstName()}</td>
			<td>${profile.getLastName()}</td>
			<td>${profile.getBirthDate()}</td>
			<td>${profile.getPhone()}</td>
			<td>${profile.getEmail()}</td>
			<td><img class="profile-img-small" alt="${profile.getFirstName()}" 
			src="/userimages/${profile.getPhoto()}"></td>
		</tr></c:if>
	</c:forEach></c:if>
	<% request.setAttribute("count", count); %>
<c:if test="${count == 0}">
	<tr>
		<td colspan="7" class="multi-col">No data found</td>
	</tr>
</c:if>
</tbody></table>

</div><!-- page-conent -->
	
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
