package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Course;
import Beans.CourseSelection;
import Beans.Professor;
import DAO.ProfessorDAO;
import DAO.ProfessorDAOImpl;
import DAO.SelectCourseDAO;
import DAO.SelectCourseDAOImpl;

/**
 * Servlet implementation class SelectCourseServlet
 */
@WebServlet("/SelectCourseServlet")
public class SelectCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String s="";
			SelectCourseDAO select_course_dao=new SelectCourseDAOImpl();
			String op=request.getParameter("op");
			response.getWriter().println(op);
			String id=(String)request.getParameter("op");
			if( op!=null&& op.equals("add") ){
				id=(String)request.getParameter("id");
				System.out.println(id);
				CourseSelection course_selection=new CourseSelection();
				course_selection.set_student_id("li");
				course_selection.set_course_id(id);
				course_selection.set_reg_id(001);
				select_course_dao.add_course_selection(course_selection);
				request.setAttribute("message", "add success");
				
			}

			if( op!=null&& op.equals("delete")){
				id=(String)request.getParameter("id");
				System.out.println(id);
				select_course_dao.delete_course_selection(id, 001);
				request.setAttribute("message", "delete success");
				
			}
			
			if( op!=null&& op.equals("check")){
				id=(String)request.getParameter("id");
				System.out.println(id);
				List<Course> list=new ArrayList<Course>();
				list=select_course_dao.check_course(id);
				
				s+=("list of courses:<br>");
				
				for (int i = 0; i < list.size(); i++) {
		            s+=(list.get(i).getCourse_id()+"  "+list.get(i).getCourse_name()+"  "+list.get(i).getProfessor_id()
		            	+"  "+list.get(i).getStart_date()+"  "+list.get(i).getEnd_date()+"<br>");
		        }
				request.setAttribute("c", s);
				System.out.println(s);
			}
			
			
			
			
			request.getRequestDispatcher("jsp/Student/schedule.jsp").forward(request, response);
			
			 
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
