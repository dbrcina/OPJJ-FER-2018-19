<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Home page</title>
</head>

<body bgcolor="${pickedBgCol}" style="font-size: large;">

	<h1>THIS IS A HOME PAGE</h1>

	<h2>Change background color</h2>
	<p>
		On provided link <a href="colors.jsp"
			title="Link to a new page where you can change background color">
			Background color chooser </a> , you can change background color
		throughout this session.<br> Each web page will have the same
		color you generated.
	</p>

	<h2>
		Trigonometric calculator for <i>sin(x)</i> and <i>cos(x)</i>
	</h2>
	<p>
		Here you can provide two parameters: <i>initial angle</i> and <i>final
			angle</i> (in <b>degrees</b>).<br> Once you press <i>Tabel</i>, you
		will be redirected to a new page where table is created and filled
		with values of trigonometric functions <i>sinus</i> and <i>cosinus</i>.
		<br> All values from <i>initial angle</i> to <i>final angle</i>
		are calculated. <br> Keep in mind if you enter <i>inital
			angle</i> that is greater than <i>final angle</i>, their values will be
		swaped.
	</p>
	<form action="trigonometric" method="GET">
		Initial angle:<br> 
		<input type="number" name="a" step="1" value="0"><br>
		Final angle:<br>
		<input type="number" name="b" step="1" value="90"><br> 
		<input type="submit" value="Tabel"> 
		<input type="reset"	value="Reset">
	</form>

	<h2>Some funny story</h2>
	<p>
		<a href="stories/funny.jsp" title="Link to funny story">This</a> link
		will lead you to a new page where you will find some short funny
		story.<br> Its font color will dynamically change each time you
		refresh or enter that page.
	</p>

	<h2>OS usage</h2>
	<p>
		<a href="report.jsp" title="Link to pie chart">Here</a> you can see
		the testing results about <i>OS</i> usage.
	</p>

	<h2>XLS document</h2>
	<p>
		On <a href="powers?a=1&b=100&n=3" title="Download table">this</a> link
		you can generate and download a .xls document.<br> Generated
		excel document consists of <i>n</i> sheets.<br>On every sheet
		there is a 101 x 2 table where first column is filled with numbers
		from [-100,100] as determined by <i>a</i> and <i>b</i> URL parameters
		and second is filled with i-th powers as determined by i-th sheet.<br>
		Initially, <i>a</i> is set to 1, <i>b</i> to 100 and <i>n</i> to 3.
		So, there will be 3 sheets and on every sheet there will be a 2-column
		table filled with numbers from <i>[a,b]</i> and its i-th power.
	</p>

	<h2>Application info</h2>
	<p>
		<a href="appinfo.jsp" title="Link to appplication info">Here</a> you
		can check for how long this web app has been running.
	</p>

	<h2>Voting</h2>
	<p>
		Vote for your favourite band <a href="glasanje"
			title="Link to voting page">here</a>.
	</p>
</body>
</html>