<%--request1.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //request1.jsp?name=홍길동&studentNum=9123133&gender=man&major=국문학과&hobby=인터넷&hobby=여행
    String name = request.getParameter("name");
    String studentNum = request.getParameter("studentNum");
    String gender = request.getParameter("gender");
    String major = request.getParameter("major");
    String hobby[] = request.getParameterValues("hobby");
%>
name: <%=name%><br>
studentNum: <%=studentNum%><br>
gender: <%=gender%><br>
major: <%=major%><br>
hobby: <% for(int i =0;i<hobby.length;i++){ %>
            <%=hobby[i]%>&nbsp;
        <% } %>

