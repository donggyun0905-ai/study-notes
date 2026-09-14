<!--includeTag2.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String siteName = request.getParameter("siteName");
%>
요청한 사이트명: <%=siteName%>
<!-- param: 요청한 페이지로 동적으로 필요한 값이 필요할때 -->
<jsp:include page="includeTagTop2.jsp">
    <jsp:param name="id" value="aaa"/>
    <jsp:param name="pwd" value="1234"/>
</jsp:include>