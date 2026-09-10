<!--scriptlet2.jsp-->
<%@ page import="ch05.MUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    for(int a = 0; a < 10; a++){
        out.print("<font color=" + MUtil.randomColor() + ">");
        out.println("오늘은 즐거운 수요일</font><br>");
    }
%>