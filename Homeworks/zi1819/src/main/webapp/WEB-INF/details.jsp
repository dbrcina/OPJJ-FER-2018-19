<%@ page language="java" contentType="text/html; charset=utf8"
	pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Grafika - ${ime}</title>
</head>
<body>
	<h2>Ime datoteke: ${ime}</h2>

	<div>
		<table>
			<tr>
				<td>LINE</td>
				<td>${line}</td>
			</tr>
			<tr>
				<td>CIRCLE</td>
				<td>${circle}</td>
			</tr>
			<tr>
				<td>FCIRCLE</td>
				<td>${fcircle}</td>
			</tr>
			<tr>
				<td>FTRIANGLE</td>
				<td>${ftriangle}</td>
			</tr>
		</table>
	</div>

	<div>
		<img src="<%=request.getContextPath()%>/picture">
	</div>
	
	<div>
		Povratak na <a href="<%=request.getContextPath()%>/main">glavnu</a>
		stranicu.
	</div>
</body>
</html>