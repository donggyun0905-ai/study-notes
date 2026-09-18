package ch08;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ch08/exServlet2")
public class ExServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//서블릿 라이프 사이클: init -> service -> destroy
	
	@Override //서블릿 처음 요청시 한번만 실행
	public void init(ServletConfig config) throws ServletException {
		System.out.println("init 호출");
	}

	@Override //Client가 요청이 있을때 마다 실행
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("service 호출");
	}
	
	//Client의 요청이 있을때 doGet(get 메소드), doPost(post 메소드)
	
	@Override //서비스 종료 및 서블릿 코드가 수정 될때
	public void destroy() {
		System.out.println("destroy 호출");
	}
}
