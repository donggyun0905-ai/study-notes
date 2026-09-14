<!--page3.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page isELIgnored="true"%>
<%@page trimDirectiveWhitespaces="false" %>
<%
    String site = "JSPStudy.co.kr";
    request.setAttribute("site",site);
%>
사이트명: ${site}
