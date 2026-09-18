<%@ page contentType="text/html; charset=EUC-KR" %>
<style type = "text/css">

table {font-size : 18pt;}
table th{color : #ffeee7;}
table tr{color : #713948;}

tbody tr:nth-child(even) {background-color:#fff8dc;}
tbody tr:nth-child(odd) {background-color:#e5ccc8;}

table tr:hover{background-color: #fef2f0;}
</style>
<body bgcolor ="fdf5e6">
<div align="center">
<h1 style ="color : #6f3b48">구구단</h1>
<table border="10">	
	<tbody>
	<thead>
	<tr style= "text-shadow:0.15em 0.15em 0.15em #e5ccc8"; bgcolor ="#522632";>
	
		<%for (int i = 2; i <= 9; i++) {%>
			<th><%=i + " 단"%></th>
		<%}%>
	
	</tr>
	</thead>
	<%for (int i = 1; i <= 9; i++) { //줄수%>
	<tr>
		<%
			for (int j = 2; j <= 9; j++) {
		%>
		<td><%=j + " X " + i + " = " + j * i%></td>
		<%}%>
	</tr>
	<%}%>
	</tbody>
</table>
</div>
</body>
