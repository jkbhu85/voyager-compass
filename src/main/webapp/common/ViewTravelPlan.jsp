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
	<title>Work Info &amp; Travel Plan | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<jsp:include page="/oth/ieAndOther.html" />
	<style>
.data-grid tbody tr td:first-of-type{
	width:25%;
}
.data-grid tbody tr td{
	position:relative;
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
<h2>Work info &amp; Travel Plan</h2>

<jk:status var="status"/>

<div class="page-content">
<%
ProfileDao profileDb = new ProfileDao();
Profile emp = null;

String userId = (String) request.getSession().getAttribute("user");
String userRole = (String) request.getSession().getAttribute("role");
%>

<table class="data-grid no-head"><tbody>

	<tr>
		<td colspan="3"><p class="data-grid-heading">Work Info</p></td>
	</tr>
	
	<tr>
		<td>Work ID</td>
		<td>${work.getWorkId()}</td>
	</tr>
	<tr>
		<td>Country Name</td>
		<td>${work.getCntName()}</td>
	</tr>
	<tr>
		<td>Work Description</td>
		<td>${work.getDescription()}</td>
	</tr>
	<tr>
		<td>Work Status</td>
		<td><i>${work.getStatus()}</i>
			<c:if test="${sessionScope.role.equals('admin')}">
				<a style="position:absolute;left:240px;top:0;" href="/admin/UpdateWork.jsp?workId=${work.getWorkId()}" class="light-link">Update</a>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td>Work Responsibility</td>
		<td>${work.getRespb()}</td>
	</tr>
	
	<tr>
		<td colspan="3"><p class="data-grid-heading">Travel Info</p></td>
	</tr>
	
	<c:if test="${travel == null}">
	<tr>
		<td colspan="2"><a  style="margin-left:40px" class="dark-link" href="/admin/TravelMaster.jsp?WorkId=${work.getWorkId()}">Add Travel Plan</a></td>
	</tr>
	</c:if>
	
	<c:if test="${travel != null}">
	<tr>
		<td>Employee Name</td>
		<td>${emp.getFirstname()} &nbsp; ${emp.getLastname()}
			<c:if test="${sessionScope.role.equals('admin')}">
				<a style="position:absolute;left:240px;top:0;" class="light-link" href="/admin/ViewEmployee.jsp?username=${emp.getLoginID()}">See Profile</a>
			</c:if>
		</td>
	</tr>
	
	<tr>
		<td>Employee department</td>
		<td>${deptDb.getDeptatment(emp.getDeptID()).getDepartmentName()}</td>
	</tr>
	
	<tr>
		<td>Start date</td>
		<td>${travel.getStartDate()}</td>
	</tr>
	
	<tr>
		<td>End date</td>
		<td>${travel.getEndDate()}</td>
	</tr>
	
	<tr>
		<td>Travel instructions</td>
		<td>${travel.getInst()}</td>
	</tr>
	</c:if>
	
	<tr>
		<td colspan="3"><p class="data-grid-heading">Ticket Info</p></td>	
	</tr>
	
		<c:if test="${empty ticket && not empty travel}"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
			<tr>
				<td colspan="3"><a class="dark-link" href="/admin/AddTravelTicket.jsp?TravelId=${travel.getTravelId()}">Add ticket</a></td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr>
				<td colspan="3">Ticket not booked yet.</td>
			</tr>
			</c:otherwise></c:choose>
		</c:if>
		
		<c:if test="${not empty ticket}">
			<tr>
				<td>Ticket no</td>
				<td>${ticket.getTicketNo()}</td>
			</tr>
			
			<tr>
				<td>Transport company</td>
				<td>${ticket.getFlightName()}</td>
			</tr>

			<tr>
				<td>Mode of transport</td>
				<td>${ticket.getTravelAgetName()}</td>
			</tr>
			
			<tr>
				<td>Start Station</td>
				<td>${ticket.getTicketFrom()}</td>
			</tr>
			
			<tr>
				<td>Destination station</td>
				<td>${ticket.getTicketTo()}</td>
			</tr>
			
			<tr>
				<td>Journey date</td>
				<td>${ticket.getTicketAvailableDate()}</td>
			</tr>
			
			<tr>
				<td>Journey time</td>
				<td>${ticket.getJourneytime()}</td>
			</tr>
			
			<tr>
				<td>Seat no</td>
				<td>${ticket.getSeatNo()}</td>
			</tr>
			
			<tr>
				<td>Vechicle no</td>
				<td>${ticket.getVehicleNo()}</td>
			</tr>
			
			<tr>
				<td>Date of booking</td>
				<td>${ticket.getTicketBookDate()}</td>
			</tr>
			
			<tr>
				<td>Customer care</td>
				<td>${ticket.getInchargeContactNo()}</td>
			</tr>
		</c:if>
	
	<tr>
		<td colspan="3"><p class="data-grid-heading">Stay Info</p></td>	
	</tr>
	
		<c:if test="${empty stay  && not empty travel && not empty ticket}"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
			<tr>
				<td colspan="3"><a class="dark-link" href="/admin/EmpStay.jsp?TravelId=${travel.getTravelId()}">Add Stay</a></td>
			</tr>
			</c:when>
			<c:otherwise>
			<tr>
				<td colspan="3">Ticket not booked yet.</td>
			</tr>
			</c:otherwise></c:choose>
		</c:if>
		
		<c:if test="${not empty stay}">
			<tr>
				<td>Hotel name</td>
				<td>${hotel.getHotelName()}</td>
			</tr>
			<tr>
				<td>Room no</td>
				<td>${stay.getRoomNo()}</td>
			</tr>
			<tr>
				<td>Hotel addr</td>
				<td>${hotel.getHotelAddr() }</td>
			</tr>
			<tr>
				<td>Hotel Customer care</td>
				<td>${hotel.getHotelPhno()}</td>
			</tr>
			<tr>
				<td>Pickup Vehicle no</td>
				<td>${stay.getVehicleNo() }</td>
			</tr>
		</c:if>
</tbody></table>

</div><!-- page-conent -->

<script>

</script>
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
