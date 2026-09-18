<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1 padding = "20">구구단 선택을 하세요</h1>
<style>
	h1{margin:50; text-align:center;}
	table{margin:0 auto;}
</style>
<form action="gugumem.jsp" method="GET" >
	<table border="1" margin=100>
		<tr>
			<td bgcolor="FA8072" >2단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="2" ></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">3단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="3" ></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">4단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="4"></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">5단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="5"></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">6단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="6"></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">7단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="7"></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">8단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="8"></td>
		</tr>
		<tr>
			<td bgcolor="FA8072">9단</td>
			<td bgcolor="F5DEB3"><input type=checkbox name="dan" value="9"></td>
		</tr>
		<tr>
			<td colspan="10"><input type="submit" value="확인"></td>
		</tr>
	</table>
</form>

