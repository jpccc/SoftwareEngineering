package Controller;

import Beans.Course;
import Beans.Registrar;
import Beans.Registration;
import DAO.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionListener;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class OpenRegisServlet extends BaseServlet{
    public void addOne(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        HttpSession session=request.getSession();
        List<Course> total= (List<Course>) session.getAttribute("allC");
        List<Course> selected= (List<Course>) session.getAttribute("selectedC");
        if(selected==null)selected=new ArrayList<>();
        int index=Integer.parseInt(request.getParameter("index"));
        Map<String,String[]> param=request.getParameterMap();
        Course course=total.get(index);
        String[] strings=param.get("timeMask");
        long val1=0x0000000000000000L;
        long mask=1;
        for(int i=0;i<strings.length;i++){
            val1=val1|(mask<<Integer.parseInt(strings[i]));
            //a bug here caused by the mask part above,java default use int ,so left shift 32 bit meet overflow
        }

        int  timeslot= (int) (val1 & 0x000000ffffffffL); //lower 32 bits,bit 0-6 means the monday
        //and bit 7-13 means tuesday,lower means earlier class
        int weekday = (int) (val1 >> 32); //higher 32 bits, actually 24 bits useful

        course.setWeekday(weekday);
        course.setTimeslot_id(timeslot);
        course.setPrice(Float.parseFloat(request.getParameter("price")));


        System.out.println("beginTime:"+request.getParameter("beginTime"));
        System.out.println("endTime:"+request.getParameter("endTime"));
        course.setStart_date(Date.valueOf(request.getParameter("beginTime")));
        course.setEnd_date(Date.valueOf(request.getParameter("endTime")));
        System.out.println(course.getStart_date());
        System.out.println(course.getEnd_date());
        selected.add(course);
        total.remove(index);

        session.setAttribute("allC",total);
        session.setAttribute("selectedC",selected);
        request.getRequestDispatcher("jsp/Registrar/OpenRegistration.jsp").forward(request,response);
    }

    public void removeOne(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        System.out.println("test:removeOne()");
        HttpSession session=request.getSession();
        List<Course> total= (List<Course>) session.getAttribute("allC");
        List<Course> selected= (List<Course>) session.getAttribute("selectedC");
        int index=Integer.parseInt(request.getParameter("index"));
        Course course=selected.get(index);
        course.setTimeslot_id(0);
        course.setWeekday(0);
        total.add(course);
        selected.remove(index);
        request.getRequestDispatcher("jsp/Registrar/OpenRegistration.jsp").forward(request,response);
    }

    public void startNewReg(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        System.out.println("test:startNewReg()");
        HttpSession session=request.getSession();
        ServletContext application=session.getServletContext();
        Integer count= (Integer) application.getAttribute("onLineCount");
        Registrar registrar= (Registrar) session.getAttribute("user");
        try {
            Registrar db=new RegistrarDAOImpl().findById(registrar.getR_id());
            if(!db.getPassword().equals(registrar.getPassword())){
                request.setAttribute("RegistrarError","身份验证失败，请查证后重新登录！");
                request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("RegistrarError","数据库操作失败，请重试");
            request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
            return;
        }
        if(count>5){//TODO: change to 1 before use
            request.setAttribute("RegistrarError","当前有其他用户在线，开启注册失败");
            request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
            return;
        }
        Registration reg= (Registration) session.getAttribute("registration");
        if(reg==null){
            RegistrationDAOImpl regDao=new RegistrationDAOImpl();
            reg=regDao.queryLatest();
            session.setAttribute("registration",reg);
        }
        if(reg.getStatus()==null||!reg.getStatus().equals("closed")){
            request.setAttribute("RegistrarError","当前有活动状态的注册，请先关闭注册");
            request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
            return;
        }

        Calendar calendar=Calendar.getInstance();
        reg.setYear(calendar.get(Calendar.YEAR));
        int month=calendar.get(Calendar.MONTH)+1;
        if(month>=2&&month<9)reg.setSemester("spring");
        else reg.setSemester("fall");
        reg.setStatus("opening");
        int key=new RegistrationDAOImpl().insert(reg);
        reg.setReg_id(key);
        session.setAttribute("registration",reg);
        List<Course> total=new CourseDAOImpl().findAll(DruidManager.OLDSYS_FLAG);
        session.setAttribute("allC",total);
        request.getRequestDispatcher("jsp/Registrar/OpenRegistration.jsp").forward(request,response);
    }

    public void submitReg(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        System.out.println("test:submitReg()");
        HttpSession session=request.getSession();
        List<Course> selected= (List<Course>) session.getAttribute("selectedC");
        Registration reg= (Registration) session.getAttribute("registration");
        if(reg==null){
            RegistrationDAOImpl regDao=new RegistrationDAOImpl();
            reg=regDao.queryLatest();
            session.setAttribute("registration",reg);
        }
        verifyRegistrar(request,response);
        for(int i=0;i<selected.size();i++){
            selected.get(i).setReg_id(reg.getReg_id());
        }
        try {
            new CourseDAOImpl().insert(selected);
            reg.setStatus("open");
            new RegistrationDAOImpl().update(reg);
            session.setAttribute("registration",reg);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("RegistrarError","提交失败，请重新提交");
            cancelReg(request,response);
            return;
        }
        request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
    }

    public void cancelReg(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        System.out.println("test:cancelReg()");
        HttpSession session=request.getSession();
        if(session.getAttribute("allC")!=null)session.removeAttribute("allC");
        if(session.getAttribute("selectedC")!=null)session.removeAttribute("selectedC");
        Registration reg= (Registration) session.getAttribute("registration");
        if(reg==null){
            RegistrationDAOImpl regDao=new RegistrationDAOImpl();
            reg=regDao.queryLatest();
            session.setAttribute("registration",reg);
        }
        RegistrationDAO registrationDAO=new RegistrationDAOImpl();
        try {
            registrationDAO.deleteByID(reg.getReg_id());
            new CourseDAOImpl().deleteByRegid(reg.getReg_id());//删除已经开的课程，避免因异常而插入数据
            reg=registrationDAO.queryLatest();
            session.setAttribute("registration",reg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
    }
    private void verifyRegistrar(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        Registrar registrar= (Registrar) session.getAttribute("user");
        if(registrar==null){
            request.setAttribute("error","登录超时，请重新登录");
            request.getRequestDispatcher("/SoftwareEngineering/RegistrarServlet?method=backToIndex")
                    .forward(request,response);
            return;
        }
        try {
            Registrar db=new RegistrarDAOImpl().findById(registrar.getR_id());
            if(db==null||db.getR_id().equals("null")||!db.getPassword().equals(registrar.getPassword())){
                request.setAttribute("error","身份验证失败，请重新登录");
                request.getRequestDispatcher("/SoftwareEngineering/RegistrarServlet?method=backToIndex")
                        .forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("RegistrarError","数据库连接失败，请重试");
            request.getRequestDispatcher("jsp/Registrar/RegistrarPage.jsp").forward(request,response);
        }
    }
}
