<%@page import="ch05.MUtil"%>
<%@ page contentType="text/html; charset=EUC-KR" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<head> 
<title>Gugudan</title> 
<style type="text/css">
/* 	.bg1 { background-color:#dd4132; }
	.bg2 { background-color:#f7786b; }
	.bg3 { background-color:#f7cac9; }
	.bg4 { background-color:#fae03c; }
	.bg5 { background-color:#79c753; }
	.bg6 { background-color:#034f84; }
	.bg7 { background-color:#91A8d0; }
	.bg8 { background-color:#98ddde; }
	.bg9 { background-color:#91A8d0; }
	
	table {
  overflow: hidden;
} */
td:nth-of-type(1):hover, td:nth-of-type(1):hover + td
, td:nth-of-type(1):hover + td + td
, td:nth-of-type(1):hover + td + td + td
, td:nth-of-type(1):hover + td + td + td + td
, td:nth-of-type(1):hover + td + td + td + td + td
, td:nth-of-type(1):hover + td + td + td + td + td+ td
, td:nth-of-type(1):hover + td + td + td + td + td+ td+ td
, td:nth-of-type(1):hover + td + td + td + td + td+ td+ td+ td
{
	background-color: black;
}
td:hover{
	background-color: <%=MUtil.randomColor() %>;
}
table tr:nth-child(even) {
  background-color : #91A8d0;
}
table tr:nth-child(odd){
	background-color : #98ddde;
}
table tr:nth-child(1){
	background-color : lightgray;
}


/*
td:nth-of-type(2):hover{
	background-color:#91A8d0;
}
td:hover + td{
	background-color: #f7786b;
}
td:hover+td+td{
	background-color: #f7cac9;
}

td:hover + td + td + td{
	background-color: #fae03c;
}
td:hover + td + td + td + td{
	background-color: #79c753;
}
-td:hover - td{
	background-color: #79c753;
}
tr.row:hover{
	background-color: #000000;
}
*/

</style>
</head> 
<body> 
<h2 align="center">구구단을 외자</h2> 
<table class="bgColors" align="center" border="1" > 
<tr bgcolor="lightgrey"> 
	<td></td> 
	<% for (int i = 2; i < 10; i++) { %> 
	<td align="center" width="80" > 
	<%=i%>
	</td> 
	<% } %> 
	<% for (int i = 1; i < 10; i++) { %> 
<tr> 
	<td align="center" bgcolor="lightgrey" width="50">
<%=i%></td> 
<% for (int j = 2; j < 10; j++) { %> 
<td align="center">
<%=j%> X <%=i%> = <%=j * i%>
</td> 
<% } %>
 </tr> 
 <% } %> </tr> 
</table> 
</body> 