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
import Beans.Registration;
import Beans.Student;
import DAO.ProfessorDAO;
import DAO.ProfessorDAOImpl;
import DAO.SelectCourseDAO;
import DAO.SelectCourseDAOImpl;
import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;
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
			
			RegistrationDAO regDao = new RegistrationDAOImpl();
			Registration reg = regDao.queryLatest();
	        if (reg == null||(reg.getReg_id()==-1)) {
	            request.setAttribute("queryError", "登录超时，请重新登录");
	            request.getRequestDispatcher("RegistrarServlet?method=backToIndex")
	                    .forward(request, response);
	            return;
	        } else if (reg.getStatus()!=null&&!reg.getStatus().equals("open")){//如果不在注册阶段
	            request.setAttribute("message", "本次课程注册已经结束");
	            request.getRequestDispatcher("/jsp/Student/add_course.jsp").forward(request, response);
	            return;
	        }else { request.removeAttribute("message");}
	        
			
			List<CourseSelection> schedule=new ArrayList<CourseSelection>();
			schedule=select_course_dao.get_schedule(student_id,reg.getReg_id());
		

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
				for(int i=0;i<100;i++){
                    int in_list_primary=0;
                    int in_list_alternate=0;
					String str=request.getParameter(String.valueOf(i));
					System.out.println(request.getParameter(String.valueOf(i)));
					if(str!=null) {
						System.out.println(str);

						String [] arr = str.split("\\s+");
						String course_id=arr[0];
						int reg_id=Integer.valueOf(arr[1]);

						String type=arr[2];
						if(type.equals("primary")) {
							in_list_primary+=1;
						}
						if(type.equals("alternate")) {
							in_list_alternate+=1;
						}
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
						int pri_num=0;
						for(int c=0;c<schedule.size();c++) {
							if(schedule.get(c).get_select_status().equals("primary")) {
								pri_num+=1;
							}
						}
						
						int alter_num=0;
						for(int c=0;c<schedule.size();c++) {
							if(schedule.get(c).get_select_status().equals("alternate")) {
								alter_num+=1;
							}
						}
						String over_ten="false";
						Course course=select_course_dao.check_course(course_id, reg_id);
						System.out.println("select course servlet info: course.student_num="+course.getStudent_count());
						if(type.equals("primary")&&course.getStudent_count()>9) {
							over_ten="true";
						}
						
						String over_two_alt="false";
						System.out.println("select course info:course.alternate_num="+alter_num);
						if(alter_num+in_list_alternate>2) {
							over_two_alt="true";
						}
						
						if(select_course_dao.satisfy_prerequire(student_id,reg.getReg_id(), course_selection).equals("yes")
						&& select_course_dao.no_conflict(schedule, course_selection).equals("yes")
						&&pri_num+in_list_primary<=4
						&&over_ten.equals("false")
						&&over_two_alt.equals("false")) {
							select_course_dao.add_course_selection(course_selection);
							schedule.add(course_selection);
							if(type.equals("primary")) {
								select_course_dao.add_student_num(course_id, reg_id);
							}
							
							request.setAttribute("message_select", "添加成功!");
						}else {
							if(select_course_dao.satisfy_prerequire(student_id,reg.getReg_id(), course_selection).equals("no")) {
								request.setAttribute("message_select", "请先选择该课程先修课!");
							}
							if(select_course_dao.no_conflict(schedule, course_selection).equals("no")) {
								request.setAttribute("message_select", "课程时间冲突!");
							}
							if(pri_num+in_list_primary>4) {
								request.setAttribute("message_select", "超过4科首选课!");
							}
							if(over_ten.equals("true")) {
								request.setAttribute("message_select", "课程人数已满(10人)!");
							}
							if(over_two_alt.equals("true")) {
								request.setAttribute("message_select", "备选课超过2科!!");
							}
							break;
						}

					}
				}
				request.getRequestDispatcher("jsp/Student/add_course.jsp").forward(request, response);
			}


			if(op!=null&& op.contentEquals("delete")) {
				String delete_all=request.getParameter("delete_all");
				if(delete_all!=null) {
					//DAO.delete all
					for(int i=0;i<schedule.size();i++) {
						String course_id=schedule.get(i).get_course_id();
						int reg_id=schedule.get(i).get_reg_id();
						select_course_dao.delete_course_selection(course_id, reg_id,student_id);
						if(schedule.get(i).get_select_status().equals("primary")) {
							select_course_dao.dec_student_num(course_id, reg_id);
						}
						
						
						
					}
					request.setAttribute("message", "delete_all success");
					request.getRequestDispatcher("jsp/Student/show_schedule.jsp").forward(request, response);
					return;
				}
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
						for(int n=0;n<schedule.size();n++) {
							if(schedule.get(n).get_course_id().equals(course_id)&&schedule.get(n).get_reg_id()==reg_id) {
								if(schedule.get(n).get_select_status().equals("primary")) {
									select_course_dao.dec_student_num(course_id, reg_id);
								}
							}
						}
						
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
