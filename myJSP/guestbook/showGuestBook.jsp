<%@ page contentType="text/html; charset=UTF-8" %>
<%
    //로그인 안된 상태에서는 login.jsp 보냄 ,대신에  현재 url값을 가지고 리턴
    String id = (String)session.getAttribute("idKey");
    if(id==null){
        StringBuffer url = request.getRequestURL();
        response.sendRedirect("login.jsp?url="+url);
        return;
    }
%>
<!DOCTYPE html>
<html>
<title>GuestBook</title>
<script type="text/javascript">

</script>
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#996600">
<div align="center">
<%@include file="postGuestBook.jsp"%>
<table width="520" cellspacing="0" cellpadding="3">
	<tr bgcolor="#F5F5F5">
		<td><b><%=login.getName()%></b></td>
		<td align="right"><b><a1 href="logout.jsp">로그아웃</a></b></td>
	</tr>
</table>
<!--GB List Start -->
<%
    Vector<GuestBookBeen> vlist = mgr.listGuestBook(id,login.getGrade());
    //out.print(vlist.size());
    if(!vlist.isEmpty()){
%>
    }else{
        for(int i =0;i<vlist.size();i++){

        }//--for
    }//--if--else
%>

<!--CM List End -->

<!--CB Form Start -->

<!--CB Form End -->

<!--GB List Start -->

<!--GB List End -->
</div>
</body>
</html>