<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Logout | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
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
			<c:redirect url="/employee/EmpHome.jsp" />
		</c:otherwise>
	</c:choose>
</c:if>

<jsp:include page="/oth/header.html"/>


<main>

<h2>Logged out successfully</h2><%-- Page heading goes here --%>

<%--

PAGE CONTENT goes here
-------------------------------

 --%>
<div class="page-content">
<div class="side-links">
	<a class="light-link" href="/index.jsp">Login</a>
</div>

</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
<%--
<c:if test="${sessionScope.role.equals('admin')}">
	<a class="admin-link" href=""></a>
</c:if>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-home-link"><a class="admin-link" href="admin/home.jsp"></a></div>
</c:if>
--%>