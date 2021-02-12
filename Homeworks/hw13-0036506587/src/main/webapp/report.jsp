<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Report</title>
</head>
<body bgcolor="${pickedBgCol}" style="font-size: larger;">

	<h1>OS usage</h1>
	<p>Here are the results of OS usage in survey that we completed in
		which 100 people took part.</p>
	<img alt="Pie chart" src="<%=request.getContextPath()%>/reportImage">
	<p>
		You can return to home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>
	
</body>
</html>