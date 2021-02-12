<%@ page import="java.util.Date,java.util.Calendar" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Primjer bloka u kojem možemo deklarirati funkcije -->

<html>
   <body>
     <table>
     	<tr><td>varA</td><td><%= request.getAttribute("varA") %></td></tr>
     	<tr><td>varB</td><td><% Double b = (Double)request.getAttribute("varB"); out.print(b); %></td></tr>
     	<tr><td>zbroj</td><td><%= request.getAttribute("zbroj") %></td></tr>
     </table>
     
     <table>
     	<tr><td>varA</td><td><c:out value="${varA}"/></td></tr>
     	<tr><td>varB</td><td><c:out value="${varB}"/></td></tr>
     	<tr><td>zbroj</td><td><c:out value="${zbroj}"/></td></tr>
     </table>
     
     <table>
     	<tr><td>varA</td><td>${requestScope.varA}</td></tr>
     	<tr><td>varB</td><td>${requestScope.varB}</td></tr>
     	<tr><td>zbroj</td><td>${requestScope.zbroj}</td></tr>
     </table>
   </body>
</html>