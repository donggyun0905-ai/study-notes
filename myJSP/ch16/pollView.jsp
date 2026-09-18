<!-- pollView.jsp -->
<%@page import="ch16.PollItemBean"%>
<%@page import="java.util.Vector"%>
<%@page import="ch16.PollListBean"%>
<%@page import="ch16.MUtil"%>
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class = "ch16.PollMgr"/>
<%
		int listNum = 0;
		if(request.getParameter("num")!=null){
			listNum = MUtil.parseInt(request, "num");
		}
		//어떤 설문에 대한 값
		PollListBean plBean = mgr.getPoll(listNum);
		Vector<PollItemBean> vlist = mgr.getView(listNum);
		//현재 설문의 sum 투표수
		int sumCnt = mgr.getSumCount(listNum);
		//현재 설문의 가장 높은 투표수
		int maxCnt = mgr.getMaxCount(listNum);
%>
<html>
<head>
<title>JSP Poll</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FFFFCC">
<div align="center"><br/>
<h2>투표 결과</h2>
<table border="1" width="400">
	<tr>
		<td colspan="4"><b>Q : <%=plBean.getQuestion()%></b></td>
	</tr>
	<tr>
		<td colspan="3"><b>총 투표자 :  <%=sumCnt%>명</b></td>
		<td width="40"><b>count(%)</b></td>
	</tr>
	<%
			String[] palette = {"#4f46e5","#14b8a6","#f59e0b","#f43f5e","#10b981","#0ea5e9","#a855f7","#eab308"};
			for(int i=0;i<vlist.size();i++){
				PollItemBean piBean = vlist.get(i);
				String item = piBean.getItem()[0];
				int count = piBean.getCount();
				//투표수/총투표수 * 100 (비율 구하기-정수로 반올림). 아직 투표가 없으면 0으로 처리
				int ratio = sumCnt==0 ? 0 : (int)(Math.round((double)count/sumCnt*100));
				//항목 순서에 따라 고정된 색상 (매번 랜덤이면 알아보기 어렵고, 흰색에 가까운 색이 나올 수도 있음)
				String rgb = palette[i % palette.length];

	%>
	<tr>
		<td width="20" align="center"><%=i+1%></td>
		<td width="120">
			<%if(maxCnt==count && count>0){ %><span class="winner"><%=item%></span>
			<%}else{ %><%=item %>
			<%} %>
		</td>
		<td>
			<div class="bar-track">
				<div class="bar-fill" style="width:<%=ratio%>%; background:<%=rgb%>;"></div>
			</div>
		</td>
		<td width="40"><%=count%>(<%=ratio%>)</td>
	</tr>
	<%}//---for%>
</table><br>
<a href="javascript:window.close()">닫기</a>
</div>
</body>
</html>