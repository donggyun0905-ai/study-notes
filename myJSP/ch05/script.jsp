<!-- script.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //주석1
    /*주석2*/
%>

<!-- 선언문(Declaration) -->
<%!
    //필드선언
    String dec = "선언문 변수";
    //메소드 선언
    public String decMethod(){
        return dec;
    }
%>
<!-- 스크립트릿(Scriptlet)-->
<%
    String scriptlet = "스크립트릿";
    out.println("내장 객체를 이용한 출력: " + dec + "<br>");

    String comment = "Comment";
%>
<!--표현식(Expression): -->
선언문1: <%= dec%><br>
선언문2: <%= decMethod()%><br>
스트립트릿: <%= scriptlet%><br>

<!-- jsp 주석 -->
<%-- 예시1: JSP 주석은 브라우저 화면은 물론 '소스 보기'에도 전혀 남지 않습니다. --%>

<%-- 예시2: HTML 주석과 다르게, 안에 JSP 코드를 넣으면 실행 자체가 안 됩니다.
     아래 줄의 dec 값(선언문 변수)은 출력되지 않습니다.
     <%= dec %>
--%>
