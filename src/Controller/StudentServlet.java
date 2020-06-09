package Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class StudentServlet extends BaseServlet {
    public void backToIndex(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("registration");
        session.removeAttribute("CourseList");
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }
}
