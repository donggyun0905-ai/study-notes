<!-- frm8.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- frm8.html 입력한 data를 받아서 처리 페이지 -->
<%
	String id = request.getParameter("id"); // 요청한 id 받기
	String pwd = request.getParameter("pwd"); // 요청한 pwd 받기
	String url = request.getParameter("url"); // 요청한 url 받기
	response.sendRedirect(url);
%>
id: <%=id%><br>
pwd: <%=pwd%><br>
url: <%=url%><br>