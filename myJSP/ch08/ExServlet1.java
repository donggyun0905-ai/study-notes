package ch08;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Tomcat에서 이전 버전에서는 web.xml에 URL Mapping값 세팅
//서버 전체에서 URL Mapping값이 중복이 되면 서버 시작 실패
@WebServlet("/ch08/exServlet1")
public class ExServlet1 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
			System.out.println("123456");
	}
}




