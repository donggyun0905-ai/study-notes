<%-- checkName.jsp : 이름 중복 확인용. 화면 없이 결과만 텍스트로 돌려줌 (AJAX 전용) --%>
<%@ page import="ch09.TeamMgr" %>
<%@ page contentType="text/plain; charset=UTF-8" %>
<%
    request.setCharacterEncoding("UTF-8");

    String name = request.getParameter("name");
    TeamMgr mgr = new TeamMgr();

    boolean dup = (name != null && !name.trim().isEmpty()) && mgr.isNameDuplicate(name);
%><%= dup ? "dup" : "ok" %>