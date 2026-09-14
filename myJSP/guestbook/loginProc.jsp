<!-- loginProc.jsp -->
<%@page contentType="text/html; charset=UTF-8"%>
<jsp:useBean id="mgr" class="guestbook.GuestBookMgr"/>
<jsp:useBean id="login" class="guestbook.JoinBean"/>
<jsp:setProperty property="*" name="login"/>
<%
    String url = "login.jsp";
    if(request.getParameter("url")!=null
    && !request.getParameter("url").equals("null")){
        url = request.getParameter("url");
    }

    boolean result = mgr.loginJoin(login.getId(), login.getPwd());
    if(result){
        login = mgr.getJoin(login.getId());//로그인 정보
        session.setAttribute("idKey",login.getId());
        session.setAttribute("login",login);
        response.sendRedirect(url);
    }else{
        url = "login.jsp";
        response.sendRedirect("login.jsp?url=" + url);
    }

%>

