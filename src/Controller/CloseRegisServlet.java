
package Controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.Course;
import Beans.Registration;
import Beans.Schedule;
import DAO.CloseRegisDAO;
import DAO.RegistrationDAO;
import DAO.RegistrationDAOImpl;
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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("enter servlet ");
		//get onLineCount and check
		HttpSession session=request.getSession();
        ServletContext application=session.getServletContext();
        Integer onLineCount= (Integer) application.getAttribute("onLineCount");
        System.out.println("onLineCount="+onLineCount);
        if(onLineCount==null) {
        	request.setAttribute("close_error", "fail to read onLineCount!");
			request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
        	return;
        }
        if(onLineCount>1) {
			request.setAttribute("close_error", "Someone online!");
			request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
        	return;
        }
		//get registration
        RegistrationDAO regdao=new RegistrationDAOImpl();
		Registration reg=regdao.queryLatest();		
		int reg_id=reg.getReg_id();
		//check if registration is open
		if(reg.getStatus().equals("closed")) {
			System.out.println("registration have been closed");
			request.setAttribute("close_error", "Registration has been closed!");
			request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
			return;
		}
		
		CloseRegisDAO dao=new CloseRegisDAO();		
		Map<String,Boolean> courseList=dao.getCourseList(reg_id);//保存课程id和是否commit的映射关系
		Map<String,Integer> StudentCountList=dao.getStudentCountList(reg_id);
		Map<String,Schedule> ScheduleList=dao.getScheduleList(reg_id);
		//commit courses
		for(Map.Entry<String,Boolean> entry : courseList.entrySet()) {
			if(StudentCountList.get(entry.getKey())>=3&&dao.haveTeacher(entry.getKey(),reg_id)) {
				entry.setValue(true);
			}
		}
		//select alternate to level the schedule		
		for(Map.Entry<String,Schedule> entry : ScheduleList.entrySet()) {
			List<String> primary=entry.getValue().getPrimary();
			List<String> alternate=entry.getValue().getAlternate();
			int j=primary.size();//防止因为元素增减变化影响遍历过程
			if(j>4) {
				request.setAttribute("close_error", "primary count>4");
				request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
				return;
			}
			for(int i=j;i<4;i++) {
					if(alternate.size()>0) {
						String selectedCourse=alternate.get(0);
						primary.add(selectedCourse);
						int count=StudentCountList.get(selectedCourse);
						StudentCountList.put(selectedCourse, count+1);
						alternate.remove(0);					
					}				
			}
			for(int i=0;i<j;i++) {
				String primaryCourse=primary.get(i);
				if(courseList.get(primaryCourse)==false) {
					if(alternate.size()>0) {
						String selectedCourse=alternate.get(0);
						primary.remove(primaryCourse);
						primary.add(selectedCourse);
						int count=StudentCountList.get(selectedCourse);
						StudentCountList.put(selectedCourse, count+1);
						alternate.remove(0);					
						i--;
						j--;
					}				
				}
			}
		}
		//commit courses because of leveling
		for(Map.Entry<String,Boolean> entry : courseList.entrySet()) {
			if(StudentCountList.get(entry.getKey())>=3&&dao.haveTeacher(entry.getKey(),reg_id)) {
				entry.setValue(true);
			}
		}
		//cancel courses still not commited
		for(Map.Entry<String,Schedule> entry : ScheduleList.entrySet()) {
			List<String> primary=entry.getValue().getPrimary();
			int j=primary.size();//防止因为元素增减变化影响遍历过程
			for(int i=0;i<j;i++) {
				String primaryCourse=primary.get(i);
				if(courseList.get(primaryCourse)==false) {
					primary.remove(primaryCourse);	
					i--;
					j--;
				}
			}
		}
		//update database
		///cancel courses
		for(Map.Entry<String,Boolean> entry : courseList.entrySet()) {
			if(entry.getValue()==false) {
				String course=entry.getKey();
				dao.deleteCourse(course,reg_id);
				dao.deleteSelection(course,reg_id);
			}
		}
		///remove alternate selections(not selected) 
		for(Map.Entry<String,Schedule> entry : ScheduleList.entrySet()) {
			List<String> alternate=entry.getValue().getAlternate();
			for(String alt : alternate) {
				dao.deleteSelection(alt, reg_id);
			}
		}
		///update studentCount because of leveling
		for(Map.Entry<String, Integer> entry : StudentCountList.entrySet()) {
			dao.updateStudentCount(entry.getKey(),entry.getValue(),reg_id);
		}
		///upgrade alternate selection to primary selection(selected)
		dao.upgradeAlternate(reg_id);
		
		///close registration
		dao.closeRegistration(reg_id);
		reg.setStatus("closed");
		request.getSession().setAttribute("registration", reg);
		//debug print
		for(Map.Entry<String,Schedule> entry : ScheduleList.entrySet()) {
			System.out.println("enter schedule");
			List<String> primary=entry.getValue().getPrimary();
			List<String> alternate=entry.getValue().getAlternate();
			for(String primaryCourse : primary) {
				System.out.println("primary:"+primaryCourse);
			}
			for(String alternateCourse : alternate) {
				System.out.println("alternate:"+alternateCourse);
			}
		}
		request.setAttribute("close_success", "Registration has been closed successfully!");
		request.getRequestDispatcher("/jsp/Registrar/RegistrarPage.jsp").forward(request, response);
		System.out.println("close registration");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
