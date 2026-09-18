<%@ page contentType="text/html; charset=EUC-KR"%>
<html>
<head>
<title>备备窜 免仿</title>
<style>
	div {
		text-align: center;
	}
	td {
		background-color: mistyrose;
		color: #289AFF;
		padding: 15px;
	}
	h3 {
		color: #289AFF;
	}
</style>
</head>
<body>
	<div>
		<h3>备 备 窜</h3>
		<table align="center" border="1">
		<%
		for(int i = 1; i < 10; i++) {
			out.print("<tr>");
			for(int j = 2; j < 10; j++) {
					out.print("<td>");
					out.print(Integer.toString(j)
					    + " X " + Integer.toString(i) 
					    + " = " + Integer.toString(j * i)); 
					out.print("</td>");
			}
			out.print("</tr>");
		}
		%>
		</table>
	</div>
</body>
</html>

