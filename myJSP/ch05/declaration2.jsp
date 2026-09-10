<!-- declaration2.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //service 메소드 안에 선언
    String msg = player +" 화이팅";
%>
<!-- !필드로 구성된 곳은 가능 하지만 !가 없는 필드는 불가능-->
<%!
    //선언문에서 선언된 필드 변수는 필드이다.
   String player = "손흥민";
%>
msg: <%=msg%>