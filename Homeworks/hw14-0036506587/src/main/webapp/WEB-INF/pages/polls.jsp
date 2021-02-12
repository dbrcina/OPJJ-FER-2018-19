<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Ankete</title>
</head>
<body style="font-size: large;">
	<h1>Lista dostupnih anketa</h1>
	<ul>
		<c:forEach var="poll" items="${polls}">
			<li><a
				href="<%=request.getContextPath()%>/servleti/glasanje?pollID=${poll.pollID}">${poll.title}</a></li>
		</c:forEach>
	</ul>
</body>
</html>