<%--response1.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //지정한 페이지로 Client 응답 request 정보를 넘어가지 않음
    response.sendRedirect("response2.jsp");
%>