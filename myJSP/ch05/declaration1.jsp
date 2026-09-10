<!-- declaration1.jsp
<%@ page contentType="text/html; charset=UTF-8" %>
<%!
   int one; //필드는 초기화를 jvm 해줌.
   int two = 1;

   //이 메소드는 현재 페이지만 사용가능
   public int plus(){
       return one+two;
   }
%>
plus 메소드 호출: <%= plus()%>