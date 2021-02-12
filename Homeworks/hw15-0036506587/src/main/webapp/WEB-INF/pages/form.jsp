<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.error {
	font-family: fantasy;
	font-weight: bold;
	font-size: 0.9em;
	color: #FF0000;
	padding-left: 110px;
}

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
<title>Kreiraj novi blog</title>
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

	<h1>Unesi podatke</h1>
	<form action="${action}" method="post" id="newForm">
		<div>
			<div>
				<span class="formLabel">Naslov</span> <input type="text"
					name="title" value='<c:out value="${form.title}"/>' size="100">
			</div>
			<c:if test="${form.hasError('title')}">
				<div class="error">
					<c:out value="${form.getError('title')}" />
				</div>
			</c:if>
			<span class="formLabel">Tekst</span>
			<textarea name="text" form="newForm" cols="100">${form.text}</textarea>
			<c:if test="${form.hasError('text')}">
				<div class="error">
					<c:out value="${form.getError('text')}" />
				</div>
			</c:if>
		</div>
		<div class="formControls">
			<span class="formLabel">&nbsp;</span> <input type="submit"
				value="Potvrdi">
		</div>
	</form>
	<p>
		Možete se vratiti na main page <a href="<%=request.getContextPath()%>">ovdje</a>
	</p>
</body>
</html>