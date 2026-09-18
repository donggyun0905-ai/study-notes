<%@page import="ch05.MUtil"%>
<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<title>Insert title here</title>
</head>
<body>
	<h3 align="center">구구당ㅋ</h3>
	<table align="center" border="1" background="https://static.wikia.nocookie.net/pokemon/images/5/5e/%EA%B5%AC%EA%B5%AC_%EA%B3%B5%EC%8B%9D_%EC%9D%BC%EB%9F%AC%EC%8A%A4%ED%8A%B8.png/revision/latest?cb=20170404234109&path-prefix=ko">
		<tr>
			<%for(int i = 2; i<= 9; i++){ %>
			<th style="background-color:<%=MUtil.randomColor()%>; font-size:30px;"><%=i + "단" %></th>
			<%} %>
		</tr>
		<%for (int i = 2; i <= 9; i++) { %>
			<tr>
			<%for (int j = 2; j <= 9; j++) { %>
				<%if(j%2==0){ %>
				<td align="center" style="color: <%= MUtil.randomColor()%>; font-size:20px;" width=100px height=80px>
				<%}else { %>
				<td align="center" style="color: <%= MUtil.randomColor()%>; font-size:20px; background-color: rgba(50, 115, 220, 0.3);" width=100px height=80px>
				<%} %>
					<%=i + "X" + j + "=" + i*j %>
				</td>
			<%} %>
			</tr>
		<%} %>
	</table>
</body>
</html>