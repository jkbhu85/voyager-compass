<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.travel.model.*, java.util.*"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.travel.dao.*,com.jk.core.util.DateWrapper"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Departments | Voyager Compass</title>
	
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

<h2>Departments</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>

<%
List<Department> dList = new DepartmentDAO().getDepartmentList();
request.setAttribute("dList", dList);
request.setAttribute("profileDb", new ProfileDAO());
%>

<div class="page-content">
<table class="data-grid"><thead>
	<tr>
		<th>Dept&nbsp;ID</th>
		<th>Name</th>
		<th>Abbreviation</th>
		<th>Incharge</th>
	</tr></thead><tbody>

<c:choose>
<c:when test="${dList.size() > 0}"><c:forEach var="dept" items="${dList}">
	<tr>
		<td class="block-wrapper"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
				<a class="block" href="/admin/UpdateDepartment.jsp?deptNo=${dept.getDepartmentID()}">${dept.getDepartmentID()}</a>
			</c:when>
			<c:otherwise>
				<a class="block">${dept.getDepartmentID()}</a>
			</c:otherwise>
		</c:choose></td>
		
		<td>${dept.getDepartmentName()}</td>
		<td>${dept.getDepartmentAbbr()}</td>
		<td>${profileDb.getUserID(dept.getDepartmentInChgID())}</td>
	</tr>
</c:forEach></c:when>

<c:otherwise>
	<tr>
		<td colspan="4" class="multi-col">No Data Found.</td>
	</tr>
</c:otherwise>
</c:choose>

</tbody></table>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-link"><a href="/admin/AddDept.jsp">Add Department</a></div>
</c:if>
</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
