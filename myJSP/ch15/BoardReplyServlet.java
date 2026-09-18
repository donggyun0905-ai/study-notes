package ch15;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch15/boardReply")
public class BoardReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardMgr mgr = new BoardMgr();
		BoardBean reBean = new BoardBean();
		
		reBean.setName(request.getParameter("name"));
		reBean.setSubject(request.getParameter("subject"));
		reBean.setContent(request.getParameter("content"));
		reBean.setRef(MUtil.parseInt(request, "ref"));
		reBean.setPos(MUtil.parseInt(request, "pos"));
		reBean.setDepth(MUtil.parseInt(request, "depth"));
		reBean.setPass(request.getParameter("pass"));
		reBean.setIp(request.getParameter("ip"));
		
		//답변 위치 확보를 위한 pos값 수정
		mgr.replyUpBoard(reBean.getRef(), reBean.getPos());
		//답변저장
		mgr.replyBoard(reBean);
		
		String nowPage = request.getParameter("nowPage");
		String numPerPage = request.getParameter("numPerPage");
		String url = "list.jsp?nowPage="+nowPage+"&numPerPage="+numPerPage;
		response.sendRedirect(url);
	}
}




