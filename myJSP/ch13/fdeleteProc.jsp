<%@ page import="ch13.MUtil" %><%--fdeleteProc.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<jsp:useBean id="mgr" class="ch13.FileloadMgr"/>
<%
    String snum[] = request.getParameterValues("fch");
    int num[] = MUtil.toIntArr(snum);//문자열 배열을 정수 배열로 리턴
    /*for (int i =0;i<num.length;i++){
        out.print(num[i] + "&nbsp;");
    }*/
    //mgr.deleteFile(num);
    mgr.deleteFile2(num);
    response.sendRedirect("flist.jsp");
%>