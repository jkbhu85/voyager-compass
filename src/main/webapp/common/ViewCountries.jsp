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
	<title>Countries | Voyager Compass</title>
	
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

<h2>Countries</h2>

<jk:status var="status"/>

<div class="page-content">

<table class="data-grid"><thead>
	<tr>
		<th>S.No.</th>
		<th>Country Name</th>
		<th>Country Abbrivation</th>
		<th>Demonym</th>
		<th>Country Description</th>
	</tr></thead><tbody>

<%
List<Country> cList = new CountryDAO().findAllCountries();
request.setAttribute("cList", cList);
%>

<c:forEach var="country" items="${cList}" varStatus="counter">
	<tr>
		<td class="block-wrapper"><c:choose>
		<c:when test="${sessionScope.role.equals('admin')}">
			<a class="block" href="/admin/UpdateCountry.jsp?COUNTRY_ID=${country.getCountryID()}">${counter.count}</a>
		</c:when>
		<c:otherwise>
			${counter.count}
		</c:otherwise></c:choose>
		</td>
		<td>${country.getCountryName()}</td>
		<td>${country.getCountryFullName()}</td>
		<td>${country.getNationality()}</td>
		<td>${country.getCountryDesc()}</td>
	</tr>
</c:forEach>
<c:if test="${cList.size() == 0}">
	<tr>
		<td colspan="5" class="multi-col">No Data Found.</td>
	</tr>
</c:if>
</tbody></table>
<c:if test="${sessionScope.role.equals('admin')}">
		<div class="admin-link"><a href="/admin/AddCountry.jsp">Add Country</a></div>
</c:if>
</div><!-- page-conent -->

		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
