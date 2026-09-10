<%@ page import="java.util.Random" %>
<%@ page import="ch05.MUtil" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<body bgcolor="lightblue">
<div align="center">
<h1>구구단</h1>
<table border="1" align="center">
    <tr>
<%
    for(int dan = 1; dan < 10; dan++){
        out.println("<th bgcolor='lightgray'>" + dan + "단</th>");
    }
%>
    </tr>

<%
    for(int i = 1; i < 10; i++){
        out.println("<tr>");
        for(int j = 1; j < 10; j++){
            out.println("<td align='center'><font color='" + MUtil.randomColor() + "'>" + j + " * " + i + " = " + (j*i) + "</font></td>");
        }
        out.println("</tr>");
    }
%>
</table><p>
</div>
</body>
</html>

<%!
    public static String randomColor(){
        Random r = new Random();
        String rgb = String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        return "#"+rgb;
    }
%>

