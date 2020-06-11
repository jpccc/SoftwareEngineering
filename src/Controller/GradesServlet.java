package Controller;

import Beans.*;
import DAO.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
public class GradesServlet extends BaseServlet {
    public void queryCourses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        RegistrationDAO regDao = new RegistrationDAOImpl();
        Registration reg = regDao.queryLatest();
        if (reg == null||(reg.getReg_id()==-1)) {
            request.setAttribute("queryError", "��¼��ʱ�������µ�¼");
            request.getRequestDispatcher("RegistrarServlet?method=backToIndex")
                    .forward(request, response);
            return;
        } else if (reg.getStatus()!=null&&!reg.getStatus().equals("closed")){//�������ѡ�ν׶�
            request.setAttribute("queryError", "���ογ�ע�ỹ�ڽ�����");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
            return;
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
            request.setAttribute("queryError", "δ�ҵ�����ѧ�ڵ��ڿ���Ϣ");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        } else {
            request.setAttribute("courseList", courses);
            if(session.getAttribute("gradeList")!=null)session.removeAttribute("gradeList");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }
    }

    public void queryStudents(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        String reg=request.getParameter("registration_id");
        int reg_id=Integer.parseInt(reg);
        Professor pro= (Professor) session.getAttribute("user");
        CourseDAO courseDAO=new CourseDAOImpl();
        List<Grade> grades = null;
        grades=courseDAO.queryStudents4Course(reg_id,request.getParameter("course_id"));
        if(grades==null||grades.size()==0){
            request.setAttribute("queryError", "δ�ҵ��ÿγ̵�ѧ����Ϣ");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }else{
            session.setAttribute("gradeList", grades);
            if(session.getAttribute("courseList")!=null)session.removeAttribute("courseList");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
        }
    }
    public void saveGrades(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        if(!verifyProfessor(request,response))return;
        List<Grade> grades= getNewGrades(request);
        if(grades==null){
            request.getRequestDispatcher("GradesServlet?method=queryCourses")
                    .forward(request, response);
            return;
        }
        CourseDAO courseDAO=new CourseDAOImpl();
        courseDAO.saveGrades(grades);
        if(grades.size()>0){
            Course course=courseDAO.findCourse(grades.get(0).getCourse_id(),grades.get(0).getReg_id());
            course.setStatus("saved");
            try {
                courseDAO.update(course);
                session.removeAttribute("gradeList");
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("queryError", "����ʧ��");
                request.getRequestDispatcher("GradesServlet?method=queryCourses")
                        .forward(request, response);
                return;
            }
        }
        request.getRequestDispatcher("GradesServlet?method=queryCourses")
                .forward(request, response);
    }

    public void submitGrades(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        if(!verifyProfessor(request,response))return;
        List<Grade> grades= getNewGrades(request);
        if(grades==null){
            request.getRequestDispatcher("GradesServlet?method=queryCourses")
                    .forward(request, response);
            return;
        }
        if(!checkGrades(grades)){
            request.setAttribute("queryError", "�Ƿ��ɼ���Ϣ������������ύ��");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
            return;
        }else{
            CourseDAO courseDAO=new CourseDAOImpl();
            courseDAO.saveGrades(grades);
            if(grades!=null&&grades.size()>0){
                Course course=courseDAO.findCourse(grades.get(0).getCourse_id(),grades.get(0).getReg_id());
                course.setStatus("submit");
                try {
                    courseDAO.update(course);
                    session.removeAttribute("gradeList");
                } catch (Exception e) {
                    e.printStackTrace();
                    request.setAttribute("queryError", "�ύʧ��");
                    request.getRequestDispatcher("GradesServlet?method=queryCourses")
                            .forward(request, response);
                    return;
                }
            }
        }
        request.getRequestDispatcher("GradesServlet?method=queryCourses")
                .forward(request, response);
    }
    public void backToMainPage(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
        HttpSession session=request.getSession();
        if(session.getAttribute("courseList")!=null)session.removeAttribute("courseList");
        if(session.getAttribute("gradeList")!=null)session.removeAttribute("gradeList");
        request.getRequestDispatcher("/jsp/Professor/ProfessorPage.jsp").forward(request, response);
        return;
    }

    private List<Grade> getNewGrades(HttpServletRequest request){
        HttpSession session=request.getSession();
        List<Grade> grades= (List<Grade>) session.getAttribute("gradeList");
        if(grades!=null){
            for(int i=0;i<grades.size();i++){
                grades.get(i).setGrade(request.getParameter("grade"+i));
            }
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
    private boolean verifyProfessor(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        HttpSession session=request.getSession();
        Professor curr= (Professor) session.getAttribute("user");
        if(curr==null){
            request.setAttribute("error", "��¼��ʱ��");
            request.getRequestDispatcher("/SoftwareEngineering/ProfessorServlet?method=backToIndex").forward(request, response);
            return false;
        }
        ProfessorDAO professorDAO=new ProfessorDAOImpl();
        Professor db=null;
        boolean flag=true;
        try {
            db=professorDAO.findById(curr.getP_id());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("queryError", "���ݿ�����ʧ�ܣ�������");
            request.getRequestDispatcher("/jsp/Professor/SubmitGrades.jsp").forward(request, response);
            return false;
        }
        if(db==null||db.getP_id().equals("null")||!db.getPassword().equals(curr.getPassword())){
            flag=false;
            request.setAttribute("queryError", "�����֤ʧ�ܣ������µ�¼����");
            if(session.getAttribute("courseList")!=null)session.removeAttribute("courseList");
            if(session.getAttribute("gradeList")!=null)session.removeAttribute("gradeList");
            request.getRequestDispatcher("ProfessorServlet?method=backToIndex").forward(request, response);
        }
        return flag;
    }
}
