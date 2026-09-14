<%@ page import="ch09.MUtil" %>
<%@ page import="ch09.SimpleBean" %><%--simpleBean1.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String msg = request.getParameter("msg");
    int cnt = MUtil.parseInt(request,"cnt");
    SimpleBean bean = new SimpleBean();
    bean.setMsg(msg);
    bean.setCnt(cnt);
%>
<h3>SimpleBean1</h3>
msg: <%=bean.getMsg()%><br>
cnt: <%=bean.getCnt()%><br>