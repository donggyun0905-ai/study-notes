<!-- updateGuestBookProc.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
<jsp:useBean id="bean" class="guestbook.GuestBookBean"/>
<jsp:setProperty property="*" name="bean"/>
<%
		if(bean.getSecret()==null)
				bean.setSecret("0");
		mgr.updateGuestBook(bean);
%>
<script>
	//open창의 입장에서 자신을 오픈한 창이름은 opener <- showGuestBook.jsp
	opener.location.reload();
	self.close();
</script>
