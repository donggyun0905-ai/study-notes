<%@ page import="ch09.TeamMgr" %>
<%@ page import="ch09.TeamBean" %>
<%@ page import="ch09.MUtil" %><%--teamInsertProc.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<jsp:useBean id="bean" class="ch09.TeamBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
    mgr.insertTeam(bean);
    response.sendRedirect("teamList.jsp");
%>