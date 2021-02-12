<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>BLOG.net</title>

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
				<form class="formControls" action="<%=request.getContextPath()%>/servleti/main" method="post">
					<span class="formLabel">&nbsp;</span>
					<input type="submit" name="method" value="logout">
				</form>
			</c:otherwise>
		</c:choose>
	</header>

	<c:if test="${sessionScope[\"current.user.id\"] == null}">
		<h1>Logiraj se</h1>
		<form action="main" method="post">
			<div>
				<div>
					<span class="formLabel">Nadimak</span> <input type="text"
						name="nick" value='<c:out value="${record.nick}"/>' size="10">
				</div>
				<c:if test="${record.hasError('nick')}">
					<div class="error">
						<c:out value="${record.getError('nick')}" />
					</div>
				</c:if>
			</div>
			<div>
				<div>
					<span class="formLabel">Lozinka</span> <input type="password"
						name="pwd" size="10">
				</div>
				<c:if test="${record.hasError('pwd')}">
					<div class="error">
						<c:out value="${record.getError('pwd')}" />
					</div>
				</c:if>
			</div>
			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					value="Potvrdi">
			</div>
		</form>

		<h1>Registracija</h1>
		<p>
			Registriraj se <a
				href="<%=request.getContextPath()%>/servleti/register">ovdje</a>
		</p>
	</c:if>
	<h1>Popis registriranih autora</h1>
	<c:choose>
		<c:when test="${users.isEmpty()}">
			Nema registriranih korisnika
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="user" items="${users}">
					<li><a
						href="<%=request.getContextPath()%>/servleti/author/${user.nick}">
							${user.firstName} ${user.lastName} </a></li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
</body>
</html>
