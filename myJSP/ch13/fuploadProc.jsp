<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch13.FileloadMgr"/>
<%
    //저장 후에 flist.jsp 리턴
    mgr.uploadFile(request);
    response.sendRedirect("flist.jsp");
%>