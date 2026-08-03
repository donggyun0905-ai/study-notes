<!-- frm_data.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
//frm1.html에서 요청한 id와 pwd를 받음
String id = request.getParameter("id");
String pwd = request.getParameter("pwd");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	id:
	<%=id%><br> pwd:
	<%=pwd%>

</body>
</html>