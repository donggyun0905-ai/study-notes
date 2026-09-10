<!-- expression1.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<%
    String subject[] = {"Java", "JSP", "Flutter", "Python", "Flask", "Spring"};

%>
1. 표현식을 사용하는 방법<p>
<table border = "1">
    <tr>
        <th>번호</th>
        <th>과목</th>
    </tr>
    <%for (int i = 0; i < subject.length; i++) { %>
    <tr>
        <td><%=i+1%></td>
        <td><%=subject[i]%></td>
    </tr>
    <%} // --for%>
</table><p>
    2. 내장객체(out)를 사용하는 방법<p>
<table border = "1">
    <tr>
        <th>번호</th>
        <th>과목</th>
    </tr>
    <%
        for (int i = 0; i < subject.length; i++) {
            out.println("<tr>");
            out.println("<td align = 'center'>" + (i+1) + "</td>");
            out.println("<td>" + subject[i] + "</td>");
            out.println("</tr>");
        }
    %>
</table><p>