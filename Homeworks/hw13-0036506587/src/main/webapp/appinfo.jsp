<%@page import="java.util.concurrent.TimeUnit"%>
<%@page import="java.io.IOException"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="true"%>

<%!private void formatTime(JspWriter out, long start, long end) throws IOException {
		// whole interval in millis
		final long milliseconds = end - start;
		// days
		final long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
		// hours
		final long hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));
		// minutes
		final long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
		// seconds
		final long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
		// milliseconds
		final long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
		// format print
		out.print(
				days + " days, " + 
				hours + " hours, " + 
				minutes + " minutes, " + 
				seconds + " seconds, " + 
				milliseconds + " milliseconds."
		);
	}%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Application info</title>
</head>
<body bgcolor="${pickedBgCol}" style="font-size: large;">

	<h3>
		This web application has been running for
		<%
			long start = (Long) application.getAttribute("life");
			long end = System.currentTimeMillis();
			formatTime(out, start, end);
		%>
	</h3>
	
	<p>
		You can return to the home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>
</body>
</html>