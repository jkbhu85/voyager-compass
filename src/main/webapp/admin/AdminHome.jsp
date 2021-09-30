<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>Home | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	
	<script type="text/javascript" src="/js/img-util.js"></script>
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

<h2>Administrator Home</h2><%-- Page heading goes here --%>

<jk:status var="status"/><%-- Status to show notification --%>

<div class="page-content">
<%
String loginId = (String) session.getAttribute("user"); 
@SuppressWarnings("deprecation")
Profile profile = new ProfileDAO().findProfile(loginId, request.getRealPath("/userimages"));

String name = profile.getFirstName() + " " + profile.getLastName();
WorkDAO workDb = new WorkDAO();
List<Work> wList = workDb.findAllWorksByEmployeeIdAndStatus(profile.getEmpid(), Work.STATUS_PREPARED);

request.setAttribute("wList", wList);
%>
<p>Welcome, <strong><%=name %></strong></p>


<h3 class="sub-heading">Works Assigned</h3>
<c:if test="${wList.size() > 0}">

<table class="data-grid"><thead>
	<tr>
		<th>Work&nbsp;ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Responsibilities</th>
        <th>Status</th>
        <th>Incharge</th>
        <th>Country</th>
	</tr></thead><tbody>
<c:forEach var="work" items="${wList}">
	<tr>
		<td class="block-wrapper"><a class="block" href="/ViewTravelAction?workId=${work.getWorkId()}">${work.getWorkId()}</a></td>
		<td>${work.getTitle()}</td>
		<td>${work.getDescription()}</td>
		<td>${work.getRespb()}</td>
		<td>${work.getStatus()}</td>
		<td>${work.getInchName()}</td>
		<td>${work.getCntName()}</td>
	</tr>
</c:forEach>
</tbody></table>
</c:if>

<%
List<Work> inchList = workDb.findAllWorksByInchargeAndStatus(profile.getEmpid(), Work.STATUS_PREPARED);
request.setAttribute("inchList", inchList);
%>

<h3 class="sub-heading">Works Assigned (Incharge)</h3>
<c:if test="${inchList.size() > 0}">
<table class="data-grid"><thead>
	<tr>
		<th>Work&nbsp;ID</th>
        <th>Title</th>
        <th>Description</th>
        <th>Responsibilities</th>
        <th>Status</th>
        <th>Incharge</th>
        <th>Country</th>
	</tr></thead><tbody>
<c:forEach var="work" items="${inchList}">
	<tr>
		<td class="block-wrapper"><a class="block" href="/ViewTravelAction?workId=${work.getWorkId()}">${work.getWorkId()}</a></td>
		<td>${work.getTitle()}</td>
		<td>${work.getDescription()}</td>
		<td>${work.getRespb()}</td>
		<td>${work.getStatus()}</td>
		<td>${work.getInchName()}</td>
		<td>${work.getCntName()}</td>
	</tr>
</c:forEach>
</tbody></table>
</c:if>

</div><!-- page-conent -->

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
