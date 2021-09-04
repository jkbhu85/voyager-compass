<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:choose>
<c:when test="${not empty sessionScope.role}">
	<c:choose>
		<c:when test="${sessionScope.role.equals('admin')}">
			<jsp:include page="/oth/adminMenu.html"/>
		</c:when>
		<c:when test="${sessionScope.role.equals('employee')}">
			<jsp:include page="/oth/empMenu.html"/>
		</c:when>
	</c:choose>
</c:when>
</c:choose>