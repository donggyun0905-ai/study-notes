<!-- read.jsp -->
<%@page import="java.util.Vector"%>
<%@page import="ch15.BCommentBean"%>
<%@page import="ch15.BoardBean"%>
<%@page import="ch15.MUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch15.BoardMgr"/>
<jsp:useBean id="cmgr" class="ch15.BCommentMgr"/>
<%
		String nowPage = request.getParameter("nowPage");
		String numPerPage = request.getParameter("numPerPage");
		String keyField = request.getParameter("keyField");
		String keyWord = request.getParameter("keyWord");
		int num = MUtil.parseInt(request, "num");

		//댓글 입력 및 삭제 요청
		String flag = request.getParameter("flag");
		if(flag!=null){
			if(flag.equals("insert")){
				BCommentBean cbean = new BCommentBean();
				cbean.setNum(num);//어떤 게시물
				cbean.setName(request.getParameter("cName"));
				cbean.setComment(request.getParameter("comment"));
				cmgr.insertBComment(cbean);
			}else if(flag.equals("delete")){
				int cnum = MUtil.parseInt(request, "cnum");
				cmgr.deleteBComment(cnum);
			}
		}else{
			//조회수 증가
			mgr.upCount(num);
		}

		//게시물 리턴
		BoardBean bean = mgr.getBoard(num);
		//세션저장: 삭제,수정,답변
		session.setAttribute("bean", bean);

		String name = bean.getName();
		String subject = bean.getSubject();
		String regdate = bean.getRegdate();
		String content = bean.getContent();
		String filename = bean.getFilename();
		int filesize = bean.getFilesize();
		String ip = bean.getIp();
		int count = bean.getCount();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>JSP Board</title>
<%@ include file="themeHead.jsp" %>
<script type="text/javascript">
	function list() {
		document.listFrm.action="list.jsp";
		document.listFrm.submit();
	}

	function down(filename) {
		document.downFrm.filename.value=filename;
		document.downFrm.submit();
	}

	function delFn() {
		const pass = document.getElementById("passId");
		//alert(pass.value);
		if(pass.value.length==0){
			alert("비밀번호를 입력하세요");
			return;
		}
		document.delFrm.pass.value=pass.value;
		document.delFrm.submit();
	}

	function cInsert() {
		if(document.cFrm.comment.value==""){
			alert("댓글을 입력하세요.");
			document.cFrm.comment.focus();
			return;
		}
		document.cFrm.submit();
	}

	function cDel(cnum) {
		document.cFrm.cnum.value=cnum;
		document.cFrm.flag.value="delete";
		document.cFrm.submit();
	}
</script>
</head>
<body class="min-h-screen bg-slate-50 text-slate-800 transition-colors dark:bg-slate-900 dark:text-slate-100">
<%@ include file="themeToggle.jsp" %>
<div class="mx-auto max-w-2xl px-4 py-10">

	<div class="overflow-hidden rounded-xl border border-slate-200 dark:border-slate-700">
		<div class="bg-indigo-600 py-2 text-center font-semibold text-white">글읽기</div>
		<table class="w-full border-collapse text-sm">
			<tr>
				<td class="w-[15%] bg-slate-100 px-3 py-2 text-center font-medium dark:bg-slate-800">이 름</td>
				<td class="bg-white px-3 py-2 dark:bg-slate-900"><%=name%></td>
				<td class="w-[15%] bg-slate-100 px-3 py-2 text-center font-medium dark:bg-slate-800">등록날짜</td>
				<td class="bg-white px-3 py-2 dark:bg-slate-900"><%=regdate%></td>
			</tr>
			<tr>
				<td class="bg-slate-100 px-3 py-2 text-center font-medium dark:bg-slate-800">제 목</td>
				<td class="bg-white px-3 py-2 dark:bg-slate-900" colspan="3"><%=subject%></td>
			</tr>
			<tr>
				<td class="bg-slate-100 px-3 py-2 text-center font-medium dark:bg-slate-800">첨부파일</td>
				<td class="bg-white px-3 py-2 dark:bg-slate-900" colspan="3">
				<%if(filename!=null&&!filename.equals("")){%>
					<a class="text-indigo-600 hover:underline dark:text-indigo-400" href="javascript:down('<%=filename%>')"><%=filename%></a>
					<span class="text-blue-500">(<%=MUtil.monFormat(filesize)%>bytes)</span>
				<%}else{%>
					첨부된 파일이 없습니다.
				<%}%>
				</td>
			</tr>
			<tr>
				<td class="bg-slate-100 px-3 py-2 text-center font-medium dark:bg-slate-800">비밀번호</td>
				<td class="bg-white px-3 py-2 dark:bg-slate-900" colspan="3">
					<input type="password" name="pass" id="passId"
						class="rounded-md border border-slate-300 bg-white px-2 py-1 text-sm dark:border-slate-600 dark:bg-slate-800">
				</td>
			</tr>
			<tr>
				<td class="bg-white px-3 py-3 dark:bg-slate-900" colspan="4">
					<pre class="whitespace-pre-wrap break-words font-sans text-sm leading-relaxed"><%=content%></pre>
				</td>
			</tr>
			<tr>
				<td class="bg-white px-3 py-2 text-right text-xs text-slate-500 dark:bg-slate-900 dark:text-slate-400" colspan="4">
					IP주소 : <%=ip%> / 조회수  <%=count%>
				</td>
			</tr>
		</table>
	</div>

	<div class="mt-6">
		<!-- 댓글 입력폼 Start -->
		<form method="post" name="cFrm" class="flex flex-wrap items-center gap-2">
			<span class="w-12 text-sm text-slate-600 dark:text-slate-300">이 름</span>
			<input name="cName" size="10" value="aaa"
				class="rounded-md border border-slate-300 bg-white px-2 py-1 text-sm dark:border-slate-600 dark:bg-slate-800">
			<span class="text-sm text-slate-600 dark:text-slate-300">내 용</span>
			<input name="comment" size="50"
				class="flex-1 rounded-md border border-slate-300 bg-white px-2 py-1 text-sm dark:border-slate-600 dark:bg-slate-800">
			<input type="button" value="등록" onclick="cInsert()"
				class="cursor-pointer rounded-md bg-indigo-600 px-3 py-1.5 text-sm text-white hover:bg-indigo-700">
		 <input type="hidden" name="flag" value="insert">
		 <input type="hidden" name="num" value="<%=num%>">
		 <!-- cnum은 삭제시 필요 -->
		 <input type="hidden" name="cnum">
	     <input type="hidden" name="nowPage" value="<%=nowPage%>">
	     <input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	     <%if(!(keyWord==null||keyWord.equals(""))){ %>
	     <input type="hidden" name="keyField" value="<%=keyField%>">
	     <input type="hidden" name="keyWord" value="<%=keyWord%>">
		<%}%>
		</form>
		<!-- 댓글 입력폼 End -->

		<hr class="my-4 border-slate-200 dark:border-slate-700">

		<!-- 댓글 리스트 Start -->
		<%
	  		Vector<BCommentBean> cvlist = cmgr.getBComment(num);
	  		if(!cvlist.isEmpty()){
	  	%>
		<ul class="space-y-2">
			<%for(BCommentBean cbean : cvlist){%>
			<li class="rounded-md border border-slate-200 px-3 py-2 text-sm dark:border-slate-700">
				<div class="font-semibold"><%=cbean.getName()%></div>
				<div class="mt-1 flex items-center justify-between gap-2">
					<span>댓글:<%=cbean.getComment()%></span>
					<span class="shrink-0 text-xs text-slate-500 dark:text-slate-400"><%=cbean.getRegdate()%></span>
					<input type="button" value="삭제"
						class="shrink-0 cursor-pointer rounded border border-slate-300 px-2 py-0.5 text-xs hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-800"
						onclick="cDel('<%=cbean.getCnum()%>')">
				</div>
			</li>
			<%}//-for %>
		</ul>
		<%}//--if%>
		<!-- 댓글 리스트 End -->

		<div class="mt-6 flex justify-center gap-3 text-sm">
			<a class="text-indigo-600 hover:underline dark:text-indigo-400" href="javascript:list()">리스트</a>
			<span class="text-slate-300 dark:text-slate-600">|</span>
			<a class="text-indigo-600 hover:underline dark:text-indigo-400" href="update.jsp?nowPage=<%=nowPage%>&numPerPage=<%=numPerPage%>">수 정</a>
			<span class="text-slate-300 dark:text-slate-600">|</span>
			<a class="text-indigo-600 hover:underline dark:text-indigo-400" href="reply.jsp?nowPage=<%=nowPage%>&numPerPage=<%=numPerPage%>">답 변</a>
			<span class="text-slate-300 dark:text-slate-600">|</span>
			<a class="text-red-500 hover:underline" href="javascript:delFn()">삭 제</a>
		</div>
	</div>
</div>

<form method="post" name="downFrm" action="download.jsp">
	<input type="hidden" name="filename">
</form>

<form name="listFrm">
	<input type="hidden" name="nowPage" value="<%=nowPage%>">
	<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	<%if(!(keyWord==null||keyWord.equals(""))){%>
	<input type="hidden" name="keyField" value="<%=keyField%>">
	<input type="hidden" name="keyWord" value="<%=keyWord%>">
	<%}%>
</form>

<form name="delFrm" action="boardDelete" method="post">
	<input type="hidden" name="nowPage" value="<%=nowPage%>">
	<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	<%if(!(keyWord==null||keyWord.equals(""))){%>
	<input type="hidden" name="keyField" value="<%=keyField%>">
	<input type="hidden" name="keyWord" value="<%=keyWord%>">
	<%}%>
	<input type="hidden" name="pass">
</form>
</body>
</html>
