package ch15;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

@WebServlet("/ch15/boardDelete")
public class BoardDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//read.jsp에서 세션에 저장된 bean를 리턴
		HttpSession session = request.getSession();
		BoardBean bean = (BoardBean)session.getAttribute("bean");
		String dbPass = bean.getPass();
		String inPass = request.getParameter("pass");
		if(inPass.equals(dbPass)) {
			//비번이 일치
			BoardMgr mgr = new BoardMgr();
			mgr.deleteBoard(bean.getNum(), request);
			//게시판 원글 삭제시 모든 댓글 삭제
			
			String nowPage = request.getParameter("nowPage");
			String numPerPage = request.getParameter("numPerPage");
			String keyField = request.getParameter("keyField");
			String keyWord = request.getParameter("keyWord");
			String url = "list.jsp?nowPage="+nowPage+"&numPerPage="+numPerPage;
			if(!(keyWord==null||keyWord.equals(""))) {
				url+="&keyField="+keyField;
				url+="&keyWord="+URLEncoder.encode(keyWord, "UTF-8");
			}
			response.sendRedirect(url);
		}else {
			//비번이 불일치
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('입력하신 비밀번호가 아닙니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
	}
}









