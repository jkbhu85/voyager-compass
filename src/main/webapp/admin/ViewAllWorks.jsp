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
	List<Work> wList = new WorkDao().findAllWorks();

		request.setAttribute("wList", wList);
	%>

<table class="data-grid"><thead>
	<tr>
		<th>Work&nbsp;Id</th>
        <th>Title</th>
        <th>Responsibilities</th>
        <th>Incharge</th>
        <th>Status</th>
        <th>Country</th>
	</tr></thead><tbody>
	
<c:forEach var="work" items="${wList}">
	<tr>
		<td class="block-wrapper">
			<a class="block" href="/ViewTravelAction?workId=${work.getWorkId()}">${work.getWorkId()}</a>
		</td>
		<td>${work.getTitle()}</td>
		<td><c:choose>
		<c:when test="${work.getRespb().length() > 200}">
			${work.getRespb().substring(0,200)}
		</c:when>
		<c:otherwise>
			${work.getRespb()}
		</c:otherwise>
		</c:choose></td>
		<td>${work.getInchName()}</td>
		<td>${work.getStatus()}</td>
		<td>${work.getCntName()}</td>
	</tr>
</c:forEach>

<c:if test="${wList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="6">No data found</td>
	</tr>
</c:if>
</tbody></table>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-link">
		<a href="/admin/AddWork.jsp">Add Work</a>
	</div>
</c:if>
</div><!-- page-conent -->


</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
