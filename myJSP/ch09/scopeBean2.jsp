<%--scopeBean2.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //세션에 특정한 값만 제거
    session.removeAttribute("sBean");
    //세션의 무효화, 초기화, 제거 <- 새로운 세션 객체가 생성. client에 sessionId가 전송이 됩니다.
    session.invalidate();
    response.sendRedirect("scopeBean1.jsp");
%>