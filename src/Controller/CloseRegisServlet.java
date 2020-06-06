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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("enter servlet ");
		//get registration
		RegistrationDAO regDao = new RegistrationDAOImpl();
		Registration reg=regDao.queryLatest();
		int reg_id=reg.getReg_id();
		//check if registration is open
		if(reg.getStatus().equals("closed")) {
			System.out.println("registration have been closed");
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
			for(String primaryCourse : primary) {
				if(courseList.get(primaryCourse)==false) {
					primary.remove(primaryCourse);		
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
		///close registration
		dao.closeRegistration(reg_id);
				
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
		System.out.println("close registration");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
