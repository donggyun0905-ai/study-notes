<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<%
    String cPath = request.getContextPath();
    String id = request.getParameter("id");
    String pwd = request.getParameter("pwd");

    boolean result = mgr.loginMember(id, pwd);
    if(result){
        session.setAttribute("idKey",id);
    }
    response.sendRedirect("login.jsp");
%>