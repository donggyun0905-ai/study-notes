<!-- page2.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page session ="true"%>
<%
    //세션: 서버에 client의 정보를 저장하는 객체 (단위)
    //사이트 처음 접속 시 부여되는 id값. 16진수 형태의 32자
    String sessionId = session.getId();
    //기본값은 30분
    session.setMaxInactiveInterval(30);//30초

%>
세션ID: <%=sessionId%>
