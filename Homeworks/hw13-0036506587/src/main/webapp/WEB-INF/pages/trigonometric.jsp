<%@page import="java.text.DecimalFormat"%>
<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8" session="true" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trigonometric calculations</title>
</head>
<body bgcolor="${pickedBgCol}">

	<p>
		You can return to home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>

	<table border="1" style="width: auto;">
		<tr align="right">
			<td><b>Angle in degrees</b></td>
			<td><b>sin(angle)</b></td>
			<td><b>cos(angle)</b></td>
		</tr>
		<c:forEach var="result" items="${results}">
			<tr align="right">
				<td>${result.angle}</td>
				<td>${result.sinAngle}</td>
				<td>${result.cosAngle}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>