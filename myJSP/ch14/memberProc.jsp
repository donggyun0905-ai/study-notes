<!-- memberProc.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<jsp:useBean id="bean" class="ch14.MemberBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
      	boolean result = mgr.insertMember(bean);
		String url = "member.jsp";
		if(result)
			url = "login.jsp";
		response.sendRedirect(url);
%>
