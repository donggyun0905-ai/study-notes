<!--page1.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.Date"%>
<%@page import="java.net.*,java.util.Vector"%>
<!-- pageEncoding: JSP 페이지 코드 인코딩. 만약 선언하지 않으면 charset이 대신의 역할 -->
<%@page pageEncoding="UTF-8"%>
<%
 Date d = new Date();
%>
현재의 날짜와 시간은? <%=d.toLocaleString()%>