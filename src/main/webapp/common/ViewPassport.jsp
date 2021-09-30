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
	<title>Employee Passport | Voyager Compass</title>
	
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
<h2>Employee Passport</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
int empId = Integer.parseInt(request.getParameter("empId"));
ProfileDAO profileDb = new ProfileDAO();

if (!profileDb.doesProfileIdExists(empId)) {
	request.getSession().setAttribute("status", new NotiMsg(NotiMsg.FAIL, "Employee does not exist."));
	response.sendRedirect("/index.jsp");
	return;
}

String role = (String) session.getAttribute("role");
String loginId = (String) session.getAttribute("user");

if ("employee".equals(role) && !loginId.equals(profileDb.findLoginIdByEmployeeId(empId))) {
	response.sendRedirect("/NotFound.jsp");
	return;
}

PassportDAO pptDb = new PassportDAO();
int pptId = pptDb.findPptIdFromEmp(empId);

Passport ppt = pptDb.findPassportById(pptId);
request.setAttribute("ppt", ppt);

if (ppt == null) {
	response.sendRedirect("/NotFound.jsp");
	return;
}

@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
loginId = profileDb.findLoginIdByEmployeeId(empId);
Profile profile = profileDb.findProfile(loginId, path);
request.setAttribute("profile", profile);

Department dept = new DepartmentDAO().findDeptatmentById(profile.getDeptID());
request.setAttribute("dept", dept);

request.setAttribute("visaDb", new VisaDAO());
%>

<table class="profile-grid">
<tr>
	<td>Employee name</td>
	<td>${profile.getFirstName()}<span></span> ${profile.getLastName()}</td>
</tr>

<tr>
	<td>Department</td>
	<td>${dept.getDepartmentName()}</td>
</tr>

<tr>
	<td>Passport no</td>
	<td>${ppt.getPptNo()}</td>
</tr>

<tr>
	<td>Passport type</td>
	<td>${ppt.getPptType()}</td>
</tr>

<tr>
	<td>Date of issue</td>
	<td>${ppt.getIssueDate()}</td>
</tr>
<tr>
	<td>Date of expire</td>
	<td>${ppt.getExpiryDate()}</td>
</tr>

<tr>
	<td>Birth place</td>
	<td>${ppt.getBirthPlace()}</td>
</tr>

<tr>
	<td>Place issued at</td>
	<td>${ppt.getPlaceIssued()}</td>
</tr>

<tr>
	<td>Address on passport</td>
	<td>${ppt.getAddress()}</td>
</tr>
</table>

<div class="side-links">
<c:if test="${visaDb.getVisaIdFromPpt(ppt.getPptId()) > 0}">
	<a class="dark-link" href="/common/ViewVisas.jsp?pptId=${ppt.getPptId()}">View Visa</a>
</c:if>
<c:if test="${sessionScope.role.equals('admin')}">
	<a class="light-link" href="/admin/AddVisa.jsp?pptId=${ppt.getPptId()}">Add Visa</a>
</c:if>
</div>
</div>
</div><!-- page-content -->

<script>

</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
