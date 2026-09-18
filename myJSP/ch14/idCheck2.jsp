<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<%
      	String id = request.getParameter("id");
		boolean result = mgr.duplicationId(id);
		if(result)
			out.print("duplicate");
		else
			out.print("available");
%>