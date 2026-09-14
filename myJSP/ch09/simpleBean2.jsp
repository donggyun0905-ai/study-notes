<%--simpleBean2.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="bean" class="ch09.SimpleBean"/>
<%--*:private 모든 것 수용--%>
<jsp:setProperty name="bean" property="*"/>
<h3>SimpleBean2</h3>
msg:<jsp:getProperty name="bean" property="msg"/><br>
cnt:<jsp:getProperty name="bean" property="cnt"/><br>
