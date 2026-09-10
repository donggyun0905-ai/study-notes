<%@ page contentType="text/html; charset=UTF-8"%>
<%
    String str = "오늘은 머 먹지?";
    //myJsp2.jsp를 실행하면 Tomcat 서버(JSP 컨테이너)에서 서블릿 코드로 변환 -> myJsp2_jsp.java
    //jsp가 servelet로 변환된 파일을 저장하는 위치를 지정하는 workDir -> server.xml
    out.print(application.getRealPath("/")+"<br>");
%>
MSG: <%=str%>