<%@page import="java.awt.Color"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<%
	Random random = new Random();
	Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
%>
<meta charset="utf-8">
<style type="text/css">
body {
	color: rgb(
		<%=color.getRed()%>,
		<%=color.getGreen()%>,
		<%=color.getBlue()%>	
	);
	font-size: larger;
}
</style>
<title>Funny story</title>
</head>
<body bgcolor="${pickedBgCol}">

	<p>
		<b style="font-size: x-large;">I swear to God he levitated:</b><br> I
		have a friend who I’ve known since I was very little. One day, when he
		was six, I was at his house when he got this absolutely god-awful
		stomach pain. I mean, he was literally writhing in pain. So, his mom
		took him to the doctor’s office, where the doctor took one look and
		told her to take him to the ER. She feared something along the lines
		of an intestinal rupture. About half way to the hospital, my friend
		suddenly let rip the loudest, most powerful fart any of us had ever
		heard. I swear to God he levitated. We thought the upholstery in the
		car seat had ripped. After a good 30 seconds of intense farting, he
		looked at his mom and said, “I feel all better now!”
	</p>

	<p>
		You can return to the home page <a href="<%=request.getContextPath()%>/index.jsp"
			title="Returns to the home page">here</a>
	</p>
</body>
</html>