package Controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Course;
import Beans.Registration;
import Beans.Student;
import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;
import DAO.ViewReportCardDAO;

/**
 * Servlet implementation class ViewReportCardServlet
 */
@WebServlet("/ViewReportCardServlet")
public class ViewReportCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewReportCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("report card:enter report card");
		//get registration
		RegistrationDAO regDao = new RegistrationDAOImpl();
		Registration reg=regDao.queryLatest();
		int reg_id=reg.getReg_id();
		System.out.println("report card: reg_id="+reg_id);
		//get student_id
		Student student=(Student) request.getSession().getAttribute("user");
		String student_id=student.getS_id();
		System.out.println("report card: student_id="+student_id);
		//get ReportCard
		ViewReportCardDAO dao=new ViewReportCardDAO();
		Map<Course,String> ReportCard=dao.getReportCard(student_id,reg_id);
		//debug print
		for(Map.Entry<Course, String> entry : ReportCard.entrySet()) {
			System.out.println("ReportCard: course_id="+entry.getKey().getCourse_id()+",grade="+entry.getValue());
		}
		//����jsp
		List<Course> courseList=new ArrayList<Course>(ReportCard.keySet());
		List<String> gradeList=new ArrayList<String>(ReportCard.values());
        request.setAttribute("CourseList", courseList);
        request.setAttribute("GradeList", gradeList);
        request.getRequestDispatcher("jsp/Student/ViewReportCard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
