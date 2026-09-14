<!--includeTagTop1.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //includeTag1.html에서 요청된 requset 정보 같이 요청됨.
    String name = request.getParameter("name");
%>
include 액션태그의 Top입니다.<p>
<b><%=name%></b> 파이팅!!
<hr color="red" width="40%" align="left">