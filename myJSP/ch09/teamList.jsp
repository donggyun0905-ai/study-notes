<%@ page import="ch09.TeamMgr" %>
<%@ page import="ch09.TeamBean" %>
<%@ page import="java.util.Vector" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    TeamMgr mgr = new TeamMgr();
    Vector<TeamBean> vlist = mgr.listTeam();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Team List</title>
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-50 min-h-screen py-10">
<div class="max-w-3xl mx-auto px-4">
    <h1 class="text-2xl font-bold text-gray-800 mb-6">Team List</h1>

    <div class="bg-white rounded-lg shadow overflow-hidden">
        <table class="w-full text-sm text-left">
            <thead class="bg-gray-100 text-gray-500 uppercase text-xs">
                <tr>
                    <th class="px-4 py-3">번호</th>
                    <th class="px-4 py-3">이름</th>
                    <th class="px-4 py-3">사는곳</th>
                    <th class="px-4 py-3">나이</th>
                    <th class="px-4 py-3">팀명</th>
                    <th class="px-4 py-3"></th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-100">
<%
    for (TeamBean bean : vlist) {
%>
                <tr class="hover:bg-gray-50">
                    <td class="px-4 py-3">
                        <a href="teamRead.jsp?num=<%=bean.getNum()%>"
                           class="text-blue-600 hover:underline font-medium"><%=bean.getNum()%></a>
                    </td>
                    <td class="px-4 py-3"><%=bean.getName()%></td>
                    <td class="px-4 py-3"><%=bean.getCity()%></td>
                    <td class="px-4 py-3"><%=bean.getAge()%></td>
                    <td class="px-4 py-3"><%=bean.getTeam()%></td>
                    <td class="px-4 py-3 text-right">
                        <button onclick="location.href='teamRead.jsp?num=<%=bean.getNum()%>'"
                                class="px-3 py-1 text-xs rounded bg-gray-100 text-gray-700 hover:bg-gray-200">이동</button>
                    </td>
                </tr>
<%
    }
%>
            </tbody>
        </table>
    </div>

    <div class="mt-4 flex items-center justify-between">
        <p class="text-sm text-gray-500">총 <%=vlist.size()%>건</p>
        <a href="teamInsert.jsp"
           class="inline-block px-4 py-2 rounded bg-blue-600 text-white text-sm font-medium hover:bg-blue-700">INSERT</a>
    </div>
</div>
<%@ include file="/common/themeToggle.jspf" %>
</body>
</html>
