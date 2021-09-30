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
	<title>All Tickets | Voyager Compass</title>
	
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

<h2>Tickets</h2>

<jk:status var="status"/>

<div class="page-content">

<table class="data-grid"><thead>
	<tr>
		<th>Ticket No</th>
		<th>Book Date</th>
		<th>From</th>
		<th>To</th>
		<th>Seat No</th>
		<th>Transport company</th>
		<th>Transport type</th>
		<th>Customer care</th>
	</tr></thead><tbody>
		
<%
		List<TravelTicket> tList = new TravelTicketDao().findAllTickets();
				request.setAttribute("tList", tList);
				request.setAttribute("profile", new ProfileDao());
		%>
<c:forEach var="ticket" items="${tList}">
	<tr>
		<td>${ticket.getTicketNo()}</td>
		<td>${ticket.getTicketBookDate()}</td>
		<td>${ticket.getTicketFrom()}</td>
		<td>${ticket.getTicketTo()}</td>
		<td>${ticket.getSeatNo()}</td>
		<td>${ticket.getFlightName()}</td>
		<td>${ticket.getTravelAgetName()}</td>
		<td>${ticket.getInchargeContactNo()}</td>
	</tr>
</c:forEach>

<c:if test="${tList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="9">No data found</td>
	</tr>
</c:if>
<%--
<c:if test="${sessionScope.role.equals('admin')}">
	<tr>
		<td colspan="9" class="multi-col-link"><a href="/admin/AddTravelTicket.jsp">Add Ticket</a></td>
	</tr>
</c:if>--%>

</tbody>
</table>

</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
