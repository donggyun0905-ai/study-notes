<!-- list.jsp -->
<%@page import="ch15.BoardBean"%>
<%@page import="java.util.Vector"%>
<%@page import="ch15.MUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch15.BoardMgr"/>
<%
		int totalRecord = 0;//총 게시물 수
		int numPerPage = 10;//페이지당 레코드 개수(5,10,20,30)
		int pagePerBlock = 15;
		int totalPage = 0;//전체 페이지
		int totalBlock = 0;//전체 블럭
		int nowPage = 1;//현재 페이지
		int nowBlock = 1;//현재 블럭
		
      	//현재페이지값 요청
      	if(request.getParameter("nowPage")!=null){
      		nowPage = MUtil.parseInt(request, "nowPage");
      	}
		
		//검색에 필요한 변수
		String keyField = "", keyWord = "";
		if(request.getParameter("keyWord")!=null){
			keyField = request.getParameter("keyField");
			keyWord = request.getParameter("keyWord");
		}
		
		totalRecord = mgr.getTotalCount(keyField, keyWord);
		//out.print(totalRecord);
		
		//동적인 numPerPage
		if(request.getParameter("numPerPage")!=null){
			numPerPage = MUtil.parseInt(request, "numPerPage");
      	}
		
      	int start = (nowPage * numPerPage)-numPerPage;
      	int cnt = numPerPage;
      	
      	totalPage = MUtil.ceilDivide(totalRecord, numPerPage);
      	totalBlock = MUtil.ceilDivide(totalPage, pagePerBlock);
      	nowBlock = MUtil.ceilDivide(nowPage, pagePerBlock);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>JSP Board</title>
	<script>!function(){var k='ch15-theme',o=['soft','deep','light'];var t=localStorage.getItem(k);if(o.indexOf(t)<0)t='soft';document.documentElement.setAttribute('data-ch15-theme',t);}();</script>
	<link href="ch15-theme.css" rel="stylesheet">
	<script src="https://cdn.tailwindcss.com"></script>
<script type="text/javascript">
	function pageing(page) {
		document.readFrm.action="list.jsp";
		document.readFrm.nowPage.value=page;
		document.readFrm.submit();
	}
	
	function sendBlock(block) {
		//블럭을 요청을 넘어가는 값은 nowPage이다. 이유는 page값을 block 계산
		document.readFrm.action="list.jsp";
		document.readFrm.nowPage.value=<%=pagePerBlock%>*(block-1)+1;
		document.readFrm.submit();
	}
	
	function check() {
		if(document.searchFrm.keyWord.value==""){
			alert("검색어를 입력하세요.");
			document.searchFrm.keyWord.focus();
			return;
		}
		document.readFrm.action="list.jsp";
		document.searchFrm.submit();
	}
	
	function numPerFn(numPerPage) {
		//alert(numPerPage);		
		document.readFrm.numPerPage.value=numPerPage;
		document.readFrm.action="list.jsp";
		document.readFrm.submit();
	}
	
	function read(num) {
		document.readFrm.num.value=num;
		document.readFrm.action="read.jsp";
		document.readFrm.submit();
	}
</script>
</head>
<body class="min-h-screen bg-[var(--ch15-page)] text-[var(--ch15-text)] antialiased">
<div class="mx-auto max-w-4xl px-4 py-8">
<h1 class="mb-8 text-center text-2xl font-bold tracking-tight text-[var(--ch15-heading)]">JSP Board</h1>

<div class="mb-6 flex flex-col gap-3 sm:flex-row sm:items-center sm:justify-between">
	<p class="text-sm text-[var(--ch15-muted)]">
		Total : <span class="font-semibold text-[var(--ch15-heading)]"><%=totalRecord%></span> Article
		<span class="text-red-400">(<%=nowPage%>/<%=totalPage%> Pages)</span>
	</p>
	<form name="npFrm" method="post" class="shrink-0">
		<select name="numPerPage" size="1"
			class="rounded-lg border border-[var(--ch15-input-border)] bg-[var(--ch15-input-bg)] px-3 py-2 text-sm text-[var(--ch15-text)] shadow-sm focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/40"
			onchange="javascript:numPerFn(this.form.numPerPage.value)">
			<option value="5">5개 보기</option>
			<option value="10" selected>10개 보기</option>
			<option value="15">15개 보기</option>
			<option value="30">30개 보기</option>
		</select>
		<script type="text/javascript">
			document.npFrm.numPerPage.value="<%=numPerPage%>";
		</script>
	</form>
</div>

<div class="overflow-hidden rounded-xl border border-[var(--ch15-border)] bg-[var(--ch15-card)] shadow-sm">
<%
			Vector<BoardBean> vlist = mgr.getBoardList(keyField, keyWord, start, cnt);
			int listSize = vlist.size();
			if(vlist.isEmpty()){
%>
	<div class="p-8 text-center text-[var(--ch15-muted)]">등록된 게시물이 없습니다.</div>
<%
			}else{
%>
	<div class="overflow-x-auto">
	<table class="min-w-full divide-y divide-[var(--ch15-border)] text-sm">
		<thead class="bg-[var(--ch15-th-bg)]">
			<tr>
				<th scope="col" class="px-4 py-3 text-center font-semibold text-[var(--ch15-th-text)]">번 호</th>
				<th scope="col" class="px-4 py-3 text-center font-semibold text-[var(--ch15-th-text)]">제 목</th>
				<th scope="col" class="px-4 py-3 text-center font-semibold text-[var(--ch15-th-text)]">이 름</th>
				<th scope="col" class="px-4 py-3 text-center font-semibold text-[var(--ch15-th-text)]">날 짜</th>
				<th scope="col" class="px-4 py-3 text-center font-semibold text-[var(--ch15-th-text)]">조회수</th>
			</tr>
		</thead>
		<tbody class="divide-y divide-[var(--ch15-border)]">
			<%
				for(int i=0;i<numPerPage;i++){
					if(i==listSize) break;
					BoardBean bean = vlist.get(i);
					int num = bean.getNum();
					String subject = bean.getSubject();
					String name = bean.getName();
					String regdate = bean.getRegdate();
					int depth = bean.getDepth();//답변의 깊이. 원글은 0
					int count = bean.getCount();
					String filename = bean.getFilename();
					//댓글 count
					int bcount = 0;//cmgr.getBCommentCount(num);
			%>
			<tr class="hover:bg-[var(--ch15-row-hover)]">
				<td class="whitespace-nowrap px-4 py-3 text-center text-[var(--ch15-text)]"><%=totalRecord-start-i%></td>
				<td class="px-4 py-3 text-left">
					<div class="flex flex-wrap items-center gap-1">
						<span class="inline-block shrink-0" style="width: <%=depth * 0.75%>rem"></span>
						<a href="javascript:read(<%=num%>)" class="font-medium text-indigo-400 hover:text-indigo-300 hover:underline"><%=subject%></a>
						<%if(filename!=null&&!filename.equals("")){ %>
							<img alt="첨부파일" src="img/clip.svg" class="inline h-4 w-4 opacity-70">
						<%}%>
						<%if(bcount>0){ %>
						<span class="text-red-400">(<%=bcount%>)</span>
						<%} %>
					</div>
				</td>
				<td class="whitespace-nowrap px-4 py-3 text-center"><%=name%></td>
				<td class="whitespace-nowrap px-4 py-3 text-center text-[var(--ch15-cell-muted)]"><%=regdate%></td>
				<td class="whitespace-nowrap px-4 py-3 text-center"><%=count%></td>
			</tr>
			<%}//--for %>
		</tbody>
	</table>
	</div>
		<%}//--if%>
</div>

<div class="mt-8 flex flex-col gap-4 border-t border-[var(--ch15-border)] pt-6 sm:flex-row sm:items-center sm:justify-between">
	<div class="flex flex-wrap items-center gap-2 text-sm">
		<%if(nowBlock>1){ %>
			<a href="javascript:sendBlock('<%=nowBlock-1%>')" class="text-indigo-400 hover:text-indigo-300 hover:underline">prev...</a>
		<%} %>
		<%
				int pageStart = (nowBlock-1)*pagePerBlock+1;
				int pageEnd = (pageStart+pagePerBlock)<totalPage?
						pageStart+pagePerBlock:totalPage+1;
				for(;pageStart<pageEnd;pageStart++){
		%>
		<a href="javascript:pageing('<%=pageStart%>')" class="rounded px-1 text-[var(--ch15-text)] hover:bg-[var(--ch15-row-hover)]">
		<%if(nowPage==pageStart){%><span class="font-bold text-sky-400"><%}%>
		[<%=pageStart%>]
		<%if(nowPage==pageStart){%></span><%}%>
		</a>
		<%}//--for %>
		<%if(totalBlock>nowBlock){ %>
			<a href="javascript:sendBlock('<%=nowBlock+1%>')" class="text-indigo-400 hover:text-indigo-300 hover:underline">...next</a>
		<%} %>
	</div>
	<div class="flex flex-wrap gap-3 text-sm font-medium">
		<a href="post.jsp" class="text-indigo-400 hover:text-indigo-300 hover:underline">글쓰기</a>
		<a href="list.jsp" class="text-[var(--ch15-muted)] hover:text-[var(--ch15-text)] hover:underline">처음으로</a>
	</div>
</div>

<hr class="my-8 border-[var(--ch15-border)]">

<!-- 검색 Form -->
<form name="searchFrm" class="rounded-xl border border-[var(--ch15-border)] bg-[var(--ch15-card)] p-4 shadow-sm">
	<div class="flex flex-wrap items-end justify-center gap-3">
		<select name="keyField" size="1"
			class="rounded-lg border border-[var(--ch15-input-border)] bg-[var(--ch15-input-bg)] px-3 py-2 text-sm text-[var(--ch15-text)] focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/40">
			<option value="name"> 이 름</option>
			<option value="subject"> 제 목</option>
			<option value="content"> 내 용</option>
		</select>
		<input name="keyWord" size="16"
			class="min-w-[12rem] rounded-lg border border-[var(--ch15-input-border)] bg-[var(--ch15-input-bg)] px-3 py-2 text-sm text-[var(--ch15-text)] focus:border-indigo-500 focus:outline-none focus:ring-2 focus:ring-indigo-500/40">
		<button type="button" onClick="javascript:check()"
			class="rounded-lg bg-indigo-600 px-4 py-2 text-sm font-medium text-white shadow hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-300">
			찾기
		</button>
		<input type="hidden" name="nowPage" value="1">
		<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	</div>
</form>
<form name="listFrm" method="post">
	<input type="hidden" name="reload" value="true">
	<input type="hidden" name="nowPage" value="1">
</form>

<form name="readFrm">
	<input type="hidden" name="num">
	<input type="hidden" name="nowPage" value="<%=nowPage%>">
	<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
	<input type="hidden" name="keyField" value="<%=keyField%>">
	<input type="hidden" name="keyWord" value="<%=keyWord%>">
</form>
</div>
<script src="ch15-theme.js"></script>
</body>
</html>
