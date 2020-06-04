package Controller;

import Beans.Course;
import Beans.Professor;
import Beans.Registration;
import DAO.CourseDAO;
import DAO.CourseDAOImpl;
import DAO.ProfessorDAOImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfessorServlet extends BaseServlet {
    public List<Course> checkConflict(List<Course> clist) {
        List<Course> conflictList = new ArrayList<Course>();
        for (Course course1 : clist) {
            for (Course course2 : clist) {
                if (course1.getCourse_id() != course2.getCourse_id()) {
                    if (course1.getStart_date().before(course2.getEnd_date())
                            && course1.getStart_date().after(course2.getEnd_date())
                            && course1.getWeekday() == course2.getWeekday()) {
                        conflictList.add(course1);
                        conflictList.add(course2);
                    }
                    if (course1.getStart_date().after(course2.getStart_date())
                            && course1.getEnd_date().before(course2.getEnd_date())
                            && course1.getWeekday() == course2.getWeekday()) {
                        conflictList.add(course1);
                        conflictList.add(course2);
                    }
                }
            }
        }
        return conflictList;
    }

    public void getCourseList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Registration registration = (Registration) req.getSession().getAttribute("registration");
        if (registration != null) {
            int reg_id = registration.getReg_id();
            CourseDAO courseDAO = new CourseDAOImpl();
            Professor professor = (Professor) req.getSession().getAttribute("user");
            if (professor != null) {
                List<Course> slist = courseDAO.findSelected(professor.getP_id(), reg_id);
                List<Course> clist = courseDAO.findQualified(professor.getDept_id(), reg_id);
                req.getSession().setAttribute("clist", clist);
                List<Course> conflictList = checkConflict(slist);
                if (conflictList.size() != 0) {
                    Iterator<Course> iterator = slist.iterator();
                    while (iterator.hasNext()) {
                        Course course = iterator.next();
                        if (conflictList.contains(course))
                            iterator.remove();
                    }
                }
                req.getSession().setAttribute("slist", slist);
                req.getSession().setAttribute("conflictList", conflictList);
                resp.sendRedirect("/SoftwareEngineering_war/jsp/Professor/SelectToTeach.jsp");
            } else {
                req.setAttribute("error", "登录超时！");
                req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "课表系统故障！");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String course_id = req.getParameter("course_id");
        CourseDAO courseDAO = new CourseDAOImpl();
        Course course = courseDAO.findById(course_id);
        if (!course.getCourse_id().equals("-1")) {
            //found!
            Professor professor = (Professor) req.getSession().getAttribute("user");
            Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
            if (professor.getPassword().equals(server_professor.getPassword())) {
                if (professor != null) {
                    course.setProfessor_id(professor.getP_id());
                    course.setStatus("1");
                    courseDAO.update(course);
                    getCourseList(req, resp);
                } else {
                    req.setAttribute("error", "登录超时");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "登录信息有误");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "课程信息获取失败！");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void deSelect(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String course_id = req.getParameter("course_id");
        CourseDAO courseDAO = new CourseDAOImpl();
        Course course = courseDAO.findById(course_id);
        if (!course.getCourse_id().equals("-1")) {
            //found!
            Professor professor = (Professor) req.getSession().getAttribute("user");
            Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
            if (professor.getPassword().equals(server_professor.getPassword())) {
                if (professor != null) {
                    course.setStatus("0");
                    courseDAO.update(course);
                    getCourseList(req, resp);
                } else {
                    req.setAttribute("error", "登录超时");
                    req.getRequestDispatcher("/index.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "登录信息有误");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "课程信息获取失败！");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void deSelectAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        CourseDAO courseDAO = new CourseDAOImpl();
        Professor professor = (Professor) req.getSession().getAttribute("user");
        Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
        if(professor.getPassword().equals(server_professor.getPassword())) {
            Registration registration = (Registration) req.getSession().getAttribute("registration");
            if (professor != null) {
                List<Course> slist = courseDAO.findSelected(professor.getP_id(), registration.getReg_id());
                if (slist.size() != 0) {
                    for (Course course : slist) {
                        course.setStatus("0");
                        courseDAO.update(course);
                    }
                    getCourseList(req, resp);
                } else {
                    req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("error", "登录超时");
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        }else {
            req.setAttribute("error", "登录信息有误");
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
