package com.lxb.Servlet;

import com.lxb.Professor.Professor;
import com.lxb.Professor.ProfessorDAO;
import com.lxb.Professor.ProfessorDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        int identify_num = Integer.parseInt(req.getParameter("identify_num"));
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String password = req.getParameter("password");

        //格式再判断
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }
        ProfessorDAO professorDAO=new ProfessorDAOImpl();
        Professor professor = professorDAO.findById(p_id);
        if(professor.getP_id().equals("null")){
            professor = new Professor(p_id,p_name,birthday,identify_num,status,dept_id,password);
            professorDAO.insert(professor);
            resp.sendRedirect("/SoftwareEngineering_war/Registrar/SearchProfessor.jsp");
        }else{
            req.setAttribute("error", "id已存在！");
            req.getRequestDispatcher("/Registrar/NewProfessor.jsp").forward(req, resp);
            return;
        }

    }

    public void searchProfessor(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String idString=req.getParameter("by_id");
        String name=req.getParameter("by_name");
        List<Professor> list=new ArrayList<Professor>();
        ProfessorDAO professorDAO = new ProfessorDAOImpl();
        if(!formatCheck(idString)&&!formatCheck(name)){
            list=professorDAO.showAll();
        }
        if(formatCheck(idString)&&!formatCheck(name)){
            //only id
            Professor professor = professorDAO.findById(idString);
            if (professor.getP_id().equals(idString)){//found!
                list.add(professor);
            }//not found add nothing!
        }
        if(!formatCheck(idString)&&formatCheck(name)){
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
        resp.sendRedirect("/SoftwareEngineering_war/Registrar/SearchProfessor.jsp");
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
                req.getRequestDispatcher("/Registrar/MaintainProfessor.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", "id错误无法修改");
                req.getRequestDispatcher("/Registrar/searchProfessor.jsp").forward(req,resp);
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
        int identify_num = Integer.parseInt(req.getParameter("identify_num"));
        String status = req.getParameter("status");
        int dept_id = Integer.parseInt(req.getParameter("dept_id"));
        String password = req.getParameter("password");
        //格式再判断
        if (!formatCheck(p_id)) {
            req.setAttribute("error", "id格式有误！");
            req.getRequestDispatcher("/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        if (!formatCheck(password)) {
            req.setAttribute("error", "密码格式有误！");
            req.getRequestDispatcher("/Registrar/MaintainProfessor.jsp").forward(req, resp);
            return;
        }
        ProfessorDAO professorDAO=new ProfessorDAOImpl();
        Professor professor = new Professor(p_id,p_name,birthday,identify_num,status,dept_id,password);
        professorDAO.update(professor);
        resp.sendRedirect("/SoftwareEngineering_war/Registrar/SearchProfessor.jsp");
    }
}
