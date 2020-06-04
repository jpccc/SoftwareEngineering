package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
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
                "root");
    }
	public Map<Course,Integer> getReportCard(String student_id){
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
			String sql = "select course_id,grade from selection where student_id="+student_id+';';	
            ResultSet rs= s.executeQuery(sql);        
            Map<Course,Integer> ReportCard=new HashMap<Course,Integer>();
            while(rs.next()) {
            	String course_id=rs.getString("course_id");
            	Course course=getCourseInfo(course_id);
            	int grade=rs.getInt("grade");
            	ReportCard.put(course, grade);
            }
            return ReportCard;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Course getCourseInfo(String course_id) {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
			String sql = "select * from course_info where course_id="+course_id+';';	
            ResultSet rs= s.executeQuery(sql);        
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
