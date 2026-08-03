<!-- color.jsp -->
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%!
	Random rd=new Random();
	public String randomColor(){
		String rgb = "#";
		int r = rd.nextInt(256);
		int g = rd.nextInt(256);
		int b = rd.nextInt(256);
		rgb+=Integer.toHexString(r);
		rgb+=Integer.toHexString(g);
		rgb+=Integer.toHexString(b);
		return rgb;
	}
	
%>

<body bgcolor="<%= randomColor()%>">
	<%for(int i=0; i<100; i++){%>
	<h3 style="color:<%= randomColor()%>">오늘은 즐거운 수요일</h3>
	<% }%>
</body>