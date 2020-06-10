package Controller;

import Beans.Professor;
import Beans.Student;
import Beans.Registrar;
import DAO.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RegistrarServlet extends BaseServlet {
    public boolean formatCheck(String string){
        if (string == null || string.equals("")) {
            return false;
        }
        else
            return true;
    }
    public void addProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("p_id");
        String p_name = req.getParameter("p_name");
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String password = req.getParameter("password");

        //格式再判断
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            ProfessorDAO professorDAO = new ProfessorDAOImpl();
            Professor professor = professorDAO.findById(p_id);
            if (professor.getP_id().equals("null")) {
                professor = new Professor(p_id, p_name, birthday, identify_num, status, dept_id, password);
                professorDAO.insert(professor);
                List<Professor> list = new ArrayList<Professor>();
                list.add(professor);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "id已存在！");
                req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
                return;
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void addStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String s_id = req.getParameter("s_id");
        String s_name = req.getParameter("s_name");
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String graduate_time = req.getParameter("birthday");
        java.util.Date graduate_Date = new SimpleDateFormat("yyyy-MM-dd").parse(graduate_time);
        Date graduate = new Date(graduate_Date.getTime());
        String password = req.getParameter("password");
        //格式在jsp页面进行判断
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.findById(s_id);
            if (student.getS_id().equals("null")) {
                student= new Student(s_id, s_name, birthday, identify_num, status, dept_id,graduate, password);
                studentDAO.insert(student);
                List<Student> list = new ArrayList<Student>();
                list.add(student);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", "id已存在！");
                req.getRequestDispatcher("/jsp/Registrar/NewStudent.jsp").forward(req, resp);
                return;
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }
    }
    public boolean notBlank(String string){
        if(string != null && !string.equals(""))
            return true;
        else return false;
    }
    public void searchProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idString=req.getParameter("by_id");
        String name=req.getParameter("by_name");
        List<Professor> list=new ArrayList<Professor>();
        ProfessorDAO professorDAO = new ProfessorDAOImpl();
        if(!notBlank(idString)&&!notBlank(name)){
            list=professorDAO.showAll();
        }
        if(notBlank(idString)&&!notBlank(name)){
            //only id
            Professor professor = professorDAO.findById(idString);
            if (professor.getP_id().equals(idString)){//found!
                list.add(professor);
            }//not found add nothing!
        }
        if(!notBlank(idString)&&notBlank(name)){
            //only name
            List<Professor> resultList = professorDAO.findByName(name);
            for(Professor professor:resultList){
                if(professor!=null)
                    list.add(professor);
                //found and add
            }
            //not found add nothing!
        }
        HttpSession session = req.getSession();
        session.setAttribute("list",list);
        resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
    }
    public void searchStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idString=req.getParameter("by_id");
        String name=req.getParameter("by_name");
        List<Student> list=new ArrayList<Student>();
        StudentDAO studentDAO = new StudentDAOImpl();
        if(!notBlank(idString)&&!notBlank(name)){
            list=studentDAO.showAll();
        }
        if(notBlank(idString)&&!notBlank(name)){
            //only id
            Student student = studentDAO.findById(idString);
            if (student.getS_id().equals(idString)){//found!
                list.add(student);
            }//not found add nothing!
        }
        if(!notBlank(idString)&&notBlank(name)){
            //only name
            List<Student> resultList = studentDAO.findByName(name);
            for(Student student:resultList){
                if(student!=null)
                    list.add(student);
                //found and add
            }
            //not found add nothing!
        }
        HttpSession session = req.getSession();
        session.setAttribute("list",list);
        resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
    }
    public void modify(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //此为查询界面跳转到修改页面的函数
        ProfessorDAO professorDAO = new ProfessorDAOImpl();
        try {
            String id = req.getParameter("id");
            if(formatCheck(id)) {
                Professor professor = professorDAO.findById(id);
                HttpSession session=req.getSession();
                req.setAttribute("professor", professor);
                req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", "id错误无法修改");
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyStu(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //此为查询界面跳转到修改页面的函数
        StudentDAO studentDAO = new StudentDAOImpl();
        try {
            String id = req.getParameter("id");
            if(formatCheck(id)) {
                Student student = studentDAO.findById(id);
                HttpSession session=req.getSession();
                req.setAttribute("student",student);
                req.getRequestDispatcher("/jsp/Registrar/MaintainStudent.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", "id错误无法修改");
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req,resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void modifyProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("id");
        String p_name = req.getParameter("p_name");
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String password = req.getParameter("password");
        //格式再判断
        System.out.println(p_id);
/*
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
 */
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        RegistrarDAO registererDAO = new RegistrarDAOImpl();
        if(registerer!=null){
            Registrar server_register = registererDAO.findById(registerer.getR_id());
            if(registerer.getPassword().equals(server_register.getPassword())) {
                ProfessorDAO professorDAO = new ProfessorDAOImpl();
                Professor professor = new Professor(p_id, p_name, birthday, identify_num, status, dept_id, password);
                professorDAO.update(professor);
                List<Professor> list = new ArrayList<Professor>();
                list.add(professor);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
            }else{
                req.setAttribute("error", "登录信息有误！");
                backToIndex(req, resp);
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void modifyStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String s_id = req.getParameter("id");
        String s_name = req.getParameter("p_name");
        String time = req.getParameter("birthday");
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String time1 = req.getParameter("graduate_date");
        java.util.Date utilDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date graduate = new Date(utilDate.getTime());
        String password = req.getParameter("password");
        //格式再判断
        System.out.println(s_id);
/*
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
 */
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        RegistrarDAOImpl registererDAO = new RegistrarDAOImpl();
        if(registerer!=null){
            Registrar server_register = registererDAO.findById(registerer.getR_id());
            if(registerer.getPassword().equals(server_register.getPassword())) {
                StudentDAO studentDAO = new StudentDAOImpl();
                Student student = new Student(s_id, s_name, birthday, identify_num, status, dept_id,graduate,password);
                studentDAO.update(student);
                List<Student> list = new ArrayList<Student>();
                list.add(student);
                req.setAttribute("list", list);
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
            }else{
                req.setAttribute("error", "登录信息有误！");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }

    }
    public void deleteProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("id");
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            ProfessorDAO professorDAO = new ProfessorDAOImpl();
            Professor professor = professorDAO.findById(p_id);
            if (!professor.getP_id().equals("null")) {
                professorDAO.delete(p_id);
                resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
            } else {
                req.setAttribute("error", "id不存在！");
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }
    }
    public void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String s_id = req.getParameter("id");
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistrarDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            StudentDAO studentDAO = new StudentDAOImpl();
            Student student = studentDAO.findById(s_id);
            if (!student.getS_id().equals("null")) {
                studentDAO.delete(s_id);
                resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
            } else {
                req.setAttribute("error", "id不存在！");
                req.getRequestDispatcher("/jsp/Registrar/SearchStudent.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchStudent.jsp");
        }else{
            req.setAttribute("error", "登录信息有误！");
            backToIndex(req, resp);
        }
    }
    public void backToIndex(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("registration");
        session.removeAttribute("selectedC");
        session.removeAttribute("allC");
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

}
