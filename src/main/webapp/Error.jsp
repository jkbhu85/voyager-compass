<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Error | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<jsp:include page="/oth/ieAndOther.html" />
</head>


<body>
<jsp:include page="/oth/header.html"/>

<c:if test="${not empty sessionScope.role}">
	<jsp:include page="/oth/selectMenu.jsp"/>
</c:if>


<main>
<h2>Error Occurred!</h2>

<jk:status var="status"/>

<div class="page-content">
<p>An error occurred while processing your request. Please try again after some time.</p>
</div><!-- page-conent -->
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
