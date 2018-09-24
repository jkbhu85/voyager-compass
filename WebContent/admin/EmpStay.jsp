<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="jktags" prefix="jk" %>
<%@ page import="com.jk.travel.model.*"%> 
<%@ page import="java.util.Enumeration,java.util.List"%>
<%@ page import="com.jk.travel.dao.*,com.jk.core.util.DateWrapper"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Add Stay | Voyager Compass</title>
	
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
<h2>Add Employee Stay Info</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
int tid = Integer.parseInt((String) request.getParameter("TravelId"));
Travel travel = new TravelMasterDAO().get(tid);

if (travel == null) {
	request.getRequestDispatcher("/index.jsp").forward(request, response);
	return;
}

Work work = new WorkDAO().get(travel.getWorkId());
List<Hotel> hList = new HotelDAO().getHotelList(work.getCntId());
request.setAttribute("travel", travel);
request.setAttribute("work", work);
request.setAttribute("hList", hList);
%>

<form method="post" name="register" action="/StayAction"><table class="form-grid">
	<tr>
		<td>Travel ID</td>
		<td>${travel.getTravelId()}<input type="hidden" value="${travel.getTravelId()}" name="travelId" readonly="readonly"></td>
		<td></td>
	</tr>
	<tr>
		<td>Destination Country</td>
		<td>${work.getCntName()}</td>
		<td></td>
	</tr>
	
	<tr>
		<td>Hotel<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="hotelId">
			<option value="0">Select Hotel</option>
			
		<c:forEach items="${hList}" var="hotel">
			<option value="${hotel.getHotelID()}">${hotel.getHotelName()}</option>
		</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Hotel room no<span class="mnd">*</span></td>
		<td><input type="text" name="roomNo" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Pickup Vehicle no</td>
		<td><input type="text" name="vehicleNo" /></td>
		<td></td>
	</tr>
	
	
	<tr>
		<td></td>
		<td><input type="submit" value="Add Stay" name="submit" class="button"/></td>
		<td></td>
	</tr>
</table></form>
</div><!-- page-conent -->

<script>
var fields = [
{name:'hotelId', display:'Hotel', rules:'required|callback_check_hotelid'},
{name:'roomNo', display:'Hotel room no', rules:'required|max_length[10]'},
{name:'vehicleNo', display:'Pickup vehicle no', rules:'callback_vehicle_number|max_length[30]'}
];

function handleFormErrors(errors, event) {
	event.preventDefault();
	
	var errWrapper = document.getElementById("formErrors");
	
	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		document.register.submit();
		return true;
	}
	//console.log(errors.length + "  errors");
	
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

validator.registerCallback('vehicle_number', function(value){
	var pat = /[A-Z0-9][A-Z0-9 -]*/i;
	
	return pat.search(value) == 0;
})
.setMessage('vehicle_number','First letter of <i>%</i> must be a character or number '+
		'and rest can be letters, numbers, spaces or hyphen(-).');

validator.registerCallback('check_hotelid', function(value){
	return document.register.hotelId.selectedIndex > 0;
})
.setMessage('check_hotelid', 'The <i>%s</i> field is required.');
</script>
		
</div></main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
