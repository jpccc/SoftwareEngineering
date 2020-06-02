package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Beans.Course;
import Beans.Schedule;
import DAO.CloseRigisDAO;
/**
 * Servlet implementation class CloseRegisServlet
 */
@WebServlet("/CloseRegisServlet")
public class CloseRegisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CloseRegisServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("enter servlet ");
		CloseRigisDAO dao=new CloseRigisDAO();
		//dao.checkInProgress();
		Map<String,Boolean> courseList=dao.getCourseList();
		Map<String,Integer> StudentCountList=dao.getStudentCountList();
		Map<String,Schedule> ScheduleList=dao.getScheduleList();
		for(Map.Entry<String,Boolean> entry : courseList.entrySet()) {
			if(StudentCountList.get(entry.getKey())>=3) {
				entry.setValue(true);
			}
		}
		for(Map.Entry<String,Schedule> entry : ScheduleList.entrySet()) {
			List<String> primary=entry.getValue().getPrimary();
			List<String> alternate=entry.getValue().getAlternate();
			System.out.println("schedule map: primary_size="+primary.size());
			System.out.println("schedule map:"+entry.getKey()+primary.get(0)+primary.get(1)+primary.get(2)+primary.get(3)+' '+alternate.get(0)+alternate.get(1));
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
