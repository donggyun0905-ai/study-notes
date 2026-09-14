<!--error.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page isErrorPage="true"%>
<%
    //내장객체: 8+1 (exception)
    String msg = exception.getMessage();
%>
<h3>Error Message</h3>
다음과 같은 예외가 발생하였습니다.
<%=msg%>
