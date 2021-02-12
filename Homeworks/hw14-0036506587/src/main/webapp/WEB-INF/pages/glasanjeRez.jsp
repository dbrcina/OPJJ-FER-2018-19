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
<title>Rezultati glasanja</title>
</head>
<body style="font-size: large;">

	<h1>Rezultati glasanja</h1>
	<p>Ovo su rezultati glasanja.</p>

	<table border="1" class="rez">
		<thead>
			<tr>
				<th>Ime natjecatelja</th>
				<th>Broj glasova</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="result" items="${results}">
				<tr>
					<td>${result.optionTitle}</td>
					<td>${result.votesCount}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<h3>Grafički prikaz rezultata</h3>
	<img src="<%=request.getContextPath()%>/servleti/glasanje-grafika" alt="Pie chart">

	<h3>Rezultati u XLS formatu</h3>
	Rezultati u XLS formatu dostupni su
	<a href="<%=request.getContextPath()%>/servleti/glasanje-xls" title="Tablični prikaz rezultata">ovdje</a>

	<h3>Razno</h3>
	Primjeri pobjednika:
	<ul>
		<c:forEach var="winner" items="${winners}">
			<li><a href="${winner.optionLink}" target="_blank">${winner.optionTitle}</a></li>
		</c:forEach>
	</ul>

</body>
</html>