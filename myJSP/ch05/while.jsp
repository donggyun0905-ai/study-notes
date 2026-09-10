<%@ page import="ch05.MUtil" %>
<!--while.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String msg = request.getParameter("msg");
    //네트워크로 요청된 모든 값들은 문자열 리턴
    int number = Integer.parseInt(request.getParameter("number"));
    int count = 0;
    while(number>count){
        out.println("<font color=" + MUtil.randomColor() + ">");
        out.println(msg + "<br>");
        out.println("</font>");
        count++;
    }

%>