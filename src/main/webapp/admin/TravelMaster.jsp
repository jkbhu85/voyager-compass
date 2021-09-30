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
	<title>Add Travel | Voyager Compass</title>
	
	<link type="text/css" rel="stylesheet" href="/css/global.css"/>
	<link type="text/css" rel="stylesheet" href="/css/form-style.css"/>
	<link type="text/css" rel="stylesheet" href="/css/primary-menu.css"/>
	<link type="text/css" rel="stylesheet" href="/css/jquery-ui.min.css"/>
	
	<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
	<script type="text/javascript" src="/js/jquery-ui.min.js"></script>
	<script type="text/javascript" src="/js/validate.min.js"></script>
	<script type="text/javascript" src="/js/form-util.js"></script>
	
	<jsp:include page="/oth/ieAndOther.html" />
<script>
function compareDates(startDate, endDate) {
	var sdp = startDate.split('-');
	var edp = endDate.split('-');
	
	var day1 = parseInt(sdp[0],10);
	var month1 = parseInt(sdp[1],10);
	var year1 = parseInt(sdp[2],10);
	
	var day2 = parseInt(edp[0],10);
	var month2 = parseInt(edp[1],10);
	var year2 = parseInt(edp[2],10);
	
	if (year1 < year2) return -1;
	else if (year1 == year2) {
		if (month1 < month2) return -1;
		else if (month1 == month2){
			if (day1 < day2) return -1;
			else if (day1 == day2) return 0;
			else return 1;
		}
		else return 1;
	}
	else return 1;
		
}

function searchEmps(evt) {
	initProgress();
	
	var alertUser = (evt != undefined);
	
	// checking if all required fields have data
	if (document.register.visaTypeId.selectedIndex <= 0) {
		if (alertUser) {
			alert("Select Visa Type");
		}
		return;
	}
	
	var startDate = document.register.StartDate.value;
	
	if (startDate.length == 0) {
		if (alertUser) {
			alert("Select start date");
		}
		return;
	}
	
	var endDate = document.register.EndDate.value;
	if (endDate.length == 0) {
		if (alertUser) {
			alert("Select end date");
		}
		return;
	}
	
	if (compareDates(startDate, endDate) > 0) {
		if (alertUser) {
			alert("End Date should be equal or after Start Date");
		}
		return;
	}
	
	var visaTypeId = document.register.visaTypeId.value;
	var url = "/SearchEmpAction";
	
	var xhttp = null;
	
	if (XMLHttpRequest) {
		xhttp = new XMLHttpRequest();
	} else if(ActiveXML) {
		xhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if (xhttp != null) {
		xhttp.onreadystatechange = function() {
			// request is complete
			if (this.readyState == 4) {
				endProgress();
				// request gets response
				if (this.status == 200) {
					try {
						var obj = JSON.parse(this.responseText);
						
						if (obj.status == 'ok') {
							//console.log(this.responseText);
							handleData(obj.data);
						} else {
							alert("Request could not be completed. Try again later");
						}
					} catch (e) {
						alert("Error occurred while fetching data from server. Try again later.");
						console.log(e);
					}
				} else {
					// request failed
					alert("Error occurred while fetching data from server. Try again later.");
				}
			}
			if (this.readyState == 3) {
				beginProgress();
			}
		}
		xhttp.open("POST", url, true);
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhttp.send('visaTypeId=' + visaTypeId + '&startDate=' + startDate + '&endDate=' + endDate);
	}
	
}

function disappear() {
	$('#bg').remove();
}

function handleData(data) {
	initShowData();
	
	var colNames = new Array();
	console.log("Array length: " + data.length);
	
	if (!data || data.length == 0) {
		$('#dg').html('No Employee found');
		return;
	}
	
	colNames.push('S.No.');
	for (var prop in data[0]) {
		colNames.push(prop.toUpperCase());
	}
	
	
	
	// setup header
	setupHeader(colNames);
	
	for (var i = 0; i < data.length; i++) {
		addRow(data[i], colNames, i+1);
	}
	
	$('#empCount').html(data.length + ' Employees');

	// when user selects a row
	// setting details to repective elements
	$("#dg tbody tr").click(function(evt){
        var e = evt.target;
        
        if (e.tagName == 'TD') e = e.parentNode;
        var cld = e.children;
   
        var val = cld[1].innerHTML + " (" + cld[2].innerHTML + ")";
        document.register.VisaIssueDate.value = cld[3].innerHTML; 
        
        console.log(e.getAttribute('id') + ' ' + val );
        $('[name="empId"]').val(e.getAttribute('id'));
        $('#empDetails').html(val);
        disappear();
    });

	//console.log("differnet event listener setup");
}

function addRow(obj, colNames, sno) {
	var s = '';
	var i = 0;
	for (var prop in obj) {
		if (i == 0) {
			s += '<tr id="' + obj[prop] + '">';
			s += '<td>' + sno + '</td>';
		} else {
			s += '<td>' + obj[prop] + '</td>';
		}
		
		i++;
	}
	s += '</tr>';
	$('#dg tbody').append(s);
}

function test() {
	var obj = [{id:'a', name:'b'}];
	handleData(obj);
}

function setupHeader(colNames) {
	var s = '<tr>';
	var i = 1;
	for (i = 0; i < colNames.length; i++) {
		if (i == 1) continue;
		s += '<th>' + colNames[i] + '</th>';
	}
	s += '</tr>';
	$('#dg thead').html(s);
	var type = document.register.visaTypeId.value;
	//console.log(type);
	var str = 'Select Employee ';
	
	if (type === 'N') str += '(No passport)';
	else if (type === 'P' ) str += '(With Passport)';
	else if (type != '0') str += '(With Visa)';
	str += '<div id="empCount"></div>';
	
	$('#dlgTitle').html(str);
}

function initShowData() {
	function setDimens() {
		//console.log('sizing objects');
		
		// minimum width for data wrapper
		var minW = 600;
		var minH = 400;
		
		$wnd = $(window);
		var wndW = $wnd.width();
		var wndH = $wnd.height();
		
		$('#bg').width(wndW).height(wndH);
		$('#dw').height(minH).width(minW);
		
		$fg = $('#fg');
		var left = ((wndW - $fg.outerWidth())/2 - 0);
		var top = ((wndH - $fg.outerHeight())/2 - 0);
		
		if (left < 0) left = "0px";
		else left += "px";
		
		if (top < 0) top = "0px";
		else top += "px";
		
		//console.log('ww,wh,fw,fh,top,left  ' + wndW + ',' + wndH  + ',' + $fg.width() + ',' + $fg.height() + ',' + top + ',' + left)
		
		$fg.css({'top':top,'left':left});
		
		if (wndW < $fg.outerWidth()) {
			$('#clBut').css({'left':'0px','right':'', 'top':'0px'});
		} else {
			$('#clBut').css({'left':'','right': '20px', 'top':'20px'});
		}
	}
	
	$(window).on('resize', setDimens);
	
	$(document).keyup(function(evt){
		if (evt.keyCode === 27) disappear();
	});
	
	var s = '<div id="bg"><div id="fg"><h2 id="dlgTitle">Select Employee</h2><div id="clBut">X</div>'+
		'<div id="dw"><table id="dg"><thead></thead><tbody></tbody></table></div></div></div>';
	$('body').append(s);
	$('#clBut').click(disappear);
	
	setDimens();
}


function addDataToSelect(arr, sel) {
	sel.selectedIndex = -1;
	sel.innerHTML = '';
	var i;
	
	for(i = 0; i < arr.length; i++) {
		sel.innerHTML += '<option value="' + arr[i].id + '">' 
		+ arr[i].name + ' (' + arr[i].abbr + ')</option>';
	}
}

function clearEmpSel() {
	document.register.empId.value = "";
	document.register.VisaIssueDate.value = "";
}

</script>

<style>
#clBut{
	width:36px;
	line-height:36px;
	background:#ccc;
	color:#fff;
	font-size:14px;
	font-weight:bold;
	text-align:center;
	position:absolute;
	right:20px;
	top:20px;
	border-radius:100%;
}
#clBut:hover{
	background:#00695c;
}
#empCount{
	font-size:13px;
	color:#666;
	padding:2px;
}
#bg{
	background:rgba(0,0,0,0.6);
	position:fixed;
	top:0;
	left:0;
}
#fg{
	background:#fff;
	border-radius:2px;
	padding:40px;
	position:absolute;
}
#dlgTitle{
	font-size:24px;
	font-weight:normal;
	margin-bottom:20px;
	color:#00695c;
}
#dw{
	overflow:auto;
}
#dg{
	width:100%;
	border-collapse:collapse;
}
#dg th{
	border-top:1px solid #e7e7e7;
	border-bottom:1px solid #e7e7e7;
	padding:10px 10px;
	text-align:left;
}
#dg tbody tr:hover{
	background:#f7f7f7;
}
#dg tbody tr td {
	border-bottom:1px solid #e7e7e7;
}
#dg td{
	padding:8px 10px;
}
#empDetails{
	border-bottom:2px solid #ccc;
}
#empDetails:focus{
	border-bottom:2px solid #00695c;
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
<h2>Add Travel</h2>

<jk:status var="status"/>

<div class="page-content">
<div class="form-wrapper">
<div id="formErrors" tabindex="0"></div>

<form method="post" action="/TravelMasterAction" name="register"><table class="form-grid">
<%
int workId = Integer.parseInt((String) request.getParameter("WorkId"));
	Work work = null;
	
	if (workId > 0) {
		work = new WorkDAO().get(workId);
	} else {
		response.sendRedirect("/index.jsp");
		return;
	}
	
	request.setAttribute("work", work);
	
	ProfileDAO prof = new ProfileDAO();
	
	request.setAttribute("pList", prof.findAllEmployees());
%>
	<tr>
		<td>Work ID</td>
		<td>${work.getWorkId()}<input type="hidden" value="${work.getWorkId()}" name="WorkId" readonly="readonly"/></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Incharge ID</td>
		<td>${work.getInchName()}</td>
		<td></td>
	<tr>
<%
List<VisaType> vtList = new VisaTypeDAO().findAllVisaTypesByCountryId(work.getCntId() );
request.setAttribute("vtList", vtList);
%>
	<tr>
		<td>Country</td>
		<td>${work.getCntName()}</td>
		<td></td>
	<tr>
	
	<tr>
		<td>Required Visa Type</td>
		<td><span class="select-wrapper">
			<select name="visaTypeId" onchange="clearEmpSel()">
				<option value="0">Select</option>
				<option value="N">No passport required</option>
				<option value="P">Only passport required</option>
			<c:forEach var="vt" items="${vtList}">
				<option value="${vt.getVisaTypeID()}">${vt.getVisaTypeName()}&nbsp;${vt.getVisaTypeAbbr()}</option>
			</c:forEach>
			</select>
			<span class="select-arrow"></span>
		</span></td>
		<td></td>
	</tr>
	
	<tr>
		<td>Start Date<span class="mnd">*</span></td>
		<td><input type="text" name="StartDate" placeholder="Click to add date" onchange="clearEmpSel()"/>
		</td>
		<td></td>
	<tr>
	
	<tr>
		<td>End Date<span class="mnd">*</span></td>
		<td><input type="text" name="EndDate" placeholder="Click to add date" onchange="clearEmpSel()"/></td>
		<td></td>
	<tr>
	
	<tr>
		<td>Employee ID<span class="mnd">*</span></td><!--
		<td><span class="select-wrapper">
			<select name="empId"></select>
			<span class="select-arrow"></span>
		</span></td>-->
		<td>
			<input type="hidden" name="empId" value="" />
			<input type="hidden" name="VisaIssueDate" value=""/>
			<div id="empDetails"></div>
		</td>
		<td><span class="dark-link" onclick="searchEmps(this)">Search Employees</span></td>
	</tr>
	
	<tr>
		<td>Instructions</td>
		<td><textarea name="Instruction"></textarea></td>
		<td></td>
	<tr>
	
	<tr>
		<td></td>
		<td><input type="submit" name="Submit" value="Add Travel Info"  class="button"/></td>
		<td></td>
	<tr>
</table></form>
</div>
</div><!-- page-conent -->

<script>
$(document).ready(function(){
	var myFmt = {
			dateFormat:'dd-mm-yy',
			minDate:new Date()
			};
	$('[name="StartDate"]').datepicker(myFmt);
	$('[name="EndDate"]').datepicker(myFmt);
	
	$('[name="StartDate"]').keypress(preventKbInput);
	$('[name="EndDate"]').keypress(preventKbInput);
	
	function preventKbInput(event){
		event.preventDefault();
	}
	
	var h = $('[name="StartDate"]').height();
	$('#empDetails').height(h);
});

var fields = [
{name:'visaTypeId', display:'Required Visa Type', rules:'required|callback_check_select'},
{name:'StartDate', display:'Start Date', rules:'required'},
{name:'EndDate', display:'End Date', rules:'required'},
{name:'empId', display:'Employee', rules:'required|callback_check_visa'},
{name:'Instruction', display:'Instructions', rules:'max_length[200]'}
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
	return value != '0';
})
.setMessage('check_select', 'The <i>%s</i> field is required.');

// check visa with conditions P and M
validator.registerCallback('check_visa', function(){
	var val = document.register.VisaIssueDate.value;
	var requirement = document.register.visaTypeId.value;
	
	if (requirement === 'P' || requirement === 'N' || requirement === '0') return true;
	
	if (parseInt(requirement, 10) > 0) {
		if (val === '') return false;
	}
	
	return true;
})
.setMessage('check_visa', 'The <i>%s</i> field was not set correctly. Please select again.');
</script>
		
</main>
<jsp:include page="/oth/footer.html"/>
</body>
</html>
