<%@page import="ch09.TeamMgr"%>
<%@page contentType="text/plain; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	if(name == null || name.trim().equals("")){
		out.print("empty");
		return;
	}
	TeamMgr mgr = new TeamMgr();
	out.print(mgr.checkName(name.trim()) ? "duplicate" : "ok");
%>
