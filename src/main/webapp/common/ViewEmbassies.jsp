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
	<title>Embassies | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<jsp:include page="/oth/ieAndOther.html" />
	<style>
	.data-grid thead tr th:nth-of-type(2){
		width:200px;
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

<h2>Embassies</h2>

<jk:status var="status"/>

<div class="page-content">
		
<%
		request.setAttribute("eList", new EmbassyDao().findAllEmbassies());
				request.setAttribute("countryDb", new CountryDao());
		%>
<table class="data-grid"><thead>	
	<tr>
		<th>S.No.</th>
		<th>Country&nbsp;Name</th>
		<th>Name</th>
		<th>Address</th>
		<th>Contact&nbsp;No</th>
	</tr></thead><tbody>
	
<c:forEach var="embassy" items="${eList}" varStatus="counter">
	<tr>
		<td class="block-wrapper"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
				<a class="block" href="/admin/UpdateEmbassy.jsp?EmbassyID=${embassy.getEmbassyID()}">${counter.count}</a>
			</c:when>
			<c:otherwise>
				<a class="block">${counter.count}</a>
			</c:otherwise>
		</c:choose></td>
		<td>${countryDb.getCountryName(embassy.getCountryID())}</td>
		<td>${embassy.getEmbassyName()}</td>
		<td>${embassy.getEmbassyAddr()}</td>
		<td>${embassy.getPhoneNo()}</td>
	</tr>
</c:forEach>

<c:if test="${eList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="5">No data found</td>
	</tr>
</c:if>

</tbody>
</table>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-link"><a href="/admin/AddEmbassy.jsp">Add Embassy</a></div>
</c:if>

</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
