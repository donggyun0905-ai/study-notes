<%@ page import="ch07.MUtil" %><%--request2.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String protocol = request.getProtocol();
    int port= request.getServerPort();
    //ip주소값
    String remoteAddr= request.getRemoteAddr();
    String method= request.getMethod();
    String uri= request.getRequestURI();
    StringBuffer url= request.getRequestURL();
    String query= request.getQueryString();
    String ageParam = request.getParameter("age");
%>
protocol: <%=protocol%><br>
port: <%=port%><br>
<!-- 0:0:0:0:0:0:0:1 : IPv6값 -> 127.0.0.1 : IPv4 -->
<!-- Run > Run Configurations > Tomcat 선택 VM arguments : -Djava.net.preferIPv4Stack=true -->
remoteAddr: <%=remoteAddr%><br>
method: <%=method%><br>
uri: <%=uri%><br>
url: <%=url%><br>
query: <%=query%><br>
<% if (ageParam != null) { %>
    age (직접 파싱): <%= Integer.parseInt(ageParam) %><br>
    age (MUtil 사용): <%= MUtil.parseInt(request, "age") %><br>
<% } %>

<%--action: 지정하지 않으면 현재 페이지 호출--%>
<form method="post">
    age: <input type="text" name="age" value="23">
    <input type="submit" value="SEND">
</form>

