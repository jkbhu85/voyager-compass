<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.vc.model.*,java.util.List"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.vc.dao.*,com.jk.vc.util.DateUtils"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Travels | Voyager Compass</title>
	
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

<h2>Travels</h2>

<jk:status var="status"/>

<div class="page-content">

<table class="data-grid"><thead>
	<tr>
        <th>Travel&nbsp;ID</th>
        <th>Start Date</th>
        <th>End Date</th>
        <th>Employee ID</th>
        <th>Country</th>
	</tr></thead><tbody>
		
<%
		List<Travel> tList = new TravelMasterDao().findAllTravels();
				request.setAttribute("tList", tList);
				request.setAttribute("cDao", new CountryDao());
				request.setAttribute("profile", new ProfileDao());
				request.setAttribute("workDb", new WorkDao());
		%>
<c:forEach var="travel" items="${tList}">
	<tr>
		<td>${travel.getTravelId()}</td>
        <td>${travel.getStartDate()}</td>
        <td>${travel.getEndDate()}</td>
        <td>${profile.getUserID(travel.getEmpId())}</td>
        <td>${workDb.get(travel.getWorkId()).getCntName()}</td>
	</tr>
</c:forEach>

<c:if test="${tList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="5">No data found</td>
	</tr>
</c:if>

</tbody>
</table>

</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
