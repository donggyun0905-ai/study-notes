<!-- memberUpdateProc.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<jsp:useBean id="bean" class="ch14.MemberBean"/>
<jsp:setProperty property="*" name="bean"/>
<%boolean result = mgr.updateMember(bean);%>
<%if(result){ %>
<script>
	alert("수정을 하였습니다.");
	location.href = "login.jsp";
</script>
<%}else{ %>
<script>
	alert("수정을 실패 하였습니다.");
	history.back();
</script>
<%} %>
