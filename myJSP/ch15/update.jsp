<!-- update.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="ch15.BoardBean"%>
<!-- read.jsp에서 session에 bean값으로 저장 -->
<jsp:useBean id="bean" scope="session" class="ch15.BoardBean"/>
<%
	  //BoardBean bean = (BoardBean)session.getAttribute("bean");
	  String nowPage = request.getParameter("nowPage");
	  String numPerPage = request.getParameter("numPerPage");
	  int num = bean.getNum();
	  String subject = bean.getSubject();
	  String name = bean.getName();
	  String content = bean.getContent();
	  //read.jsp에서 session에 빈즈 단위로 저장 했기 때문에 파일명도 가져 올 수 있다.
	  String filename = bean.getFilename();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>JSP Board</title>
<%@ include file="themeHead.jsp" %>
<script>
	function check() {
	   if (document.updateFrm.pass.value == "") {
		 alert("수정을 위해 비밀번호를 입력하세요.");
		 document.updateFrm.pass.focus();
		 return false;
		 }
	   document.updateFrm.submit();
	}
</script>
</head>
<body class="min-h-screen bg-slate-50 text-slate-800 transition-colors dark:bg-slate-900 dark:text-slate-100">
<%@ include file="themeToggle.jsp" %>
<div class="mx-auto max-w-2xl px-4 py-10">

	<div class="mb-4 rounded-xl bg-orange-500 py-2 text-center font-semibold text-white">수정하기</div>

	<form name="updateFrm" method="post" action="boardUpdate" enctype="multipart/form-data"
		class="overflow-hidden rounded-xl border border-slate-200 dark:border-slate-700">
	<table class="w-full border-collapse text-sm">
		<tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="w-[20%] bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">성 명</td>
	     <td class="px-3 py-2">
		  <input name="name" value="<%=name%>" size="30" maxlength="20"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900">
		 </td>
		</tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">제 목</td>
	     <td class="px-3 py-2">
		  <input name="subject" size="50" value="<%=subject%>" maxlength="50"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900">
		 </td>
		</tr>
	    <tr class="border-b border-slate-200 dark:border-slate-700">
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">내 용</td>
	     <td class="px-3 py-2">
		  <textarea name="content" rows="10" cols="50"
		  	class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900"><%=content%></textarea>
		 </td>
	    </tr>
	    <tr class="border-b border-slate-200 dark:border-slate-700">
	    <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">첨부파일</td>
	     <td class="px-3 py-2">
	     	<div class="mb-1 text-xs text-slate-500 dark:text-slate-400"><%=filename!=null?filename:"첨부된 파일이 없습니다."%></div>
	     	<input type="file" name="filename" size="50" maxlength="50"
	     		class="w-full text-sm file:mr-3 file:rounded-md file:border-0 file:bg-slate-200 file:px-3 file:py-1 file:text-slate-700 hover:file:bg-slate-300 dark:file:bg-slate-700 dark:file:text-slate-100 dark:hover:file:bg-slate-600">
	     </td>
	    </tr>
		<tr>
	     <td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">비밀 번호</td>
	     <td class="px-3 py-2">
	     	<input type="password" name="pass" size="15" maxlength="15"
	     		class="rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900">
	     	<span class="ml-2 text-xs text-slate-500 dark:text-slate-400">수정 시에는 비밀번호가 필요합니다.</span>
	     </td>
	    </tr>
	</table>
	<div class="flex justify-center gap-2 border-t border-slate-200 bg-slate-50 px-3 py-4 dark:border-slate-700 dark:bg-slate-800/50">
	      <input type="button" value="수정완료" onClick="check()"
	      	class="cursor-pointer rounded-md bg-indigo-600 px-4 py-1.5 text-sm text-white hover:bg-indigo-700">
	      <input type="reset" value="다시수정"
	      	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
	      <input type="button" value="뒤로" onClick="history.go(-1)"
	      	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
	</div>
	 <input type="hidden" name="nowPage" value="<%=nowPage %>">
	 <input type='hidden' name="num" value="<%=num%>">
	 <input type='hidden' name="numPerPage" value="<%=numPerPage%>">
	</form>
</div>
</body>
</html>
