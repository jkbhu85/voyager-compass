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
	<title>Update Department | Voyager Compass</title>
	
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
<h2>Update Department</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
String deptno = request.getParameter("deptNo");
Department dept = new DepartmentDAO().findDeptatmentById(Integer.parseInt(deptno));

List<Profile> pList = new ProfileDAO().findAllEmployeesByDepartmentId(deptno);

request.setAttribute("dept", dept);
request.setAttribute("pList", pList);
%>
<form method="post" name="register" action="/UpdateDeptAction"><table class="form-grid">
	<tr>
		<td>Department Name<span class="mnd">*</span></td>
		<td><input type="text" name="DeptName" value="${dept.getDepartmentName()}" /></td>
		<td></td>
	</tr>

	<tr>
		<td>Department Abbr<span class="mnd">*</span></td>
		<td><input type="text" name="DeptAbbr" value="${dept.getDepartmentAbbr()}" /></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Incharge ID</td>
		<td><span class="select-wrapper"><select name="EmpID">
			<option value="0">Select Incharge</option>
		<c:forEach var="profile" items="${pList }">
			<option value="${profile.getEmpid()}">${profile.getFirstname()}&nbsp;${profile.getLastname()} (${profile.getLoginID()})</option>	
		</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Update Deparment" class="button"/></td>
		<td></td>
	</tr>
</table>
<input type="hidden" name="deptno" value="<%=deptno%>">
</form>
</div>
</div><!-- page-conent -->

<script>
var fields = [
{name:'DeptName', display:'Deparment Name', rules:'required|max_length[40]'},
{name:'DeptAbbr', display:'Department Abbr', rules:'required|max_length[40]'}
];

function handleFormErrors(errors, event) {
	event.preventDefault();

	var errWrapper = document.getElementById("formErrors");

	if (errors.length == 0) {
		errWrapper.innerHTML = '';
		document.register.submit();
		return true;
	}

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
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
