package Controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Course;
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
		//获取student_id
		String student_id=(String) request.getParameter("student_id");
		System.out.println("get student_id="+student_id);
		//student_id="0";
		//获取ReportCard
		ViewReportCardDAO dao=new ViewReportCardDAO();
		Map<Course,Integer> ReportCard=dao.getReportCard(student_id);
		//debug print
		for(Map.Entry<Course, Integer> entry : ReportCard.entrySet()) {
			System.out.println("ReportCard: course_id="+entry.getKey().getCourse_id()+",grade="+entry.getValue());
		}
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
