<%--forwardTag2_1.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String bloodType = request.getParameter("bloodType");
    String name = "홍길동";
%>
<jsp:forward page='<%=bloodType + ".jsp"%>'>
    <jsp:param value="<%=name%>" name = "name"/>
</jsp:forward>