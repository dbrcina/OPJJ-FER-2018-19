<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Error page</title>
</head>
<body bgcolor="${pickedBgCol}" style="font-size: large;">

	<h1 style="color: red;">AN ERROR OCCURED</h1>
	<p style="color: red; font-size: larger;">${error}</p>
	<p>
		You can return to home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>

</body>
</html>