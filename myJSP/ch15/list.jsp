<!-- list.jsp> -->
<%@page import="ch15.BoardBean"%>
<%@page import="java.util.Vector"%>
<%@page import="ch15.MUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch15.BoardMgr"/>
<jsp:useBean id="cmgr" class="ch15.BCommentMgr"/>
<%
      	int totalRecord = 0;//총 게시물 수
		int numPerPage = 10;//페이지당 레코드 개수(5,10,20,30)
		int pagePerBlock = 15;//블럭당 페이지 개수
		int totalPage = 0;//전체 페이지
		int totalBlock = 0;//전체 블럭
		int nowPage = 1;//현재 페이지
		int nowBlock = 1;//현재 블럭

		//현재 페이지 요청
		if(request.getParameter("nowPage")!=null){
			nowPage = MUtil.parseInt(request, "nowPage");
		}

		//검색에 필요한 변수
		String keyField = "", keyWord = "";
		if(request.getParameter("keyWord")!=null){
			keyField = request.getParameter("keyField");
			keyWord = request.getParameter("keyWord");
			//out.print(keyField +" : " + keyWord);
		}

		totalRecord = mgr.getTotalCount(keyField, keyWord);
		//out.print(totalRecord);

		//동적인 numPerPage
		if(request.getParameter("numPerPage")!=null){
			numPerPage = MUtil.parseInt(request, "numPerPage");
		}

		int start = (nowPage * numPerPage) - numPerPage;
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
<%@ include file="themeHead.jsp" %>
<script type="text/javascript">
	function check() {
		if(document.searchFrm.keyWord.value==""){
			alert("검색어를 입력하세요.");
			document.searchFrm.keyWord.focus();
			return;
		}
		document.searchFrm.action="list.jsp";
		document.searchFrm.submit();
	}

	function read(num) {
		document.readFrm.num.value=num;
		document.readFrm.action="read.jsp";
		document.readFrm.submit();
	}

	function pageing(page) {
		document.readFrm.nowPage.value=page;
		document.readFrm.action="list.jsp";
		document.readFrm.submit();
	}

	function sendBlock(block) {
		//블럭요청에 넘어가는 값은 결국 nowPage. 이유는 page값으로 block 계산
		document.readFrm.nowPage.value=<%=pagePerBlock%>*(block-1)+1;
		document.readFrm.action="list.jsp";
		document.readFrm.submit();
	}

	function numPerFn(numPerPage){
		document.readFrm.numPerPage.value=numPerPage;
		document.readFrm.action="list.jsp";
		document.readFrm.submit();
	}
</script>
</head>
<body class="min-h-screen bg-slate-50 text-slate-800 transition-colors dark:bg-slate-900 dark:text-slate-100">
<%@ include file="themeToggle.jsp" %>
<div class="mx-auto max-w-4xl px-4 py-10">
	<h2 class="mb-6 text-center text-2xl font-bold">JSP Board</h2>

	<div class="mb-3 flex items-center justify-between text-sm text-slate-600 dark:text-slate-300">
		<span>Total: <%=totalRecord%>Article(<span class="font-semibold text-red-500"><%=nowPage+"/"+totalPage%>Pages</span>)</span>
		<form name="npFrm" method="post">
			<select name="numPerPage" size="1"
				class="rounded-md border border-slate-300 bg-white px-2 py-1 text-sm dark:border-slate-600 dark:bg-slate-800"
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

	<!-- 게시물 리스팅 -->
	<div class="overflow-hidden rounded-xl border border-slate-200 dark:border-slate-700">
	<%
			Vector<BoardBean> vlist = mgr.getBoardList(keyField, keyWord, start, cnt);
			int listSize = vlist.size();
			if(vlist.isEmpty()){
	%>
	<p class="p-6 text-center text-sm text-slate-500 dark:text-slate-400">등록된 게시물이 없습니다.</p>
	<%}else{%>
	<table class="w-full border-collapse text-sm">
		<thead>
			<tr class="bg-slate-100 text-slate-600 dark:bg-slate-800 dark:text-slate-300">
				<td class="w-16 py-2 text-center font-medium">번 호</td>
				<td class="py-2 pl-3 text-left font-medium">제 목</td>
				<td class="w-24 py-2 text-center font-medium">이 름</td>
				<td class="w-32 py-2 text-center font-medium">날 짜</td>
				<td class="w-20 py-2 text-center font-medium">조회수</td>
			</tr>
		</thead>
		<tbody class="divide-y divide-slate-100 dark:divide-slate-700">
			<!-- 공지 리스팅 Start: nowPage=1 적용-->
			<%
					if(nowPage==1){
					Vector<BoardBean> nvlist = mgr.getBoardNoticeList();
					for(int i=0;i<nvlist.size();i++){
						BoardBean bean = nvlist.get(i);
						int num = bean.getNum();
						String subject = bean.getSubject();
						String name = bean.getName();
						String regdate = bean.getRegdate();
						int depth = bean.getDepth();
						int count = bean.getCount();
						String filename = bean.getFilename();
						int bcount = cmgr.getBCommentCount(num);
			%>
			<tr class="bg-sky-50 text-center font-medium dark:bg-sky-900/30">
				<td class="py-2">공지</td>
				<td class="py-2 pl-3 text-left">
					<%for(int j=0;j<depth;j++){out.println("&nbsp;&nbsp;");} %>
					<a class="hover:underline" href="javascript:read('<%=num%>')"><%=subject%></a>
					<%if(filename!=null&&!filename.equals("")){ %>
						<img class="inline h-4 w-4 align-text-bottom" alt="첨부파일" src="img/clip.svg">
					<%} %>
					<!-- 댓글 개수 -->
					<%if(bcount>0){%>
						<span class="text-red-500">(<%=bcount%>)</span>
					<%}%>
				</td>
				<td class="py-2"><%=name%></td>
				<td class="py-2"><%=regdate%></td>
				<td class="py-2"><%=count%></td>
			</tr>
			<%
					}//--for1
				}//--if
			%>
			<!-- 공지 리스팅 End-->
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
						int bcount = cmgr.getBCommentCount(num);
			%>
			<tr class="text-center hover:bg-slate-50 dark:hover:bg-slate-800/60">
				<td class="py-2"><%=totalRecord-start-i%></td>
				<td class="py-2 pl-3 text-left">
					<%for(int j=0;j<depth;j++){out.println("&nbsp;&nbsp;");} %>
					<a class="hover:underline" href="javascript:read('<%=num%>')"><%=subject%></a>
					<%if(filename!=null&&!filename.equals("")){ %>
						<img class="inline h-4 w-4 align-text-bottom" alt="첨부파일" src="img/clip.svg">
					<%} %>
					<!-- 댓글 개수 -->
					<%if(bcount>0){%>
						<span class="text-red-500">(<%=bcount%>)</span>
					<%}%>
				</td>
				<td class="py-2"><%=name%></td>
				<td class="py-2"><%=regdate%></td>
				<td class="py-2"><%=count%></td>
			</tr>
			<%}//--for2%>
		</tbody>
	</table>
	<%}//--if-else%>
	</div>

	<div class="mt-4 flex flex-wrap items-center justify-between gap-3 text-sm">
		<div class="flex flex-wrap items-center gap-1">
			<!-- 이전블럭 -->
			<%if(nowBlock>1){ %>
				<a class="rounded px-2 py-1 hover:bg-slate-100 dark:hover:bg-slate-800" href="javascript:sendBlock('<%=nowBlock-1%>')">prev...</a>
			<%}%>
			<!-- 페이징 -->
			<%
					int pageStart = (nowBlock-1)*pagePerBlock+1;
					int pageEnd = (pageStart+15)<totalPage?
							pageStart+pagePerBlock:totalPage+1;
					for(;pageStart<pageEnd;pageStart++){
			%>
				<a class="rounded px-2 py-1 <%=pageStart==nowPage?"bg-indigo-600 font-bold text-white":"hover:bg-slate-100 dark:hover:bg-slate-800"%>"
					href="javascript:pageing('<%=pageStart%>')">[<%=pageStart%>]</a>
			<%}//--for%>
			<!-- 다음블럭 -->
			<%if(totalBlock>nowBlock){ %>
				<a class="rounded px-2 py-1 hover:bg-slate-100 dark:hover:bg-slate-800" href="javascript:sendBlock('<%=nowBlock+1%>')">...next</a>
			<%}%>
		</div>
		<div class="flex gap-2">
			<a class="rounded-md bg-indigo-600 px-3 py-1.5 text-white hover:bg-indigo-700" href="post.jsp">글쓰기</a>
			<a class="rounded-md border border-slate-300 px-3 py-1.5 hover:bg-slate-100 dark:border-slate-600 dark:hover:bg-slate-800" href="list.jsp">처음으로</a>
		</div>
	</div>

	<hr class="my-6 border-slate-200 dark:border-slate-700">

	<!-- 검색 Form -->
	<form name="searchFrm" class="flex flex-wrap items-center justify-center gap-2">
		<select name="keyField" size="1"
			class="rounded-md border border-slate-300 bg-white px-2 py-1.5 text-sm dark:border-slate-600 dark:bg-slate-800">
    		<option value="name"> 이 름</option>
    		<option value="subject"> 제 목</option>
    		<option value="content"> 내 용</option>
   		</select>
   		<input size="16" name="keyWord"
   			class="rounded-md border border-slate-300 bg-white px-2 py-1.5 text-sm dark:border-slate-600 dark:bg-slate-800">
   		<input type="button" value="찾기" onClick="javascript:check()"
   			class="cursor-pointer rounded-md bg-slate-700 px-3 py-1.5 text-sm text-white hover:bg-slate-800">
   		<input type="hidden" name="nowPage" value="1">
   		<input type="hidden" name="numPerPage" value="<%=numPerPage%>">
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
</body>
</html>
