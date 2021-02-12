<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Voting</title>
</head>
<body bgcolor="${pickedBgCol}" style="font-size: large;">

	<h1>Voting for a favorite band;</h1>
	<p>Of the following bands, which is your favorite? Click on link to
		vote!</p>
	<ol>
		<c:forEach var="band" items="${bands.values()}">
			<li><a href="glasanje-glasaj?id=${band.ID}">${band.name}</a></li>
		</c:forEach>
	</ol>

	<p>
		You can return to home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to home page">here</a>
	</p>
</body>
</html>