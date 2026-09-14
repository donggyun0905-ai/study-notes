<!-- teamRead.jsp -->
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
    <title>Team Read</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen py-10">
<div class="max-w-md mx-auto px-4">
    <h1 class="text-2xl font-bold text-gray-800 mb-6">Team Read</h1>

    <div class="bg-white rounded-lg shadow divide-y divide-gray-100">
        <div class="flex px-5 py-3">
            <span class="w-24 text-gray-500 text-sm">번호</span>
            <span class="font-medium text-gray-800"><%=bean.getNum()%></span>
        </div>
        <div class="flex px-5 py-3">
            <span class="w-24 text-gray-500 text-sm">이름</span>
            <span class="font-medium text-gray-800"><%=bean.getName()%></span>
        </div>
        <div class="flex px-5 py-3">
            <span class="w-24 text-gray-500 text-sm">사는곳</span>
            <span class="font-medium text-gray-800"><%=bean.getCity()%></span>
        </div>
        <div class="flex px-5 py-3">
            <span class="w-24 text-gray-500 text-sm">나이</span>
            <span class="font-medium text-gray-800"><%=bean.getAge()%></span>
        </div>
        <div class="flex px-5 py-3">
            <span class="w-24 text-gray-500 text-sm">팀명</span>
            <span class="font-medium text-gray-800"><%=bean.getTeam()%></span>
        </div>
    </div>

    <div class="mt-5 flex flex-wrap gap-2">
        <a href="teamList.jsp"
           class="px-4 py-2 rounded bg-gray-100 text-gray-700 text-sm font-medium hover:bg-gray-200">LIST</a>
        <a href="teamInsert.jsp"
           class="px-4 py-2 rounded bg-blue-600 text-white text-sm font-medium hover:bg-blue-700">INSERT</a>
        <a href="teamUpdate.jsp?num=<%=num%>"
           class="px-4 py-2 rounded bg-amber-500 text-white text-sm font-medium hover:bg-amber-600">UPDATE</a>
        <a href="teamDelete.jsp?num=<%=num%>"
           class="px-4 py-2 rounded bg-red-600 text-white text-sm font-medium hover:bg-red-700">DELETE</a>
        <a href="teamDelete?num=<%=num%>"
           class="px-4 py-2 rounded border border-red-600 text-red-600 text-sm font-medium hover:bg-red-50">DELETE2</a>
    </div>
</div>
<%@ include file="/common/themeToggle.jspf" %>
</body>
</html>
