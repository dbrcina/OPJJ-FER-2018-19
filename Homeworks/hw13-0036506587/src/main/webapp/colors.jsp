<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Color factory</title>
</head>
<body bgcolor="${pickedBgCol}">

	<h2>Choose a background color:</h2>
	<ul>
		<li><a href="setcolor?color=white">WHITE</a></li>
		<li><a href="setcolor?color=red">RED</a><br></li>
		<li><a href="setcolor?color=green">GREEN</a><br></li>
		<li><a href="setcolor?color=cyan">CYAN</a><br></li>
	</ul>

	<p>
		You can return to the home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>
</body>
</html>