<%--fileSelect.jsp--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<form method="post" action="viewPage.jsp" enctype="multipart/form-data">
    user: <input name="user" value="홍길동"><br>
    title: <input name="title" value="파일업로드"><br>
    file: <input type="file" name="myfile"><br>
    <input type="submit" value="파일전송">
</form>