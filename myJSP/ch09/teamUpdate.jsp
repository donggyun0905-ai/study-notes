<!-- teamUpdate.jsp -->
<%@page import="ch09.TeamBean"%>
<%@page import="ch09.MUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch09.TeamMgr"/>
<%
	int num = 0;
	TeamBean bean = null;
	if(request.getParameter("num")==null){
		//num값이 정상적으로 넘어오지 않을때
		response.sendRedirect("teamList.jsp");
		return;
	}else if(!MUtil.isNumeric(request.getParameter("num"))){
		//숫자의 형태의 num이 아닐때
		response.sendRedirect("teamList.jsp");
		return;
	}else{
		num = MUtil.parseInt(request,"num");
		bean = mgr.getTeam(num);
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team Update</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen py-10">
<div class="max-w-md mx-auto px-4">
<h1 class="text-2xl font-bold text-gray-800 mb-6">Team Update</h1>

<form name="frm" method="post" action="teamUpdateProc.jsp"
      class="bg-white rounded-lg shadow divide-y divide-gray-100">
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">번호</label>
        <input name="num" value="<%=bean.getNum()%>" readonly
               class="flex-1 px-3 py-1.5 rounded border border-gray-200 bg-gray-50 text-gray-500 text-sm">
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">이름</label>
        <input name="name" value="<%=bean.getName()%>"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-amber-400">
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">사는곳</label>
        <input name="city" value="<%=bean.getCity()%>"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-amber-400">
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">나이</label>
        <input name="age" value="<%=bean.getAge()%>"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-amber-400">
    </div>
    <div class="flex items-center px-5 py-3">
        <label class="w-24 text-gray-500 text-sm">팀명</label>
        <input name="team" value="<%=bean.getTeam()%>"
               class="flex-1 px-3 py-1.5 rounded border border-gray-300 text-sm focus:outline-none focus:ring-2 focus:ring-amber-400">
    </div>
    <div class="px-5 py-4">
        <input type="submit" value="UPDATE"
               class="w-full py-2 rounded bg-amber-500 text-white text-sm font-medium hover:bg-amber-600 cursor-pointer">
    </div>
</form>

<div class="mt-4">
    <a href="teamRead.jsp?num=<%=num%>"
       class="px-4 py-2 rounded bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200 inline-block">READ</a>
</div>
</div>
<%@ include file="/common/themeToggle.jspf" %>
</body>
</html>
