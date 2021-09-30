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
	<title>Employee Visas | Voyager Compass</title>
	
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
<h2>Employee Visas</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
VisaDao visaDb = new VisaDao();
PassportDao pptDb = new PassportDao();
ProfileDao profileDb = new ProfileDao();
DepartmentDao deptDb = new DepartmentDao();

request.setAttribute("visaDb", visaDb);
request.setAttribute("pptDb", pptDb);
request.setAttribute("profileDb", profileDb);
request.setAttribute("deptDb", deptDb);


// pass id must exist
int pptId = Integer.parseInt(request.getParameter("pptId"));
//System.out.println("pptid: " + pptId);
Passport ppt = pptDb.findPassportById(pptId);

if (ppt == null) {
	request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
	return;
}

//visa must exist
int visaId = visaDb.findVisaIdFromPassportId(pptId);
//System.out.println("visaid: " + visaId);
List<Visa> vList = visaDb.findAllVisasByPassportId(pptId);

if (vList.size() == 0) {
	request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
	return;
}
 
int empId = ppt.getEmpId();

String role = (String) session.getAttribute("role");
String loginId = (String) session.getAttribute("user");

//System.out.println("loginId: " + loginId);
// if employee is visiting this page then 
// visa should be his own
//System.out.println("emploginid: " + profileDb.getUserID(empId));
if ("employee".equals(role) && !loginId.equals(profileDb.findLoginIdByEmployeeId(empId))) {
	request.getRequestDispatcher("/NotFound.jsp").forward(request, response);
	return;
}


@SuppressWarnings("deprecation")
String path = request.getRealPath("/userimages");
String empLoginId = profileDb.findLoginIdByEmployeeId(ppt.getEmpId());
Profile profile = profileDb.findProfile(empLoginId, path);

Department dept = deptDb.findDeptatmentById(profile.getDeptID());

request.setAttribute("vList", vList);
request.setAttribute("ppt", ppt);
request.setAttribute("dept", dept);
request.setAttribute("profile", profile);
%>

<table class="profile-grid">
<tr>
	<td>Employee name</td>
	<td>${profile.getFirstName()}&nbsp;${profile.getLastName()}</td>
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

<c:forEach var="visa" items="${vList}" varStatus="counter">
<tr>
	<td colspan="2" class="profile-grid-heading">${counter.count}.&nbsp;&nbsp;${visa.getVisaCountry()} &nbsp;(${visa.getVisaTypeName()})</td>
</tr>

<tr>
	<td>Visa Number</td>
	<td>${visa.getVisaNo()}</td>
</tr>

<tr>
	<td>Issue Date</td>
	<td>${visa.getIssueDate()}</td>
</tr>

<tr>
	<td>Expire Date</td>
	<td>${visa.getExpireDate()}</td>
</tr>

<tr>
	<td>Max Visits</td>
	<td>${visa.getMaxVisits() == -1 ? "M" : visa.getMaxVisits()}</td>
</tr>

<tr>
	<td>Visit count</td>
	<td>${visa.getVisitCount()}</td>
</tr>

<tr>
	<td>Place of issue</td>
	<td>${visa.getPlaceIssued()}</td>
</tr>

<tr>
	<td>Cost</td>
	<td>${visa.getVisaCost()}</td>
</tr>

<c:if test="${visa.getCancelStatus() != 0}"><tr>
	<td>Cancel Status</td>
	<td>${visa.getCancelDate()}</td>
</tr></c:if>

<tr>
	<td>Comments</td>
	<td>${visa.getComments()}</td>
</tr>
</c:forEach>

</table>

<c:if test="${visaDb.getVisaIdFromPpt(ppt.getPptId()) > 0}">
<div class="side-links">
<c:if test="${sessionScope.role.equals('admin')}">
	<a class="light-link" href="/admin/ViewEmployee.jsp?username=${profile.getLoginID()}">View Profile</a>
</c:if>
</div>
</c:if>
</div>
</div><!-- page-content -->

<script>

</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
