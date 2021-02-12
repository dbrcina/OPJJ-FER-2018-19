<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Grafika</title>
</head>
<body>
	<div>
		<%
			ArrayList<String> imena = (ArrayList<String>) request.getAttribute("imena");
			if (!imena.isEmpty()) {
				out.write("Datoteke");
			}
		%>
		<br>
		<c:forEach var="ime" items="${imena}">
			<li><a href="<%=request.getContextPath()%>/doc?path=${ime}">${ime}</a></li>
		</c:forEach>
	</div>

	<form action="create" method="get">
		<table>
			<tr>
				<td><label>Ime datoteke</label></td>
				<td><input type="text" name="ime"></td>
			</tr>
			<tr>
				<td><label>Sadržaj datoteke</label></td>
				<td><textarea name="text" cols="60" rows="10"></textarea></td>
			</tr>
			<tr>
				<td><input type="submit"></td>
			</tr>
		</table>
	</form>
</body>
</html>