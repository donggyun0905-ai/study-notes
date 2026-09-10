<!--scriptlet1.jsp-->
<%@page import="java.util.Random" %>
<%@page contentType="text/html; charset=UTF-8" %>
<%!
    public static String randomColor(){
        Random r = new Random();
        String rgb = Integer.toHexString(r.nextInt(256));
        rgb += Integer.toHexString(r.nextInt(256));
        rgb += Integer.toHexString(r.nextInt(256));
        return "#"+rgb;
    }
%>
<%
    System.out.println("Tomcat 서버 콘솔창 스트림");
    //out.print:클라이언트 브라우저 전송 스트림
    for(int i=0;i<10;i++){
        out.println("<font color=" + randomColor() + ">");
        out.println("오늘은 즐거운 수요일<br>");
        out.println("</font>");
    }
%>