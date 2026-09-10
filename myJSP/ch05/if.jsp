<!-- if.jsp-->
<!-- html://localhost/myapp/ch05/if.jsp?name=aaa&color=blue-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //if.html에 입력한 name과 color를 받아옴
    String name = request.getParameter("name");
    String color = request.getParameter("color");
    String msg = "";
    if(color.equals("blue")){
        msg = "파란색";
    }else if(color.equals("red")){
        msg = "빨간색";
    }else if(color.equals("ornage")){
        msg = "오렌지색";
    }else{
        msg = "기타";
        color = "white";
    }
%>
<body bgcolor="<%=color%>">
<%=name%>님이 좋아하는 색상은 <%=msg%>입니다.
</body>
<%=name+" / "+color%>