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
	<title>Add Travel Ticket | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<link type="text/css" rel="stylesheet" href="/css/jquery-ui.min.css"/>
	
	<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.theme.min.js"></script>
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
<h2>Add Travel Ticket</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors"></div>

<form method="POST" action="/AddTicket" name="register"><table class="form-grid">
<%
String travelId = request.getParameter("TravelId");
// if empty throws an exception hence 
// forces user to come to this page with a value

int id = Integer.parseInt(travelId);
TravelMasterDao travelDb = new TravelMasterDao();

if (!travelDb.doesTravelExistById(id)) {
	request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
	return;
}
%>
	<tr>
		<td>Travel ID</td>
		<td><%=travelId %><input type="hidden" value="<%=travelId%>" name="TravelID"></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Ticket number<span class="mnd">*</span></td>
		<td><input type="text" name="TicketNumber" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Booking date<span class="mnd">*</span></td>
		<td><input type="text" name="bookingDate" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Journey date<span class="mnd">*</span></td>
		<td><input type="text" name="availabledate" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Journey Start Time<span class="mnd">*</span></td>
		<td><input type="text" name="journeyTime" /></td>
		<td></td>
	</tr>
	
	
	<tr>
		<td>From Station<span class="mnd">*</span></td>
		<td><input type="text" name="TicketFrom" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>To Station<span class="mnd">*</span></td>
		<td><input type="text" name="TicketTo" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Seat no</td>
		<td><input type="text" name="SeatNo"  value=""/></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Transport Type<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="AgentName">
			<option value="0" >Select Type</option>
			<option value="Aeroplane">Aeroplane</option>
			<option value="Train">Train</option>
			<option value="Bus">Bus</option>
			<option value="Taxi">Taxi</option>
			<option value="Ship">Ship</option>
		</select><span class='select-arrow'></span></span></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Transport Company<span class="mnd">*</span></td>
		<td><input type="text" name="FTName" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Vehicle no</td>
		<td><input type="text" name="vehicleNo" value="" /></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Customer care no</td>
		<td><input type="text" name="ContactNo" /></td>
		<td></td>
	<tr>
	
	<tr style="display:none;">
		<td>Employee ID</td>
		<td><input name="EmpID" value=""></td>
		<td></td>
	<tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Add Ticket" class="button"/></td>
		<td></td>
	</tr>
</table>
<input type="hidden" name="availabletodate" value=""/>
</form>
</div>
</div><!-- page-conent -->

<script>
$(document).ready(function(){
	var myFmt = {
			dateFormat:'dd-mm-yy'
			};
	$('[name="availabledate"]').datepicker(myFmt);
	$('[name="bookingDate"]').datepicker(myFmt);
	
	$('[name="availableDate"]').keypress(preventKbInput);
	$('[name="bookingDate"]').keypress(preventKbInput);
	
	function preventKbInput(event){
		event.preventDefault();
	}
});

var fields = [];

function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = document.getElementById("formErrors");
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		document.register.submit();
		return true;
	}
	
	var errStr = '<li style="list-style:none;"><strong>Errors</strong></li>';
	
	for (var i = 0; i < errors.length; i++) {
		errStr += '<li>' + errors[i].message + '</li>';
	}
	errStr = '<ul>' + errStr + '</ul>';
	
	errWrapper.innerHTML = errStr;
	errWrapper.focus();
	return false;
}

var validator = new FormValidator('register', fields, handleFormErrors);
</script>
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
