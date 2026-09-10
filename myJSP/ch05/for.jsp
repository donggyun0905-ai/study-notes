<%@ page import="java.util.Random" %>
<%@ page import="ch05.MUtil" %>
<!--for.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String[] topGirlGroups = {
            "BLACKPINK",
            "IVE",
            "aespa",
            "TWICE",
            "LE SSERAFIM"
    };
%>
<%!
    public static String randomColor(){
        Random r = new Random();
        // %02x로 두 자리씩 고정해야 합니다. 안 그러면 0~15 사이 값일 때
        // 한 글자만 나와서 "#5a3" 처럼 깨진 색상 코드가 나올 수 있습니다.
        String rgb = String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        rgb += String.format("%02x", r.nextInt(256));
        return "#"+rgb;
    }
%>
<table border="1">
    <tr>
        <th>번호</th>
        <th>그룹명</th>
    </tr>
    <%for(int i =0;i<topGirlGroups.length;i++){%>
    <tr>
        <td align="center"><%=i+1%></td>
        <td><font color="<%=randomColor()%>"><%=topGirlGroups[i]%></font></td>
    </tr>
    <%}//--for%>
</table>
<!--표현식, out.println방식으로 각각 만들고 그룹명은 랜덤한 색상으로 표현 -->
<table border="1">

    <tr>
        <th>번호</th>
        <th>그룹명</th>
    </tr>
<%
    for(int i =0;i<topGirlGroups.length;i++){
        out.println("<tr>");
        out.println("<td align='center'>" + (i+1) + "</td>");
        out.println("<td><font color='" + MUtil.randomColor() + "'>" + topGirlGroups[i] + "</font></td>");
        out.println("</tr>");
    }
%>
</table><p>