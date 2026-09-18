<!-- postGuestBookProc.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
<jsp:useBean id="bean" class="guestbook.GuestBookBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
      	if(bean.getSecret()==null)
      		bean.setSecret("0");//비밀글을 체크하지 않으면 secret 속성 자체가 넘어오지 않음
      	mgr.insertGuestBook(bean);
      	response.sendRedirect("showGuestBook.jsp");
%>
