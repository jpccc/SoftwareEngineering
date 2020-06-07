package Controller;

import Beans.Professor;
import Beans.Registerer;
import DAO.ProfessorDAO;
import DAO.ProfessorDAOImpl;
import DAO.RegistererDAOImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        Registerer registerer = (Registerer) req.getSession().getAttribute("user");
        Registerer server_register = new RegistererDAOImpl().findById(registerer.getR_id());
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
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
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
        resp.sendRedirect("/SoftwareEngineering_war/jsp/Registrar/SearchProfessor.jsp");
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
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/jsp/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        Registerer registerer = (Registerer) req.getSession().getAttribute("user");
        Registerer server_register = new RegistererDAOImpl().findById(registerer.getR_id());
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
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
    public void deleteProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("id");
        Registerer registerer = (Registerer) req.getSession().getAttribute("user");
        Registerer server_register = new RegistererDAOImpl().findById(registerer.getR_id());
        if(registerer.getPassword().equals(server_register.getPassword())) {
            ProfessorDAO professorDAO = new ProfessorDAOImpl();
            Professor professor = professorDAO.findById(p_id);
            if (!professor.getP_id().equals("null")) {
                professorDAO.delete(p_id);
                resp.sendRedirect("/SoftwareEngineering_war/jsp/Registrar/SearchProfessor.jsp");
            } else {
                req.setAttribute("error", "id不存在！");
                req.getRequestDispatcher("/jsp/Registrar/SearchProfessor.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("/SoftwareEngineering_war/jsp/Registrar/SearchProfessor.jsp");
        }else{
            req.setAttribute("error", "登录信息有误！");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
