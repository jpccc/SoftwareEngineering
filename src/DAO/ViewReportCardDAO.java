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
                "zm2019..");
    }
	public Map<Course,Integer> getReportCard(String student_id,int reg_id){
		try (Connection c = getConnection();PreparedStatement s = c.prepareStatement("select course_id,grade from selection where student_id= ? and reg_id= ? ;"); ) {	
			s.setString(1,student_id);
            s.setInt(2, reg_id);
			ResultSet rs= s.executeQuery();        
            Map<Course,Integer> ReportCard=new HashMap<Course,Integer>();
            while(rs.next()) {
            	String course_id=rs.getString("course_id");
            	Course course=getCourseInfo(course_id,reg_id);
            	int grade=rs.getInt("grade");
            	ReportCard.put(course, grade);
            }
            return ReportCard;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Course getCourseInfo(String course_id,int reg_id) {
		try (Connection c = getConnection(); PreparedStatement s = c.prepareStatement("select * from course_info where course_id= ? and reg_id= ? ;");) {	
			s.setString(1, course_id);	
			s.setInt(2, reg_id);
            ResultSet rs= s.executeQuery();        
            if(rs.next()) {
            	Course course=new Course();
            	course.setCourse_id(rs.getString("course_id"));
            	course.setCourse_name(rs.getString("course_name"));
            	return course;
            }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
}
