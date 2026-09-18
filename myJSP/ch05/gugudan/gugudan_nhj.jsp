<%@ page  contentType="text/html; charset=EUC-KR"%>

<h1 align="center">구구단</h1>
<table border="1" align="center">
	<tr>
		<%for (int i = 2; i <= 9; i++) {%>
			<th style="font-weight: bold; font-size: 25px"><%=i + " 단"%></th>
		<%}%>
	</tr>
		<%for (int i = 1; i <= 9; i++) { //줄수%>
	<tr>
		<%
			for (int j = 2; j <= 9; j++) {
		%>
		<td onmouseover="this.style.background='blue'" onmouseout="this.style.background='black'" style="background-color: black; color: white; font-size: 23px; font-weight: bold" ><%=j + " X " + i + " = " + j * i%></td>
		<%}%>
	</tr>
	<%}%>
</table>