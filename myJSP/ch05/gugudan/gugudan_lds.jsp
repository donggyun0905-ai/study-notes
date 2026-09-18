<%@ page  contentType="text/html; charset=EUC-KR"%>
<%
		
%>

<style>
	div{text-align: center;} 
	div{font-size: 45px}
	.a{text-shadow: 0px 0px 3px orange;}
	table {border-collapse: collapse;}
	td, th {
 	text-align: center;
 	padding : 15px;
 	height : 20px;
 	width :  100px;
 	}
/*  	 td, th {border: 2px dotted black;}  */
		
	 thead, tfoot {
 	background: palegoldenrod;
	color: peru;
	 }
 	tbody tr:nth-child(even) {
	background: peachpuff;
	}
	tbody tr:nth-child(odd) {
	background: oldlace;
	}
	tbody td:hover {
	background: pink;
	}
	
	
	#menubar tr th{display: inline-block;
				list-style-type: none;
				padding: 0px 15px;}
	#munubar tr th a{
	color: black;
	text-decoration: none;
	}
	#munubar tr th a:hover {
	color:pink;
}
	
</style>
	<div class="a">구구단</div>
		<table>
			<thead id ="munubar">
			<tr> 	
			<th><a href="">2단</a></th>
			<th><a href="">3단</a></th>
			<th><a href="">4단</a></th>
			<th><a href="">5단</a></th>
			<th><a href="">6단</a></th>
			<th><a href="">7단</a></th>
			<th><a href="">8단</a></th>
			<th><a href="">9단</a></th>
			</tr>
			</thead>
	<tbody>
	<%
	for(int i =2; i<10;i++){
		for(int j = 1; j<10; j++) {
			if(i==2) {
	%>
	<tr>	
		<td style="cursor: pointer;"><%=i+"x"+(j)+"="+ i*j %></td>
		<td style="cursor: pointer;"><%=(i+1)+"x"+(j)+"="+ (i+1)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+2)+"x"+(j)+"="+ (i+2)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+3)+"x"+(j)+"="+ (i+3)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+4)+"x"+(j)+"="+ (i+4)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+5)+"x"+(j)+"="+ (i+5)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+6)+"x"+(j)+"="+ (i+6)*(j) %></td>
		<td style="cursor: pointer;"><%=(i+7)+"x"+(j)+"="+ (i+7)*(j) %></td>
	</tr>
	<%
			}
		}
	}
	%>
	</tbody>
</table>