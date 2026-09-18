<!-- reply.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<!-- read.jsp에서 원글을 session 저장 -->
<jsp:useBean id="bean" scope="session" class="ch15.BoardBean"/>
<%
		String nowPage = request.getParameter("nowPage");
		String numPerPage = request.getParameter("numPerPage");
		String subject = bean.getSubject();
		String content = bean.getContent();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>JSPBoard</title>
<%@ include file="themeHead.jsp" %>
</head>
<body class="min-h-screen bg-slate-50 text-slate-800 transition-colors dark:bg-slate-900 dark:text-slate-100">
<%@ include file="themeToggle.jsp" %>
<div class="mx-auto max-w-2xl px-4 py-10">

	<div class="mb-4 rounded-xl bg-amber-500 py-2 text-center font-semibold text-white">답변하기</div>

	<form method="post" action="boardReply" class="overflow-hidden rounded-xl border border-slate-200 dark:border-slate-700">
	<table class="w-full border-collapse text-sm">
		<tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="w-[20%] bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">성 명</td>
	     <td class="px-3 py-2">
		  <input name="name" size="30" maxlength="20" value="aaa"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900"></td>
	    </tr>
	    <tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">제 목</td>
	     <td class="px-3 py-2">
		  <input name="subject" size="50" value="답변 : <%=subject%>" maxlength="50"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900"></td>
	    </tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">내 용</td>
	     <td class="px-3 py-2">
		  <textarea name="content" rows="12" cols="50"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900">
      	<%=content %>
      	========답변 글을 쓰세요.=======
      	</textarea>
	      </td>
	    </tr>
	    <tr>
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">비밀 번호</td>
	     <td class="px-3 py-2">
		  <input type="password" name="pass" size="15" maxlength="15" value="1234"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900 sm:w-auto"></td>
	    </tr>
	</table>
	<div class="flex justify-center gap-2 border-t border-slate-200 bg-slate-50 px-3 py-4 dark:border-slate-700 dark:bg-slate-800/50">
	      <input type="submit" value="답변등록"
	      	class="cursor-pointer rounded-md bg-indigo-600 px-4 py-1.5 text-sm text-white hover:bg-indigo-700">
	      <input type="reset" value="다시쓰기"
	      	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
	      <input type="button" value="뒤로" onClick="history.back()"
	      	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
	</div>
	 <input type="hidden" name="nowPage" value="<%=nowPage%>">
	 <input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	 <input type="hidden" name="ip" value="<%=request.getRemoteAddr()%>" >
	 <input type="hidden" name="ref" value="<%=bean.getRef()%>">
	 <input type="hidden" name="pos" value="<%=bean.getPos()%>">
	 <input type="hidden" name="depth" value="<%=bean.getDepth()%>">
	</form>
</div>
</body>
</html>
