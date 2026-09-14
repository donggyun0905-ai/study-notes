<!--includeTag3.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String bloodType = request.getParameter("bloodType");
    String name = "기안84";
%>
<!-- 표현식에서 ""값이 필요하면 "으로 시작 -->
<jsp:include page='<%=bloodType+".jsp"%>'>
    <jsp:param value="<%=name%>" name="name"/>
</jsp:include>
