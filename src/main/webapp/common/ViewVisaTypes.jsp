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
	<title>Visa Types | Voyager Compass</title>
	
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

<h2>Visa Types</h2>

<jk:status var="status"/>

<div class="page-content">

<table class="data-grid"><thead>
	<tr>
		<th><div>S.No.</div></th>
		<th><div>Type Name</div></th>
		<th><div>Type Abbrivation</div></th>
		<th><div>Type Description</div></th>
	</tr></thead><tbody>
<%
List<VisaType> vtList = new VisaTypeDAO().getVisaTypeList();
request.setAttribute("vtList", vtList);
%>

<c:forEach var="visa" items="${vtList}" varStatus="counter">
	<tr>
		<td class="block-wrapper"><c:choose>
			<c:when test="${sessionScope.role.equals('admin')}">
				<a class="block" href="/admin/UpdateVisaType.jsp?VisaTypeID=${visa.getVisaTypeID()}">${counter.count}</a>
			</c:when>
			<c:otherwise>
				<a class="block">${counter.count}</a>
			</c:otherwise>
		</c:choose></td>
		<td>${visa.getVisaTypeName()}</td>
		<td>${visa.getVisaTypeAbbr()}</td>
		<td>${visa.getVisaTypeDesc()}</td>
	</tr>
</c:forEach>

<c:if test="${vtList.size() == 0}">
	<tr>
		<td class="multi-col" colspan="4">No data found</td>
	</tr>
</c:if>

</tbody></table>

<c:if test="${sessionScope.role.equals('admin')}">
	<div class="admin-link"><a href="/admin/AddVisaType.jsp">Add Visa Type</a></div>
</c:if>

</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
