<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Postovi autora: '${author.nick}'</title>
<style type="text/css">
.formLabel {
	display: inline-block;
	width: 100px;
	font-weight: bold;
	text-align: right;
	padding-right: 10px;
}

.formControls {
	margin-top: 10px;
}
</style>
</head>
<body>
	<header style="background-color: lime;">
		<c:choose>
			<c:when test="${sessionScope[\"current.user.id\"] == null}">
				<c:out value="Niste ulogirani"></c:out>
			</c:when>
			<c:otherwise>
				<c:out value="Trenutno ulogiran autor: "></c:out>
				<c:out value="${sessionScope[\"current.user.fn\"]}"></c:out>
				<c:out value="${sessionScope[\"current.user.ln\"]}"></c:out>
				<form class="formControls"
					action="<%=request.getContextPath()%>/servleti/main" method="post">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="method" value="logout">
				</form>
			</c:otherwise>
		</c:choose>
	</header>
	<c:choose>
		<c:when test="${blogs.isEmpty()}">
			<c:out value="Trenutno nema postova"></c:out>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="blog" items="${blogs}">
					<li><a href="${author.nick}/${blog.id}">${blog.title}</a></li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

	<c:if test="${sessionScope[\"current.user.nick\"].equals(author.nick)}">
		<form
			action="<%=request.getContextPath()%>/servleti/author/${author.nick}/new">
			<input type="submit" value="Novi blog">
		</form>
	</c:if>

	<p>
		Možete se vratiti na main page <a href="<%=request.getContextPath()%>">ovdje</a>
	</p>
</body>
</html>