<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>${entry.title}</title>
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
				<form class="formControls"
					action="<%=request.getContextPath()%>/servleti/main" method="post">
					<span class="formLabel">&nbsp;</span> <input type="submit"
						name="method" value="logout">
				</form>
			</c:otherwise>
		</c:choose>
	</header>

	<h1>${entry.text}</h1>
	<p>Blog je kreiran ${entry.createdAt}</p>

	<p>
		<c:if test="${entry.lastModifiedAt != null}">
			Zadnje modifikacije bloga: ${entry.lastModifiedAt}
		</c:if>
	</p>

	<p>
		<c:if
			test="${sessionScope[\"current.user.nick\"].equals(author.nick)}">
		Editiraj trenutni blog <a
				href="<%=request.getContextPath()%>/servleti/author/${author.nick}/edit?id=${entry.id}">ovdje</a>
		</c:if>
	</p>
	<c:choose>
		<c:when test="${comments.isEmpty()}">
			<c:out value="Trenutno nema komentara"></c:out>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="comment" items="${comments}">
					<li>${comment.message}<br> ${comment.usersEMail}<br>
						Postavljeno ${comment.postedOn}
					</li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>

	<hr>
	<br>
	<div>
		<form
			action="<%=request.getContextPath()%>/servleti/author/${author.nick}/${entry.id}"
			method="post" id="commentForm">
			<div>
				<span class="formLabel">Komentar</span>
				<textarea name="message" form="commentForm" cols="100" rows="5">${form.message}</textarea>
				<c:if test="${form.hasError('message')}">
					<div class="error">
						<c:out value="${form.getError('message')}" />
					</div>
				</c:if>
				<c:choose>
					<c:when test="${sessionScope[\"current.user.id\"] == null}">
						<div>
							<span class="formLabel">Email</span> <input type="email"
								name="email" size="100">
						</div>
						<c:if test="${form.hasError('email')}">
							<div class="error">
								<c:out value="${form.getError('email')}" />
							</div>
						</c:if>
					</c:when>
					<c:otherwise>
						<input type="hidden" name="email"
							value="<%=session.getAttribute("current.user.email")%>">
					</c:otherwise>
				</c:choose>
			</div>
			<div class="formControls">
				<span class="formLabel">&nbsp;</span> <input type="submit"
					value="Dodaj komentar">
			</div>
		</form>
	</div>

	<p>
		Možete se vratiti na main page <a href="<%=request.getContextPath()%>">ovdje</a>
	</p>
</body>
</html>