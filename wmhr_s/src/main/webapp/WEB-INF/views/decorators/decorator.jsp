<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <jsp:include page="header.jsp" /> 
	<title><%-- <decorator:title /> --%>wmhr</title>
	<decorator:head />
	
<c:if test="${requestScope.errorMsg != null}">
	<script>alert("${requestScope.errorMsg }");</script>
</c:if>


</head>
<body>
<table class="topTable" style="width:100%; height:1000px;">
<tr style="height:50px">
	<td colspan="2">
	
<jsp:include page="top.jsp" />
	 <jsp:include page="menu.jsp" /> 
	
	</td>
</tr>
<tr style="height:300px">
	<td valign="top" style="width:100%; height:800px;"><center><decorator:body /></center></td>
</tr>

<tr>
	<td colspan="2">
	<hr>
	<center>
	<jsp:include page="bottom.jsp" />
	</center>
	</td>
</tr>
</table>
</body>
</html>
