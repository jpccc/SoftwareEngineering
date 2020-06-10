package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import Beans.Course;

public class ViewReportCardDAO {
	public ViewReportCardDAO() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/registration?characterEncoding=utf-8&useSSL=false&useUnicode=true&serverTimezone=UTC", "root",
                "123456");
    }
	public Map<Course,String> getReportCard(String student_id,int reg_id){
		try (Connection c = getConnection();PreparedStatement s = c.prepareStatement("select selection.course_id,grade from selection,course_info where selection.course_id=course_info.course_id and selection.reg_id=course_info.reg_id and student_id= ? and selection.reg_id= ? and status='submit';"); ) {	
			s.setString(1,student_id);
            s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<Course,String> ReportCard=new HashMap<Course,String>();
            while(rs.next()) {
            	String course_id=rs.getString("course_id");
            	Course course=getCourseInfo(course_id,reg_id);
            	String grade=rs.getString("grade");
            	ReportCard.put(course, grade);
            }
            return ReportCard;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return new HashMap<Course,String>();
	}
	public Course getCourseInfo(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select * from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);
			s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery(); 
			Course course=new Course();
			if(rs.next()) {
				course.setCourse_id(rs.getString("course_id"));
				course.setCourse_name(rs.getString("course_name"));
				course.setDept_id(rs.getInt("dept_id"));
				course.setEnd_date(rs.getDate("end_date"));
				course.setStart_date(rs.getDate("start_date"));
				course.setTimeslot_id(rs.getInt("timeslot_id"));
				course.setWeekday(rs.getInt("weekday"));
			}
            return course;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
}
