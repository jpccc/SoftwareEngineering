package Controller;

import Beans.Professor;
import Beans.Registrar;
import DAO.ProfessorDAO;
import DAO.ProfessorDAOImpl;
import DAO.RegistererDAO;
import DAO.RegistererDAOImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrarServlet extends BaseServlet {
    public boolean formatCheck(String string){
        if (string == null || string.equals("")) {
            return false;
        }
        else
            return true;
    }
    public static boolean hasSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    public void addProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("p_id");
        if(p_id==null||hasSpecialChar(p_id)){
            req.setAttribute("error", "id含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String p_name = req.getParameter("p_name");
        if(p_name==null||hasSpecialChar(p_name)){
            req.setAttribute("error", "name含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String time = req.getParameter("birthday");
        if(time==null){
            req.setAttribute("error", "birthday不能为空！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        if(identify_num==null||isInteger(identify_num)){
            req.setAttribute("error", "identify num需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String status = req.getParameter("status");
        if(status==null||isInteger(status)){
            req.setAttribute("error", "status需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String sdept_id = req.getParameter("dept_id");
        if(sdept_id==null||isInteger(sdept_id)){
            req.setAttribute("error", "部门编号需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        int dept_id = Integer.parseInt(sdept_id);
        String password = req.getParameter("password");
        if(password==null||hasSpecialChar(password)){
            req.setAttribute("error", "密码含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistererDAOImpl().findById(registerer.getR_id());
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
        resp.sendRedirect("/SoftwareEngineering/jsp/Registrar/SearchProfessor.jsp");
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
        String p_id = req.getParameter("p_id");
        if(p_id==null){
            req.setAttribute("error", "id为空");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String p_name = req.getParameter("p_name");
        if(p_name==null||hasSpecialChar(p_name)){
            req.setAttribute("error", "name含有非法字符！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String time = req.getParameter("birthday");
        if(time==null){
            req.setAttribute("error", "birthday不能为空！");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        Date birthday = new Date(utilDate.getTime());
        String identify_num = req.getParameter("identify_num");
        if(identify_num==null||isInteger(identify_num)){
            req.setAttribute("error", "identify num需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String status = req.getParameter("status");
        if(status==null||isInteger(status)){
            req.setAttribute("error", "status需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        String sdept_id = req.getParameter("dept_id");
        if(sdept_id==null||isInteger(sdept_id)){
            req.setAttribute("error", "部门编号需为数字");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        int dept_id = Integer.parseInt(sdept_id);
        String password = req.getParameter("password");
        if(password==null||hasSpecialChar(password)){
            req.setAttribute("error", "密码含有非法字符");
            req.getRequestDispatcher("/jsp/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        RegistererDAO registererDAO = new RegistererDAOImpl();
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
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }else{
            req.setAttribute("error", "登录信息有误！");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }

    }
    public void deleteProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String p_id = req.getParameter("id");
        Registrar registerer = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistererDAOImpl().findById(registerer.getR_id());
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
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
