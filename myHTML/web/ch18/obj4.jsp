<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
        String id = request.getParameter("id");
        String pwd = request.getParameter("id");
%>
ID : <%=id%><br>
PWD : <%=pwd%><br>
<button onclick="history.back()">뒤로</button>