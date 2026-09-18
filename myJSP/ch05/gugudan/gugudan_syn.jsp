<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Nerko+One&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Ranchers&display=swap" rel="stylesheet">
<link rel ="stylesheet" type ="text/css" href="gugudan_syn.css">
<title>구구단</title>
</head>
<body>
<center>
<h2>***구구단***</h2>
<hr>
<table border="1">
<%
for(int i=0; i<10; i++) {
    %>
    <%="<tr>" %>
    <%
    for(int j=1; j<10; j++) {
    %><%if(i==0 && j==1) {%>
         <%="<td bgcolor=white width=100></td>" %>  <% 
        } else if(j==1) {%>
            <%= "<td align=center bgcolor=LightYellow width=100>" + i + "</td>" %>
        <%} else if(i==0) {
            %><%="<td align=center bgcolor=Moccasin	 width=100>" + j + "단" + "</td>"%> <%
            } else {%>
                <%="<td align=center width=80>" + j + " X " + i + " = " + i*j + "</td>"%>
            <%}%>    
<%    }%> <%="</tr>" %> 
<%} %>
</table>
</center>
</body>
<footer>
모두 화이팅~~~
</footer>
</html>