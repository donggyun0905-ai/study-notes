<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>구구단 출력</h1>
<style>
	h1{margin:50; text-align:center;}
	table{margin:0 auto;}

</style>
<%
	String arr[] = request.getParameterValues("dan");
String str = "<table border='2' > ";
for (int i = 0; i < arr.length; i++) {
	str += "<tr>" + "<td >" + arr[i] + "단" + "</td>";
	str += "<td>";
	for (int j = 1; j <= 9; j++) {
		int gugu = Integer.parseInt(arr[i]);
		str += gugu + "*" + j + "=" + gugu * j;
		str += "<br>";
	}
	str += "</td>";
}
str += "</table>";
%>
<%=str%>
