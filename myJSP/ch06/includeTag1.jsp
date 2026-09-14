<!--includeTag1.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String name = request.getParameter("name");
%>
<!--include 액션태그는 .request 정보까지 제어권 넘어갈때 전달-->
<jsp:include page="includeTagTop1.jsp"/>
include 액션태그의 body입니다.<p>
