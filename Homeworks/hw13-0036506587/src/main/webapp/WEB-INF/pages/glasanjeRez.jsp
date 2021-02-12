<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<style type="text/css">
table.rez td {
	text-align: center;
}
</style>
<meta charset="utf-8">
<title>Voting results</title>
</head>
<body bgcolor="${pickedBgCol}" style="font-size: large;">
	<h1>Voting results</h1>
	<p>Here are voting results.</p>

	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Band</th>
				<th>Number of votes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${results.entrySet()}">
				<tr>
					<td>${result.key}</td>
					<td>${result.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h3>Graphic display of results</h3>
	<img src="<%=request.getContextPath()%>/glasanje-grafika" alt="Pie chart">

	<h3>Results in XLS format</h3>
	Results in XLS format are available
	<a href="glasanje-xls" title="Tablični prikaz rezultata">here</a>

	<h3>Various</h3>
	Examples of songs of the winning bands:
	<ul>
		<c:forEach var="winner" items="${winners}">
			<li><a href="${winner.link}" target="_blank">${winner.name}</a></li>
		</c:forEach>
	</ul>

	<p>
		You can return to voting page <a href="<%=request.getContextPath()%>/glasanje"
			title="Returns to the voting page">here</a><br>
		You can return to home page <a href="<%=request.getContextPath()%>/index.jsp" 
			title="Returns to home page">here</a>
	</p>

</body>
</html>