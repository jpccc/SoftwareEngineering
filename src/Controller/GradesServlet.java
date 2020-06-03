package Controller;

import Beans.*;
import DAO.CourseDAO;
import DAO.CourseDAOImpl;
import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class GradesServlet extends BaseServlet {
    public void queryCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int year = Integer.parseInt(request.getParameter("year"));
        String semester = request.getParameter("semester");
        RegistrationDAO regDao = new RegistrationDAOImpl();
        Registration reg = regDao.queryByTime(year, semester);
        if (reg == null) {
            request.setAttribute("queryError", "查询此次注册失败，请重新输入");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        } else if (reg.getStatus().equals("open")){//如果还在选课阶段
            request.setAttribute("queryError", "本次课程注册还在进行中，请重新查询");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }else  request.removeAttribute("queryError");
        CourseDAO courseDAO = new CourseDAOImpl();
        HttpSession session = request.getSession();
        Professor user = (Professor) session.getAttribute("user");
        List<Course> courses = null;
        try {
            courses = courseDAO.findSelected(user.getP_id(), reg.getReg_id());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (courses == null||courses.size()==0) {
            request.setAttribute("queryError", "未找到您本学期的授课信息");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        } else {
            request.setAttribute("courseList", courses);
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }
    }

    public void queryStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        request.setAttribute("course_name",request.getParameter("course_name"));
        int reg_id=Integer.parseInt(request.getParameter("reg_id"));
        Professor pro= (Professor) session.getAttribute("user");
        CourseDAO courseDAO=new CourseDAOImpl();
        List<Grade> grades = null;
        grades=courseDAO.queryStudents4Course(reg_id,request.getParameter("course_id"));
        if(grades==null||grades.size()==0){
            request.setAttribute("queryError", "未找到该课程的学生信息");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }else{
            session.setAttribute("gradeList", grades);
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }
    }
    public void saveGrades(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        HttpSession session=request.getSession();
        List<Grade> grades= (List<Grade>) session.getAttribute("gradeList");
        CourseDAO courseDAO=new CourseDAOImpl();
        courseDAO.saveGrades(grades);
        if(grades.size()>0){
            Course course=courseDAO.findCourse(grades.get(0).getCourse_id(),grades.get(0).getReg_id());
            course.setStatus("saved");
            try {
                courseDAO.update(course);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("queryError", "保存失败");
                request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
            }
        }
        session.removeAttribute("gradeList");
        request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
    }

    public void submitGrades(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        HttpSession session=request.getSession();
        List<Grade> grades= (List<Grade>) session.getAttribute("gradeList");
        if(!checkGrades(grades)){
            request.setAttribute("queryError", "非法成绩信息！请检查后重新提交！");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }
        CourseDAO courseDAO=new CourseDAOImpl();
        courseDAO.saveGrades(grades);
        if(grades.size()>0){
            Course course=courseDAO.findCourse(grades.get(0).getCourse_id(),grades.get(0).getReg_id());
            course.setStatus("submit");
            try {
                courseDAO.update(course);
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("queryError", "提交失败");
                request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
            }
        }
        session.removeAttribute("gradeList");
        request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
    }
    private List<Grade> getNewGrades(HttpServletRequest request){
        HttpSession session=request.getSession();
        List<Grade> grades= (List<Grade>) session.getAttribute("gradeList");
        for(int i=0;i<grades.size();i++){
            grades.get(i).setGrade(request.getParameter("grade"+i));
        }
        return grades;
    }
    private boolean checkGrades(List<Grade> grades){
        boolean flag=true;
        for (Grade value : grades) {
            String grade = value.getGrade();
            if (!(grade.equals("A") ||
                    grade.equals("B") ||
                    grade.equals("C") ||
                    grade.equals("D") ||
                    grade.equals("E") ||
                    grade.equals("F") ||
                    grade.equals("I"))) {
                flag = false;
                break;
            }
        }
        return flag;
    }
}
