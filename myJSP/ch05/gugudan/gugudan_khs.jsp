<%@ page  contentType="text/html; charset=EUC-KR"%>

<h1 style = "text-align : center;"><구구단></h1>

<div style =
"
display:flex;
justify-content: center;">

<% for(int i =1; i<10; i++){%>

<table style =
"background-image: linear-gradient(to top left, #39b385, #9be15d);
 margin : 5px;
 font-size : 20px;
 border: 1px solid black;
 border-radius: 10px">
  <tr>
 	<th ><%=i %>단</th>
  </tr>
<% for(int j = 1; j<10; j++){ %>
  <tr>
 	<th><%=i%> X <%=j%> = <%=i*j%></th>
  </tr>
<%
	}
}
%>
</table>
</div>
