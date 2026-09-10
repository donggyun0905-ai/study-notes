<!-- actionTag.jsp-->
<%@ page contentType="text/html; charset=UTF-8" %>
<!--스크립트 요소를 액션태그로 제공 <- 거의 사용안함. 불편함 -->
<jsp:declaration>
    String str = "테스트";
</jsp:declaration>

<jsp:scriptlet>
    String str1 = "테스트1";
</jsp:scriptlet>

<jsp:expression>
    str + " : " + str1
</jsp:expression>
