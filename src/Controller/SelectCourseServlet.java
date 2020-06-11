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

import Beans.*;
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
		response.setCharacterEncoding("UTF-8");
		try {
			Student student = (Student) request.getSession().getAttribute("user");
			String student_name=student.getS_name();
			String student_id=student.getS_id();
			String s="";
			SelectCourseDAO select_course_dao=new SelectCourseDAOImpl();
			String op=request.getParameter("op");
			response.getWriter().println(op);
			String id=(String)request.getParameter("op");
			Registration registration1 = (Registration) request.getSession().getAttribute("registration");
			if(!registration1.getStatus().equals("open")) {
				request.setAttribute("message", "系统未开放！");
				request.getRequestDispatcher("jsp/Student/add_course.jsp").forward(request, response);
				return;
			}
			if( op!=null&& op.equals("addxxx") ){
				id=(String)request.getParameter("id");

				System.out.println(id);
				CourseSelection course_selection=new CourseSelection();



				course_selection.set_student_id(student_name);
				course_selection.set_course_id(id);
				course_selection.set_reg_id(12);

				List<CourseSelection> schedule=new ArrayList<CourseSelection>();
				schedule=select_course_dao.get_schedule(student_name);
				System.out.println(select_course_dao.no_conflict(schedule,course_selection));
				if(select_course_dao.no_conflict(schedule,course_selection).equals("yes")) {


					select_course_dao.add_course_selection(course_selection);
					request.setAttribute("message", "add success");
				}

				request.getRequestDispatcher("jsp/Student/add_course.jsp").forward(request, response);

			}

			if( op!=null&& op.equals("deletexxx")){
				id=(String)request.getParameter("id");
				System.out.println(id);
				select_course_dao.delete_course_selection(id, 12,"li");
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
				request.getRequestDispatcher("jsp/Student/add_course.jsp").forward(request, response);
			}

			if(op!=null&& op.contentEquals("add")) {
				List<CourseSelection> schedule=new ArrayList<CourseSelection>();
				schedule=select_course_dao.get_schedule(student_id);
				for(int i=0;i<100;i++){
					String str=request.getParameter(String.valueOf(i));
					System.out.println(request.getParameter(String.valueOf(i)));
					if(str!=null) {
						System.out.println(str);

						String [] arr = str.split("\\s+");
						String course_id=arr[0];
						int reg_id=Integer.valueOf(arr[1]);

						String type=arr[2];

						float price=Float.valueOf(arr[3]);
						System.out.println("price= "+price);

						CourseSelection course_selection=new CourseSelection();
						course_selection.set_student_id(student_id);
						course_selection.set_course_id(course_id);
						course_selection.set_reg_id(reg_id);
						course_selection.set_select_status(type);
						course_selection.setPrice(price);
						/*
						List<CourseSelection> schedule=new ArrayList<CourseSelection>();
						schedule=select_course_dao.get_schedule("li");
						System.out.println(select_course_dao.no_conflict(schedule,course_selection));
						if(select_course_dao.no_conflict(schedule,course_selection).equals("yes")) {
							select_course_dao.add_course_selection(course_selection);
							request.setAttribute("message", "add success");
						}
						*/
						if(select_course_dao.satisfy_prerequire(schedule, course_selection).equals("yes")
								&& select_course_dao.no_conflict(schedule, course_selection).equals("yes")
								&&schedule.size()+1<=4) {
							select_course_dao.add_course_selection(course_selection);
							select_course_dao.add_student_num(course_id, reg_id);
							request.setAttribute("message", "add success  PRICE="+price +"type="+type);
						}else {
							if(select_course_dao.satisfy_prerequire(schedule, course_selection).equals("no")) {
								request.setAttribute("message", "not satisfy prev_require courses!");
							}
							if(select_course_dao.no_conflict(schedule, course_selection).equals("no")) {
								request.setAttribute("message", "time slot conflict!");
							}
							if(schedule.size()+1>4) {
								request.setAttribute("message", "over 4 courses!");
							}
						}

					}
				}
				request.getRequestDispatcher("jsp/Student/add_course.jsp").forward(request, response);
			}


			if(op!=null&& op.contentEquals("delete")) {
				for(int i=0;i<300;i++){
					String str=request.getParameter(String.valueOf(i));
					System.out.println(request.getParameter(String.valueOf(i)));
					if(str!=null) {
						String [] arr = str.split("\\s+");
						System.out.println("arr0 " +arr[0]);
						System.out.println("arr1 "+arr[1]);
					}

					if(str!=null) {
						String [] arr = str.split("\\s+");
						System.out.println(arr[0]);
						System.out.println(arr[1]);
						String course_id=arr[0];
						System.out.println(course_id);
						int reg_id=Integer.valueOf(arr[1]);
						System.out.println(reg_id);

						select_course_dao.delete_course_selection(course_id, reg_id,student_id);
						select_course_dao.add_student_num(course_id, reg_id);
						request.setAttribute("message", "delete success");
					}

				}
				request.getRequestDispatcher("jsp/Student/show_schedule.jsp").forward(request, response);
			}

			//request.getRequestDispatcher("jsp/Student/schedule.jsp").forward(request, response);
			//request.getRequestDispatcher("test.jsp").forward(request, response);



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
