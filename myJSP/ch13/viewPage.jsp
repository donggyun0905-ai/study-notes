<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="java.io.File" %><%--viewPage.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //업로드 파일 저장 위치
    final String SAVEFOLDER = "C:/Jsp/myapp/src/main/webapp/ch13/storage/";
    //업로드 파일명 인코딩
    final String ENCODING = "UTF-8";
    //업로드 파일 크기 제한
    final int MAXSIZE = 1024*1024*50;//50mb
    try{
        //request는 매개변수로 넘기는 순간 null
        //DefaultFileRenamePolicy: 중복파일이 생기면 자동으로 파일명 뒤에 index 번호값 생김.
        //MultipartRequest 객체가 성공적으로 생성되는 순간 서버 파일이 업로드.
        MultipartRequest multi = new MultipartRequest(request,SAVEFOLDER,MAXSIZE,ENCODING, new DefaultFileRenamePolicy());
        //getParameter()는 encoding 생성자 인자가 안 먹혀서 한글이 깨짐
        //ISO-8859-1로 잘못 읽힌 걸 바이트로 되돌린 뒤 UTF-8로 다시 디코딩
        String user = new String(multi.getParameter("user").getBytes("8859_1"), "UTF-8");
        String title = new String(multi.getParameter("title").getBytes("8859_1"), "UTF-8");

        //파일정보
        String fileName = multi.getFilesystemName("myfile");
        String fileType = multi.getContentType("myfile");
        File f = multi.getFile("myfile");
        long len = 0;
        if(f!=null)
            len = f.length();
%>
user: <%=user%><br>
title: <%=title%><br>
fileName: <%=fileName%><br>
fileType: <%=fileType%><br>
len: <%=len%>byte<br>
<%
    }catch(Exception e){
        e.printStackTrace();
    }
%>