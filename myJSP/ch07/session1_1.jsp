<%--session1_1.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String season = request.getParameter("season");
    String fruit = request.getParameter("fruit");

    //세션에 저장된 id값 리턴
    String id = (String)session.getAttribute("idKey");
    int interValTime = session.getMaxInactiveInterval();
    if(id!=null){
%>
    <b><%=id%></b>님 좋아하는 계절과 과일은 <br>
    <b><%=season%></b>과 <b><%=fruit%></b>입니다.<br>
    세션Id: <%=session.getId()%><br>
    세션유지시간: <%=interValTime%>초
<% }else{ %>
    세션의 시간이 경과를 하였거나 다른 이유로 연결을 지속 할 수 없습니다.
    <a href="session1.html">입력폼</a>
<% } %>
