package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Beans.Course;
import Beans.Schedule;
public class CloseRigisDAO {
	public CloseRigisDAO() {
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
	public Map<String,Boolean> getCourseList() {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
			String sql = "select course_id from course_info where `reg_id`=0";			   
            ResultSet rs= s.executeQuery(sql);        
            Map<String,Boolean> courseList=new HashMap<String,Boolean>();
            while(rs.next()) {
            	Course course=new Course();
            	course.setCourse_id(rs.getString(1));
            	courseList.put(course.getCourse_id(),false);
            }
            
            return courseList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public Map<String,Schedule> getScheduleList(){
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
			String sql = "select student_id,course_id,select_status from selection where `reg_id`=0";			   
            ResultSet rs= s.executeQuery(sql);        
            Map<String,Schedule> List=new HashMap<String,Schedule>();
            Map<String,ArrayList<String>> primaryList=new HashMap<String,ArrayList<String>>();
            Map<String,ArrayList<String>> alternateList=new HashMap<String,ArrayList<String>>();
            while(rs.next()) {
            	String student_id=rs.getString("student_id");
            	String course_id=rs.getString("course_id");
            	String select_status=rs.getString("select_status");
            	ArrayList<String> primary=primaryList.get(student_id);
            	ArrayList<String> alternate=alternateList.get(student_id);
            	if(primary==null) {
            		primary=new ArrayList<String>();
            		primaryList.put(student_id,primary);
            	}
            	if(alternate==null) {
            		alternate=new ArrayList<String>();
            		alternateList.put(student_id,alternate);
            	}
            	if(select_status.equals("primary")) {      			
            		primary.add(course_id);
            	}
            	if(select_status.equals("alternate")) {
            		alternate.add(course_id);
            	}
            }
            
            for(Map.Entry<String,ArrayList<String>> entry : primaryList.entrySet()) {
            	String student_id=entry.getKey();
            	if(List.get(student_id)==null) {
            		List.put(student_id, new Schedule(student_id,entry.getValue(),alternateList.get(student_id)));
            	}
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public boolean haveTeacher(String course_id) {		
		try (Connection c = getConnection(); Statement s = c.createStatement();) {
			   
            String sql = "select professor_id from course_info where course_id="+course_id+";"; 
            ResultSet rs= s.executeQuery(sql);
            Map<String,Boolean> courseList=new HashMap<String,Boolean>();
            if(rs.next()) {
            	String result=rs.getString(1);
            	System.out.println("result:" + result);
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public Map<String,Integer> getStudentCountList() {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {
			   
            String sql = "select course_id,student_count from course_info; ";
            ResultSet rs= s.executeQuery(sql);        
            Map<String,Integer> List=new HashMap<String,Integer>();
            while(rs.next()) {
            	List.put(rs.getString(1), rs.getInt(2));
            }
            return List;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
	}
	public boolean checkInProgress() {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {
			   
            String sql = "select `status` from Registration where `reg_id`=0";
   
            ResultSet rs= s.executeQuery(sql);
            String result="";
            System.out.println("result:" + result);
            if(rs.next()) {
            	result=rs.getString(1);
            	System.out.println("result:" + result);
            }
            if(result.equals("InProgress")) {
            		return true;
            }
            else if(result.equals("notInProgress")){
            		return false;
            }else {
            	throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean deleteCourse(String course) {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
            String sql = "delete from course_info where course_id="+course+';';
            int result=s.executeUpdate(sql);           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
	public boolean deleteSelection(String course) {
		try (Connection c = getConnection(); Statement s = c.createStatement();) {	
            String sql = "delete from selection where course_id="+course+';';
            int result=s.executeUpdate(sql);           
            System.out.println("result:delete count=" + result);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
	}
}
