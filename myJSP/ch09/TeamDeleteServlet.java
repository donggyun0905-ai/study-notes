package ch09;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/ch09/teamDelete")
public class TeamDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request
            , HttpServletResponse response) throws ServletException, IOException {
        TeamMgr mgr = new TeamMgr();
        int num =0;
        if(request.getParameter("num")!=null
                && MUtil.isNumeric(request.getParameter("num"))){
            num = MUtil.parseInt(request, "num");
            mgr.deleteTeam(num);
        }
        response.sendRedirect("teamList.jsp");
    }
}

