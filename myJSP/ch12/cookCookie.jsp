<%--cookCookie.jsp--%>
<%--쿠키: 부스레기가 생긴다고 해서 웹의 흔적을 뜻함.--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //쿠키는 Client에 정보가 저장되는 기능
    //서버에서 쿠키에 저장될 값을 만든다.
    String cookieName = "myCookie";
    //쿠키 생성
    Cookie cookie = new Cookie(cookieName,"Apple");
    cookie.setMaxAge(60); //1분
    //값 수정
    cookie.setValue("Melone");
    //생성된 쿠키는 Client 전송
    response.addCookie(cookie);
%>
쿠키를 만들었습니다. <br>
쿠키 내용은 <a href="tasteCookie.jsp">여기로</a>