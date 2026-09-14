<%--loginProc.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String id = request.getParameter("id");
    String pwd = request.getParameter("pwd");
    boolean result = false;
    if(id != null && pwd!=null)
        result = true;
    String msg = "로그인에 실패 하였습니다.";
    String url = "login.jsp";
    if(result){
        msg = "로그인 성공입니다.";
        url = "loginOK.jsp";
        session.setAttribute("idKey",id);
    }
%>
<%--잘못된 로그인 처리 방식--%>
<script>
    alert("<%=msg%>")
    location.href="<%=url%>";
</script>