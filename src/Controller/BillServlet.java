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
import Beans.Student;
import DAO.CloseRegisDAO;

/**
 * Servlet implementation class BillServlet
 */
@WebServlet("/BillServlet")
public class BillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BillServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//获取学生id
		Student student=(Student) request.getSession().getAttribute("user");
		String student_id=student.getS_id();
		//获取bill
		CloseRegisDAO dao=new CloseRegisDAO();
		Map<Course,Integer> bill=dao.getBill(student_id);
		//计算总价格
		int total_cost=0;
		for(int cost : bill.values()) {
			total_cost+=cost;
		}
		//传入jsp
		List<Course> courseList=new ArrayList<Course>(bill.keySet());
		List<Integer> costList=new ArrayList<Integer>(bill.values());
		request.setAttribute("CourseList", courseList);
        request.setAttribute("CostList", costList);
        request.setAttribute("TotalCost", total_cost);
        request.getRequestDispatcher("jsp/Student/ShowBill.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
