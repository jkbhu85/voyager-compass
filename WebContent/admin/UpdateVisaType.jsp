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
	<title>Update Visa Type | Voyager Compass</title>
	
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
<h2>Update Visa Type</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<%
String vtId = request.getParameter("VisaTypeID");
int visaTypeId = Integer.parseInt(vtId);
VisaType visaType = new VisaTypeDAO().getVisaType(visaTypeId);

if (visaType == null) {
	NotiMsg noti = new NotiMsg(NotiMsg.FAIL, "Invalid Visa Type.");
	request.getSession().setAttribute("status", noti);
	response.sendRedirect("/common/ViewVisaTypes.jsp");
	return;
}
request.setAttribute("cList", new CountryDAO().getCountryList());
request.setAttribute("visaType", new VisaTypeDAO().getVisaType(visaTypeId));
%>

<form method="post" name="register" action="/UpdateVisaTypeAction">
<input type="hidden" readonly="readonly" value="${visaType.getVisaTypeID()}" name="VisaTypeID">
<table class="form-grid">
	<tr>
		<td>Country<span class="mnd">*</span></td>
		<td><span class="select-wrapper"><select name="CountryID">
			<option value="0">Select Country</option>
			<c:forEach var="country" items="${cList }">
				<option value="${country.getCountryID()}">${country.getCountryName()}</option>
			</c:forEach>
		</select><span class="select-arrow"></span></span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Type Name<span class="mnd">*</span></td>
		<td><input type="text" name="VisaTypeName" value="${visaType.getVisaTypeName()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Type Abbreviation<span class="mnd">*</span></td>
		<td><input type="text" name="VisaTypeAbbr" value="${visaType.getVisaTypeAbbr()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Description<span class="mnd">*</span></td>
		<td><input type="text" name="VisaTypeDesc" value="${visaType.getVisaTypeDesc()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Max Duration of Stay (days)</td>
		<td><input type="text" name="MaxDur" value="${visaType.getMaxDuration()}"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Required in Advance</td>
		<td>
			<input type="radio" name="ReqAdv" value="1" id="reqAdvYes" class="radio">
			<label for="reqAdvYes"><span><span></span></span>Yes</label>
			<input type="radio" name="ReqAdv" value="0" id="reqAdvNo" class="radio">
			<label for="reqAdvNo"><span><span></span></span>No</label>
		</td>
	</tr>
	
	<tr>
		<td>Elegibility criteria<span class="mnd">*</span></td>
		<td><textarea name="VisaTypeEleg">${visaType.getVisaEleg()}</textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Stamping Guidlines</td>
		<td><textarea name="StampGuide">${visaType.getStampGuide()}</textarea></td>
		<td></td>
	</tr>
	
	<tr>
		<td></td>
		<td><input type="submit" value="Update Visa Type" class="button"/></td>
		<td></td>
	</tr>
</table></form>
</div>
</div><!-- page-conent -->

<script>

window.addEventListener("load", function(){
	document.register.CountryID.value = '${visaType.getCntVtFk()}';
	document.register.ReqAdv.value = '${visaType.getReqAdv()}'
});

var fields = [
{name:'VisaTypeName', display:'Visa Type Name', rules:'required|max_length[50]'},
{name:'VisaTypeAbbr', display:'Visa Type Abbreviation', rules:'required|max_length[50]'},
{name:'VisaTypeDesc', display:'Visa Type Description', rules:'required|max_length[100]'},
{name:'VisaTypeEleg', display:'',rules:'required|max_length[100]'},
{name:'MaxDur',       display:'Max Duration of stay',rules:'numeric'},
{name:'StampGuide',  display:'Stamping Guidelines',rules:'max_length[200]'},
{name:'CountryID',  display:'Country',rules:'required|callback_check_select'},
{name:'ReqAdv',  display:'Required in advance',rules:'required'}
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

	validator.registerCallback('check_select', function(value){
		return document.register.CountryID.selectedIndex > 0;
	})
	.setMessage('check_select', 'The <i>%s</i> field is required.');
</script>

</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
