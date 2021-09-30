<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
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
	<title>All Employees | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<jsp:include page="/oth/ieAndOther.html" />
	<style>
	.mail,.link{
		color:#008;
	}
	.mail:hover{
		color:#078;
	}
	</style>
</head>


<body>
<%-- Checking if user is logged in or not --%>
<c:if test="${empty sessionScope.role}">
	<c:redirect url="/index.jsp" />
</c:if>

<jsp:include page="/oth/header.html"/>

<jsp:include page="/oth/selectMenu.jsp"/>

<main>
<h2>All Employees</h2><%-- Page heading goes here --%>
<jk:status var="status"/><%-- Status to show notification --%>
<%
@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
List<Profile> pList = new ProfileDao().getAllEmpProfiles(path);
request.setAttribute("pList", pList);
request.setAttribute("pptDb", new PassportDao());
request.setAttribute("deptDb", new DepartmentDao());
%>

<div class="page-content">
<table class="data-grid"><thead>
	<tr>
		<th>Name</th>
		<th>Department</th>
		<th>Phone No</th>
		<th>Login&nbsp;ID</th>
		<th>Email</th>
		<th>Photograph</th><c:if test="${sessionScope.role.equals('admin')}">
		<th>Passport Status</th></c:if>
	</tr></thead><tbody>
	
<c:choose>
<c:when test="${pList.size() > 0}"><c:forEach var="profile" items="${pList }">
	<tr>
		<td>${profile.getFirstName()}<span></span> ${profile.getLastName()}</td>
		<td>${profile.getDeptName()}</td>
		<td>${profile.getPhone()}</td>		
		<td><c:if test="${sessionScope.role.equals('admin')}">
			<a class="link" href="/admin/ViewEmployee.jsp?username=${profile.getLoginID()}">${profile.getLoginID()}</a>
		</c:if>
		<c:if test="${!sessionScope.role.equals('admin')}">${profile.getLoginID()}</c:if>
		</td>
		<td><a class="mail" href="mailto:${profile.getEmail()}">${profile.getEmail()}</a></td>
		<td><img class="profile-img-small" alt="${profile.getFirstName()}" src="/userimages/${profile.getPhoto()}"></td>
		<c:if test="${sessionScope.role.equals('admin')}">
		<td><c:choose>
			<c:when test="${pptDb.getPptIdFromEmp(profile.getEmpid()) == 0}">
				<a class="light-link" href="/admin/AddPassport.jsp?empId=${profile.getEmpid()}">Add&nbsp;Now</a>
			</c:when>
			<c:otherwise>
				<a class="dark-link" href="/common/ViewPassport.jsp?empId=${profile.getEmpid()}">See&nbsp;here</A>
			</c:otherwise>
		</c:choose></td></c:if>
	</tr>
</c:forEach></c:when>

<c:otherwise>
	<tr>
		<td colspan="8" class="multi-col">No data found</td>
	</tr>
</c:otherwise>
</c:choose>
</tbody></table>
</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
