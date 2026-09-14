<%@ page import="ch09.ScopeBean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%--
    scope: useBean으로 만든 데이터가 저장되는 범위
    1.page: 해당 페이지
    2.request: HTTP 요청이 살아있는 동안 유효. 액션태그(include, forward)
    3.session : 세션 객체에 저장
    4.application: 서버가 종료 될때까지 사용가능
    공통메소드 : setAttribute,getAttribute,removeAttribute
--%>
<jsp:useBean id="pBean" scope="page" class="ch09.ScopeBean"/>
<%--session은 동일한 id로 값이 있다면 재사용--%>
<jsp:useBean id="sBean" scope="session" class="ch09.ScopeBean"/>
<%
    ScopeBean sBean2 = new ScopeBean();
    session.setAttribute("sBean2",sBean2);
    
%>
<jsp:setProperty name="pBean" property="num" value="<%=pBean.getNum() + 10%>"/>
<jsp:setProperty name="sBean" property="num" value="<%=sBean.getNum() + 10%>"/>
<h3>Scope Bean</h3>
pBean num값: <jsp:getProperty name="pBean" property="num"/><br>
sBean num값: <jsp:getProperty name="sBean" property="num"/><br>
<a href="scopeBean2.jsp">scopeBean2</a>
