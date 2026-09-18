<!-- login.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String id = (String)session.getAttribute("idKey");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<script type="text/javascript">
    function loginCheck() {
        if (document.loginFrm.id.value == "") {
            alert("아이디를 입력해 주세요.");
            document.loginFrm.id.focus();
            return;
        }
        if (document.loginFrm.pwd.value == "") {
            alert("비밀번호를 입력해 주세요.");
            document.loginFrm.pwd.focus();
            return;
        }
        document.loginFrm.submit();
    }
</script>

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-sm-8 col-md-5 col-lg-4">
            <%if(id != null){ %>
            <div class="card shadow-sm">
                <div class="card-body text-center py-5">
                    <h5 class="mb-3"><strong><%=id%></strong>님 환영합니다.</h5>
                    <p class="text-muted mb-4">제한된 기능을 사용하실 수 있습니다.</p>
                    <div class="d-grid gap-2">
                        <a href="logout.jsp" class="btn btn-outline-secondary">로그아웃</a>
                        <a href="memberUpdate.jsp" class="btn btn-primary">회원정보 수정</a>
                    </div>
                </div>
            </div>
            <%} else {
                id = request.getParameter("id");
            %>
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center py-3">
                    <h5 class="mb-0">로그인</h5>
                </div>
                <div class="card-body p-4">
                    <form name="loginFrm" method="post" action="loginProc.jsp">
                        <div class="mb-3">
                            <label class="form-label">아이디</label>
                            <input type="text" name="id" class="form-control"
                                   value="<%=(id != null) ? id : "aaa"%>" autocomplete="username">
                        </div>
                        <div class="mb-4">
                            <label class="form-label">비밀번호</label>
                            <input type="password" name="pwd" class="form-control" value="1234" autocomplete="current-password">
                        </div>
                        <div class="d-grid gap-2">
                            <button type="button" class="btn btn-primary" onclick="loginCheck()">로그인</button>
                            <button type="button" class="btn btn-outline-secondary"
                                    onclick="location.href='member.jsp'">회원가입</button>
                        </div>
                    </form>
                </div>
            </div>
            <%}%>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>