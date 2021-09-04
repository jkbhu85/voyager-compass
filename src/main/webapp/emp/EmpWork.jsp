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
	<title>Works | Voyager Compass</title>
	
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

<h2>Works</h2>

<jk:status var="status"/>

<div class="page-content">
	
<%
String loginId = (String) session.getAttribute("user");
ProfileDAO profileDb = new ProfileDAO();
List<Work> wList = new WorkDAO().getEmpWise(profileDb.getEmpIdFromLogin(loginId), Work.STATUS_FAILED);
request.setAttribute("wList", wList);
%>

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
	
<c:forEach var="work" items="${wList}" >
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

<c:if test="${wList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="6">No data found</td>
	</tr>
</c:if>

<c:if test="${sessionScope.role.equals('admin')}">
	<tr>
		<td colspan="6" class="multi-col-link"><a href="/admin/AddWork.jsp">Add Work</a></td>
	</tr>
</c:if>

</tbody>
</table>

</div><!-- page-conent -->


</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
