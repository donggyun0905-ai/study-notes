package ch15;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/ch15/boardUpdate")
public class BoardUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		BoardBean bean = (BoardBean)session.getAttribute("bean");
		String dbPass = bean.getPass();
		MultipartRequest multi = 
				new MultipartRequest(request, BoardMgr.getSaveFolder(request),
						BoardMgr.MAXSIZE, BoardMgr.ENCODING,
						new DefaultFileRenamePolicy());
		String inPass = multi.getParameter("pass");
		if(inPass.equals(dbPass)) {
			//비번이 일치
			BoardMgr mgr = new BoardMgr();
			mgr.updateBoard(multi, request);
			String nowPage = multi.getParameter("nowPage");
			String numPerPage = multi.getParameter("numPerPage");
			String url = "read.jsp?num="+bean.getNum()+"&nowPage=";
			url+= nowPage+"&numPerPage="+numPerPage;
			response.sendRedirect(url);
		}else {
			//수정 실패시 업로드된 파일 삭제
			String filename = multi.getFilesystemName("filename");
			if(filename!=null&&!filename.equals("")) {
				File f = new File(BoardMgr.getSaveFolder(request)+filename);
				if(f.exists()) f.delete();
			}
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('입력하신 비밀번호가 아닙니다.')");
			out.println("history.back()");
			out.println("</script>");
		}
	}
}








