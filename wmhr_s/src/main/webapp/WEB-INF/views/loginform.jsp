<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<br>
	<center>	<font style="font-size:50px; font-weight: bold;">로그인폼</font>
	<form action="${pageContext.request.contextPath}/login.do">
	<input type="hidden" name="method" value="empLogin">
	<table>
		<tr>
			<td>사원명</td>
			<td><input class="ui-button ui-widget ui-corner-all" type="text" name="name"></td>
		</tr>
		<tr>
			<td>사원코드</td>
			<td><input class="ui-button ui-widget ui-corner-all" type="password" name="ec"></td>
		</tr>
		<tr>
			<td colspan="2">
				<input class="ui-button ui-widget ui-corner-all" type="submit" value="로그인">
				<input class="ui-button ui-widget ui-corner-all" type="reset" value="취소">
			</td>
		</tr>
	</table>
	${requestScope.errormsg}
	</form>
	</center>
</body>
</html>