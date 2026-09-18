<!-- idCheck.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="ch14.MemberMgr"/>
<%
      	String id = request.getParameter("id");
		boolean result = mgr.duplicationId(id);
		//out.print(result);
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ID 중복체크</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center" style="min-height:100vh;">
<div class="text-center p-4">
  <%if (result) {%>
  <div class="alert alert-danger mb-3" role="alert">
    <strong><%=id%></strong>는 이미 존재하는 ID입니다.
  </div>
  <%} else {%>
  <div class="alert alert-success mb-3" role="alert">
    <strong><%=id%></strong>는 사용 가능합니다.
  </div>
  <%}%>
  <button type="button" class="btn btn-outline-secondary btn-sm"
    onclick="self.close()">닫기</button>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>