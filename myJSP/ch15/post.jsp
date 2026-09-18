<!-- post.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>JSP Board</title>
<%@ include file="themeHead.jsp" %>
</head>
<body class="min-h-screen bg-slate-50 text-slate-800 transition-colors dark:bg-slate-900 dark:text-slate-100">
<%@ include file="themeToggle.jsp" %>
<div class="mx-auto max-w-2xl px-4 py-10">

	<div class="mb-4 rounded-xl bg-emerald-500 py-2 text-center font-semibold text-white">글쓰기</div>

	<form name="postFrm" method="post" action="boardPost"
	enctype="multipart/form-data" class="overflow-hidden rounded-xl border border-slate-200 dark:border-slate-700">
	<table class="w-full border-collapse text-sm">
		<tr class="border-b border-slate-200 dark:border-slate-700">
			<td class="w-[20%] bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">성 명</td>
			<td class="px-3 py-2">
			<input name="name" size="10" maxlength="8" value="aaa"
				class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900 sm:w-auto"></td>
		</tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
			<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">제 목</td>
			<td class="px-3 py-2">
			<input name="subject" size="50" maxlength="30" value="테스트"
				class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900"></td>
		</tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
			<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">내 용</td>
			<td class="px-3 py-2"><textarea name="content" rows="10" cols="50"
				class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900">내용테스트</textarea></td>
		</tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
			<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">비밀 번호</td>
			<td class="px-3 py-2"><input type="password" name="pass" size="15" maxlength="15" value="1234"
				class="w-full rounded-md border border-slate-300 bg-white px-2 py-1 dark:border-slate-600 dark:bg-slate-900 sm:w-auto"></td>
		</tr>
		<tr class="border-b border-slate-200 dark:border-slate-700">
     		<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">파일찾기</td>
     		<td class="px-3 py-2"><input type="file" name="filename" size="50" maxlength="50"
     			class="w-full text-sm file:mr-3 file:rounded-md file:border-0 file:bg-slate-200 file:px-3 file:py-1 file:text-slate-700 hover:file:bg-slate-300 dark:file:bg-slate-700 dark:file:text-slate-100 dark:hover:file:bg-slate-600"></td>
    		</tr>
 			<tr class="border-b border-slate-200 dark:border-slate-700">
 				<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">내용타입</td>
 				<td class="px-3 py-2">
 					<label class="mr-4 inline-flex items-center gap-1">
 						<input type="radio" name="contentType" value="HTTP">HTML
 					</label>
 					<label class="inline-flex items-center gap-1">
 						<input type="radio" name="contentType" value="TEXT" checked>TEXT
 					</label>
 				</td>
 			</tr>
 			<tr>
 				<td class="bg-slate-100 px-3 py-2 font-medium dark:bg-slate-800">공지등록</td>
 				<td class="px-3 py-2">
 					<label class="inline-flex items-center gap-1">
 						공지<input type="checkbox" name="mode" value="1">
 					</label>
 				</td>
 			</tr>
	</table>
	<div class="flex justify-center gap-2 border-t border-slate-200 bg-slate-50 px-3 py-4 dark:border-slate-700 dark:bg-slate-800/50">
		 <input type="submit" value="등록"
		 	class="cursor-pointer rounded-md bg-indigo-600 px-4 py-1.5 text-sm text-white hover:bg-indigo-700">
		 <input type="reset" value="다시쓰기"
		 	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
		 <input type="button" value="리스트" onClick="javascript:location.href='list.jsp'"
		 	class="cursor-pointer rounded-md border border-slate-300 px-4 py-1.5 text-sm hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-700">
	</div>
	<input type="hidden" name="ip" value="<%=request.getRemoteAddr()%>">
	</form>
</div>
</body>
</html>
