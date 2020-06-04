package Controller;

import Beans.Professor;
import Beans.Registerer;
import Beans.Student;
import DAO.ProfessorDAO;
import DAO.ProfessorDAOImpl;
import DAO.RegistererDAOImpl;
import DAO.StudentDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(username==null||password==null){
            request.setAttribute("error","网络错误，请重试");
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return;
        }else{
            switch(username.charAt(0)){
                case 'P':{
                    ProfessorDAO professorDAO=new ProfessorDAOImpl();
                    Professor pro=null;
                    try {
                        pro=professorDAO.findById(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(pro!=null
                            &&!pro.getP_id().equals("null")
                            &&pro.getPassword().equals(password)){
                        request.getSession().setAttribute("user",pro);
                        request.getRequestDispatcher("/jsp/Professor/ProfessorPage.jsp")
                                .forward(request,response);
                        return;
                    }
                    break;
                }
                case 'S':{
                    StudentDAOImpl std=new StudentDAOImpl();
                    Student student=null;
                    try {
                        student=std.findById(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(student!=null
                            &&!student.getS_id().equals("null")
                            &&student.getPassword().equals(password)) {
                        request.getSession().setAttribute("user",student);
                        request.getRequestDispatcher("/jsp/Student/StudentPage.jsp")
                                .forward(request,response);
                        return;
                    }
                    break;
                }
                case 'R': {
                    RegistererDAOImpl reger=new RegistererDAOImpl();
                    Registerer registerer=null;
                    try {
                        registerer=reger.findById(username);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(registerer!=null
                            &&!registerer.getR_id().equals("null")
                            &&registerer.getPassword().equals(password)) {
                        request.getSession().setAttribute("user",registerer);
                        request.getRequestDispatcher("/jsp/Registerer/RegistererPage.jsp")
                                .forward(request, response);
                        return;
                    }
                    break;
                }
            }
            request.setAttribute("error","账号或密码错误");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
