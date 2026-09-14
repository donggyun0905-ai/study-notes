<!--teamUpdateProc.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<jsp:useBean id="bean" class="ch09.TeamBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
    //수정 후에 teamRead.jsp 리턴
    mgr.updateTeam(bean);
    response.sendRedirect("teamRead.jsp?num=" + bean.getNum());
%>