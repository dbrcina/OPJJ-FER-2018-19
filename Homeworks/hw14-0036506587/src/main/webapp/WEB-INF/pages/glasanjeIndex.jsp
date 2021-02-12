<%@page import="hr.fer.zemris.java.hw14.models.Poll"%>
<%@page import="hr.fer.zemris.java.hw14.constants.AttributesConstants"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Glasanje</title>
</head>
<body style="font-size: large;">

	<%
		Poll poll = (Poll) session.getAttribute(AttributesConstants.POLL);
	%>

	<h1><%=poll.getTitle()%></h1>
	<p><%=poll.getMessage()%></p>

	<ol>
		<c:forEach var="pollOption" items="${pollOptions}">
			<li>
				<a href="<%=request.getContextPath()%>/servleti/glasanje-glasaj?optionID=${pollOption.optionID}">
					${pollOption.optionTitle}
				</a>
			</li>
		</c:forEach>
	</ol>
	
</body>
</html>