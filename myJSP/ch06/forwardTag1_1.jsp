<!--forwardTag1_1.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String id = request.getParameter("id");
    String pwd = request.getParameter("pwd");
%>
id: <%=id %><br>
pwd: <%=pwd %><br>
<!--forward 화면이 보여지는 기능은 없고 Controll 역할을 한다 -->
<!-- include 액션 태그와 동일하게 request(요청 바구니) 도 같이 넘어감 -->
<jsp:forward page="forwardTag1_2.jsp"/>