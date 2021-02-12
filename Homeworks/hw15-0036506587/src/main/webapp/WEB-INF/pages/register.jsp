<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Registracija</title>

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

	<h1>Registracija</h1>

	<form action="register" method="post">
		<div>
			<div>
				<span class="formLabel">Ime</span> <input type="text" name="fn"
					value='<c:out value="${record.firstName}"/>' size="10">
			</div>
			<c:if test="${record.hasError('fn')}">
				<div class="error">
					<c:out value="${record.getError('fn')}" />
				</div>
			</c:if>
		</div>
		<div>
			<div>
				<span class="formLabel">Prezime</span> <input type="text" name="ln"
					value='<c:out value="${record.lastName}"/>' size="10">
			</div>
			<c:if test="${record.hasError('ln')}">
				<div class="error">
					<c:out value="${record.getError('ln')}" />
				</div>
			</c:if>
		</div>
		<div>
			<div>
				<span class="formLabel">Email</span> <input type="text" name="email"
					value='<c:out value="${record.email}"/>' size="20">
			</div>
			<c:if test="${record.hasError('email')}">
				<div class="error">
					<c:out value="${record.getError('email')}" />
				</div>
			</c:if>
		</div>
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
				value="Pohrani">
		</div>
	</form>
	<p>
		Možete se vratiti na main page <a href="<%=request.getContextPath()%>">ovdje</a>
	</p>
</body>
</html>