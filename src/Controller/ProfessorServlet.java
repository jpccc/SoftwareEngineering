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
import javax.servlet.http.HttpSession;
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
                            && course1.getStart_date().after(course2.getStart_date())&&(( ( (~(course1.getWeekday()^course2.getWeekday()))& course2.getWeekday() )!=0x00000000 )||( ( (~(course1.getTimeslot_id()^course2.getTimeslot_id()))&course1.getTimeslot_id())!=0x00000000 ))) {
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
                resp.sendRedirect("/SoftwareEngineering/jsp/Professor/SelectToTeach.jsp");
            } else {
                req.setAttribute("error", "��¼��ʱ��");
                backToIndex(req, resp);
            }
        } else {
            req.setAttribute("error", "�α�ϵͳ���ϣ�");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void select(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Registration registration = (Registration) req.getSession().getAttribute("registration");
        if (registration.getStatus().equals("open")) {
            String course_id = req.getParameter("course_id");
            CourseDAO courseDAO = new CourseDAOImpl();
            Course course = courseDAO.findCourse(course_id,registration.getReg_id());
            if (!course.getCourse_id().equals("-1")) {
                //found!
                Professor professor = (Professor) req.getSession().getAttribute("user");
                Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
                if (professor.getPassword().equals(server_professor.getPassword())) {
                    if (professor != null) {
                        course.setProfessor_id(professor.getP_id());
                        courseDAO.update(course);
                        getCourseList(req, resp);
                    } else {
                        req.setAttribute("error", "��¼��ʱ");
                        backToIndex(req, resp);
                    }
                } else {
                    req.setAttribute("error", "��¼��Ϣ����");
                    backToIndex(req, resp);
                }
            } else {
                req.setAttribute("error", "�γ���Ϣ��ȡʧ�ܣ�");
                req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "ע��δ������");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void deSelect(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Registration registration = (Registration) req.getSession().getAttribute("registration");
        if (registration.getStatus().equals("open")) {
            String course_id = req.getParameter("course_id");
            CourseDAO courseDAO = new CourseDAOImpl();
            Course course = courseDAO.findCourse(course_id,registration.getReg_id());
            if (!course.getCourse_id().equals("-1")) {
                //found!
                Professor professor = (Professor) req.getSession().getAttribute("user");
                Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
                if (professor.getPassword().equals(server_professor.getPassword())) {
                    if (professor != null) {
                        course.setProfessor_id(null);
                        courseDAO.update(course);
                        getCourseList(req, resp);
                    } else {
                        req.setAttribute("error", "��¼��ʱ");
                        backToIndex(req, resp);
                    }
                } else {
                    req.setAttribute("error", "��¼��Ϣ����");
                    backToIndex(req, resp);
                }
            } else {
                req.setAttribute("error", "�γ���Ϣ��ȡʧ�ܣ�");
                req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "ע��δ������");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void deSelectAll(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Registration registration1 = (Registration) req.getSession().getAttribute("registration");
        if (registration1.getStatus().equals("open")) {
            CourseDAO courseDAO = new CourseDAOImpl();
            Professor professor = (Professor) req.getSession().getAttribute("user");
            Professor server_professor = new ProfessorDAOImpl().findById(professor.getP_id());
            if (professor.getPassword().equals(server_professor.getPassword())) {
                Registration registration = (Registration) req.getSession().getAttribute("registration");
                if (professor != null) {
                    List<Course> slist = courseDAO.findSelected(professor.getP_id(), registration.getReg_id());
                    if (slist.size() != 0) {
                        for (Course course : slist) {
                            course.setProfessor_id(null);
                            courseDAO.update(course);
                        }
                        getCourseList(req, resp);
                    } else {
                        req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
                    }
                } else {
                    req.setAttribute("error", "��¼��ʱ");
                    backToIndex(req, resp);
                    return;
                }
            } else {
                req.setAttribute("error", "��¼��Ϣ����");
                backToIndex(req, resp);
            }
        } else {
            req.setAttribute("error", "ע��δ������");
            req.getRequestDispatcher("/jsp/Professor/SelectToTeach.jsp").forward(req, resp);
        }
    }

    public void backToIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        session.removeAttribute("registration");
        session.removeAttribute("courseList");
        session.removeAttribute("gradeList");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        return;
    }

}
