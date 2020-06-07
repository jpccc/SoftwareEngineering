package Controller;

import Beans.Professor;
import Beans.Registrar;
import Beans.Registration;
import DAO.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistrarServlet extends BaseServlet {
    public boolean formatCheck(String string){
        if (string == null || string.equals("") || string.length() < 6) {
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
        Registrar registrar = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistererDAOImpl().findById(registrar.getR_id());
        if(registrar.getPassword().equals(server_register.getPassword())) {
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
        Registrar registrar = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistererDAOImpl().findById(registrar.getR_id());
        if(registrar.getPassword().equals(server_register.getPassword())) {
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
        Registrar registrar = (Registrar) req.getSession().getAttribute("user");
        Registrar server_register = new RegistererDAOImpl().findById(registrar.getR_id());
        if(registrar.getPassword().equals(server_register.getPassword())) {
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

    public void openRegistration(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        Integer count= (Integer) request.getSession().getServletContext().getAttribute("onLine");
        if(count==1){
            Registration reg= (Registration) request.getSession().getAttribute("registration");
            if(!reg.getStatus().equals("closed")){
                request.setAttribute("RegistrarError","当前有注册正在进行，开启失败");
                request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request,response);
                return;
            }else{
                Registration newReg=new Registration();
                Calendar calendar= Calendar.getInstance();
                newReg.setYear(calendar.get(Calendar.YEAR));
                int month=calendar.get(Calendar.MONTH)+1;
                if(month>=2&&month<=8)newReg.setSemester("spring");
                else newReg.setSemester("fall");
                RegistrationDAO registrationDAO=new RegistrationDAOImpl();
                newReg.setStatus("open");
                int key=registrationDAO.insert(newReg);//获得新生成的key，用于更新课程列表
                /*
                    后续开启课程时需要如何更新开的课程尚不清楚，暂时保留，可以从request中获取参数来添加课程
                 */
                request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request,response);
            }
        }else{
            request.setAttribute("RegistrarError","有其他用户在线，操作失败！");
            request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request,response);
        }
    }
}
