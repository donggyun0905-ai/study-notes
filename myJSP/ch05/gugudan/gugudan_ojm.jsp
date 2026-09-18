<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>구구단</title>
<style type="text/css">
table{
text-align: center;
margin: auto;
border: 2px solid green;
}
td, th {
	text-align: center;
	padding: 5px;
	height: 15px;
	width: 100px;
	border: 2px solid green;
	
}

thead {
	background-color: #2d5d78;
}

tbody tr:nth-child(even){
	background: #1C9C44;
}

tbody tr:nth-child(odd){
	background: #2E8A4B;
}

h3 {
	text-align: center;
}
</style>
</head>
<body>
	<h3>구구단을 외자!</h3>
	<table>
		<thead>

			<%
				for (int i = 0; i < 9; i++) {
				String s = Integer.toString(i + 1) + " 단";
			%>
			<th><%=s%></th>
			<%
				}
			%>
		</thead>
		<tbody>
			<%
				for (int i = 0; i < 9; i++) {
			%>
			<tr>
				<%
					for (int j = 0; j < 9; j++) {
					String s = Integer.toString(j + 1) + " X " + Integer.toString(i + 1);
				%>
				<td><%=s%></td>
				<%
					}
				%>
			</tr>
			<%
				}
			%>
		</tbody>

	</table>

</body>
</html>