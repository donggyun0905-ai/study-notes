<!--include1.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<!--include 지시자는 여러개의 파일이 합쳐서 하나의 자바파일 (서블릿)로 변환되는 기능-->
<%@include file="top.jsp" %>
include 지시자의 body입니다.
top.jsp에서 선언한 변수 str: <%=str%>
<%@include file="bottom.jsp"%>
