<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.travel.model.*,java.util.List"%> 
<%@ page import="java.util.Enumeration"%>
<%@ page import="com.jk.travel.dao.*,com.jk.core.util.DateWrapper"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Hotels | Voyager Compass</title>
	
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

<h2>Hotels</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>
<%--

PAGE CONTENT goes here
-------------------------------

 --%>
<div class="page-content">

<table class="data-grid"><thead>
	<tr>
		<th>S.No.</th>
		<th>Name</th>
		<th>Address</th>
		<th>Phone No</th>
		<th>Min Charge</th>
		<th>Max Charge</th>
		<th>Country</th>
	</tr></thead><tbody>
		
<%
List<Hotel> hList = new HotelDAO().getHotelList();
request.setAttribute("hList", hList);
//request.setAttribute("countryDb", new CountryDAO());
%>
<c:forEach var="hotel" items="${hList}" varStatus="counter">
	<tr>
		<td class="block-wrapper"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
				<a class="block" href="/admin/UpdateHotel.jsp?HotelID=${hotel.getHotelID()}">${counter.count}</a>
			</c:when>
			<c:otherwise>
				<a class="block">${counter.count}</a>
			</c:otherwise>
		</c:choose></td>
		<td>${hotel.getHotelName()}</td>
		<td>${hotel.getHotelAddr()}</td>
		<td>${hotel.getHotelPhno()}</td>
		<td>${hotel.getMinCharge()}</td>
		<td>${hotel.getMaxCharge()}</td>
		<td>${hotel.getCountryName()}</td>
	</tr>
</c:forEach>

<c:if test="${hList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="6">No data found</td>
	</tr>
</c:if>
</tbody>
</table>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-link"><a href="/admin/AddHotel.jsp">Add Hotel</a></div>
</c:if>
</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
