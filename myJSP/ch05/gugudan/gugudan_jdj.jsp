<%@ page  contentType="text/html; charset=EUC-KR"%>
<html>
<body>
<style>
body {
	height: 100%;
}
body {
	margin: 0;
	background: linear-gradient(45deg, #49a09d, #5f2c82);
	font-family: sans-serif;
	font-weight: 100;
}
.gugu {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
}
table {
	width: 800px;
	border-collapse: collapse;
	overflow: hidden;
	box-shadow: 0 0 20px rgba(0,0,0,0.1);
}

th,
td {
	padding: 15px;
	background-color: rgba(255,255,255,0.2);
	color: #fff;
	text-align: center;
}

thead {
	th {
		background-color: #55608f;
	}
}
tbody tr:hover{
			background-color: rgba(255,255,255,0.3);
		}

tbody tr,td{position: relative;}

tbody td:hover::before {
				content: "";
				position: absolute;
				left: 0;
				right: 0;
				top: -9999px;
				bottom: -9999px;
				background-color: rgba(255,255,255,0.2);
				z-index: -1;
			}
		}
</style>
<h1 align="center" style="color: rgba(255,255,255,0.2)">구구단</h1>
<div class="gugu">
	<table border="1">
	<thead>
			<tr align="center">
			<%for(int dan=2;dan<10;dan++){
			out.write("<th>"+dan+"단</th>");
			}%>
			</tr>
			</thead>
			<tbody>
			<%
			for(int i=2;i<10;i++){%><tr>
			<%for(int i2=2;i2<10;i2++){%><td>
			<%out.write(i2+"x"+i+"="+i*i2);%><br></td>
			<%}//for%>
			</tr>
			<%}//for%>
			</tbody>
	</table>
	</div>
</body>
</html>