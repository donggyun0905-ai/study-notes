<!-- logout.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<%
       //세션 파기, 삭제, 제거
       session.invalidate();
	   response.sendRedirect("login.jsp");	
%>
